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

package com.liferay.file.uploads.configuration.listener;

import com.liferay.file.uploads.configuration.GeneralFileUploadsConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upload.UploadServletRequestImpl;

import java.io.File;
import java.io.IOException;

import java.util.Dictionary;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.file.uploads.configuration.GeneralFileUploadsConfiguration"
	},
	service = ConfigurationModelListener.class
)
public class GeneralFileUploadsConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterDelete(String pid)
		throws ConfigurationModelListenerException {

		_updateSettings(new HashMapDictionary());
	}

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		_updateSettings(properties);
	}

	private void _updateSettings(Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		GeneralFileUploadsConfiguration generalFileUploadsConfiguration =
			ConfigurableUtil.createConfigurable(
				GeneralFileUploadsConfiguration.class, properties);

		PortletPreferences portletPreferences = _prefsProps.getPreferences();

		try {
			portletPreferences.setValue(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
				String.valueOf(
					generalFileUploadsConfiguration.
						uploadServletRequestImplMaxSize()));

			String tempDir =
				generalFileUploadsConfiguration.
					uploadServletRequestImplTempDir();

			if (Validator.isNull(tempDir)) {
				tempDir = SystemProperties.get(SystemProperties.TMP_DIR);
			}

			portletPreferences.setValue(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR, tempDir);

			UploadServletRequestImpl.setTempDir(new File(tempDir));

			portletPreferences.store();
		}
		catch (IOException | ReadOnlyException | ValidatorException e) {
			Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass());

			throw new ConfigurationModelListenerException(
				LanguageUtil.get(
					resourceBundle,
					"there-was-an-issue-storing-the-preferences"),
				GeneralFileUploadsConfiguration.class, this.getClass(),
				properties);
		}
	}

	@Reference
	private PrefsProps _prefsProps;

}