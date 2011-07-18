/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.sites.util.SitesUtil;

import java.util.Collection;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletPermissionImpl implements PortletPermission {

	public static final boolean DEFAULT_STRICT = false;

	public void check(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId)
		throws PortalException, SystemException {

		check(
			permissionChecker, groupId, plid, portletId, actionId,
			DEFAULT_STRICT);
	}

	public void check(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, groupId, plid, portletId, actionId,
				strict)) {

			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId)
		throws PortalException, SystemException {

		check(permissionChecker, plid, portletId, actionId, DEFAULT_STRICT);
	}

	public void check(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId, boolean strict)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, plid, portletId, actionId, strict)) {
			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, String portletId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, portletId, actionId)) {
			throw new PrincipalException();
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			Collection<Portlet> portlets, String actionId)
		throws PortalException, SystemException {

		for (Portlet portlet : portlets) {
			if (contains(
					permissionChecker, groupId, 0, portlet.getPortletId(),
					ActionKeys.ACCESS_IN_CONTROL_PANEL, true)) {

				return true;
			}
		}

		return false;
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			Portlet portlet, String actionId)
		throws PortalException, SystemException {

		return contains(
			permissionChecker, groupId, plid, portlet, actionId,
			DEFAULT_STRICT);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			Portlet portlet, String actionId, boolean strict)
		throws PortalException, SystemException {

		if (portlet.isUndeployedPortlet()) {
			return false;
		}

		boolean value = contains(
			permissionChecker, groupId, plid, portlet.getPortletId(), actionId,
			strict);

		if (value) {
			return true;
		}
		else {
			if (portlet.isSystem() && actionId.equals(ActionKeys.VIEW)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId)
		throws PortalException, SystemException {

		return contains(
			permissionChecker, groupId, plid, portletId, actionId,
			DEFAULT_STRICT);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException {

		String name = null;
		String primKey = null;

		if (plid > 0) {
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			Group group = layout.getGroup();

			name = PortletConstants.getRootPortletId(portletId);
			primKey = getPrimaryKey(plid, portletId);

			if (!group.isLayoutSetPrototype() &&
				SitesUtil.isLayoutLocked(layout) &&
				actionId.equals(ActionKeys.CONFIGURATION)) {

				return false;
			}

			Boolean hasPermission = StagingPermissionUtil.hasPermission(
				permissionChecker, group.getGroupId(), name, group.getGroupId(),
				name, actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}

			if ((layout.isPrivateLayout() &&
				 !PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE) ||
				(layout.isPublicLayout() &&
				 !PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE)) {

				if (actionId.equals(ActionKeys.CONFIGURATION) &&
					group.isUser()) {

					return false;
				}
			}

			if (actionId.equals(ActionKeys.VIEW) && group.isControlPanel()) {
				return true;
			}

			if (!strict) {
				if (LayoutPermissionUtil.contains(
						permissionChecker, group.getGroupId(),
						layout.isPrivateLayout(), layout.getLayoutId(),
						ActionKeys.UPDATE) &&
					hasLayoutManagerPermission(portletId, actionId)) {

					return true;
				}
			}
		}
		else {
			name = portletId;
			primKey = portletId;
		}

		return permissionChecker.hasPermission(
			groupId, name, primKey, actionId);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long plid, Portlet portlet,
			String actionId)
		throws PortalException, SystemException {

		return contains(
			permissionChecker, plid, portlet, actionId, DEFAULT_STRICT);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long plid, Portlet portlet,
			String actionId, boolean strict)
		throws PortalException, SystemException {

		return contains(
			permissionChecker, 0, plid, portlet, actionId, strict);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId)
		throws PortalException, SystemException {

		return contains(
			permissionChecker, plid, portletId, actionId, DEFAULT_STRICT);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId, boolean strict)
		throws PortalException, SystemException {

		return contains(
			permissionChecker, 0, plid, portletId, actionId, strict);
	}

	public boolean contains(
			PermissionChecker permissionChecker, String portletId,
			String actionId)
		throws PortalException, SystemException {

		return contains(permissionChecker, 0, portletId, actionId);
	}

	public String getPrimaryKey(long plid, String portletId) {
		return String.valueOf(plid).concat(
			PortletConstants.LAYOUT_SEPARATOR).concat(portletId);
	}

	public boolean hasLayoutManagerPermission(
		String portletId, String actionId) {

		try {
			return hasLayoutManagerPermissionImpl(portletId, actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	protected boolean hasLayoutManagerPermissionImpl(
		String portletId, String actionId) {

		portletId = PortletConstants.getRootPortletId(portletId);

		List<String> layoutManagerActions =
			ResourceActionsUtil.getPortletResourceLayoutManagerActions(
				portletId);

		return layoutManagerActions.contains(actionId);
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletPermissionImpl.class);

}