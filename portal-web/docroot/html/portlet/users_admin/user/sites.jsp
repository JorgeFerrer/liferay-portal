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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
User selUser = (User)request.getAttribute("user.selUser");
List<Group> regularGroups = (List<Group>)request.getAttribute("user.regularGroups");
List<UserGroupRole> siteRoles = (List<UserGroupRole>)request.getAttribute("user.siteRoles");
List<UserGroupGroupRole> userGroupGroupRoles = (List<UserGroupGroupRole>)request.getAttribute("user.userGroupGroupRoles");

currentURLObj.setParameter("historyKey", renderResponse.getNamespace() + "sites");
%>

<liferay-ui:error-marker key="errorSection" value="sites" />

<liferay-ui:membership-policy-error />

<liferay-util:buffer var="removeGroupIcon">
	<liferay-ui:icon
		iconCssClass="icon-remove"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<h3><liferay-ui:message key="sites" /></h3>

<%
Map<Group, Map<Role, List<String>>> groupRoleUserGroupNamesMap = new HashMap<Group, Map<Role, List<String>>>();

for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
	Group group = userGroupGroupRole.getGroup();
	Role role = userGroupGroupRole.getRole();
	UserGroup userGroup = userGroupGroupRole.getUserGroup();

	Map<Role, List<String>> roleUserGroupNames = groupRoleUserGroupNamesMap.get(group);

	if (roleUserGroupNames == null) {
		roleUserGroupNames = new HashMap<Role, List<String>>();

		roleUserGroupNames.put(role, new ArrayList<String>(Arrays.asList(userGroup.getName())));

		groupRoleUserGroupNamesMap.put(group, roleUserGroupNames);

		continue;
	}

	List<String> userGroupNames = roleUserGroupNames.get(role);

	if (userGroupNames == null) {
		roleUserGroupNames.put(role, new ArrayList<String>(Arrays.asList(userGroup.getName())));

		continue;
	}

	userGroupNames.add(userGroup.getName());
}

for (UserGroupRole siteRole : siteRoles) {
	Group group = siteRole.getGroup();
	Role role = siteRole.getRole();

	Map<Role, List<String>> roleUserGroupNames = groupRoleUserGroupNamesMap.get(group);

	if (roleUserGroupNames == null) {
		roleUserGroupNames = new HashMap<Role, List<String>>();

		roleUserGroupNames.put(role, new ArrayList<String>());

		groupRoleUserGroupNamesMap.put(group, roleUserGroupNames);

		continue;
	}

	if (roleUserGroupNames.containsKey(role)) {
		continue;
	}

	roleUserGroupNames.put(role, new ArrayList<String>());
}
%>

<liferay-ui:search-container
	curParam="sitesCur"
	headerNames="name,roles,null"
	iteratorURL="<%= currentURLObj %>"
	total="<%= regularGroups.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= regularGroups.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Group"
		escapedModel="<%= true %>"
		keyProperty="groupId"
		modelVar="group"
		rowIdProperty="friendlyURL"
	>

		<%
		List<String> names = new ArrayList<String>();

		Map<Role, List<String>> roleUserGroupNames = groupRoleUserGroupNamesMap.get(group);

		if (roleUserGroupNames != null) {
			Set<String> userGroupNames = new HashSet<String>();

			for (List<String> values : roleUserGroupNames.values()) {
				userGroupNames.addAll(values);
			}

			names.addAll(userGroupNames);
		}

		names.addAll(SitesUtil.getOrganizationNames(group, selUser));

		boolean hasUserGroupSiteMembership = !names.isEmpty();
		%>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="name"
		>

			<%
			String groupName = HtmlUtil.escape(group.getDescriptiveName(locale));

			buffer.append(groupName);

			List<String> userGroupNames = SitesUtil.getUserGroupNames(group, selUser);

			if (!userGroupNames.isEmpty()) {
				hasUserGroupSiteMembership = true;

				String message = StringPool.BLANK;

				if (userGroupNames.size() == 1) {
					message = LanguageUtil.format(request, "this-user-is-a-member-of-x-because-he-belongs-to-x", new Object[] {groupName, userGroupNames.get(0)}, false);
				}
				else {
					message = LanguageUtil.format(request, "this-user-is-a-member-of-x-because-he-belongs-to-x-and-x", new Object[] {groupName, StringUtil.merge(userGroupNames.subList(0, userGroupNames.size() - 1).toArray(new String[userGroupNames.size() - 1]), StringPool.COMMA_AND_SPACE) + ((userGroupNames.size() > 2) ? StringPool.COMMA : StringPool.BLANK), userGroupNames.get(userGroupNames.size() - 1)}, false);
				}
			%>

				<liferay-util:buffer var="iconHelp">
					<liferay-ui:icon-help message="<%= message %>" />
				</liferay-util:buffer>

			<%
				buffer.append(iconHelp);
			}
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="roles"
		>

			<%
			if (roleUserGroupNames != null) {
				for (Role role : roleUserGroupNames.keySet()) {
					String title = HtmlUtil.escape(role.getTitle(locale));

					buffer.append(title);

					names = roleUserGroupNames.get(role);

					if ((names != null) && !names.isEmpty()) {
						String message = StringPool.BLANK;

						if (names.size() == 1) {
							message = LanguageUtil.format(request, "this-user-is-assigned-x-from-x", new Object[] {title, names.get(0)}, false);
						}
						else {
							message = LanguageUtil.format(request, "this-user-is-assigned-x-from-x-and-x", new Object[] {title, StringUtil.merge(names.subList(0, names.size() - 1).toArray(new String[names.size() - 1]), StringPool.COMMA_AND_SPACE) + ((names.size() > 2) ? StringPool.COMMA : StringPool.BLANK), names.get(names.size() - 1)}, false);
						}
					%>

						<liferay-util:buffer var="iconHelp">
							<liferay-ui:icon-help message="<%= message %>" />
						</liferay-util:buffer>

					<%
						buffer.append(iconHelp);
					}

					buffer.append(StringPool.COMMA_AND_SPACE);
				}

				if (!roleUserGroupNames.isEmpty()) {
					buffer.setIndex(buffer.index() - 1);
				}
			}
			%>

		</liferay-ui:search-container-column-text>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) && !SiteMembershipPolicyUtil.isMembershipRequired(selUser.getUserId(), group.getGroupId()) && !SiteMembershipPolicyUtil.isMembershipProtected(permissionChecker, selUser.getUserId(), group.getGroupId()) %>">
			<liferay-ui:search-container-column-text>
				<c:if test="<%= !hasUserGroupSiteMembership && group.isManualMembership() %>">
					<a class="modify-link" data-rowId="<%= group.getGroupId() %>" href="javascript:;"><%= removeGroupIcon %></a>
				</c:if>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<liferay-ui:icon
		cssClass="modify-link"
		iconCssClass="icon-search"
		id="selectSiteLink"
		label="<%= true %>"
		linkCssClass="btn btn-default"
		message="select"
		url="javascript:;"
	/>

	<aui:script use="liferay-search-container">
		var Util = Liferay.Util;

		A.one('#<portlet:namespace />selectSiteLink').on(
			'click',
			function(event) {
				Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true,
							width: 600
						},
						id: '<portlet:namespace />selectGroup',
						title: '<liferay-ui:message arguments="site" key="select-x" />',

						<portlet:renderURL var="groupSelectorURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
							<portlet:param name="struts_action" value="/users_admin/select_site" />
							<portlet:param name="p_u_i_d" value="<%= String.valueOf(selUser.getUserId()) %>" />
						</portlet:renderURL>

						uri: '<%= groupSelectorURL.toString() %>'
					},
					function(event) {
						var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />groupsSearchContainer');

						var rowColumns = [];

						rowColumns.push(event.groupdescriptivename);
						rowColumns.push('');
						rowColumns.push('<a class="modify-link" data-rowId="' + event.groupid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeGroupIcon) %></a>');

						searchContainer.addRow(rowColumns, event.groupid);
						searchContainer.updateDataStore();
					}
				);
			}
		);

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />groupsSearchContainer');

		var searchContainerContentBox = searchContainer.get('contentBox');

		searchContainerContentBox.delegate(
			'click',
			function(event) {
				var link = event.currentTarget;

				var tr = link.ancestor('tr');
				var rowId = link.attr('data-rowId');

				var selectGroup = Util.getWindow('<portlet:namespace />selectGroup');

				if (selectGroup) {
					var selectButton = selectGroup.iframe.node.get('contentWindow.document').one('.selector-button[data-groupid="' + rowId + '"]');

					Util.toggleDisabled(selectButton, false);
				}

				searchContainer.deleteRow(tr, rowId);
			},
			'.modify-link'
		);

		Liferay.on(
			'<portlet:namespace />enableRemovedSites',
			function(event) {
				event.selectors.each(
					function(item, index, collection) {
						var modifyLink = searchContainerContentBox.one('.modify-link[data-rowid="' + item.attr('data-groupid') + '"]');

						if (!modifyLink) {
							Util.toggleDisabled(item, false);
						}
					}
				);
			}
		);
	</aui:script>
</c:if>