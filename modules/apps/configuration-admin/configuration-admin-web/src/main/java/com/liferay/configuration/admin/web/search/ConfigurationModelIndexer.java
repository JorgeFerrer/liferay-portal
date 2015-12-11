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

import com.liferay.configuration.admin.ExtendedMetaTypeService;
import com.liferay.configuration.admin.web.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.configuration.admin.web.util.ConfigurationHelper;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.model.CompanyConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = Indexer.class)
public class ConfigurationModelIndexer extends BaseIndexer<ConfigurationModel> {

	@Override
	public String getClassName() {
		return ConfigurationModel.class.getName();
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(
			searchQuery, searchContext,
			FieldNames.CONFIGURATION_MODEL_ATTRIBUTE_DESCRIPTION, false);
		addSearchTerm(
			searchQuery, searchContext,
			FieldNames.CONFIGURATION_MODEL_ATTRIBUTE_NAME, false);
		addSearchTerm(
			searchQuery, searchContext, FieldNames.CONFIGURATION_MODEL_CATEGORY,
			false);
		addSearchTerm(
			searchQuery, searchContext,
			FieldNames.CONFIGURATION_MODEL_FACTORY_PID, false);
		addSearchTerm(
			searchQuery, searchContext, FieldNames.CONFIGURATION_MODEL_ID,
			false);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.DESCRIPTION, Field.ENTRY_CLASS_NAME,
			Field.TITLE, Field.UID,
			FieldNames.CONFIGURATION_MODEL_ATTRIBUTE_DESCRIPTION,
			FieldNames.CONFIGURATION_MODEL_ATTRIBUTE_NAME,
			FieldNames.CONFIGURATION_MODEL_CATEGORY,
			FieldNames.CONFIGURATION_MODEL_FACTORY_PID,
			FieldNames.CONFIGURATION_MODEL_ID);

		setFilterSearch(false);
		setPermissionAware(false);
		setSelectAllLocales(false);
		setStagingAware(false);
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;
	}

	@Override
	protected void doDelete(ConfigurationModel configurationModel)
		throws Exception {

		Document document = newDocument();

		document.addUID(
			ConfigurationAdminPortletKeys.CONFIGURATION_ADMIN,
			configurationModel.getFactoryPid());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), CompanyConstants.SYSTEM,
			document.get(Field.UID), isCommitImmediately());
	}

	@Override
	protected Document doGetDocument(ConfigurationModel configurationModel)
		throws Exception {

		Document document = newDocument();

		document.addUID(
			ConfigurationAdminPortletKeys.CONFIGURATION_ADMIN,
			configurationModel.getID());

		document.addKeyword(
			FieldNames.CONFIGURATION_MODEL_CATEGORY,
			configurationModel.getCategory());
		document.addKeyword(
			FieldNames.CONFIGURATION_MODEL_FACTORY_PID,
			configurationModel.getFactoryPid());
		document.addKeyword(
			FieldNames.CONFIGURATION_MODEL_ID, configurationModel.getID());
		document.addKeyword(Field.COMPANY_ID, CompanyConstants.SYSTEM);
		document.addKeyword(Field.ENTRY_CLASS_NAME, getClassName());

		document.addText(
			Field.DESCRIPTION, configurationModel.getDescription());
		document.addText(Field.TITLE, configurationModel.getName());

		AttributeDefinition[] requiredAttributeDefinitions =
			configurationModel.getAttributeDefinitions(
				ObjectClassDefinition.ALL);

		List<String> attributeNames = new ArrayList<>(
			requiredAttributeDefinitions.length);

		List<String> attributeDescriptions = new ArrayList<>(
			requiredAttributeDefinitions.length);

		for (AttributeDefinition attributeDefinition :
				requiredAttributeDefinitions) {

			attributeNames.add(attributeDefinition.getName());
			attributeDescriptions.add(attributeDefinition.getDescription());
		}

		document.addKeyword(
			FieldNames.CONFIGURATION_MODEL_ATTRIBUTE_NAME,
			attributeNames.toArray(new String[attributeNames.size()]));

		document.addText(
			FieldNames.CONFIGURATION_MODEL_ATTRIBUTE_DESCRIPTION,
			attributeDescriptions.toArray(
				new String[attributeDescriptions.size()]));

		return document;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		Summary summary = createSummary(
			document, Field.TITLE, Field.DESCRIPTION);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(ConfigurationModel configurationModel)
		throws Exception {

		Document document = getDocument(configurationModel);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), CompanyConstants.SYSTEM, document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		ConfigurationHelper configurationHelper = new ConfigurationHelper(
			_bundleContext, _configurationAdmin, _extendedMetaTypeService,
			null);

		List<ConfigurationModel> configurationModels =
			configurationHelper.getConfigurationModels();

		for (ConfigurationModel configurationModel : configurationModels) {
			doReindex(configurationModel);
		}
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

	private BundleContext _bundleContext;
	private volatile ConfigurationAdmin _configurationAdmin;
	private volatile ExtendedMetaTypeService _extendedMetaTypeService;

}