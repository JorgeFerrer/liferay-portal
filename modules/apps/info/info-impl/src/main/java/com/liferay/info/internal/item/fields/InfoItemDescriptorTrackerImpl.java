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

package com.liferay.info.internal.item.fields;

import com.liferay.info.internal.util.GenericsUtil;
import com.liferay.info.item.descriptor.InfoItemDescriptor;
import com.liferay.info.item.descriptor.InfoItemDescriptorTracker;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Collections;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemDescriptorTracker.class)
public class InfoItemDescriptorTrackerImpl
	implements InfoItemDescriptorTracker {

	@Override
	public InfoItemDescriptor getInfoItemDescriptor(String itemClassName) {
		return _infoItemDescriptorServiceTrackerMap.getService(itemClassName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_infoItemDescriptorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, InfoItemDescriptor.class, null,
				new ServiceReferenceMapper<String, InfoItemDescriptor>() {

					@Override
					public void map(
						ServiceReference<InfoItemDescriptor> serviceReference,
						Emitter<String> emitter) {

						InfoItemDescriptor infoItemDescriptor =
							bundleContext.getService(serviceReference);

						String className = GenericsUtil.getItemClassName(
							infoItemDescriptor);

						emitter.emit(className);
					}

				},
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator("service.ranking")));
	}

	private ServiceTrackerMap<String, InfoItemDescriptor>
		_infoItemDescriptorServiceTrackerMap;

}