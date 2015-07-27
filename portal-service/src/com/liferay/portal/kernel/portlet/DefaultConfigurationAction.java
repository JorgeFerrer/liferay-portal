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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.settings.ConfigurationProperties;
import com.liferay.portal.kernel.settings.PortletPreferencesSettings;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;

/**
 * @author Iván Zaera
 */
public class DefaultConfigurationAction
	extends BaseJSPSettingsConfigurationAction
	implements ConfigurationAction, ResourceServingConfigurationAction {

	public DefaultConfigurationAction() {
		setParameterNamePrefix("preferences--");
	}

	@Override
	protected ConfigurationProperties getSettings(ActionRequest actionRequest) {
		return new PortletPreferencesSettings(actionRequest.getPreferences());
	}

	@Override
	protected void postProcess(
			long companyId, PortletRequest portletRequest,
			ConfigurationProperties configurationProperties)
		throws PortalException {

		PortletPreferencesSettings portletPreferencesSettings =
			(PortletPreferencesSettings)configurationProperties;

		postProcess(
			companyId, portletRequest,
			portletPreferencesSettings.getPortletPreferences());
	}

	@SuppressWarnings("unused")
	protected void postProcess(
			long companyId, PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws PortalException {
	}

	protected void removeDefaultValue(
		PortletRequest portletRequest, PortletPreferences portletPreferences,
		String key, String defaultValue) {

		String value = getParameter(portletRequest, key);

		if (defaultValue.equals(value) ||
			StringUtil.equalsIgnoreBreakLine(defaultValue, value)) {

			try {
				portletPreferences.reset(key);
			}
			catch (ReadOnlyException roe) {
				throw new SystemException(roe);
			}
		}
	}

	@Override
	protected void updateMultiValuedKeys(ActionRequest actionRequest) {

		// Legacy configuration actions that are not based on Settings must
		// ignore this method to avoid failures due to multi valued keys not
		// registering with SettingsConfigurationAction

	}

}