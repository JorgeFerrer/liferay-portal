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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.util.GroupCreationStep;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = GroupCreationStepHelper.class)
public class GroupCreationStepHelper {

	public GroupCreationStep getNextGroupCreationStep(
		long groupId, String groupCreationStepName) {

		if (Validator.isNull(groupCreationStepName)) {
			return null;
		}

		List<GroupCreationStep> groupCreationSteps =
			_groupCreationStepRegistry.getGroupCreationSteps(groupId);

		GroupCreationStep groupCreationStep =
			_groupCreationStepRegistry.getGroupCreationStep(
				groupCreationStepName);

		int groupCreationStepIndex = groupCreationSteps.indexOf(
			groupCreationStep);

		if ((groupCreationStepIndex >= 0) &&
			(groupCreationStepIndex < (groupCreationSteps.size() - 1))) {

			return groupCreationSteps.get(groupCreationStepIndex + 1);
		}

		return null;
	}

	public GroupCreationStep getPreviousGroupCreationStep(
		long groupId, String groupCreationStepName) {

		if (Validator.isNull(groupCreationStepName)) {
			return null;
		}

		List<GroupCreationStep> groupCreationSteps =
			_groupCreationStepRegistry.getGroupCreationSteps(groupId);

		GroupCreationStep groupCreationStep =
			_groupCreationStepRegistry.getGroupCreationStep(
				groupCreationStepName);

		int groupCreationStepIndex = groupCreationSteps.indexOf(
			groupCreationStep);

		if (groupCreationStepIndex > 0) {
			return groupCreationSteps.get(groupCreationStepIndex - 1);
		}

		return null;
	}

	public boolean isLastGroupCreationStep(
		long groupId, String groupCreationStepName) {

		GroupCreationStep groupCreationStep = getNextGroupCreationStep(
			groupId, groupCreationStepName);

		if (groupCreationStep == null) {
			return true;
		}

		return false;
	}

	@Reference
	private GroupCreationStepRegistry _groupCreationStepRegistry;

}