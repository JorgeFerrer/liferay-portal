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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.persistence.UserGroupRolePK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public abstract class BaseSiteMembershipPolicy implements SiteMembershipPolicy {

	@Override
	public void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException {

		// See LPS-57385

		if (ListUtil.isNotEmpty(addUserGroupRoles)) {
			for (final UserGroupRole userGroupRole : addUserGroupRoles) {
				ActionableDynamicQuery actionableDynamicQuery =
					ResourcePermissionLocalServiceUtil.
						getActionableDynamicQuery();

				actionableDynamicQuery.setAddCriteriaMethod(
					new ActionableDynamicQuery.AddCriteriaMethod() {

						@Override
						public void addCriteria(DynamicQuery dynamicQuery) {
							Property roleId = PropertyFactoryUtil.forName(
								"roleId");
							Property scope = PropertyFactoryUtil.forName(
								"scope");

							dynamicQuery.add(
								roleId.eq(userGroupRole.getRoleId()));
							dynamicQuery.add(
								scope.eq(
									ResourceConstants.SCOPE_GROUP_TEMPLATE));
						}

					});
				actionableDynamicQuery.setPerformActionMethod(
					new ActionableDynamicQuery.PerformActionMethod() {

						@Override
						public void performAction(Object object)
							throws PortalException {

							ResourcePermission resourcePermission =
								(ResourcePermission)object;

							long actionIds = resourcePermission.getActionIds();
							String resource = resourcePermission.getName();

							DynamicQuery dynamicQuery =
								DynamicQueryFactoryUtil.forClass(
									ResourcePermission.class);

							Property resourceName = PropertyFactoryUtil.forName(
								"name");
							Property companyId = PropertyFactoryUtil.forName(
								"companyId");
							Property primKey = PropertyFactoryUtil.forName(
								"primKey");
							Property roleId = PropertyFactoryUtil.forName(
								"roleId");
							Property scope = PropertyFactoryUtil.forName(
								"scope");

							String primKeyValue = String.valueOf(
								userGroupRole.getGroupId());

							dynamicQuery.add(
								companyId.eq(
									userGroupRole.getGroup().getCompanyId()));
							dynamicQuery.add(primKey.eq(primKeyValue));
							dynamicQuery.add(resourceName.eq(resource));
							dynamicQuery.add(
								roleId.eq(userGroupRole.getRoleId()));
							dynamicQuery.add(
								scope.eq(ResourceConstants.SCOPE_INDIVIDUAL));

							long resourcePermissionCount =
								ResourcePermissionLocalServiceUtil.
									dynamicQueryCount(dynamicQuery);

							if (resourcePermissionCount > 0) {
								return;
							}

							List<ResourceAction> resourceActions =
								ResourceActionLocalServiceUtil.
									getResourceActions(resource);

							List<String> actions = new ArrayList<>();

							for (ResourceAction resourceAction :
									resourceActions) {

								long bitwiseValue =
									resourceAction.getBitwiseValue();

								if ((actionIds & bitwiseValue) ==
										bitwiseValue) {

									actions.add(resourceAction.getActionId());
								}
							}

							Map<Long, String[]> roleIdsToActionIds =
								new HashMap<>();

							roleIdsToActionIds.put(
								userGroupRole.getRoleId(),
								ArrayUtil.toStringArray(actions));

							ResourcePermissionServiceUtil.
								setIndividualResourcePermissions(
									userGroupRole.getGroupId(),
									userGroupRole.getGroup().getCompanyId(),
									resource,
									String.valueOf(userGroupRole.getGroupId()),
									roleIdsToActionIds);
						}

					});

				actionableDynamicQuery.performActions();
			}
		}
	}

	@Override
	@SuppressWarnings("unused")
	public boolean isMembershipAllowed(long userId, long groupId)
		throws PortalException {

		try {
			checkMembership(new long[] {userId}, new long[] {groupId}, null);
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId, long groupId)
		throws PortalException {

		if (permissionChecker.isGroupOwner(groupId)) {
			return false;
		}

		Role siteAdministratorRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, groupId, siteAdministratorRole.getRoleId())) {

			return true;
		}

		Role siteOwnerRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.SITE_OWNER);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, groupId, siteOwnerRole.getRoleId())) {

			return true;
		}

		return false;
	}

	@Override
	@SuppressWarnings("unused")
	public boolean isMembershipRequired(long userId, long groupId)
		throws PortalException {

		try {
			checkMembership(new long[] {userId}, null, new long[] {groupId});
		}
		catch (Exception e) {
			return true;
		}

		return false;
	}

	@Override
	@SuppressWarnings("unused")
	public boolean isRoleAllowed(long userId, long groupId, long roleId)
		throws PortalException {

		List<UserGroupRole> userGroupRoles = new ArrayList<>();

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userId, groupId, roleId);

		UserGroupRole userGroupRole =
			UserGroupRoleLocalServiceUtil.createUserGroupRole(userGroupRolePK);

		userGroupRoles.add(userGroupRole);

		try {
			checkRoles(userGroupRoles, null);
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId, long groupId,
			long roleId)
		throws PortalException {

		if (permissionChecker.isGroupOwner(groupId)) {
			return false;
		}

		Role role = RoleLocalServiceUtil.getRole(roleId);

		String roleName = role.getName();

		if (!roleName.equals(RoleConstants.SITE_ADMINISTRATOR) &&
			!roleName.equals(RoleConstants.SITE_OWNER)) {

			return false;
		}

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, groupId, roleId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isRoleRequired(long userId, long groupId, long roleId) {
		List<UserGroupRole> userGroupRoles = new ArrayList<>();

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userId, groupId, roleId);

		UserGroupRole userGroupRole =
			UserGroupRoleLocalServiceUtil.createUserGroupRole(userGroupRolePK);

		userGroupRoles.add(userGroupRole);

		try {
			checkRoles(null, userGroupRoles);
		}
		catch (Exception e) {
			return true;
		}

		return false;
	}

	@Override
	@SuppressWarnings("unused")
	public void propagateRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException {
	}

	@Override
	public void verifyPolicy() throws PortalException {
		ActionableDynamicQuery groupActionableDynamicQuery =
			GroupLocalServiceUtil.getActionableDynamicQuery();

		groupActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName("site");

					dynamicQuery.add(property.eq(true));
				}

			});
		groupActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					Group group = (Group)object;

					verifyPolicy(group);

					ActionableDynamicQuery userGroupRoleActionableDynamicQuery =
						UserGroupRoleLocalServiceUtil.
							getActionableDynamicQuery();

					userGroupRoleActionableDynamicQuery.setGroupId(
						group.getGroupId());
					userGroupRoleActionableDynamicQuery.setPerformActionMethod(
						new ActionableDynamicQuery.PerformActionMethod() {

							@Override
							public void performAction(Object object)
								throws PortalException {

								UserGroupRole userGroupRole =
									(UserGroupRole)object;

								verifyPolicy(userGroupRole.getRole());
							}

						});

					userGroupRoleActionableDynamicQuery.performActions();
				}

			});

		groupActionableDynamicQuery.performActions();
	}

	@Override
	public void verifyPolicy(Group group) throws PortalException {
		verifyPolicy(group, null, null, null, null, null);
	}

	@Override
	public void verifyPolicy(Role role) {
	}

	@Override
	public void verifyPolicy(
		Role role, Role oldRole,
		Map<String, Serializable> oldExpandoAttributes) {
	}

}