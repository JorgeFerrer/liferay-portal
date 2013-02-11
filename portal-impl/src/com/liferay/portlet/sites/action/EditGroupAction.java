/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.sites.action;

import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.GroupNameException;
import com.liferay.portal.LayoutSetVirtualHostException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.RemoteExportException;
import com.liferay.portal.RemoteOptionsException;
import com.liferay.portal.RequiredGroupException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeServiceUtil;
import com.liferay.portal.service.LayoutSetServiceUtil;
import com.liferay.portal.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.service.MembershipRequestServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.sites.util.SitesUtil;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Berentey
 * @author Josef Sustacek
 */
public class EditGroupAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");
		String closeRedirect = ParamUtil.getString(
			actionRequest, "closeRedirect");

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Object[] returnValue = updateGroup(actionRequest);

				Group group = (Group)returnValue[0];
				String oldFriendlyURL = (String)returnValue[1];
				String oldStagingFriendlyURL = (String)returnValue[2];
				long newRefererPlid = (Long)returnValue[3];

				redirect = HttpUtil.setParameter(
					redirect, "doAsGroupId", group.getGroupId());
				redirect = HttpUtil.setParameter(
					redirect, "refererPlid", newRefererPlid);

				closeRedirect = updateCloseRedirect(
					closeRedirect, group, themeDisplay, oldFriendlyURL,
					oldStagingFriendlyURL);
			}
			else if (cmd.equals(Constants.DEACTIVATE) ||
					 cmd.equals(Constants.RESTORE)) {

				updateActive(actionRequest, cmd);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteGroups(actionRequest);
			}
			else if (cmd.equals(Constants.RESET_MERGE_FAIL_COUNT)) {
				resetMergeFailCountAndMerge(actionRequest);
			}

			sendRedirect(
				portletConfig, actionRequest, actionResponse, redirect,
				closeRedirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.sites_admin.error");
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException ||
					 e instanceof DuplicateGroupException ||
					 e instanceof GroupFriendlyURLException ||
					 e instanceof GroupNameException ||
					 e instanceof LayoutSetVirtualHostException ||
					 e instanceof LocaleException ||
					 e instanceof RemoteExportException ||
					 e instanceof RemoteOptionsException ||
					 e instanceof RequiredGroupException ||
					 e instanceof SystemException) {

				SessionErrors.add(actionRequest, e.getClass(), e);

				sendRedirect(
					portletConfig, actionRequest, actionResponse, redirect,
					closeRedirect);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.sites_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.sites_admin.edit_site"));
	}

	protected void deleteGroups(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] deleteGroupIds = null;

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		if (groupId > 0) {
			deleteGroupIds = new long[] {groupId};
		}
		else {
			deleteGroupIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteGroupIds"), 0L);
		}

		for (long deleteGroupId : deleteGroupIds) {
			GroupServiceUtil.deleteGroup(deleteGroupId);

			LiveUsers.deleteGroup(themeDisplay.getCompanyId(), deleteGroupId);
		}
	}

	protected long getRefererGroupId(ThemeDisplay themeDisplay)
		throws Exception {

		long refererGroupId = 0;

		try {
			Layout refererLayout = LayoutLocalServiceUtil.getLayout(
				themeDisplay.getRefererPlid());

			refererGroupId = refererLayout.getGroupId();
		}
		catch (NoSuchLayoutException nsle) {
		}

		return refererGroupId;
	}

	protected List<Role> getRoles(PortletRequest portletRequest)
		throws Exception {

		List<Role> roles = new ArrayList<Role>();

		long[] siteRolesRoleIds = StringUtil.split(
			ParamUtil.getString(portletRequest, "siteRolesRoleIds"), 0L);

		for (long siteRolesRoleId : siteRolesRoleIds) {
			if (siteRolesRoleId == 0) {
				continue;
			}

			Role role = RoleLocalServiceUtil.getRole(siteRolesRoleId);

			roles.add(role);
		}

		return roles;
	}

	protected List<Team> getTeams(PortletRequest portletRequest)
		throws Exception {

		List<Team> teams = new UniqueList<Team>();

		long[] teamsTeamIds= StringUtil.split(
			ParamUtil.getString(portletRequest, "teamsTeamIds"), 0L);

		for (long teamsTeamId : teamsTeamIds) {
			if (teamsTeamId == 0) {
				continue;
			}

			Team team = TeamLocalServiceUtil.getTeam(teamsTeamId);

			teams.add(team);
		}

		return teams;
	}

	/**
	 * Resets the counter of previously failed merges from site template and
	 * optionally performs the merge from the site template to given layout set.
	 * Couple of parameters are retrieved from <code>actionRequest</code>:
	 *
	 * <ul>
	 * <li>
	 * <code>groupId</code>: target group where the template should be merged,
	 * i.e. a group with layout set created from a site template.
	 * </li>
	 * <li>
	 * <code>privateLayoutSet</code>: whether we will be merging to private
	 * (value == <code>true</code>) or public (value == <code>false</code>)
	 * pages of given target group.
	 * </li>
	 * <li>
	 * <code>layoutSetPrototypeId</code>: is of site template, that we'll be
	 * resetting and merging from
	 * </li>
	 * <li>
	 * <code>forceMergeNow</code>: whether you want only to reset the counter
	 * (value = <code>false</code>) or also perform immediate merge of content
	 * (value = <code>true</code>).
	 * </li>
	 * </ul>
	 *
	 * @param  actionRequest portlet request used to retrieve parameters
	 * @throws Exception when errors occur during resetting of merging of site
	 *         template
	 */
	protected void resetMergeFailCountAndMerge(ActionRequest actionRequest)
		throws Exception {

		long targetGroupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayoutSet = ParamUtil.getBoolean(
			actionRequest, "privateLayoutSet");
		long layoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutSetPrototypeId");
		boolean forceMergeNow = ParamUtil.getBoolean(
			actionRequest, "forceMergeNow");

		// reset counter

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeServiceUtil.getLayoutSetPrototype(
				layoutSetPrototypeId);

		LayoutSet layoutSetPrototypeLayoutSet =
			layoutSetPrototype.getLayoutSet();

		SitesUtil.setMergeFailCount(layoutSetPrototypeLayoutSet, 0);

		LayoutSetLocalServiceUtil.updateLayoutSet(layoutSetPrototypeLayoutSet);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"'merge-fail-count' was reset for layoutSetPrototype " +
				layoutSetPrototypeId);
		}

		// force merge from template

		if (forceMergeNow) {

			LayoutSet targetGroupLayoutSet =
				LayoutSetLocalServiceUtil.getLayoutSet(
					targetGroupId, privateLayoutSet);

			Group targetGroup = GroupLocalServiceUtil.getGroup(targetGroupId);

			// enable link, if disabled

			if (!targetGroupLayoutSet.isLayoutSetPrototypeLinkEnabled()) {

				LayoutSetLocalServiceUtil.updateLayoutSetPrototypeLinkEnabled(
					targetGroupId, privateLayoutSet, true,
					layoutSetPrototype.getUuid());

				targetGroupLayoutSet =
					LayoutSetLocalServiceUtil.getLayoutSet(
						targetGroupId, privateLayoutSet);
			}

			// reset merge timestamps

			SitesUtil.resetPrototype(targetGroupLayoutSet);

			// do the merge

			SitesUtil.mergeLayoutSetPrototypeLayouts(
				targetGroup, targetGroupLayoutSet);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Site template " + layoutSetPrototypeId +
					" was merged to group " + targetGroupId );
			}
		}

		// check whether reset (and possible merge) was successful

		layoutSetPrototype =
			LayoutSetPrototypeServiceUtil.getLayoutSetPrototype(
				layoutSetPrototypeId);

		int mergeFailCountAfterMerge = SitesUtil.getMergeFailCount(
			layoutSetPrototype);

		if (mergeFailCountAfterMerge > 0) {

			SessionErrors.add(
				actionRequest, "templateMergeFailedSeeLogsForDetails");
		}
	}

	protected void updateActive(ActionRequest actionRequest, String cmd)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		if ((groupId == themeDisplay.getDoAsGroupId()) ||
			(groupId == themeDisplay.getScopeGroupId()) ||
			(groupId == getRefererGroupId(themeDisplay))) {

			throw new RequiredGroupException(
				String.valueOf(groupId), RequiredGroupException.CURRENT_GROUP);
		}

		Group group = GroupServiceUtil.getGroup(groupId);

		boolean active = false;

		if (cmd.equals(Constants.RESTORE)) {
			active = true;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Group.class.getName(), actionRequest);

		GroupServiceUtil.updateGroup(
			groupId, group.getParentGroupId(), group.getName(),
			group.getDescription(), group.getType(), group.getFriendlyURL(),
			active, serviceContext);
	}

	protected String updateCloseRedirect(
			String closeRedirect, Group group, ThemeDisplay themeDisplay,
			String oldFriendlyURL, String oldStagingFriendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(closeRedirect) || (group == null)) {
			return closeRedirect;
		}

		String oldPath = null;
		String newPath = null;

		if (Validator.isNotNull(oldFriendlyURL)) {
			oldPath = oldFriendlyURL;
			newPath = group.getFriendlyURL();

			if (closeRedirect.contains(oldPath)) {
				closeRedirect = PortalUtil.updateRedirect(
					closeRedirect, oldPath, newPath);
			}
			else {
				closeRedirect = PortalUtil.getGroupFriendlyURL(
					group, false, themeDisplay);
			}
		}

		if (Validator.isNotNull(oldStagingFriendlyURL)) {
			Group stagingGroup = group.getStagingGroup();

			if (GroupLocalServiceUtil.fetchGroup(
					stagingGroup.getGroupId()) == null) {

				oldPath = oldStagingFriendlyURL;
				newPath = group.getFriendlyURL();
			}
			else {
				oldPath = oldStagingFriendlyURL;
				newPath = stagingGroup.getFriendlyURL();
			}

			if (closeRedirect.contains(oldPath)) {
				closeRedirect = PortalUtil.updateRedirect(
					closeRedirect, oldPath, newPath);
			}
			else {
				closeRedirect = PortalUtil.getGroupFriendlyURL(
					group, false, themeDisplay);
			}
		}

		return closeRedirect;
	}

	protected Object[] updateGroup(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = PortalUtil.getUserId(actionRequest);

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		long parentGroupId = ParamUtil.getLong(
			actionRequest, "parentGroupSearchContainerPrimaryKeys",
			GroupConstants.DEFAULT_PARENT_GROUP_ID);
		String name = null;
		String description = null;
		int type = 0;
		String friendlyURL = null;
		boolean active = false;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Group.class.getName(), actionRequest);

		Group liveGroup = null;
		String oldFriendlyURL = null;
		String oldStagingFriendlyURL = null;

		if (liveGroupId <= 0) {

			// Add group

			name = ParamUtil.getString(actionRequest, "name");
			description = ParamUtil.getString(actionRequest, "description");
			type = ParamUtil.getInteger(actionRequest, "type");
			friendlyURL = ParamUtil.getString(actionRequest, "friendlyURL");
			active = ParamUtil.getBoolean(actionRequest, "active");

			liveGroup = GroupServiceUtil.addGroup(
				parentGroupId, GroupConstants.DEFAULT_LIVE_GROUP_ID, name,
				description, type, friendlyURL, true, active, serviceContext);

			LiveUsers.joinGroup(
				themeDisplay.getCompanyId(), liveGroup.getGroupId(), userId);
		}
		else {

			// Update group

			liveGroup = GroupLocalServiceUtil.getGroup(liveGroupId);

			oldFriendlyURL = liveGroup.getFriendlyURL();

			name = ParamUtil.getString(
				actionRequest, "name", liveGroup.getName());
			description = ParamUtil.getString(
				actionRequest, "description", liveGroup.getDescription());
			type = ParamUtil.getInteger(
				actionRequest, "type", liveGroup.getType());
			friendlyURL = ParamUtil.getString(
				actionRequest, "friendlyURL", liveGroup.getFriendlyURL());
			active = ParamUtil.getBoolean(
				actionRequest, "active", liveGroup.getActive());

			liveGroup = GroupServiceUtil.updateGroup(
				liveGroupId, parentGroupId, name, description, type,
				friendlyURL, active, serviceContext);

			if (type == GroupConstants.TYPE_SITE_OPEN) {
				List<MembershipRequest> membershipRequests =
					MembershipRequestLocalServiceUtil.search(
						liveGroupId, MembershipRequestConstants.STATUS_PENDING,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (MembershipRequest membershipRequest : membershipRequests) {
					MembershipRequestServiceUtil.updateStatus(
						membershipRequest.getMembershipRequestId(),
						themeDisplay.translate(
							"your-membership-has-been-approved"),
						MembershipRequestConstants.STATUS_APPROVED,
						serviceContext);

					LiveUsers.joinGroup(
						themeDisplay.getCompanyId(),
						membershipRequest.getGroupId(),
						new long[] {membershipRequest.getUserId()});
				}
			}
		}

		// Settings

		UnicodeProperties typeSettingsProperties =
			liveGroup.getTypeSettingsProperties();

		String customJspServletContextName = ParamUtil.getString(
			actionRequest, "customJspServletContextName",
			typeSettingsProperties.getProperty("customJspServletContextName"));

		typeSettingsProperties.setProperty(
			"customJspServletContextName", customJspServletContextName);

		typeSettingsProperties.setProperty(
			"defaultSiteRoleIds",
			ListUtil.toString(
				getRoles(actionRequest), Role.ROLE_ID_ACCESSOR,
				StringPool.COMMA));
		typeSettingsProperties.setProperty(
			"defaultTeamIds",
			ListUtil.toString(
				getTeams(actionRequest), Team.TEAM_ID_ACCESSOR,
				StringPool.COMMA));

		String[] analyticsTypes = PrefsPropsUtil.getStringArray(
			themeDisplay.getCompanyId(), PropsKeys.ADMIN_ANALYTICS_TYPES,
			StringPool.NEW_LINE);

		for (String analyticsType : analyticsTypes) {
			if (analyticsType.equalsIgnoreCase("google")) {
				String googleAnalyticsId = ParamUtil.getString(
					actionRequest, "googleAnalyticsId",
					typeSettingsProperties.getProperty("googleAnalyticsId"));

				typeSettingsProperties.setProperty(
					"googleAnalyticsId", googleAnalyticsId);
			}
			else {
				String analyticsScript = ParamUtil.getString(
					actionRequest, SitesUtil.ANALYTICS_PREFIX + analyticsType,
					typeSettingsProperties.getProperty(analyticsType));

				typeSettingsProperties.setProperty(
					SitesUtil.ANALYTICS_PREFIX + analyticsType,
					analyticsScript);
			}
		}

		String publicRobots = ParamUtil.getString(
			actionRequest, "publicRobots",
			liveGroup.getTypeSettingsProperty("false-robots.txt"));
		String privateRobots = ParamUtil.getString(
			actionRequest, "privateRobots",
			liveGroup.getTypeSettingsProperty("true-robots.txt"));

		typeSettingsProperties.setProperty("false-robots.txt", publicRobots);
		typeSettingsProperties.setProperty("true-robots.txt", privateRobots);

		int trashEnabled = ParamUtil.getInteger(
			actionRequest, "trashEnabled",
			GetterUtil.getInteger(
				typeSettingsProperties.getProperty("trashEnabled"),
				TrashUtil.TRASH_DEFAULT_VALUE));

		typeSettingsProperties.setProperty(
			"trashEnabled", String.valueOf(trashEnabled));

		int trashEntriesMaxAgeCompany = PrefsPropsUtil.getInteger(
			themeDisplay.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE);

		int defaultTrashEntriesMaxAgeGroup = GetterUtil.getInteger(
			typeSettingsProperties.getProperty("trashEntriesMaxAge"),
			trashEntriesMaxAgeCompany);

		int trashEntriesMaxAgeGroup = ParamUtil.getInteger(
			actionRequest, "trashEntriesMaxAge",
			defaultTrashEntriesMaxAgeGroup);

		if (trashEntriesMaxAgeGroup != trashEntriesMaxAgeCompany) {
			typeSettingsProperties.setProperty(
				"trashEntriesMaxAge", String.valueOf(trashEntriesMaxAgeGroup));
		}
		else {
			typeSettingsProperties.remove("trashEntriesMaxAge");
		}

		int contentSharingWithChildrenEnabled = ParamUtil.getInteger(
			actionRequest, "contentSharingWithChildrenEnabled",
			GetterUtil.getInteger(
				typeSettingsProperties.getProperty(
					"contentSharingWithChildrenEnabled"),
				SitesUtil.CONTENT_SHARING_WITH_CHILDREN_DEFAULT_VALUE));

		typeSettingsProperties.setProperty(
			"contentSharingWithChildrenEnabled",
			String.valueOf(contentSharingWithChildrenEnabled));

		UnicodeProperties formTypeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		typeSettingsProperties.putAll(formTypeSettingsProperties);

		// Virtual hosts

		LayoutSet publicLayoutSet = liveGroup.getPublicLayoutSet();

		String publicVirtualHost = ParamUtil.getString(
			actionRequest, "publicVirtualHost",
			publicLayoutSet.getVirtualHostname());

		LayoutSetServiceUtil.updateVirtualHost(
			liveGroup.getGroupId(), false, publicVirtualHost);

		LayoutSet privateLayoutSet = liveGroup.getPrivateLayoutSet();

		String privateVirtualHost = ParamUtil.getString(
			actionRequest, "privateVirtualHost",
			privateLayoutSet.getVirtualHostname());

		LayoutSetServiceUtil.updateVirtualHost(
			liveGroup.getGroupId(), true, privateVirtualHost);

		// Staging

		if (liveGroup.hasStagingGroup()) {
			Group stagingGroup = liveGroup.getStagingGroup();

			oldStagingFriendlyURL = stagingGroup.getFriendlyURL();

			friendlyURL = ParamUtil.getString(
				actionRequest, "stagingFriendlyURL",
				stagingGroup.getFriendlyURL());

			GroupServiceUtil.updateFriendlyURL(
				stagingGroup.getGroupId(), friendlyURL);

			LayoutSet stagingPublicLayoutSet =
				stagingGroup.getPublicLayoutSet();

			publicVirtualHost = ParamUtil.getString(
				actionRequest, "stagingPublicVirtualHost",
				stagingPublicLayoutSet.getVirtualHostname());

			LayoutSetServiceUtil.updateVirtualHost(
				stagingGroup.getGroupId(), false, publicVirtualHost);

			LayoutSet stagingPrivateLayoutSet =
				stagingGroup.getPrivateLayoutSet();

			privateVirtualHost = ParamUtil.getString(
				actionRequest, "stagingPrivateVirtualHost",
				stagingPrivateLayoutSet.getVirtualHostname());

			LayoutSetServiceUtil.updateVirtualHost(
				stagingGroup.getGroupId(), true, privateVirtualHost);
		}

		liveGroup = GroupServiceUtil.updateGroup(
			liveGroup.getGroupId(), typeSettingsProperties.toString());

		// Layout set prototypes

		if (!liveGroup.isStaged()) {
			long privateLayoutSetPrototypeId = ParamUtil.getLong(
				actionRequest, "privateLayoutSetPrototypeId");
			long publicLayoutSetPrototypeId = ParamUtil.getLong(
				actionRequest, "publicLayoutSetPrototypeId");

			boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				actionRequest, "privateLayoutSetPrototypeLinkEnabled",
				privateLayoutSet.isLayoutSetPrototypeLinkEnabled());
			boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				actionRequest, "publicLayoutSetPrototypeLinkEnabled",
				publicLayoutSet.isLayoutSetPrototypeLinkEnabled());

			if ((privateLayoutSetPrototypeId == 0) &&
				(publicLayoutSetPrototypeId == 0) &&
				!privateLayoutSetPrototypeLinkEnabled &&
				!publicLayoutSetPrototypeLinkEnabled) {

				long layoutSetPrototypeId = ParamUtil.getLong(
					actionRequest, "layoutSetPrototypeId");
				int layoutSetVisibility = ParamUtil.getInteger(
					actionRequest, "layoutSetVisibility");
				boolean layoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
					actionRequest, "layoutSetPrototypeLinkEnabled",
					(layoutSetPrototypeId > 0));

				if (layoutSetVisibility == _LAYOUT_SET_VISIBILITY_PRIVATE) {
					privateLayoutSetPrototypeId = layoutSetPrototypeId;

					privateLayoutSetPrototypeLinkEnabled =
						layoutSetPrototypeLinkEnabled;
				}
				else {
					publicLayoutSetPrototypeId = layoutSetPrototypeId;

					publicLayoutSetPrototypeLinkEnabled =
						layoutSetPrototypeLinkEnabled;
				}
			}

			SitesUtil.updateLayoutSetPrototypesLinks(
				liveGroup, publicLayoutSetPrototypeId,
				privateLayoutSetPrototypeId,
				publicLayoutSetPrototypeLinkEnabled,
				privateLayoutSetPrototypeLinkEnabled);
		}

		// Staging

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		long refererPlid = GetterUtil.getLong(
			HttpUtil.getParameter(redirect, "refererPlid", false));

		if (!privateLayoutSet.isLayoutSetPrototypeLinkActive() &&
			!publicLayoutSet.isLayoutSetPrototypeLinkActive()) {

			if ((refererPlid > 0) && liveGroup.hasStagingGroup() &&
				(themeDisplay.getScopeGroupId() != liveGroup.getGroupId())) {

				Layout firstLayout = LayoutLocalServiceUtil.fetchFirstLayout(
					liveGroup.getGroupId(), false,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

				if (firstLayout == null) {
					firstLayout = LayoutLocalServiceUtil.fetchFirstLayout(
						liveGroup.getGroupId(), true,
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
				}

				if (firstLayout != null) {
					refererPlid = firstLayout.getPlid();
				}
				else {
					refererPlid = 0;
				}
			}

			StagingUtil.updateStaging(actionRequest, liveGroup);
		}

		return new Object[] {
			liveGroup, oldFriendlyURL, oldStagingFriendlyURL, refererPlid};
	}

	private static final int _LAYOUT_SET_VISIBILITY_PRIVATE = 1;

	private static Log _log = LogFactoryUtil.getLog(EditGroupAction.class);

}