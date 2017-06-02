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

package com.liferay.address.formatter;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionService;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
public abstract class BaseAddressFormatter implements AddressFormatter {

	public String getCity(Address address) {
		return address.getCity();
	}

	public String getCountry(Address address) {
		long countryId = address.getCountryId();

		Country country = countryService.fetchCountry(countryId);

		if (country != null) {
			return country.getName();
		}

		return null;
	}

	public String getRegion(Address address) {
		long regionId = address.getRegionId();

		Region region = regionService.fetchRegion(regionId);

		if (region != null) {
			return region.getName();
		}

		return null;
	}

	public String getStreet1(Address address) {
		return address.getStreet1();
	}

	public String getStreet2(Address address) {
		return address.getStreet2();
	}

	public String getStreet3(Address address) {
		return address.getStreet3();
	}

	public String getZip(Address address) {
		return address.getZip();
	}

	@Reference
	protected CountryService countryService;

	@Reference
	protected RegionService regionService;

}