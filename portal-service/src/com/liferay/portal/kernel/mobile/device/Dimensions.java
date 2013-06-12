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

package com.liferay.portal.kernel.mobile.device;

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * @author Milen Dyankov
 * @author Michael C. Han
 */
public class Dimensions implements Serializable, Comparable<Dimensions> {

	public static final Dimensions UNKNOWN = new Dimensions(-1, -1);

	public Dimensions(int height, int width) {

		_height = height;
		_width = width;
	}

	@Override
	public int compareTo(Dimensions o) {

		if (_width != o.getWidth()) {
			return _width - o.getWidth();
		}
		else {
			if (_height != o.getHeight()) {
				return _height - o.getHeight();
			}
			else {
				return 0;
			}
		}
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;
		Dimensions other = (Dimensions)obj;

		if (_height != other._height)
			return false;

		if (_width != other._width)
			return false;
		return true;
	}

	public int getHeight() {

		return _height;
	}

	public int getWidth() {

		return _width;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + _height;
		result = prime * result + _width;
		return result;
	}

	@Override
	public String toString() {

		StringBundler sb = new StringBundler(5);

		sb.append("{height=");
		sb.append(_height);
		sb.append(", width=");
		sb.append(_width);
		sb.append("}");

		return sb.toString();
	}

	private int _height;
	private int _width;

}