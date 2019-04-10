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

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jorge Ferrer
 */
@Component(service = FragmentRenderer.class)
public class SampleFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey(
		FragmentRendererContext fragmentRendererContext) {

		return "dynamic-fragments";
	}

	@Override
	public String getLabel(
		FragmentRendererContext fragmentRendererContext) {

		return "Sample Fragment";
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		PrintWriter writer = httpServletResponse.getWriter();

		writer.write("<p>Hello, I'm a programmatic component</p>");
	}
	
}