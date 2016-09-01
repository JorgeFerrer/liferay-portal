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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.security.permission.ModelResourceActionsBag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author László Csontos
 */
public class ModelResourceActionsBagImpl
	extends ResourceActionsBagImpl implements ModelResourceActionsBag {

	public ModelResourceActionsBagImpl() {
	}

	public ModelResourceActionsBagImpl(
		ModelResourceActionsBag modelResourceActionsBag) {

		super(modelResourceActionsBag);

		for (String porletName :
				modelResourceActionsBag.getPortletResources()) {

			_portletModelResourceActions.put(
				porletName,
				modelResourceActionsBag.getPortletModelResourceActions(
					porletName));
		}

		_resourceOwnerDefaultActions.addAll(
			modelResourceActionsBag.getResourceOwnerDefaultActions());
		_resourceWeights.putAll(modelResourceActionsBag.getResourceWeights());
	}

	@Override
	public ModelResourceActionsBag clone() {
		return new ModelResourceActionsBagImpl(this);
	}

	public Set<String> getPortletModelResourceActions(String portletName) {
		if (!_portletModelResourceActions.containsKey(portletName)) {
			_portletModelResourceActions.put(
				portletName, new HashSet<String>());
		}

		return _portletModelResourceActions.get(portletName);
	}

	public Set<String> getPortletResources() {
		return _portletModelResourceActions.keySet();
	}

	@Override
	public Set<String> getResourceOwnerDefaultActions() {
		return _resourceOwnerDefaultActions;
	}

	@Override
	public Map<String, Double> getResourceWeights() {
		return _resourceWeights;
	}

	private final Map<String, Set<String>> _portletModelResourceActions =
		new HashMap<>();
	private final Set<String> _resourceOwnerDefaultActions = new HashSet<>();
	private final Map<String, Double> _resourceWeights = new HashMap<>();

}