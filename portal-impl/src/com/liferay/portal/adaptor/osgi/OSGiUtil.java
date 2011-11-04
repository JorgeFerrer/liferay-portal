/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.adaptor.osgi;

import com.liferay.portal.kernel.adaptor.Adaptor;
import com.liferay.portal.kernel.adaptor.AdaptorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;

import java.io.InputStream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.startlevel.BundleStartLevel;

/**
 * @author Raymond Augé
 */
public class OSGiUtil {

	public Object addBundle(String location) throws PortalException {
		return addBundle(location, null);
	}

	public Object addBundle(String location, InputStream inputStream)
		throws PortalException {

		checkPermission();

		BundleContext bundleContext = (BundleContext)getBundleContext();

		try {
			return bundleContext.installBundle(location, inputStream);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	public Object getBundleContext() {
		Framework framework = (Framework)getFramework();

		return framework.getBundleContext();
	}

	public Object getFramework() {
		if (_framework != null) {
			return _framework;
		}

		try {
			Adaptor adaptor = AdaptorUtil.getAdaptor();

			if ((adaptor != null) && (adaptor instanceof OSGiAdaptor)) {
				OSGiAdaptor osgiAdaptor = (OSGiAdaptor)adaptor;

				_framework = osgiAdaptor.getFramework();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return _framework;
	}

	public String getState(long bundleId) throws PortalException {
		checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		int state = bundle.getState();

		if (state == Bundle.ACTIVE) {
			return "active";
		}
		else if (state == Bundle.INSTALLED) {
			return "installed";
		}
		else if (state == Bundle.RESOLVED) {
			return "resolved";
		}
		else if (state == Bundle.STARTING) {
			return "starting";
		}
		else if (state == Bundle.STOPPING) {
			return "stopping";
		}
		else if (state == Bundle.UNINSTALLED) {
			return "uninstalled";
		}
		else {
			return StringPool.BLANK;
		}
	}

	public void setBundleStartLevel(long bundleId, int startLevel)
		throws PortalException {

		checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		BundleStartLevel bundleStartLevel = (BundleStartLevel)bundle;

		bundleStartLevel.setStartLevel(startLevel);
	}

	public void startBundle(long bundleId) throws PortalException {
		checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.start();
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	public void startBundle(long bundleId, int options) throws PortalException {
		checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.start(options);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	public void stopBundle(long bundleId) throws PortalException {
		checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.stop();
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	public void stopBundle(long bundleId, int options) throws PortalException {
		checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.stop(options);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	public void uninstallBundle(long bundleId) throws PortalException {
		checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.uninstall();
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	public void updateBundle(long bundleId) throws PortalException {
		checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.update();
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	public void updateBundle(long bundleId, InputStream inputStream)
		throws PortalException {

		checkPermission();

		Bundle bundle = getBundle(bundleId);

		if (bundle == null) {
			throw new OSGiException("No bundle with ID " + bundleId);
		}

		try {
			bundle.update(inputStream);
		}
		catch (BundleException be) {
			_log.error(be, be);

			throw new OSGiException(be);
		}
	}

	protected void checkPermission() throws PrincipalException {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker == null) || !permissionChecker.isOmniadmin()) {
			throw new PrincipalException();
		}
	}

	protected Bundle getBundle(long bundleId) {
		BundleContext bundleContext = (BundleContext)getBundleContext();

		return bundleContext.getBundle(bundleId);
	}

	private static final Log _log = LogFactoryUtil.getLog(OSGiUtil.class);

	private Framework _framework;

}