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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Portlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
public interface PortletContainerSecurity {

	public boolean hasAccessPermission(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException;

	public boolean isAccessAllowedToControlPanelPortlet(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException;

	public boolean isAccessAllowedToLayoutPortlet(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException;

	public void checkCSRFProtection(
			HttpServletRequest request, Portlet portlet)
		throws PortalException;

	public boolean isValidPortletId(String portletId);

}
