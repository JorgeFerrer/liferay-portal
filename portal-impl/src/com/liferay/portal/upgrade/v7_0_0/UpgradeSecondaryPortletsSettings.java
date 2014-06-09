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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.DLSettings;
import com.liferay.portlet.documentlibrary.util.DLConstants;

import java.sql.SQLException;

import javax.portlet.ReadOnlyException;

/**
 * @author Sergio González
 * @author Iván Zaera
 */
public class UpgradeSecondaryPortletsSettings
	extends BaseUpgradePortletSettings {

	public UpgradeSecondaryPortletsSettings() {
		registerUpgradeablePortlet(
			PortletKeys.DOCUMENT_LIBRARY_DISPLAY, DLConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			DLPortletInstanceSettings.ALL_KEYS, DLSettings.ALL_KEYS);

		registerUpgradeablePortlet(
			PortletKeys.MEDIA_GALLERY_DISPLAY, DLConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			DLPortletInstanceSettings.ALL_KEYS, DLSettings.ALL_KEYS);

		registerUpgradeablePortlet(
			PortletKeys.WIKI_DISPLAY, DLConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			DLPortletInstanceSettings.ALL_KEYS, DLSettings.ALL_KEYS);
	}

	protected void upgradePortlet(String portletId) throws PortalException {
		int ownerType = ownerTypes.get(portletId);

		try {
			deletePortletPreferencesKeys(
				portletId, ownerType, serviceKeys.get(portletId));
		}
		catch (ReadOnlyException roe) {
			throw new PortalException(
				"Unable to upgrade portlet " + portletId, roe);
		}
		catch (SQLException sqle) {
			throw new PortalException(
				"Unable to upgrade portlet " + portletId, sqle);
		}
	}

}