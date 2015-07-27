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
public class MemoryConfigurationPropertiesTest extends PowerMockito {

	@Test
	public void testSetAndGetValue() {
		_memoryConfigurationProperties.setValue("key", "value");

		Collection<String> keys =
			_memoryConfigurationProperties.getModifiedKeys();

		Assert.assertEquals(1, keys.size());
		Assert.assertEquals(
			"value", _memoryConfigurationProperties.getValue("key", null));
	}

	@Test
	public void testSetAndGetValues() {
		_memoryConfigurationProperties.setValues(
			"key",
			new String[] {"value1", "value2"});

		Collection<String> keys =
			_memoryConfigurationProperties.getModifiedKeys();

		Assert.assertEquals(1, keys.size());

		String[] values = _memoryConfigurationProperties.getValues("key", null);

		Assert.assertEquals(2, values.length);
		Assert.assertEquals("value1", values[0]);
		Assert.assertEquals("value2", values[1]);

		Assert.assertEquals(
			"value1", _memoryConfigurationProperties.getValue("key", null));
	}

	private final MemoryConfigurationProperties _memoryConfigurationProperties =
		new MemoryConfigurationProperties();

}