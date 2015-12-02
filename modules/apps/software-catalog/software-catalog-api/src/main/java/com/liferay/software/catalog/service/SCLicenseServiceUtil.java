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
 * Provides the remote service utility for SCLicense. This utility wraps
 * {@link com.liferay.software.catalog.service.impl.SCLicenseServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SCLicenseService
 * @see com.liferay.software.catalog.service.base.SCLicenseServiceBaseImpl
 * @see com.liferay.software.catalog.service.impl.SCLicenseServiceImpl
 * @generated
 */
@ProviderType
public class SCLicenseServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.software.catalog.service.impl.SCLicenseServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.software.catalog.model.SCLicense addLicense(
		java.lang.String name, java.lang.String url, boolean openSource,
		boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLicense(name, url, openSource, active, recommended);
	}

	public static void deleteLicense(long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteLicense(licenseId);
	}

	public static com.liferay.software.catalog.model.SCLicense getLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLicense(licenseId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.software.catalog.model.SCLicense updateLicense(
		long licenseId, java.lang.String name, java.lang.String url,
		boolean openSource, boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLicense(licenseId, name, url, openSource, active,
			recommended);
	}

	public static SCLicenseService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(SCLicenseService service) {
	}

	private static ServiceTracker<SCLicenseService, SCLicenseService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SCLicenseServiceUtil.class);

		_serviceTracker = new ServiceTracker<SCLicenseService, SCLicenseService>(bundle.getBundleContext(),
				SCLicenseService.class, null);

		_serviceTracker.open();
	}
}