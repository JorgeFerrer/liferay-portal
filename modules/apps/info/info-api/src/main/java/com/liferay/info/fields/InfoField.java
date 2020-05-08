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
import com.liferay.info.localized.LocalizedValue;
import com.liferay.petra.lang.HashUtil;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
public class InfoField implements InfoFieldSetEntry {

	public InfoField(
		LocalizedValue<String> label, String name, InfoFieldType type) {

		_label = label;
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

		if (Objects.equals(_label, infoDisplayField._label) &&
			Objects.equals(_name, infoDisplayField._name) &&
			Objects.equals(_type, infoDisplayField._type)) {

			return true;
		}

		return false;
	}

	@Override
	public LocalizedValue getLabel() {
		return _label;
	}

	@Override
	public String getLabel(Locale locale) {
		return _label.getValue(locale);
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
		int hash = HashUtil.hash(0, _label);

		hash = HashUtil.hash(hash, _name);

		return HashUtil.hash(hash, _type);
	}

	private final LocalizedValue<String> _label;
	private final String _name;
	private final InfoFieldType _type;

}