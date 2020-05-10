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
import com.liferay.info.fields.InfoFieldValue;
import com.liferay.info.item.fields.ClassNameInfoItemFieldsProvider;
import com.liferay.info.item.fields.reader.InfoItemFieldReader;
import com.liferay.info.item.fields.reader.InfoItemFieldReaderTracker;

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

	@Override
	public List<InfoFieldValue<Object>> getFieldValues(
		String className, Object itemObject) {

		List<InfoFieldValue<Object>> fieldValues = new ArrayList<>();

		List<InfoItemFieldReader> infoItemFieldReaders =
			_infoItemFieldReaderTracker.getInfoItemFieldReaders(className);

		for (InfoItemFieldReader infoItemFieldReader : infoItemFieldReaders) {

			/*
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if ((serviceContext != null) &&
				!Objects.equals(
					InfoDisplayContributorFieldType.URL,
					infoDisplayContributorFieldType) &&
				(fieldValue instanceof String)) {

				fieldValue = SanitizerUtil.sanitize(
					serviceContext.getCompanyId(),
					serviceContext.getScopeGroupId(),
					serviceContext.getUserId(), className, 0,
					ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
					(String)fieldValue, null);
			}
			*/

			fieldValues.add(
				new InfoFieldValue<>(
					infoItemFieldReader.getField(),
					infoItemFieldReader.getValue(itemObject)));
		}

		return fieldValues;
	}

	@Reference
	private InfoItemFieldReaderTracker _infoItemFieldReaderTracker;

}