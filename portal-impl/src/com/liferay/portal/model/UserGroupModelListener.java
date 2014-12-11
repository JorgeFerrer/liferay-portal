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

package com.liferay.portal.model;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.security.exportimport.UserExporterUtil;
import com.liferay.portal.security.exportimport.UserImportTransactionThreadLocal;
import com.liferay.portal.security.exportimport.UserOperation;
import com.liferay.portal.service.persistence.UserGroupUtil;

/**
 * @author Marcellus Tavares
 */
public class UserGroupModelListener
	extends UserCollectionReindexListener<UserGroup> {

	public UserGroupModelListener() {
		super(
			SetUtil.fromArray(
				new String[] {
					Group.class.getName(), Team.class.getName()
				}),
			true, false);
	}

	@Override
	public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			if (associationClassName.equals(User.class.getName())) {
				exportToLDAP(
					(Long)associationClassPK, (Long)classPK, UserOperation.ADD);
			}

			super.onAfterAddAssociation(
				classPK, associationClassName, associationClassPK);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			if (associationClassName.equals(User.class.getName())) {
				exportToLDAP(
					(Long)associationClassPK, (Long)classPK,
					UserOperation.REMOVE);
			}

			super.onAfterRemoveAssociation(
				classPK, associationClassName, associationClassPK);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected void exportToLDAP(
			long userId, long userGroupId, UserOperation userOperation)
		throws Exception {

		if (UserImportTransactionThreadLocal.isOriginatesFromImport()) {
			return;
		}

		UserExporterUtil.exportUser(userId, userGroupId, userOperation);
	}

	@Override
	protected long[] getUserIds(Object classPK) {
		return UserGroupUtil.getUserPrimaryKeys((Long)classPK);
	}

}