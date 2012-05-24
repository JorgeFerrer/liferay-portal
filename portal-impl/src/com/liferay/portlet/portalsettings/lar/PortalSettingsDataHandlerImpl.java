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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.AccountLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.CompanyServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

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
                            .getPreferences(
                                    companyId, companyId, onwerTypeId);
            if (companyPreferences != null) {
                exportDataStructure =
                        _buildExportData(portletDataContext, company,
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

            Map<String, String[]> parameterMap =
                    portletDataContext.getParameterMap();

            document = SAXReaderUtil.read(data);

            Element rootElement = document.getRootElement();

            Element portalPreferencesElement =
                    rootElement.element("portlet-preferences");

            Element companyElement =
                    rootElement.element("company");

            if (companyElement != null) {

                String path = companyElement.attributeValue("path");

                Company importedCompany =
                        (Company) portletDataContext
                                .getZipEntryAsObject(path);

                boolean importCompanyDetails =
                        _isChecked(parameterMap, _export_company_details,
                                _EXPORT_COMPANY_DETAILS);

                if (importedCompany != null) {

                    long companyId = importedCompany.getCompanyId();

                    if (_log.isDebugEnabled()) {
                        _log.debug("Current Company Id"
                                + portletDataContext.getCompanyId());
                        _log.debug("Imported Company Id" + companyId);
                    }

                    importedCompany = CompanyLocalServiceUtil
                            .updateCompany(importedCompany, true);

                    // Update General, Indentification details
                    if (importCompanyDetails) {
                        _updateDetails(portletDataContext, companyElement,
                                importedCompany);
                    }

                    boolean importLogo =
                            _isChecked(parameterMap,
                                    _export_logo,
                                    _EXPORT_LOGO);

                    // Update Logo
                    if (importLogo) {
                        _updateLogo(portletDataContext, companyElement,
                                companyId);
                    }

                    boolean importPreferences =
                            _isChecked(parameterMap,
                                    _export_company_preferences,
                                    _EXPORT_COMPANY_PREFERENCES);

                    if (importPreferences) {
                        // Import other preferences
                        _importPreferences(portalPreferencesElement, companyId);
                        _clearCaches();
                    }

                }
            }
        } catch (Exception e) {
            _log.error("Error occured while importing data", e);
            throw new PortletDataException(e);
        }

        return null;
    }

    @Override
    public PortletDataHandlerControl[] getExportControls() {

        return new PortletDataHandlerControl[] { _ENABLE_EXPORT };
    }

    @Override
    public PortletDataHandlerControl[] getImportControls() {

        return new PortletDataHandlerControl[] { _ENABLE_EXPORT };
    }

    private String _buildExportData(PortletDataContext portletDataContext,
            Company company, PortletPreferences companyPreferences)
            throws Exception {

        Map<String, String[]> parameterMap =
                portletDataContext.getParameterMap();

        if (_log.isDebugEnabled()) {
            _log.debug("Parameter map:" + parameterMap);
        }

        Document document = SAXReaderUtil.createDocument();

        Element rootElement = document.addElement("portal-settings");

        Element companyElement = rootElement.addElement("company");

        String path = _getCompanyPath(portletDataContext, company);

        portletDataContext.addClassedModel(companyElement, path, company,
                _NAMESPACE);

        boolean exportCompanyDetails =
                _isChecked(parameterMap, _export_company_details,
                        _EXPORT_COMPANY_DETAILS);

        if (_log.isDebugEnabled()) {
            _log.debug("exportCompanyDetails?" + exportCompanyDetails);
        }

        if (exportCompanyDetails) {

            Account account = company.getAccount();

            String acPath =
                    _getCompanyAccountPath(portletDataContext, company);

            Element companyAccountElement = rootElement.addElement("company");

            portletDataContext.addClassedModel(companyAccountElement, acPath,
                    account,
                    _NAMESPACE);

            _exportCompanyDefaultUser(portletDataContext, company,
                    companyElement);

            boolean exportLogo = _isChecked(parameterMap, _export_logo,
                    _EXPORT_LOGO);

            if (_log.isDebugEnabled()) {
                _log.debug("exportLogo?" + exportLogo);
            }

            if (exportLogo) {

                Element logoElement = companyElement.addElement("logo");

                long logoId = company.getLogoId();

                logoElement.addAttribute("logoId", String.valueOf(logoId));

                String logoPath =
                        _getCompanyLogoPath(portletDataContext, company);
                logoElement.addAttribute("path", logoPath);

                Image image = ImageLocalServiceUtil.getCompanyLogo(logoId);

                byte[] imgBytes = image.getTextObj();

                portletDataContext.addZipEntry(logoPath, imgBytes);
            }
        }

        boolean exportCompanyPrefrences =
                _isChecked(parameterMap, _export_company_preferences,
                        _EXPORT_COMPANY_PREFERENCES);

        if (_log.isDebugEnabled()) {
            _log.debug("exportCompanyPrefrences?" + exportCompanyPrefrences);
        }

        if (exportCompanyPrefrences) {
            Element preferencesElement =
                    rootElement.addElement("portlet-preferences");

            Enumeration<String> names = companyPreferences.getNames();
            while (names.hasMoreElements()) {
                Element prefElement =
                        preferencesElement.addElement("preference");
                Element prefNameElement = prefElement.addElement("name");
                Element prefValueElement = prefElement.addElement("value");
                String prefName = names.nextElement();
                prefNameElement.addText(prefName);
                String prefValue = companyPreferences.getValue(
                        prefName, StringPool.BLANK);
                prefValueElement.addText(prefValue);
            }
        }

        return document.compactString();
    }

    private void _clearCaches() {

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

    private void _exportCompanyDefaultUser(
            PortletDataContext portletDataContext, Company company,
            Element companyElement)
            throws PortalException, SystemException {

        Element userElement = companyElement.addElement("user");

        User defaultUser =
                UserLocalServiceUtil.getDefaultUser(company.getCompanyId());

        StringBundler sb = new StringBundler(4);
        sb.append(portletDataContext
                .getPortletPath(PortletKeys.PORTAL_SETTINGS));
        sb.append("/companies");
        sb.append("User_");
        sb.append(defaultUser.getUserId());
        sb.append(".xml");

        String defaultUserPath = sb.toString();

        userElement.addAttribute("path", defaultUserPath);

        portletDataContext.addClassedModel(userElement, sb.toString(),
                defaultUser,
                _NAMESPACE);
    }

    private Account
            _getAccount(
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

    private String _getCompanyPath(
            PortletDataContext portletDataContext, Company company) {

        StringBundler sb = new StringBundler(4);
        sb.append(portletDataContext
                .getPortletPath(PortletKeys.PORTAL_SETTINGS));
        sb.append("/companies/");
        sb.append(company.getCompanyId());
        sb.append(".xml");

        return sb.toString();
    }

    private String _getCompanyAccountPath(
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

    private String _getCompanyLogoPath(
            PortletDataContext portletDataContext, Company company) {

        StringBundler sb = new StringBundler(4);
        sb.append(portletDataContext
                .getPortletPath(PortletKeys.PORTAL_SETTINGS));
        sb.append("/logo");
        sb.append(company.getLogoId());
        sb.append(StringPool.SLASH);

        return sb.toString();
    }

    private void _importPreferences(
            Element portalPreferencesElement, long companyId)
            throws IOException, SystemException {

        if (portalPreferencesElement != null) {
            int onwerTypeId = PortletKeys.PREFS_OWNER_TYPE_COMPANY;

            String prefsXml =
                    portalPreferencesElement.compactString();

            if (_log.isDebugEnabled()) {
                _log.debug("Owner Id" + companyId);
                _log.debug("PortalSettingsDataHandlerImpl-" + "\n" +
                        " Importing data:" + prefsXml);
            }

            PortalPreferencesLocalServiceUtil
                    .updatePreferences(companyId, onwerTypeId,
                            prefsXml);

        }
    }

    private boolean _isChecked(
            Map<String, String[]> parameterMap,
            PortletDataHandlerBoolean booleanControl, String key) {

        if (_log.isDebugEnabled()) {
            _log.debug("Key:" + key);
        }
        boolean isChecked = MapUtil.getBoolean(
                parameterMap, key,
                booleanControl.getDefaultState());

        return isChecked;

    }

    private void _updateDetails(
            PortletDataContext portletDataContext, Element companyElement,
            Company company)
            throws PortalException, SystemException {

        long companyId = company.getCompanyId();

        Element userElement = companyElement.element("user");

        // update the company account
        Account account =
                _getAccount(portletDataContext, companyElement);

        account = AccountLocalServiceUtil.updateAccount(account);

        String languageId = StringPool.BLANK;
        String timeZoneId = StringPool.BLANK;
        String virtualHostname = StringPool.BLANK;
        String mx = StringPool.BLANK;
        String homeURL = StringPool.BLANK;
        String name = StringPool.BLANK;
        String legalName = StringPool.BLANK;
        String legalId = StringPool.BLANK;
        String legalType = StringPool.BLANK;
        String sicCode = StringPool.BLANK;
        String tickerSymbol = StringPool.BLANK;
        String industry = StringPool.BLANK;
        String type = StringPool.BLANK;
        String size = StringPool.BLANK;

        List<Address> addresses = new ArrayList<Address>();

        List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();

        List<Phone> phones = new ArrayList<Phone>();

        List<Website> websites = new ArrayList<Website>();

        UnicodeProperties properties = new UnicodeProperties();

        if (userElement != null) {

            String path = userElement.attributeValue("path");

            User defaultUser =
                    (User) portletDataContext.getZipEntryAsObject(path);

            if (defaultUser != null) {

                languageId = defaultUser.getLanguageId();

                timeZoneId = defaultUser.getTimeZoneId();

                addresses =
                        defaultUser.getAddresses();

                emailAddresses =
                        defaultUser.getEmailAddresses();

                phones = defaultUser.getPhones();

                websites = defaultUser.getWebsites();
            }
        }

        if (account != null) {
            company.setAccountId(account.getAccountId());
            virtualHostname = company.getVirtualHostname();
            mx = company.getMx();
            homeURL = company.getHomeURL();
            name = company.getName();
            legalName = account.getLegalName();
            legalId = account.getLegalId();
            legalType = account.getLegalType();
            sicCode = account.getSicCode();
            tickerSymbol = account.getTickerSymbol();
            industry = account.getIndustry();
            type = account.getType();
            size = account.getSize();
        }

        CompanyServiceUtil.updateCompany(
                companyId, virtualHostname, mx, homeURL, name, legalName,
                legalId,
                legalType, sicCode, tickerSymbol, industry, type, size,
                languageId,
                timeZoneId, addresses, emailAddresses, phones, websites,
                properties);

        PortalUtil.resetCDNHosts();

    }

    private void _updateLogo(
            PortletDataContext portletDataContext, Element companyElement,
            long companyId)
            throws PortalException, SystemException {

        Element logoElement = companyElement.element("logo");
        if (logoElement != null) {
            String logoPath = logoElement.attributeValue("path");
            if (logoPath != null) {
                InputStream is =
                        portletDataContext
                                .getZipEntryAsInputStream(logoPath);
                if (is != null) {
                    CompanyLocalServiceUtil.updateLogo(companyId,
                            is);
                }
            }
        }
    }

    private static Log _log = LogFactoryUtil
            .getLog(PortalSettingsDataHandlerImpl.class);

    private static final String _NAMESPACE = "portal-settings";

    private static final String _EXPORT_COMPANY_DETAILS = "company-details";

    private static final String _EXPORT_PORTAL_SETTINGS_LAR_DATA =
            "portal-settings";

    private static PortletDataHandlerBoolean _export_company_details =
            new PortletDataHandlerBoolean(_NAMESPACE,
                    _EXPORT_COMPANY_DETAILS, true);

    private static final String _EXPORT_COMPANY_PREFERENCES =
            "export-company-preferences";

    private static PortletDataHandlerBoolean _export_company_preferences =
            new PortletDataHandlerBoolean(_NAMESPACE,
                    _EXPORT_COMPANY_PREFERENCES, true);

    private static final String _EXPORT_LOGO = "logo";

    private static final PortletDataHandlerBoolean _export_logo =
            new PortletDataHandlerBoolean(_NAMESPACE,
                    _EXPORT_LOGO, true);

    private static final PortletDataHandlerControl[] _PORTAL_SETTINGS_CONTROLS =
            new PortletDataHandlerControl[] { _export_company_details,
                    _export_company_preferences, _export_logo };

    private static final PortletDataHandlerBoolean _ENABLE_EXPORT =
            new PortletDataHandlerBoolean(_NAMESPACE,
                    _EXPORT_PORTAL_SETTINGS_LAR_DATA,
                    true, _PORTAL_SETTINGS_CONTROLS);

}