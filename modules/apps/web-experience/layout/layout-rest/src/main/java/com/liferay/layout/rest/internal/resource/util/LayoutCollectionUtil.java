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

package com.liferay.layout.rest.internal.resource.util;

import com.liferay.apio.architect.identifier.LongIdentifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = LayoutCollectionUtil.class)
public class LayoutCollectionUtil {

	public Layout getLayout(LongIdentifier layoutLongIdentifier) {
		try {
			return _layoutLocalService.getLayout(layoutLongIdentifier.getId());
		}
		catch (NoSuchLayoutException nsle) {
			throw new NotFoundException(
				"Unable to get article " + layoutLongIdentifier.getId(), nsle);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	public String getLayoutBreadcrumb(Layout layout, Locale locale) {
		if (layout == null) {
			return StringPool.BLANK;
		}

		List<Layout> ancestors = null;

		try {
			ancestors = layout.getAncestors();
		}
		catch (Exception e) {
			_log.error("Unable to get layout ancestors", e);

			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(locale, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(locale, "public-pages"));
		}

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);

		Collections.reverse(ancestors);

		for (Layout ancestor : ancestors) {
			sb.append(HtmlUtil.escape(ancestor.getName(locale)));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		sb.append(HtmlUtil.escape(layout.getName(locale)));

		return sb.toString();
	}

	public String getLayoutImageUrl(Layout layout) {
		Optional<ServiceContext> serviceContextOptional = Optional.ofNullable(
			ServiceContextThreadLocal.getServiceContext());

		ServiceContext serviceContext = serviceContextOptional.orElse(
			new ServiceContext());

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if ((layout == null) || (themeDisplay == null) ||
			!layout.isIconImage()) {

			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getPathImage());
		sb.append("/layout_icon?img_id");
		sb.append(layout.getIconImageId());
		sb.append("&t=");
		sb.append(WebServerServletTokenUtil.getToken(layout.getIconImageId()));

		return sb.toString();
	}

	public PageItems<Layout> getLayouts(
		Pagination pagination, LongIdentifier groupIdentifier) {

		List<Layout> layouts = new ArrayList<>();
		int totalCount = 0;

		try {
			layouts.addAll(
				_layoutService.getLayouts(groupIdentifier.getId(), false));

			layouts.addAll(
				_layoutService.getLayouts(groupIdentifier.getId(), true));

			Group group = _groupService.getGroup(groupIdentifier.getId());

			totalCount += _layoutLocalService.getLayoutsCount(group, false);

			totalCount += _layoutLocalService.getLayoutsCount(group, true);
		}
		catch (PortalException pe) {
			_log.error("Unable to get layouts", pe);
		}

		return new PageItems<>(layouts, totalCount);
	}

	public Layout removeLayout(LongIdentifier layoutLongIdentifier) {
		try {
			return _layoutLocalService.deleteLayout(
				layoutLongIdentifier.getId());
		}
		catch (NoSuchLayoutException nsle) {
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutCollectionUtil.class);

	@Reference
	private GroupService _groupService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutService _layoutService;

}