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
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.PortletPreferences;
public class BaseUpgradePortletSettings extends UpgradeProcess {

	protected void copyPortletPreferencesToService(
			String portletId, int ownerType, String serviceName)
		throws Exception, SystemException {

		List<PortletPreferencesRow> portletPreferencesRows =
			getPortletPreferencesRows(portletId, ownerType);

		for (
			PortletPreferencesRow portletPreferencesRow :
				portletPreferencesRows) {

			portletPreferencesRow.portletPreferencesId = increment();
			portletPreferencesRow.ownerType =
				PortletKeys.PREFS_OWNER_TYPE_GROUP;
			portletPreferencesRow.portletId = serviceName;

			if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
				long groupId = getGroupIdFromPlid(portletPreferencesRow.plid);

				portletPreferencesRow.ownerId = groupId;
				portletPreferencesRow.plid = 0;
			}

			createPortletPreferences(portletPreferencesRow);
		}
	}

	protected void createPortletPreferences(
			PortletPreferencesRow portletPreferencesRow)
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

			ps.setLong(1, portletPreferencesRow.portletPreferencesId);
			ps.setLong(2, portletPreferencesRow.ownerId);
			ps.setInt(3, portletPreferencesRow.ownerType);
			ps.setLong(4, portletPreferencesRow.plid);
			ps.setString(5, portletPreferencesRow.portletId);
			ps.setString(6, portletPreferencesRow.preferences);
			ps.setLong(7, portletPreferencesRow.mvccVersion);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void deletePortletPreferencesKeys(
			String portletId, int ownerType, String[] preferencesKeys)
		throws Exception {

		List<PortletPreferencesRow> portletPreferencesRows =
			getPortletPreferencesRows(portletId, ownerType);

		for (
			PortletPreferencesRow portletPreferencesRow :
				portletPreferencesRows) {

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.fromDefaultXML(
					portletPreferencesRow.preferences);

			Enumeration<String> names = portletPreferences.getNames();

			List<String> keysToReset = new ArrayList<String>();

			while (names.hasMoreElements()) {
				String name = names.nextElement();

				for (String preferencesKey : preferencesKeys) {
					if (name.startsWith(preferencesKey)) {
						keysToReset.add(name);

						break;
					}
				}
			}

			for (String keyToReset : keysToReset) {
				portletPreferences.reset(keyToReset);
			}

			portletPreferencesRow.preferences =
				PortletPreferencesFactoryUtil.toXML(portletPreferences);

			updatePortletPreferences(portletPreferencesRow);
		}
	}

	protected long getGroupIdFromPlid(long plid) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("select groupId from Layout where plid = ?");

			String sql = sb.toString();

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

	protected List<PortletPreferencesRow> getPortletPreferencesRows(
			String portletId, int ownerType)
		throws Exception {

		List<PortletPreferencesRow> portletPreferencesRows =
			new ArrayList<PortletPreferencesRow>();

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
				PortletPreferencesRow portletPreferencesRow =
					new PortletPreferencesRow();

				portletPreferencesRow.portletPreferencesId = rs.getLong(
					"portletPreferencesId");
				portletPreferencesRow.ownerId = rs.getLong("ownerId");
				portletPreferencesRow.ownerType = rs.getInt("ownerType");
				portletPreferencesRow.plid = rs.getLong("plid");
				portletPreferencesRow.portletId = rs.getString("portletId");
				portletPreferencesRow.preferences = GetterUtil.getString(
					rs.getString("preferences"));

				portletPreferencesRows.add(portletPreferencesRow);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return portletPreferencesRows;
	}

	protected void updatePortletPreferences(
			PortletPreferencesRow portletPreferencesRow)
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

			ps.setLong(1, portletPreferencesRow.ownerId);
			ps.setInt(2, portletPreferencesRow.ownerType);
			ps.setLong(3, portletPreferencesRow.plid);
			ps.setString(4, portletPreferencesRow.portletId);
			ps.setString(5, portletPreferencesRow.preferences);
			ps.setLong(6, portletPreferencesRow.mvccVersion);
			ps.setLong(7, portletPreferencesRow.portletPreferencesId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected static class PortletPreferencesRow {

		public long portletPreferencesId;
		public long ownerId;
		public int ownerType;
		public long plid;
		public String portletId;
		public String preferences;
		public long mvccVersion;
	}

}