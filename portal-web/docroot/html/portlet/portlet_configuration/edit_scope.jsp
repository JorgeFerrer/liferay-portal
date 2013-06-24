<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
long[] selectedGroupIds = PortletConfigurationUtil.getGroupIds(portletPreferences, scopeGroupId, layout);

Group group = null;

if (selectedGroupIds.length > 0) {
	group = GroupLocalServiceUtil.getGroup(selectedGroupIds[0]);
}

Set<Group> availableGroups = new LinkedHashSet<Group>();

if (group != null) {
	availableGroups.add(group);
}

availableGroups.add(themeDisplay.getSiteGroup());
availableGroups.add(company.getGroup());

for (Layout scopeGroupLayout : LayoutLocalServiceUtil.getScopeGroupLayouts(layout.getGroupId(), layout.isPrivateLayout())) {
	availableGroups.add(scopeGroupLayout.getScopeGroup());
}
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="scope" />
</liferay-util:include>

<aui:fieldset>
	<aui:field-wrapper label="scope" name="scopeId">
		<liferay-ui:icon-menu direction="down" icon="<%= group.getIconURL(themeDisplay) %>" message="<%= group.getDescriptiveName(locale) %>" showWhenSingleIcon="<%= true %>">

			<%
			for (Group availableGroup : availableGroups) {
			%>

				<liferay-portlet:actionURL var="setScopeURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="portletResource" value="<%= portletResource %>" />
					<portlet:param name="struts_action" value="/portlet_configuration/edit_scope" />
					<portlet:param name="scopeId" value="<%= PortletConfigurationUtil.getScopeId(availableGroup, scopeGroupId) %>" />
				</liferay-portlet:actionURL>

				<liferay-ui:icon
					id='<%= "scope" + availableGroup.getGroupId() %>'
					message="<%= availableGroup.getDescriptiveName(locale) %>"
					method="post"
					src="<%= availableGroup.getIconURL(themeDisplay) %>"
					url="<%= setScopeURL %>"
				/>

			<%
			}
			%>

			<c:if test="<%= !layout.hasScopeGroup() %>">
				<liferay-portlet:actionURL var="createNewScopeURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="portletResource" value="<%= portletResource %>" />
					<portlet:param name="struts_action" value="/portlet_configuration/edit_scope" />
					<portlet:param name="scopeId" value="<%= PortletConfigurationUtil.SCOPE_ID_LAYOUT_UUID_PREFIX + layout.getUuid() %>" />
				</liferay-portlet:actionURL>

				<liferay-ui:icon
					id="scopeCurLayout"
					image="add"
					message='<%= HtmlUtil.escape(layout.getName(locale)) + " (" + LanguageUtil.get(pageContext, "create-new") + ")" %>'
					method="post"
					url="<%= createNewScopeURL %>"
				/>
			</c:if>
		</liferay-ui:icon-menu>
	</aui:field-wrapper>
</aui:fieldset>