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

package com.liferay.portlet.usersadmin.util;

import com.liferay.portal.NoSuchContactException;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PasswordPolicyRel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.impl.ContactImpl;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PasswordPolicyRelLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Raymond Augé
 * @author Zsigmond Rab
 * @author Hugo Huijser
 */
public class UserIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {User.class.getName()};

	public static final String PORTLET_ID = PortletKeys.USERS_ADMIN;

	public UserIndexer() {
		setCommitImmediately(true);
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.UID, Field.USER_ID);
		setIndexerEnabled(PropsValues.USERS_INDEXER_ENABLED);
		setPermissionAware(true);
		setStagingAware(false);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			return;
		}

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (value == null) {
				continue;
			}

			Class<?> clazz = value.getClass();

			if (clazz.isArray()) {
				Object[] values = (Object[])value;

				if (values.length == 0) {
					continue;
				}
			}

			addContextQueryParams(contextQuery, searchContext, key, value);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, "city", false);
		addSearchTerm(searchQuery, searchContext, "country", false);
		addSearchTerm(searchQuery, searchContext, "emailAddress", false);
		addSearchTerm(searchQuery, searchContext, "firstName", false);
		addSearchTerm(searchQuery, searchContext, "fullName", false);
		addSearchTerm(searchQuery, searchContext, "lastName", false);
		addSearchTerm(searchQuery, searchContext, "middleName", false);
		addSearchTerm(searchQuery, searchContext, "region", false);
		addSearchTerm(searchQuery, searchContext, "screenName", false);
		addSearchTerm(searchQuery, searchContext, "street", false);
		addSearchTerm(searchQuery, searchContext, "zip", false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	protected void addContextQueryParams(
			BooleanQuery contextQuery, SearchContext searchContext, String key,
			Object value)
		throws Exception {

		if (key.equals("usersGroups")) {
			if (value instanceof Long[]) {
				Long[] values = (Long[])value;

				BooleanQuery usersGroupsQuery = BooleanQueryFactoryUtil.create(
					searchContext);

				for (long groupId : values) {
					usersGroupsQuery.addTerm("groupIds", groupId);
				}

				contextQuery.add(usersGroupsQuery, BooleanClauseOccur.MUST);
			}
			else {
				contextQuery.addRequiredTerm("groupIds", String.valueOf(value));
			}
		}
		else if (key.equals("userGroupRole")) {
			if (value instanceof Long[] && ((Long[])value).length == 2) {
				Long[] values = (Long[])value;

				contextQuery.addRequiredTerm(
					"userGroupRoleIds",
					values[0] + StringPool.UNDERLINE + values[1]);
			}
		}
		else if (key.equals("usersOrgs")) {
			if (value instanceof Long[]) {
				Long[] values = (Long[])value;

				BooleanQuery usersOrgsQuery = BooleanQueryFactoryUtil.create(
					searchContext);

				for (long organizationId : values) {
					usersOrgsQuery.addTerm("organizationIds", organizationId);
					usersOrgsQuery.addTerm(
						"ancestorOrganizationIds", organizationId);
				}

				contextQuery.add(usersOrgsQuery, BooleanClauseOccur.MUST);
			}
			else {
				contextQuery.addRequiredTerm(
					"organizationIds", String.valueOf(value));
			}
		}
		else if (key.equals("usersOrgsCount")) {
			contextQuery.addRequiredTerm(
				"organizationCount", String.valueOf(value));
		}
		else if (key.equals("usersOrgsTree")) {
			if (value instanceof Long[]) {
				Long[] values = (Long[])value;

				BooleanQuery usersGroupsQuery = BooleanQueryFactoryUtil.create(
					searchContext);

				for (long organizationId : values) {
					usersGroupsQuery.addTerm("orgTreeIds", organizationId);
				}

				contextQuery.add(usersGroupsQuery, BooleanClauseOccur.MUST);
			}
			else {
				contextQuery.addRequiredTerm(
					"orgTreeIds", String.valueOf(value));
			}
		}
		else if (key.equals("usersPasswordPolicies")) {
			contextQuery.addRequiredTerm(
				"passwordPolicyId", String.valueOf(value));
		}
		else if (key.equals("usersRoles")) {
			contextQuery.addRequiredTerm("roleIds", String.valueOf(value));
		}
		else if (key.equals("usersTeams")) {
			contextQuery.addRequiredTerm("teamIds", String.valueOf(value));
		}
		else if (key.equals("usersUserGroups")) {
			contextQuery.addRequiredTerm("userGroupIds", String.valueOf(value));
		}
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		User user = (User)obj;

		deleteDocument(user.getCompanyId(), user.getUserId());

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(Contact.class);

		Contact contact = new ContactImpl();

		contact.setContactId(user.getContactId());
		contact.setCompanyId(user.getCompanyId());

		indexer.delete(contact);
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		User user = (User)obj;

		Document document = getBaseModelDocument(PORTLET_ID, user);

		long[] organizationIds = user.getOrganizationIds();

		document.addKeyword(Field.COMPANY_ID, user.getCompanyId());
		document.addKeyword(Field.GROUP_ID, user.getGroupIds());
		document.addDate(Field.MODIFIED_DATE, user.getModifiedDate());
		document.addKeyword(Field.SCOPE_GROUP_ID, user.getGroupIds());
		document.addKeyword(Field.STATUS, user.getStatus());
		document.addKeyword(Field.USER_ID, user.getUserId());
		document.addKeyword(Field.USER_NAME, user.getFullName());

		document.addKeyword(
			"ancestorOrganizationIds",
			getAncestorOrganizationIds(user.getOrganizationIds()));
		document.addText("emailAddress", user.getEmailAddress());
		document.addText("firstName", user.getFirstName());
		document.addText("fullName", user.getFullName());
		document.addKeyword("groupIds", user.getGroupIds());
		document.addText("jobTitle", user.getJobTitle());
		document.addText("lastName", user.getLastName());
		document.addText("middleName", user.getMiddleName());
		document.addKeyword("organizationIds", organizationIds);
		document.addKeyword(
			"organizationCount", String.valueOf(organizationIds.length));
		document.addKeyword("roleIds", user.getRoleIds());
		document.addText("screenName", user.getScreenName());
		document.addKeyword("teamIds", user.getTeamIds());
		document.addKeyword("userGroupIds", user.getUserGroupIds());

		document.addKeyword(
			"orgTreeIds", getDescendantOrganizationIds(organizationIds));

		document.addKeyword(
			"passwordPolicyId", getPasswordPolicyId(user.getUserId()));

		document.addKeyword(
			"userGroupRoleIds", getUserGroupRoleIds(user.getUserId()));

		populateAddresses(document, user.getAddresses(), 0, 0);

		return document;
	}

	@Override
	protected String doGetSortField(String orderByCol) {
		if (orderByCol.equals("email-address")) {
			return "emailAddress";
		}
		else if (orderByCol.equals("first-name")) {
			return "firstName";
		}
		else if (orderByCol.equals("job-title")) {
			return "jobTitle";
		}
		else if (orderByCol.equals("last-name")) {
			return "lastName";
		}
		else if (orderByCol.equals("screen-name")) {
			return "screenName";
		}
		else {
			return orderByCol;
		}
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet, PortletURL portletURL,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String firstName = document.get("firstName");
		String middleName = document.get("middleName");
		String lastName = document.get("lastName");

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		String title = fullNameGenerator.getFullName(
			firstName, middleName, lastName);

		String content = null;

		String userId = document.get(Field.USER_ID);

		portletURL.setParameter("struts_action", "/users_admin/edit_user");
		portletURL.setParameter("p_u_i_d", userId);

		return new Summary(title, content, portletURL);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		if (obj instanceof Long) {
			long userId = (Long)obj;

			User user = UserLocalServiceUtil.getUserById(userId);

			doReindex(user);
		}
		else if (obj instanceof long[]) {
			long[] userIds = (long[])obj;

			Map<Long, Collection<Document>> documentsMap =
				new HashMap<Long, Collection<Document>>();

			for (long userId : userIds) {
				User user = UserLocalServiceUtil.getUserById(userId);

				if (user.isDefaultUser()) {
					continue;
				}

				Document document = getDocument(user);

				long companyId = user.getCompanyId();

				Collection<Document> documents = documentsMap.get(companyId);

				if (documents == null) {
					documents = new ArrayList<Document>();

					documentsMap.put(companyId, documents);
				}

				documents.add(document);
			}

			for (Map.Entry<Long, Collection<Document>> entry :
					documentsMap.entrySet()) {

				long companyId = entry.getKey();
				Collection<Document> documents = entry.getValue();

				SearchEngineUtil.updateDocuments(
					getSearchEngineId(), companyId, documents,
					isCommitImmediately());
			}
		}
		else if (obj instanceof User) {
			User user = (User)obj;

			if (user.isDefaultUser()) {
				return;
			}

			Document document = getDocument(user);

			SearchEngineUtil.updateDocument(
				getSearchEngineId(), user.getCompanyId(), document,
				isCommitImmediately());

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				Contact.class);

			try {
				indexer.reindex(user.getContact());
			}
			catch (NoSuchContactException nscce) {

				// This is a temporary workaround for LPS-46825

			}
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		User user = UserLocalServiceUtil.getUserById(classPK);

		doReindex(user);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexUsers(companyId);
	}

	protected long[] getAncestorOrganizationIds(long[] organizationIds)
		throws Exception {

		Set<Long> ancestorOrganizationIds = new HashSet<Long>();

		for (long organizationId : organizationIds) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(organizationId);

			for (long ancestorOrganizationId :
					organization.getAncestorOrganizationIds()) {

				ancestorOrganizationIds.add(ancestorOrganizationId);
			}
		}

		return ArrayUtil.toLongArray(ancestorOrganizationIds);
	}

	protected long[] getDescendantOrganizationIds(long[] organizationIds)
		throws PortalException {

		Set<Long> descendantOrganizationIds = new HashSet<Long>();

		for (long organizationId : organizationIds) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(organizationId);

			descendantOrganizationIds.add(organizationId);

			for (long descendantId :
					organization.getDescendantOrganizationIds()) {

				descendantOrganizationIds.add(descendantId);
			}
		}

		return ArrayUtil.toLongArray(descendantOrganizationIds);
	}

	protected long getPasswordPolicyId(long userId) {
		PasswordPolicyRel passwordPolicyRel =
			PasswordPolicyRelLocalServiceUtil.fetchPasswordPolicyRel(
				User.class.getName(), userId);

		long passwordPolicyId = 0;

		if (passwordPolicyRel != null) {
			passwordPolicyId = passwordPolicyRel.getPasswordPolicyId();
		}

		return passwordPolicyId;
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected String[] getUserGroupRoleIds(long userId) {
		List<UserGroupRole> userGroupRoles =
			UserGroupRoleLocalServiceUtil.getUserGroupRoles(userId);

		String[] userGroupRoleIds = new String[userGroupRoles.size()];

		for (int i = 0; i < userGroupRoles.size(); i++) {
			UserGroupRole userGroupRole = userGroupRoles.get(i);

			userGroupRoleIds[i] =
				userGroupRole.getGroupId() + StringPool.UNDERLINE +
					userGroupRole.getRoleId();
		}

		return userGroupRoleIds;
	}

	protected void reindexUsers(long companyId) throws PortalException {
		final ActionableDynamicQuery actionableDynamicQuery =
			UserLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					User user = (User)object;

					if (!user.isDefaultUser()) {
						Document document = getDocument(user);

						actionableDynamicQuery.addDocument(document);
					}
				}

			});
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

}