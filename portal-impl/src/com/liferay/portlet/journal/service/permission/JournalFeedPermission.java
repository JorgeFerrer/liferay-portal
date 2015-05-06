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

package com.liferay.portlet.journal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;

/**
 * @author Raymond Augé
 */
@OSGiBeanProperties(
	property = {
		"model.class.name=com.liferay.portlet.journal.model.JournalFeed"
	}
)
public class JournalFeedPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, JournalFeed feed,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, feed, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker.getUserId(), JournalFeed.class.getName(),
				feed.getFeedId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long id, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, id, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker.getUserId(), JournalFeed.class.getName(),
				id, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, String feedId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, feedId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker.getUserId(), JournalFeed.class.getName(),
				feedId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, JournalFeed feed,
		String actionId) {

		String portletId = PortletProviderUtil.getPortletId(
			JournalArticle.class.getName(), PortletProvider.Action.EDIT);

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, feed.getGroupId(), JournalFeed.class.getName(),
			feed.getPrimaryKey(), portletId, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				feed.getCompanyId(), JournalFeed.class.getName(), feed.getId(),
				feed.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			feed.getGroupId(), JournalFeed.class.getName(), feed.getId(),
			actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long feedId, String actionId)
		throws PortalException {

		JournalFeed feed = JournalFeedLocalServiceUtil.getFeed(feedId);

		return contains(permissionChecker, feed, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String feedId,
			String actionId)
		throws PortalException {

		JournalFeed feed = JournalFeedLocalServiceUtil.getFeed(groupId, feedId);

		return contains(permissionChecker, feed, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

}