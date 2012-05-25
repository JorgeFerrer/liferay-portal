/**
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
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.util.PortletKeys;

import java.util.Map;

/**
 * @author kamesh
 * 
 */
public abstract class PortalSettingsLARHandler {

    public static final String _NAMESPACE = "portal-settings";

    public static final String _EXPORT_COMPANY_DETAILS = "company-details";

    public static final PortletDataHandlerBoolean _export_company_details =
            new PortletDataHandlerBoolean(_NAMESPACE,
                    _EXPORT_COMPANY_DETAILS, true);

    public static final String _EXPORT_COMPANY_PREFERENCES =
            "export-company-preferences";

    public static final PortletDataHandlerBoolean _export_company_preferences =
            new PortletDataHandlerBoolean(_NAMESPACE,
                    _EXPORT_COMPANY_PREFERENCES, true);

    public static final String _EXPORT_LOGO = "logo";

    public static final PortletDataHandlerBoolean _export_logo =
            new PortletDataHandlerBoolean(_NAMESPACE, _EXPORT_LOGO, true);

    public static final PortletDataHandlerControl[] _PORTAL_SETTINGS_CONTROLS =
            new PortletDataHandlerControl[] { _export_company_details,
                    _export_company_preferences, _export_logo };

    protected Account
            getAccount(
                    PortletDataContext portletDataContext,
                    Element companyElement) {

        Account account = null;
        Element accountElement = companyElement.element("account");
        if (accountElement != null) {
            String accountPath = accountElement.attributeValue("path");
            if (accountPath != null) {
                account =
                        (Account) portletDataContext
                                .getZipEntryAsObject(accountPath);
            }

        }
        return account;

    }

    protected void clearCaches() {

        if (_log.isDebugEnabled()) {
            _log.debug("Clearing the DB cache");
        }

        CacheRegistryUtil.clear();

        if (_log.isDebugEnabled()) {
            _log.debug("Clearing the clustered VM cache");
        }

        MultiVMPoolUtil.clear();

        if (_log.isDebugEnabled()) {
            _log.debug("Clearing the Single VM cache");
        }

        WebCachePoolUtil.clear();
    }

    protected String getCompanyPath(
            PortletDataContext portletDataContext, Company company) {

        StringBundler sb = new StringBundler(4);
        sb.append(portletDataContext
                .getPortletPath(PortletKeys.PORTAL_SETTINGS));
        sb.append("/companies/");
        sb.append(company.getCompanyId());
        sb.append(".xml");

        return sb.toString();
    }

    protected String getCompanyAccountPath(
            PortletDataContext portletDataContext, Company company) {

        StringBundler sb = new StringBundler(4);
        sb.append(portletDataContext
                .getPortletPath(PortletKeys.PORTAL_SETTINGS));
        sb.append("/companies/");
        sb.append("Account_");
        sb.append(company.getAccountId());
        sb.append(".xml");

        return sb.toString();
    }

    protected String getCompanyLogoPath(
            PortletDataContext portletDataContext, Company company) {

        StringBundler sb = new StringBundler(4);
        sb.append(portletDataContext
                .getPortletPath(PortletKeys.PORTAL_SETTINGS));
        sb.append("/companies/");
        sb.append("logo_");
        sb.append(company.getLogoId());
        sb.append(StringPool.SLASH);

        return sb.toString();
    }

    protected String getQualifiedKey(String key) {

        if (_log.isDebugEnabled()) {
            _log.debug("Building Qualified Key for '" + key + "' with NS '"
                    + _NAMESPACE + "'");
        }

        StringBundler qualifiedKey = new StringBundler("_");
        qualifiedKey.append(_NAMESPACE);
        qualifiedKey.append("_");
        qualifiedKey.append(key);

        return qualifiedKey.toString();
    }

    protected boolean isChecked(
            Map<String, String[]> parameterMap,
            String key,
            PortletDataHandlerBoolean booleanControl) {

        key = getQualifiedKey(key);

        if (_log.isDebugEnabled()) {
            _log.debug("Key[" + key + "]");
        }

        return MapUtil.getBoolean(parameterMap, key,
                booleanControl.getDefaultState());
    }

    private static Log _log = LogFactoryUtil
            .getLog(PortalSettingsExportLARHandler.class);
}
