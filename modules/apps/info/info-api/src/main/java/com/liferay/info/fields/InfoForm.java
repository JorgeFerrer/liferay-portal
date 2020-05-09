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
public class InfoForm {

	public InfoForm(String name) {
		_name = name;
	}

	public InfoForm add(InfoFieldSetEntry fieldSetEntry) {
		_entries.put(fieldSetEntry.getName(), fieldSetEntry);

		return this;
	}

	public InfoForm addAll(Collection<InfoFieldSetEntry> fieldSetEntries) {
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

		InfoForm infoItemFieldSet = (InfoForm)obj;

		if (Objects.equals(_description, infoItemFieldSet._description) &&
			Objects.equals(_label, infoItemFieldSet._label) &&
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

	public LocalizedValue getDescription() {
		return _description;
	}

	public List<InfoFieldSetEntry> getEntries() {
		return new ArrayList<>(_entries.values());
	}

	public InfoFieldSetEntry getEntry(String name) {
		return _entries.get(name);
	}

	public LocalizedValue getLabel() {
		return _label;
	}

	public String getLabel(Locale locale) {
		return _label.getValue(locale);
	}

	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _description);

		hash = HashUtil.hash(hash, _label);

		return HashUtil.hash(hash, _name);
	}

	public void setDescription(LocalizedValue<String> description) {
		_description = description;
	}

	public void setLabel(LocalizedValue<String> label) {
		_label = label;
	}

	private LocalizedValue<String> _description;
	private final Map<String, InfoFieldSetEntry> _entries =
		new LinkedHashMap<>();
	private LocalizedValue<String> _label;
	private final String _name;

}