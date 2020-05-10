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
import com.liferay.info.fields.InfoForm;
import com.liferay.info.item.fields.ClassNameInfoItemFieldsProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
@Component(service = InfoItemFormProvider.class)
public class BlogsEntryInfoItemFormProvider
	implements InfoItemFormProvider<BlogsEntry> {

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

	@Reference
	private AssetEntryInfoItemFieldsProvider _assetEntryInfoItemFieldsProvider;

	@Reference
	private ClassNameInfoItemFieldsProvider _classNameInfoItemFieldsProvider;

	@Reference
	private ExpandoInfoItemFieldsProvider _expandoInfoItemFieldsProvider;

}