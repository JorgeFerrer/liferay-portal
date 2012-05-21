/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.portlet.portalsettings.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Company;

import java.util.HashMap;

import javax.portlet.PortletPreferences;

import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.when;

/**
 * @author kamesh
 *
 */
@PrepareForTest(value = { MapUtil.class })
@RunWith(PowerMockRunner.class)
public abstract class BasePortalSettingsLARTestCase {

	BasePortalSettingsLARTestCase() {

		exportLARHandler = Mockito.mock(PortalSettingsExportLARHandler.class);

		importLARHandler = Mockito.mock(PortalSettingsImportLARHandler.class);

		when(portletDataContext.getParameterMap()).thenReturn(
				new HashMap<String, String[]>());
		when(portletDataContext.getCompanyId()).thenReturn(10152l);
		initMocks();

	}

	protected void initExportMockProtectedMethods() {

		PowerMockito.doReturn("company").when(
				exportLARHandler.getCompanyAccountPath(portletDataContext,
						company));

	}

	protected void initMocks() {

		when(exportLARHandler.isChecked(
				portletDataContext.getParameterMap(), "exportPreferences",
				mockBooleanControl)).thenAnswer(
				new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation)
							throws Throwable {

						return MapUtil.getBoolean(
								portletDataContext.getParameterMap(),
								"exportPreferences");
					}

				});

		when(exportLARHandler.isChecked(
				portletDataContext.getParameterMap(), "exportCompanyDetails",
				mockBooleanControl)).thenAnswer(
				new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation)
							throws Throwable {

						return MapUtil.getBoolean(
								portletDataContext.getParameterMap(),
								"exportCompanyDetails");
					}

				});

		when(exportLARHandler.isChecked(
				portletDataContext.getParameterMap(), "exportLogo",
				mockBooleanControl)).thenAnswer(
				new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation)
							throws Throwable {

						return MapUtil.getBoolean(
								portletDataContext.getParameterMap(),
								"exportLogo");
					}

				});

	}

	protected static final PortletDataHandlerBoolean mockBooleanControl =
			Mockito
					.mock(PortletDataHandlerBoolean.class);

	protected static final PortletDataContext portletDataContext =
			PowerMockito
					.mock(PortletDataContext.class);

	protected static final PortletPreferences portletPreferences =
			PowerMockito
					.mock(PortletPreferences.class);

	protected final Company company = PowerMockito.mock(Company.class);

	protected final Element companyElement = PowerMockito.mock(Element.class);

	protected final Element prefsElement = PowerMockito.mock(Element.class);

	protected PortalSettingsExportLARHandler exportLARHandler;

	protected PortalSettingsImportLARHandler importLARHandler;

}