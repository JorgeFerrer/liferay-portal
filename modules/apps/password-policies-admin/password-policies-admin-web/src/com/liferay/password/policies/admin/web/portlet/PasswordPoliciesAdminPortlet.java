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

package com.liferay.password.policies.admin.web.portlet;

import com.liferay.password.policies.admin.web.constants.PasswordPoliciesAdminPortletKeys;
import com.liferay.password.policies.admin.web.upgrade.PasswordPoliciesAdminWebUpgrade;
import com.liferay.portal.DuplicatePasswordPolicyException;
import com.liferay.portal.NoSuchPasswordPolicyException;
import com.liferay.portal.PasswordPolicyNameException;
import com.liferay.portal.RequiredPasswordPolicyException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.OrganizationServiceUtil;
import com.liferay.portal.service.PasswordPolicyServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserServiceUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.control-panel-entry-category=users",
		"com.liferay.portlet.control-panel-entry-weight=4.0",
		"com.liferay.portlet.css-class-wrapper=portlet-users-admin",
		"com.liferay.portlet.icon=/icons/password_policies_admin.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + PasswordPoliciesAdminPortletKeys.PASSWORD_POLICIES_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class PasswordPoliciesAdminPortlet extends MVCPortlet {

	public void deletePasswordPolicy(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long passwordPolicyId = ParamUtil.getLong(
			actionRequest, "passwordPolicyId");

		PasswordPolicyServiceUtil.deletePasswordPolicy(passwordPolicyId);
	}

	public void editPasswordPolicy(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long passwordPolicyId = ParamUtil.getLong(
			actionRequest, "passwordPolicyId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		boolean changeable = ParamUtil.getBoolean(actionRequest, "changeable");

		boolean changeRequired = false;
		long minAge = 0;

		if (changeable) {
			changeRequired = ParamUtil.getBoolean(
				actionRequest, "changeRequired");
			minAge = ParamUtil.getLong(actionRequest, "minAge");
		}

		boolean checkSyntax = ParamUtil.getBoolean(
			actionRequest, "checkSyntax");
		boolean allowDictionaryWords = ParamUtil.getBoolean(
			actionRequest, "allowDictionaryWords");
		int minAlphanumeric = ParamUtil.getInteger(
			actionRequest, "minAlphanumeric");
		int minLength = ParamUtil.getInteger(actionRequest, "minLength");
		int minLowerCase = ParamUtil.getInteger(actionRequest, "minLowerCase");
		int minNumbers = ParamUtil.getInteger(actionRequest, "minNumbers");
		int minSymbols = ParamUtil.getInteger(actionRequest, "minSymbols");
		int minUpperCase = ParamUtil.getInteger(actionRequest, "minUpperCase");
		String regex = ParamUtil.getString(actionRequest, "regex");
		boolean history = ParamUtil.getBoolean(actionRequest, "history");
		int historyCount = ParamUtil.getInteger(actionRequest, "historyCount");
		boolean expireable = ParamUtil.getBoolean(actionRequest, "expireable");
		long maxAge = ParamUtil.getLong(actionRequest, "maxAge");
		long warningTime = ParamUtil.getLong(actionRequest, "warningTime");
		int graceLimit = ParamUtil.getInteger(actionRequest, "graceLimit");
		boolean lockout = ParamUtil.getBoolean(actionRequest, "lockout");
		int maxFailure = ParamUtil.getInteger(actionRequest, "maxFailure");
		long lockoutDuration = ParamUtil.getLong(
			actionRequest, "lockoutDuration");
		long resetFailureCount = ParamUtil.getLong(
			actionRequest, "resetFailureCount");
		long resetTicketMaxAge = ParamUtil.getLong(
			actionRequest, "resetTicketMaxAge");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			PasswordPolicy.class.getName(), actionRequest);

		if (passwordPolicyId <= 0) {

			// Add password policy

			PasswordPolicyServiceUtil.addPasswordPolicy(
				name, description, changeable, changeRequired, minAge,
				checkSyntax, allowDictionaryWords, minAlphanumeric, minLength,
				minLowerCase, minNumbers, minSymbols, minUpperCase, regex,
				history, historyCount, expireable, maxAge, warningTime,
				graceLimit, lockout, maxFailure, lockoutDuration,
				resetFailureCount, resetTicketMaxAge, serviceContext);
		}
		else {

			// Update password policy

			PasswordPolicyServiceUtil.updatePasswordPolicy(
				passwordPolicyId, name, description, changeable, changeRequired,
				minAge, checkSyntax, allowDictionaryWords, minAlphanumeric,
				minLength, minLowerCase, minNumbers, minSymbols, minUpperCase,
				regex, history, historyCount, expireable, maxAge, warningTime,
				graceLimit, lockout, maxFailure, lockoutDuration,
				resetFailureCount, resetTicketMaxAge, serviceContext);
		}
	}

	public void editPasswordPolicyAssignments(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long passwordPolicyId = ParamUtil.getLong(
			actionRequest, "passwordPolicyId");

		long[] addUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserIds"), 0L);
		long[] removeUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserIds"), 0L);
		long[] addOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addOrganizationIds"), 0L);
		long[] removeOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeOrganizationIds"), 0L);

		if (ArrayUtil.isNotEmpty(addUserIds)) {
			UserServiceUtil.addPasswordPolicyUsers(
				passwordPolicyId, addUserIds);
		}

		if (ArrayUtil.isNotEmpty(removeUserIds)) {
			UserServiceUtil.unsetPasswordPolicyUsers(
				passwordPolicyId, removeUserIds);
		}

		if (ArrayUtil.isNotEmpty(addOrganizationIds)) {
			OrganizationServiceUtil.addPasswordPolicyOrganizations(
				passwordPolicyId, addOrganizationIds);
		}

		if (ArrayUtil.isNotEmpty(removeOrganizationIds)) {
			OrganizationServiceUtil.unsetPasswordPolicyOrganizations(
				passwordPolicyId, removeOrganizationIds);
		}
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchPasswordPolicyException.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeAuthenticated.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeCompanyAdmin.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeEnabled.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeInvokedByPost.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeMarketplaceAdmin.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeOmniadmin.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeOwnedByCurrentUser.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBePortletStrutsPath.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.
					MustBeSupportedActionForRole.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeValidPortlet.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustHavePermission.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustHaveUserGroupRole.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustHaveUserRole.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustHaveValidGroup.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.
					MustHaveValidPermissionChecker.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustHaveValidPortletId.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.
					MustHaveValidPrincipalName.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.
					MustInitializePermissionChecker.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustNotBeGroupAdmin.class.getName())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else if (SessionErrors.contains(
					renderRequest,
					DuplicatePasswordPolicyException.class.getName()) ||
				 SessionErrors.contains(
					renderRequest,
					PasswordPolicyNameException.class.getName())) {

			include("/edit_password_policy.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof DuplicatePasswordPolicyException ||
			cause instanceof NoSuchPasswordPolicyException ||
			cause instanceof PasswordPolicyNameException ||
			cause instanceof PrincipalException ||
			cause instanceof RequiredPasswordPolicyException) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setPasswordPoliciesAdminWebUpgrade(
		PasswordPoliciesAdminWebUpgrade passwordPoliciesAdminWebUpgrade) {
	}

}