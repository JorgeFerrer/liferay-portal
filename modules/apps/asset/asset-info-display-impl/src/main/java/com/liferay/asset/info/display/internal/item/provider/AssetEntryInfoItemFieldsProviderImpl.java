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
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.exception.NoSuchEntryException;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.fields.InfoFieldSetEntry;
import com.liferay.info.fields.InfoFieldValue;
import com.liferay.info.item.NoSuchInfoItemException;
import com.liferay.info.item.fields.ClassNameInfoItemFieldsProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = AssetEntryInfoItemFieldsProvider.class)
public class AssetEntryInfoItemFieldsProviderImpl
	implements AssetEntryInfoItemFieldsProvider {

	@Override
	public List<InfoFieldSetEntry> getFields(String className) {
		List<InfoFieldSetEntry> fields = new ArrayList<>();

		fields.addAll(
			_classNameInfoItemFieldsProvider.getFields(
				AssetEntry.class.getName()));

		return fields;
	}

	@Override
	public List<InfoFieldValue<Object>> getFieldValues(AssetEntry assetEntry) {
		List<InfoFieldValue<Object>> fieldValues = new ArrayList<>();

		fieldValues.addAll(
			_classNameInfoItemFieldsProvider.getFieldValues(
				AssetEntry.class.getName(), assetEntry));

		return fieldValues;
	}

	@Override
	public List<InfoFieldValue<Object>> getFieldValues(
			String className, long classPK)
		throws NoSuchInfoItemException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		try {
			AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
				className, classPK);

			return getFieldValues(assetEntry);
		}
		catch (NoSuchEntryException noSuchEntryException) {
			throw new NoSuchInfoItemException(
				StringBundler.concat(
					"Unable to find item with className=", className, " and ",
					"classPK=", classPK),
				noSuchEntryException);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Reference
	private ClassNameInfoItemFieldsProvider _classNameInfoItemFieldsProvider;

}