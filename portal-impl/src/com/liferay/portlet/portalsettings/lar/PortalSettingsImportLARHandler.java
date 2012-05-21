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

import com.liferay.portal.NoSuchAddressException;
import com.liferay.portal.NoSuchEmailAddressException;
import com.liferay.portal.NoSuchPhoneException;
import com.liferay.portal.NoSuchWebsiteException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.AccountLocalServiceUtil;
import com.liferay.portal.service.AddressServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.CompanyServiceUtil;
import com.liferay.portal.service.EmailAddressServiceUtil;
import com.liferay.portal.service.PhoneServiceUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.WebsiteServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kamesh
 *
 */
public class PortalSettingsImportLARHandler extends PortalSettingsLARHandler {

	public void importSettings(
			PortletDataContext portletDataContext,
			Element portalPreferencesElement, Element companyElement)
			throws Exception {

		Map<String, String[]> parameterMap =
				portletDataContext.getParameterMap();

		if (companyElement != null) {

			String path = companyElement.attributeValue(ATTRIBUTE_PATH);

			Company importedCompany =
					(Company) portletDataContext
							.getZipEntryAsObject(path);

			boolean importCompanyDetails =
					isChecked(parameterMap, EXPORT_COMPANY_DETAILS,
							exportCompanyDetails);

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
					_importCompanyDetails(portletDataContext, companyElement,
							importedCompany);
				}

				boolean importLogo =
						isChecked(parameterMap,
								EXPORT_LOGO, exportLogo);

				// Update Logo
				if (importLogo) {
					_importLogo(portletDataContext, companyElement,
							companyId);
				}

				boolean importPreferences =
						isChecked(parameterMap,
								EXPORT_COMPANY_PREFERENCES,
								exportCompanyPreferences);

				if (importPreferences) {
					// Import other preferences
					_importPreferences(portalPreferencesElement, companyId);
					clearCaches();
				}

			}
		}
	}

	private List<Address> _importAddress(
			PortletDataContext portletDataContext, Company company,
			Element accountElement)
			throws PortalException, SystemException {

		if (_log.isDebugEnabled()) {
			_log.debug("Importing Company Addresses");
		}

		List<Address> addresses = new ArrayList<Address>();

		Element addressesElement = accountElement.element(ELEMENT_ADDRESSES);

		if (addressesElement != null) {

			List<Element> listOfAddress = addressesElement.elements(
					ELEMENT_ADDRESS);

			for (Element element : listOfAddress) {
				String path = element.attributeValue(ATTRIBUTE_PATH);

				Address address =
						(Address) portletDataContext
								.getZipEntryAsObject(path);
				if (address != null) {

					long addressId = address.getAddressId();

					try {

						AddressServiceUtil.getAddress(addressId);

					} catch (NoSuchAddressException e) {

						_log.debug("Address with PK:" + addressId
								+ " does not exist, creating ...");

						String className = address.getClassName();
						long classPK = address.getClassPK();
						String street1 = address.getStreet1();
						String street2 = address.getStreet2();
						String street3 = address.getStreet3();
						String city = address.getCity();
						String zip = address.getZip();
						long regionId = address.getRegionId();
						long countryId = address.getCountryId();
						int typeId = address.getTypeId();
						boolean mailing = address.isMailing();
						boolean primary = address.isPrimary();

						address =
								AddressServiceUtil.addAddress(className,
										classPK, street1, street2, street3,
										city, zip, regionId, countryId, typeId,
										mailing, primary);
					} finally {

						addresses.add(address);
					}

				}
			}

			UsersAdminUtil.updateAddresses(
					Account.class.getName(), company.getAccountId(), addresses);

			if (_log.isDebugEnabled()) {
				_log.debug("Successfully imported " + addresses.size()
						+ " addresses");
			}

		}

		return addresses;
	}

	private void _importCompanyDetails(
			PortletDataContext portletDataContext, Element companyElement,
			Company company)
			throws PortalException, SystemException {

		long companyId = company.getCompanyId();

		Element userElement = companyElement.element(ELEMENT_USER);

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

		if (userElement != null) {

			String path = userElement.attributeValue(ATTRIBUTE_PATH);

			User defaultUser =
					(User) portletDataContext.getZipEntryAsObject(path);

			if (defaultUser != null) {

				if (_log.isDebugEnabled()) {
					_log.debug("Company default user from LAR :"
							+ defaultUser.getUserId());
				}

				String languageId = defaultUser.getLanguageId();
				String timeZoneId = defaultUser.getTimeZoneId();

				CompanyLocalServiceUtil.updateDisplay(companyId,
						languageId, timeZoneId);
			}
		}

		// update the company account
		Account account = getAccount(portletDataContext, companyElement);

		account = AccountLocalServiceUtil.updateAccount(account);

		if (account != null) {

			if (_log.isDebugEnabled()) {

				_log.debug("Importing Company Account details ");
			}

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

			CompanyServiceUtil.updateCompany(
					companyId, virtualHostname, mx, homeURL, name, legalName,
					legalId, legalType, sicCode, tickerSymbol, industry, type,
					size);

			if (_log.isDebugEnabled()) {

				_log.debug("Importing Company Identification section details ");
			}

			Element accountElement = companyElement.element(ELEMENT_ACCOUNT);

			_importAddress(portletDataContext, company,
					accountElement);

			_importEmailAddress(portletDataContext, company,
					accountElement);

			_importPhones(portletDataContext, company,
					accountElement);

			_importWebsites(portletDataContext, company,
					accountElement);

		}

		PortalUtil.resetCDNHosts();

	}

	private List<EmailAddress> _importEmailAddress(
			PortletDataContext portletDataContext, Company company,
			Element accountElement)
			throws PortalException, SystemException {

		if (_log.isDebugEnabled()) {
			_log.debug("Importing Company Email Addresses");
		}

		List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();

		Element emailAddressesElement = accountElement.element(
				ELEMENT_EMAIL_ADDRESSES);

		if (emailAddressesElement != null) {

			List<Element> listOfEmailAddress = emailAddressesElement.elements(
					ELEMENT_EMAIL_ADDRESS);

			for (Element element : listOfEmailAddress) {

				String path = element.attributeValue(ATTRIBUTE_PATH);

				EmailAddress emailAddress =
						(EmailAddress) portletDataContext
								.getZipEntryAsObject(path);

				if (emailAddress != null) {
					long emailAddressId = emailAddress.getEmailAddressId();

					try {

						EmailAddressServiceUtil
								.getEmailAddress(emailAddressId);

					} catch (NoSuchEmailAddressException e) {

						_log.debug("No email exists with PK:" + emailAddressId
								+ " creating ...");

						String className = emailAddress.getClassName();
						long classPK = emailAddress.getClassPK();
						String address = emailAddress.getAddress();
						int typeId = emailAddress.getTypeId();
						boolean primary = emailAddress.isPrimary();

						emailAddress =
								EmailAddressServiceUtil.addEmailAddress(
										className, classPK, address, typeId,
										primary);

					} finally {

						emailAddresses.add(emailAddress);

					}
				}
			}

			UsersAdminUtil
					.updateEmailAddresses(
							Account.class.getName(), company.getAccountId(),
							emailAddresses);

			if (_log.isDebugEnabled()) {
				_log.debug("Successfully imported " + emailAddresses.size()
						+ " email addresses");
			}
		}

		return emailAddresses;
	}

	private List<Phone> _importPhones(
			PortletDataContext portletDataContext, Company company,
			Element accountElement)
			throws PortalException, SystemException {

		if (_log.isDebugEnabled()) {
			_log.debug("Importing Company Phones");
		}

		List<Phone> phones = new ArrayList<Phone>();

		Element phonesElement = accountElement.element(ELEMENT_PHONES);

		if (phonesElement != null) {

			List<Element> listOfPhones = phonesElement.elements(ELEMENT_PHONE);

			for (Element element : listOfPhones) {

				String path = element.attributeValue(ATTRIBUTE_PATH);

				Phone phone =
						(Phone) portletDataContext
								.getZipEntryAsObject(path);

				if (phone != null) {

					long phoneId = phone.getPhoneId();

					try {

						PhoneServiceUtil.getPhone(phoneId);

					} catch (NoSuchPhoneException e) {

						_log.debug("No Phone exist with PK:" + phoneId
								+ " creating new ...");

						String className = phone.getClassName();
						long classPK = phone.getClassPK();
						String number = phone.getNumber();
						String extension = phone.getExtension();
						int typeId = phone.getTypeId();
						boolean primary = phone.isPrimary();

						phone =
								PhoneServiceUtil.addPhone(className, classPK,
										number, extension, typeId, primary);

					} finally {

						phones.add(phone);

					}
				}

			}

			UsersAdminUtil
					.updatePhones(
							Account.class.getName(), company.getAccountId(),
							phones);

			if (_log.isDebugEnabled()) {
				_log.debug("Successfully imported " + phones.size()
						+ " phones");
			}

		}

		return phones;
	}

	private void _importLogo(
			PortletDataContext portletDataContext, Element companyElement,
			long companyId)
			throws PortalException, SystemException {

		Element logoElement = companyElement.element(ELEMENT_LOGO);

		if (logoElement != null) {

			String logoPath = logoElement.attributeValue(ATTRIBUTE_PATH);

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

	private void _importPreferences(
			Element portalPreferencesElement, long companyId)
			throws IOException, SystemException {

		if (portalPreferencesElement != null) {

			int onwerTypeId = PortletKeys.PREFS_OWNER_TYPE_COMPANY;

			String prefsXml = portalPreferencesElement.compactString();

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

	private List<Website> _importWebsites(
			PortletDataContext portletDataContext, Company company,
			Element accountElement)
			throws PortalException, SystemException {

		if (_log.isDebugEnabled()) {
			_log.debug("Importing Company Websites");
		}

		List<Website> websites = new ArrayList<Website>();

		Element websitesElement = accountElement.element(ELEMENT_WEBSITES);

		if (websitesElement != null) {

			List<Element> listOfWebsites = websitesElement.elements(
					ELEMENT_WEBSITE);

			for (Element element : listOfWebsites) {

				String path = element.attributeValue(ATTRIBUTE_PATH);

				Website website =
						(Website) portletDataContext
								.getZipEntryAsObject(path);

				if (website != null) {

					long websiteId = website.getWebsiteId();

					try {

						WebsiteServiceUtil.getWebsite(websiteId);

					} catch (NoSuchWebsiteException e) {
						_log.debug("No website exist with PK:" + websiteId
								+ " creating new ...");

						String className = website.getClassName();
						long classPK = website.getClassPK();
						String url = website.getUrl();
						int typeId = website.getTypeId();
						boolean primary = website.isPrimary();

						website =
								WebsiteServiceUtil.addWebsite(className,
										classPK, url, typeId, primary);

					} finally {

						websites.add(website);

					}
				}
			}

			UsersAdminUtil.updateWebsites(
					Account.class.getName(), company.getAccountId(), websites);

			if (_log.isDebugEnabled()) {
				_log.debug("Successfully imported " + websites.size()
						+ " web sites");
			}
		}

		return websites;
	}

	private static Log _log = LogFactoryUtil
			.getLog(PortalSettingsImportLARHandler.class);
}