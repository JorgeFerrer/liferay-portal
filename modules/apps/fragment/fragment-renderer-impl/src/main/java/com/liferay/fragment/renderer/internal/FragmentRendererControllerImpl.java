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

package com.liferay.fragment.renderer.internal;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.renderer.constants.FragmentRendererConstants;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = FragmentRendererController.class)
public class FragmentRendererControllerImpl
	implements FragmentRendererController {

	@Override
	public String render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		HttpServletResponse pipingHttpServletResponse =
			new PipingServletResponse(httpServletResponse, unsyncStringWriter);

		FragmentRenderer fragmentRenderer = _getFragmentRenderer(
			fragmentRendererContext.getFragmentEntryLink());

		fragmentRenderer.render(
			fragmentRendererContext, httpServletRequest,
			pipingHttpServletResponse);

		return unsyncStringWriter.toString();
	}

	private FragmentRenderer _getFragmentRenderer(
		FragmentEntryLink fragmentEntryLink) {

		FragmentRenderer fragmentRenderer = null;

		if (Validator.isNotNull(fragmentEntryLink.getRendererKey())) {
			fragmentRenderer = _fragmentRendererTracker.getFragmentRenderer(
				fragmentEntryLink.getRendererKey());
		}
		else {
			fragmentRenderer = _fragmentRendererTracker.getFragmentRenderer(
				FragmentRendererConstants.FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY);
		}

		return fragmentRenderer;
	}

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

}