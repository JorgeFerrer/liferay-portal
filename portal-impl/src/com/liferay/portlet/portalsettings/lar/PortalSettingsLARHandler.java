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
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortletKeys;

import java.util.Map;

/**
 * @author kamesh
 *
 */
public abstract class PortalSettingsLARHandler {

	public static final String NAMESPACE = "portal-settings";

	public static final String EXPORT_COMPANY_DETAILS = "company-details";

	public static final PortletDataHandlerBoolean exportCompanyDetails =
			new PortletDataHandlerBoolean(NAMESPACE,
					EXPORT_COMPANY_DETAILS, true);

	public static final String EXPORT_COMPANY_PREFERENCES =
			"export-company-preferences";

	public static final PortletDataHandlerBoolean exportCompanyPreferences =
			new PortletDataHandlerBoolean(NAMESPACE,
					EXPORT_COMPANY_PREFERENCES, true);

	public static final String EXPORT_LOGO = "logo";

	public static final PortletDataHandlerBoolean exportLogo =
			new PortletDataHandlerBoolean(NAMESPACE, EXPORT_LOGO, true);

	public static final PortletDataHandlerControl[] PORTAL_SETTINGS_CONTROLS =
			new PortletDataHandlerControl[] { exportCompanyDetails,
					exportCompanyPreferences, exportLogo };

	protected Account
			getAccount(
					PortletDataContext portletDataContext,
					Element companyElement) {

		Account account = null;
		Element accountElement = companyElement.element(ELEMENT_ACCOUNT);
		if (accountElement != null) {
			String accountPath = accountElement.attributeValue(ATTRIBUTE_PATH);
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

	protected String getAddressPath(
			PortletDataContext portletDataContext, long accountId,
			long addressId) {

		StringBundler sb = new StringBundler(4);
		sb.append(portletDataContext
				.getPortletPath(PortletKeys.PORTAL_SETTINGS));
		sb.append(PATH_COMPANY);
		sb.append("Address_");
		sb.append(accountId);
		sb.append("_");
		sb.append(addressId);
		sb.append(".xml");

		return sb.toString();
	}

	protected String getCompanyPath(
			PortletDataContext portletDataContext, Company company) {

		StringBundler sb = new StringBundler(4);
		sb.append(portletDataContext
				.getPortletPath(PortletKeys.PORTAL_SETTINGS));
		sb.append(PATH_COMPANY);
		sb.append(company.getCompanyId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getCompanyAccountPath(
			PortletDataContext portletDataContext, Company company) {

		StringBundler sb = new StringBundler(4);
		sb.append(portletDataContext
				.getPortletPath(PortletKeys.PORTAL_SETTINGS));
		sb.append(PATH_COMPANY);
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
		sb.append(PATH_COMPANY);
		sb.append("logo_");
		sb.append(company.getLogoId());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected String getEmailAddressPath(
			PortletDataContext portletDataContext, long accountId,
			long emailAddressId) {

		StringBundler sb = new StringBundler(4);
		sb.append(portletDataContext
				.getPortletPath(PortletKeys.PORTAL_SETTINGS));
		sb.append(PATH_COMPANY);
		sb.append("Email_Address_");
		sb.append(accountId);
		sb.append("_");
		sb.append(emailAddressId);
		sb.append(".xml");

		return sb.toString();
	}

	protected String getPhonePath(
			PortletDataContext portletDataContext, long accountId,
			long phoneId) {

		StringBundler sb = new StringBundler(4);
		sb.append(portletDataContext
				.getPortletPath(PortletKeys.PORTAL_SETTINGS));
		sb.append(PATH_COMPANY);
		sb.append("Phone_");
		sb.append(accountId);
		sb.append("_");
		sb.append(phoneId);
		sb.append(".xml");

		return sb.toString();
	}

	protected String getQualifiedKey(String key) {

		if (_log.isDebugEnabled()) {
			_log.debug("Building Qualified Key for '" + key + "' with NS '"
					+ NAMESPACE + "'");
		}

		StringBundler qualifiedKey = new StringBundler("_");
		qualifiedKey.append(NAMESPACE);
		qualifiedKey.append("_");
		qualifiedKey.append(key);

		return qualifiedKey.toString();
	}

	protected StringBundler getUserPath(
			PortletDataContext portletDataContext, User defaultUser) {

		StringBundler sb = new StringBundler(4);
		sb.append(portletDataContext
				.getPortletPath(PortletKeys.PORTAL_SETTINGS));
		sb.append(PATH_COMPANY);
		sb.append("User_");
		sb.append(defaultUser.getUserId());
		sb.append(".xml");
		return sb;
	}

	protected String getWebsitePath(
			PortletDataContext portletDataContext, long accountId,
			long websiteId) {

		StringBundler sb = new StringBundler(4);
		sb.append(portletDataContext
				.getPortletPath(PortletKeys.PORTAL_SETTINGS));
		sb.append(PATH_COMPANY);
		sb.append("Website_");
		sb.append(accountId);
		sb.append("_");
		sb.append(websiteId);
		sb.append(".xml");

		return sb.toString();
	}

	protected boolean isChecked(
			Map<String, String[]> parameterMap, String key,
			PortletDataHandlerBoolean booleanControl) {

		key = getQualifiedKey(key);

		if (_log.isDebugEnabled()) {
			_log.debug("Key[" + key + "]");
		}

		return MapUtil.getBoolean(parameterMap, key,
				booleanControl.getDefaultState());
	}

	protected static final String ATTRIBUTE_PATH = "path";

	protected static final String ATTRIBUTE_LOGO_ID = "logoId";

	protected static final String ELEMENT_ACCOUNT = "account";

	protected static final String ELEMENT_ADDRESS = "address";

	protected static final String ELEMENT_ADDRESSES = "addresses";

	protected static final String ELEMENT_COMPANY = "company";

	protected static final String ELEMENT_EMAIL_ADDRESS = "emailAddress";

	protected static final String ELEMENT_EMAIL_ADDRESSES = "emailAddresses";

	protected static final String ELEMENT_NAME = "name";

	protected static final String ELEMENT_LOGO = "logo";

	protected static final String ELEMENT_PHONES = "phones";

	protected static final String ELEMENT_PHONE = "phone";

	protected static final String ELEMENT_PORTAL_SETTINGS = "portal-settings";

	protected static final String ELEMENT_PORTLET_PREFERENCES =
			"portlet-preferences";

	protected static final String ELEMENT_PREFERENCE = "preference";

	protected static final String ELEMENT_USER = "user";

	protected static final String ELEMENT_VALUE = "value";

	protected static final String ELEMENT_WEBSITE = "website";

	protected static final String ELEMENT_WEBSITES = "websites";

	private static Log _log = LogFactoryUtil
			.getLog(PortalSettingsExportLARHandler.class);

	private static final String PATH_COMPANY = "/company/";
}