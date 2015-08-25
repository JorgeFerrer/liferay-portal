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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class VerifySiteRole extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyUserGroupRoles();
	}

	protected void verifySiteRoleResourcePermissions(
			final long groupId, final long roleId)
		throws PortalException {

		// See LPS-57385

		ActionableDynamicQuery actionableDynamicQuery =
			ResourcePermissionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property permissionRoleId = PropertyFactoryUtil.forName(
						"roleId");
					Property scope = PropertyFactoryUtil.forName("scope");

					dynamicQuery.add(
						scope.eq(ResourceConstants.SCOPE_GROUP_TEMPLATE));

					dynamicQuery.add(permissionRoleId.eq(roleId));
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
					long companyId = resourcePermission.getCompanyId();
					String primKey = resourcePermission.getPrimKey();
					String resource = resourcePermission.getName();

					List<ResourceAction> resourceActions =
						ResourceActionLocalServiceUtil.getResourceActions(
							resource);

					List<String> actions = new ArrayList<>();

					for (ResourceAction resourceAction : resourceActions) {
						if (ResourcePermissionLocalServiceUtil.
								hasResourcePermission(
									companyId, resource,
									ResourceConstants.SCOPE_INDIVIDUAL,
									String.valueOf(groupId), roleId,
									resourceAction.getActionId())) {

							continue;
						}

						long bitwiseValue = resourceAction.getBitwiseValue();

						if ((actionIds & bitwiseValue) == bitwiseValue) {
							actions.add(resourceAction.getActionId());
						}
					}

					if (actions.size() == 0) {
						return;
					}

					Map<Long, String[]> roleIdsToActionIds = new HashMap<>();

					roleIdsToActionIds.put(
						roleId, ArrayUtil.toStringArray(actions));

					ResourcePermissionLocalServiceUtil.setResourcePermissions(
						companyId, resource, ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(groupId), roleIdsToActionIds);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void verifyUserGroupRoles() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			UserGroupRoleLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					UserGroupRole userGroupRole = (UserGroupRole)object;

					DynamicQuery dynamicQuery =
						DynamicQueryFactoryUtil.forClass(
							ResourcePermission.class);

					Property companyId = PropertyFactoryUtil.forName(
						"companyId");
					Property primKey = PropertyFactoryUtil.forName("primKey");
					Property roleId = PropertyFactoryUtil.forName("roleId");
					Property scope = PropertyFactoryUtil.forName("scope");

					dynamicQuery.add(
						companyId.eq(userGroupRole.getGroup().getCompanyId()));
					dynamicQuery.add(
						primKey.eq(String.valueOf(userGroupRole.getGroupId())));
					dynamicQuery.add(roleId.eq(userGroupRole.getRoleId()));
					dynamicQuery.add(
						scope.eq(ResourceConstants.SCOPE_INDIVIDUAL));

					long resourcePermissionCount =
						ResourcePermissionLocalServiceUtil.dynamicQueryCount(
							dynamicQuery);

					if (resourcePermissionCount > 0) {
						return;
					}

					verifySiteRoleResourcePermissions(
						userGroupRole.getGroupId(), userGroupRole.getRoleId());
				}

			});

		actionableDynamicQuery.performActions();
	}

}