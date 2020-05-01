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
import com.liferay.info.item.fields.InfoItemField;
import com.liferay.info.item.fields.descriptor.InfoItemFieldsDescriptor;
import com.liferay.info.item.fields.descriptor.SubtypedInfoItemFieldsDescriptor;
import com.liferay.info.item.fields.provider.ClassNameInfoItemFieldsProvider;
import com.liferay.journal.model.JournalArticle;

import java.util.ArrayList;
import java.util.List;

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
	public List<InfoItemField> getFields() {
		List<InfoItemField> infoItemFields = new ArrayList<>();

		infoItemFields.addAll(
			_classNameInfoItemFieldsProvider.getFields(
				AssetEntry.class.getName()));
		infoItemFields.addAll(
			_classNameInfoItemFieldsProvider.getFields(
				JournalArticle.class.getName()));
		infoItemFields.addAll(
			_expandoInfoItemFieldsProvider.getFields(
				JournalArticle.class.getName()));

		return infoItemFields;
	}

	@Override
	public List<InfoItemField> getFields(long ddmStructureId)
		throws NoSuchSubtypeException {

		List<InfoItemField> infoItemFields = getFields();

		try {
			infoItemFields.addAll(
				_ddmStructureInfoItemFieldsProvider.getInfoItemFields(
					ddmStructureId));
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw new NoSuchSubtypeException(
				noSuchStructureException.getMessage());
		}

		return infoItemFields;
	}

	@Reference
	private ClassNameInfoItemFieldsProvider _classNameInfoItemFieldsProvider;

	@Reference
	private DDMStructureInfoItemFieldsProvider
		_ddmStructureInfoItemFieldsProvider;

	@Reference
	private ExpandoInfoItemFieldsProvider _expandoInfoItemFieldsProvider;

}