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

package com.liferay.portlet.messageboards;

import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.util.RSSUtil;

/**
 * @author Jorge Ferrer
 */
public class MBSettings {

	public static FallbackKeys FALLBACK_KEYS = new FallbackKeys();

	static {
		FALLBACK_KEYS.add(
			"allowAnonymousPosting",
			PropsKeys.MESSAGE_BOARDS_ANONYMOUS_POSTING_ENABLED);
		FALLBACK_KEYS.add(
			"emailFromAddress", PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		FALLBACK_KEYS.add(
			"emailFromName", PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		FALLBACK_KEYS.add(
			"emailHtmlFormat", PropsKeys.MESSAGE_BOARDS_EMAIL_HTML_FORMAT);
		FALLBACK_KEYS.add(
			"emailMessageAddedBody",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY);
		FALLBACK_KEYS.add(
			"emailMessageAddedEnabled",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_ENABLED);
		FALLBACK_KEYS.add(
			"emailMessageAddedSubject",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SUBJECT);
		FALLBACK_KEYS.add(
			"emailMessageUpdatedBody",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY);
		FALLBACK_KEYS.add(
			"emailMessageUpdatedEnabled",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_ENABLED);
		FALLBACK_KEYS.add(
			"emailMessageUpdatedSubject",
			PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SUBJECT);
		FALLBACK_KEYS.add(
			"enableFlags", PropsKeys.MESSAGE_BOARDS_FLAGS_ENABLED);
		FALLBACK_KEYS.add(
			"enableRatings", PropsKeys.MESSAGE_BOARDS_RATINGS_ENABLED);
		FALLBACK_KEYS.add("enableRss", PropsKeys.MESSAGE_BOARDS_RSS_ENABLED);
		FALLBACK_KEYS.add(
			"messageFormat", PropsKeys.MESSAGE_BOARDS_MESSAGE_FORMATS_DEFAULT);
		FALLBACK_KEYS.add(
			"priorities", PropsKeys.MESSAGE_BOARDS_THREAD_PRIORITIES);
		FALLBACK_KEYS.add("ranks", PropsKeys.MESSAGE_BOARDS_USER_RANKS);
		FALLBACK_KEYS.add(
			"recentPostsDateOffset",
			PropsKeys.MESSAGE_BOARDS_RECENT_POSTS_DATE_OFFSET);
		FALLBACK_KEYS.add(
			"rssDelta", PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA);
		FALLBACK_KEYS.add(
			"rssDisplayStyle", PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT);
		FALLBACK_KEYS.add("rssFeedType", PropsKeys.RSS_FEED_TYPE_DEFAULT);
		FALLBACK_KEYS.add(
			"subscribeByDefault",
			PropsKeys.MESSAGE_BOARDS_SUBSCRIBE_BY_DEFAULT);
	}

	public MBSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
	}

	public LocalizedValuesMap getEmailMessageAddedBody() {
		return _typedSettings.getLocalizedValuesMap("emailMessageAddedBody");
	}

	public String getEmailMessageAddedBodyXml() {
		LocalizedValuesMap emailMessageBodyMap = getEmailMessageAddedBody();

		return emailMessageBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailMessageAddedSubject() {
		return _typedSettings.getLocalizedValuesMap("emailMessageAddedSubject");
	}

	public String getEmailMessageAddedSubjectXml() {
		LocalizedValuesMap emailMessageAddedSubjectMap =
			getEmailMessageAddedSubject();

		return emailMessageAddedSubjectMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailMessageUpdatedBody() {
		return _typedSettings.getLocalizedValuesMap("emailMessageUpdatedBody");
	}

	public String getEmailMessageUpdatedBodyXml() {
		LocalizedValuesMap emailMessageUpdatedBodyMap =
			getEmailMessageUpdatedBody();

		return emailMessageUpdatedBodyMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailMessageUpdatedSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailMessageUpdatedSubject");
	}

	public String getEmailMessageUpdatedSubjectXml() {
		LocalizedValuesMap emailMessageUpdatedSubjectMap =
			getEmailMessageUpdatedSubject();

		return emailMessageUpdatedSubjectMap.getLocalizationXml();
	}

	public String getMessageFormat() {
		String messageFormat = _typedSettings.getValue("messageFormat");

		if (MBUtil.isValidMessageFormat(messageFormat)) {
			return messageFormat;
		}

		return "html";
	}

	public String[] getPriorities(String currentLanguageId) {
		return LocalizationUtil.getSettingsValues(
			_typedSettings, "priorities", currentLanguageId);
	}

	public String[] getRanks(String languageId) {
		return LocalizationUtil.getSettingsValues(
			_typedSettings, "ranks", languageId);
	}

	public String getRecentPostsDateOffset() {
		return _typedSettings.getValue("recentPostsDateOffset");
	}

	public int getRSSDelta() {
		return _typedSettings.getIntegerValue("rssDelta");
	}

	public String getRSSDisplayStyle() {
		return _typedSettings.getValue(
			"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_FULL_CONTENT);
	}

	public String getRSSFeedType() {
		return _typedSettings.getValue(
			"rssFeedType", RSSUtil.getFeedType(RSSUtil.ATOM, 1.0));
	}

	public boolean isAllowAnonymousPosting() {
		return _typedSettings.getBooleanValue("allowAnonymousPosting");
	}

	public boolean isEmailHtmlFormat() {
		return _typedSettings.getBooleanValue("emailHtmlFormat");
	}

	public boolean isEmailMessageAddedEnabled() {
		return _typedSettings.getBooleanValue("emailMessageAddedEnabled");
	}

	public boolean isEmailMessageUpdatedEnabled() {
		return _typedSettings.getBooleanValue("emailMessageUpdatedEnabled");
	}

	public boolean isEnableFlags() {
		return _typedSettings.getBooleanValue("enableFlags");
	}

	public boolean isEnableRatings() {
		return _typedSettings.getBooleanValue("enableRatings");
	}

	public boolean isEnableRSS() {
		if (!PortalUtil.isRSSFeedsEnabled()) {
			return false;
		}

		return _typedSettings.getBooleanValue("enableRss");
	}

	public boolean isSubscribeByDefault() {
		return _typedSettings.getBooleanValue("subscribeByDefault");
	}

	public boolean isThreadAsQuestionByDefault() {
		return _typedSettings.getBooleanValue("threadAsQuestionByDefault");
	}

	private TypedSettings _typedSettings;

}