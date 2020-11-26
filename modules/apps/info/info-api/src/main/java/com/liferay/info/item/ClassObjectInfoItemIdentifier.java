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

package com.liferay.info.item;

import com.liferay.petra.string.StringBundler;

import java.util.Objects;

/**
 * @author Pavel Savinov
 */
public class ClassObjectInfoItemIdentifier extends BaseInfoItemIdentifier {

	public ClassObjectInfoItemIdentifier(String className, long classPK) {
		_className = className;
		_classPK = classPK;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ClassObjectInfoItemIdentifier)) {
			return false;
		}

		ClassObjectInfoItemIdentifier classObjectInfoItemIdentifier =
			(ClassObjectInfoItemIdentifier)object;

		if (!Objects.equals(
				_className, classObjectInfoItemIdentifier._className) ||
			!Objects.equals(_classPK, classObjectInfoItemIdentifier._classPK)) {

			return false;
		}

		return true;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_className, _classPK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{className=");
		sb.append(_className);
		sb.append(", classPK=");
		sb.append(_classPK);
		sb.append("}");

		return sb.toString();
	}

	private final String _className;
	private final long _classPK;

}