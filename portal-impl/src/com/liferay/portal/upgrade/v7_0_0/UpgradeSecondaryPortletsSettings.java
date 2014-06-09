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

import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.DLSettings;
import com.liferay.portlet.wiki.WikiSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergio González
 * @author Iván Zaera
 */
public class UpgradeSecondaryPortletsSettings
	extends BaseUpgradePortletSettings {

	public UpgradeSecondaryPortletsSettings() {
		registerUpgradeablePortlet(
			PortletKeys.DOCUMENT_LIBRARY_DISPLAY,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, DLSettings.ALL_KEYS);

		registerUpgradeablePortlet(
			PortletKeys.MEDIA_GALLERY_DISPLAY,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, DLSettings.ALL_KEYS);

		registerUpgradeablePortlet(
			PortletKeys.WIKI_DISPLAY, PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			WikiSettings.ALL_KEYS);
	}

	@Override
	protected void doUpgrade() throws Exception {
		for (String portletId : _ownerTypes.keySet()) {
			upgradePortlet(portletId);
		}
	}

	protected void registerUpgradeablePortlet(
		String portletId, int ownerType, String[] servicePreferencesKeys) {

		_ownerTypes.put(portletId, ownerType);

		_servicePreferencesKeys.put(portletId, servicePreferencesKeys);
	}

	protected void upgradePortlet(String portletId) throws Exception {
		int ownerType = _ownerTypes.get(portletId);

		deletePortletPreferencesKeys(
			portletId, ownerType, _servicePreferencesKeys.get(portletId));
	}

	private Map<String, Integer> _ownerTypes = new HashMap<String, Integer>();
	private Map<String, String[]> _servicePreferencesKeys =
		new HashMap<String, String[]>();

}