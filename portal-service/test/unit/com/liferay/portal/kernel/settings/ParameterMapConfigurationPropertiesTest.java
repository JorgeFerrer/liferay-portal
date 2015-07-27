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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Iván Zaera
 */
public class ParameterMapConfigurationPropertiesTest extends PowerMockito {

	public ParameterMapConfigurationPropertiesTest() {
		_parameterMapConfigurationProperties =
			new ParameterMapConfigurationProperties(
				_parameterMap, _parentConfigurationProperties);
	}

	@Test
	public void testGetValuesWhenFoundInParameterMap() {
		String[] values = {"requestValue1", "requestValue2"};

		_parameterMap.put("preferences--key--", values);

		Assert.assertArrayEquals(
			values,
			_parameterMapConfigurationProperties.getValues(
				"key", new String[] {"defaultValue"}));
	}

	@Test
	public void testGetValuesWhenFoundInSettings() {
		String[] values = {"settingsValue1", "settingsValue2"};

		_parentConfigurationProperties.setValues("key", values);

		Assert.assertArrayEquals(
			values,
			_parameterMapConfigurationProperties.getValues(
				"key", new String[] {"defaultValue"}));
	}

	@Test
	public void testGetValueWhenFoundInParameterMap() {
		_parameterMap.put("preferences--key--", new String[] {"requestValue"});

		_parentConfigurationProperties.setValue("key", "settingsValue");

		Assert.assertEquals(
			"requestValue",
			_parameterMapConfigurationProperties.getValue(
				"key", "defaultValue"));
	}

	@Test
	public void testGetValueWhenFoundInSettings() {
		_parentConfigurationProperties.setValue("key", "settingsValue");

		Assert.assertEquals(
			"settingsValue",
			_parameterMapConfigurationProperties.getValue(
				"key", "defaultValue"));
	}

	private final Map<String, String[]> _parameterMap = new HashMap<>();
	private final ParameterMapConfigurationProperties
		_parameterMapConfigurationProperties;
	private final MemoryConfigurationProperties _parentConfigurationProperties =
		new MemoryConfigurationProperties();

}