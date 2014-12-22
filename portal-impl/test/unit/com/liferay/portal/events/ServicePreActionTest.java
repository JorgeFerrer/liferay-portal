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

package com.liferay.portal.events;

import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.util.test.UserTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Preston Crary
 */
public class ServicePreActionTest extends ServicePreAction {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		LayoutTestUtil.addLayout(_group);

		_request.setRequestURI(PortalUtil.getPathMain() + "/portal/login");

		_request.setAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET, _group.getPublicLayoutSet());
	}

	@Test
	public void testInitThemeDisplayPlidSiteLayout() throws Exception {
		long plid = getThemeDisplayPlid(false, false);

		Object[] defaultLayout = getDefaultSiteLayout(_user);

		Layout layout = (Layout)defaultLayout[0];

		Assert.assertEquals(layout.getPlid(), plid);
	}

	@Test
	public void testInitThemeDisplayPlidUserPersonalLayout() throws Exception {
		long plid = getThemeDisplayPlid(false, true);

		Object[] defaultLayout = getDefaultUserPersonalLayout(_user);

		Layout layout = (Layout)defaultLayout[0];

		Assert.assertEquals(layout.getPlid(), plid);
	}

	@Test
	public void testInitThemeDisplayPlidUserSiteLayout() throws Exception {
		boolean publicLayoutsAutoCreate =
			PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE;
		boolean privateLayoutsAutoCreate =
			PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE;

		PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE = false;
		PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE = false;

		try {
			long plid = getThemeDisplayPlid(false, true);

			Object[] defaultLayout = getDefaultUserSiteLayout(_user);

			Layout layout = (Layout) defaultLayout[0];

			Assert.assertEquals(layout.getPlid(), plid);
		}
		finally {
			PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE =
				publicLayoutsAutoCreate;
			PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE =
				privateLayoutsAutoCreate;
		}
	}

	@Test
	public void testInitThemeDisplayPlidVirtualLayout() throws Exception {
		long plid = getThemeDisplayPlid(true, false);

		Object[] defaultLayout = getDefaultVirtualLayout(_request);

		Layout layout = (Layout)defaultLayout[0];

		Assert.assertEquals(layout.getPlid(), plid);
	}

	protected long getThemeDisplayPlid(
			boolean hasGuestViewPermission, boolean signedIn)
		throws Exception {

		if (!hasGuestViewPermission) {
			Role role = RoleLocalServiceUtil.getRole(
				_group.getCompanyId(), RoleConstants.GUEST);

			ResourcePermissionLocalServiceUtil.removeResourcePermissions(
				_group.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, role.getRoleId(),
				ActionKeys.VIEW);
		}

		if (signedIn) {
			_user = UserTestUtil.addUser();
		}
		else {
			_user = PortalUtil.initUser(_request);
		}

		_request.setAttribute(WebKeys.USER, _user);

		ThemeDisplay themeDisplay = _servicePreAction.initThemeDisplay(
			_request, new MockHttpServletResponse());

		return themeDisplay.getPlid();
	}

	@DeleteAfterTestRun
	private Group _group;

	private final MockHttpServletRequest _request =
		new MockHttpServletRequest();
	private final ServicePreAction _servicePreAction = new ServicePreAction();
	private User _user;

}