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

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.impl.LayoutSetImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Mate Thurzo
 */
public class LayoutSetFinderImpl
	extends BasePersistenceImpl<LayoutSet> implements LayoutSetFinder {

	public static String FIND_BY_PROTOTYPE_UUID =
		LayoutSetFinder.class.getName() + ".findByPrototypeUuid";

	public List<LayoutSet> findByPrototypeUuid(String uuid)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_PROTOTYPE_UUID);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("LayoutSet", LayoutSetImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add("%layoutSetPrototypeUuid=" + uuid + "%");

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}