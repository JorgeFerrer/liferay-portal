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

import com.liferay.portal.kernel.settings.BasePortletPreferencesSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;

import java.util.concurrent.Callable;

import javax.portlet.PortletPreferences;

/**
 * @author Jorge Ferrer
 * @author Iván Zaera
 */
public class LazyPortletPreferencesSettings
	extends BasePortletPreferencesSettings {

	public LazyPortletPreferencesSettings(
		long companyId, long ownerId, int ownerType, long plid,
		String settingsId) {

		this(companyId, ownerId, ownerType, plid, settingsId, null);
	}

	public LazyPortletPreferencesSettings(
		long companyId, long ownerId, int ownerType, long plid,
		String settingsId, Settings parentSettings) {

		super(parentSettings);

		_companyId = companyId;
		_ownerId = ownerId;
		_ownerType = ownerType;
		_plid = plid;
		_settingsId = settingsId;
	}

	protected PortletPreferences createPortletPreferences() {
		return PortletPreferencesLocalServiceUtil.getPreferences(
			_companyId, _ownerId, _ownerType, _plid, _settingsId);
	}

	@Override
	protected PortletPreferences getPortletPreferences() {
		if (_portletPreferences == null) {
			long portletPreferencesCount =
				PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
					_ownerId, _ownerType, _plid, _settingsId);

			if (portletPreferencesCount == 0) {
				_portletPreferences = new CreateOnSetPortletPreferences(
					new Callable<PortletPreferences>() {

						@Override
						public PortletPreferences call() {
							return createPortletPreferences();
						}
					});
			}
			else {
				_portletPreferences = createPortletPreferences();
			}
		}

		return _portletPreferences;
	}

	private long _companyId;
	private long _ownerId;
	private int _ownerType;
	private long _plid;
	private PortletPreferences _portletPreferences;
	private String _settingsId;

}