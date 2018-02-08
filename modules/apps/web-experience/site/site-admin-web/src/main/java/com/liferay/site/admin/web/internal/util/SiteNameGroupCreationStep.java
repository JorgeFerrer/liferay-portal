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

import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.DuplicateGroupException;
import com.liferay.portal.kernel.exception.GroupInheritContentException;
import com.liferay.portal.kernel.exception.GroupKeyException;
import com.liferay.portal.kernel.exception.GroupParentException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.admin.web.internal.constants.SiteAdminConstants;
import com.liferay.site.constants.SiteWebKeys;
import com.liferay.site.util.GroupCreationStep;
import com.liferay.site.util.GroupStarterKit;
import com.liferay.site.util.GroupStarterKitRegistry;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"group.creation.step.name=" + SiteNameGroupCreationStep.NAME,
		"group.creation.step.order:Integer=10"
	},
	service = GroupCreationStep.class
)
public class SiteNameGroupCreationStep implements GroupCreationStep {

	public static final String NAME = "site-name";

	@Override
	public String getLabel(HttpServletRequest httpServletRequest) {
		return LanguageUtil.get(httpServletRequest, getName());
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isActive(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		return true;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			updateGroup(actionRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");

				return;
			}

			if (e instanceof DuplicateGroupException ||
				e instanceof GroupInheritContentException ||
				e instanceof GroupKeyException ||
				e instanceof GroupParentException.MustNotHaveStagingParent) {

				SessionErrors.add(actionRequest, e.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "currentURL");

				actionResponse.sendRedirect(redirect);

				return;
			}

			throw e;
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		actionResponse.sendRedirect(redirect);
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/site_wizard/site_name.jsp");
	}

	protected void setSessionAttribute(
		Group group, HttpServletRequest httpServletRequest) {

		HttpSession httpSession = httpServletRequest.getSession();

		if (group != null) {
			httpSession.setAttribute(SiteWebKeys.GROUP_ID, group.getGroupId());
		}
	}

	protected Group updateGroup(ActionRequest actionRequest) throws Exception {
		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String creationType = ParamUtil.getString(
			actionRequest, "creationType");
		String groupStarterKitKey = ParamUtil.getString(
			actionRequest, "groupStarterKitKey");
		long layoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutSetPrototypeId");
		long parentGroupId = ParamUtil.getLong(
			actionRequest, "parentGroupSearchContainerPrimaryKeys",
			GroupConstants.DEFAULT_PARENT_GROUP_ID);
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Group.class.getName(), actionRequest);

		Group group = null;

		if (groupId <= 0) {
			group = _groupService.addGroup(
				parentGroupId, GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap,
				new HashMap<Locale, String>(), GroupConstants.TYPE_SITE_OPEN,
				true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
				StringPool.BLANK, true, true, serviceContext);
		}
		else {
			Group oldGroup = _groupService.getGroup(groupId);

			group = _groupService.updateGroup(
				groupId, parentGroupId, nameMap, oldGroup.getDescriptionMap(),
				oldGroup.getType(), oldGroup.getManualMembership(),
				oldGroup.getMembershipRestriction(), oldGroup.getFriendlyURL(),
				oldGroup.getInheritContent(), oldGroup.getActive(),
				serviceContext);
		}

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

		setSessionAttribute(group, httpServletRequest);

		return group;
	}

	@Reference
	private GroupService _groupService;

	@Reference
	private GroupStarterKitRegistry _groupStarterKitRegistry;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

}