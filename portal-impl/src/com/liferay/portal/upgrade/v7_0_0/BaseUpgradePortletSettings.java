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
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Iván Zaera
 */
public class BaseUpgradePortletSettings extends UpgradeProcess {

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

	protected static class PortletPreferences {

		public long portletPreferencesId;
		public long ownerId;
		public int ownerType;
		public long plid;
		public String portletId;
		public String preferences;
		public long mvccVersion;
	}

}