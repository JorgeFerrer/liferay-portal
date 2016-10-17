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

package com.liferay.portal.configuration.module.configuration.internal;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class ConfigurationInvocationHandler<S> implements InvocationHandler {

	public ConfigurationInvocationHandler(
		Class<S> clazz, Object configurationOverrideInstance,
		TypedSettings typedSettings) {

		_clazz = clazz;
		_configurationOverrideInstance = configurationOverrideInstance;
		_typedSettings = typedSettings;
	}

	public S createProxy() {
		return (S)ProxyUtil.newProxyInstance(
			_clazz.getClassLoader(), new Class[] {_clazz}, this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws InvocationTargetException {

		if (_configurationOverrideInstance != null) {
			try {
				return _invokeConfigurationOverride(method, args);
			}
			catch (InvocationTargetException ite) {
				throw ite;
			}
			catch (Exception e) {
			}
		}

		try {
			return _invokeTypedSettings(method);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object _getValue(
			Class<?> returnType, String key, boolean getReturnTypeDefault)
		throws NoSuchMethodException, IllegalAccessException,
		InvocationTargetException, InstantiationException {

		if (!getReturnTypeDefault &&
			(returnType.equals(boolean.class) ||
			 returnType.equals(double.class) ||
			 returnType.equals(float.class) || returnType.equals(int.class) ||
			 returnType.equals(long.class))) {

			String value = _typedSettings.getValue(key, null);

			if (value == null) {
				return value;
			}

			if (returnType.equals(boolean.class)) {
				return GetterUtil.getBoolean(value);
			}
			else if (returnType.equals(double.class)) {
				return GetterUtil.getDouble(value);
			}
			else if (returnType.equals(float.class)) {
				return GetterUtil.getFloat(value);
			}
			else if (returnType.equals(int.class)) {
				return GetterUtil.getInteger(value);
			}
			else if (returnType.equals(long.class)) {
				return GetterUtil.getLong(value);
			}
		}
		else if (returnType.equals(boolean.class)) {
			return _typedSettings.getBooleanValue(key);
		}
		else if (returnType.equals(double.class)) {
			return _typedSettings.getDoubleValue(key);
		}
		else if (returnType.equals(float.class)) {
			return _typedSettings.getFloatValue(key);
		}
		else if (returnType.equals(int.class)) {
			return _typedSettings.getIntegerValue(key);
		}
		else if (returnType.equals(LocalizedValuesMap.class)) {
			LocalizedValuesMap value = _typedSettings.getLocalizedValuesMap(
				key);

			if (getReturnTypeDefault) {
				return value;
			}

			Map<Locale, String> valuesMap = value.getValues();

			Collection<String> values = valuesMap.values();

			if (values.isEmpty()) {
				return null;
			}
		}
		else if (returnType.equals(long.class)) {
			return _typedSettings.getLongValue(key);
		}
		else if (returnType.equals(String.class)) {
			if (getReturnTypeDefault) {
				return _typedSettings.getValue(key);
			}

			return _typedSettings.getValue(key, null);
		}
		else if (returnType.equals(String[].class)) {
			if (getReturnTypeDefault) {
				return _typedSettings.getValues(key);
			}

			return _typedSettings.getValues(key, null);
		}
		else if (returnType.isEnum()) {
			Method valueOfMethod = returnType.getDeclaredMethod(
				"valueOf", String.class);

			String value = _typedSettings.getValue(key);

			if (value == null) {
				return value;
			}

			return valueOfMethod.invoke(returnType, value);
		}

		Constructor<?> constructor = returnType.getConstructor(String.class);

		return constructor.newInstance(_typedSettings.getValue(key));
	}

	private Object _invokeConfigurationOverride(Method method, Object[] args)
		throws IllegalAccessException, InvocationTargetException,
			   NoSuchMethodException {

		Class<?> clazz = _configurationOverrideInstance.getClass();

		method = clazz.getMethod(method.getName(), method.getParameterTypes());

		return method.invoke(_configurationOverrideInstance, args);
	}

	private Object _invokeTypedSettings(Method method)
		throws NoSuchMethodException, IllegalAccessException,
			   InvocationTargetException, InstantiationException {

		Class<?> returnType = method.getReturnType();

		Object value = null;

		Meta.AD annotation = method.getAnnotation(Meta.AD.class);

		if ((annotation != null) && !_NULL_VALUE.equals(annotation.id())) {
			value = _getValue(returnType, annotation.id(), false);
		}

		if (value == null) {
			return _getValue(returnType, method.getName(), true);
		}

		return value;
	}

	private static final String _NULL_VALUE = "§NULL§";

	private final Class<S> _clazz;
	private final Object _configurationOverrideInstance;
	private final TypedSettings _typedSettings;

}