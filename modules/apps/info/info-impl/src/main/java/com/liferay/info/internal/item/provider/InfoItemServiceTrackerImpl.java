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

package com.liferay.info.internal.item.provider;

import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.item.provider.InfoItemServiceTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemServiceTracker.class)
public class InfoItemServiceTrackerImpl implements InfoItemServiceTracker {

	@Override
	public <P> List<P> getAllInfoItemServices(
		Class<P> serviceClass, String itemClassName) {

		ServiceTrackerMap<String, ? extends List<?>>
			infoItemServiceTrackerMap =
				_itemClassNameInfoItemServiceTrackerMap.get(
					serviceClass.getName());

		return (List<P>)infoItemServiceTrackerMap.getService(itemClassName);
	}

	@Override
	public <P> List<String> getInfoItemClassNames(Class<P> serviceClass) {
		ServiceTrackerMap<String, ?> infoItemProviderServiceTrackerMap =
			_infoItemServiceTrackerMap.get(serviceClass.getName());

		return new ArrayList<>(infoItemProviderServiceTrackerMap.keySet());
	}

	@Override
	public <P> P getInfoItemService(
		Class<P> serviceClass, String itemClassName) {

		List<?> infoItemServices = getAllInfoItemServices(
			serviceClass, itemClassName);

		if ((infoItemServices == null) || infoItemServices.isEmpty()) {
			return null;
		}

		return (P)infoItemServices.get(0);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		Class<?>[] serviceClasses = new Class<?>[] {
			InfoItemFormProvider.class, InfoItemObjectProvider.class
		};

		for (Class<?> serviceClass : serviceClasses) {
			ServiceTrackerMap<String, ? extends List<?>>
				itemClassNameInfoItemServiceTrackerMap =
					ServiceTrackerMapFactory.openMultiValueMap(
						bundleContext, serviceClass, null,
						ServiceReferenceMapperFactory.create(
							bundleContext,
							(service, emitter) -> emitter.emit(
								GenericUtil.getGenericClassName(service))));

			_itemClassNameInfoItemServiceTrackerMap.put(
				serviceClass.getName(), itemClassNameInfoItemServiceTrackerMap);

		}
	}

	private final Map<String, ServiceTrackerMap<String, ? extends List<?>>>
		_itemClassNameInfoItemServiceTrackerMap = new HashMap<>();

}