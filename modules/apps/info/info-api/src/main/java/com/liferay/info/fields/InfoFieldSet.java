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

import com.liferay.info.localized.LocalizedValue;
import com.liferay.petra.lang.HashUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jorge Ferrer
 */
public class InfoFieldSet implements InfoFieldSetEntry {

	public InfoFieldSet(LocalizedValue<String> label, String name) {
		_label = label;
		_name = name;
	}

	public InfoFieldSet add(InfoFieldSetEntry fieldSetEntry) {
		_entries.put(fieldSetEntry.getName(), fieldSetEntry);

		return this;
	}

	public InfoFieldSet addAll(Collection<InfoFieldSetEntry> fieldSetEntries) {
		for (InfoFieldSetEntry fieldSetEntry : fieldSetEntries) {
			add(fieldSetEntry);
		}

		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof InfoField)) {
			return false;
		}

		InfoFieldSet infoItemFieldSet = (InfoFieldSet)obj;

		if (Objects.equals(_label, infoItemFieldSet._label) &&
			Objects.equals(_name, infoItemFieldSet._name)) {

			return true;
		}

		return false;
	}

	public List<InfoField> getAllFields() {
		List<InfoField> allFields = new ArrayList<>();

		for (InfoFieldSetEntry infoFieldSetEntry : _entries.values()) {
			if (infoFieldSetEntry instanceof InfoField) {
				allFields.add((InfoField)infoFieldSetEntry);
			}
			else if (infoFieldSetEntry instanceof InfoFieldSet) {
				InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

				allFields.addAll(infoFieldSet.getAllFields());
			}
		}

		return allFields;
	}

	public List<InfoFieldSetEntry> getEntries() {
		return new ArrayList<>(_entries.values());
	}

	public InfoFieldSetEntry getEntry(String name) {
		return _entries.get(name);
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

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _label);

		return HashUtil.hash(hash, _name);
	}

	private final Map<String, InfoFieldSetEntry> _entries =
		new LinkedHashMap<>();
	private final LocalizedValue<String> _label;
	private final String _name;

}