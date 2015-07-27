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

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Iván Zaera
 */
public class BaseModifiableConfigurationPropertiesTest extends PowerMockito {

	@Test
	public void testReset() {
		_baseModifiableConfigurationProperties.setValue("key1", "value2");
		_baseModifiableConfigurationProperties.setValue("key2", "value2");

		Assert.assertEquals(
			2, _baseModifiableConfigurationProperties.getModifiedKeys().size());

		_baseModifiableConfigurationProperties.reset();

		Assert.assertEquals(
			0, _baseModifiableConfigurationProperties.getModifiedKeys().size());
	}

	@Test
	public void testSetValues() {
		_baseModifiableConfigurationProperties.setValue("key1", "value1");
		_baseModifiableConfigurationProperties.setValue("key2", "value2");

		ModifiableConfigurationProperties sourceModifiableSettings =
			new MemoryConfigurationProperties();

		sourceModifiableSettings.setValue("otherKey", "otherValue");

		_baseModifiableConfigurationProperties.setValues(
			sourceModifiableSettings);

		Collection<String> keys =
			_baseModifiableConfigurationProperties.getModifiedKeys();

		Assert.assertEquals(3, keys.size());
		Assert.assertEquals(
			"otherValue",
			_baseModifiableConfigurationProperties.getValue("otherKey", null));
		Assert.assertEquals(
			"value1",
			_baseModifiableConfigurationProperties.getValue("key1", null));
		Assert.assertEquals(
			"value2",
			_baseModifiableConfigurationProperties.getValue("key2", null));
	}

	private final BaseModifiableConfigurationProperties
		_baseModifiableConfigurationProperties =
			new MemoryConfigurationProperties();

}