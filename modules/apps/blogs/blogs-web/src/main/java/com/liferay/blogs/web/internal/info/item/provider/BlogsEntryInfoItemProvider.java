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

package com.liferay.blogs.web.internal.info.item.provider;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.info.item.NoSuchInfoItemException;
import com.liferay.info.item.provider.InfoItemProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemProvider.class)
public class BlogsEntryInfoItemProvider
	implements InfoItemProvider<BlogsEntry> {

	@Override
	public BlogsEntry getInfoItem(long classPK) throws NoSuchInfoItemException {
		BlogsEntry blogsEntry = _blogsEntryLocalService.fetchBlogsEntry(
			classPK);

		if ((blogsEntry == null) || blogsEntry.isDraft() ||
			blogsEntry.isInTrash()) {

			throw new NoSuchInfoItemException(
				"Unable to find blogs entry with entryId: " + classPK);
		}

		return blogsEntry;
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

}