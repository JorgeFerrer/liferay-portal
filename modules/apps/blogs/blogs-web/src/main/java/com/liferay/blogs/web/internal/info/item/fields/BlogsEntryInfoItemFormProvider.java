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

import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldsProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldsProvider;
import com.liferay.info.fields.InfoFieldValue;
import com.liferay.info.fields.InfoForm;
import com.liferay.info.fields.InfoFormValues;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.NoSuchInfoItemException;
import com.liferay.info.item.fields.ClassNameInfoItemFieldsProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
@Component(service = InfoItemFormProvider.class)
public class BlogsEntryInfoItemFormProvider
	implements InfoItemFormProvider<BlogsEntry> {

	@Override
	public InfoFieldValue getInfoFieldValue(
		BlogsEntry blogsEntry, String fieldName) {

		return null;
	}

	@Override
	public InfoForm getInfoForm() {
		InfoForm infoForm = new InfoForm(BlogsEntry.class.getName());

		infoForm.addAll(
			_classNameInfoItemFieldsProvider.getFields(
				BlogsEntry.class.getName()));

		infoForm.addAll(
			_assetEntryInfoItemFieldsProvider.getFields(
				AssetEntry.class.getName()));

		infoForm.addAll(
			_expandoInfoItemFieldsProvider.getFields(
				BlogsEntry.class.getName()));

		return infoForm;
	}

	@Override
	public InfoFormValues getInfoFormValues(BlogsEntry blogsEntry) {
		InfoFormValues infoFormValues = new InfoFormValues();

		infoFormValues.setInfoItemClassPKReference(
			new InfoItemClassPKReference(
				BlogsEntry.class.getName(), blogsEntry.getEntryId()));

		try {
			infoFormValues.addAll(
				_assetEntryInfoItemFieldsProvider.getFieldValues(
					BlogsEntry.class.getName(), blogsEntry.getEntryId()));
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			throw new RuntimeException(
				"Unexpected exception. This should never occur",
				noSuchInfoItemException);
		}

		infoFormValues.addAll(
			_expandoInfoItemFieldsProvider.getFieldValues(
				BlogsEntry.class.getName(), blogsEntry));
		infoFormValues.addAll(
			_classNameInfoItemFieldsProvider.getFieldValues(
				BlogsEntry.class.getName(), blogsEntry));

		return infoFormValues;
	}

	@Reference
	private AssetEntryInfoItemFieldsProvider _assetEntryInfoItemFieldsProvider;

	@Reference
	private ClassNameInfoItemFieldsProvider _classNameInfoItemFieldsProvider;

	@Reference
	private ExpandoInfoItemFieldsProvider _expandoInfoItemFieldsProvider;

}