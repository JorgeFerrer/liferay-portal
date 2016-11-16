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

package com.liferay.file.uploads.configuration.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.file.uploads.configuration.GeneralFileUploadsConfiguration;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PrefsPropsUtil;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class GeneralFileUploadsConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			GeneralFileUploadsConfigurationModelListenerTest.class);

		_bundleContext = bundle.getBundleContext();

		ServiceReference<ConfigurationAdmin>
			configurationAdminServiceReference =
				_bundleContext.getServiceReference(ConfigurationAdmin.class);

		_configurationAdmin = _bundleContext.getService(
			configurationAdminServiceReference);
	}

	@Test
	public void testOnAfterDelete() throws Exception {
		long defaultMaxUploadSize = 104857600;
		String defaultTempDir = SystemProperties.get(SystemProperties.TMP_DIR);

		Dictionary<String, Object> configurationProperties = new Hashtable<>();

		configurationProperties.put(
			Constants.SERVICE_PID,
			GeneralFileUploadsConfiguration.class.getName());
		configurationProperties.put(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
			RandomTestUtil.randomInt(5001, 10000));
		configurationProperties.put(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR,
			RandomTestUtil.randomString(10));

		Configuration configuration = _configurationAdmin.getConfiguration(
			GeneralFileUploadsConfiguration.class.getName());

		configuration.update(configurationProperties);

		Assert.assertNotEquals(
			defaultMaxUploadSize,
			PrefsPropsUtil.getLong(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));
		Assert.assertNotEquals(
			defaultTempDir,
			PrefsPropsUtil.getString(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR));

		configuration.delete();

		Assert.assertEquals(
			defaultMaxUploadSize,
			PrefsPropsUtil.getLong(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));
		Assert.assertEquals(
			defaultTempDir,
			PrefsPropsUtil.getString(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR));

		Assert.assertTrue(true);
	}

	@Test
	public void testOnAfterSave() throws Exception {
		final String testTempDir = RandomTestUtil.randomString(15);
		final long testUploadSize = RandomTestUtil.randomInt(1000, 5000);

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences();

		portletPreferences.setValue(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR,
			RandomTestUtil.randomString(10));
		portletPreferences.setValue(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
			String.valueOf(RandomTestUtil.randomInt(5001, 10000)));

		portletPreferences.store();

		Assert.assertNotEquals(
			testTempDir, PrefsPropsUtil.getString(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR));
		Assert.assertNotEquals(
			testUploadSize, PrefsPropsUtil.getString(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));

		Dictionary<String, Object> configurationProperties = new Hashtable<>();

		configurationProperties.put(
			Constants.SERVICE_PID,
			GeneralFileUploadsConfiguration.class.getName());
		configurationProperties.put(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE, testUploadSize);
		configurationProperties.put(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR, testTempDir);

		Configuration configuration = _configurationAdmin.getConfiguration(
			GeneralFileUploadsConfiguration.class.getName());

		configuration.update(configurationProperties);

		Assert.assertEquals(
			testTempDir, PrefsPropsUtil.getString(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR));
		Assert.assertEquals(
			testUploadSize, PrefsPropsUtil.getLong(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));
	}

	private static BundleContext _bundleContext;
	private static ConfigurationAdmin _configurationAdmin;

}