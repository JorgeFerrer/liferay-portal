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

package com.liferay.site.admin.web.internal.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.LayoutSetPrototypeImpl;
import com.liferay.site.admin.web.internal.util.GroupCreationStepHelper;
import com.liferay.site.admin.web.internal.util.GroupCreationStepRegistry;
import com.liferay.site.admin.web.internal.util.GroupStarterKitRegistry;
import com.liferay.site.constants.SiteWebKeys;
import com.liferay.site.util.GroupCreationStep;
import com.liferay.site.util.GroupStarterKit;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class SiteCreationWizardDisplayContext {

	public SiteCreationWizardDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		LiferayPortletResponse liferayPortletResponse) {

		_request = request;
		_response = response;
		_liferayPortletResponse = liferayPortletResponse;

		_groupCreationStepHelper =
			(GroupCreationStepHelper)request.getAttribute(
				SiteWebKeys.GROUP_CREATION_STEP_HELPER);

		_groupCreationStepRegistry =
			(GroupCreationStepRegistry)request.getAttribute(
				SiteWebKeys.GROUP_CREATION_STEP_REGISTRY);

		_groupStarterKitRegistry =
			(GroupStarterKitRegistry)request.getAttribute(
				SiteWebKeys.GROUP_STARTER_KIT_REGISTRY);

		String creationStepName = ParamUtil.getString(
			request, "creationStepName");

		GroupCreationStep groupCreationStep =
			_groupCreationStepRegistry.getGroupCreationStep(creationStepName);

		if (groupCreationStep == null) {
			List<GroupCreationStep> groupCreationSteps =
				_groupCreationStepRegistry.getGroupCreationSteps(getGroupId());

			groupCreationStep = groupCreationSteps.get(0);
		}

		_groupCreationStep = groupCreationStep;
	}

	public String getCreationStepRedirect() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String groupCreationStepName = getNextCreationStepName();

		if (Validator.isNull(groupCreationStepName)) {
			portletURL.setParameter("jspPage", "/site_wizard/summary.jsp");

			String redirect = ParamUtil.getString(_request, "redirect");

			if (Validator.isNotNull(redirect)) {
				portletURL.setParameter("redirect", redirect);
			}

			String backURL = ParamUtil.getString(_request, "backURL");

			if (Validator.isNotNull(redirect)) {
				portletURL.setParameter("backURL", backURL);
			}
		}
		else {
			portletURL.setParameter(
				"jspPage", "/site_wizard/site_creation_wizard.jsp");

			String redirect = ParamUtil.getString(_request, "redirect");

			if (Validator.isNotNull(redirect)) {
				portletURL.setParameter("redirect", redirect);
			}

			String creationStepName = ParamUtil.getString(
				_request, "creationStepName");

			if (Validator.isNotNull(creationStepName)) {
				portletURL.setParameter("creationStepName", creationStepName);
			}

			String creationType = ParamUtil.getString(_request, "creationType");

			if (Validator.isNotNull(creationStepName)) {
				portletURL.setParameter("creationType", creationType);
			}

			String groupStarterKitKey = ParamUtil.getString(
				_request, "groupStarterKitKey");

			if (Validator.isNotNull(groupStarterKitKey)) {
				portletURL.setParameter(
					"groupStarterKitKey", groupStarterKitKey);
			}
		}

		return portletURL.toString();
	}

	public String getCurrentCreationStepLabel() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _groupCreationStep.getLabel(themeDisplay.getLocale());
	}

	public String getCurrentCreationStepName() {
		return _groupCreationStep.getName();
	}

	public Group getGroup() throws PortalException {
		long groupId = getGroupId();

		if (groupId > 0) {
			_group = GroupServiceUtil.getGroup(groupId);
		}

		return _group;
	}

	public List<GroupCreationStep> getGroupCreationSteps() {
		return _groupCreationStepRegistry.getGroupCreationSteps(getGroupId());
	}

	public long getGroupId() {
		if (_groupId <= 0) {
			_groupId = ParamUtil.getLong(
				_request, "groupId", GroupConstants.DEFAULT_PARENT_GROUP_ID);
		}

		return _groupId;
	}

	public String getGroupStarterKitDescription() {
		String groupStarterKitKey = ParamUtil.getString(
			_request, "groupStarterKitKey");

		GroupStarterKit groupStarterKit =
			_groupStarterKitRegistry.getGroupStarterKit(groupStarterKitKey);

		if (groupStarterKit == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return groupStarterKit.getDescription(themeDisplay.getLocale());
	}

	public List<GroupStarterKit> getGroupStarterKits() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _groupStarterKitRegistry.getGroupStarterKits(
			themeDisplay.getCompanyId(), true);
	}

	public String getGroupStarterKitThumbnailSrc() {
		String groupStarterKitKey = ParamUtil.getString(
			_request, "groupStarterKitKey");

		GroupStarterKit groupStarterKit =
			_groupStarterKitRegistry.getGroupStarterKit(groupStarterKitKey);

		if (groupStarterKit == null) {
			return StringPool.BLANK;
		}

		return groupStarterKit.getThumbnailSrc();
	}

	public List<LayoutSetPrototype> getLayoutSetPrototypes()
		throws PortalException {

		List<LayoutSetPrototype> layoutSetPrototypes = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		LayoutSetPrototype defaultLayoutSetPrototype =
			new LayoutSetPrototypeImpl();

		defaultLayoutSetPrototype.setActive(true);
		defaultLayoutSetPrototype.setCompanyId(themeDisplay.getCompanyId());
		defaultLayoutSetPrototype.setLayoutSetPrototypeId(0L);

		String blankSiteLayoutSetPrototypeName = LanguageUtil.get(
			_request, "blank-site");

		defaultLayoutSetPrototype.setName(
			blankSiteLayoutSetPrototypeName, themeDisplay.getLocale());

		layoutSetPrototypes.add(defaultLayoutSetPrototype);

		layoutSetPrototypes.addAll(
			LayoutSetPrototypeServiceUtil.search(
				themeDisplay.getCompanyId(), Boolean.TRUE, null));

		return layoutSetPrototypes;
	}

	public String getPreviousCreationStepName() throws Exception {
		GroupCreationStep groupCreationStep =
			_groupCreationStepHelper.getPreviousGroupCreationStep(
				getGroupId(), _groupCreationStep.getName());

		if (groupCreationStep == null) {
			return null;
		}

		return groupCreationStep.getName();
	}

	public String getRenderPreviewURL(
		String functionName, String title, String url) {

		StringBundler sb = new StringBundler(13);

		sb.append("javascript:");
		sb.append(_liferayPortletResponse.getNamespace());
		sb.append(functionName);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(StringPool.APOSTROPHE);
		sb.append(HtmlUtil.escapeJS(title));
		sb.append(StringPool.APOSTROPHE);
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(StringPool.APOSTROPHE);
		sb.append(HtmlUtil.escapeJS(url));
		sb.append(StringPool.APOSTROPHE);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		return sb.toString();
	}

	public String getSummarySuccessMessage(String url) {
		StringBundler sb = new StringBundler(3);

		sb.append(
			LanguageUtil.get(
				_request, "congratulations-your-site-has-been-created"));
		sb.append(StringPool.NEW_LINE);
		sb.append(
			LanguageUtil.format(
				_request,
				"you-can-finish-it-by-configuring-its-x-when-you-are-ready",
				_getSummarySuccessMessageArgument(url)));

		return sb.toString();
	}

	public boolean isLastGroupCreationStep() {
		return _groupCreationStepHelper.isLastGroupCreationStep(
			getGroupId(), getCurrentCreationStepName());
	}

	public void renderCurrentCreationStep() throws Exception {
		_groupCreationStep.render(_request, _response);
	}

	protected String getNextCreationStepName() {
		GroupCreationStep groupCreationStep =
			_groupCreationStepHelper.getNextGroupCreationStep(
				getGroupId(), _groupCreationStep.getName());

		if (groupCreationStep == null) {
			return null;
		}

		return groupCreationStep.getName();
	}

	private String _getSummarySuccessMessageArgument(String url) {
		StringBundler sb = new StringBundler(7);

		sb.append("<a href=");
		sb.append(StringPool.QUOTE);
		sb.append(url);
		sb.append(StringPool.QUOTE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(LanguageUtil.get(_request, "site-settings"));
		sb.append("</a>");

		return sb.toString();
	}

	private Group _group;
	private final GroupCreationStep _groupCreationStep;
	private final GroupCreationStepHelper _groupCreationStepHelper;
	private final GroupCreationStepRegistry _groupCreationStepRegistry;
	private long _groupId;
	private final GroupStarterKitRegistry _groupStarterKitRegistry;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;
	private final HttpServletResponse _response;

}