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

package com.liferay.info.item.fields;

import com.liferay.info.item.fields.type.InfoItemFieldType;
import com.liferay.petra.lang.HashUtil;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
public class InfoItemField {

	public InfoItemField(
		Map<Locale, String> labels, String name, InfoItemFieldType type) {

		_labels = labels;
		_name = name;
		_type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof InfoItemField)) {
			return false;
		}

		InfoItemField infoDisplayField = (InfoItemField)obj;

		if (Objects.equals(_labels, infoDisplayField._labels) &&
			Objects.equals(_name, infoDisplayField._name) &&
			Objects.equals(_type, infoDisplayField._type)) {

			return true;
		}

		return false;
	}

	public String getLabel(Locale locale) {
		return _labels.get(locale);
	}

	public String getName() {
		return _name;
	}

	public InfoItemFieldType getType() {
		return _type;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _labels);

		hash = HashUtil.hash(hash, _name);

		return HashUtil.hash(hash, _type);
	}

	private final Map<Locale, String> _labels;
	private final String _name;
	private final InfoItemFieldType _type;

}