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

package com.liferay.asset.publisher.web.internal.samples;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.provider.InfoListProvider;
import com.liferay.info.provider.InfoListProviderContext;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoListProvider.class)
public class DLFileEntriesInfoListProvider
	implements InfoListProvider<DLFileEntry> {

	@Override
	public List<DLFileEntry> getInfoList(InfoListProviderContext context) {
		Group scopeGroup = context.getScopeGroup();

		return _dlFileEntryLocalService.getGroupFileEntries(
			scopeGroup.getGroupId(), 0, 20);
	}

	@Override
	public List<DLFileEntry> getInfoList(
		InfoListProviderContext context, Pagination pagination, Sort sort) {

		return getInfoList(context);
	}

	@Override
	public int getInfoListCount(InfoListProviderContext context) {
		Group scopeGroup = context.getScopeGroup();

		return _dlFileEntryLocalService.getGroupFileEntriesCount(
			scopeGroup.getGroupId());
	}

	@Override
	public String getLabel(Locale locale) {
		return "Documents";
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

}