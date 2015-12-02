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

package com.liferay.software.catalog.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for SCFrameworkVersion. This utility wraps
 * {@link com.liferay.software.catalog.service.impl.SCFrameworkVersionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersionService
 * @see com.liferay.software.catalog.service.base.SCFrameworkVersionServiceBaseImpl
 * @see com.liferay.software.catalog.service.impl.SCFrameworkVersionServiceImpl
 * @generated
 */
@ProviderType
public class SCFrameworkVersionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.software.catalog.service.impl.SCFrameworkVersionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.software.catalog.model.SCFrameworkVersion addFrameworkVersion(
		java.lang.String name, java.lang.String url, boolean active,
		int priority, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFrameworkVersion(name, url, active, priority,
			serviceContext);
	}

	public static void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFrameworkVersion(frameworkVersionId);
	}

	public static com.liferay.software.catalog.model.SCFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFrameworkVersion(frameworkVersionId);
	}

	public static java.util.List<com.liferay.software.catalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active) {
		return getService().getFrameworkVersions(groupId, active);
	}

	public static java.util.List<com.liferay.software.catalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active, int start, int end) {
		return getService().getFrameworkVersions(groupId, active, start, end);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.software.catalog.model.SCFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFrameworkVersion(frameworkVersionId, name, url,
			active, priority);
	}

	public static SCFrameworkVersionService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(SCFrameworkVersionService service) {
	}

	private static ServiceTracker<SCFrameworkVersionService, SCFrameworkVersionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SCFrameworkVersionServiceUtil.class);

		_serviceTracker = new ServiceTracker<SCFrameworkVersionService, SCFrameworkVersionService>(bundle.getBundleContext(),
				SCFrameworkVersionService.class, null);

		_serviceTracker.open();
	}
}