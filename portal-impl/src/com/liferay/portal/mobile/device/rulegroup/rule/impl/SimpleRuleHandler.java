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

	public static final String PROPERTY_DISPLAY_MAX_HEIGHT =
		"display_height_max";
	public static final String PROPERTY_DISPLAY_MAX_WIDTH = "display_width_max";
	public static final String PROPERTY_DISPLAY_MIN_HEIGHT =
		"display_height_min";
	public static final String PROPERTY_DISPLAY_MIN_WIDTH = "display_width_min";
	public static final String PROPERTY_OS = "os";
	public static final String PROPERTY_RESOLUTION_MAX_HEIGHT =
		"resolution_height_max";
	public static final String PROPERTY_RESOLUTION_MAX_WIDTH =
		"resolution_width_max";
	public static final String PROPERTY_RESOLUTION_MIN_HEIGHT =
		"resolution_height_min";
	public static final String PROPERTY_RESOLUTION_MIN_WIDTH =
		"resolution_width_min";
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
			_log.debug("Device's OS: " + device.getOS());
		}

		if (Validator.isNotNull(os)) {
			String[] operatingSystems = StringUtil.split(os);

			if (!ArrayUtil.contains(operatingSystems, device.getOS())) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skipping rule '" +
						mdrRule.getNameCurrentValue() +
						"'! Device's OS does not match rule condition: " +
						Arrays.toString(operatingSystems));
				}

				return false;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Device's OS matches rule condition: " +
					operatingSystems);
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("No condition defined for device OS!");
			}
		}

		// check OS

		String tablet = typeSettingsProperties.get(PROPERTY_TABLET);

		if (_log.isDebugEnabled()) {
			_log.debug("Device type : " +
				(device.isTablet() ? "tablet" : "other device"));
		}

		if (Validator.isNotNull(tablet)) {
			boolean tabletBoolean = GetterUtil.getBoolean(tablet);

			if (tabletBoolean != device.isTablet()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skipping rule '" +
						mdrRule.getNameCurrentValue() +
						"'! Device type does not match! Rule condition: is tablet = " +
						(tabletBoolean ? "tablet" : "other device"));
				}

				return false;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Device type matches rule condition: " +
					(tabletBoolean ? "tablet" : "other device"));
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("No condition defined for device type!");
			}
		}

		// check display height

		if (!between(
				device.getDisplaySize().getHeight(),
			typeSettingsProperties.get(PROPERTY_DISPLAY_MIN_HEIGHT),
			typeSettingsProperties.get(PROPERTY_DISPLAY_MAX_HEIGHT),
			mdrRule.getNameCurrentValue(), "display height")) {
			return false;
		}

		// check display width

		if (!between(
				device.getDisplaySize().getWidth(),
			typeSettingsProperties.get(PROPERTY_DISPLAY_MIN_WIDTH),
			typeSettingsProperties.get(PROPERTY_DISPLAY_MAX_WIDTH),
			mdrRule.getNameCurrentValue(), "display width")) {
			return false;
		}

		// check resolution height

		if (!between(
				device.getResolution().getHeight(),
			typeSettingsProperties.get(PROPERTY_RESOLUTION_MIN_HEIGHT),
			typeSettingsProperties.get(PROPERTY_RESOLUTION_MAX_HEIGHT),
			mdrRule.getNameCurrentValue(), "resolution height")) {
			return false;
		}

		// check resolution width

		if (!between(
				device.getResolution().getWidth(),
			typeSettingsProperties.get(PROPERTY_RESOLUTION_MIN_WIDTH),
			typeSettingsProperties.get(PROPERTY_RESOLUTION_MAX_WIDTH),
			mdrRule.getNameCurrentValue(), "resolution width")) {
			return false;
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

	protected boolean between(
		int value, String ruleStringValueMin, String ruleStringValueMax,
		String logRuleName, String logDescription) {

		int ruleValue;

		if (_log.isDebugEnabled()) {
			_log.debug("Device's " + logDescription + ": " + value);
		}

		if (Validator.isNull(ruleStringValueMax) &&
			Validator.isNull(ruleStringValueMin)) {
			if (_log.isDebugEnabled()) {
				_log.debug("No condition defined for device's " +
					logDescription + "!");
			}

			return true;
		}

		if (Validator.isNotNull(ruleStringValueMax)) {
			ruleValue = GetterUtil.getInteger(ruleStringValueMax);

			if (value > ruleValue) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skipping rule '" + logRuleName +
						"'! Device's " + logDescription + " > max " +
						logRuleName + "(" + ruleValue + ")");
				}

				return false;
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Device's " + logDescription +
						" matches rule condition: <= " + ruleValue);
				}
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("No condition defined for device's max " +
					logDescription + "!");
			}
		}

		if (Validator.isNotNull(ruleStringValueMin)) {
			ruleValue = GetterUtil.getInteger(ruleStringValueMin);

			if (value < ruleValue) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skipping rule '" + logRuleName +
						"'! Device's " + logDescription + " < min " +
						logRuleName + "(" + ruleValue + ")");
				}

				return false;
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Device's " + logDescription +
						" matches rule condition: >= " + ruleValue);
				}
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("No condition defined for device's min " +
					logDescription + "!");
			}
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(SimpleRuleHandler.class);

	static {
		_propertyNames = new ArrayList<String>(10);

		_propertyNames.add(PROPERTY_OS);
		_propertyNames.add(PROPERTY_DISPLAY_MAX_WIDTH);
		_propertyNames.add(PROPERTY_DISPLAY_MIN_WIDTH);
		_propertyNames.add(PROPERTY_DISPLAY_MAX_HEIGHT);
		_propertyNames.add(PROPERTY_DISPLAY_MIN_HEIGHT);
		_propertyNames.add(PROPERTY_RESOLUTION_MAX_WIDTH);
		_propertyNames.add(PROPERTY_RESOLUTION_MIN_WIDTH);
		_propertyNames.add(PROPERTY_RESOLUTION_MAX_HEIGHT);
		_propertyNames.add(PROPERTY_RESOLUTION_MIN_HEIGHT);
		_propertyNames.add(PROPERTY_TABLET);

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}

	private static Collection<String> _propertyNames;

}