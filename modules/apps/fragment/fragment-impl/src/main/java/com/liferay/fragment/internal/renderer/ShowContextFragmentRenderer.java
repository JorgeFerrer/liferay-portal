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

package com.liferay.fragment.internal.renderer;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.petra.string.StringUtil;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
@Component(service = FragmentRenderer.class)
public class ShowContextFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "sample-components";
	}

	@Override
	public String getLabel(Locale locale) {
		return "Show Context Component";
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.write("<h3>Context</h3>");
		printWriter.write("<ul>");

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		printWriter.write("<li>Added by: " + fragmentEntryLink.getUserName());
		printWriter.write("<li>Added in: " + fragmentEntryLink.getCreateDate());

		printWriter.write("<li>Locale: " + fragmentRendererContext.getLocale());
		printWriter.write("<li>Mode: " + fragmentRendererContext.getMode());
		printWriter.write("<li>PreviewClassPK: " + fragmentRendererContext.getPreviewClassPK());
		printWriter.write("<li>PreviewType: " + fragmentRendererContext.getPreviewType());
		printWriter.write("<li>Segment experiences: " + StringUtil.merge(fragmentRendererContext.getSegmentsExperienceIds(), ", "));
		printWriter.write("</ul>");
	}

}
