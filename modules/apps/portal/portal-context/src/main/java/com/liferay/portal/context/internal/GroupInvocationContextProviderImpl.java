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

import com.liferay.portal.kernel.context.GroupInvocationContextProvider;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GroupThreadLocal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = GroupInvocationContextProvider.class)
public class GroupInvocationContextProviderImpl
	implements GroupInvocationContextProvider {

	@Override
	public Group getCurrent() {
		if (isPresent()) {
			return _groupLocalService.fetchGroup(getGroupId());
		}

		return null;
	}

	@Override
	public Class<Group> getModelClass() {
		return Group.class;
	}

	@Override
	public boolean isPresent() {
		Long groupId = getGroupId();

		if ((groupId == null) || (groupId == 0)) {
			return false;
		}

		return true;
	}

	protected Long getGroupId() {
		return GroupThreadLocal.getGroupId();
	}

	@Reference
	private GroupLocalService _groupLocalService;

}