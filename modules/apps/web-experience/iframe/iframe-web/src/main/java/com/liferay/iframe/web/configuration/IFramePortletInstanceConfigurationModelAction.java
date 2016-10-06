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

package com.liferay.iframe.web.configuration;

import com.liferay.configuration.admin.action.ConfigurationModelAction;
import com.liferay.configuration.admin.action.ConfigurationModelActionException;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.iframe.web.configuration.IFramePortletInstanceConfiguration"
	},
	service = ConfigurationModelAction.class
)
public class IFramePortletInstanceConfigurationModelAction
	implements ConfigurationModelAction {

	public void doAfter(Dictionary<String, Object> properties)
		throws ConfigurationModelActionException {

		System.out.println("Configuration successfully saved!");
	}

	public void doBefore(Dictionary<String, Object> properties)
		throws ConfigurationModelActionException {

		String authType = (String)properties.get("authType");

		if (authType.equals("none")) {
			throw new ConfigurationModelActionException(
				"There was an issue validating the values in " +
				"IFramePortletInstanceConfiguration");
		}
	}

}