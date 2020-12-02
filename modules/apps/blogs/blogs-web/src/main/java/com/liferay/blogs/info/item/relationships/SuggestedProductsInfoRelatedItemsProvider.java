package com.liferay.blogs.info.item.relationships;
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

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.item.relationships.InfoRelatedItemsProvider;
import com.liferay.info.list.provider.InfoListProviderContext;

import java.util.List;
import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public class SuggestedProductsInfoRelatedItemsProvider
	implements InfoRelatedItemsProvider<BlogsEntry, BlogsEntry> {

	@Override
	public List<BlogsEntry> getRelatedItems(
		BlogsEntry sourceItem, InfoListProviderContext infoListProviderContext) {

		return null;
	}

	@Override
	public String getLabel(Locale locale) {
		return "Suggested Entries";
	}
}
