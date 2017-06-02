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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true, property = {"country=US"},
	service = AddressFormatter.class
)
public class USAddressFormatter extends BaseAddressFormatter {

	public String format(Address address) {
		StringBundler sb = new StringBundler(14);

		if (Validator.isNotNull(getStreet1(address))) {
			sb.append(getStreet1(address));
		}

		if (Validator.isNotNull(getStreet2(address))) {
			sb.append(StringPool.NEW_LINE);
			sb.append(getStreet2(address));
		}

		if (Validator.isNotNull(getStreet3(address))) {
			sb.append(StringPool.NEW_LINE);
			sb.append(getStreet3(address));
		}

		sb.append(StringPool.NEW_LINE);

		if (Validator.isNotNull(getCity(address))) {
			sb.append(getCity(address));
		}

		if (Validator.isNotNull(getRegion(address))) {
			sb.append(StringPool.COMMA);
			sb.append(StringPool.SPACE);
			sb.append(getRegion(address));
		}

		if (Validator.isNotNull(getZip(address))) {
			sb.append(StringPool.SPACE);
			sb.append(getZip(address));
		}

		if (Validator.isNotNull(getCountry(address))) {
			sb.append(StringPool.NEW_LINE);
			sb.append(getCountry(address));
		}

		return sb.toString();
	}

}