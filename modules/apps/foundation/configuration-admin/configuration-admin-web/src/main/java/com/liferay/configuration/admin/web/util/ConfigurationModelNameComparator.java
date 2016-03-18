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

package com.liferay.configuration.admin.web.util;

import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * @author Andrew Betts
 */
public class ConfigurationModelNameComparator
	implements Comparator<ConfigurationModel> {

	public ConfigurationModelNameComparator(
		String languageId,
		ResourceBundleLoaderProvider resourceBundleLoaderProvider) {

		_languageId = languageId;
		_resourceBundleLoaderProvider = resourceBundleLoaderProvider;
	}

	@Override
	public int compare(
		ConfigurationModel configurationModel1,
		ConfigurationModel configurationModel2) {

		String name1 = getConfigurationModelName(configurationModel1);
		String name2 = getConfigurationModelName(configurationModel2);

		return name1.compareTo(name2);
	}

	private String getConfigurationModelName(
		ConfigurationModel configurationModel) {

		String bundleSymbolicName = configurationModel.getBundleSymbolicName();

		ResourceBundleLoader resourceBundleLoader =
			_resourceBundleLoaderProvider.getResourceBundleLoader(
				bundleSymbolicName);

		ResourceBundle componentResourceBundle =
			resourceBundleLoader.loadResourceBundle(_languageId);

		String name = configurationModel.getName();

		if (componentResourceBundle == null) {
			return name;
		}

		return LanguageUtil.get(componentResourceBundle, name);
	}

	private final String _languageId;
	private final ResourceBundleLoaderProvider _resourceBundleLoaderProvider;

}