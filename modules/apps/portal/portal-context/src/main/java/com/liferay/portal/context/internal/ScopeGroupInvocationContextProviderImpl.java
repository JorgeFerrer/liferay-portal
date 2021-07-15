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

import com.liferay.portal.kernel.context.ScopeGroupInvocationContextProvider;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = ScopeGroupInvocationContextProvider.class)
public class ScopeGroupInvocationContextProviderImpl
	implements ScopeGroupInvocationContextProvider {

	@Override
	public Group getCurrent() {
		if (isPresent()) {
			return _groupLocalService.fetchGroup(getScopeGroupId());
		}

		return null;
	}

	@Override
	public boolean isPresent() {
		Long groupId = getScopeGroupId();

		if ((groupId == null) || (groupId == 0)) {
			return false;
		}

		return true;
	}

	protected Long getScopeGroupId() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return null;
		}

		return serviceContext.getScopeGroupId();
	}

	@Reference
	private GroupLocalService _groupLocalService;

}