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

package com.liferay.site.internal.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.internal.util.comparator.GroupStarterKitServiceWrapperOrderComparator;
import com.liferay.site.util.GroupStarterKit;
import com.liferay.site.util.GroupStarterKitRegistry;

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
@Component(immediate = true)
public class GroupStarterKitRegistryImpl implements GroupStarterKitRegistry {

	@Override
	public GroupStarterKit getGroupStarterKit(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<GroupStarterKit> groupStarterKitServiceWrapper =
			_serviceTrackerMap.getService(key);

		if (groupStarterKitServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No group starter kit registered with key " + key);
			}

			return null;
		}

		return groupStarterKitServiceWrapper.getService();
	}

	@Override
	public List<GroupStarterKit> getGroupStarterKits(long companyId) {
		return getGroupStarterKits(companyId, false);
	}

	@Override
	public List<GroupStarterKit> getGroupStarterKits(
		long companyId, boolean active) {

		List<GroupStarterKit> groupStarterKits = new ArrayList<>();

		List<ServiceWrapper<GroupStarterKit>> groupStarterKitServiceWrappers =
			ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			groupStarterKitServiceWrappers,
			_groupStarterKitServiceWrapperOrderComparator);

		for (ServiceWrapper<GroupStarterKit> groupStarterKitServiceWrapper :
				groupStarterKitServiceWrappers) {

			GroupStarterKit groupStarterKit =
				groupStarterKitServiceWrapper.getService();

			if (!active || (active && groupStarterKit.isActive(companyId))) {
				groupStarterKits.add(groupStarterKit);
			}
		}

		return Collections.unmodifiableList(groupStarterKits);
	}

	@Override
	public GroupStarterKit getNextGroupStarterKit(
		long companyId, String groupStarterKitkey, boolean active) {

		if (Validator.isNull(groupStarterKitkey)) {
			return null;
		}

		List<GroupStarterKit> groupStarterKits = getGroupStarterKits(
			companyId, active);

		GroupStarterKit groupStarterKit = getGroupStarterKit(
			groupStarterKitkey);

		int groupStarterKeyIndex = groupStarterKits.indexOf(groupStarterKit);

		if (groupStarterKeyIndex >= 0) {
			if (groupStarterKeyIndex >= (groupStarterKits.size() - 1)) {
				return groupStarterKits.get(0);
			}

			return groupStarterKits.get(groupStarterKeyIndex + 1);
		}

		return null;
	}

	@Override
	public GroupStarterKit getPreviousGroupStarterKit(
		long companyId, String groupStarterKitkey, boolean active) {

		if (Validator.isNull(groupStarterKitkey)) {
			return null;
		}

		List<GroupStarterKit> groupStarterKits = getGroupStarterKits(
			companyId, active);

		GroupStarterKit groupStarterKit = getGroupStarterKit(
			groupStarterKitkey);

		int groupStarterKeyIndex = groupStarterKits.indexOf(groupStarterKit);

		if (groupStarterKeyIndex > 0) {
			return groupStarterKits.get(groupStarterKeyIndex - 1);
		}

		if (groupStarterKeyIndex == 0) {
			return groupStarterKits.get(groupStarterKits.size() - 1);
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, GroupStarterKit.class, "group.starter.kit.key",
			ServiceTrackerCustomizerFactory.<GroupStarterKit>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupStarterKitRegistryImpl.class);

	private static final Comparator<ServiceWrapper<GroupStarterKit>>
		_groupStarterKitServiceWrapperOrderComparator =
			new GroupStarterKitServiceWrapperOrderComparator();

	private ServiceTrackerMap<String, ServiceWrapper<GroupStarterKit>>
		_serviceTrackerMap;

}