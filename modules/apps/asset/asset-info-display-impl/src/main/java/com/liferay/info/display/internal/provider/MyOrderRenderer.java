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

import com.liferay.info.renderer.InfoItemRenderer;
import com.liferay.portal.kernel.util.StringBundler;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoItemRenderer.class)
public class MyOrderRenderer implements InfoItemRenderer<MyOrder> {
	@Override
	public void render(
		MyOrder myOrder, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		StringBundler sb = new StringBundler(3);

		sb.append("<ul>");
		sb.append("<li>By: " + myOrder.getBy());
		sb.append("<li>When: " + myOrder.getWhen());
		sb.append("<li>Items: " + myOrder.getItems());
		sb.append("</ul>");

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.write(sb.toString());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

}
