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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.AccountLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.CompanyServiceUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kamesh
 * 
 */
public class PortlalSettingsImportLARHandler extends PortalSettingsLARHandler {

    public void importSettings(
            PortletDataContext portletDataContext,
            Map<String, String[]> parameterMap,
            Element portalPreferencesElement, Element companyElement)
            throws Exception {

        if (companyElement != null) {

            String path = companyElement.attributeValue("path");

            Company importedCompany =
                    (Company) portletDataContext
                            .getZipEntryAsObject(path);

            boolean importCompanyDetails =
                    isChecked(parameterMap, _EXPORT_COMPANY_DETAILS,
                            _export_company_details);

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
                        isChecked(parameterMap,
                                _EXPORT_LOGO,
                                _export_logo);

                // Update Logo
                if (importLogo) {
                    _updateLogo(portletDataContext, companyElement,
                            companyId);
                }

                boolean importPreferences =
                        isChecked(parameterMap,
                                _EXPORT_COMPANY_PREFERENCES,
                                _export_company_preferences);

                if (importPreferences) {
                    // Import other preferences
                    _importPreferences(portalPreferencesElement, companyId);
                    clearCaches();
                }

            }
        }
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

    private void _updateDetails(
            PortletDataContext portletDataContext, Element companyElement,
            Company company)
            throws PortalException, SystemException {

        long companyId = company.getCompanyId();

        Element userElement = companyElement.element("user");

        // update the company account
        Account account =
                getAccount(portletDataContext, companyElement);

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

                User existingUser =
                        UserLocalServiceUtil.getUser(defaultUser.getUserId());

                // create the user if not exist
                if (existingUser == null) {
                    defaultUser =
                            UserLocalServiceUtil.updateUser(existingUser);
                }

                // attach the user the company
                defaultUser.setCompanyId(companyId);

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
            .getLog(PortlalSettingsImportLARHandler.class);
}
