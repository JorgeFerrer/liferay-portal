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

package com.liferay.portal.kernel.util;

import java.util.Comparator;
import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 */
public class TimeZoneComparator implements Comparator<TimeZone> {

	public TimeZoneComparator() {
	}

	@Override
	public int compare(TimeZone timeZone1, TimeZone timeZone2) {
		Integer totalOffset1 =
			timeZone1.getRawOffset() + timeZone1.getDSTSavings();
		Integer totalOffset2 =
			timeZone2.getRawOffset() + timeZone2.getDSTSavings();

		int value = totalOffset1.compareTo(totalOffset2);

		if (value == 0) {
			Integer timeZoneDSTSavings1 = timeZone1.getDSTSavings();
			Integer timeZoneDSTSavings2 = timeZone2.getDSTSavings();

			value = timeZoneDSTSavings1.compareTo(timeZoneDSTSavings2);
		}

		if (value == 0) {
			String timeZoneID1 = timeZone1.getID();
			String timeZoneID2 = timeZone2.getID();

			value = timeZoneID1.compareTo(timeZoneID2);
		}

		return value;
	}

}