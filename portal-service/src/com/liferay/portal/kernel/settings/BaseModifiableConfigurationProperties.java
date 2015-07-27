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

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Iván Zaera
 */
public abstract class BaseModifiableConfigurationProperties
	extends BaseConfigurationProperties
	implements ModifiableConfigurationProperties {

	public BaseModifiableConfigurationProperties() {
	}

	public BaseModifiableConfigurationProperties(
		ConfigurationProperties parentConfigurationProperties) {

		super(parentConfigurationProperties);
	}

	@Override
	public void reset() {
		for (String key : getModifiedKeys()) {
			reset(key);
		}
	}

	@Override
	public ModifiableConfigurationProperties setValues(
		ModifiableConfigurationProperties modifiableConfigurationProperties) {

		for (String key : modifiableConfigurationProperties.getModifiedKeys()) {
			String[] values = modifiableConfigurationProperties.getValues(
				key, StringPool.EMPTY_ARRAY);

			if (values.length == 1) {
				setValue(key, values[0]);
			}
			else {
				setValues(key, values);
			}
		}

		return this;
	}

}