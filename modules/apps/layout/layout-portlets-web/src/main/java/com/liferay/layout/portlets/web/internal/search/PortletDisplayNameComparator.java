/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.portlets.web.internal.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Jorge Ferrer
 */
public class PortletDisplayNameComparator extends OrderByComparator<Portlet> {

	public PortletDisplayNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Portlet portlet1, Portlet portlet2) {
		String portletDisplayName1 = StringPool.BLANK;

		if (portlet1 != null) {
			portletDisplayName1 = GetterUtil.getString(
				portlet1.getDisplayName());
		}

		String portletDisplayName2 = StringPool.BLANK;

		if (portlet2 != null) {
			portletDisplayName2 = GetterUtil.getString(
				portlet2.getDisplayName());
		}

		int value = portletDisplayName1.compareTo(portletDisplayName2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;
	
}
