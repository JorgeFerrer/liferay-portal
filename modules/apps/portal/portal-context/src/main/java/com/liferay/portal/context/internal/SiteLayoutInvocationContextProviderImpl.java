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

import com.liferay.portal.kernel.context.SiteLayoutInvocationContextProvider;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = SiteLayoutInvocationContextProvider.class)
public class SiteLayoutInvocationContextProviderImpl
	implements SiteLayoutInvocationContextProvider {

	@Override
	public Layout getCurrent() {
		if (isPresent()) {
			return _layoutLocalService.fetchLayout(getSiteLayoutId());
		}

		return null;
	}

	@Override
	public Class<Layout> getModelClass() {
		return Layout.class;
	}

	@Override
	public boolean isPresent() {
		Long layoutId = getSiteLayoutId();

		if ((layoutId == null) || (layoutId == 0)) {
			return false;
		}

		return true;
	}

	protected Long getSiteLayoutId() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return null;
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		Layout layout = themeDisplay.getLayout();

		long layoutId = layout.getLayoutId();

		if (layout.isTypeControlPanel()) {
			layoutId = themeDisplay.getRefererPlid();
		}

		return layoutId;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

}