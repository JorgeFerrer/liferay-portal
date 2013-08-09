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

package com.liferay.portal.kernel.trash;

import com.liferay.portal.service.ServiceContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Zsolt Berentey
 */
public class TrashContext extends ServiceContext {

	public Map<Long, Integer> getDependentStatuses(String className) {
		HashMap<String, HashMap<Long, Integer>> dependentStatusMaps =
			(HashMap<String, HashMap<Long, Integer>>)getAttribute(
				TrashConstants.TRASH_DEPENDENT_STATUS_MAP);

		if (dependentStatusMaps == null) {
			dependentStatusMaps = new HashMap<String, HashMap<Long, Integer>>();

			setAttribute(
				TrashConstants.TRASH_DEPENDENT_STATUS_MAP, dependentStatusMaps);
		}

		HashMap<Long, Integer> dependentStatuses = dependentStatusMaps.get(
			className);

		if (dependentStatuses == null) {
			dependentStatuses = new HashMap<Long, Integer>();

			dependentStatusMaps.put(className, dependentStatuses);
		}

		return dependentStatuses;
	}

	public Set<Long> getEntityIds(String className) {
		HashMap<String, HashSet<Long>> entityIdsMap =
			(HashMap<String, HashSet<Long>>)getAttribute(
				TrashConstants.TRASH_ENTITY_IDS_MAP);

		if (entityIdsMap == null) {
			entityIdsMap = new HashMap<String, HashSet<Long>>();

			setAttribute(TrashConstants.TRASH_ENTITY_IDS_MAP, entityIdsMap);
		}

		HashSet<Long> entityIds = entityIdsMap.get(className);

		if (entityIds == null) {
			entityIds = new HashSet<Long>();

			entityIdsMap.put(className, entityIds);
		}

		return entityIds;
	}

	public void setDependentStatuses(
		String className, HashMap<Long, Integer> dependentStatuses) {

		if (dependentStatuses == null) {
			return;
		}

		HashMap<String, HashMap<Long, Integer>> dependentStatusMaps =
			(HashMap<String, HashMap<Long, Integer>>)getAttribute(
				TrashConstants.TRASH_DEPENDENT_STATUS_MAP);

		if (dependentStatusMaps == null) {
			dependentStatusMaps = new HashMap<String, HashMap<Long, Integer>>();

			setAttribute(
				TrashConstants.TRASH_DEPENDENT_STATUS_MAP, dependentStatusMaps);
		}

		dependentStatusMaps.put(className, dependentStatuses);
	}

	public void setEntityIds(String className, HashSet<Long> entityIds) {
		if (entityIds == null) {
			return;
		}

		HashMap<String, HashSet<Long>> entityIdsMap =
			(HashMap<String, HashSet<Long>>)getAttribute(
				TrashConstants.TRASH_ENTITY_IDS_MAP);

		if (entityIdsMap == null) {
			entityIdsMap = new HashMap<String, HashSet<Long>>();

			setAttribute(TrashConstants.TRASH_ENTITY_IDS_MAP, entityIdsMap);
		}

		entityIdsMap.put(className, entityIds);
	}

}