/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.portlets.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.layout.portlets.web.internal.search.PortletSearch;
import com.liferay.portal.util.WebAppPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jorge Ferrer
 */
public class LayoutPortletsDisplayContext {

	public LayoutPortletsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_request = request;

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		initPortlets(themeDisplay.getCompanyId());
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(_request, "displayStyle", "list");

		return _displayStyle;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_request, "orderByCol", "name");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public String getPortletCategoryLabels(String portletId) {
		String[] categories = _portletCategories.get(portletId);

		StringBuffer sb = new StringBuffer(categories.length * 2);

		for (int i = 0; i < categories.length; i++) {
			String category = categories[i];
			sb.append(LanguageUtil.get(_request, category));

			if (i < (categories.length -1)) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}
		}

		return sb.toString();
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("displayStyle", getDisplayStyle());

		return portletURL;
	}

	public SearchContainer getSearchContainer() {
		SearchContainer searchContainer = new PortletSearch(
			_renderRequest, getPortletURL());

		searchContainer.setEmptyResultsMessage("there-are-no-widgets");

		searchContainer.setId("layoutPortlets");
		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());

		int total = _portlets.size();

		searchContainer.setTotal(total);

		List results = ListUtil.sort(
			_portlets, searchContainer.getOrderByComparator());

		results = ListUtil.subList(
			results, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		return searchContainer;
	}

	protected void initPortlets(long companyId) {
		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			companyId, WebKeys.PORTLET_CATEGORY);

		Collection<PortletCategory> portletCategories =
			portletCategory.getCategories();

		_portlets = new ArrayList<>();

		for (PortletCategory curPortletCategory : portletCategories) {
			if (curPortletCategory.isHidden()) {
				continue;
			}

			for (String portletId : curPortletCategory.getPortletIds()) {
				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					companyId, portletId);

				if (portlet.isSystem() || !portlet.isInclude()) {
					continue;
				}

				if (portlet != null) {
					_portlets.add(portlet);
				}

				String[] categories = _portletCategories.get(portletId);

				String curPortletCategoryName = curPortletCategory.getName();

				if (categories == null) {
					_portletCategories.put(
						portletId, new String[]{curPortletCategoryName});
				}
				else {
					_portletCategories.put(
						portletId, ArrayUtil.append(
							categories, curPortletCategoryName));
				}
			}
		}
	}

	private String _displayStyle;
	private String _orderByCol;
	private String _orderByType;
	private ArrayList<Portlet> _portlets;
	private Map<String, String[]> _portletCategories = new HashMap<>();
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}