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

package com.liferay.site.util;

import aQute.bnd.annotation.ProviderType;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface GroupCreationStepRegistry {

	public GroupCreationStep getGroupCreationStep(String groupCreationStepName);

	public List<GroupCreationStep> getGroupCreationSteps(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	public GroupCreationStep getNextGroupCreationStep(
			String groupCreationStepName, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	public GroupCreationStep getPreviousGroupCreationStep(
			String groupCreationStepName, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

}