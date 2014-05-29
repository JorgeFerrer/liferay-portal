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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.wiki.WikiPortletInstanceSettings;
import com.liferay.portlet.wiki.WikiSettings;
import com.liferay.portlet.wiki.util.WikiConstants;

/**
 * @author Iván Zaera
 */
public class SettingsFactoryStartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) {
		initSettingsFactory();
	}

	protected void initSettingsFactory() {
		SettingsFactory settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

		settingsFactory.registerFallbackKeys(
			WikiConstants.SERVICE_NAME, WikiSettings.getFallbackKeys());

		settingsFactory.registerFallbackKeys(
			PortletKeys.WIKI, WikiPortletInstanceSettings.getFallbackKeys());

		settingsFactory.registerFallbackKeys(
			PortletKeys.WIKI_DISPLAY,
			WikiPortletInstanceSettings.getFallbackKeys());
	}

}