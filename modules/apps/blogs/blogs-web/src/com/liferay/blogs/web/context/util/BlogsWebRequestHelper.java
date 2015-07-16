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

package com.liferay.blogs.web.context.util;

import com.liferay.blogs.service.util.BlogsServiceComponentProvider;
import com.liferay.blogs.settings.BlogsGroupServiceSettings;
import com.liferay.blogs.web.configuration.BlogsPortletInstanceConfiguration;
import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.blogs.util.BlogsConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class BlogsWebRequestHelper extends BaseRequestHelper {

	public BlogsWebRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public BlogsGroupServiceSettings getBlogsGroupServiceSettings() {
		try {
			if (_blogsGroupServiceSettings == null) {
				ThemeDisplay themeDisplay = getThemeDisplay();

				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				BlogsServiceComponentProvider blogsServiceComponentProvider =
					BlogsServiceComponentProvider.
						getBlogsServiceComponentProvider();

				SettingsFactory settingsFactory =
					blogsServiceComponentProvider.getSettingsFactory();

				if (Validator.isNotNull(portletDisplay.getPortletResource())) {
					_blogsGroupServiceSettings = settingsFactory.getSettings(
						BlogsGroupServiceSettings.class,
						new ParameterMapSettingsLocator(
							getRequest().getParameterMap(),
							new GroupServiceSettingsLocator(
								themeDisplay.getSiteGroupId(),
								BlogsConstants.SERVICE_NAME)));
				}
				else {
					_blogsGroupServiceSettings = settingsFactory.getSettings(
						BlogsGroupServiceSettings.class,
						new GroupServiceSettingsLocator(
							themeDisplay.getSiteGroupId(),
							BlogsConstants.SERVICE_NAME));
				}
			}

			return _blogsGroupServiceSettings;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public BlogsPortletInstanceConfiguration
		getBlogsPortletInstanceConfiguration() {

		try {
			if (_blogsPortletInstanceConfiguration == null) {
				ThemeDisplay themeDisplay = getThemeDisplay();

				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				BlogsServiceComponentProvider blogsServiceComponentProvider =
					BlogsServiceComponentProvider.
						getBlogsServiceComponentProvider();

				SettingsFactory settingsFactory =
					blogsServiceComponentProvider.getSettingsFactory();

				if (Validator.isNotNull(portletDisplay.getPortletResource())) {
					_blogsPortletInstanceConfiguration =
						settingsFactory.getSettings(
							BlogsPortletInstanceConfiguration.class,
						new ParameterMapSettingsLocator(
							getRequest().getParameterMap(),
							new PortletInstanceSettingsLocator(
								themeDisplay.getLayout(),
								portletDisplay.getPortletResource())));
				}
				else {
					_blogsPortletInstanceConfiguration =
						settingsFactory.getSettings(
							BlogsPortletInstanceConfiguration.class,
						new PortletInstanceSettingsLocator(
							themeDisplay.getLayout(), portletDisplay.getId()));
				}
			}

			return _blogsPortletInstanceConfiguration;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private BlogsGroupServiceSettings _blogsGroupServiceSettings;
	private BlogsPortletInstanceConfiguration
		_blogsPortletInstanceConfiguration;

}