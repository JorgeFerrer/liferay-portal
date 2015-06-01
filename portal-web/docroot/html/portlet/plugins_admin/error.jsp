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

<%@ include file="/html/portlet/plugins_admin/init.jsp" %>

<liferay-ui:error-header />

<liferay-ui:error exception="<%= PrincipalException.MustBeAuthenticated.class %>" message="please-sign-in-to-access-this-application" />
<liferay-ui:error exception="<%= PrincipalException.MustBeCompanyAdmin.class %>" message="you-do-not-have-the-required-permissions" />

<liferay-ui:error exception="<%= PrincipalException.MustBeEnabled.class %>">

	<%
	PrincipalException.MustBeEnabled pe = (PrincipalException.MustBeEnabled)errorException;
	%>

	<liferay-ui:message arguments="<%= pe.resourceName %>" key="x-is-not-enabled" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= PrincipalException.MustBeInvokedByPost.class %>" message="an-unexpected-error-occurred-while-connecting-to-the-specified-url" />
<liferay-ui:error exception="<%= PrincipalException.MustBeMarketplaceAdmin.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustBeOmniadmin.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustBeOwnedByCurrentUser.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustBePortletStrutsPath.class %>" message="the-portlet-is-not-configured-correctly" />
<liferay-ui:error exception="<%= PrincipalException.MustBeSupportedActionForRole.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustBeValidPortlet.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustHavePermission.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustHaveUserGroupRole.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustHaveUserRole.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= PrincipalException.MustHaveValidGroup.class %>" message="the-portlet-is-not-configured-correctly" />
<liferay-ui:error exception="<%= PrincipalException.MustHaveValidPermissionChecker.class %>" message="the-portlet-is-not-configured-correctly" />
<liferay-ui:error exception="<%= PrincipalException.MustHaveValidPortletId.class %>" message="the-portlet-is-not-configured-correctly" />
<liferay-ui:error exception="<%= PrincipalException.MustHaveValidPrincipalName.class %>" message="the-portlet-is-not-configured-correctly" />
<liferay-ui:error exception="<%= PrincipalException.MustInitializePermissionChecker.class %>" message="the-portlet-is-not-configured-correctly" />
<liferay-ui:error exception="<%= PrincipalException.MustNotBeGroupAdmin.class %>" message="you-do-not-have-the-required-permissions" />