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
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Iván Zaera
 */
public class ConfigurationBeanConfigurationPropertiesTest extends PowerMockito {

	@Before
	public void setUp() {
		_configurationBean = new ConfigurationBean();

		_mockLocationVariableResolver = mock(LocationVariableResolver.class);

		_configurationBeanConfigurationProperties =
			new ConfigurationBeanConfigurationProperties(
				_mockLocationVariableResolver, _configurationBean, null);
	}

	@Test
	public void testGetValuesWithExistingKey() {
		Assert.assertArrayEquals(
			_configurationBean.stringArrayValue(),
			_configurationBeanConfigurationProperties.getValues(
				"stringArrayValue", new String[] {"defaultValue"}));
	}

	@Test
	public void testGetValuesWithMissingKey() {
		String[] defaultValue = {"defaultValue"};

		Assert.assertArrayEquals(
			defaultValue,
			_configurationBeanConfigurationProperties.getValues(
				"missingKey", defaultValue));
	}

	@Test
	public void testGetValueWithExistingBooleanValue() {
		Assert.assertEquals(
			String.valueOf(_configurationBean.booleanValue()),
			_configurationBeanConfigurationProperties.getValue(
				"booleanValue", "defaultValue"));
	}

	@Test
	public void testGetValueWithExistingLocalizedValuesMapValue() {
		LocalizedValuesMap localizedValuesMap =
			_configurationBean.localizedValuesMap();

		Assert.assertEquals(
			localizedValuesMap.getDefaultValue(),
			_configurationBeanConfigurationProperties.getValue(
				"localizedValuesMap", null));
	}

	@Test
	public void testGetValueWithExistingStringValue() {
		Assert.assertEquals(
			_configurationBean.stringValue(),
			_configurationBeanConfigurationProperties.getValue(
				"stringValue", "defaultValue"));
	}

	@Test
	public void testGetValueWithLocationVariable() {
		when(
			_mockLocationVariableResolver.isLocationVariable(
				_configurationBean.locationVariableValue())
		).thenReturn(
			true
		);

		String expectedValue = "Once upon a time...";

		when(
			_mockLocationVariableResolver.resolve(
				_configurationBean.locationVariableValue())
		).thenReturn(
			expectedValue
		);

		Assert.assertEquals(
			expectedValue,
			_configurationBeanConfigurationProperties.getValue(
				"locationVariableValue", "defaultValue"));
	}

	@Test
	public void testGetValueWithMissingKey() {
		Assert.assertEquals(
			"defaultValue",
			_configurationBeanConfigurationProperties.getValue(
				"missingKey", "defaultValue"));
	}

	@Test
	public void testGetValueWithNullConfigurationBean() {
		_configurationBeanConfigurationProperties =
			new ConfigurationBeanConfigurationProperties(null, null, null);

		Assert.assertEquals(
			"defaultValue",
			_configurationBeanConfigurationProperties.getValue(
				"anyKey", "defaultValue"));
	}

	private ConfigurationBean _configurationBean;
	private ConfigurationBeanConfigurationProperties
		_configurationBeanConfigurationProperties;
	private LocationVariableResolver _mockLocationVariableResolver;

	private static class ConfigurationBean {

		public boolean booleanValue() {
			return true;
		}

		public LocalizedValuesMap localizedValuesMap() {
			return new LocalizedValuesMap("default localized value");
		}

		public String locationVariableValue() {
			return "${resource:template.ftl}";
		}

		public String[] stringArrayValue() {
			return new String[] {
				"string value 0", "string value 1", "string value 2"
			};
		}

		public String stringValue() {
			return "a string value";
		}

	}

}