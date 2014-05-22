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
public class BaseSettingsTest extends PowerMockito {

	public BaseSettingsTest() {
		_parentSettings = new MemorySettings();
		_baseSettings = new MemorySettings(_parentSettings);
	}

	@Test
	public void testGetModifiableSettingsForModifiableBaseSettings() {
		BaseSettings modifiableBaseSettings = new MemorySettings();

		Assert.assertTrue(modifiableBaseSettings instanceof ModifiableSettings);
		Assert.assertSame(
			modifiableBaseSettings,
			modifiableBaseSettings.getModifiableSettings());
	}

	@Test
	public void testGetModifiableSettingsForUnmodifiableBaseSettings() {
		ModifiableSettings parentSettings = new MemorySettings();
		BaseSettings unmodifiableBaseSettings = new ParameterMapSettings(
			Collections.<String, String[]> emptyMap(), parentSettings);

		Assert.assertFalse(
			unmodifiableBaseSettings instanceof ModifiableSettings);
		Assert.assertSame(
			parentSettings, unmodifiableBaseSettings.getModifiableSettings());
	}

	@Test
	public void testGetParentSettings() {
		Assert.assertSame(_parentSettings, _baseSettings.getParentSettings());
	}

	private BaseSettings _baseSettings;
	private MemorySettings _parentSettings;

}