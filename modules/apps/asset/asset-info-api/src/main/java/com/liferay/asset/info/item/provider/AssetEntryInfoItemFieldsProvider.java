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

package com.liferay.asset.info.item.provider;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.fields.InfoFieldSetEntry;
import com.liferay.info.fields.InfoFieldValue;
import com.liferay.info.item.NoSuchInfoItemException;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@ProviderType
public interface AssetEntryInfoItemFieldsProvider {

	public List<InfoFieldSetEntry> getInfoFieldSetEntries(String itemClassName);

	public List<InfoFieldValue<Object>> getInfoFieldValues(
		AssetEntry assetEntry);

	public List<InfoFieldValue<Object>> getInfoFieldValues(
			String itemClassName, long itemClassPK)
		throws NoSuchInfoItemException;

}