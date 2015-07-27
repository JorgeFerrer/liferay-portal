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

import org.junit.Assert;
import org.junit.Test;

import org.mockito.InOrder;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Iván Zaera
 */
public class FallbackSettingsTest extends PowerMockito {

	public FallbackSettingsTest() {
		_configurationProperties = mock(ConfigurationProperties.class);

		_fallbackKeys = new FallbackKeys();

		_fallbackKeys.add("key1", "key2", "key3");
		_fallbackKeys.add("key2", "key7");
		_fallbackKeys.add("key3", "key5");

		_fallbackSettings = new FallbackSettings(
			_configurationProperties, _fallbackKeys);
	}

	@Test
	public void testGetValuesWhenConfigured() {
		String[] defaultValues = {"default"};
		String[] mockValues = {"value"};

		when(
			_configurationProperties.getValues("key2", null)
		).thenReturn(
			mockValues
		);

		String[] values = _fallbackSettings.getValues("key1", defaultValues);

		Assert.assertArrayEquals(mockValues, values);

		verifyGetValues("key1", "key2");
	}

	@Test
	public void testGetValuesWhenUnconfigured() {
		String[] defaultValues = {"default"};

		String[] values = _fallbackSettings.getValues("key1", defaultValues);

		Assert.assertArrayEquals(defaultValues, values);

		verifyGetValues("key1", "key2", "key3");
	}

	@Test
	public void testGetValueWhenConfigured() {
		when(
			_configurationProperties.getValue("key2", null)
		).thenReturn(
			"value"
		);

		String value = _fallbackSettings.getValue("key1", "default");

		Assert.assertEquals("value", value);

		verifyGetValue("key1", "key2");
	}

	@Test
	public void testGetValueWhenUnconfigured() {
		String value = _fallbackSettings.getValue("key1", "default");

		Assert.assertEquals("default", value);

		verifyGetValue("key1", "key2", "key3");
	}

	protected void verifyGetValue(String... keys) {
		InOrder inOrder = Mockito.inOrder(_configurationProperties);

		for (String key : keys) {
			inOrder.verify(_configurationProperties);

			_configurationProperties.getValue(key, null);
		}
	}

	protected void verifyGetValues(String... keys) {
		InOrder inOrder = Mockito.inOrder(_configurationProperties);

		for (String key : keys) {
			inOrder.verify(_configurationProperties);

			_configurationProperties.getValues(key, null);
		}
	}

	private final ConfigurationProperties _configurationProperties;
	private final FallbackKeys _fallbackKeys;
	private final FallbackSettings _fallbackSettings;

}