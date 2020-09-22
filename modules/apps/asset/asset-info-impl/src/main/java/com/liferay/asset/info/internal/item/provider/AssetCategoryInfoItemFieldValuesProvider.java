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

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.info.internal.item.CategoryInfoItemFields;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.ArrayList;
import java.util.List;

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
		).infoFieldValues(
			_getDisplayPageUrlInfoFieldValues(assetCategory)
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

	private String _getDisplayPageURL(AssetCategory assetCategory)
		throws PortalException {

		return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
			AssetCategory.class.getName(), assetCategory.getCategoryId(),
			_getThemeDisplay());
	}

	private List<InfoFieldValue<Object>> _getDisplayPageUrlInfoFieldValues(
		AssetCategory assetCategory) {

		List<InfoFieldValue<Object>> assetCategoryFieldValues =
			new ArrayList<>();

		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay != null) {
			try {
				assetCategoryFieldValues.add(
					new InfoFieldValue<>(
						CategoryInfoItemFields.displayPageUrlInfoField,
						_getDisplayPageURL(assetCategory)));
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}
		}

		return assetCategoryFieldValues;
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

}