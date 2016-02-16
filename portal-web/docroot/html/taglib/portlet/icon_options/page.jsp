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

<%@ include file="/html/taglib/portlet/icon_options/init.jsp" %>

<liferay-ui:icon-menu
	cssClass="portlet-options"
	direction="<%= direction %>"
	extended="<%= false %>"
	icon='<%= (markupView != null) && markupView.equals("lexicon") ? StringPool.BLANK : "../aui/ellipsis-vertical" %>'
	markupView="<%= markupView %>"
	message="options"
	showArrow="<%= false %>"
	showWhenSingleIcon="<%= true %>"
	triggerCssClass="icon-monospaced"
>

	<%
	for (PortletConfigurationIcon portletConfigurationIcon : portletConfigurationIcons) {
		boolean include = portletConfigurationIcon.include(request, new PipingServletResponse(pageContext));

		if (!include) {
			resourceBundle = (ResourceBundle)pageContext.getAttribute("resourceBundle");
			ResourceBundleLoader resourceBundleLoader = (ResourceBundleLoader)request.getAttribute("RESOURCE_BUNDLE_LOADER");

			pageContext.removeAttribute("resourceBundle");
			request.setAttribute("RESOURCE_BUNDLE_LOADER", portletConfigurationIcon.getResourceBundleLoader());
		%>

			<liferay-ui:icon
				alt="<%= portletConfigurationIcon.getAlt() %>"
				ariaRole="<%= portletConfigurationIcon.getAriaRole() %>"
				cssClass="<%= portletConfigurationIcon.getCssClass() %>"
				data="<%= portletConfigurationIcon.getData() %>"
				iconCssClass="<%= portletConfigurationIcon.getIconCssClass() %>"
				id="<%= portletConfigurationIcon.getId() %>"
				image="<%= portletConfigurationIcon.getImage() %>"
				imageHover="<%= portletConfigurationIcon.getImageHover() %>"
				label="<%= portletConfigurationIcon.isLabel() %>"
				lang="<%= portletConfigurationIcon.getLang() %>"
				linkCssClass="<%= portletConfigurationIcon.getLinkCssClass() %>"
				localizeMessage="<%= portletConfigurationIcon.isLocalizeMessage() %>"
				message="<%= portletConfigurationIcon.getMessage() %>"
				method="<%= portletConfigurationIcon.getMethod() %>"
				onClick="<%= portletConfigurationIcon.getOnClick() %>"
				src="<%= portletConfigurationIcon.getSrc() %>"
				srcHover="<%= portletConfigurationIcon.getSrcHover() %>"
				target="<%= portletConfigurationIcon.getTarget() %>"
				toolTip="<%= portletConfigurationIcon.isToolTip() %>"
				url="<%= portletConfigurationIcon.getURL() %>"
				useDialog="<%= portletConfigurationIcon.isUseDialog() %>"
			/>

		<%
			if (resourceBundle != null) {
				pageContext.setAttribute("resourceBundle", resourceBundle);
			}
			if (resourceBundleLoader != null) {
				request.setAttribute("RESOURCE_BUNDLE_LOADER", resourceBundleLoader);
			}
			else {
				request.removeAttribute("RESOURCE_BUNDLE_LOADER");
			}
		}
	}
	%>

</liferay-ui:icon-menu>