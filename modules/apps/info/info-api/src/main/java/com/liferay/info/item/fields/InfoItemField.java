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

import com.liferay.petra.lang.HashUtil;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
public class InfoItemField {

	public InfoItemField(Map<Locale, String> labels, String name) {
		_labels = labels;
		_name = name;
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
			Objects.equals(_name, infoDisplayField._name)) {

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

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _labels);

		return HashUtil.hash(hash, _name);
	}

	private final Map<Locale, String> _labels;
	private final String _name;

}