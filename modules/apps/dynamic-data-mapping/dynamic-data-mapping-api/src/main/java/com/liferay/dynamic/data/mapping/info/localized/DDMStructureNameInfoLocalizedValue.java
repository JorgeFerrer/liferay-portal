/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.mapping.info.localized;

import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class DDMStructureNameInfoLocalizedValue
	implements InfoLocalizedValue<String> {

	public DDMStructureNameInfoLocalizedValue(DDMStructure ddmStructure) {
		_ddmStructure = ddmStructure;
	}

	@Override
	public Set<Locale> getAvailableLocales() {
		String[] availableLanguageIds =
			_ddmStructure.getAvailableLanguageIds();

		Set<Locale> availableLocales = new HashSet<>(
			availableLanguageIds.length);

		for (String availableLanguageId : availableLanguageIds) {
			availableLocales.add(
				LanguageUtil.getLocale(availableLanguageId));
		}

		return availableLocales;
	}

	@Override
	public Locale getDefaultLocale() {
		return LanguageUtil.getLocale(_ddmStructure.getDefaultLanguageId());
	}

	@Override
	public String getValue() {
		return getValue(getDefaultLocale());
	}

	@Override
	public String getValue(Locale locale) {
		return _ddmStructure.getName(locale);
	}

	private final DDMStructure _ddmStructure;

}
