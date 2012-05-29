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
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.AddressServiceUtil;
import com.liferay.portal.service.EmailAddressServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.PhoneServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.WebsiteServiceUtil;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author kamesh
 *
 */
public class PortalSettingsExportLARHandler extends PortalSettingsLARHandler {

	public String exportPortalSettings(
			PortletDataContext portletDataContext, Company company,
			PortletPreferences companyPreferences)
			throws Exception {

		Map<String, String[]> parameterMap =
				portletDataContext.getParameterMap();

		if (_log.isDebugEnabled()) {
			_log.debug("Parameter map:" + parameterMap);
		}

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement(ELEMENT_PORTAL_SETTINGS);

		Element companyElement = _exportCompanyDetails(
				portletDataContext, company, rootElement);

		boolean exportCompanyDetails =
				isChecked(parameterMap, EXPORT_COMPANY_DETAILS,
						exportCompanyDetails);

		if (_log.isDebugEnabled()) {
			_log.debug("Export Company Details?" + exportCompanyDetails);
		}

		// this includes account, addresess, email, websites etc.,
		if (exportCompanyDetails) {

			_exportAccount(portletDataContext, company, companyElement);

			_exportCompanyDefaultUser(portletDataContext, company,
					companyElement);
		}

		boolean exportLogo = isChecked(parameterMap, EXPORT_LOGO,
				exportLogo);

		if (_log.isDebugEnabled()) {
			_log.debug("Export Logo?" + exportLogo);
		}

		if (exportLogo) {

			_exportLogo(portletDataContext, company, companyElement);

		}

		boolean exportCompanyPrefrences =
				isChecked(parameterMap, EXPORT_COMPANY_PREFERENCES,
						exportCompanyPreferences);

		if (_log.isDebugEnabled()) {
			_log.debug("Export Company Preferences ?" + exportCompanyPrefrences);
		}

		if (exportCompanyPrefrences) {
			_exportPreferences(companyPreferences, rootElement);
		}

		return document.compactString();
	}

	private void
			_exportAddresses(
					PortletDataContext portletDataContext,
					Element companyAccountElement, long accountId)
					throws PortalException, SystemException {

		List<Address> addresses = Collections.emptyList();
		if (_log.isDebugEnabled()) {
			_log.debug("Exporting Company Addresses for classPK:" + accountId);
		}

		addresses =
				AddressServiceUtil.getAddresses(Account.class.getName(),
						accountId);
		if (!addresses.isEmpty()) {
			Element addressesElement = companyAccountElement.addElement(
					ELEMENT_ADDRESSES);
			for (Address address : addresses) {
				Element addressElement = addressesElement.addElement(
						ELEMENT_ADDRESS);
				long addressId = address.getAddressId();
				String addressPath =
						getAddressPath(portletDataContext, accountId, addressId);
				portletDataContext.addClassedModel(addressElement,
						addressPath, address, NAMESPACE);
			}
		}

	}

	private void _exportAccount(
			PortletDataContext portletDataContext, Company company,
			Element rootElement)
			throws PortalException, SystemException {

		Account account = company.getAccount();

		String acPath = getCompanyAccountPath(portletDataContext, company);

		Element companyAccountElement = rootElement.addElement(ELEMENT_ACCOUNT);

		portletDataContext.addClassedModel(companyAccountElement, acPath,
				account, NAMESPACE);

		long accountId = account.getAccountId();

		_exportAddresses(portletDataContext, companyAccountElement, accountId);

		_exportEmailAddresses(portletDataContext, companyAccountElement,
				accountId);

		_exportPhones(portletDataContext, companyAccountElement, accountId);

		_exportWebsites(portletDataContext, companyAccountElement, accountId);
	}

	private Element _exportCompanyDetails(
			PortletDataContext portletDataContext, Company company,
			Element rootElement)
			throws PortalException, SystemException {

		Element companyElement = rootElement.addElement(ELEMENT_COMPANY);

		String path = getCompanyPath(portletDataContext, company);

		portletDataContext.addClassedModel(companyElement, path, company,
				NAMESPACE);
		return companyElement;
	}

	private void _exportCompanyDefaultUser(
			PortletDataContext portletDataContext, Company company,
			Element companyElement)
			throws PortalException, SystemException {

		Element userElement = companyElement.addElement(ELEMENT_USER);

		User defaultUser = UserLocalServiceUtil.getDefaultUser(
				company.getCompanyId());

		StringBundler sb = getUserPath(portletDataContext, defaultUser);

		String defaultUserPath = sb.toString();

		portletDataContext.addClassedModel(userElement, defaultUserPath,
				defaultUser, NAMESPACE);
	}

	private void _exportEmailAddresses(
			PortletDataContext portletDataContext,
			Element companyAccountElement, long accountId)
			throws PortalException, SystemException {

		List<EmailAddress> emailAddresses = Collections.emptyList();

		if (_log.isDebugEnabled()) {
			_log.debug("Exporting Company Email Addresses for classPK:"
					+ accountId);
		}

		emailAddresses =
				EmailAddressServiceUtil.getEmailAddresses(
						Account.class.getName(), accountId);
		if (!emailAddresses.isEmpty()) {

			Element emailAddressesElement = companyAccountElement.addElement(
					ELEMENT_EMAIL_ADDRESSES);

			for (EmailAddress emailAddress : emailAddresses) {
				Element emailAddressElement = emailAddressesElement.addElement(
						ELEMENT_EMAIL_ADDRESS);

				long emailAddressId = emailAddress.getEmailAddressId();

				String emailAddressPath =
						getEmailAddressPath(portletDataContext, accountId,
								emailAddressId);

				portletDataContext.addClassedModel(emailAddressElement,
						emailAddressPath, emailAddress, NAMESPACE);
			}
		}

	}

	private void _exportLogo(
			PortletDataContext portletDataContext, Company company,
			Element companyElement)
			throws SystemException {

		Element logoElement = companyElement.addElement(ELEMENT_LOGO);

		long logoId = company.getLogoId();

		logoElement.addAttribute(ATTRIBUTE_LOGO_ID, String.valueOf(logoId));

		String logoPath = getCompanyLogoPath(portletDataContext, company);
		logoElement.addAttribute(ATTRIBUTE_PATH, logoPath);

		Image image = ImageLocalServiceUtil.getCompanyLogo(logoId);

		byte[] imgBytes = image.getTextObj();

		portletDataContext.addZipEntry(logoPath, imgBytes);
	}

	private void _exportPhones(
			PortletDataContext portletDataContext,
			Element companyAccountElement, long accountId)
			throws PortalException, SystemException {

		List<Phone> phones = Collections.emptyList();

		if (_log.isDebugEnabled()) {

			_log.debug("Exporting Company Email Phones for classPK:"
					+ accountId);
		}

		phones = PhoneServiceUtil.getPhones(Account.class.getName(), accountId);

		if (!phones.isEmpty()) {

			Element phonesElement = companyAccountElement.addElement(
					ELEMENT_PHONES);

			for (Phone phone : phones) {

				Element phoneElement = phonesElement.addElement(ELEMENT_PHONE);

				long phoneId = phone.getPhoneId();

				String phonePath =
						getPhonePath(portletDataContext, accountId,
								phoneId);
				portletDataContext.addClassedModel(phoneElement,
						phonePath, phone, NAMESPACE);
			}
		}

	}

	private void _exportPreferences(
			PortletPreferences companyPreferences, Element rootElement) {

		Element preferencesElement = rootElement.addElement(
				ELEMENT_PORTLET_PREFERENCES);

		Enumeration<String> names = companyPreferences.getNames();

		while (names.hasMoreElements()) {
			Element prefElement =

					preferencesElement.addElement(ELEMENT_PREFERENCE);

			Element prefNameElement = prefElement.addElement(ELEMENT_NAME);

			Element prefValueElement = prefElement.addElement(ELEMENT_VALUE);

			String prefName = names.nextElement();
			prefNameElement.addText(prefName);
			String prefValue = companyPreferences.getValue(
					prefName, StringPool.BLANK);
			prefValueElement.addText(prefValue);
		}
	}

	private void _exportWebsites(
			PortletDataContext portletDataContext,
			Element companyAccountElement, long accountId)
			throws PortalException, SystemException {

		List<Website> websites = Collections.emptyList();

		if (_log.isDebugEnabled()) {

			_log.debug("Exporting Company Websites for classPK:"
					+ accountId);
		}

		websites =
				WebsiteServiceUtil.getWebsites(
						Account.class.getName(), accountId);

		if (!websites.isEmpty()) {

			Element websitesElement = companyAccountElement.addElement(
					ELEMENT_WEBSITES);

			for (Website website : websites) {

				Element websiteElement = websitesElement.addElement(
						ELEMENT_WEBSITE);

				long websiteId = website.getWebsiteId();

				String websitePath =
						getWebsitePath(portletDataContext, accountId,
								websiteId);

				portletDataContext.addClassedModel(websiteElement,
						websitePath, website, NAMESPACE);
			}
		}
	}

	private static Log _log = LogFactoryUtil
			.getLog(PortalSettingsExportLARHandler.class);

}