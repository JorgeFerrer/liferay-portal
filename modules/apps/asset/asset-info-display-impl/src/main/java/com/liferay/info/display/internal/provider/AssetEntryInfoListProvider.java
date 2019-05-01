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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.provider.InfoListProvider;
import com.liferay.info.provider.InfoListProviderContext;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.model.Company;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoListProvider.class)
public class AssetEntryInfoListProvider implements InfoListProvider<AssetEntry> {

	@Override
	public List<AssetEntry> getInfoList(
		InfoListProviderContext infoListProviderContext) {

		return _assetEntryLocalService.getTopViewedEntries(
			new String[0], false, 0, 20);
	}

	@Override
	public List<AssetEntry> getInfoList(
		InfoListProviderContext infoListProviderContext, Pagination pagination,
		Sort sort) {

		return _assetEntryLocalService.getTopViewedEntries(
			new String[0], !sort.isReverse(), pagination.getStart(),
			pagination.getEnd());
	}

	@Override
	public int getInfoListCount(
		InfoListProviderContext infoListProviderContext) {

		Company company = infoListProviderContext.getCompany();

		return _assetEntryLocalService.getCompanyEntriesCount(
			company.getCompanyId());
	}

	@Override
	public String getLabel(Locale locale) {
		return null;
	}

	@Reference
	AssetEntryLocalService _assetEntryLocalService;
}
