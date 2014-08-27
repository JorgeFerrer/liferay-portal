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
		Integer rawOffset1 = timeZone1.getRawOffset();
		Integer rawOffset2 = timeZone2.getRawOffset();

		int value = rawOffset1.compareTo(rawOffset2);

		if (value == 0) {
			String timeZoneID1 = timeZone1.getID();
			String timeZoneID2 = timeZone2.getID();

			value = timeZoneID1.compareTo(timeZoneID2);
		}

		return value;
	}

}