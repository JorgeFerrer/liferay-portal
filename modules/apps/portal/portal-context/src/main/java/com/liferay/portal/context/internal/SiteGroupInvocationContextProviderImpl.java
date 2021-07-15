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

import com.liferay.portal.kernel.context.SiteGroupInvocationContextProvider;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = SiteGroupInvocationContextProvider.class)
public class SiteGroupInvocationContextProviderImpl
	implements SiteGroupInvocationContextProvider {

	@Override
	public Group getCurrent() {
		if (isPresent()) {
			return _groupLocalService.fetchGroup(getSiteGroupId());
		}

		return null;
	}

	@Override
	public Class<Group> getModelClass() {
		return Group.class;
	}

	@Override
	public boolean isPresent() {
		Long groupId = getSiteGroupId();

		if ((groupId == null) || (groupId == 0)) {
			return false;
		}

		return true;
	}

	protected Long getSiteGroupId() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return null;
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		return themeDisplay.getSiteGroupId();
	}

	@Reference
	private GroupLocalService _groupLocalService;

}