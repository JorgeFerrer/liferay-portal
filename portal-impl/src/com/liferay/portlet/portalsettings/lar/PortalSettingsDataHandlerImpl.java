/*
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.portalsettings.lar;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;

import javax.portlet.PortletPreferences;

import static com.liferay.portlet.portalsettings.lar.PortalSettingsLARHandler.PORTAL_SETTINGS_CONTROLS;

/**
 * @author Kamesh Sampath
 */
public class PortalSettingsDataHandlerImpl extends BasePortletDataHandler {

	@Override
	public PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
			throws PortletDataException {

		if (_log.isDebugEnabled()) {
			_log.debug("Deleting existing Portlet data");
		}

		int onwerTypeId = PortletKeys.PREFS_OWNER_TYPE_COMPANY;
		long companyId = portletDataContext.getCompanyId();

		if (_log.isDebugEnabled()) {
			_log.debug("deleteData - Owner ID Type Id:" + onwerTypeId);
		}

		if (PortletKeys.PREFS_OWNER_TYPE_COMPANY == onwerTypeId) {
			long ownerId = companyId;
			if (_log.isDebugEnabled()) {
				_log.debug("deleteData - Owner ID" + ownerId);
			}

			try {
				PortalPreferencesLocalServiceUtil.updatePreferences(ownerId,
						onwerTypeId, PortletConstants.DEFAULT_PREFERENCES);
			} catch (SystemException e) {
				_log.error("Error while deleting data", e);
				throw new PortletDataException(e);

			}
		}

		return null;
	}

	@Override
	public String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
			throws PortletDataException {

		if (_log.isDebugEnabled()) {
			_log.debug("Exporting Portal Settings data");
		}

		String exportDataStructure = "";

		long companyId = portletDataContext.getCompanyId();

		int onwerTypeId = PortletKeys.PREFS_OWNER_TYPE_COMPANY;

		if (_log.isDebugEnabled()) {
			_log.debug("Owner Id" + companyId);
		}

		try {
			Company company = CompanyLocalServiceUtil.getCompany(companyId);
			PortletPreferences companyPreferences =
					PortalPreferencesLocalServiceUtil
							.getPreferences(companyId, companyId, onwerTypeId);
			if (companyPreferences != null) {
				exportDataStructure =
						_exportLARHandler.exportPortalSettings(
								portletDataContext, company,
								companyPreferences);
			}
		} catch (Exception e) {
			_log.error(e);
			throw new PortletDataException(e.getMessage());
		}

		return exportDataStructure;
	}

	@Override
	public PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
			throws PortletDataException {

		if (_log.isDebugEnabled()) {
			_log.debug("Import Portal Settings data");
		}

		Document document = null;

		try {

			document = SAXReaderUtil.read(data);

			Element rootElement = document.getRootElement();

			Element portalPreferencesElement = rootElement.element(
					PortalSettingsLARHandler.ELEMENT_PREFERENCE);

			Element companyElement =
					rootElement
							.element(PortalSettingsLARHandler.ELEMENT_COMPANY);

			_importLARHandler.importSettings(portletDataContext,
					portalPreferencesElement, companyElement);
		} catch (Exception e) {
			_log.error("Error occured while importing data", e);
			throw new PortletDataException(e);
		}

		return null;
	}

	@Override
	public PortletDataHandlerControl[] getExportControls() {

		return PORTAL_SETTINGS_CONTROLS;
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {

		return PORTAL_SETTINGS_CONTROLS;
	}

	private static Log _log = LogFactoryUtil
			.getLog(PortalSettingsDataHandlerImpl.class);

	private final PortalSettingsExportLARHandler _exportLARHandler =
			new PortalSettingsExportLARHandler();

	private final PortalSettingsImportLARHandler _importLARHandler =
			new PortalSettingsImportLARHandler();

}