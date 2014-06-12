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

package com.liferay.portal.kernel.captcha;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class CaptchaUtil {

	public static void check(HttpServletRequest request)
		throws CaptchaException {

		_instance._check(request);
	}

	public static void check(PortletRequest portletRequest)
		throws CaptchaException {

		_instance._check(portletRequest);
	}

	public static String getTaglibPath() {
		return _instance._getTaglibPath();
	}

	public static boolean isEnabled(HttpServletRequest request)
		throws CaptchaException {

		return _instance._isEnabled(request);
	}

	public static boolean isEnabled(PortletRequest portletRequest)
		throws CaptchaException {

		return _instance._isEnabled(portletRequest);
	}

	public static void serveImage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		_instance._serveImage(request, response);
	}

	public static void serveImage(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		_instance._serveImage(resourceRequest, resourceResponse);
	}

	public CaptchaUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(Captcha.class);

		_serviceTracker.open();
	}

	public void _check(HttpServletRequest request)
		throws CaptchaException {

		if (_serviceTracker.isEmpty()) {
			return;
		}

		_get().check(request);
	}

	public void _check(PortletRequest portletRequest)
		throws CaptchaException {

		if (_serviceTracker.isEmpty()) {
			return;
		}

		_get().check(portletRequest);
	}

	public String _getTaglibPath() {
		if (_serviceTracker.isEmpty()) {
			return null;
		}

		return _get().getTaglibPath();
	}

	public boolean _isEnabled(HttpServletRequest request)
		throws CaptchaException {

		if (_serviceTracker.isEmpty()) {
			return false;
		}

		return _get().isEnabled(request);
	}

	public boolean _isEnabled(PortletRequest portletRequest)
		throws CaptchaException {

		if (_serviceTracker.isEmpty()) {
			return false;
		}

		return _get().isEnabled(portletRequest);
	}

	public void _serveImage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		if (_serviceTracker.isEmpty()) {
			return;
		}

		_get().serveImage(request, response);
	}

	public void _serveImage(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		if (_serviceTracker.isEmpty()) {
			return;
		}

		Captcha captcha = _serviceTracker.getService();

		captcha.serveImage(resourceRequest, resourceResponse);
	}

	private Captcha _get() {
		return _serviceTracker.getService();
	}

	private static CaptchaUtil _instance = new CaptchaUtil();

	private ServiceTracker<Captcha, Captcha> _serviceTracker;

}