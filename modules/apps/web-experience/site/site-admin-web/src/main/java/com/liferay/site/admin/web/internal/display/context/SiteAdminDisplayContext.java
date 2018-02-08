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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeServiceUtil;
import com.liferay.portal.kernel.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.impl.LayoutSetPrototypeImpl;
import com.liferay.portal.service.persistence.constants.UserGroupFinderConstants;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.site.admin.web.internal.constants.SiteAdminConstants;
import com.liferay.site.admin.web.internal.constants.SiteAdminPortletKeys;
import com.liferay.site.constants.SiteWebKeys;
import com.liferay.site.util.GroupCreationStep;
import com.liferay.site.util.GroupCreationStepRegistry;
import com.liferay.site.util.GroupSearchProvider;
import com.liferay.site.util.GroupStarterKit;
import com.liferay.site.util.GroupStarterKitRegistry;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pavel Savinov
 * @author Alessio Antonio Rendina
 */
public class SiteAdminDisplayContext {

	public SiteAdminDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		_request = request;
		_response = response;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_groupCreationStepRegistry =
			(GroupCreationStepRegistry)request.getAttribute(
				SiteWebKeys.GROUP_CREATION_STEP_REGISTRY);

		_groupSearchProvider = (GroupSearchProvider)request.getAttribute(
			SiteWebKeys.GROUP_SEARCH_PROVIDER);

		_groupStarterKitRegistry =
			(GroupStarterKitRegistry)request.getAttribute(
				SiteWebKeys.GROUP_STARTER_KIT_REGISTRY);

		String creationStepName = ParamUtil.getString(
			liferayPortletRequest, "creationStepName");

		GroupCreationStep groupCreationStep =
			_groupCreationStepRegistry.getGroupCreationStep(creationStepName);

		if (groupCreationStep == null) {
			List<GroupCreationStep> groupCreationSteps =
				_groupCreationStepRegistry.getGroupCreationSteps(
					request, response);

			groupCreationStep = groupCreationSteps.get(0);
		}

		_groupCreationStep = groupCreationStep;
	}

	public int getChildSitesCount(Group group) {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return GroupLocalServiceUtil.getGroupsCount(
			themeDisplay.getCompanyId(), group.getGroupId(), true);
	}

	public String getCreationStepRedirect() throws Exception {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String groupCreationStepName = getNextCreationStepName();

		if (Validator.isNull(groupCreationStepName)) {
			portletURL.setParameter("jspPage", "/summary.jsp");

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
			portletURL.setParameter("jspPage", "/site_creation_wizard.jsp");

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
		return _groupCreationStep.getLabel(_request);
	}

	public String getCurrentCreationStepName() {
		return _groupCreationStep.getName();
	}

	public PortletURL getCurrentURL() {
		PortletURL currentURL = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		currentURL.setParameter("displayStyle", getDisplayStyle());

		return currentURL;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			SiteAdminPortletKeys.SITE_ADMIN, "display-style", "list");

		return _displayStyle;
	}

	public Group getGroup() throws PortalException {
		long groupId = getGroupId();

		if (groupId > 0) {
			_group = GroupServiceUtil.getGroup(groupId);
		}

		return _group;
	}

	public List<GroupCreationStep> getGroupCreationSteps() throws Exception {
		return _groupCreationStepRegistry.getGroupCreationSteps(
			_request, _response);
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
		return _groupStarterKitRegistry.getGroupStarterKits(
			_group.getCompanyId(), true);
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

	public String getKeywords() {
		if (_keywords == null) {
			_keywords = ParamUtil.getString(_request, "keywords");
		}

		return _keywords;
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

	public List<NavigationItem> getNavigationItems() {
		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem entriesNavigationItem = new NavigationItem();

		entriesNavigationItem.setActive(true);

		PortletURL mainURL = _liferayPortletResponse.createRenderURL();

		entriesNavigationItem.setHref(mainURL.toString());

		entriesNavigationItem.setLabel(LanguageUtil.get(_request, "sites"));

		navigationItems.add(entriesNavigationItem);

		return navigationItems;
	}

	public int getOrganizationsCount() throws PortalException {
		return getOrganizationsCount(getGroup());
	}

	public int getOrganizationsCount(Group group) {
		LinkedHashMap<String, Object> organizationParams =
			new LinkedHashMap<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		organizationParams.put("groupOrganization", group.getGroupId());
		organizationParams.put("organizationsGroups", group.getGroupId());

		return OrganizationLocalServiceUtil.searchCount(
			company.getCompanyId(),
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null, null, null,
			null, organizationParams);
	}

	public int getPendingRequestsCount() throws PortalException {
		return getPendingRequestsCount(getGroup());
	}

	public int getPendingRequestsCount(Group group) throws PortalException {
		int pendingRequests = 0;

		if (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED) {
			pendingRequests = MembershipRequestLocalServiceUtil.searchCount(
				group.getGroupId(), MembershipRequestConstants.STATUS_PENDING);
		}

		return pendingRequests;
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("groupId", String.valueOf(getGroupId()));
		portletURL.setParameter("displayStyle", getDisplayStyle());

		return portletURL;
	}

	public String getPreviousCreationStepName() throws Exception {
		GroupCreationStep groupCreationStep =
			_groupCreationStepRegistry.getPreviousGroupCreationStep(
				_groupCreationStep.getName(), _request, _response);

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

	public GroupSearch getSearchContainer() throws PortalException {
		return _groupSearchProvider.getGroupSearch(
			_liferayPortletRequest, getPortletURL());
	}

	public PortletURL getSearchURL() throws PortalException {
		PortletURL searchURL = _liferayPortletResponse.createRenderURL();

		searchURL.setParameter("groupId", String.valueOf(getGroupId()));
		searchURL.setParameter("displayStyle", getDisplayStyle());

		return searchURL;
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

	public int getUserGroupsCount(Group group) {
		LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		userGroupParams.put(
			UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_GROUPS,
			group.getGroupId());

		return UserGroupLocalServiceUtil.searchCount(
			company.getCompanyId(), null, userGroupParams);
	}

	public int getUsersCount(Group group) {
		LinkedHashMap<String, Object> userParams = new LinkedHashMap<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		userParams.put("inherit", Boolean.TRUE);
		userParams.put("usersGroups", group.getGroupId());

		return UserLocalServiceUtil.searchCount(
			company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED,
			userParams);
	}

	public boolean hasAddChildSitePermission(Group group)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!group.isCompany() &&
			(PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.ADD_COMMUNITY) ||
			 GroupPermissionUtil.contains(
				 permissionChecker, group, ActionKeys.ADD_COMMUNITY))) {

			return true;
		}

		return false;
	}

	public boolean hasDeleteGroupPermission(Group group)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!group.isCompany() &&
			GroupPermissionUtil.contains(
				permissionChecker, group, ActionKeys.DELETE) &&
			!PortalUtil.isSystemGroup(group.getGroupKey())) {

			return true;
		}

		return false;
	}

	public boolean hasEditAssignmentsPermission(
			Group group, boolean organizationUser, boolean userGroupUser)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		if (!group.isCompany() &&
			!(organizationUser || userGroupUser) &&
			((group.getType() == GroupConstants.TYPE_SITE_OPEN) ||
				(group.getType() == GroupConstants.TYPE_SITE_RESTRICTED)) &&
			GroupLocalServiceUtil.hasUserGroup(
				user.getUserId(), group.getGroupId()) &&
			!SiteMembershipPolicyUtil.isMembershipRequired(
				user.getUserId(), group.getGroupId())) {

			return true;
		}

		return false;
	}

	public boolean isLastGroupCreationStep() throws Exception {
		String creationType = ParamUtil.getString(_request, "creationType");

		if (!creationType.equals(
				SiteAdminConstants.CREATION_TYPE_STARTER_KIT)) {

			return false;
		}

		if (Validator.isNotNull(getNextCreationStepName())) {
			return false;
		}

		return true;
	}

	public void renderCurrentCreationStep() throws Exception {
		_groupCreationStep.render(_request, _response);
	}

	protected String getNextCreationStepName() throws Exception {
		GroupCreationStep groupCreationStep =
			_groupCreationStepRegistry.getNextGroupCreationStep(
				_groupCreationStep.getName(), _request, _response);

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

	private String _displayStyle;
	private Group _group;
	private final GroupCreationStep _groupCreationStep;
	private final GroupCreationStepRegistry _groupCreationStepRegistry;
	private long _groupId;
	private final GroupSearchProvider _groupSearchProvider;
	private final GroupStarterKitRegistry _groupStarterKitRegistry;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;
	private final HttpServletResponse _response;

}