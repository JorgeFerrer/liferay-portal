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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;

import java.util.Set;

/**
 * @author Minhchau Dang
 */
public abstract class UserCollectionReindexListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	@Override
	public void onAfterAddAssociation(
		Object classPK, String associationClassName,
		Object associationClassPK) {

		if (_reindexAssociation) {
			reindexUsers(classPK, associationClassName);
		}
	}

	@Override
	public void onAfterCreate(T model) {
		if (_reindexModel) {
			long[] userId = getUserIds(model);

			reindexUsers(userId);
		}
	}

	@Override
	public void onAfterRemove(T model) {
		if (_reindexModel) {
			long[] userId = getUserIds(model);

			reindexUsers(userId);
		}
	}

	@Override
	public void onAfterRemoveAssociation(
		Object classPK, String associationClassName,
		Object associationClassPK) {

		if (_reindexAssociation) {
			reindexUsers(classPK, associationClassName);
		}
	}

	@Override
	public void onAfterUpdate(T model) {
		if (_reindexModel) {
			long[] userId = getUserIds(model);

			reindexUsers(userId);
		}
	}

	protected UserCollectionReindexListener(
		Set<String> tableMapperClasses, boolean reindexAssociation,
		boolean reindexModel) {

		_tableMapperClasses = tableMapperClasses;
		_reindexAssociation = reindexAssociation;
		_reindexModel = reindexModel;
	}

	protected long[] getUserIds(Object classPK) {
		return new long[0];
	}

	protected long[] getUserIds(T model) {
		return new long[0];
	}

	protected void reindexUsers(final long... userIds) {
		try {
			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				User.class.getName());

			indexer.commitCallbackReindex(userIds);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	protected void reindexUsers(Object classPK, String associationClassName) {
		if (!_tableMapperClasses.contains(associationClassName)) {
			return;
		}

		long[] userIds = getUserIds(classPK);

		reindexUsers(userIds);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserCollectionReindexListener.class);

	private final boolean _reindexAssociation;
	private final boolean _reindexModel;
	private final Set<String> _tableMapperClasses;

}