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

package com.liferay.layout.type.controller.single.portlet.application.controller;

import com.liferay.layout.type.controller.full.page.application.controller.FullPageApplicationLayoutTypeController;
import com.liferay.layout.type.controller.single.portlet.application.constants.SinglePortletApplicationLayoutTypeControllerConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.LayoutTypeController;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {"layout.type=" + SinglePortletApplicationLayoutTypeControllerConstants.LAYOUT_TYPE_SINGLE_PORTLET_APPLICATION},
	service = LayoutTypeController.class
)
public class SinglePortletApplicationLayoutTypeController
	extends FullPageApplicationLayoutTypeController {

	@Override
	public String getURL() {
		return _URL;
	}

	@Override
	public boolean isInstanceable() {
		return false;
	}

	@Override
	protected String getEditPage() {
		return StringPool.BLANK;
	}

	@Override
	protected String getViewPage() {
		return _VIEW_PAGE;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.type.controller.single.portlet.application)"
	)
	protected void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	private static final String _URL =
		"${liferay:mainPath}/portal/layout?p_l_id=${liferay:plid}" +
			"&p_v_l_s_g_id=${liferay:pvlsgid}&p_p_state=pop_up";

	private static final String _VIEW_PAGE =
		"/layout/view/single_portlet_application.jsp";

}