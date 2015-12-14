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

package com.liferay.configuration.admin.web.portlet.action;

import com.liferay.configuration.admin.ExtendedMetaTypeService;
import com.liferay.configuration.admin.web.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.constants.ConfigurationAdminWebKeys;
import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.configuration.admin.web.search.FieldNames;
import com.liferay.configuration.admin.web.util.ConfigurationHelper;
import com.liferay.configuration.admin.web.util.ConfigurationModelIterator;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.CONFIGURATION_ADMIN,
		"mvc.command.name=search"
	},
	service = MVCActionCommand.class
)
public class SearchMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		Indexer indexer = _indexerRegistry.nullSafeGetIndexer(
			ConfigurationModel.class);

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(false);

		searchContext.setCompanyId(CompanyConstants.SYSTEM);

		String keywords = actionRequest.getParameter("keywords");

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(true);
		queryConfig.setScoreEnabled(true);

		try {
			Hits hits = indexer.search(searchContext);

			Document[] documents = hits.getDocs();

			ConfigurationHelper configurationHelper = new ConfigurationHelper(
				_bundleContext, _configurationAdmin, _extendedMetaTypeService,
				null);

			List<ConfigurationModel> configurationModels = new ArrayList<>(
				documents.length);

			for (Document document : documents) {
				String configurationModelId = document.get(
					FieldNames.CONFIGURATION_MODEL_ID);

				ConfigurationModel configurationModel =
					configurationHelper.getConfigurationModel(
						configurationModelId);

				configurationModels.add(configurationModel);
			}

			ConfigurationModelIterator configurationModelIterator =
				new ConfigurationModelIterator(configurationModels);

			actionRequest.setAttribute(
				ConfigurationAdminWebKeys.CONFIGURATION_CATEGORIES,
				configurationModelIterator);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;
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

	private BundleContext _bundleContext;
	private volatile ConfigurationAdmin _configurationAdmin;
	private volatile ExtendedMetaTypeService _extendedMetaTypeService;
	private volatile IndexerRegistry _indexerRegistry;

}