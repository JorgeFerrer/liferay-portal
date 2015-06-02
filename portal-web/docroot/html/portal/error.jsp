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

<%@ include file="/html/portal/init.jsp" %>

<%
Boolean staleSession = (Boolean)session.getAttribute(WebKeys.STALE_SESSION);

String userLogin = user.getEmailAddress();

if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_SN)) {
	userLogin = user.getScreenName();
}
else if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_ID)) {
	userLogin = String.valueOf(user.getUserId());
}
%>

<c:if test="<%= (staleSession != null) && staleSession.booleanValue() %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="you-have-been-logged-off-because-you-signed-on-with-this-account-using-a-different-session" />
	</div>

	<%
	session.invalidate();
	%>

</c:if>

<c:if test="<%= SessionErrors.contains(request, LayoutPermissionException.class.getName()) %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="you-do-not-have-permission-to-view-this-page" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, PortletActiveException.class.getName()) %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="this-page-is-part-of-an-inactive-portlet" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, PrincipalException.MustBeAuthenticated.class.getName()) %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="please-sign-in-to-access-this-application" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, PrincipalException.MustBeCompanyAdmin.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustBeMarketplaceAdmin.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustBeOmniadmin.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustBeOwnedByCurrentUser.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustBeSupportedActionForRole.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustBeValidPortlet.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustHavePermission.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustHaveUserGroupRole.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustHaveUserRole.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustNotBeGroupAdmin.class.getName()) %>">

	<div class="alert alert-danger">
		<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, PrincipalException.MustBeEnabled.class.getName()) %>">
	<div class="alert alert-danger">

		<%
		PrincipalException.MustBeEnabled pe = (PrincipalException.MustBeEnabled)SessionErrors.get(request, PrincipalException.MustBeEnabled.class.getName());
		%>

		<liferay-ui:message arguments="<%= pe.resourceName %>" key="x-is-not-enabled" translateArguments="<%= false %>" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, PrincipalException.MustBeInvokedByPost.class.getName()) %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="an-unexpected-error-occurred-while-connecting-to-the-specified-url" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, PrincipalException.MustBePortletStrutsPath.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustHaveValidGroup.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustHaveValidPermissionChecker.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustHaveValidPortletId.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustHaveValidPrincipalName.class.getName()) ||
	SessionErrors.contains(request, PrincipalException.MustInitializePermissionChecker.class.getName()) %>">

	<div class="alert alert-danger">
		<liferay-ui:message key="the-portlet-is-not-configured-correctly" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, RequiredLayoutException.class.getName()) %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="please-contact-the-administrator-because-you-do-not-have-any-pages-configured" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, RequiredRoleException.class.getName()) %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="please-contact-the-administrator-because-you-do-not-have-any-roles" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, UserActiveException.class.getName()) %>">
	<div class="alert alert-danger">
		<%= LanguageUtil.format(request, "your-account-with-login-x-is-not-active", new LanguageWrapper[] {new LanguageWrapper("", HtmlUtil.escape(user.getFullName()), ""), new LanguageWrapper("<strong><em>", HtmlUtil.escape(userLogin), "</em></strong>")}, false) %><br /><br />
	</div>

	<%= LanguageUtil.format(request, "if-you-are-not-x-logout-and-try-again", HtmlUtil.escape(user.getFullName()), false) %>
</c:if>