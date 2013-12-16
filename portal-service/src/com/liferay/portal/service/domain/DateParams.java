/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.domain;

import aQute.bnd.annotation.ProviderType;

@ProviderType
public final class DateParams {
	public static DateParams never() {
		if (_NEVER == null) {
			DateParams dateParams = new DateParams(-1, -1, -1);

			_NEVER = dateParams;
		}

		return _NEVER;
	}

	public DateParams(int year, int month, int day) {
		this(year, day, month, -1, -1);
	}

	public DateParams(int year, int month, int day, int hour, int minute) {
		_year = year;
		_month = month;
		_day = day;
		_hour = hour;
		_minute = minute;
	}

	public int getDay() {
		return _day;
	}

	public int getHour() {
		return _hour;
	}

	public int getMinute() {
		return _minute;
	}

	public int getMonth() {
		return _month;
	}

	public int getYear() {
		return _year;
	}

	public boolean isNever() {
		return
			(_year == -1) && (_month == -1) && (_day == -1) && (_hour == -1) &&
			(_minute == -1);
	}

	private final int _day;
	private final int _hour;
	private final int _month;
	private final int _minute;
	private final int _year;

	private static DateParams _NEVER = null;

}
