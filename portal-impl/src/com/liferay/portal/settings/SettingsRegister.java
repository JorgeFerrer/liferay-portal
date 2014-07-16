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

import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;

import java.util.List;

/**
 * @author Roberto Díaz
 */
public class SettingsRegister implements Runnable {

	public SettingsRegister(
		FallbackKeys fallbackKeys, String[] multiValuedKeys, String portletId) {

		_fallbackKeys = fallbackKeys;
		_multiValuedKeys = multiValuedKeys;
		_portletId = portletId;
	}

	@Override
	public void run() {
		SettingsFactory settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

		List<String> multiValuedKeys = settingsFactory.getMultiValuedKeys(
			_portletId);

		if (multiValuedKeys == null) {
			settingsFactory.registerSettingsMetadata(
				_portletId, _fallbackKeys, _multiValuedKeys);
		}
	}

	private FallbackKeys _fallbackKeys;
	private String[] _multiValuedKeys = {};
	private String _portletId;

}