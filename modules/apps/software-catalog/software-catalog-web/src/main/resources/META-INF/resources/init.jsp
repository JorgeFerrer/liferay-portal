<%--
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
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.DocumentComparator" %><%@
page import="com.liferay.portal.kernel.search.Field" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.OrderByComparator" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.model.*" %><%@
page import="com.liferay.portal.model.impl.*" %><%@
page import="com.liferay.portal.plugin.PluginPackageUtil" %><%@
page import="com.liferay.portal.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.service.*" %><%@
page import="com.liferay.portal.service.permission.PortalPermissionUtil" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PortletKeys" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portal.util.WebKeys" %><%@
page import="com.liferay.portal.webserver.WebServerServletTokenUtil" %><%@
page import="com.liferay.portlet.PortalPreferences" %><%@
page import="com.liferay.portlet.PortletPreferencesFactoryUtil" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.software.catalog.exception.DuplicateProductEntryModuleIdException" %><%@
page import="com.liferay.software.catalog.exception.DuplicateProductVersionDirectDownloadURLException" %><%@
page import="com.liferay.software.catalog.exception.FrameworkVersionNameException" %><%@
page import="com.liferay.software.catalog.exception.LicenseNameException" %><%@
page import="com.liferay.software.catalog.exception.NoSuchFrameworkVersionException" %><%@
page import="com.liferay.software.catalog.exception.NoSuchLicenseException" %><%@
page import="com.liferay.software.catalog.exception.NoSuchProductEntryException" %><%@
page import="com.liferay.software.catalog.exception.NoSuchProductVersionException" %><%@
page import="com.liferay.software.catalog.exception.ProductEntryAuthorException" %><%@
page import="com.liferay.software.catalog.exception.ProductEntryLicenseException" %><%@
page import="com.liferay.software.catalog.exception.ProductEntryNameException" %><%@
page import="com.liferay.software.catalog.exception.ProductEntryPageURLException" %><%@
page import="com.liferay.software.catalog.exception.ProductEntryScreenshotsException" %><%@
page import="com.liferay.software.catalog.exception.ProductEntryShortDescriptionException" %><%@
page import="com.liferay.software.catalog.exception.ProductEntryTypeException" %><%@
page import="com.liferay.software.catalog.exception.ProductVersionChangeLogException" %><%@
page import="com.liferay.software.catalog.exception.ProductVersionDownloadURLException" %><%@
page import="com.liferay.software.catalog.exception.ProductVersionFrameworkVersionException" %><%@
page import="com.liferay.software.catalog.exception.ProductVersionNameException" %><%@
page import="com.liferay.software.catalog.exception.RequiredLicenseException" %><%@
page import="com.liferay.software.catalog.exception.UnavailableProductVersionDirectDownloadURLException" %><%@
page import="com.liferay.software.catalog.model.SCFrameworkVersion" %><%@
page import="com.liferay.software.catalog.model.SCLicense" %><%@
page import="com.liferay.software.catalog.model.SCProductEntry" %><%@
page import="com.liferay.software.catalog.model.SCProductScreenshot" %><%@
page import="com.liferay.software.catalog.model.SCProductVersion" %><%@
page import="com.liferay.software.catalog.service.SCFrameworkVersionLocalServiceUtil" %><%@
page import="com.liferay.software.catalog.service.SCFrameworkVersionServiceUtil" %><%@
page import="com.liferay.software.catalog.service.SCLicenseLocalServiceUtil" %><%@
page import="com.liferay.software.catalog.service.SCProductEntryLocalServiceUtil" %><%@
page import="com.liferay.software.catalog.service.SCProductScreenshotLocalServiceUtil" %><%@
page import="com.liferay.software.catalog.service.SCProductVersionServiceUtil" %><%@
page import="com.liferay.software.catalog.service.permission.SCFrameworkVersionPermission" %><%@
page import="com.liferay.software.catalog.service.permission.SCLicensePermission" %><%@
page import="com.liferay.software.catalog.service.permission.SCPermission" %><%@
page import="com.liferay.software.catalog.service.permission.SCProductEntryPermission" %><%@
page import="com.liferay.software.catalog.web.util.SCUtil" %><%@
page import="com.liferay.software.catalog.web.constants.SoftwareCatalogWebKeys" %><%@
page import="com.liferay.taglib.search.ResultRow" %><%@
page import="com.liferay.taglib.search.SearchEntry" %><%@
page import="com.liferay.taglib.search.TextSearchEntry" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.HashSet" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
WindowState windowState = liferayPortletRequest.getWindowState();

PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();

PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/init-ext.jsp" %>