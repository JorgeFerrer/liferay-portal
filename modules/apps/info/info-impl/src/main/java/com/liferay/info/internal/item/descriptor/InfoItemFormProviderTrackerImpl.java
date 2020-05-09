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

package com.liferay.info.internal.item.descriptor;

import com.liferay.info.internal.util.GenericsUtil;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormProviderTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemFormProviderTracker.class)
public class InfoItemFormProviderTrackerImpl
	implements InfoItemFormProviderTracker {

	@Override
	public InfoItemFormProvider getInfoItemFormProvider(String itemClassName) {
		return _infoItemFormProviderServiceTrackerMap.getService(itemClassName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_infoItemFormProviderServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, InfoItemFormProvider.class, null,
				new ServiceReferenceMapper<String, InfoItemFormProvider>() {

					@Override
					public void map(
						ServiceReference<InfoItemFormProvider> serviceReference,
						Emitter<String> emitter) {

						InfoItemFormProvider infoItemFormProvider =
							bundleContext.getService(serviceReference);

						String className =
							infoItemFormProvider.getItemClassName();

						if (className == null) {
							className = GenericsUtil.getItemClassName(
								infoItemFormProvider);
						}

						emitter.emit(className);
					}

				});
	}

	private ServiceTrackerMap<String, InfoItemFormProvider>
		_infoItemFormProviderServiceTrackerMap;

}