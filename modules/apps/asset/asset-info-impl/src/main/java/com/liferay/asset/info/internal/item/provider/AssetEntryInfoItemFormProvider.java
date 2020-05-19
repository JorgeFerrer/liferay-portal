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

import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldsProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.fields.InfoField;
import com.liferay.info.fields.InfoFieldSetEntry;
import com.liferay.info.fields.InfoFieldValue;
import com.liferay.info.fields.InfoForm;
import com.liferay.info.fields.InfoFormValues;
import com.liferay.info.fields.type.TextInfoFieldType;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
			_assetEntryInfoItemFieldsProvider.getInfoFieldSet(
				AssetEntry.class.getName()));
		infoForm.addAll(_getAssetEntryFieldSetEntries());

		return infoForm;
	}

	@Override
	public InfoFormValues getInfoFormValues(AssetEntry assetEntry) {
		InfoFormValues infoFormValues = new InfoFormValues();

		infoFormValues.addAll(
			_assetEntryInfoItemFieldsProvider.getInfoFieldValues(assetEntry));
		infoFormValues.addAll(_getAssetEntryInfoFieldValues(assetEntry));

		return infoFormValues;
	}

	private List<InfoFieldSetEntry> _getAssetEntryFieldSetEntries() {
		List<InfoFieldSetEntry> infoFieldSetEntries = new ArrayList<>();

		infoFieldSetEntries.add(_titleInfoField);

		infoFieldSetEntries.add(_descriptionInfoField);

		infoFieldSetEntries.add(_summaryInfoField);

		infoFieldSetEntries.add(_userNameInfoField);

		infoFieldSetEntries.add(_createDateInfoField);

		infoFieldSetEntries.add(_expirationDateInfoField);

		infoFieldSetEntries.add(_viewCountInfoField);

		infoFieldSetEntries.add(_urlInfoField);

		return infoFieldSetEntries;
	}

	private List<InfoFieldValue<Object>> _getAssetEntryInfoFieldValues(
		AssetEntry assetEntry) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		infoFieldValues.add(
			new InfoFieldValue<>(_titleInfoField, assetEntry.getTitle(locale)));

		infoFieldValues.add(
			new InfoFieldValue<>(
				_descriptionInfoField, assetEntry.getDescription(locale)));

		infoFieldValues.add(
			new InfoFieldValue<>(
				_summaryInfoField, assetEntry.getSummary(locale)));

		infoFieldValues.add(
			new InfoFieldValue<>(_userNameInfoField, assetEntry.getUserName()));

		infoFieldValues.add(
			new InfoFieldValue<>(
				_createDateInfoField,
				_getDateValue(assetEntry.getCreateDate())));

		infoFieldValues.add(
			new InfoFieldValue<>(
				_expirationDateInfoField,
				_getDateValue(assetEntry.getExpirationDate())));

		infoFieldValues.add(
			new InfoFieldValue<>(
				_viewCountInfoField, assetEntry.getViewCount()));

		infoFieldValues.add(
			new InfoFieldValue<>(_urlInfoField, assetEntry.getUrl()));

		return infoFieldValues;
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

	private final InfoField _createDateInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "create-date"), "createDate",
		TextInfoFieldType.INSTANCE);
	private final InfoField _descriptionInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "description"), "description",
		TextInfoFieldType.INSTANCE);
	private final InfoField _expirationDateInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "expiration-date"),
		"expirationDate", TextInfoFieldType.INSTANCE);
	private final InfoField _summaryInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "summary"), "summary",
		TextInfoFieldType.INSTANCE);
	private final InfoField _titleInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "title"), "title",
		TextInfoFieldType.INSTANCE);
	private final InfoField _urlInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "url"), "url",
		TextInfoFieldType.INSTANCE);
	private final InfoField _userNameInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "user-name"), "userName",
		TextInfoFieldType.INSTANCE);
	private final InfoField _viewCountInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "view-count"), "viewName",
		TextInfoFieldType.INSTANCE);

}