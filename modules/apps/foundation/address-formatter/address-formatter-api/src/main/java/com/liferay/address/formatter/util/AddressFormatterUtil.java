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

package com.liferay.address.formatter.util;

import com.liferay.address.formatter.AddressFormatter;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = AddressFormatterUtil.class)
public class AddressFormatterUtil {

	public static String format(Address address) {
		Country country = address.getCountry();

		String a2 = country.getA2();

		AddressFormatter addressFormatter = getAddressFormatter(a2);

		if (addressFormatter == null) {
			addressFormatter = getAddressFormatter("US");
		}

		return addressFormatter.format(address);
	}

	public static AddressFormatter getAddressFormatter(String countryA2) {
		return _instance._serviceTrackerMap.getService(countryA2);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AddressFormatter.class, "country");

		_instance = this;
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();

		_instance = null;
	}

	private static AddressFormatterUtil _instance;

	private ServiceTrackerMap<String, AddressFormatter> _serviceTrackerMap;

}