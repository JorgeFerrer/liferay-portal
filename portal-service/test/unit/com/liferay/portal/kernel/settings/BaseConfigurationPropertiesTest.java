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

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Iván Zaera
 */
public class BaseConfigurationPropertiesTest extends PowerMockito {

	public BaseConfigurationPropertiesTest() {
		_parentConfigurationProperties = new MemoryConfigurationProperties();

		_baseConfigurationProperties = new MemoryConfigurationProperties(
			_parentConfigurationProperties);
	}

	@Test
	public void testGetModifiableSettingsForModifiableBaseSettings() {
		BaseConfigurationProperties baseConfigurationProperties =
			new MemoryConfigurationProperties();

		Assert.assertTrue(
			baseConfigurationProperties
				instanceof ModifiableConfigurationProperties);
		Assert.assertSame(
			baseConfigurationProperties,
			baseConfigurationProperties.getModifiableConfigurationProperties());
	}

	@Test
	public void testGetModifiableSettingsForUnmodifiableBaseSettings() {
		ModifiableConfigurationProperties modifiableConfigurationProperties =
			new MemoryConfigurationProperties();
		BaseConfigurationProperties baseConfigurationProperties =
			new ParameterMapConfigurationProperties(
				Collections.<String, String[]> emptyMap(),
			modifiableConfigurationProperties);

		Assert.assertFalse(
			baseConfigurationProperties
				instanceof ModifiableConfigurationProperties);
		Assert.assertSame(
			modifiableConfigurationProperties,
			baseConfigurationProperties.getModifiableConfigurationProperties());
	}

	@Test
	public void testGetParentConfigurationProperties() {
		Assert.assertSame(
			_parentConfigurationProperties,
			_baseConfigurationProperties.getParentConfigurationProperties());
	}

	@Test
	public void testGetValueReturnsDefaultWhenValueAndParentNotSet() {
		Assert.assertEquals(
			_DEFAULT_VALUE,
			_baseConfigurationProperties.getValue(_KEY, _DEFAULT_VALUE));
		Assert.assertArrayEquals(
			_DEFAULT_VALUES,
			_baseConfigurationProperties.getValues(_KEY, _DEFAULT_VALUES));

		_parentConfigurationProperties.setValue(_KEY, _VALUE);

		Assert.assertEquals(
			_VALUE,
			_baseConfigurationProperties.getValue(_KEY, _DEFAULT_VALUE));

		_parentConfigurationProperties.setValues(_KEY, _VALUES);

		Assert.assertArrayEquals(
			_VALUES,
			_baseConfigurationProperties.getValues(_KEY, _DEFAULT_VALUES));
	}

	private static final String _DEFAULT_VALUE = "defaultValue";

	private static final String[] _DEFAULT_VALUES = {
		"defaultValue0", "defaultValue1"
	};

	private static final String _KEY = "key";

	private static final String _VALUE = "value";

	private static final String[] _VALUES = {"value0", "value1"};

	private final BaseConfigurationProperties _baseConfigurationProperties;
	private final MemoryConfigurationProperties _parentConfigurationProperties;

}