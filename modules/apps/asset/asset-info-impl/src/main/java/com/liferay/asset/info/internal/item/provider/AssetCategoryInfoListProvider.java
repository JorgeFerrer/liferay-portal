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

package com.liferay.asset.info.internal.item.provider;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.asset.util.comparator.AssetCategoryCreateDateComparator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoListProvider.class)
public class AssetCategoryInfoListProvider implements
	InfoListProvider<AssetCategory> {

	@Override
	public List<AssetCategory> getInfoList(
		InfoListProviderContext infoListProviderContext) {

		return getInfoList(infoListProviderContext, null, null);
	}

	@Override
	public List<AssetCategory> getInfoList(
		InfoListProviderContext infoListProviderContext, Pagination pagination,
		Sort sort) {

		List<AssetVocabulary> assetVocabularies =
			_getAssetVocabularies(infoListProviderContext);

		int start = 0;
		int end = 10;

		if (pagination != null) {
			start = pagination.getStart();
			end = pagination.getEnd();
		}

		// WARNING: Not honoring the 'sort' parameter yet
		OrderByComparator<AssetCategory> orderByComparator =
			new AssetCategoryCreateDateComparator(true);

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			try {
				return _assetCategoryService.getVocabularyCategories(
					assetVocabulary.getVocabularyId(), start, end,
					orderByComparator);
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}
		}

		return Collections.emptyList();
	}

	@Override
	public int getInfoListCount(
		InfoListProviderContext infoListProviderContext) {

		int count = 0;

		List<AssetVocabulary> assetVocabularies =
			_getAssetVocabularies(infoListProviderContext);

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			count += _assetCategoryService.getVocabularyCategoriesCount(
				assetVocabulary.getGroupId(),
				assetVocabulary.getVocabularyId());
		}

		return count;
	}

	@Override
	public String getLabel(Locale locale) {
		return "Site Categories";
	}

	private List<AssetVocabulary> _getAssetVocabularies(
		InfoListProviderContext infoListProviderContext) {
		long groupId;

		Optional<Group> groupOptional =
			infoListProviderContext.getGroupOptional();

		if (groupOptional.isPresent()) {
			Group group = groupOptional.get();
			groupId = group.getGroupId();
		}
		else {
			Company company = infoListProviderContext.getCompany();

			try {
				groupId = company.getGroupId();
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}
		}

		List<AssetVocabulary> groupVocabularies =
			_assetVocabularyService.getGroupVocabularies(
				groupId, AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC);

		return groupVocabularies;
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

}
