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

package com.liferay.info.localized;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class LocalizedValue<T> {

	public static Builder builder() {
		return new Builder();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LocalizedValue)) {
			return false;
		}

		LocalizedValue localizedValue = (LocalizedValue)obj;

		if (Objects.equals(
				_builder._defaultLocale,
				localizedValue._builder._defaultLocale) &&
			Objects.equals(_builder._values, localizedValue._builder._values)) {

			return true;
		}

		return false;
	}

	public Set<Locale> getAvailableLocales() {
		return _builder._values.keySet();
	}

	public Locale getDefaultLocale() {
		if (_builder._defaultLocale == null) {
			return LocaleUtil.getDefault();
		}

		return _builder._defaultLocale;
	}

	public T getValue() {
		return _builder._values.get(getDefaultLocale());
	}

	public T getValue(Locale locale) {
		T value = _builder._values.get(locale);

		if (value == null) {
			value = _builder._values.get(getDefaultLocale());
		}

		return value;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _builder._defaultLocale);

		return HashUtil.hash(hash, _builder._values);
	}

	public static class Builder<T> {

		public Builder addValue(Locale locale, T value) {
			_values.put(locale, value);

			return this;
		}

		public Builder addValues(Map<Locale, T> values) {
			_values.putAll(values);

			return this;
		}

		public LocalizedValue<T> build() {
			return new LocalizedValue<>(this);
		}

		public Builder defaultLocale(Locale locale) {
			_defaultLocale = locale;

			return this;
		}

		private Builder() {
		}

		private Locale _defaultLocale;
		private final Map<Locale, T> _values = new HashMap<>();

	}

	private LocalizedValue(Builder<T> builder) {
		_builder = builder;
	}

	private final Builder<T> _builder;

}