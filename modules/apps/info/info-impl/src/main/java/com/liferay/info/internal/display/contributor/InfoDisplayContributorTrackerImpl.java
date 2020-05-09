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

package com.liferay.info.internal.display.contributor;

import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.item.descriptor.InfoItemDescriptor;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = InfoDisplayContributorTracker.class)
public class InfoDisplayContributorTrackerImpl
	implements InfoDisplayContributorTracker {

	@Override
	public InfoDisplayContributor getInfoDisplayContributor(String className) {
		return _infoDisplayContributorMap.getService(className);
	}

	@Override
	public InfoDisplayContributor getInfoDisplayContributorByURLSeparator(
		String urlSeparator) {

		return _infoDisplayContributorByURLSeparatorMap.getService(
			urlSeparator);
	}

	@Override
	public List<InfoDisplayContributor> getInfoDisplayContributors() {
		return new ArrayList(_infoDisplayContributorMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_infoDisplayContributorMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, InfoDisplayContributor.class, null,
				(serviceReference, emitter) -> {
					InfoDisplayContributor infoDisplayContributor =
						bundleContext.getService(serviceReference);

					emitter.emit(infoDisplayContributor.getClassName());
				});
		_infoDisplayContributorByURLSeparatorMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, InfoDisplayContributor.class, null,
				(serviceReference, emitter) -> {
					InfoDisplayContributor infoDisplayContributor =
						bundleContext.getService(serviceReference);

					emitter.emit(infoDisplayContributor.getInfoURLSeparator());
				});

		_infoDisplayContributorServiceTracker =
			new ServiceTracker
				<InfoDisplayContributor,
				 ServiceRegistration<InfoDisplayContributor>>(
					 bundleContext, InfoDisplayContributor.class,
					 new ServiceTrackerCustomizer
						 <InfoDisplayContributor,
						  ServiceRegistration<InfoDisplayContributor>>() {

			 			@Override
			 			public ServiceRegistration<InfoDisplayContributor>
				 			addingService(
				 				ServiceReference<InfoDisplayContributor>
					 				serviceReference) {

				 			InfoDisplayContributor infoDisplayContributor =
					 			bundleContext.getService(serviceReference);

				 			InfoItemDescriptor infoItemDescriptor =
								new InfoDisplayContributorInfoItemDescriptorWrapper(
									infoDisplayContributor);

				 			return bundleContext.registerService(
					 			InfoItemDescriptor.class, infoItemDescriptor,
					 			_getServiceReferenceProperties(
						 			serviceReference));
			 			}

			 			@Override
			 			public void modifiedService(
				 			ServiceReference<InfoDisplayContributor>
					 			serviceReference,
				 			ServiceRegistration<InfoDisplayContributor>
					 			serviceRegistration) {

				 			serviceRegistration.setProperties(
					 			_getServiceReferenceProperties(
						 			serviceReference));
			 			}

			 			@Override
			 			public void removedService(
				 			ServiceReference<InfoDisplayContributor>
					 			serviceReference,
				 			ServiceRegistration<InfoDisplayContributor>
					 			serviceRegistration) {

				 			serviceRegistration.unregister();
			 			}

					 });

		_infoDisplayContributorServiceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_infoDisplayContributorServiceTracker.close();
	}

	private Dictionary _getServiceReferenceProperties(
		ServiceReference serviceReference) {

		Dictionary dictionary = new Hashtable();

		for (String key : serviceReference.getPropertyKeys()) {
			dictionary.put(key, serviceReference.getProperty(key));
		}

		return dictionary;
	}

	private ServiceTrackerMap<String, InfoDisplayContributor>
		_infoDisplayContributorByURLSeparatorMap;
	private ServiceTrackerMap<String, InfoDisplayContributor>
		_infoDisplayContributorMap;
	private ServiceTracker
		<InfoDisplayContributor, ServiceRegistration<InfoDisplayContributor>>
			_infoDisplayContributorServiceTracker;

}