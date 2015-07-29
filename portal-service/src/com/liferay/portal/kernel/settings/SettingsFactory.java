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

import java.util.List;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
public interface SettingsFactory {

	public ArchivedConfigurationProperties
		getPortletInstanceArchivedConfigurationProperties(
			long groupId, String portletId, String name)
		throws SettingsException;

	public List<ArchivedConfigurationProperties>
		getPortletInstanceArchivedConfigurationPropertiesList(
			long groupId, String portletId);

	public ConfigurationProperties getServerSettings(String settingsId);

	public <T> T getSettings(
			Class<T> clazz, ConfigurationLocator configurationLocator)
		throws SettingsException;

	public ConfigurationProperties getSettings(
			ConfigurationLocator configurationLocator)
		throws SettingsException;

	public SettingsDescriptor getSettingsDescriptor(String settingsId);

	public void registerSettingsMetadata(
		Class<?> settingsClass, Object configurationBean,
		FallbackKeys fallbackKeys);

}