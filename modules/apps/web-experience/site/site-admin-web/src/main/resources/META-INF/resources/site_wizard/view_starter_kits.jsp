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

SearchContainer<GroupStarterKit> groupStarterKitSearchContainer = new SearchContainer<>(liferayPortletRequest, currentURLObj, null, "there-are-no-available-starter-kits");

List<GroupStarterKit> groupStarterKits = siteCreationWizardDisplayContext.getGroupStarterKits();

int indexFrom = groupStarterKitSearchContainer.getStart();
int indexTo = groupStarterKitSearchContainer.getEnd();

if (indexTo > groupStarterKits.size()) {
	indexTo = groupStarterKits.size();
}

groupStarterKitSearchContainer.setResults(groupStarterKits.subList(indexFrom, indexTo));
groupStarterKitSearchContainer.setTotal(groupStarterKits.size());

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "sites"), mainURL.toString());
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "starter-kits"), StringPool.BLANK);

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
			searchContainer="<%= groupStarterKitSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.site.util.GroupStarterKit"
				escapedModel="<%= true %>"
				keyProperty="key"
				modelVar="groupStarterKit"
			>
				<liferay-portlet:renderURL varImpl="addSiteURL">
					<portlet:param name="jspPath" value="/site_creation_wizard.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(siteCreationWizardDisplayContext.getGroupId()) %>" />
					<portlet:param name="parentGroupSearchContainerPrimaryKeys" value="<%= String.valueOf(parentGroupSearchContainerPrimaryKeys) %>" />
					<portlet:param name="groupStarterKitKey" value="<%= groupStarterKit.getKey() %>" />
					<portlet:param name="creationType" value="<%= SiteAdminConstants.CREATION_TYPE_STARTER_KIT %>" />
				</liferay-portlet:renderURL>

				<%
				row.setCssClass("entry-card lfr-asset-item starter-kit-card");

				String thumbnailSrc = groupStarterKit.getThumbnailSrc();

				if (Validator.isNull(thumbnailSrc)) {
					thumbnailSrc = "sites";
				}
				%>

				<liferay-ui:search-container-column-text>
					<liferay-frontend:icon-vertical-card
						cssClass="entry-display-style"
						icon="<%= thumbnailSrc %>"
						resultRow="<%= row %>"
						title="<%= groupStarterKit.getName(locale) %>"
					/>

					<aui:button cssClass="hide starter-kit-apply-button" href="<%= addSiteURL.toString() %>" value="apply" />

					<liferay-portlet:renderURL var="renderPreviewURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="jspPage" value="/starter_kit/details.jsp" />
						<portlet:param name="groupStarterKitKey" value="<%= groupStarterKit.getKey() %>" />
						<portlet:param name="addSiteURL" value="<%= addSiteURL.toString() %>" />
					</liferay-portlet:renderURL>

					<a class="btn btn-default hide starter-kit-details-button" href="<%= siteCreationWizardDisplayContext.getRenderPreviewURL("renderPreview", groupStarterKit.getName(locale), renderPreviewURL) %>">
						<liferay-ui:message key="details" />
					</a>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="icon" markupView="lexicon" />
		</liferay-ui:search-container>
	</div>

	<aui:script use="aui-base">
		Liferay.provide(
			window,
			'<portlet:namespace/>renderPreview',
			function(title, uri) {
				Liferay.Util.openWindow(
					{
						dialog: {
							centered: true,
							destroyOnClose: true,
							height: 700,
							modal: true,
							width: 800
						},
						dialogIframe: {
							bodyCssClass: 'dialog-with-footer'
						},
						id: 'renderPreviewDialog',
						title: title,
						uri: uri
					}
				);
			}
		);

		Liferay.provide(
			window,
			'refreshPortlet',
			function(url) {
				Liferay.Util.getOpener().closePopup('renderPreviewDialog');

				if (url) {
					window.location.replace(url);
				}
			},
			['aui-dialog','aui-dialog-iframe']
		);

		Liferay.provide(
			window,
			'closePopup',
			function(dialogId) {
				var dialog = Liferay.Util.Window.getById(dialogId);

				dialog.destroy();
			},
			['liferay-util-window']
		);

		$('.starter-kit-card').hover(
			function() {
				var applyButton = $(this).find('.starter-kit-apply-button');
				var detailsButton = $(this).find('.starter-kit-details-button');
				var card = $(this).find('.taglib-vertical-card');

				applyButton.removeClass('hide');
				detailsButton.removeClass('hide');
				card.css('opacity', '0.5');
			},
			function() {
				var applyButton = $(this).find('.starter-kit-apply-button');
				var detailsButton = $(this).find('.starter-kit-details-button');
				var card = $(this).find('.taglib-vertical-card');

				applyButton.addClass('hide');
				detailsButton.addClass('hide');
				card.css('opacity', '1');
			}
		);
	</aui:script>
</c:if>