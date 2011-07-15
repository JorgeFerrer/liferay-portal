/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutSetFinderUtil {
	public static java.util.List<com.liferay.portal.model.LayoutSet> findByPrototypeUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByPrototypeUuid(uuid);
	}

	public static LayoutSetFinder getFinder() {
		if (_finder == null) {
			_finder = (LayoutSetFinder)PortalBeanLocatorUtil.locate(LayoutSetFinder.class.getName());

			ReferenceRegistry.registerReference(LayoutSetFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(LayoutSetFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(LayoutSetFinderUtil.class, "_finder");
	}

	private static LayoutSetFinder _finder;
}