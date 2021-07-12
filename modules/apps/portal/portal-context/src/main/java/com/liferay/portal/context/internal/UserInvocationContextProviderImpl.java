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

import com.liferay.portal.kernel.context.UserInvocationContextProvider;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = UserInvocationContextProvider.class)
public class UserInvocationContextProviderImpl
	implements UserInvocationContextProvider {

	@Override
	public User getCurrent() {
		if (isPresent()) {
			return _userLocalService.fetchUser(getUserId());
		}

		return null;
	}

	@Override
	public boolean isPresent() {
		Long companyId = getUserId();

		if ((companyId == null) || (companyId == 0)) {
			return false;
		}

		return true;
	}

	protected Long getUserId() {
		return PrincipalThreadLocal.getUserId();
	}

	@Reference
	private UserLocalService _userLocalService;

}