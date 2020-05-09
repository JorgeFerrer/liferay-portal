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

import com.liferay.info.internal.util.GenericsUtil;
import com.liferay.info.item.provider.InfoItemProvider;
import com.liferay.info.item.provider.InfoItemProviderTracker;
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
@Component(immediate = true, service = InfoItemProviderTracker.class)
public class InfoItemProviderTrackerImpl
	implements InfoItemProviderTracker {

	@Override
	public InfoItemProvider getInfoItemProvider(
		String itemClassName) {
		return _infoItemProviderServiceTrackerMap.getService(itemClassName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_infoItemProviderServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, InfoItemProvider.class, null,
				new ServiceReferenceMapper<String, InfoItemProvider>() {

					@Override
					public void map(
						ServiceReference<InfoItemProvider> serviceReference,
						Emitter<String> emitter) {

						InfoItemProvider infoItemProvider =
							bundleContext.getService(serviceReference);

						String className = infoItemProvider.getItemClassName();

						if (className == null) {
							className = GenericsUtil.getItemClassName(
								infoItemProvider);
						}

						emitter.emit(className);
					}

				});
	}

	private ServiceTrackerMap<String, InfoItemProvider>
		_infoItemProviderServiceTrackerMap;

}