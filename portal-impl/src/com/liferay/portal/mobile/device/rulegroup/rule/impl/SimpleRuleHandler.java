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
import java.util.Collection;
import java.util.Collections;

/**
 * @author Edward Han
 * @author Milen Daynkov
 */
public class SimpleRuleHandler implements RuleHandler {

	public static final String PROPERTY_MAX_SUFFIX = "-max";

	public static final String PROPERTY_MIN_SUFFIX = "-min";

	public static final String PROPERTY_OS = "os";

	public static final String PROPERTY_SCREEN_PHYSICAL_HEIGHT_PREFIX =
		"screen-physical-height";

	public static final String PROPERTY_SCREEN_PHYSICAL_WIDTH_PREFIX =
		"screen-physical-width";

	public static final String PROPERTY_SCREEN_RESOLUTION_HEIGHT_PREFIX =
		"screen-resolution-height";

	public static final String PROPERTY_SCREEN_RESOLUTION_WIDTH_PREFIX =
		"screen-resolution-width";

	public static final String PROPERTY_TABLET = "tablet";

	public static String getHandlerType() {
		return SimpleRuleHandler.class.getName();
	}

	@Override
	public boolean evaluateRule(MDRRule mdrRule, ThemeDisplay themeDisplay) {
		Device device = themeDisplay.getDevice();

		if (device == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Rule evaluation is not possible because the information " +
						"about the device is not available");
			}

			return false;
		}

		if (!isValidMultiValue(mdrRule, PROPERTY_OS, device.getOS())) {
			return false;
		}

		if (!isValidBooleanValue(mdrRule, PROPERTY_TABLET, device.isTablet())) {
			return false;
		}

		Dimensions screenPhysicalSize = device.getScreenPhysicalSize();

		if (!isValidRangeValue(
				mdrRule, PROPERTY_SCREEN_PHYSICAL_HEIGHT_PREFIX,
				screenPhysicalSize.getHeight())) {

			return false;
		}

		if (!isValidRangeValue(
				mdrRule, PROPERTY_SCREEN_PHYSICAL_WIDTH_PREFIX,
				screenPhysicalSize.getWidth())) {

			return false;
		}

		Dimensions screenResolution = device.getScreenResolution();

		if (!isValidRangeValue(
				mdrRule, PROPERTY_SCREEN_RESOLUTION_HEIGHT_PREFIX,
				screenResolution.getHeight())) {

			return false;
		}

		if (!isValidRangeValue(
				mdrRule, PROPERTY_SCREEN_RESOLUTION_WIDTH_PREFIX,
				screenResolution.getWidth())) {

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

	protected StringBundler getLogStringBundler(
		MDRRule mdrRule, String property, String value, boolean valid) {

		StringBundler sb = new StringBundler();

		sb.append("Rule ");
		sb.append(mdrRule.getNameCurrentValue());
		sb.append(": Value for '");
		sb.append(property);
		sb.append("' is '");
		sb.append(value);
		sb.append("' which is");

		if (!valid) {
			sb.append(" NOT ");
		}

		return sb;
	}

	protected boolean isValidBooleanValue(
		MDRRule mdrRule, String property, boolean value) {

		UnicodeProperties typeSettingsProperties =
			mdrRule.getTypeSettingsProperties();

		String validValueString = typeSettingsProperties.get(property);

		if (Validator.isNull(validValueString)) {
			return true;
		}

		boolean ruleValue = GetterUtil.getBoolean(validValueString);

		if (ruleValue != value) {
			logBooleanValue(mdrRule, property, value, false);

			return false;
		}

		logBooleanValue(mdrRule, property, value, true);

		return true;
	}

	protected boolean isValidMultiValue(
		MDRRule mdrRule, String property, String value) {

		UnicodeProperties typeSettingsProperties =
			mdrRule.getTypeSettingsProperties();

		String validValueString = typeSettingsProperties.get(property);

		if (Validator.isNull(validValueString)) {
			return true;
		}

		String[] validValues = StringUtil.split(validValueString);

		if (!ArrayUtil.contains(validValues, value)) {
			logMultiValue(mdrRule, property, value, validValues, false);

			return false;
		}

		logMultiValue(mdrRule, property, value, validValues, true);

		return true;
	}

	protected boolean isValidRangeValue(
		MDRRule mdrRule, String property, float value) {

		UnicodeProperties typeSettingsProperties =
			mdrRule.getTypeSettingsProperties();

		String max = typeSettingsProperties.get(property + PROPERTY_MAX_SUFFIX);
		String min = typeSettingsProperties.get(property + PROPERTY_MIN_SUFFIX);

		if (Validator.isNull(max) && Validator.isNull(min)) {
			logRangeValue(mdrRule, property, value, max, min, true);

			return true;
		}

		if (Validator.isNotNull(max)) {
			float maxFloat = GetterUtil.getFloat(max);

			if (value > maxFloat) {
				logRangeValue(mdrRule, property, value, max, min, false);

				return false;
			}

			logRangeValue(mdrRule, property, value, max, min, true);
		}

		if (Validator.isNotNull(min)) {
			float minFloat = GetterUtil.getFloat(min);

			if (value < minFloat) {
				logRangeValue(mdrRule, property, value, max, min, false);

				return false;
			}

			logRangeValue(mdrRule, property, value, max, min, true);
		}

		return true;
	}

	protected void logBooleanValue(
		MDRRule mdrRule, String property, boolean value, boolean valid) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		StringBundler sb = getLogStringBundler(
			mdrRule, property, String.valueOf(value), valid);

		sb.append("valid.");

		_log.debug(sb.toString());
	}

	protected void logMultiValue(
		MDRRule mdrRule, String property, String value, String[] validValues,
		boolean valid) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		StringBundler sb = getLogStringBundler(mdrRule, property, value, valid);

		sb.append("within the allowed values (");
		sb.append(StringUtil.merge(validValues));
		sb.append(")");

		_log.debug(sb.toString());
	}

	protected void logRangeValue(
		MDRRule mdrRule, String property, float value, String max, String min,
		boolean valid) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		StringBundler sb = getLogStringBundler(
			mdrRule, property, String.valueOf(value), valid);

		sb.append("within the allowed range");

		if (Validator.isNotNull(max) && Validator.isNotNull(min)) {
			sb.append(" (");
			sb.append(min);
			sb.append(", ");
			sb.append(max);
			sb.append(")");
		}

		_log.debug(sb.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(SimpleRuleHandler.class);
	private static Collection<String> _propertyNames;

	static {
		_propertyNames = new ArrayList<String>(10);

		_propertyNames.add(PROPERTY_OS);
		_propertyNames.add(
			PROPERTY_SCREEN_PHYSICAL_WIDTH_PREFIX + PROPERTY_MAX_SUFFIX);
		_propertyNames.add(
			PROPERTY_SCREEN_PHYSICAL_WIDTH_PREFIX + PROPERTY_MIN_SUFFIX);
		_propertyNames.add(
			PROPERTY_SCREEN_PHYSICAL_HEIGHT_PREFIX + PROPERTY_MAX_SUFFIX);
		_propertyNames.add(
			PROPERTY_SCREEN_PHYSICAL_HEIGHT_PREFIX + PROPERTY_MIN_SUFFIX);
		_propertyNames.add(
			PROPERTY_SCREEN_RESOLUTION_WIDTH_PREFIX + PROPERTY_MAX_SUFFIX);
		_propertyNames.add(
			PROPERTY_SCREEN_RESOLUTION_WIDTH_PREFIX + PROPERTY_MIN_SUFFIX);
		_propertyNames.add(
			PROPERTY_SCREEN_RESOLUTION_HEIGHT_PREFIX + PROPERTY_MAX_SUFFIX);
		_propertyNames.add(
			PROPERTY_SCREEN_RESOLUTION_HEIGHT_PREFIX + PROPERTY_MIN_SUFFIX);
		_propertyNames.add(PROPERTY_TABLET);

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}

}