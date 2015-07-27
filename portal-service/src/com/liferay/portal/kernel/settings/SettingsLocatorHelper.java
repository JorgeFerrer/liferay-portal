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

package com.liferay.portal.kernel.settings;

import com.liferay.portal.model.Layout;

/**
 * @author Iván Zaera
 */
public interface SettingsLocatorHelper {

	public ConfigurationProperties getCompanyPortletPreferencesSettings(
		long companyId, String settingsId,
		ConfigurationProperties parentConfigurationProperties);

	public ConfigurationProperties getConfigurationBeanSettings(
		String settingsId,
		ConfigurationProperties parentConfigurationProperties);

	public ConfigurationProperties getGroupPortletPreferencesSettings(
		long groupId, String settingsId,
		ConfigurationProperties parentConfigurationProperties);

	public ConfigurationProperties getPortalPreferencesSettings(
		long companyId, ConfigurationProperties parentConfigurationProperties);

	public ConfigurationProperties getPortalPropertiesSettings();

	public ConfigurationProperties getPortletInstancePortletPreferencesSettings(
		Layout layout, String portletId,
		ConfigurationProperties parentConfigurationProperties);

}