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

package com.liferay.portal.staging.permission;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Ferrer
 */
@DoPrivileged
public class StagingPermissionImpl implements StagingPermission {

	@Override
	public Boolean hasPermission(
		PermissionChecker permissionChecker, Group group, String className,
		long classPK, String portletId, String actionId) {

		try {
			return doHasPermission(
				permissionChecker, group, className, classPK, portletId,
				actionId);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public Boolean hasPermission(
		PermissionChecker permissionChecker, long groupId, String className,
		long classPK, String portletId, String actionId) {

		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			return doHasPermission(
				permissionChecker, group, className, classPK, portletId,
				actionId);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	protected Boolean doHasPermission(
			PermissionChecker permissionChecker, Group group, String className,
			long classPK, String portletId, String actionId)
		throws Exception {

		if (_allowedActionIds.contains(actionId)) {
			return null;
		}

		if (!group.hasLocalOrRemoteStagingGroup()) {
			return null;
		}

		if (Validator.equals(Layout.class.getName(), className)) {
			return false;
		}

		if (Validator.isNull(portletId)) {
			return null;
		}

		if (group.isStagedPortlet(portletId)) {
			return false;
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingPermissionImpl.class);

	private static final List<String> _allowedActionIds = new ArrayList<>();

	static {
		_allowedActionIds.add(ActionKeys.ACCESS_IN_CONTROL_PANEL);
		_allowedActionIds.add(ActionKeys.ADD_TO_PAGE);
		_allowedActionIds.add(ActionKeys.CONFIGURATION);
		_allowedActionIds.add(ActionKeys.CUSTOMIZE);
		_allowedActionIds.add(ActionKeys.DELETE);
		_allowedActionIds.add(ActionKeys.VIEW);
	}

}