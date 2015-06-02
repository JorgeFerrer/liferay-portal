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

package com.liferay.portlet.dynamicdatalists.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;

/**
 * @author Marcellus Tavares
 */
public class DDLRecordPermission {

	public static void check(
			PermissionChecker permissionChecker, DDLRecord record,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, record, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker.getUserId(), DDLRecord.class.getName(),
				record.getRecordId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long recordId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, recordId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker.getUserId(), DDLRecord.class.getName(),
				recordId, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DDLRecord record,
			String actionId)
		throws PortalException {

		DDLRecordSet recordSet = record.getRecordSet();

		return DDLRecordSetPermission.contains(
			permissionChecker, recordSet, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long recordId, String actionId)
		throws PortalException {

		DDLRecord record = DDLRecordLocalServiceUtil.getRecord(recordId);

		return contains(permissionChecker, record, actionId);
	}

}