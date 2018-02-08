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

<img src="<%= siteAdminDisplayContext.getGroupStarterKitThumbnailSrc() %>" />

<p><%= HtmlUtil.escape(siteAdminDisplayContext.getGroupStarterKitDescription()) %></p>

<aui:button-row>
	<aui:button cssClass="btn-lg" name="applyButton" primary="<%= true %>" value="apply" />

	<aui:button cssClass="btn-lg" name="cancelButton" type="cancel" />
</aui:button-row>

<%
String addSiteURL = ParamUtil.getString(request, "addSiteURL");
%>

<aui:script use="aui-base">
	A.one('#<portlet:namespace/>applyButton').on(
		'click',
		function(event) {
			Liferay.Util.getOpener().refreshPortlet('<%= HtmlUtil.escape(addSiteURL) %>');
		}
	);

	A.one('#<portlet:namespace/>cancelButton').on(
		'click',
		function(event) {
			Liferay.Util.getOpener().closePopup('renderPreviewDialog');
		}
	);
</aui:script>