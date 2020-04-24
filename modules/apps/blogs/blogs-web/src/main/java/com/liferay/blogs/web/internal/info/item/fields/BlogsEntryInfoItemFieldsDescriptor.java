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

package com.liferay.blogs.web.internal.info.item.fields;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.expando.info.item.fields.provider.ExpandoInfoItemFieldsProvider;
import com.liferay.info.item.fields.InfoItemField;
import com.liferay.info.item.fields.descriptor.InfoItemFieldsDescriptor;
import com.liferay.info.item.fields.provider.ClassNameInfoItemFieldsProvider;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
@Component(service = InfoItemFieldsDescriptor.class)
public class BlogsEntryInfoItemFieldsDescriptor
	implements InfoItemFieldsDescriptor<BlogsEntry> {

	@Override
	public List<InfoItemField> getFields() {
		List<InfoItemField> infoItemFields = new ArrayList<>();

		infoItemFields.addAll(
			_classNameInfoItemFieldsProvider.getFields(
				AssetEntry.class.getName()));
		infoItemFields.addAll(
			_classNameInfoItemFieldsProvider.getFields(
				BlogsEntry.class.getName()));
		infoItemFields.addAll(
			_expandoInfoItemFieldsProvider.getFields(
				BlogsEntry.class.getName()));

		return infoItemFields;
	}

	@Reference
	private ClassNameInfoItemFieldsProvider _classNameInfoItemFieldsProvider;

	@Reference
	private ExpandoInfoItemFieldsProvider _expandoInfoItemFieldsProvider;

}