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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.context.InvocationContextProvider;
import com.liferay.portal.kernel.util.MapUtil;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class InvocationContextProviderServiceTracker {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				StringBundler.concat(
					"(&(objectClass=",
					InvocationContextProvider.class.getName(),
					")(!(model.class.name=*))")),
			new InvocationContextProviderServiceTrackerCustomizer(
				bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<?, ?> _serviceTracker;

	private static class InvocationContextProviderServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<InvocationContextProvider<?>, ServiceRegistration<?>> {

		@Override
		public ServiceRegistration<?> addingService(
			ServiceReference<InvocationContextProvider<?>> serviceReference) {

			InvocationContextProvider<?> invocationContextProvider =
				_bundleContext.getService(serviceReference);

			Class<?> modelClass = invocationContextProvider.getModelClass();

			return _bundleContext.registerService(
				InvocationContextProvider.class, invocationContextProvider,
				MapUtil.singletonDictionary(
					"model.class.name", modelClass.getName()));
		}

		@Override
		public void modifiedService(
			ServiceReference<InvocationContextProvider<?>> serviceReference,
			ServiceRegistration<?> serviceRegistration) {
		}

		@Override
		public void removedService(
			ServiceReference<InvocationContextProvider<?>> serviceReference,
			ServiceRegistration<?> serviceRegistration) {

			serviceRegistration.unregister();

			_bundleContext.ungetService(serviceReference);
		}

		private InvocationContextProviderServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

}