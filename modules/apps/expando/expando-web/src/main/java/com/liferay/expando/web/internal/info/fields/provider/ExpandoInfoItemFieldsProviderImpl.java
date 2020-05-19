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

package com.liferay.expando.web.internal.info.fields.provider;

import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldsProvider;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.info.fields.InfoFieldSet;
import com.liferay.info.fields.InfoFieldValue;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = ExpandoInfoItemFieldsProvider.class)
public class ExpandoInfoItemFieldsProviderImpl
	implements ExpandoInfoItemFieldsProvider {

	@Override
	public InfoFieldSet getInfoFieldSet(String itemClassName) {
		InfoFieldSet infoFieldSet = new InfoFieldSet(
			InfoLocalizedValue.localize(getClass(), "custom-fields"),
			"expando");

		for (ExpandoInfoItemFieldReader expandoInfoItemFieldReader :
				_getExpandoFieldReaders(itemClassName)) {

			infoFieldSet.add(expandoInfoItemFieldReader.getField());
		}

		return infoFieldSet;
	}

	@Override
	public List<InfoFieldValue<Object>> getInfoFieldValues(
		String itemClassName, Object itemObject) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		for (ExpandoInfoItemFieldReader expandoInfoItemFieldReader :
				_getExpandoFieldReaders(itemClassName)) {

			InfoFieldValue<Object> infoFieldValue = new InfoFieldValue<>(
				expandoInfoItemFieldReader.getField(),
				expandoInfoItemFieldReader.getValue(itemObject));

			infoFieldValues.add(infoFieldValue);
		}

		return infoFieldValues;
	}

	private List<ExpandoInfoItemFieldReader> _getExpandoFieldReaders(
		String itemClassName) {

		List<ExpandoInfoItemFieldReader> expandoInfoItemFieldReaders =
			new ArrayList<>();

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			CompanyThreadLocal.getCompanyId(), itemClassName, 0L);

		Enumeration<String> attributeNames = expandoBridge.getAttributeNames();

		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();

			expandoInfoItemFieldReaders.add(
				new ExpandoInfoItemFieldReader(attributeName, expandoBridge));
		}

		return expandoInfoItemFieldReaders;
	}

}