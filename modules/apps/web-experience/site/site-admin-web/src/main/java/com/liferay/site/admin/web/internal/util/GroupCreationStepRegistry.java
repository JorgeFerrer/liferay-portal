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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.admin.web.internal.util.comparator.GroupCreationStepServiceWrapperDisplayOrderComparator;
import com.liferay.site.util.GroupCreationStep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = GroupCreationStepRegistry.class)
public class GroupCreationStepRegistry {

	public GroupCreationStep getGroupCreationStep(
		String groupCreationStepName) {

		if (Validator.isNull(groupCreationStepName)) {
			return null;
		}

		ServiceWrapper<GroupCreationStep> groupCreationStepServiceWrapper =
			_groupCreationStepServiceTrackerMap.getService(
				groupCreationStepName);

		if (groupCreationStepServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No group creation step registered with name " +
						groupCreationStepName);
			}

			return null;
		}

		return groupCreationStepServiceWrapper.getService();
	}

	public List<GroupCreationStep> getGroupCreationSteps(long groupId) {
		List<GroupCreationStep> groupCreationSteps = new ArrayList<>();

		List<ServiceWrapper<GroupCreationStep>>
			groupCreationStepServiceWrappers = ListUtil.fromCollection(
				_groupCreationStepServiceTrackerMap.values());

		Collections.sort(
			groupCreationStepServiceWrappers,
			_groupCreationStepServiceWrapperDisplayOrderComparator);

		for (ServiceWrapper<GroupCreationStep> groupCreationStepServiceWrapper :
				groupCreationStepServiceWrappers) {

			GroupCreationStep groupCreationStep =
				groupCreationStepServiceWrapper.getService();

			if (groupCreationStep.isActive(groupId)) {
				groupCreationSteps.add(groupCreationStep);
			}
		}

		return Collections.unmodifiableList(groupCreationSteps);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_groupCreationStepServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, GroupCreationStep.class,
				"group.creation.step.name",
				ServiceTrackerCustomizerFactory.
					<GroupCreationStep>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_groupCreationStepServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupCreationStepRegistry.class);

	private ServiceTrackerMap<String, ServiceWrapper<GroupCreationStep>>
		_groupCreationStepServiceTrackerMap;
	private final Comparator<ServiceWrapper<GroupCreationStep>>
		_groupCreationStepServiceWrapperDisplayOrderComparator =
			new GroupCreationStepServiceWrapperDisplayOrderComparator();

}