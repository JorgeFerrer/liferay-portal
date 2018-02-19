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

package com.liferay.site.admin.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.site.admin.web.internal.constants.SiteAdminConstants;
import com.liferay.site.util.GroupStarterKit;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = SiteAdminPortletHelper.class)
public class SiteAdminPortletHelper {

	public long getGroupId(PortletRequest portletRequest) {
		return ParamUtil.getLong(portletRequest, "groupId");
	}

	public Group updateGroup(
			ActionRequest actionRequest, ServiceContext serviceContext)
		throws Exception {

		return updateGroup(actionRequest, serviceContext, true);
	}

	public Group updateGroup(
			ActionRequest actionRequest, ServiceContext serviceContext,
			boolean create)
		throws Exception {

		if (!create) {
			return null;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();

		long parentGroupId = ParamUtil.getLong(
			actionRequest, "parentGroupSearchContainerPrimaryKeys",
			GroupConstants.DEFAULT_PARENT_GROUP_ID);
		Map<Locale, String> nameMap = null;
		Map<Locale, String> descriptionMap = null;
		int type = 0;
		String friendlyURL = null;
		boolean inheritContent = false;
		boolean active = false;
		boolean manualMembership = true;

		int membershipRestriction =
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION;

		boolean actionRequestMembershipRestriction = ParamUtil.getBoolean(
			actionRequest, "membershipRestriction");

		if (actionRequestMembershipRestriction &&
			(parentGroupId != GroupConstants.DEFAULT_PARENT_GROUP_ID)) {

			membershipRestriction =
				GroupConstants.MEMBERSHIP_RESTRICTION_TO_PARENT_SITE_MEMBERS;
		}

		Group group = getGroup(actionRequest);

		if (group == null) {
			nameMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "name");
			descriptionMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "description");
			type = ParamUtil.getInteger(
				actionRequest, "type", GroupConstants.TYPE_SITE_OPEN);
			friendlyURL = ParamUtil.getString(actionRequest, "friendlyURL");
			manualMembership = ParamUtil.getBoolean(
				actionRequest, "manualMembership", true);
			inheritContent = ParamUtil.getBoolean(
				actionRequest, "inheritContent");
			active = ParamUtil.getBoolean(actionRequest, "active", true);

			group = _groupService.addGroup(
				parentGroupId, GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap,
				descriptionMap, type, manualMembership, membershipRestriction,
				friendlyURL, true, inheritContent, active, serviceContext);

			LiveUsers.joinGroup(
				themeDisplay.getCompanyId(), group.getGroupId(), userId);
		}
		else {
			nameMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "name", group.getNameMap());
			descriptionMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "description", group.getDescriptionMap());
			type = ParamUtil.getInteger(actionRequest, "type", group.getType());
			manualMembership = ParamUtil.getBoolean(
				actionRequest, "manualMembership", group.isManualMembership());
			friendlyURL = ParamUtil.getString(
				actionRequest, "friendlyURL", group.getFriendlyURL());
			inheritContent = ParamUtil.getBoolean(
				actionRequest, "inheritContent", group.getInheritContent());
			active = ParamUtil.getBoolean(
				actionRequest, "active", group.getActive());

			group = _groupService.updateGroup(
				group.getGroupId(), parentGroupId, nameMap, descriptionMap,
				type, manualMembership, membershipRestriction, friendlyURL,
				inheritContent, active, serviceContext);
		}

		String creationType = ParamUtil.getString(
			actionRequest, "creationType");
		String groupStarterKitKey = ParamUtil.getString(
			actionRequest, "groupStarterKitKey");
		long layoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutSetPrototypeId");

		if (creationType.equals(SiteAdminConstants.CREATION_TYPE_STARTER_KIT) &&
			Validator.isNotNull(groupStarterKitKey)) {

			GroupStarterKit groupStarterKit =
				_groupStarterKitRegistry.getGroupStarterKit(groupStarterKitKey);

			groupStarterKit.initialize(group.getGroupId());
		}

		if (creationType.equals(
				SiteAdminConstants.CREATION_TYPE_SITE_TEMPLATE) &&
			(layoutSetPrototypeId > 0)) {

			if (!group.isStaged() || group.isStagedRemotely()) {
				SitesUtil.updateLayoutSetPrototypesLinks(
					group, layoutSetPrototypeId, 0L, true, false);
			}
			else {
				SitesUtil.updateLayoutSetPrototypesLinks(
					group.getStagingGroup(), layoutSetPrototypeId, 0L, true,
					false);
			}
		}

		return group;
	}

	protected Group getGroup(PortletRequest portletRequest)
		throws PortalException {

		long groupId = getGroupId(portletRequest);

		if (groupId <= 0) {
			return null;
		}

		return _groupService.getGroup(groupId);
	}

	@Reference
	private GroupService _groupService;

	@Reference
	private GroupStarterKitRegistry _groupStarterKitRegistry;

}