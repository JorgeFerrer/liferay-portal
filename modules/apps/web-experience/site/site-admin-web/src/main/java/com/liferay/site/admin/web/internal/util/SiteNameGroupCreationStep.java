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

package com.liferay.site.admin.web.internal.util;

import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.DuplicateGroupException;
import com.liferay.portal.kernel.exception.GroupInheritContentException;
import com.liferay.portal.kernel.exception.GroupKeyException;
import com.liferay.portal.kernel.exception.GroupParentException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.site.util.GroupCreationStep;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"group.creation.step.display.order:Integer=10",
		"group.creation.step.name=" + SiteNameGroupCreationStep.NAME
	},
	service = GroupCreationStep.class
)
public class SiteNameGroupCreationStep implements GroupCreationStep {

	public static final String NAME = "site-name";

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, getName());
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isActive(long groupId) {
		return true;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse,
			boolean create)
		throws Exception {

		Group group = null;

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Group.class.getName(), actionRequest);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			group = _siteAdminPortletHelper.updateGroup(
				actionRequest, serviceContext, create);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");

				return;
			}

			if (e instanceof DuplicateGroupException ||
				e instanceof GroupInheritContentException ||
				e instanceof GroupKeyException ||
				e instanceof GroupParentException.MustNotHaveStagingParent) {

				SessionErrors.add(actionRequest, e.getClass());

				redirect = ParamUtil.getString(actionRequest, "currentURL");

				actionResponse.sendRedirect(redirect);

				return;
			}

			throw e;
		}

		if (group != null) {
			redirect = getRedirect(actionResponse, group, redirect);
		}

		actionResponse.sendRedirect(redirect);
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/site_wizard/site_name.jsp");
	}

	protected String getRedirect(
		ActionResponse actionResponse, Group group, String portletURL) {

		StringBundler keySB = new StringBundler(5);

		keySB.append(StringPool.AMPERSAND);
		keySB.append(actionResponse.getNamespace());
		keySB.append("groupId");

		if (portletURL.contains(keySB.toString())) {
			return portletURL;
		}

		keySB.append(StringPool.EQUAL);
		keySB.append(group.getGroupId());

		StringBundler redirectSB = new StringBundler(2);

		redirectSB.append(portletURL);
		redirectSB.append(keySB.toString());

		return redirectSB.toString();
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private SiteAdminPortletHelper _siteAdminPortletHelper;

}