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

package com.liferay.frontend.theme.dxp.wrapper;

import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.ThemeLocalServiceWrapper;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class DXPThemeLocalServiceWrapper
	extends ThemeLocalServiceWrapper {

	private static final String _ADMIN = "admin";
	private static final String _CLASSIC = "classic";
	private static final String _DXP_ADMIN = "admindxptheme_WAR_admindxptheme";
	private static final String _DXP_CLASSIC =
		"classicdxptheme_WAR_classicdxptheme";

	public DXPThemeLocalServiceWrapper() {
		super(null);
	}

	public DXPThemeLocalServiceWrapper(
		ThemeLocalService themeLocalService) {

		super(themeLocalService);
	}

	public Theme getTheme(
		long companyId, String themeId) {

		Theme theme = null;

		if (themeId.equals(_ADMIN)) {
			theme = super.getTheme(companyId, _DXP_ADMIN);
		}
		else if (themeId.equals(_CLASSIC)) {
			theme = super.getTheme(companyId, _DXP_CLASSIC);
		}

		if (theme == null) {
			theme = super.getTheme(companyId, themeId);
		}

		return theme;
	}

}