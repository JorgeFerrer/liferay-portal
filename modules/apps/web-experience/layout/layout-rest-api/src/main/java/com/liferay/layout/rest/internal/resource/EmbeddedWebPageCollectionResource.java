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

import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.layout.rest.internal.util.LayoutResourceCollectionUtil;
import com.liferay.layout.rest.model.WebPage;
import com.liferay.layout.rest.model.impl.EmbeddedWebPage;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.site.rest.model.WebSite;
import com.liferay.site.rest.service.WebSiteService;

import java.util.Optional;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true)
public class EmbeddedWebPageCollectionResource
	implements NestedCollectionResource<EmbeddedWebPage, Long, WebSite, Long> {

	@Override
	public NestedCollectionRoutes<EmbeddedWebPage> collectionRoutes(
		NestedCollectionRoutes.Builder<EmbeddedWebPage, Long> builder) {

		return builder.<Long>addGetter(
			(pagination, groupId) -> _layoutResourceCollectionUtil.getLayouts(
				pagination, groupId, EmbeddedWebPage.class,
				LayoutConstants.TYPE_EMBEDDED)
		).build();
	}

	@Override
	public String getName() {
		return "embedded-web-pages";
	}

	@Override
	public ItemRoutes<EmbeddedWebPage> itemRoutes(
		ItemRoutes.Builder<EmbeddedWebPage, Long> builder) {

		return builder.addGetter(
			webPageId -> _layoutResourceCollectionUtil.getLayout(
				webPageId, EmbeddedWebPage.class)
		).addRemover(
			webPageId -> _layoutResourceCollectionUtil.removeLayout(
				webPageId, EmbeddedWebPage.class)
		).build();
	}

	@Override
	public Representor<EmbeddedWebPage, Long> representor(
		Representor.Builder<EmbeddedWebPage, Long> builder) {

		return builder.types(
			"EmbeddedWebPage"
		).identifier(
			EmbeddedWebPage::getWebPageId
		).addBidirectionalModel(
			"webSite", "embeddedWebPages", WebSite.class,
			this::_getWebSiteOptional, WebSite::getWebSiteId
		).addLinkedModel(
			"author", User.class, this::_getUserOptional
		).addLocalizedString(
			"breadcrumb", EmbeddedWebPage::getBreadcrumb
		).addDate(
			"dateCreated", EmbeddedWebPage::getCreateDate
		).addDate(
			"dateModified", EmbeddedWebPage::getModifiedDate
		).addDate(
			"datePublished", EmbeddedWebPage::getPublishedDate
		).addLocalizedString(
			"description", EmbeddedWebPage::getDescription
		).addString(
			"image", EmbeddedWebPage::getImageURL
		).addLocalizedString(
			"keywords", EmbeddedWebPage::getKeywords
		).addLocalizedString(
			"name", EmbeddedWebPage::getName
		).addLocalizedString(
			"title", EmbeddedWebPage::getTitle
		).addLocalizedString(
			"url", EmbeddedWebPage::getFriendlyURL
		).addString(
			"embeddedUrl", EmbeddedWebPage::getEmbeddedURL
		).build();
	}

	private Optional<User> _getUserOptional(WebPage webPage) {
		try {
			return Optional.ofNullable(
				_userLocalService.getUserById(webPage.getCreatorId()));
		}
		catch (AuthException | PrincipalException e) {
			throw new NotAuthorizedException(e);
		}
		catch (NoSuchUserException nsue) {
			throw new NotFoundException(
				"Unable to get user " + webPage.getCreatorId(), nsue);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Optional<WebSite> _getWebSiteOptional(WebPage webPage) {
		return _webSiteService.getWebSite(webPage.getWebSiteId());
	}

	@Reference
	private LayoutResourceCollectionUtil _layoutResourceCollectionUtil;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WebSiteService _webSiteService;

}