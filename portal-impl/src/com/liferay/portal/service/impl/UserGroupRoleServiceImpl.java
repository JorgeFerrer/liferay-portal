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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.base.UserGroupRoleServiceBaseImpl;
import com.liferay.portal.service.permission.UserGroupRolePermissionUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupRoleServiceImpl extends UserGroupRoleServiceBaseImpl {

	public void addUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		for (long roleId : roleIds) {
			UserGroupRolePermissionUtil.check(
				getPermissionChecker(), groupId, roleId);
		}

		userGroupRoleLocalService.addUserGroupRoles(userId, groupId, roleIds);
	}

	public void addUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePermissionUtil.check(
			getPermissionChecker(), groupId, roleId);

		userGroupRoleLocalService.addUserGroupRoles(userIds, groupId, roleId);
	}

	public void deleteUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		long[] filteredRoles = roleIds;

		for (long roleId : roleIds) {
			UserGroupRolePermissionUtil.check(
				getPermissionChecker(), groupId, roleId);

			Role role = roleLocalService.getRole(roleId);

			if (role.getName().equals(RoleConstants.SITE_ADMINISTRATOR) ||
			    role.getName().equals(RoleConstants.SITE_OWNER)) {

				if (!userService.isUnsetGroupUserAllowed(groupId, userId)) {
					filteredRoles = ArrayUtil.remove(filteredRoles, roleId);
				}
			}
			else if (role.getName().equals(
				RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
			         role.getName().equals(RoleConstants.ORGANIZATION_OWNER)) {

				if (!userService.isUnsetOrganizationUserAllowed(groupId, userId)) {
					filteredRoles = ArrayUtil.remove(filteredRoles, roleId);
				}
			}
		}

		if (filteredRoles.length == 0) {
			return;
		}

		userGroupRoleLocalService.deleteUserGroupRoles(
			userId, groupId, filteredRoles);
	}

	public void deleteUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePermissionUtil.check(
			getPermissionChecker(), groupId, roleId);

		Role role = roleLocalService.getRole(roleId);

		if (role.getName().equals(RoleConstants.SITE_ADMINISTRATOR) ||
			role.getName().equals(RoleConstants.SITE_OWNER)) {

			userService.filterUnsetGroupUserIds(groupId, userIds);
		}
		else if (role.getName().equals(
					RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
		         role.getName().equals(RoleConstants.ORGANIZATION_OWNER)) {

			userService.filterUnsetOrganizationUserIds(groupId, userIds);
		}

		userGroupRoleLocalService.deleteUserGroupRoles(
			userIds, groupId, roleId);
	}

}