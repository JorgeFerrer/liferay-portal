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

package com.liferay.roles.admin.web.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class RoleAssigneesUtil {

	public static int getAssigneesCount(Role role) {
		if (isImpliedRole(role)) {
			return 0;
		}

		if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
			return getOrganizationRoleAssigneesCount(role);
		}

		if (role.getType() == RoleConstants.TYPE_SITE) {
			return getSiteRoleAssigneesCount(role);
		}

		return getRegularRoleAssigneesCount(role);
	}

	public static String getAssigneesMessage(
		HttpServletRequest request, Role role) {

		if (isImpliedRole(role)) {
			return LanguageUtil.get(request, "this-role-is-auto-assigned");
		}

		int count = getAssigneesCount(role);

		if (count == 1) {
			return LanguageUtil.get(request, "one-assignee");
		}

		return LanguageUtil.format(request, "x-assignees", count);
	}

	public static int getOrganizationRoleAssigneesCount(Role role) {
		if (isImpliedRole(role) ||
			(role.getType() != RoleConstants.TYPE_ORGANIZATION)) {

			return 0;
		}

		return UserGroupRoleLocalServiceUtil.getUserGroupRolesCount(
			role.getRoleId());
	}

	public static int getRegularRoleAssigneesCount(Role role) {
		if (isImpliedRole(role) ||
			(role.getType() != RoleConstants.TYPE_REGULAR)) {

			return 0;
		}

		return RoleLocalServiceUtil.getRegularRoleUsersGroupsCount(role);
	}

	public static int getSiteRoleAssigneesCount(Role role) {
		if (isImpliedRole(role) ||
			(role.getType() != RoleConstants.TYPE_SITE)) {

			return 0;
		}

		return RoleLocalServiceUtil.getSiteRoleUsersUserGroupsCount(role);
	}

	public static boolean isImpliedRole(Role role) {
		return ArrayUtil.contains(_IMPLIED_ROLES, role.getName());
	}

	private static final String[] _IMPLIED_ROLES = {
		RoleConstants.GUEST, RoleConstants.ORGANIZATION_USER,
		RoleConstants.OWNER, RoleConstants.SITE_MEMBER, RoleConstants.USER
	};

}