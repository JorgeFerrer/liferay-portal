/**
 *
 */
package com.liferay.portlet.portalsettings.lar;

import com.liferay.portal.kernel.util.MapUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.when;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * @author kamesh
 *
 */
@PrepareForTest(value = { MapUtil.class })
@RunWith(PowerMockRunner.class)
public class PortalSettingsLARTest extends BasePortalSettingsLARTestCase {

	@Test
	public void testExportAll() {

		mockStatic(MapUtil.class);

		when(
				MapUtil.getBoolean(portletDataContext.getParameterMap(),
						"exportPreferences")).thenReturn(true);
		when(
				MapUtil.getBoolean(portletDataContext.getParameterMap(),
						"exportCompanyDetails")).thenReturn(true);
		when(
				MapUtil.getBoolean(portletDataContext.getParameterMap(),
						"exportLogo"))
				.thenReturn(true);

		final boolean exportPreferences =
				exportLARHandler.isChecked(
						portletDataContext.getParameterMap(),
						"exportPreferences", mockBooleanControl);
		final boolean exportCompanyDetails =
				exportLARHandler.isChecked(
						portletDataContext.getParameterMap(),
						"exportCompanyDetails", mockBooleanControl);
		final boolean exportLogo =
				exportLARHandler.isChecked(
						portletDataContext.getParameterMap(), "exportLogo",
						mockBooleanControl);

		assertTrue(exportPreferences);
		assertTrue(exportCompanyDetails);
		assertTrue(exportLogo);
	}

	@Test
	public void testExportPrefsOnly() {

		mockStatic(MapUtil.class);

		when(
				MapUtil.getBoolean(portletDataContext.getParameterMap(),
						"exportPreferences")).thenReturn(true);
		when(
				MapUtil.getBoolean(portletDataContext.getParameterMap(),
						"exportCompanyDetails")).thenReturn(false);
		when(
				MapUtil.getBoolean(portletDataContext.getParameterMap(),
						"exportLogo"))
				.thenReturn(false);

		boolean exportPreferences =
				exportLARHandler.isChecked(
						portletDataContext.getParameterMap(),
						"exportPreferences", mockBooleanControl);
		boolean exportCompanyDetails =
				exportLARHandler.isChecked(
						portletDataContext.getParameterMap(),
						"exportCompanyDetails", mockBooleanControl);
		boolean exportLogo =
				exportLARHandler.isChecked(
						portletDataContext.getParameterMap(), "exportLogo",
						mockBooleanControl);

		assertTrue(exportPreferences);
		assertFalse(exportCompanyDetails);
		assertFalse(exportLogo);
	}

	@Test
	public void testExportLogoOnly() {

		PowerMockito.mockStatic(MapUtil.class);

		when(
				MapUtil.getBoolean(portletDataContext.getParameterMap(),
						"exportPreferences")).thenReturn(false);
		when(
				MapUtil.getBoolean(portletDataContext.getParameterMap(),
						"exportCompanyDetails")).thenReturn(false);
		when(
				MapUtil.getBoolean(portletDataContext.getParameterMap(),
						"exportLogo"))
				.thenReturn(true);

		boolean exportPreferences =
				exportLARHandler.isChecked(
						portletDataContext.getParameterMap(),
						"exportPreferences", mockBooleanControl);
		boolean exportCompanyDetails =
				exportLARHandler.isChecked(
						portletDataContext.getParameterMap(),
						"exportCompanyDetails", mockBooleanControl);
		boolean exportLogo =
				exportLARHandler.isChecked(
						portletDataContext.getParameterMap(), "exportLogo",
						mockBooleanControl);

		assertFalse(exportPreferences);
		assertFalse(exportCompanyDetails);
		assertTrue(exportLogo);

	}

	@Test
	public void testExportCompanyDetailsOnly() {

		PowerMockito.mockStatic(MapUtil.class);

		when(
				MapUtil.getBoolean(portletDataContext.getParameterMap(),
						"exportPreferences")).thenReturn(false);
		when(
				MapUtil.getBoolean(portletDataContext.getParameterMap(),
						"exportCompanyDetails")).thenReturn(true);
		when(
				MapUtil.getBoolean(portletDataContext.getParameterMap(),
						"exportLogo"))
				.thenReturn(false);

		boolean exportPreferences =
				exportLARHandler.isChecked(
						portletDataContext.getParameterMap(),
						"exportPreferences", mockBooleanControl);
		boolean exportCompanyDetails =
				exportLARHandler.isChecked(
						portletDataContext.getParameterMap(),
						"exportCompanyDetails", mockBooleanControl);
		boolean exportLogo =
				exportLARHandler.isChecked(
						portletDataContext.getParameterMap(), "exportLogo",
						mockBooleanControl);

		assertFalse(exportPreferences);
		assertTrue(exportCompanyDetails);
		assertFalse(exportLogo);

	}

	@Test
	public void testExportPortalSettings() {

		try {

			when(
					exportLARHandler.exportPortalSettings(portletDataContext,
							company,
							portletPreferences)).thenAnswer(
					new Answer<String>() {

						@Override
						public String answer(InvocationOnMock invocation)
								throws Throwable {

							return "<portlet-data>Exported Portal Settings</portlet-data>";
						}
					});

		} catch (Exception e) {
			fail(e.getMessage());
		}

		try {
			String xmlString =
					exportLARHandler.exportPortalSettings(portletDataContext,
							company, portletPreferences);
			assertEquals(
					"<portlet-data>Exported Portal Settings</portlet-data>",
					xmlString);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testImportPortalSettings() {

		try {

			PowerMockito.doNothing().when(importLARHandler)
					.importSettings(portletDataContext,
							prefsElement, companyElement);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}
}