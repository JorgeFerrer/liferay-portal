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

package com.liferay.layout.rest.internal.resource;

import com.liferay.apio.architect.identifier.LongIdentifier;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.sample.liferay.portal.website.WebSite;
import com.liferay.apio.architect.sample.liferay.portal.website.WebSiteService;
import com.liferay.layout.rest.internal.resource.util.LayoutCollectionUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Optional;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true)
public class LayoutCollectionResource implements
	NestedCollectionResource<Layout, LongIdentifier, WebSite, LongIdentifier> {

	@Override
	public NestedCollectionRoutes<Layout> collectionRoutes(
		NestedCollectionRoutes.Builder<Layout, LongIdentifier> builder) {

		return builder.addGetter(
			_layoutCollectionUtil::getLayouts
		).build();
	}

	@Override
	public String getName() {
		return "web-pages";
	}

	@Override
	public ItemRoutes<Layout> itemRoutes(
		ItemRoutes.Builder<Layout, LongIdentifier> builder) {

		return builder.addGetter(
			_layoutCollectionUtil::getLayout
		).addRemover(
			_layoutCollectionUtil::removeLayout
		).build();
	}

	@Override
	public Representor<Layout, LongIdentifier> representor(
		Representor.Builder<Layout, LongIdentifier> builder) {

		return builder.types(
			"WebPage"
		).identifier(
			layout -> layout::getPlid
		).addBidirectionalModel(
			"webSite", "webPages", WebSite.class, this::_getWebSiteOptional,
			WebSite::getWebSiteLongIdentifier
		).addLinkedModel(
			"author", User.class, this::_getUserOptional
		).addLocalizedString(
			"breadcrumb",
			(layout, language) -> _layoutCollectionUtil.getLayoutBreadcrumb(
				layout, language.getPreferredLocale())
		).addDate(
			"dateCreated", Layout::getCreateDate
		).addDate(
			"dateModified", Layout::getModifiedDate
		).addDate(
			"datePublished", Layout::getLastPublishDate
		).addLocalizedString(
			"description",
			(layout, language) -> layout.getDescription(
				language.getPreferredLocale())
		).addString(
			"image", _layoutCollectionUtil::getLayoutImageUrl
		).addLocalizedString(
			"keywords",
			(layout, language) -> layout.getKeywords(
				language.getPreferredLocale())
		).addLocalizedString(
			"name",
			(layout, language) -> layout.getName(language.getPreferredLocale())
		).addString(
			"pageType", Layout::getType
		).addLocalizedString(
			"title",
			(layout, language) -> layout.getTitle(language.getPreferredLocale())
		).addLocalizedString(
			"url",
			(layout, language) -> layout.getFriendlyURL(
				language.getPreferredLocale())
		).build();
	}

	private Optional<User> _getUserOptional(Layout layout) {
		try {
			return Optional.ofNullable(
				_userLocalService.getUserById(layout.getUserId()));
		}
		catch (NoSuchUserException | PrincipalException e) {
			throw new NotFoundException(
				"Unable to get user " + layout.getUserId(), e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Optional<WebSite> _getWebSiteOptional(Layout layout) {
		return _webSiteService.getWebSite(layout.getGroupId());
	}

	@Reference
	private LayoutCollectionUtil _layoutCollectionUtil;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WebSiteService _webSiteService;

}