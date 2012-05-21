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

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerChoice;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Kamesh Sampath
 */
public class PortalSettingsDataHandlerImpl extends BasePortletDataHandler {

    @Override
    public PortletPreferences deleteData(
            PortletDataContext portletDataContext, String portletId,
            PortletPreferences portletPreferences)
            throws PortletDataException {
        Map<String, String[]> parameterMap =
                portletDataContext.getParameterMap();
        if (_log.isDebugEnabled()) {
            _log.debug("Deleting existing Portlet data");
        }

        String ownerType =
                MapUtil.getString(parameterMap, _OWNER_TYPE,
                        _OWNER_TYPES.getDefaultChoice());
        int onwerTypeId = _findOwneIdFromString(ownerType);
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
                PortalPreferencesLocalServiceUtil.addPortalPreferences
                        (companyId,
                                ownerId, onwerTypeId, null);
            } catch (SystemException e) {
                _log.error("Error while deleting data", e);
                throw new PortletDataException(e);

            }
        }

        return null;
    }

    @Override
    public String exportData(
            PortletDataContext portletDataContext, String portletId,
            PortletPreferences portletPreferences)
            throws PortletDataException {
        if (_log.isDebugEnabled()) {
            _log.debug("Exporting Portal Settings data");
        }

        Map<String, String[]> parameterMap =
                portletDataContext.getParameterMap();
        boolean canExportData =
                MapUtil.getBoolean(parameterMap,
                        _EXPORT_PORTAL_SETTINGS_LAR_DATA,
                        _ENABLE_EXPORT.getDefaultState());
        String exportDataStructure = "";
        String ownerType =
                MapUtil.getString(parameterMap, _OWNER_TYPE,
                        _OWNER_TYPES.getDefaultChoice());
        long companyId = portletDataContext.getCompanyId();
        int onwerTypeId = _findOwneIdFromString(ownerType);

        if (canExportData) {
            if (PortletKeys.PREFS_OWNER_TYPE_COMPANY == onwerTypeId) {
                long ownerId = companyId;
                if (_log.isDebugEnabled()) {
                    _log.debug("Owner Id" + ownerId);
                }

                try {
                    PortletPreferences companyPreferences =
                            PortalPreferencesLocalServiceUtil
                                    .getPreferences(
                                            companyId, companyId, onwerTypeId);
                    if (companyPreferences != null) {
                        exportDataStructure =
                                _buildExportData(companyId, ownerType,
                                        companyPreferences);
                    }
                } catch (SystemException e) {
                    _log.error(e);
                    throw new PortletDataException(e.getMessage());
                } catch (IOException e) {
                    _log.error(e);
                    throw new PortletDataException(e.getMessage());
                }
            }
        }

        return exportDataStructure;
    }

    @Override
    public PortletDataHandlerControl[] getExportControls() {
        return new PortletDataHandlerControl[] { _ENABLE_EXPORT };
    }

    @Override
    public PortletDataHandlerControl[] getImportControls() {
        return new PortletDataHandlerControl[] { _ENABLE_EXPORT };
    }

    @Override
    public PortletPreferences importData(
            PortletDataContext portletDataContext, String portletId,
            PortletPreferences portletPreferences, String data)
            throws PortletDataException {
        if (_log.isDebugEnabled()) {
            _log.debug("Import Portal Settings data");
        }

        Map<String, String[]> parameterMap =
                portletDataContext.getParameterMap();
        Document document = null;
        try {
            document = SAXReaderUtil.read(data);
            Element portalPreferencesElement = document.getRootElement();
            if (portalPreferencesElement != null) {
                long companyId = portletDataContext.getCompanyId();
                String ownerType =
                        MapUtil.getString(parameterMap, _OWNER_TYPE,
                                _OWNER_TYPES.getDefaultChoice());
                int onwerTypeId = _findOwneIdFromString(ownerType);

                if (_log.isDebugEnabled()) {
                    _log.debug("Onwer Type Id" + onwerTypeId);
                }

                if (PortletKeys.PREFS_OWNER_TYPE_COMPANY == onwerTypeId) {
                    long ownerId = companyId;
                    String prefsXml = portalPreferencesElement.compactString();
                    if (_log.isDebugEnabled()) {
                        _log.debug("Owner Id" + ownerId);
                        _log.debug("PortalSettingsDataHandlerImpl-" + "\n" +
                                " Importing data:" + prefsXml);
                    }

                    PortalPreferencesLocalServiceUtil.updatePreferences
                            (ownerId, onwerTypeId, prefsXml);
                    _clearDbCache();
                    _clearVMCache();
                    _clearWebCache();
                }

            }
        } catch (DocumentException e) {
            _log.error("Error occured while importing data", e);
            throw new PortletDataException(e);
        } catch (IOException e) {
            _log.error("Error occured while importing data", e);
            throw new PortletDataException(e);
        } catch (SystemException e) {
            _log.error("Error occured while importing data", e);
            throw new PortletDataException(e);
        }

        return null;
    }

    private String _buildExportData(
            long ownerId, String ownerType,
            PortletPreferences companyPreferences)
            throws IOException {
        Document document = SAXReaderUtil.createDocument();

        Element rootElement = document.addElement("portlet-preferences");

        Enumeration<String> names = companyPreferences.getNames();
        while (names.hasMoreElements()) {
            Element prefElement = rootElement.addElement("preference");
            Element prefNameElement = prefElement.addElement("name");
            Element prefValueElement = prefElement.addElement("value");
            String prefName = names.nextElement();
            prefNameElement.addText(prefName);
            String prefValue = companyPreferences.getValue(
                    prefName, StringPool.BLANK);
            prefValueElement.addText(prefValue);
        }

        return document.compactString();
    }

    private void _clearDbCache() {
        if (_log.isDebugEnabled()) {
            _log.debug("Clearing the DB cache");
        }
        CacheRegistryUtil.clear();
    }

    private void _clearVMCache() {
        if (_log.isDebugEnabled()) {
            _log.debug("Clearing the clustered VM cache");
        }
        MultiVMPoolUtil.clear();
    }

    private void _clearWebCache() {
        if (_log.isDebugEnabled()) {
            _log.debug("Clearing the Single VM cache");
        }
        WebCachePoolUtil.clear();
    }

    private int _findOwneIdFromString(String ownerType) {
        if (_OWNER_TYPE_COMPANY.equalsIgnoreCase(ownerType)) {
            return PortletKeys.PREFS_OWNER_TYPE_COMPANY;
        }

        return 0;
    }

    private static Log _log = LogFactoryUtil
            .getLog(PortalSettingsDataHandlerImpl.class);

    private static final String _NAMESPACE = "portal-settings";

    private static final String _EXPORT_PORTAL_SETTINGS_LAR_DATA =
            "portal-settings";

    private static final String _OWNER_TYPE = "owner-type";

    private static final String _OWNER_TYPE_COMPANY = "company";

    private static final PortletDataHandlerChoice _OWNER_TYPES =
            new PortletDataHandlerChoice(_NAMESPACE, _OWNER_TYPE, 0,
                    new String[] { _OWNER_TYPE_COMPANY });

    private static final PortletDataHandlerBoolean _ENABLE_EXPORT =
            new PortletDataHandlerBoolean(_NAMESPACE,
                    _EXPORT_PORTAL_SETTINGS_LAR_DATA, true,
                    new PortletDataHandlerControl[] { _OWNER_TYPES });

}