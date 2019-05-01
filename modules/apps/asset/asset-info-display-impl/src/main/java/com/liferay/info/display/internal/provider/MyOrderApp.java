/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.info.display.internal.provider;

import com.liferay.info.provider.InfoListProviderTracker;
import com.liferay.info.renderer.InfoItemRendererTracker;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
public class MyOrderApp {

	public void main() {

		_infoListProviderTracker.getInfoListProviders(MyOrder.class);

		String infoListProviderClassName = MyOrderProvider.class.getName();

		_infoListProviderTracker.getInfoListProvider(infoListProviderClassName);

		_infoItemRendererTracker.getInfoItemRenderers(MyOrder.class.getName());

		String infoItemRendererClassName = MyOrderRenderer.class.getName();

		_infoItemRendererTracker.getInfoItemRenderer(infoItemRendererClassName);
	}

	@Reference
	InfoListProviderTracker _infoListProviderTracker;

	@Reference
	InfoItemRendererTracker _infoItemRendererTracker;

}
