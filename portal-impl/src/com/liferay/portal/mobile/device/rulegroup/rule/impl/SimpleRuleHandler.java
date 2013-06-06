/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.mobile.device.rulegroup.rule.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.rulegroup.rule.RuleHandler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Edward Han
 * @author Milen Daynkov
 */
public class SimpleRuleHandler implements RuleHandler {

	public static final String PROPERTY_DISPLAY_HEIGHT = "display_height";
	public static final String PROPERTY_DISPLAY_WIDTH = "display_width";
	public static final String PROPERTY_OS = "os";
	public static final String PROPERTY_RESOLUTION_HEIGHT = "resolution_height";
	public static final String PROPERTY_RESOLUTION_WIDTH = "resolution_width";
	public static final String PROPERTY_TABLET = "tablet";

	public static String getHandlerType() {

		return SimpleRuleHandler.class.getName();
	}

	@Override
	public boolean evaluateRule(MDRRule mdrRule, ThemeDisplay themeDisplay) {

		Device device = themeDisplay.getDevice();

		if (_log.isDebugEnabled()) {
			_log.debug("Evaluating rule '" + mdrRule.getNameCurrentValue() +
				"'!");
		}

		if (device == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Skipping rule '" + mdrRule.getNameCurrentValue() +
					"'! Mising device information!");
			}

			return false;
		}

		UnicodeProperties typeSettingsProperties =
			mdrRule.getTypeSettingsProperties();

		// check OS

		String os = typeSettingsProperties.get(PROPERTY_OS);

		if (_log.isDebugEnabled()) {
			_log.debug("Device OS: " + device.getOS());
		}

		if (Validator.isNotNull(os)) {
			String[] operatingSystems = StringUtil.split(os);

			if (!ArrayUtil.contains(operatingSystems, device.getOS())) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skipping rule '" +
						mdrRule.getNameCurrentValue() +
						"'! Device OS does not match! Rule condition: " +
						Arrays.toString(operatingSystems));
				}

				return false;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Device OS matches rule condition: " +
					operatingSystems);
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("No condition for device OS!");
			}
		}

		// check OS

		String tablet = typeSettingsProperties.get(PROPERTY_TABLET);

		if (_log.isDebugEnabled()) {
			_log.debug("Device is tablet : " +
				(device.isTablet() ? "yes" : "no"));
		}

		if (Validator.isNotNull(tablet)) {
			boolean tabletBoolean = GetterUtil.getBoolean(tablet);

			if (tabletBoolean != device.isTablet()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skipping rule '" +
						mdrRule.getNameCurrentValue() +
						"'! Device type does not match! Rule condition: is tablet = " +
						(tabletBoolean ? "yes" : "no"));
				}

				return false;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Device type matches rule condition: is tablet = " +
					(tabletBoolean ? "yes" : "no"));
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("No condition for device type!");
			}
		}

		String ruleStringValue;
		int deviceValue;
		int ruleValue;

		// check display width

		ruleStringValue = typeSettingsProperties.get(PROPERTY_DISPLAY_WIDTH);

		if (_log.isDebugEnabled()) {
			_log.debug("Device display width: " +
				device.getDisplaySize().getWidth());
		}

		if (Validator.isNotNull(ruleStringValue)) {
			ruleValue = GetterUtil.getInteger(ruleStringValue);
			deviceValue = device.getDisplaySize().getWidth();

			if (ruleValue != deviceValue) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skipping rule '" +
						mdrRule.getNameCurrentValue() +
						"'! Device display width does not match! Rule condition: " +
						ruleValue);
				}

				return false;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Device display width matches rule condition: " +
					ruleValue);
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("No condition for device display width!");
			}
		}

		// check display height

		ruleStringValue = typeSettingsProperties.get(PROPERTY_DISPLAY_HEIGHT);

		if (_log.isDebugEnabled()) {
			_log.debug("Device display height: " +
				device.getDisplaySize().getHeight());
		}

		if (Validator.isNotNull(ruleStringValue)) {
			ruleValue = GetterUtil.getInteger(ruleStringValue);
			deviceValue = device.getDisplaySize().getHeight();

			if (ruleValue != deviceValue) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skipping rule '" +
						mdrRule.getNameCurrentValue() +
						"'! Device display height does not match! Rule condition: " +
						ruleValue);
				}

				return false;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Device display height matches rule condition: " +
					ruleValue);
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("No condition for device display height!");
			}
		}

		// check resolution height

		ruleStringValue = typeSettingsProperties.get(
			PROPERTY_RESOLUTION_HEIGHT);

		if (_log.isDebugEnabled()) {
			_log.debug("Device resolution height: " +
				device.getResolution().getHeight());
		}

		if (Validator.isNotNull(ruleStringValue)) {
			ruleValue = GetterUtil.getInteger(ruleStringValue);
			deviceValue = device.getResolution().getHeight();

			if (ruleValue != deviceValue) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skipping rule '" +
						mdrRule.getNameCurrentValue() +
						"'! Device resolution height does not match! Rule condition: " +
						ruleValue);
				}

				return false;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Device resolution height matches rule condition: " +
					ruleValue);
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("No condition for Device resolution width!");
			}
		}

		// check resolution width

		ruleStringValue = typeSettingsProperties.get(PROPERTY_RESOLUTION_WIDTH);

		if (_log.isDebugEnabled()) {
			_log.debug("Device resolution width: " +
				device.getResolution().getWidth());
		}

		if (Validator.isNotNull(ruleStringValue)) {
			ruleValue = GetterUtil.getInteger(ruleStringValue);
			deviceValue = device.getResolution().getWidth();

			if (ruleValue != deviceValue) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skipping rule '" +
						mdrRule.getNameCurrentValue() +
						"'! Device resolution width does not match! Rule condition: " +
						ruleValue);
				}

				return false;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Device resolution width matches rule condition: " +
					ruleValue);
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("No condition for device resolution height!");
			}
		}

		// if we are here then either everything matches or there are no
		// conditions. Thus we return true

		return true;
	}

	@Override
	public Collection<String> getPropertyNames() {

		return _propertyNames;
	}

	@Override
	public String getType() {

		return getHandlerType();
	}

	private static Log _log = LogFactoryUtil.getLog(SimpleRuleHandler.class);

	static {
		_propertyNames = new ArrayList<String>(6);

		_propertyNames.add(PROPERTY_OS);
		_propertyNames.add(PROPERTY_TABLET);
		_propertyNames.add(PROPERTY_DISPLAY_WIDTH);
		_propertyNames.add(PROPERTY_DISPLAY_HEIGHT);
		_propertyNames.add(PROPERTY_RESOLUTION_WIDTH);
		_propertyNames.add(PROPERTY_RESOLUTION_HEIGHT);

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}

	private static Collection<String> _propertyNames;

}