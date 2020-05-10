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

package com.liferay.info.fields;

import com.liferay.info.accessor.InfoAccessor;
import com.liferay.info.localized.LocalizedValue;

import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public class InfoFieldValue<T> {

	public InfoFieldValue(InfoField infoField, T value) {
		_infoField = infoField;
		_value = value;
	}

	public InfoField getInfoField() {
		return _infoField;
	}

	public T getValue(Locale locale) {
		T value = null;

		if (_value instanceof InfoAccessor) {
			InfoAccessor<T> infoAccessor = (InfoAccessor)value;

			return infoAccessor.getValue();
		}
		else if (_value instanceof LocalizedValue) {
			return ((LocalizedValue<T>)_value).getValue(locale);
		}

		return value;
	}

	private final InfoField _infoField;
	private final Object _value;

}