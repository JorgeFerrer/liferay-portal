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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.portlet.PortletPreferences;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class GeneralFileUploadsVerifyProcessTest
	extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(objectClass=" + VerifyProcess.class.getName() +
				")(verify.process.name=" +
					"com.liferay.file.uploads.configuration))");

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();

		Bundle bundle = FrameworkUtil.getBundle(
			GeneralFileUploadsVerifyProcessTest.class);

		_bundleContext = bundle.getBundleContext();

		ServiceReference<ConfigurationAdmin>
			configurationAdminServiceReference =
				_bundleContext.getServiceReference(ConfigurationAdmin.class);

		_configurationAdmin = _bundleContext.getService(
			configurationAdminServiceReference);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_serviceTracker.close();

		Configuration configuration = _configurationAdmin.getConfiguration(
			GeneralFileUploadsConfiguration.class.getName());

		configuration.delete();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();

		GeneralFileUploadsConfiguration baseGeneralFileUploadsConfiguration =
			ConfigurableUtil.createConfigurable(
				GeneralFileUploadsConfiguration.class, Collections.emptyMap());

		Dictionary<String, Object> baseProperties = new Hashtable<>();

		baseProperties.put(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR,
			baseGeneralFileUploadsConfiguration.
				uploadServletRequestImplTempDir());
		baseProperties.put(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
			baseGeneralFileUploadsConfiguration.
				uploadServletRequestImplMaxSize());

		Configuration baseConfiguration = _configurationAdmin.getConfiguration(
			GeneralFileUploadsConfiguration.class.getName());

		baseConfiguration.update(baseProperties);
	}

	@Test
	public void testVerifyGeneralFileUploadsConfiguration() throws Exception {
		Dictionary properties = getConfigurationProperties();

		final String testTempDir = RandomTestUtil.randomString(10);
		final long testUploadSize = RandomTestUtil.randomInt(1000, 5000);

		Assert.assertNotEquals(
			testTempDir,
			properties.get(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR));
		Assert.assertNotEquals(
			testUploadSize,
			properties.get(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences();

		portletPreferences.setValue(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR, testTempDir);
		portletPreferences.setValue(
			PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
			String.valueOf(testUploadSize));

		portletPreferences.store();

		doVerify();

		properties = getConfigurationProperties();

		Assert.assertEquals(
			testTempDir,
			properties.get(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR));
		Assert.assertEquals(
			testUploadSize,
			properties.get(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));
	}

	protected Dictionary getConfigurationProperties() throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			GeneralFileUploadsConfiguration.class.getName());

		return configuration.getProperties();
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return _serviceTracker.getService();
	}

	private static BundleContext _bundleContext;
	private static ConfigurationAdmin _configurationAdmin;
	private static ServiceTracker<VerifyProcess, VerifyProcess> _serviceTracker;

}