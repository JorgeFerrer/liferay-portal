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

package com.liferay.portal.kernel.settings;

/**
 * @author Iván Zaera
 */
public class FallbackSettings extends BaseSettings {

	public FallbackSettings(
		ConfigurationProperties parentConfigurationProperties,
		FallbackKeys fallbackKeys) {

		super(parentConfigurationProperties);

		_fallbackKeys = fallbackKeys;
	}

	@Override
	protected String doGetValue(String key) {
		String value = parentConfigurationProperties.getValue(key, null);

		if (value != null) {
			return value;
		}

		String[] fallbackKeysArray = _fallbackKeys.get(key);

		for (String fallbackKey : fallbackKeysArray) {
			value = parentConfigurationProperties.getValue(fallbackKey, null);

			if (value != null) {
				return value;
			}
		}

		return null;
	}

	@Override
	protected String[] doGetValues(String key) {
		String[] values = parentConfigurationProperties.getValues(key, null);

		if (values != null) {
			return values;
		}

		String[] fallbackKeysArray = _fallbackKeys.get(key);

		for (String fallbackKey : fallbackKeysArray) {
			values = parentConfigurationProperties.getValues(fallbackKey, null);

			if (values != null) {
				return values;
			}
		}

		return null;
	}

	private final FallbackKeys _fallbackKeys;

}