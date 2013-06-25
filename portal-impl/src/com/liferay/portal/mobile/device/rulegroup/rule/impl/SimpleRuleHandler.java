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
import com.liferay.portal.kernel.mobile.device.Dimensions;
import com.liferay.portal.kernel.mobile.device.rulegroup.rule.RuleHandler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
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

	public static final String PROPERTY_OS = "os";

	public static final String PROPERTY_SCREEN_PHYSICAL_HEIGHT_MAX =
		"screen-physical-height-max";

	public static final String PROPERTY_SCREEN_PHYSICAL_HEIGHT_MIN =
		"screen-physical-height-min";

	public static final String PROPERTY_SCREEN_PHYSICAL_WIDTH_MAX =
		"screen-physical-width-max";

	public static final String PROPERTY_SCREEN_PHYSICAL_WIDTH_MIN =
		"screen-physical-width-min";

	public static final String PROPERTY_SCREEN_RESOLUTION_HEIGHT_MAX =
		"screen-resolution-height-max";

	public static final String PROPERTY_SCREEN_RESOLUTION_HEIGHT_MIN =
		"screen-resolution-height-min";

	public static final String PROPERTY_SCREEN_RESOLUTION_WIDTH_MAX =
		"screen-resolution-width-max";

	public static final String PROPERTY_SCREEN_RESOLUTION_WIDTH_MIN =
		"screen-resolution-width-min";

	public static final String PROPERTY_TABLET = "tablet";

	public static String getHandlerType() {

		return SimpleRuleHandler.class.getName();
	}

	@Override
	public boolean evaluateRule(MDRRule mdrRule, ThemeDisplay themeDisplay) {

		Device device = themeDisplay.getDevice();

		if (device == null) {
			if (_log.isDebugEnabled()) {
				StringBuilder sb =
					new StringBuilder("Skipping rule '").append(
						mdrRule.getNameCurrentValue()).append(
						"'! Mising device information!");
				_log.debug(sb.toString());
			}

			return false;
		}

		UnicodeProperties typeSettingsProperties =
			mdrRule.getTypeSettingsProperties();

		String os = typeSettingsProperties.get(PROPERTY_OS);

		if (Validator.isNotNull(os)) {
			String[] operatingSystems = StringUtil.split(os);

			if (!ArrayUtil.contains(operatingSystems, device.getOS())) {
				logRuleStatus(
					mdrRule, RuleStatus.NO_MATCH, PROPERTY_OS, device.getOS(),
					"one of ", operatingSystems);
				return false;
			}

			logRuleStatus(
				mdrRule, RuleStatus.MATCH, PROPERTY_OS, device.getOS(),
				"one of ", operatingSystems);
		}
		else {
			logRuleStatus(
				mdrRule, RuleStatus.NO_CONDITION, PROPERTY_OS, device.getOS(),
				"one of ");
		}

		String tablet = typeSettingsProperties.get(PROPERTY_TABLET);

		if (Validator.isNotNull(tablet)) {
			boolean tabletBoolean = GetterUtil.getBoolean(tablet);

			if (tabletBoolean != device.isTablet()) {
				logRuleStatus(
					mdrRule, RuleStatus.NO_MATCH, PROPERTY_TABLET,
					String.valueOf(device.isTablet()), "=", tablet);
				return false;
			}

			logRuleStatus(
				mdrRule, RuleStatus.MATCH, PROPERTY_TABLET,
				String.valueOf(device.isTablet()), "=", tablet);
		}
		else {
			logRuleStatus(
				mdrRule, RuleStatus.NO_CONDITION, PROPERTY_TABLET,
				String.valueOf(device.isTablet()), "=");
		}

		Dimensions screenPhysicalSize = device.getScreenPhysicalSize();

		if (!isValidValue(
				mdrRule, "screen physical height",
				screenPhysicalSize.getHeight(),
				typeSettingsProperties.get(
					PROPERTY_SCREEN_PHYSICAL_HEIGHT_MAX),
				typeSettingsProperties.get(
					PROPERTY_SCREEN_PHYSICAL_HEIGHT_MIN))) {

			return false;
		}

		if (!isValidValue(
				mdrRule, "screen physical width", screenPhysicalSize.getWidth(),
				typeSettingsProperties.get(
					PROPERTY_SCREEN_PHYSICAL_WIDTH_MAX),
				typeSettingsProperties.get(
					PROPERTY_SCREEN_PHYSICAL_WIDTH_MIN))) {

			return false;
		}

		Dimensions screenResolution = device.getScreenResolution();

		if (!isValidValue(
				mdrRule, "screen resolution height",
				screenResolution.getHeight(),
				typeSettingsProperties.get(
					PROPERTY_SCREEN_RESOLUTION_HEIGHT_MAX),
				typeSettingsProperties.get(
					PROPERTY_SCREEN_RESOLUTION_HEIGHT_MIN))) {

			return false;
		}

		if (!isValidValue(
				mdrRule, "screen resolution width", screenResolution.getWidth(),
				typeSettingsProperties.get(
					PROPERTY_SCREEN_RESOLUTION_WIDTH_MAX),
				typeSettingsProperties.get(
					PROPERTY_SCREEN_RESOLUTION_WIDTH_MIN))) {

			return false;
		}

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

	protected boolean isValidValue(
		MDRRule rule, String capability, float value, String max, String min) {

		if (Validator.isNull(max) && Validator.isNull(min)) {
			logRuleStatus(rule, RuleStatus.NO_CONDITION, capability,
			String.valueOf(value), "");
			return true;
		}

		if (Validator.isNotNull(max)) {
			float maxFloat = GetterUtil.getFloat(max);

			if (value > maxFloat) {
				logRuleStatus(
					rule, RuleStatus.NO_MATCH, capability,
					String.valueOf(value), "<= ", String.valueOf(maxFloat));
				return false;
			}

			logRuleStatus(
				rule, RuleStatus.MATCH, capability, String.valueOf(value),
				"<= ", String.valueOf(maxFloat));
		}

		if (Validator.isNotNull(min)) {
			float minFloat = GetterUtil.getFloat(min);

			if (value < minFloat) {
				logRuleStatus(
					rule, RuleStatus.NO_MATCH, capability,
					String.valueOf(value), ">= ", String.valueOf(minFloat));
				return false;
			}

			logRuleStatus(
				rule, RuleStatus.MATCH, capability, String.valueOf(value),
				">= ", String.valueOf(minFloat));
		}

		return true;
	}

	protected void logRuleStatus(
		MDRRule rule, RuleStatus status, String capability, String value,
		String condition, String... expectedValues) {

		if (rule == null)
			return;

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler();

			switch (status) {

			case MATCH:
				sb.append("Processing rule '").append(
					rule.getNameCurrentValue()).append(
					"'! Device's capability '").append(capability).append(
					"' is '").append(value).append("' which matches");

				if (expectedValues != null) {
					String expected;

					if (expectedValues.length > 1) {
						expected = Arrays.toString(expectedValues);
					}
					else {
						expected = expectedValues[0];
					}

					sb.append(" the rule condition '").append(condition).append(
						expected).append("'!");
				}
				else {
					sb.append(" the rule condition!");
				}

				break;

			case NO_MATCH:
				sb.append("Skipping rule '").append(
					rule.getNameCurrentValue()).append(
						"'! Device's capability '").append(capability).append(
					"' is '").append(value).append("' which does not match");

				if (expectedValues != null) {
					String expected;

					if (expectedValues.length > 1) {
						expected = Arrays.toString(expectedValues);
					}
					else {
						expected = expectedValues[0];
					}

					sb.append(" the rule condition '").append(condition).append(
						expected).append("'!");
				}
				else {
					sb.append(" the rule condition!");
				}

				break;

			case NO_CONDITION:

				sb.append("Processing rule '").append(
					rule.getNameCurrentValue()).append(
					"'! Device's capability '").append(capability).append(
					"' is '").append(value).append(
					"'! The rule is valid for any value!");

				break;
			}

			if (sb.length() > 0) {
				_log.debug(sb.toString());
			}

		}

	}

	private static Log _log = LogFactoryUtil.getLog(SimpleRuleHandler.class);

	private static Collection<String> _propertyNames;

	private enum RuleStatus {MATCH, NO_CONDITION, NO_MATCH};

	static {
		_propertyNames = new ArrayList<String>(10);

		_propertyNames.add(PROPERTY_OS);
		_propertyNames.add(PROPERTY_SCREEN_PHYSICAL_WIDTH_MAX);
		_propertyNames.add(PROPERTY_SCREEN_PHYSICAL_WIDTH_MIN);
		_propertyNames.add(PROPERTY_SCREEN_PHYSICAL_HEIGHT_MAX);
		_propertyNames.add(PROPERTY_SCREEN_PHYSICAL_HEIGHT_MIN);
		_propertyNames.add(PROPERTY_SCREEN_RESOLUTION_WIDTH_MAX);
		_propertyNames.add(PROPERTY_SCREEN_RESOLUTION_WIDTH_MIN);
		_propertyNames.add(PROPERTY_SCREEN_RESOLUTION_HEIGHT_MAX);
		_propertyNames.add(PROPERTY_SCREEN_RESOLUTION_HEIGHT_MIN);
		_propertyNames.add(PROPERTY_TABLET);

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}

}