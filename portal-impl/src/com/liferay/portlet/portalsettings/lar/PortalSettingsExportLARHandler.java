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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author kamesh
 * 
 */
public class PortalSettingsExportLARHandler extends PortalSettingsLARHandler {

    public String exportPortalSettings(
            PortletDataContext portletDataContext,
            Company company, PortletPreferences companyPreferences)
            throws Exception {

        Map<String, String[]> parameterMap =
                portletDataContext.getParameterMap();

        if (_log.isDebugEnabled()) {
            _log.debug("Parameter map:" + parameterMap);
        }

        Document document = SAXReaderUtil.createDocument();

        Element rootElement = document.addElement("portal-settings");

        Element companyElement =
                _exportCompanyDetails(portletDataContext, company, rootElement);

        boolean exportCompanyDetails =
                isChecked(parameterMap, _EXPORT_COMPANY_DETAILS,
                        _export_company_details);

        if (_log.isDebugEnabled()) {
            _log.debug("exportCompanyDetails?" + exportCompanyDetails);
        }

        // this includes account, addresess, email, websites etc.,
        if (exportCompanyDetails) {

            _exportAccount(portletDataContext, company, companyElement);

            _exportCompanyDefaultUser(portletDataContext, company,
                    companyElement);
        }

        boolean exportLogo = isChecked(parameterMap, _EXPORT_LOGO,
                _export_logo);

        if (_log.isDebugEnabled()) {
            _log.debug("exportLogo?" + exportLogo);
        }

        if (exportLogo) {

            _exportLogo(portletDataContext, company, companyElement);

        }

        boolean exportCompanyPrefrences =
                isChecked(parameterMap, _EXPORT_COMPANY_PREFERENCES,
                        _export_company_preferences);

        if (_log.isDebugEnabled()) {
            _log.debug("exportCompanyPreferences?" + exportCompanyPrefrences);
        }

        if (exportCompanyPrefrences) {
            _exportPreferences(companyPreferences, rootElement);
        }

        return document.compactString();
    }

    private void _exportAccount(
            PortletDataContext portletDataContext, Company company,
            Element rootElement)
            throws PortalException, SystemException {

        Account account = company.getAccount();

        String acPath =
                getCompanyAccountPath(portletDataContext, company);

        Element companyAccountElement = rootElement.addElement("account");

        portletDataContext.addClassedModel(companyAccountElement, acPath,
                account,
                _NAMESPACE);
    }

    private Element _exportCompanyDetails(
            PortletDataContext portletDataContext, Company company,
            Element rootElement)
            throws PortalException, SystemException {

        Element companyElement = rootElement.addElement("company");

        String path = getCompanyPath(portletDataContext, company);

        portletDataContext.addClassedModel(companyElement, path, company,
                _NAMESPACE);
        return companyElement;
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
        sb.append("/companies/");
        sb.append("User_");
        sb.append(defaultUser.getUserId());
        sb.append(".xml");

        String defaultUserPath = sb.toString();

        portletDataContext.addClassedModel(userElement, defaultUserPath,
                defaultUser,
                _NAMESPACE);
    }

    private void _exportLogo(
            PortletDataContext portletDataContext, Company company,
            Element companyElement)
            throws SystemException {

        Element logoElement = companyElement.addElement("logo");

        long logoId = company.getLogoId();

        logoElement.addAttribute("logoId", String.valueOf(logoId));

        String logoPath =
                getCompanyLogoPath(portletDataContext, company);
        logoElement.addAttribute("path", logoPath);

        Image image = ImageLocalServiceUtil.getCompanyLogo(logoId);

        byte[] imgBytes = image.getTextObj();

        portletDataContext.addZipEntry(logoPath, imgBytes);
    }

    private void _exportPreferences(
            PortletPreferences companyPreferences, Element rootElement) {

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

    private static Log _log = LogFactoryUtil
            .getLog(PortalSettingsExportLARHandler.class);

}
