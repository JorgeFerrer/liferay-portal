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

import java.io.IOException;

import java.util.Collection;

import javax.portlet.ValidatorException;

/**
 * @author Iván Zaera
 */
public interface ModifiableConfigurationProperties
	extends ConfigurationProperties {

	public Collection<String> getModifiedKeys();

	public void reset();

	public void reset(String key);

	public ModifiableConfigurationProperties setValue(String key, String value);

	public ModifiableConfigurationProperties setValues(
		ModifiableConfigurationProperties modifiableConfigurationProperties);

	public ModifiableConfigurationProperties setValues(
		String key, String[] values);

	public void store() throws IOException, ValidatorException;

}