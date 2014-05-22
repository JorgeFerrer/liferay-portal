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

package com.liferay.portlet.wiki;

import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import javax.portlet.ValidatorException;

/**
 * @author Iván Zaera
 */
public class WikiSettings {

	public WikiSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public String getDisplayStyle() {
		return _typedSettings.getValue("displayStyle");
	}

	public long getDisplayStyleGroupId(long defaultDisplayStyleGroupId) {
		return _typedSettings.getLongValue(
			"displayStyleGroupId", defaultDisplayStyleGroupId);
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
	}

	public LocalizedValuesMap getEmailPageAddedBody() {
		return _typedSettings.getLocalizedValuesMap("emailPageAddedBody");
	}

	public String getEmailPageAddedBodyXml() {
		LocalizedValuesMap emailPageAddedBodyMap = getEmailPageAddedBody();

		return emailPageAddedBodyMap.getLocalizationXml();
	}

	public boolean getEmailPageAddedEnabled() {
		return _typedSettings.getBooleanValue("emailPageAddedEnabled");
	}

	public LocalizedValuesMap getEmailPageAddedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailPageAddedSubject");
	}

	public String getEmailPageAddedSubjectXml() {
		LocalizedValuesMap emailPageAddedSubjectMap =
			getEmailPageAddedSubject();

		return emailPageAddedSubjectMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailPageUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap("emailPageUpdatedBody");
	}

	public String getEmailPageUpdatedBodyXml() {
		LocalizedValuesMap emailPageUpdatedBodyMap = getEmailPageUpdatedBody();

		return emailPageUpdatedBodyMap.getLocalizationXml();
	}

	public boolean getEmailPageUpdatedEnabled() {
		return _typedSettings.getBooleanValue("emailPageUpdatedEnabled");
	}

	public LocalizedValuesMap getEmailPageUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailPageUpdatedSubject");
	}

	public String getEmailPageUpdatedSubjectXml() {
		LocalizedValuesMap emailPageUpdatedSubjectMap =
			getEmailPageUpdatedSubject();

		return emailPageUpdatedSubjectMap.getLocalizationXml();
	}

	public boolean getEnableCommentRatings() {
		return _typedSettings.getBooleanValue("enableCommentRatings");
	}

	public boolean getEnableComments() {
		return _typedSettings.getBooleanValue("enableComments");
	}

	public boolean getEnablePageRatings() {
		return _typedSettings.getBooleanValue("enablePageRatings");
	}

	public boolean getEnableRelatedAssets() {
		return _typedSettings.getBooleanValue("enableRelatedAssets");
	}

	public boolean getEnableRSS() {
		if (!PortalUtil.isRSSFeedsEnabled()) {
			return false;
		}

		return _typedSettings.getBooleanValue("enableRss");
	}

	public String[] getHiddenNodes() {
		return _typedSettings.getValues("hiddenNodes");
	}

	public int getRssDelta() {
		return _typedSettings.getIntegerValue("rssDelta");
	}

	public String getRssDisplayStyle() {
		return _typedSettings.getValue("rssDisplayStyle");
	}

	public String getRssFeedType() {
		return _typedSettings.getValue("rssFeedType");
	}

	public String[] getVisibleNodes() {
		return _typedSettings.getValues("visibleNodes");
	}

	public void setHiddenNodes(String[] hiddenNodes) {
		_typedSettings.setValues("hiddenNodes", hiddenNodes);
	}

	public void setVisibleNodes(String[] visibleNodes) {
		_typedSettings.setValues("visibleNodes", visibleNodes);
	}

	public void store() throws IOException, ValidatorException {
		_typedSettings.store();
	}

	private static FallbackKeys _fallbackKeys = new FallbackKeys();

	static {
		_fallbackKeys.add(
			"emailFromAddress", PropsKeys.WIKI_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		_fallbackKeys.add(
			"emailFromName", PropsKeys.WIKI_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		_fallbackKeys.add(
			"emailPageAddedBody", PropsKeys.WIKI_EMAIL_PAGE_ADDED_BODY);
		_fallbackKeys.add(
			"emailPageAddedEnabled", PropsKeys.WIKI_EMAIL_PAGE_ADDED_ENABLED);
		_fallbackKeys.add(
			"emailPageAddedSubject", PropsKeys.WIKI_EMAIL_PAGE_ADDED_SUBJECT);
		_fallbackKeys.add(
			"emailPageUpdatedBody", PropsKeys.WIKI_EMAIL_PAGE_UPDATED_BODY);
		_fallbackKeys.add(
			"emailPageUpdatedEnabled",
			PropsKeys.WIKI_EMAIL_PAGE_UPDATED_ENABLED);
		_fallbackKeys.add(
			"emailPageUpdatedSubject",
			PropsKeys.WIKI_EMAIL_PAGE_UPDATED_SUBJECT);
		_fallbackKeys.add(
			"enableComments", PropsKeys.WIKI_PAGE_COMMENTS_ENABLED);
		_fallbackKeys.add(
			"enableCommentRatings", PropsKeys.WIKI_COMMENT_RATINGS_ENABLED);
		_fallbackKeys.add(
			"enablePageRatings", PropsKeys.WIKI_PAGE_RATINGS_ENABLED);
		_fallbackKeys.add(
			"enableRelatedAssets", PropsKeys.WIKI_RELATED_ASSETS_ENABLED);
		_fallbackKeys.add("enableRss", PropsKeys.WIKI_RSS_ENABLED);
		_fallbackKeys.add(
			"rssDelta", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		_fallbackKeys.add(
			"rssDisplayStyle", PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT);
		_fallbackKeys.add("rssFeedType", PropsKeys.RSS_FEED_TYPE_DEFAULT);
	}

	private TypedSettings _typedSettings;

}