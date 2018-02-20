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
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

long parentGroupSearchContainerPrimaryKeys = ParamUtil.getLong(request, "parentGroupSearchContainerPrimaryKeys");

SearchContainer<LayoutSetPrototype> layoutSetPrototypeSearchContainer = new SearchContainer<>(liferayPortletRequest, currentURLObj, null, "there-are-no-site-templates");

List<LayoutSetPrototype> layoutSetPrototypes = siteCreationWizardDisplayContext.getLayoutSetPrototypes();

int indexFrom = layoutSetPrototypeSearchContainer.getStart();
int indexTo = layoutSetPrototypeSearchContainer.getEnd();

if (indexTo > layoutSetPrototypes.size()) {
	indexTo = layoutSetPrototypes.size();
}

layoutSetPrototypeSearchContainer.setResults(layoutSetPrototypes.subList(indexFrom, indexTo));
layoutSetPrototypeSearchContainer.setTotal(layoutSetPrototypes.size());

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "sites"), mainURL.toString());
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "site-templates"), StringPool.BLANK);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<%@ include file="/site_wizard/navigation.jspf" %>

<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_COMMUNITY) %>">
	<div class="breadcrumb-container">
		<div class="container-fluid-1280">
			<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
		</div>
	</div>

	<div class="container-fluid-1280">
		<liferay-ui:search-container
			searchContainer="<%= layoutSetPrototypeSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.LayoutSetPrototype"
				cssClass="selectable"
				escapedModel="<%= true %>"
				keyProperty="layoutSetPrototypeId"
				modelVar="layoutSetPrototype"
			>
				<liferay-portlet:renderURL varImpl="addSiteURL">
					<portlet:param name="jspPage" value="/site_creation_wizard.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(siteCreationWizardDisplayContext.getGroupId()) %>" />
					<portlet:param name="layoutSetPrototypeId" value="<%= String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId()) %>" />
					<portlet:param name="parentGroupSearchContainerPrimaryKeys" value="<%= String.valueOf(parentGroupSearchContainerPrimaryKeys) %>" />
					<portlet:param name="creationType" value="<%= SiteAdminConstants.CREATION_TYPE_SITE_TEMPLATE %>" />
				</liferay-portlet:renderURL>

				<%
				row.setCssClass("entry-card lfr-asset-item site-template-card");
				%>

				<liferay-ui:search-container-column-text>
					<liferay-frontend:icon-vertical-card
						cssClass="entry-display-style"
						icon="site-template"
						resultRow="<%= row %>"
						title="<%= layoutSetPrototype.getName(locale) %>"
					>
						<liferay-frontend:vertical-card-footer>
							<label class="text-muted">
								<%= HtmlUtil.escape(layoutSetPrototype.getUserName()) %>
							</label>
						</liferay-frontend:vertical-card-footer>
					</liferay-frontend:icon-vertical-card>

					<aui:button cssClass="hide site-template-button" href="<%= addSiteURL.toString() %>" value="select" />
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="icon" markupView="lexicon" searchContainer="<%= layoutSetPrototypeSearchContainer %>" />
		</liferay-ui:search-container>
	</div>

	<aui:script>
		$('.site-template-card').hover(
			function() {
				var button = $(this).find('.site-template-button');
				var card = $(this).find('.taglib-vertical-card');

				button.removeClass('hide');
				card.css('opacity', '0.5');
			},
			function() {
				var button = $(this).find('.site-template-button');
				var card = $(this).find('.taglib-vertical-card');

				button.addClass('hide');
				card.css('opacity', '1');
			}
		);
	</aui:script>
</c:if>