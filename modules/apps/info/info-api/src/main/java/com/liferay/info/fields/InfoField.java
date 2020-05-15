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

import com.liferay.info.fields.type.InfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
public class InfoField implements InfoFieldSetEntry {

	public InfoField(
		InfoLocalizedValue<String> labelInfoLocalizedValue, String name,
		InfoFieldType type) {

		_labelInfoLocalizedValue = labelInfoLocalizedValue;
		_name = name;
		_type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof InfoField)) {
			return false;
		}

		InfoField infoDisplayField = (InfoField)obj;

		if (Objects.equals(
				_labelInfoLocalizedValue,
				infoDisplayField._labelInfoLocalizedValue) &&
			Objects.equals(_name, infoDisplayField._name) &&
			Objects.equals(_type, infoDisplayField._type)) {

			return true;
		}

		return false;
	}

	@Override
	public InfoLocalizedValue getLabelInfoLocalizedValue() {
		return _labelInfoLocalizedValue;
	}

	@Override
	public String getLabelInfoLocalizedValue(Locale locale) {
		return _labelInfoLocalizedValue.getValue(locale);
	}

	@Override
	public String getName() {
		return _name;
	}

	public InfoFieldType getType() {
		return _type;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _labelInfoLocalizedValue);

		hash = HashUtil.hash(hash, _name);

		return HashUtil.hash(hash, _type);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{name: ");
		sb.append(_name);
		sb.append(", type: ");
		sb.append(_type.getName());
		sb.append("}");

		return sb.toString();
	}

	private final InfoLocalizedValue<String> _labelInfoLocalizedValue;
	private final String _name;
	private final InfoFieldType _type;

}