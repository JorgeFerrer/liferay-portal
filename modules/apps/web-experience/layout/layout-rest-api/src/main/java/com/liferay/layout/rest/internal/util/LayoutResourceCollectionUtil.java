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

import java.util.List;
import java.util.function.Function;
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

	public <T extends WebPage> T getLayout(
		Long plid, Function<Layout, T> function) {

		try {
			Layout layout = _layoutLocalService.getLayout(plid);

			return function.apply(layout);
		}
		catch (AuthException | PrincipalException e) {
			throw new NotAuthorizedException(e);
		}
		catch (NoSuchLayoutException nsle) {
			throw new NotFoundException("Unable to get article " + plid, nsle);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	public <T> PageItems<T> getLayouts(
		Pagination pagination, Long groupId, Function<Layout, T> function,
		String type) {

		List<Layout> privateLayouts = _layoutService.getLayouts(groupId, false);
		List<Layout> publicLayouts = _layoutService.getLayouts(groupId, true);

		Stream<Layout> stream = Stream.concat(
			privateLayouts.stream(), publicLayouts.stream());

		List<Layout> layouts = stream.filter(
			layout -> layout.getType().equals(type)
		).collect(
			Collectors.toList()
		);

		int totalCount = layouts.size();

		int endPosition = Math.min(pagination.getEndPosition(), totalCount);

		List<Layout> layoutsSublist = layouts.subList(
			pagination.getStartPosition(), endPosition);

		Stream<Layout> sublistStream = layoutsSublist.stream();

		List<T> webPages = sublistStream.map(
			function
		).collect(
			Collectors.toList()
		);

		return new PageItems<>(webPages, totalCount);
	}

	public <T extends WebPage> T removeLayout(
		Long plid, Function<Layout, T> function) {

		try {
			Layout layout = _layoutLocalService.deleteLayout(plid);

			return function.apply(layout);
		}
		catch (AuthException | PrincipalException e) {
			throw new NotAuthorizedException(e);
		}
		catch (NoSuchLayoutException nsle) {
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
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