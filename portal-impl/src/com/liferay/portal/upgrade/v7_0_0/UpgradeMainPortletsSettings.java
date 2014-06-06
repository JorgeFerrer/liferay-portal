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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergio González
 * @author Iván Zaera
 */
public class UpgradeMainPortletsSettings extends UpgradeProcess {

	public UpgradeMainPortletsSettings() {
		registerUpgradeablePortlet(
			PortletKeys.BLOGS, BlogsConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP,
			BlogsPortletInstanceSettings.ALL_KEYS, BlogsSettings.ALL_KEYS);

		registerUpgradeablePortlet(
			PortletKeys.BOOKMARKS, BookmarksConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, StringPool.EMPTY_ARRAY,
			BookmarksSettings.ALL_KEYS);

		registerUpgradeablePortlet(
			PortletKeys.DOCUMENT_LIBRARY, DLConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP,
			DLPortletInstanceSettings.ALL_KEYS, DLSettings.ALL_KEYS);

		registerUpgradeablePortlet(
			PortletKeys.MESSAGE_BOARDS, MBConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP, StringPool.EMPTY_ARRAY,
			MBSettings.ALL_KEYS);

		registerUpgradeablePortlet(
			PortletKeys.SHOPPING, ShoppingConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_GROUP, StringPool.EMPTY_ARRAY,
			ShoppingSettings.ALL_KEYS);

		registerUpgradeablePortlet(
			PortletKeys.WIKI, WikiConstants.SERVICE_NAME,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			WikiPortletInstanceSettings.ALL_KEYS, WikiSettings.ALL_KEYS);
	}

	protected void createPortletPreferences(
			PortletPreferences portletPreferences)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(9);

			sb.append("insert into PortletPreferences (portletPreferencesId, ");
			sb.append("ownerId, ownerType, plid, portletId, preferences, ");
			sb.append("mvccVersion) values (?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, portletPreferences.portletPreferencesId);
			ps.setLong(2, portletPreferences.ownerId);
			ps.setInt(3, portletPreferences.ownerType);
			ps.setLong(4, portletPreferences.plid);
			ps.setString(5, portletPreferences.portletId);
			ps.setString(6, portletPreferences.preferences);
			ps.setLong(7, portletPreferences.mvccVersion);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void createServiceSettings(
			String portletId, int ownerType, String serviceName)
		throws Exception, SystemException {

		List<PortletPreferences> portletPreferencesList = getPortletPreferences(
			portletId, ownerType);

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			portletPreferences.portletPreferencesId = increment();
			portletPreferences.ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			portletPreferences.portletId = serviceName;

			if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
				long groupId = getGroupIdFromPlid(portletPreferences.plid);

				portletPreferences.ownerId = groupId;
				portletPreferences.plid = 0;
			}

			createPortletPreferences(portletPreferences);
		}
	}

	protected void deletePortletPreferencesKeys(
			String portletId, int ownerType, String[] keys)
		throws Exception {

		List<PortletPreferences> portletPreferencesList = getPortletPreferences(
			portletId, ownerType);

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			javax.portlet.PortletPreferences javaxPortletPreferences =
				PortletPreferencesFactoryUtil.fromDefaultXML(
					portletPreferences.preferences);

			Enumeration<String> names = javaxPortletPreferences.getNames();

			List<String> keysToReset = new ArrayList<String>();

			while (names.hasMoreElements()) {
				String name = names.nextElement();

				for (String key : keys) {
					if (name.startsWith(key)) {
						keysToReset.add(name);

						break;
					}
				}
			}

			for (String keyToReset : keysToReset) {
				javaxPortletPreferences.reset(keyToReset);
			}

			portletPreferences.preferences =
				PortletPreferencesFactoryUtil.toXML(javaxPortletPreferences);

			updatePortletPreferences(portletPreferences);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		for (String portletId : _ownerTypes.keySet()) {
			upgradePortlet(portletId);
		}
	}

	protected long getGroupIdFromPlid(long plid) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			String sql = "select groupId from Layout where plid = ?";

			ps = con.prepareStatement(sql);

			ps.setLong(1, plid);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return 0;
	}

	protected List<PortletPreferences> getPortletPreferences(
			String portletId, int ownerType)
		throws Exception {

		List<PortletPreferences> portletPreferencesList =
			new ArrayList<PortletPreferences>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("select portletPreferencesId, ownerId, ownerType, ");
			sb.append("plid, portletId, preferences from PortletPreferences ");
			sb.append("where ownerType = ? and portletId = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setInt(1, ownerType);
			ps.setString(2, portletId);

			rs = ps.executeQuery();

			while (rs.next()) {
				PortletPreferences portletPreferences =
					new PortletPreferences();

				portletPreferences.portletPreferencesId = rs.getLong(
					"portletPreferencesId");
				portletPreferences.ownerId = rs.getLong("ownerId");
				portletPreferences.ownerType = rs.getInt("ownerType");
				portletPreferences.plid = rs.getLong("plid");
				portletPreferences.portletId = rs.getString("portletId");
				portletPreferences.preferences = GetterUtil.getString(
					rs.getString("preferences"));

				portletPreferencesList.add(portletPreferences);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return portletPreferencesList;
	}

	protected void registerUpgradeablePortlet(
		String portletId, String serviceName, int ownerType,
		String[] portletInstanceKeys, String[] serviceKeys) {

		_serviceNames.put(portletId, serviceName);

		_ownerTypes.put(portletId, ownerType);

		_portletInstanceKeys.put(portletId, portletInstanceKeys);

		_serviceKeys.put(portletId, serviceKeys);
	}

	protected void updatePortletPreferences(
			PortletPreferences portletPreferences)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(9);

			sb.append(
				"update PortletPreferences set ownerId = ?, ownerType = ?, " +
				"plid = ?, portletId = ?, preferences = ?, mvccVersion = ? " +
				"where portletPreferencesId = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, portletPreferences.ownerId);
			ps.setInt(2, portletPreferences.ownerType);
			ps.setLong(3, portletPreferences.plid);
			ps.setString(4, portletPreferences.portletId);
			ps.setString(5, portletPreferences.preferences);
			ps.setLong(6, portletPreferences.mvccVersion);
			ps.setLong(7, portletPreferences.portletPreferencesId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void upgradePortlet(String portletId) throws Exception {
		String serviceName = _serviceNames.get(portletId);

		int ownerType = _ownerTypes.get(portletId);

		createServiceSettings(portletId, ownerType, serviceName);

		deletePortletPreferencesKeys(
			serviceName, PortletKeys.PREFS_OWNER_TYPE_GROUP,
			_portletInstanceKeys.get(portletId));

		deletePortletPreferencesKeys(
			portletId, ownerType, _serviceKeys.get(portletId));
	}

	protected static class PortletPreferences {

		public long portletPreferencesId;
		public long ownerId;
		public int ownerType;
		public long plid;
		public String portletId;
		public String preferences;
		public long mvccVersion;
	}

	private Map<String, Integer> _ownerTypes = new HashMap<String, Integer>();
	private Map<String, String[]> _portletInstanceKeys =
		new HashMap<String, String[]>();
	private Map<String, String[]> _serviceKeys =
		new HashMap<String, String[]>();
	private Map<String, String> _serviceNames = new HashMap<String, String>();

}