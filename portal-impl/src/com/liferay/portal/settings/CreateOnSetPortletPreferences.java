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

package com.liferay.portal.settings;

import com.liferay.portal.kernel.exception.SystemException;

import java.io.IOException;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Iván Zaera
 */
public class CreateOnSetPortletPreferences implements PortletPreferences {

	public CreateOnSetPortletPreferences(
		Callable<PortletPreferences> portletPreferencesCreator) {

		_portletPreferencesCreator = portletPreferencesCreator;
	}

	@Override
	public Map<String, String[]> getMap() {
		if (_portletPreferences != null) {
			return _portletPreferences.getMap();
		}

		return Collections.emptyMap();
	}

	@Override
	public Enumeration<String> getNames() {
		if (_portletPreferences != null) {
			return _portletPreferences.getNames();
		}

		return Collections.emptyEnumeration();
	}

	@Override
	public String getValue(String key, String defaultValue) {
		if (_portletPreferences != null) {
			return _portletPreferences.getValue(key, defaultValue);
		}

		return defaultValue;
	}

	@Override
	public String[] getValues(String key, String[] defaultValues) {
		if (_portletPreferences != null) {
			return _portletPreferences.getValues(key, defaultValues);
		}

		return defaultValues;
	}

	@Override
	public boolean isReadOnly(String key) {
		if (_portletPreferences != null) {
			return _portletPreferences.isReadOnly(key);
		}

		return false;
	}

	@Override
	public void reset(String key) throws ReadOnlyException {
		if (_portletPreferences != null) {
			_portletPreferences.reset(key);
		}
	}

	@Override
	public void setValue(String key, String value) throws ReadOnlyException {
		if (_portletPreferences == null) {
			_portletPreferences = _createPortletPreferences();
		}

		_portletPreferences.setValue(key, value);
	}

	@Override
	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		if (_portletPreferences == null) {
			_portletPreferences = _createPortletPreferences();
		}

		_portletPreferences.setValues(key, values);
	}

	@Override
	public void store() throws IOException, ValidatorException {
		if (_portletPreferences == null) {
			_portletPreferences = _createPortletPreferences();
		}

		_portletPreferences.store();
	}

	private PortletPreferences _createPortletPreferences() {
		try {
			return _portletPreferencesCreator.call();
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private PortletPreferences _portletPreferences;
	private Callable<PortletPreferences> _portletPreferencesCreator;

}