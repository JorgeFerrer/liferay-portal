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

package com.liferay.blogs.service.util;

import com.liferay.blogs.configuration.BlogsGroupServiceConfiguration;
import com.liferay.portal.kernel.settings.SettingsFactory;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true)
public class BlogsServiceComponentProvider {

	public static BlogsServiceComponentProvider
		getBlogsServiceComponentProvider() {

		return _blogsServiceComponentProvider;
	}

	@Activate
	public void activate() {
		_blogsServiceComponentProvider = this;
	}

	@Deactivate
	public void deactivate() {
		_blogsServiceComponentProvider = null;
	}

	public BlogsGroupServiceConfiguration getBlogsGroupServiceConfiguration() {
		return _blogsGroupServiceConfiguration;
	}

	public SettingsFactory getSettingsFactory() {
		return _settingsFactory;
	}

	@Reference
	public void setBlogsGroupServiceConfiguration(
		BlogsGroupServiceConfiguration blogsGroupServiceConfiguration) {

		_blogsGroupServiceConfiguration = blogsGroupServiceConfiguration;
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	protected void unsetBlogsGroupServiceConfiguration(
		BlogsGroupServiceConfiguration blogsGroupServiceConfiguration) {

		_blogsGroupServiceConfiguration = null;
	}

	private static BlogsServiceComponentProvider _blogsServiceComponentProvider;

	private BlogsGroupServiceConfiguration _blogsGroupServiceConfiguration;
	private SettingsFactory _settingsFactory;

}