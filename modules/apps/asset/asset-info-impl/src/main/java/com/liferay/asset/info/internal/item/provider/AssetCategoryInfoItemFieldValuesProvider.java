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

package com.liferay.asset.info.internal.item.provider;

import com.liferay.asset.info.internal.item.CategoryInfoItemFields;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class AssetCategoryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<AssetCategory> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		AssetCategory assetCategory) {

		return InfoItemFieldValues.builder(
		).infoFieldValue(
			new InfoFieldValue<>(
				CategoryInfoItemFields.descriptionInfoField,
				InfoLocalizedValue.<String>builder(
				).values(
					assetCategory.getDescriptionMap()
				).build())
		).infoFieldValue(
			new InfoFieldValue<>(
				CategoryInfoItemFields.titleInfoField,
				InfoLocalizedValue.<String>builder(
				).values(
					assetCategory.getTitleMap()
				).build())
		).infoFieldValue(
			new InfoFieldValue<>(
				CategoryInfoItemFields.vocabularyInfoField,
				() -> _getAssetVocabularyTitle(assetCategory))
		).infoFieldValues(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				AssetCategory.class.getName(), assetCategory)
		).infoItemReference(
			new InfoItemReference(
				AssetCategory.class.getName(), assetCategory.getCategoryId())
		).build();
	}

	private InfoLocalizedValue<String> _getAssetVocabularyTitle(
		AssetCategory assetCategory) {

		try {
			AssetVocabulary assetVocabulary =
				_assetVocabularyLocalService.getVocabulary(
					assetCategory.getVocabularyId());

			return InfoLocalizedValue.<String>builder(
			).values(
				assetVocabulary.getTitleMap()
			).build();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Unexpected exception retrieving asset vocabulary",
				portalException);
		}
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

}