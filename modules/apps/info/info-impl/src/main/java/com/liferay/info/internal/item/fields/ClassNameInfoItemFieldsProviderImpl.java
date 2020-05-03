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

package com.liferay.info.internal.item.fields;

import com.liferay.info.fields.InfoFieldSetEntry;
import com.liferay.info.fields.reader.InfoItemFieldReader;
import com.liferay.info.fields.reader.InfoItemFieldReaderTracker;
import com.liferay.info.item.fields.ClassNameInfoItemFieldsProvider;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = ClassNameInfoItemFieldsProvider.class)
public class ClassNameInfoItemFieldsProviderImpl
	implements ClassNameInfoItemFieldsProvider {

	@Override
	public List<InfoFieldSetEntry> getFields(String className) {
		List<InfoFieldSetEntry> infoItemFields = new ArrayList<>();

		List<InfoItemFieldReader> infoItemFieldReaders =
			_infoItemFieldReaderTracker.getInfoItemFieldReaders(className);

		for (InfoItemFieldReader infoItemFieldReader : infoItemFieldReaders) {
			infoItemFields.add(infoItemFieldReader.getField());
		}

		return infoItemFields;
	}

	@Reference
	private InfoItemFieldReaderTracker _infoItemFieldReaderTracker;

}