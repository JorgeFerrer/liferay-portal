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

package com.liferay.configuration.admin.web.search;

import com.liferay.configuration.admin.ExtendedMetaTypeInformation;
import com.liferay.configuration.admin.ExtendedMetaTypeService;
import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = BundleListener.class)
public class ConfigurationIndexingBundleListener implements BundleListener {

	@Override
	public void bundleChanged(BundleEvent bundleEvent) {
		if ((bundleEvent.getType() != BundleEvent.STARTED) &&
			(bundleEvent.getType() != BundleEvent.STOPPED)) {

			return;
		}

		Bundle bundle = bundleEvent.getBundle();

		List<ConfigurationModel> configurationModels = new ArrayList<>();
		_collectConfigurationModels(bundle, configurationModels, null, false);
		_collectConfigurationModels(bundle, configurationModels, null, true);

		Indexer<ConfigurationModel> indexer =
			_indexerRegistry.nullSafeGetIndexer(ConfigurationModel.class);

		try {
			indexer.reindex(configurationModels);
		}
		catch (SearchException e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to reindex models", e);
			}
		}
	}

	public Configuration getConfiguration(String pid) {
		try {
			String pidFilter = _getPidFilterString(pid, false);

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(pidFilter);

			if (configurations != null) {
				return configurations[0];
			}
		}
		catch (InvalidSyntaxException | IOException e) {
			ReflectionUtil.throwException(e);
		}

		return null;
	}

	@Reference(unbind = "-")
	protected void setConfigAdminService(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Reference(unbind = "-")
	protected void setExtendedMetaTypeService(
		ExtendedMetaTypeService extendedMetaTypeService) {

		_extendedMetaTypeService = extendedMetaTypeService;
	}

	@Reference(unbind = "-")
	protected void setIndexerRegistry(IndexerRegistry indexerRegistry) {
		_indexerRegistry = indexerRegistry;
	}

	private void _collectConfigurationModels(
		Bundle bundle, List<ConfigurationModel> configurationModels,
		String locale, boolean factory) {

		ExtendedMetaTypeInformation extendedMetaTypeInformation =
			_extendedMetaTypeService.getMetaTypeInformation(bundle);

		if (extendedMetaTypeInformation == null) {
			return;
		}

		String[] pids = null;

		if (factory) {
			pids = extendedMetaTypeInformation.getFactoryPids();
		}
		else {
			pids = extendedMetaTypeInformation.getPids();
		}

		for (String pid : pids) {
			ConfigurationModel configurationModel = _getConfigurationModel(
				bundle, pid, factory, locale);

			if (configurationModel == null) {
				continue;
			}

			configurationModels.add(configurationModel);
		}
	}

	private ConfigurationModel _getConfigurationModel(
		Bundle bundle, String pid, boolean factory, String locale) {

		ExtendedMetaTypeInformation metaTypeInformation =
			_extendedMetaTypeService.getMetaTypeInformation(bundle);

		if (metaTypeInformation == null) {
			return null;
		}

		return new ConfigurationModel(
			metaTypeInformation.getObjectClassDefinition(pid, locale),
			getConfiguration(pid), StringPool.QUESTION, factory);
	}

	private String _getPidFilterString(String pid, boolean factory) {
		StringBundler filter = new StringBundler(5);

		filter.append(StringPool.OPEN_PARENTHESIS);

		if (factory) {
			filter.append(ConfigurationAdmin.SERVICE_FACTORYPID);
		}
		else {
			filter.append(Constants.SERVICE_PID);
		}

		filter.append(StringPool.EQUAL);
		filter.append(pid);
		filter.append(StringPool.CLOSE_PARENTHESIS);

		return filter.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationIndexingBundleListener.class);

	private volatile ConfigurationAdmin _configurationAdmin;
	private volatile ExtendedMetaTypeService _extendedMetaTypeService;
	private volatile IndexerRegistry _indexerRegistry;

}