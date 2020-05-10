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

package com.liferay.info.internal.display.contributor;

import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorFieldType;
import com.liferay.info.fields.InfoField;
import com.liferay.info.fields.InfoFieldValue;
import com.liferay.info.fields.InfoForm;
import com.liferay.info.fields.InfoFormValues;
import com.liferay.info.fields.type.ImageInfoFieldType;
import com.liferay.info.fields.type.InfoFieldType;
import com.liferay.info.fields.type.TextInfoFieldType;
import com.liferay.info.fields.type.URLInfoFieldType;
import com.liferay.info.internal.util.GenericsUtil;
import com.liferay.info.item.NoSuchClassTypeException;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.LocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class InfoDisplayContributorInfoItemFormProviderWrapper
	implements InfoItemFormProvider {

	public InfoDisplayContributorInfoItemFormProviderWrapper(
		InfoDisplayContributor infoDisplayContributor) {

		_infoDisplayContributor = infoDisplayContributor;
	}

	@Override
	public InfoForm getInfoForm() {
		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		try {
			Set<InfoDisplayField> infoDisplayFields =
				_infoDisplayContributor.getInfoDisplayFields(0, locale);

			return _convertToInfoForm(infoDisplayFields);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public InfoForm getInfoForm(long classTypeId)
		throws NoSuchClassTypeException {

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		try {
			return _convertToInfoForm(
				_infoDisplayContributor.getInfoDisplayFields(
					classTypeId, locale));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Error retrieving fields for classTypeId: " + classTypeId,
				portalException);
		}
	}

	@Override
	public InfoForm getInfoForm(Object infoItemObject) {
		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		try {
			return _convertToInfoForm(
				_infoDisplayContributor.getInfoDisplayFields(
					infoItemObject, locale));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public InfoFormValues getInfoFormValues(Object infoItemObject) {
		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		try {
			return _convertToInfoFormValues(
				_infoDisplayContributor.getInfoDisplayFieldsValues(
					infoItemObject, locale));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public String getItemClassName() {
		return GenericsUtil.getItemClassName(_infoDisplayContributor);
	}

	private InfoForm _convertToInfoForm(
		Set<InfoDisplayField> infoDisplayFields) {

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		InfoForm infoForm = new InfoForm("fields");

		for (InfoDisplayField infoDisplayField : infoDisplayFields) {
			InfoFieldType type = _getInfoFieldTypeType(
				infoDisplayField.getType());

			LocalizedValue fieldLabel = LocalizedValue.builder(
			).addValue(
				locale, infoDisplayField.getLabel()
			).build();

			InfoField infoField = new InfoField(
				fieldLabel, infoDisplayField.getKey(), type);

			infoForm.add(infoField);
		}

		return infoForm;
	}

	private InfoFormValues _convertToInfoFormValues(
		Map<String, Object> infoDisplayFieldsValues) {

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		InfoFormValues infoFormValues = new InfoFormValues();

		for (Map.Entry<String, Object> entry :
				infoDisplayFieldsValues.entrySet()) {

			String fieldName = entry.getKey();

			LocalizedValue<String> fieldLabel = LocalizedValue.builder(
			).addValue(
				locale, fieldName
			).build();

			InfoField infoField = new InfoField(
				fieldLabel, fieldName, TextInfoFieldType.INSTANCE);

			InfoFieldValue infoFormValue = new InfoFieldValue(
				infoField, entry.getValue());

			infoFormValues.add(infoFormValue);
		}

		return infoFormValues;
	}

	private InfoFieldType _getInfoFieldTypeType(String infoDisplayFieldType) {
		if (infoDisplayFieldType.equals(
				InfoDisplayContributorFieldType.IMAGE)) {

			return ImageInfoFieldType.INSTANCE;
		}
		else if (infoDisplayFieldType.equals(
					InfoDisplayContributorFieldType.URL)) {

			return URLInfoFieldType.INSTANCE;
		}

		return TextInfoFieldType.INSTANCE;
	}

	private final InfoDisplayContributor _infoDisplayContributor;

}