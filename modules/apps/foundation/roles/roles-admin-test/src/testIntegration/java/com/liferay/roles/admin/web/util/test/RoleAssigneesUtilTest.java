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

package com.liferay.roles.admin.web.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.roles.admin.web.util.RoleAssigneesUtil;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class RoleAssigneesUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_regularRole = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_regularRoleUser = UserTestUtil.addUser();
		_regularRoleSite = GroupTestUtil.addGroup();
		_regularRoleOrganization = OrganizationTestUtil.addOrganization();
		_regularRoleUserGroup = UserGroupTestUtil.addUserGroup();

		RoleLocalServiceUtil.addUserRole(
			_regularRoleUser.getUserId(), _regularRole);
		RoleLocalServiceUtil.addGroupRole(
			_regularRoleSite.getGroupId(), _regularRole);
		RoleLocalServiceUtil.addGroupRole(
			_regularRoleOrganization.getGroupId(), _regularRole);
		RoleLocalServiceUtil.addGroupRole(
			_regularRoleUserGroup.getGroupId(), _regularRole);

		_siteRole = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		_testSite = GroupTestUtil.addGroup();
		_siteRoleUser = UserTestUtil.addUser();
		_siteRoleUserGroup = UserGroupTestUtil.addUserGroup();

		GroupLocalServiceUtil.addUserGroup(
			_siteRoleUser.getUserId(), _testSite);
		GroupLocalServiceUtil.addUserGroupGroup(
			_siteRoleUserGroup.getUserGroupId(), _testSite);

		long[] siteRoleIdArray = new long[] {_siteRole.getRoleId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			_siteRoleUser.getUserId(), _testSite.getGroupId(), siteRoleIdArray);
		UserGroupGroupRoleLocalServiceUtil.addUserGroupGroupRoles(
			_siteRoleUserGroup.getGroupId(), _testSite.getGroupId(),
			siteRoleIdArray);

		_organizationRole = RoleTestUtil.addRole(
			RoleConstants.TYPE_ORGANIZATION);

		_testOrganization = OrganizationTestUtil.addOrganization();
		_organizationRoleUser = UserTestUtil.addUser();

		OrganizationLocalServiceUtil.addUserOrganization(
			_organizationRoleUser.getUserId(), _testOrganization);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			_organizationRoleUser.getUserId(), _testOrganization.getGroupId(),
			new long[] {_organizationRole.getRoleId()});
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_regularRoleSite);
		GroupLocalServiceUtil.deleteGroup(_testSite);
		OrganizationLocalServiceUtil.deleteOrganization(
			_regularRoleOrganization);
		OrganizationLocalServiceUtil.deleteOrganization(_testOrganization);
		RoleLocalServiceUtil.deleteRole(_organizationRole);
		RoleLocalServiceUtil.deleteRole(_regularRole);
		RoleLocalServiceUtil.deleteRole(_siteRole);
		UserGroupLocalServiceUtil.deleteUserGroup(_regularRoleUserGroup);
		UserGroupLocalServiceUtil.deleteUserGroup(_siteRoleUserGroup);
		UserLocalServiceUtil.deleteUser(_organizationRoleUser);
		UserLocalServiceUtil.deleteUser(_regularRoleUser);
		UserLocalServiceUtil.deleteUser(_siteRoleUser);
	}

	@Test
	public void testGetAssigneesCountOrganizationRole() {
		int organizationRoleAssigneesCount =
			RoleAssigneesUtil.getAssigneesCount(_organizationRole);

		Assert.assertEquals(1, organizationRoleAssigneesCount);
	}

	@Test
	public void testGetAssigneesCountRegularRole() {
		int regularRoleAssigneesCount = RoleAssigneesUtil.getAssigneesCount(
			_regularRole);

		Assert.assertEquals(4, regularRoleAssigneesCount);
	}

	@Test
	public void testGetAssigneesCountSiteRole() {
		int siteRoleAssigneesCount = RoleAssigneesUtil.getAssigneesCount(
			_siteRole);

		Assert.assertEquals(2, siteRoleAssigneesCount);
	}

	@Test
	public void testGetAssigneesMessageImpliedRole() throws Exception {
		String impliedRoleMessage = "this-role-is-auto-assigned";

		HttpServletRequest request = new MockHttpServletRequest();

		Role userRole = RoleLocalServiceUtil.getRole(
			_regularRole.getCompanyId(), RoleConstants.USER);

		Assert.assertEquals(
			impliedRoleMessage,
			RoleAssigneesUtil.getAssigneesMessage(request, userRole));
	}

	@Test
	public void testGetAssigneesMessageMultipleAssignees() {
		String multipleAssigneeMessage = "x-assignees";

		HttpServletRequest request = new MockHttpServletRequest();

		Assert.assertEquals(
			multipleAssigneeMessage,
			RoleAssigneesUtil.getAssigneesMessage(request, _regularRole));
	}

	@Test
	public void testGetAssigneesMessageSingleAssignee() {
		String singleAssigneeMessage = "one-assignee";

		HttpServletRequest request = new MockHttpServletRequest();

		Assert.assertEquals(
			singleAssigneeMessage,
			RoleAssigneesUtil.getAssigneesMessage(request, _organizationRole));
	}

	@Test
	public void testGetOrganizationRoleAssigneesCountImpliedOrganizationRole() {
		int organizationRoleAssigneesCount =
			RoleAssigneesUtil.getOrganizationRoleAssigneesCount(
				_organizationRole);

		Assert.assertEquals(1, organizationRoleAssigneesCount);
	}

	@Test
	public void testGetOrganizationRoleAssigneesCountOrganizationRole() {
		int organizationRoleAssigneesCount =
			RoleAssigneesUtil.getOrganizationRoleAssigneesCount(
				_organizationRole);

		Assert.assertEquals(1, organizationRoleAssigneesCount);
	}

	@Test
	public void testGetOrganizationRoleAssigneesCountRegularRole() {
		int regularRoleAssigneesCount =
			RoleAssigneesUtil.getOrganizationRoleAssigneesCount(_regularRole);

		Assert.assertEquals(0, regularRoleAssigneesCount);
	}

	@Test
	public void testGetOrganizationRoleAssigneesCountSiteRole() {
		int siteRoleAssigneesCount =
			RoleAssigneesUtil.getOrganizationRoleAssigneesCount(_siteRole);

		Assert.assertEquals(0, siteRoleAssigneesCount);
	}

	@Test
	public void testGetRegularRoleAssigneesCountOrganizationRole() {
		int organizationRoleAssigneesCount1 =
			RoleAssigneesUtil.getRegularRoleAssigneesCount(_organizationRole);

		Assert.assertEquals(0, organizationRoleAssigneesCount1);
	}

	@Test
	public void testGetRegularRoleAssigneesCountRegularRole() {
		int regularRoleAssigneesCount =
			RoleAssigneesUtil.getRegularRoleAssigneesCount(_regularRole);

		Assert.assertEquals(4, regularRoleAssigneesCount);
	}

	@Test
	public void testGetRegularRoleAssigneesCountSiteRole() {
		int siteRoleAssigneesCount1 =
			RoleAssigneesUtil.getRegularRoleAssigneesCount(_siteRole);

		Assert.assertEquals(0, siteRoleAssigneesCount1);
	}

	@Test
	public void testGetSiteRoleAssigneesCountOrganizationRole() {
		int organizationRoleAssigneesCount1 =
			RoleAssigneesUtil.getSiteRoleAssigneesCount(_organizationRole);

		Assert.assertEquals(0, organizationRoleAssigneesCount1);
	}

	@Test
	public void testGetSiteRoleAssigneesCountRegularRole() {
		int regularRoleAssigneesCount =
			RoleAssigneesUtil.getSiteRoleAssigneesCount(_regularRole);

		Assert.assertEquals(0, regularRoleAssigneesCount);
	}

	@Test
	public void testGetSiteRoleAssigneesCountSiteRole() {
		int siteRoleAssigneesCount =
			RoleAssigneesUtil.getSiteRoleAssigneesCount(_siteRole);

		Assert.assertEquals(2, siteRoleAssigneesCount);
	}

	@Test
	public void testIsImpliedRole() throws Exception {
		long companyId = _regularRole.getCompanyId();

		Role guestRole = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);
		Role organizationUserRole = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.ORGANIZATION_USER);
		Role ownerRole = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.OWNER);
		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.SITE_MEMBER);
		Role userRole = RoleLocalServiceUtil.getRole(
			_regularRole.getCompanyId(), RoleConstants.USER);

		Assert.assertTrue(RoleAssigneesUtil.isImpliedRole(guestRole));
		Assert.assertTrue(
			RoleAssigneesUtil.isImpliedRole(organizationUserRole));
		Assert.assertTrue(RoleAssigneesUtil.isImpliedRole(ownerRole));
		Assert.assertTrue(RoleAssigneesUtil.isImpliedRole(siteMemberRole));
		Assert.assertTrue(RoleAssigneesUtil.isImpliedRole(userRole));
		Assert.assertFalse(RoleAssigneesUtil.isImpliedRole(_regularRole));
		Assert.assertFalse(RoleAssigneesUtil.isImpliedRole(_siteRole));
		Assert.assertFalse(RoleAssigneesUtil.isImpliedRole(_organizationRole));
	}

	private static Role _organizationRole;
	private static User _organizationRoleUser;
	private static Role _regularRole;
	private static Organization _regularRoleOrganization;
	private static Group _regularRoleSite;
	private static User _regularRoleUser;
	private static UserGroup _regularRoleUserGroup;
	private static Role _siteRole;
	private static User _siteRoleUser;
	private static UserGroup _siteRoleUserGroup;
	private static Organization _testOrganization;
	private static Group _testSite;

}