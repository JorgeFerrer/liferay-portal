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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.BlogsPortletInstanceSettings;
import com.liferay.portlet.blogs.BlogsSettings;
import com.liferay.portlet.blogs.util.BlogsConstants;
import com.liferay.portlet.bookmarks.BookmarksSettings;
import com.liferay.portlet.bookmarks.util.BookmarksConstants;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
import com.liferay.portlet.documentlibrary.DLSettings;
import com.liferay.portlet.documentlibrary.util.DLConstants;
import com.liferay.portlet.messageboards.MBSettings;
import com.liferay.portlet.messageboards.util.MBConstants;
import com.liferay.portlet.shopping.ShoppingSettings;
import com.liferay.portlet.shopping.util.ShoppingConstants;
import com.liferay.portlet.wiki.WikiPortletInstanceSettings;
import com.liferay.portlet.wiki.WikiSettings;
import com.liferay.portlet.wiki.util.WikiConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergio González
 * @author Iván Zaera
 */
public class UpgradeMasterPortletsSettings extends BaseUpgradePortletSettings {

	public UpgradeMasterPortletsSettings() {
		registerPortlet(
			PortletKeys.BLOGS, BlogsConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP,
			BlogsPortletInstanceSettings.ALL_KEYS, BlogsSettings.ALL_KEYS);

		registerPortlet(
			PortletKeys.BOOKMARKS, BookmarksConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, StringPool.EMPTY_ARRAY,
			BookmarksSettings.ALL_KEYS);

		registerPortlet(
			PortletKeys.DOCUMENT_LIBRARY, DLConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP,
			DLPortletInstanceSettings.ALL_KEYS, DLSettings.ALL_KEYS);

		registerPortlet(
			PortletKeys.MESSAGE_BOARDS, MBConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP, StringPool.EMPTY_ARRAY,
			MBSettings.ALL_KEYS);

		registerPortlet(
			PortletKeys.SHOPPING, ShoppingConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP, StringPool.EMPTY_ARRAY,
			ShoppingSettings.ALL_KEYS);

		registerPortlet(
			PortletKeys.WIKI, WikiConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			WikiPortletInstanceSettings.ALL_KEYS, WikiSettings.ALL_KEYS);
	}

	@Override
	protected void doUpgrade() throws Exception {
		for (String portletId : _ownerTypes.keySet()) {
			upgradePortlet(portletId);
		}
	}

	protected void registerPortlet(
		String portletId, String serviceName, int ownerType,
		String[] portletInstancePreferencesKeys,
		String[] servicePreferencesKeys) {

		_serviceNames.put(portletId, serviceName);

		_ownerTypes.put(portletId, ownerType);

		_portletInstancePreferencesKeys.put(
			portletId, portletInstancePreferencesKeys);

		_servicePreferencesKeys.put(portletId, servicePreferencesKeys);
	}

	protected void upgradePortlet(String portletId) throws Exception {
		String serviceName = _serviceNames.get(portletId);

		int ownerType = _ownerTypes.get(portletId);

		copyPortletPreferencesToService(portletId, ownerType, serviceName);

		deletePortletPreferencesKeys(
			serviceName, PortletKeys.PREFS_OWNER_TYPE_GROUP,
			_portletInstancePreferencesKeys.get(portletId));

		deletePortletPreferencesKeys(
			portletId, ownerType, _servicePreferencesKeys.get(portletId));
	}

	private Map<String, Integer> _ownerTypes = new HashMap<String, Integer>();
	private Map<String, String[]> _portletInstancePreferencesKeys =
		new HashMap<String, String[]>();
	private Map<String, String> _serviceNames = new HashMap<String, String>();
	private Map<String, String[]> _servicePreferencesKeys =
		new HashMap<String, String[]>();

}