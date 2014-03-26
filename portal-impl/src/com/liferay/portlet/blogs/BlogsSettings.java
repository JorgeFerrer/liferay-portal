/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.settings.BaseServiceSettings;
import com.liferay.portal.settings.FallbackKeys;
import com.liferay.portal.settings.LocalizedValue;
import com.liferay.portal.settings.Settings;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.util.BlogsUtil;

/**
 * @author Iván Zaera
 */
public class BlogsSettings extends BaseServiceSettings {

	public BlogsSettings(Settings settings) {
		super(settings, _fallbackKeys);
	}

	public String getDisplayStyle() {
		return typedSettings.getValue(
			"displayStyle", BlogsUtil.DISPLAY_STYLE_FULL_CONTENT);
	}

	public long getDisplayStyleGroupId(long defaultDisplayStyleGroupId) {
		return typedSettings.getLongValue(
			"displayStyleGroupId", defaultDisplayStyleGroupId);
	}

	public LocalizedValue getEmailEntryAddedBody() {
		return localizedSettings.getLocalizedValue("emailEntryAddedBody");
	}

	public boolean getEmailEntryAddedEnabled() {
		return typedSettings.getBooleanValue("emailEntryAddedEnabled");
	}

	public LocalizedValue getEmailEntryAddedSubject() {
		return localizedSettings.getLocalizedValue("emailEntryAddedSubject");
	}

	public LocalizedValue getEmailEntryUpdatedBody() {
		return localizedSettings.getLocalizedValue("emailEntryUpdatedBody");
	}

	public boolean getEmailEntryUpdatedEnabled() {
		return typedSettings.getBooleanValue("emailEntryUpdatedEnabled");
	}

	public LocalizedValue getEmailEntryUpdatedSubject() {
		return localizedSettings.getLocalizedValue("emailEntryUpdatedSubject");
	}

	public String getEmailFromAddress() throws SystemException {
		return typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() throws SystemException {
		return typedSettings.getValue("emailFromName");
	}

	public boolean getEnableCommentRatings() {
		return typedSettings.getBooleanValue("enableCommentRatings", true);
	}

	public boolean getEnableComments() {
		if (!PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED) {
			return false;
		}

		return typedSettings.getBooleanValue("enableComments", true);
	}

	public boolean getEnableFlags() {
		return typedSettings.getBooleanValue("enableFlags", true);
	}

	public boolean getEnableRatings() {
		return typedSettings.getBooleanValue("enableRatings", true);
	}

	public boolean getEnableRelatedAssets() {
		return typedSettings.getBooleanValue("enableRelatedAssets", true);
	}

	public boolean getEnableRSS() {
		if (!PortalUtil.isRSSFeedsEnabled()) {
			return false;
		}

		return typedSettings.getBooleanValue("enableRss");
	}

	public boolean getEnableSocialBookmarks() {
		return typedSettings.getBooleanValue("enableSocialBookmarks", true);
	}

	public int getPageDelta() {
		return typedSettings.getIntegerValue(
			"pageDelta", SearchContainer.DEFAULT_DELTA);
	}

	public int getRssDelta() {
		return typedSettings.getIntegerValue("rssDelta");
	}

	public String getRssDisplayStyle() {
		return typedSettings.getValue("rssDisplayStyle");
	}

	public String getRssFeedType() {
		return typedSettings.getValue("rssFeedType");
	}

	public String getSocialBookmarksDisplayPosition() {
		return typedSettings.getValue(
			"socialBookmarksDisplayPosition", "bottom");
	}

	public String[] getSocialBookmarksDisplayStyles() {
		return typedSettings.getValues("socialBookmarksDisplayStyle");
	}

	public String getSocialBookmarksTypes() {
		return typedSettings.getValue("socialBookmarksTypes");
	}

	private static FallbackKeys _fallbackKeys = new FallbackKeys();

	static {
		_fallbackKeys.add(
			"emailEntryAddedBody", PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_BODY);
		_fallbackKeys.add(
			"emailEntryAddedEnabled",
			PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_ENABLED);
		_fallbackKeys.add(
			"emailEntryAddedSubject",
			PropsKeys.BLOGS_EMAIL_ENTRY_ADDED_SUBJECT);
		_fallbackKeys.add(
			"emailEntryUpdatedBody", PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_BODY);
		_fallbackKeys.add(
			"emailEntryUpdatedEnabled",
			PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_ENABLED);
		_fallbackKeys.add(
			"emailEntryUpdatedSubject",
			PropsKeys.BLOGS_EMAIL_ENTRY_UPDATED_SUBJECT);
		_fallbackKeys.add(
			"emailFromAddress", PropsKeys.BLOGS_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		_fallbackKeys.add(
			"emailFromName", PropsKeys.BLOGS_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		_fallbackKeys.add(
			"rssDelta", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		_fallbackKeys.add(
			"rssDisplayStyle", PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT);
		_fallbackKeys.add("rssFeedType", PropsKeys.RSS_FEED_TYPE_DEFAULT);
		_fallbackKeys.add(
			"socialBookmarksDisplayStyle",
			PropsKeys.SOCIAL_BOOKMARK_DISPLAY_STYLES);
		_fallbackKeys.add(
			"socialBookmarksTypes", PropsKeys.SOCIAL_BOOKMARK_TYPES);
	}

}