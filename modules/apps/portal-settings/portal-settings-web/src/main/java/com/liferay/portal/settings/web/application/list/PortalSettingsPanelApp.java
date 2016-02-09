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

package com.liferay.portal.settings.web.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.settings.web.constants.PortalSettingsPortletKeys;
import com.liferay.product.navigation.control.panel.application.list.ConfigurationPanelCategory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + ConfigurationPanelCategory.KEY,
		"service.ranking:Integer=100"
	},
	service = PanelApp.class
)
public class PortalSettingsPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return PortalSettingsPortletKeys.PORTAL_SETTINGS;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + PortalSettingsPortletKeys.PORTAL_SETTINGS + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}