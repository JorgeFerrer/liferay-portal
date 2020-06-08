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

import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormProviderTracker;
import com.liferay.info.item.provider.InfoItemServiceTracker;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemFormProviderTracker.class)
public class InfoItemFormProviderTrackerImpl
	implements InfoItemFormProviderTracker {

	@Override
	public List<String> getInfoItemClassNames() {
		return _infoItemServiceTracker.getInfoItemClassNames(
			InfoItemFormProvider.class);
	}

	@Override
	public InfoItemFormProvider<?> getInfoItemFormProvider(
		String itemClassName) {

		return _infoItemServiceTracker.getInfoItemService(
			InfoItemFormProvider.class, itemClassName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}