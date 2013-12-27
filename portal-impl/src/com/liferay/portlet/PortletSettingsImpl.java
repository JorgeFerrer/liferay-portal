/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.PortletSettings;
import com.liferay.util.xml.XMLFormatter;

import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Raymond Augé
 */
public class PortletSettingsImpl implements PortletSettings {

	public PortletSettingsImpl(
		PortletPreferences companySettings, Properties portalProperties) {

		this(null, null, companySettings, portalProperties);
	}

	public PortletSettingsImpl(
		PortletPreferences siteSettings, PortletPreferences companySettings,
		Properties portalProperties) {

		this(null, siteSettings, companySettings, portalProperties);
	}

	public PortletSettingsImpl(
		PortletPreferences instanceSettings, PortletPreferences siteSettings,
		PortletPreferences companySettings, Properties portalProperties) {

		_instanceSettings = instanceSettings;
		_siteSettings = siteSettings;
		_companySettings = companySettings;
		_portalProperties = portalProperties;
	}

	@Override
	public String getValue(String key, String def) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		String value = null;

		if (_instanceSettings != null) {
			value = _instanceSettings.getValue(key, null);
		}

		if (isNull(value) && (_siteSettings != null)) {
			value = _siteSettings.getValue(key, null);
		}

		if (isNull(value)) {
			value = _companySettings.getValue(key, null);
		}

		if (isNull(value)) {
			value = _portalProperties.getProperty(key, null);
		}

		if (!isNull(value)) {
			return normalizeValue(value);
		}

		return normalizeValue(def);
	}

	@Override
	public String[] getValues(String key, String[] def) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		String[] values = _siteSettings.getValues(key, def);

		if (_instanceSettings != null) {
			values = _instanceSettings.getValues(key, null);
		}

		if (ArrayUtil.isNotEmpty(values) && (_siteSettings != null)) {
			values = _siteSettings.getValues(key, null);
		}

		if (ArrayUtil.isNotEmpty(values)) {
			values = _companySettings.getValues(key, null);
		}

		if (ArrayUtil.isNotEmpty(values)) {
			values = StringUtil.split(_portalProperties.getProperty(key));
		}

		if (ArrayUtil.isNotEmpty(values)) {
			return normalizeValues(values);
		}

		return normalizeValues(def);
	}

	private boolean isNull(String value) {
		return (value == null) || value.equals(_NULL_VALUE);
	}

	protected String normalizeValue(String value) {
		if (isNull(value)) {
			return null;
		}

		return XMLFormatter.fromCompactSafe(value);
	}

	protected String[] normalizeValues(String[] values) {
		if (values == null) {
			return null;
		}

		if (values.length == 1) {
			String actualValue = normalizeValue(values[0]);

			if (actualValue == null) {
				return null;
			}

			return new String[] {actualValue};
		}

		String[] actualValues = new String[values.length];

		for (int i = 0; i < actualValues.length; i++) {
			actualValues[i] = normalizeValue(values[i]);
		}

		return actualValues;
	}

	private static final String _NULL_VALUE = "NULL_VALUE";

	private PortletPreferences _companySettings;
	private PortletPreferences _instanceSettings;
	private Properties _portalProperties;
	private PortletPreferences _siteSettings;

}