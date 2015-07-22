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

package com.liferay.blogs.settings.internal;

import com.liferay.blogs.configuration.BlogsGroupServiceConfiguration;
import com.liferay.portal.kernel.settings.definition.SettingsIdMapping;
import com.liferay.portlet.blogs.util.BlogsConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component
public class BlogsGroupServiceSettingsIdMapping implements SettingsIdMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return BlogsGroupServiceConfiguration.class;
	}

	@Override
	public String getSettingsId() {
		return BlogsConstants.SERVICE_NAME;
	}

}