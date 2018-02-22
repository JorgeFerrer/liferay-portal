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

<%@ include file="/init.jsp" %>

<%
Group group = siteCreationWizardDisplayContext.getGroup();

GroupURLProvider groupURLProvider = (GroupURLProvider)request.getAttribute(SiteWebKeys.GROUP_URL_PROVIDER);

String viewSiteURL = groupURLProvider.getGroupURL(group, liferayPortletRequest);

PortletURL viewSiteSettingsURL = PortalUtil.getControlPanelPortletURL(request, group, SiteAdminPortletKeys.SITE_SETTINGS, 0, 0, PortletRequest.RENDER_PHASE);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
%>

<liferay-ui:success key='<%= SiteAdminPortletKeys.SITE_ADMIN + "requestProcessed" %>' message="<%= siteCreationWizardDisplayContext.getSummarySuccessMessage(viewSiteSettingsURL.toString()) %>" />

<div class="container-fluid-1280">
	<div class="col-md-8 offset-md-2 site-creation-container">
		<div class="site-creation-header">
			<liferay-ui:message key="summary" />
		</div>

		<div class="row site-creation-header">
			<div class="col-md-5">
			</div>

			<div class="col-md-7">
				<h3><%= HtmlUtil.escape(group.getDescriptiveName(locale)) %></h3>

				<aui:button-row>
					<aui:button href="<%= viewSiteURL %>" value='<%= LanguageUtil.format(request, "go-to-x", "site", true) %>' />

					<aui:button href="<%= viewSiteSettingsURL.toString() %>" value='<%= LanguageUtil.format(request, "go-to-x", "site-settings", true) %>' />
				</aui:button-row>
			</div>
		</div>
	</div>
</div>