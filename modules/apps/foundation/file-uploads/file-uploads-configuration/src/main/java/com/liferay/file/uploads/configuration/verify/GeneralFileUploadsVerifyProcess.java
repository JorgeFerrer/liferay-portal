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

package com.liferay.file.uploads.configuration.verify;

import com.liferay.file.uploads.configuration.GeneralFileUploadsConfiguration;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.verify.VerifyProcess;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.file.uploads.configuration"},
	service = VerifyProcess.class
)
public class GeneralFileUploadsVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		_saveConfiguration();
	}

	private void _saveConfiguration() throws IOException {
		String pid = GeneralFileUploadsConfiguration.class.getName();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(Constants.SERVICE_PID, pid);

		long uploadServletRequestImplMaxSize = _prefsProps.getLong(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
		String uploadServletRequestImplTempDir = _prefsProps.getString(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR,
			SystemProperties.get(SystemProperties.TMP_DIR));

		properties.put(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
			uploadServletRequestImplMaxSize);
		properties.put(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR,
			uploadServletRequestImplTempDir);

		Configuration configuration = _configurationAdmin.getConfiguration(pid);

		configuration.update(properties);
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private PrefsProps _prefsProps;

}