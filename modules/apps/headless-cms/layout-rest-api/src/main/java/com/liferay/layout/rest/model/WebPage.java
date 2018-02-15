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

package com.liferay.layout.rest.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.language.Language;

import java.util.Date;

/**
 * Represents a web page.
 *
 * <p>
 * Conforms to the <a href="http://schema.org/WebPage">WebPage </a> type from
 * schema.org.
 * </p>
 *
 * @author Pavel Savinov
 */
@ProviderType
public interface WebPage {

	/**
	 * Returns the web page's breadcrumb.
	 *
	 * @param  language the requested language
	 * @return the web page's breadcrumb
	 */
	public String getBreadcrumb(Language language);

	/**
	 * Returns the date that the current web page was created.
	 *
	 * @return the current web page's creation date
	 */
	public Date getCreateDate();

	/**
	 * Returns the ID of the current web page's creator.
	 *
	 * @return the ID of the current web page's creator
	 */
	public long getCreatorId();

	/**
	 * Returns the web page's description.
	 *
	 * @param  language the requested language
	 * @return the web page's description
	 */
	public String getDescription(Language language);

	/**
	 * Returns the web page's friendly URL.
	 *
	 * @param  language the requested language
	 * @return the web page's friendly URL
	 */
	public String getFriendlyURL(Language language);

	/**
	 * Returns the image URL for this web page.
	 *
	 * @return the image URL for this web page.
	 */
	public String getImageURL();

	/**
	 * Returns the web page's keywords.
	 *
	 * @param  language the requested language
	 * @return the web page's keywords
	 */
	public String getKeywords(Language language);

	/**
	 * Returns the date that the current web page was modified.
	 *
	 * @return the current web page's modification date
	 */
	public Date getModifiedDate();

	/**
	 * Returns the web page's name.
	 *
	 * @param  language the requested language
	 * @return the web page's name
	 */
	public String getName(Language language);

	/**
	 * Returns the date that the current web page was published.
	 *
	 * @return the current web page's publication date
	 */
	public Date getPublishedDate();

	/**
	 * Returns the web page's title.
	 *
	 * @param  language the requested language
	 * @return the web page's friendly URL
	 */
	public String getTitle(Language language);

	/**
	 * Returns the web page's identifier.
	 *
	 * @return the web page's identifier
	 */
	public long getWebPageId();

	/**
	 * Returns the web page's web site identifier.
	 *
	 * @return the web page's web site identifier
	 */
	public long getWebSiteId();

}