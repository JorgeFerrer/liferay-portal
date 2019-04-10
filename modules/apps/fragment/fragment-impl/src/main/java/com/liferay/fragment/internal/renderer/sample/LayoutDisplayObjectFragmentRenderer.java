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

package com.liferay.fragment.internal.renderer.sample;

import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.renderer.InfoItemRenderer;
import com.liferay.info.renderer.InfoItemRendererTracker;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Jorge Ferrer
 */
@Component(service = FragmentRenderer.class)
public class LayoutDisplayObjectFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey(
		FragmentRendererContext fragmentRendererContext) {

		return "Display Page";
	}

	@Override
	public String getLabel(
		FragmentRendererContext fragmentRendererContext) {

		return "Display Page Content";
	}

	@Override
	public boolean isAvailable(
		HttpServletRequest httpServletRequest) {

		Object displayObject = _getDisplayObject(httpServletRequest);

		if (displayObject == null) {
			return false;
		}

		Class<?> displayObjectClass = displayObject.getClass();

		InfoItemRenderer infoItemRenderer =
			infoItemRendererTracker.getInfoItemRenderer(
				displayObjectClass.getName());

		if (infoItemRenderer == null) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		Object displayObject = _getDisplayObject(httpServletRequest);

		if (displayObject == null) {
			return;
		}

		Class<?> displayObjectClass = displayObject.getClass();

		InfoItemRenderer infoItemRenderer =
			infoItemRendererTracker.getInfoItemRenderer(
				displayObjectClass.getName());

		if (infoItemRenderer == null) {
			return;
		}

		infoItemRenderer.render(
			displayObject, httpServletRequest, httpServletResponse);
	}

	private Object _getDisplayObject(HttpServletRequest httpServletRequest) {
		InfoDisplayObjectProvider infoDisplayObjectProvider =
			(InfoDisplayObjectProvider)httpServletRequest.getAttribute(
				AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER);

		if (infoDisplayObjectProvider == null) {
			return null;
		}

		return infoDisplayObjectProvider.getDisplayObject();
	}

	@Reference
	InfoItemRendererTracker infoItemRendererTracker;
}