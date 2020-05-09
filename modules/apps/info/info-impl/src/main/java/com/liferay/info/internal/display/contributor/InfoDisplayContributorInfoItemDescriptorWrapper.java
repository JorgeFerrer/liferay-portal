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
import com.liferay.info.fields.InfoFieldSet;
import com.liferay.info.fields.type.ImageInfoFieldType;
import com.liferay.info.fields.type.InfoFieldType;
import com.liferay.info.fields.type.TextInfoFieldType;
import com.liferay.info.fields.type.URLInfoFieldType;
import com.liferay.info.internal.util.GenericsUtil;
import com.liferay.info.item.NoSuchSubtypeException;
import com.liferay.info.item.descriptor.InfoItemDescriptor;
import com.liferay.info.localized.LocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.util.Locale;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class InfoDisplayContributorInfoItemDescriptorWrapper
	implements InfoItemDescriptor {

	public InfoDisplayContributorInfoItemDescriptorWrapper(
		InfoDisplayContributor infoDisplayContributor) {

		_infoDisplayContributor = infoDisplayContributor;
	}

	@Override
	public InfoFieldSet getInfoFieldSet() {
		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		try {
			Set<InfoDisplayField> infoDisplayFields =
				_infoDisplayContributor.getInfoDisplayFields(0, locale);

			return _convertToInfoFieldSet(infoDisplayFields);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public InfoFieldSet getInfoFieldSet(long classTypeId)
		throws NoSuchSubtypeException {

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		try {
			return _convertToInfoFieldSet(
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
	public InfoFieldSet getInfoFieldSet(Object infoItemObject) {
		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		try {
			return _convertToInfoFieldSet(
				_infoDisplayContributor.getInfoDisplayFields(
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

	private InfoFieldSet _convertToInfoFieldSet(
		Set<InfoDisplayField> infoDisplayFields) {

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		LocalizedValue fieldSetLabel = LocalizedValue.builder(
		).addValue(
			locale, "fields"
		).build();

		InfoFieldSet infoFieldSet = new InfoFieldSet(fieldSetLabel, "fields");

		for (InfoDisplayField infoDisplayField : infoDisplayFields) {
			InfoFieldType type = _getInfoFieldTypeType(
				infoDisplayField.getType());

			LocalizedValue fieldLabel = LocalizedValue.builder(
			).addValue(
				locale, infoDisplayField.getLabel()
			).build();

			InfoField infoField = new InfoField(
				fieldLabel, infoDisplayField.getKey(), type);

			infoFieldSet.add(infoField);
		}

		return infoFieldSet;
	}

	private InfoFieldType _getInfoFieldTypeType(String infoDisplayFieldType) {
		if (infoDisplayFieldType.equals(
				InfoDisplayContributorFieldType.IMAGE)) {

			return new ImageInfoFieldType();
		}
		else if (infoDisplayFieldType.equals(
					InfoDisplayContributorFieldType.URL)) {

			return new URLInfoFieldType();
		}

		return new TextInfoFieldType();
	}

	private final InfoDisplayContributor _infoDisplayContributor;

}