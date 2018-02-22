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

<%@ include file="/META-INF/resources/init.jsp" %>

<%
String creationType = ParamUtil.getString(request, "creationType");

String groupStarterKitKey = ParamUtil.getString(request, "groupStarterKitKey");

long layoutSetPrototypeId = ParamUtil.getLong(request, "layoutSetPrototypeId");

long parentGroupSearchContainerPrimaryKeys = ParamUtil.getLong(request, "parentGroupSearchContainerPrimaryKeys");

long groupId = ParamUtil.getLong(request, "groupId");

Group group = null;

if (groupId > 0) {
	group = GroupLocalServiceUtil.getGroup(groupId);
}
%>

<portlet:actionURL name="editSiteName" var="editSiteNameActionURL" />

<aui:form action="<%= editSiteNameActionURL %>" method="post" name="siteNameFm">
	<aui:input name="creationType" type="hidden" value="<%= creationType %>" />
	<aui:input name="groupId" type="hidden" value="<%= siteAdminDisplayContext.getGroupId() %>" />
	<aui:input name="groupStarterKitKey" type="hidden" value="<%= groupStarterKitKey %>" />
	<aui:input name="layoutSetPrototypeId" type="hidden" value="<%= layoutSetPrototypeId %>" />
	<aui:input name="parentGroupSearchContainerPrimaryKeys" type="hidden" value="<%= parentGroupSearchContainerPrimaryKeys %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<aui:model-context bean="<%= group %>" model="<%= Group.class %>" />

	<aui:fieldset cssClass="site-name-fieldset">
		<aui:input label="site-name" name="name" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="submitButton" value="submit" />

		<aui:button cssClass="btn-lg" name="cancelButton" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base,aui-io-request,liferay-notification,liferay-portlet-url">
	A.one('#<portlet:namespace />submitButton').on(
		'click',
		function(event) {
			var url = '<%= editSiteNameActionURL.toString() %>';

			A.io.request(
				url,
				{
					form: {
						id: '<portlet:namespace />siteNameFm'
					},
					method: 'POST',
					on: {
						success: function(event, id, obj) {
							var response = JSON.parse(obj.response);

							if (response.success) {
								var redirect = Liferay.PortletURL.createURL('<%= siteCreationWizardDisplayContext.getSiteNameRedirect() %>');

								redirect.setParameter('groupId', response.groupId.toString());
								redirect.setWindowState('<%= LiferayWindowState.NORMAL.toString() %>');

								Liferay.Util.getOpener().refreshPortlet('editSiteNameDialog', redirect.toString());
							}
							else {
								new Liferay.Notification(
									{
										message: response.errorMessage,
										render: true,
										title: '<liferay-ui:message key="danger" />',
										type: 'danger'
									}
								);
							}
						}
					}
				}
			);
		}
	);

	A.one('#<portlet:namespace />cancelButton').on(
		'click',
		function(event) {
			Liferay.Util.getOpener().closePopup('editSiteNameDialog');
		}
	);
</aui:script>