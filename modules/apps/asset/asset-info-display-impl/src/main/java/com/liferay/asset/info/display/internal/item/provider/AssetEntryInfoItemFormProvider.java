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

package com.liferay.asset.info.display.internal.item.provider;

import com.liferay.asset.info.display.item.provider.AssetEntryInfoItemFieldsProvider;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.info.fields.InfoField;
import com.liferay.info.fields.InfoFieldSetEntry;
import com.liferay.info.fields.InfoFieldValue;
import com.liferay.info.fields.InfoForm;
import com.liferay.info.fields.InfoFormValues;
import com.liferay.info.fields.type.TextInfoFieldType;
import com.liferay.info.item.provider.InfoItemFormProvider;

import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoItemFormProvider.class)
public class AssetEntryInfoItemFormProvider
	implements InfoItemFormProvider<AssetEntry> {
	@Override
	public InfoForm getInfoForm() {
		InfoForm infoForm = new InfoForm(AssetEntry.class.getName());

		infoForm.addAll(
			_assetEntryInfoItemFieldsProvider.getFields(
				AssetEntry.class.getName()));
		infoForm.addAll(_getAssetEntryFields());

		return infoForm;
	}

	@Override
	public InfoFormValues getInfoFormValues(AssetEntry assetEntry) {
		return new InfoFormValues()
			.addAll(
				_assetEntryInfoItemFieldsProvider.getFieldValues(assetEntry))
			.addAll(_getAssetEntryFieldValues(assetEntry));
	}

	private List<InfoFieldSetEntry> _getAssetEntryFields() {
		List<InfoFieldSetEntry> assetEntryFields = new ArrayList<>();

		assetEntryFields.add(_titleInfoField);

		assetEntryFields.add(_descriptionInfoField);

		assetEntryFields.add(_summaryInfoField);

		assetEntryFields.add(_userNameInfoField);

		assetEntryFields.add(_createDateInfoField);

		assetEntryFields.add(_expirationDateInfoField);

		assetEntryFields.add(_viewCountInfoField);

		assetEntryFields.add(_urlInfoField);

		assetEntryFields.add(_categoriesInfoField);

		assetEntryFields.add(_tagsInfoField);

		return assetEntryFields;
	}

	private List<InfoFieldValue<Object>> _getAssetEntryFieldValues(
		AssetEntry assetEntry) {

		List<InfoFieldValue<Object>> assetEntryFieldValues = new ArrayList<>();

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		assetEntryFieldValues.add(
			new InfoFieldValue<>(
				_titleInfoField, assetEntry.getTitle(locale)));

		assetEntryFieldValues.add(
			new InfoFieldValue<>(
				_descriptionInfoField, assetEntry.getDescription(locale)));

		assetEntryFieldValues.add(
			new InfoFieldValue<>(
				_summaryInfoField, assetEntry.getSummary(locale)));

		assetEntryFieldValues.add(
			new InfoFieldValue<>(
				_userNameInfoField, assetEntry.getUserName()));

		assetEntryFieldValues.add(
			new InfoFieldValue<>(
				_createDateInfoField,
				_getDateValue(assetEntry.getCreateDate())));

		assetEntryFieldValues.add(
			new InfoFieldValue<>(
				_expirationDateInfoField,
				_getDateValue(assetEntry.getExpirationDate())));

		assetEntryFieldValues.add(
			new InfoFieldValue<>(
				_viewCountInfoField, assetEntry.getViewCount()));

		assetEntryFieldValues.add(
			new InfoFieldValue<>(
				_urlInfoField, assetEntry.getUserName()));

		assetEntryFieldValues.add(
			new InfoFieldValue<>(
				_categoriesInfoField, _getCategoryNames(assetEntry)));

		assetEntryFieldValues.add(
			new InfoFieldValue<>(
				_tagsInfoField,
				ListUtil.toString(
					assetEntry.getTags(), AssetTag.NAME_ACCESSOR)));

		return assetEntryFieldValues;
	}

	private String _getCategoryNames(AssetEntry assetEntry) {
		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		return ListUtil.toString(
			assetEntry.getCategories(),
			new Accessor<AssetCategory, String>() {

				@Override
				public String get(AssetCategory assetCategory) {
					String title = assetCategory.getTitle(locale);

					if (Validator.isNull(title)) {
						return assetCategory.getName();
					}

					return title;
				}

				@Override
				public Class<String> getAttributeClass() {
					return String.class;
				}

				@Override
				public Class<AssetCategory> getTypeClass() {
					return AssetCategory.class;
				}

			});
	}

	private String _getDateValue(Date date) {
		if (date == null) {
			return StringPool.BLANK;
		}

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			locale);

		return dateFormatDateTime.format(date);
	}

	@Reference
	private AssetEntryInfoItemFieldsProvider _assetEntryInfoItemFieldsProvider;

	private final InfoField _categoriesInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "categories"), "categories",
		TextInfoFieldType.INSTANCE);

	private final InfoField _descriptionInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "description"), "description",
		TextInfoFieldType.INSTANCE);
	private final InfoField _createDateInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "create-date"), "createDate",
		TextInfoFieldType.INSTANCE);
	private final InfoField _expirationDateInfoField = new InfoField(
		InfoLocalizedValue.localize(
			getClass(), "expiration-date"), "expirationDate",
		TextInfoFieldType.INSTANCE);
	private final InfoField _summaryInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "summary"), "summary",
		TextInfoFieldType.INSTANCE);
	private final InfoField _titleInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "title"), "title",
		TextInfoFieldType.INSTANCE);
	private final InfoField _tagsInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "tags"), "tagNames",
		TextInfoFieldType.INSTANCE);
	private final InfoField _userNameInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "user-name"), "userName",
		TextInfoFieldType.INSTANCE);
	private final InfoField _urlInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "url"), "url",
		TextInfoFieldType.INSTANCE);
	private final InfoField _viewCountInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "view-count"), "viewName",
		TextInfoFieldType.INSTANCE);

}