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

package com.liferay.layout.rest.model.impl;

import com.liferay.apio.architect.language.Language;
import com.liferay.layout.rest.model.WebPage;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Pavel Savinov
 */
public class EmbeddedWebPage implements WebPage {

	public EmbeddedWebPage(Layout layout) {
		_layout = layout;
	}

	@Override
	public String getBreadcrumb(Language language) {
		List<Layout> ancestors = null;

		Locale locale = language.getPreferredLocale();

		try {
			ancestors = _layout.getAncestors();
		}
		catch (Exception e) {
			_log.error("Unable to get layout ancestors", e);

			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

		if (_layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(locale, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(locale, "public-pages"));
		}

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);

		Collections.reverse(ancestors);

		for (Layout ancestor : ancestors) {
			sb.append(HtmlUtil.escape(ancestor.getName(locale)));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		sb.append(HtmlUtil.escape(_layout.getName(locale)));

		return sb.toString();
	}

	@Override
	public Date getCreateDate() {
		return _layout.getCreateDate();
	}

	@Override
	public long getCreatorId() {
		return _layout.getUserId();
	}

	@Override
	public String getDescription(Language language) {
		return _layout.getDescription(language.getPreferredLocale());
	}

	public String getEmbeddedURL() {
		return _layout.getTypeSettingsProperty("embeddedLayoutURL");
	}

	@Override
	public String getFriendlyURL(Language language) {
		return _layout.getFriendlyURL(language.getPreferredLocale());
	}

	@Override
	public String getImageURL() {
		Optional<ServiceContext> serviceContextOptional = Optional.ofNullable(
			ServiceContextThreadLocal.getServiceContext());

		ServiceContext serviceContext = serviceContextOptional.orElse(
			new ServiceContext());

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if ((_layout == null) || (themeDisplay == null) ||
			!_layout.isIconImage()) {

			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getPathImage());
		sb.append("/layout_icon?img_id");
		sb.append(_layout.getIconImageId());
		sb.append("&t=");
		sb.append(WebServerServletTokenUtil.getToken(_layout.getIconImageId()));

		return sb.toString();
	}

	@Override
	public String getKeywords(Language language) {
		return _layout.getKeywords(language.getPreferredLocale());
	}

	@Override
	public Date getModifiedDate() {
		return _layout.getModifiedDate();
	}

	@Override
	public String getName(Language language) {
		return _layout.getName(language.getPreferredLocale());
	}

	@Override
	public Date getPublishedDate() {
		return _layout.getLastPublishDate();
	}

	@Override
	public String getTitle(Language language) {
		return _layout.getTitle(language.getPreferredLocale());
	}

	@Override
	public long getWebPageId() {
		return _layout.getPlid();
	}

	@Override
	public long getWebSiteId() {
		return _layout.getGroupId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmbeddedWebPage.class);

	private Layout _layout;

}