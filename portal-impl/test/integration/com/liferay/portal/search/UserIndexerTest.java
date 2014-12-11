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

package com.liferay.portal.search;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.UserGroupRolePK;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationTestRule;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.OrganizationTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.RoleTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserGroupTestUtil;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.passwordpoliciesadmin.util.test.PasswordPolicyTestUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Andrew Betts
 */
@Sync
public class UserIndexerTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddRemoveGroups() throws Exception {
		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersGroups", _group.getGroupId());

		int initialCount = getUsersCount(params);

		_user1 = UserTestUtil.addGroupUser(_group, RoleConstants.SITE_MEMBER);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		long[] groupIds = ArrayUtil.remove(
			_user1.getGroupIds(), _group.getGroupId());

		UserTestUtil.updateUser(_user1, groupIds, null, null, null, null);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemoveOrganizations() throws Exception {
		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersOrgs", _organization.getOrganizationId());

		int initialCount = getUsersCount(params);

		_user1 = UserTestUtil.addOrganizationUser(
			_organization, RoleConstants.ORGANIZATION_USER);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		long[] organizationIds = ArrayUtil.remove(
			_user1.getOrganizationIds(), _organization.getOrganizationId());

		UserTestUtil.updateUser(
			_user1, null, organizationIds, null, null, null);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemovePasswordPolicies() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(TestPropsValues.getUserId());

		_passwordPolicy = PasswordPolicyTestUtil.addPasswordPolicy(
			serviceContext);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put(
			"usersPasswordPolicies", _passwordPolicy.getPasswordPolicyId());

		int initialCount = getUsersCount(params);

		_user1 = UserTestUtil.addUser();

		long[] userIds = new long[] {_user1.getUserId()};

		UserLocalServiceUtil.addPasswordPolicyUsers(
			_passwordPolicy.getPasswordPolicyId(), userIds);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		UserLocalServiceUtil.unsetPasswordPolicyUsers(
			_passwordPolicy.getPasswordPolicyId(), userIds);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemoveRoles() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersRoles", _role.getRoleId());

		int initialCount = getUsersCount(params);

		_user1 = UserTestUtil.addUser();

		long[] roleIds = ArrayUtil.append(
			_user1.getRoleIds(), _role.getRoleId());

		UserTestUtil.updateUser(_user1, null, null, roleIds, null, null);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		roleIds = ArrayUtil.remove(roleIds, _role.getRoleId());

		UserTestUtil.updateUser(_user1, null, null, roleIds, null, null);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemoveTeams() throws Exception {
		_team = TeamLocalServiceUtil.addTeam(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			RandomTestUtil.randomString(), null);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersTeams", _team.getTeamId());

		int initialCount = getUsersCount(params);

		_user1 = UserTestUtil.addUser();

		UserLocalServiceUtil.addTeamUser(_team.getTeamId(), _user1.getUserId());

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		UserLocalServiceUtil.deleteTeamUser(
			_team.getTeamId(), _user1.getUserId());

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemoveUserGroupRole() throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put(
			"userGroupRole",
			new Long[] {_group.getGroupId(), role.getRoleId()});

		int initialCount = getUsersCount(params);

		_user1 = UserTestUtil.addGroupAdminUser(_group);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		UserGroupRole userGroupRole =
			UserGroupRoleLocalServiceUtil.getUserGroupRole(
				new UserGroupRolePK(
					_user1.getUserId(), _group.getGroupId(), role.getRoleId()));

		List<UserGroupRole> userGroupRoles =
			new ArrayList<UserGroupRole> (
				UserGroupRoleLocalServiceUtil.getUserGroupRoles(
					_user1.getUserId(), _group.getGroupId()));

		userGroupRoles.remove(userGroupRole);

		UserTestUtil.updateUser(_user1, null, null, null, userGroupRoles, null);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemoveUserGroups() throws Exception {
		_userGroup = UserGroupTestUtil.addUserGroup();

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersUserGroups", _userGroup.getUserGroupId());

		int initialCount = getUsersCount(params);

		_user1 = UserTestUtil.addUser();

		long[] userGroupIds = ArrayUtil.append(
			_user1.getUserGroupIds(), _userGroup.getUserGroupId());

		UserTestUtil.updateUser(_user1, null, null, null, null, userGroupIds);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		userGroupIds = ArrayUtil.remove(
			userGroupIds, _userGroup.getUserGroupId());

		UserTestUtil.updateUser(_user1, null, null, null, null, userGroupIds);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemoveUsersOrganizationTree() throws Exception {
		_childOrganization = OrganizationTestUtil.addOrganization(
			_organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersOrgsTree", _childOrganization.getOrganizationId());

		int initialCount = getUsersCount(params);

		_user1 = UserTestUtil.addOrganizationUser(
			_organization, RoleConstants.ORGANIZATION_USER);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		OrganizationTestUtil.updateOrganization(
			_childOrganization.getOrganizationId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			_childOrganization.getName(), false);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testInheritedGroups() throws Exception {
		_userGroup = UserGroupTestUtil.addUserGroup();

		GroupLocalServiceUtil.addOrganizationGroup(
			_organization.getOrganizationId(), _group);
		GroupLocalServiceUtil.addUserGroupGroup(
			_userGroup.getUserGroupId(), _group);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("inherit", Boolean.TRUE);
		params.put("usersGroups", _group.getGroupId());

		int initialCount = getUsersCount(params);

		User organizationUser = UserTestUtil.addOrganizationUser(
			_organization, RoleConstants.ORGANIZATION_USER);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		User userGroupUser = UserTestUtil.addUser();

		long[] userGroupIds = ArrayUtil.append(
			userGroupUser.getUserGroupIds(), _userGroup.getUserGroupId());

		UserTestUtil.updateUser(
			userGroupUser, null, null, null, null, userGroupIds);

		Assert.assertEquals(initialCount + 2, getUsersCount(params));

		long[] organizationIds = ArrayUtil.remove(
			organizationUser.getOrganizationIds(),
			_organization.getOrganizationId());

		UserTestUtil.updateUser(
			organizationUser, null, organizationIds, null, null, null);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		userGroupIds = ArrayUtil.remove(
			userGroupIds, _userGroup.getUserGroupId());

		UserTestUtil.updateUser(
			userGroupUser, null, null, null, null, userGroupIds);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testInheritedRoles() throws Exception {
		_userGroup = UserGroupTestUtil.addUserGroup();

		GroupLocalServiceUtil.addOrganizationGroup(
			_organization.getOrganizationId(), _group);
		GroupLocalServiceUtil.addUserGroupGroup(
			_userGroup.getUserGroupId(), _group);

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleLocalServiceUtil.addGroupRole(_group.getGroupId(), _role);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("inherit", Boolean.TRUE);
		params.put("usersRoles", _role.getRoleId());

		int initialCount = getUsersCount(params);

		_user1 = UserTestUtil.addOrganizationUser(
			_organization, RoleConstants.ORGANIZATION_USER);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		_user2 = UserTestUtil.addUser();

		long[] userGroupIds = ArrayUtil.append(
			_user2.getUserGroupIds(), _userGroup.getUserGroupId());

		UserTestUtil.updateUser(_user2, null, null, null, null, userGroupIds);

		Assert.assertEquals(initialCount + 2, getUsersCount(params));

		long[] organizationIds = ArrayUtil.remove(
			_user1.getOrganizationIds(), _organization.getOrganizationId());

		UserTestUtil.updateUser(
			_user1, null, organizationIds, null, null, null);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		userGroupIds = ArrayUtil.remove(
			userGroupIds, _userGroup.getUserGroupId());

		UserTestUtil.updateUser(_user2, null, null, null, null, userGroupIds);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	protected int getUsersCount(LinkedHashMap<String, Object> params)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(true);

		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(TestPropsValues.getCompanyId());
		searchContext.setEnd(QueryUtil.ALL_POS);

		if (params != null) {
			String keywords = (String) params.remove("keywords");

			if (Validator.isNotNull(keywords)) {
				searchContext.setKeywords(keywords);
			}
		}

		searchContext.setStart(QueryUtil.ALL_POS);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		Hits hits = indexer.search(searchContext);

		return hits.getLength();
	}

	@DeleteAfterTestRun
	private Organization _childOrganization;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private PasswordPolicy _passwordPolicy;

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private Team _team;

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

	@DeleteAfterTestRun
	private UserGroup _userGroup;

}