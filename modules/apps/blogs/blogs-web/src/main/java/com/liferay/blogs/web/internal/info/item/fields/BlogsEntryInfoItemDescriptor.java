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
import com.liferay.info.fields.InfoFieldSet;
import com.liferay.info.item.descriptor.InfoItemDescriptor;
import com.liferay.info.item.fields.ClassNameInfoItemFieldsProvider;
import com.liferay.info.localized.LocalizedValue;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
@Component(service = InfoItemDescriptor.class)
public class BlogsEntryInfoItemDescriptor
	implements InfoItemDescriptor<BlogsEntry> {

	@Override
	public InfoFieldSet getFieldSet() {
		Locale locale = LocaleUtil.getDefault();
		String labelKey =
			_MODEL_RESOURCE_NAME_PREFIX + BlogsEntry.class.getName();

		LocalizedValue<String> label = LocalizedValue.builder(
		).addValue(
			locale, LanguageUtil.get(locale, labelKey)
		).build();

		InfoFieldSet infoItemFieldSet = new InfoFieldSet(
			label, BlogsEntry.class.getName());

		infoItemFieldSet.addAll(
			_classNameInfoItemFieldsProvider.getFields(
				AssetEntry.class.getName()));
		infoItemFieldSet.addAll(
			_classNameInfoItemFieldsProvider.getFields(
				BlogsEntry.class.getName()));
		infoItemFieldSet.addAll(
			_expandoInfoItemFieldsProvider.getFields(
				BlogsEntry.class.getName()));

		return infoItemFieldSet;
	}

	private static final String _MODEL_RESOURCE_NAME_PREFIX = "model.resource.";

	@Reference
	private ClassNameInfoItemFieldsProvider _classNameInfoItemFieldsProvider;

	@Reference
	private ExpandoInfoItemFieldsProvider _expandoInfoItemFieldsProvider;

}