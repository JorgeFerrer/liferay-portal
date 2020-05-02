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

package com.liferay.journal.web.internal.info.item.fields.descriptor;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.dynamic.data.mapping.info.item.fields.provider.DDMStructureInfoItemFieldsProvider;
import com.liferay.dynamic.data.mapping.kernel.NoSuchStructureException;
import com.liferay.expando.info.item.fields.provider.ExpandoInfoItemFieldsProvider;
import com.liferay.info.item.NoSuchSubtypeException;
import com.liferay.info.item.fields.InfoItemFieldSet;
import com.liferay.info.item.fields.descriptor.InfoItemFieldsDescriptor;
import com.liferay.info.item.fields.descriptor.SubtypedInfoItemFieldsDescriptor;
import com.liferay.info.item.fields.provider.ClassNameInfoItemFieldsProvider;
import com.liferay.info.localized.LocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemFieldsDescriptor.class)
public class JournalArticleInfoItemFieldsDescriptor
	implements SubtypedInfoItemFieldsDescriptor<JournalArticle> {

	@Override
	public InfoItemFieldSet getFields(long ddmStructureId)
		throws NoSuchSubtypeException {

		InfoItemFieldSet infoItemFieldSet = getFieldSet();

		try {
			infoItemFieldSet.addAll(
				_ddmStructureInfoItemFieldsProvider.getInfoItemFields(
					ddmStructureId));
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw new NoSuchSubtypeException(
				noSuchStructureException.getMessage());
		}

		return infoItemFieldSet;
	}

	@Override
	public InfoItemFieldSet getFieldSet() {
		Locale locale = LocaleUtil.getDefault();
		String labelKey =
			_MODEL_RESOURCE_NAME_PREFIX + JournalArticle.class.getName();

		LocalizedValue<String> label = LocalizedValue.builder(
		).addValue(
			locale, LanguageUtil.get(locale, labelKey)
		).build();

		InfoItemFieldSet infoItemFieldSet = new InfoItemFieldSet(
			label, JournalArticle.class.getName());

		infoItemFieldSet.addAll(
			_classNameInfoItemFieldsProvider.getFields(
				AssetEntry.class.getName()));
		infoItemFieldSet.addAll(
			_classNameInfoItemFieldsProvider.getFields(
				JournalArticle.class.getName()));
		infoItemFieldSet.addAll(
			_expandoInfoItemFieldsProvider.getFields(
				JournalArticle.class.getName()));

		return infoItemFieldSet;
	}

	private static final String _MODEL_RESOURCE_NAME_PREFIX = "model.resource.";

	@Reference
	private ClassNameInfoItemFieldsProvider _classNameInfoItemFieldsProvider;

	@Reference
	private DDMStructureInfoItemFieldsProvider
		_ddmStructureInfoItemFieldsProvider;

	@Reference
	private ExpandoInfoItemFieldsProvider _expandoInfoItemFieldsProvider;

}