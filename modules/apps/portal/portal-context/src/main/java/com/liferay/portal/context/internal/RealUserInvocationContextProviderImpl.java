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

package com.liferay.portal.context.internal;

import com.liferay.portal.kernel.context.RealUserInvocationContextProvider;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = RealUserInvocationContextProvider.class)
public class RealUserInvocationContextProviderImpl
	implements RealUserInvocationContextProvider {

	@Override
	public User getCurrent() {
		if (isPresent()) {
			return _userLocalService.fetchUser(getRealUserId());
		}

		return null;
	}

	@Override
	public Class<User> getModelClass() {
		return User.class;
	}

	@Override
	public boolean isPresent() {
		Long companyId = getRealUserId();

		if ((companyId == null) || (companyId == 0)) {
			return false;
		}

		return true;
	}

	protected Long getRealUserId() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return null;
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		return themeDisplay.getRealUserId();
	}

	@Reference
	private UserLocalService _userLocalService;

}