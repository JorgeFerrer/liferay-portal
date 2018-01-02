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

package com.liferay.layout.rest.internal.util;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.layout.rest.model.WebPage;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;

import java.lang.reflect.Constructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = LayoutResourceCollectionUtil.class)
public class LayoutResourceCollectionUtil {

	public <T extends WebPage> T getLayout(Long plid, Class<T> clazz) {
		try {
			Layout layout = _layoutLocalService.getLayout(plid);

			Constructor<T> constructor = clazz.getConstructor(Layout.class);

			T webPage = constructor.newInstance(layout);

			return webPage;
		}
		catch (AuthException | PrincipalException e) {
			throw new NotAuthorizedException(e);
		}
		catch (NoSuchLayoutException nsle) {
			throw new NotFoundException("Unable to get article " + plid, nsle);
		}
		catch (PortalException | ReflectiveOperationException e) {
			throw new ServerErrorException(500, e);
		}
	}

	public <T extends WebPage> PageItems<T> getLayouts(
		Pagination pagination, Long groupId, Class clazz, String type) {

		List<T> webPages = new ArrayList<>();
		int totalCount = 0;

		try {
			List<Layout> layouts = new ArrayList<>();

			layouts.addAll(_layoutService.getLayouts(groupId, false));
			layouts.addAll(_layoutService.getLayouts(groupId, true));

			Constructor<T> constructor = clazz.getConstructor(Layout.class);

			Stream<Layout> stream = layouts.stream();

			stream = stream.filter(layout -> layout.getType().equals(type));

			layouts = stream.collect(Collectors.toList());

			totalCount = layouts.size();

			int endPosition = pagination.getEndPosition();

			if (endPosition > totalCount) {
				endPosition = totalCount;
			}

			List<Layout> layoutsSublist = layouts.subList(
				pagination.getStartPosition(), endPosition);

			stream = layoutsSublist.stream();

			webPages = stream.<List<T>>collect(
				() -> new ArrayList<>(),
				(result, layout) -> {
					try {
						result.add(constructor.newInstance(layout));
					}
					catch (ReflectiveOperationException roe) {
					}
				},
				(left, right) -> {
					left.addAll(right);
				});
		}
		catch (ReflectiveOperationException roe) {
			_log.error("Unable to get layouts", roe);
		}

		return new PageItems(webPages, totalCount);
	}

	public <T extends WebPage> T removeLayout(Long plid, Class<T> clazz) {
		try {
			Layout layout = _layoutLocalService.deleteLayout(plid);

			Constructor<T> constructor = clazz.getConstructor(Layout.class);

			T webPage = constructor.newInstance(layout);

			return webPage;
		}
		catch (AuthException | PrincipalException e) {
			throw new NotAuthorizedException(e);
		}
		catch (NoSuchLayoutException nsle) {
		}
		catch (PortalException | ReflectiveOperationException e) {
			throw new ServerErrorException(500, e);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutResourceCollectionUtil.class);

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutService _layoutService;

}