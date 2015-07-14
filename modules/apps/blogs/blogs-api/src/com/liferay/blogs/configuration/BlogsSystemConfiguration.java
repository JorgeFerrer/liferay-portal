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

package com.liferay.blogs.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Sergio González
 */
@Meta.OCD(id = "com.liferay.blogs.configuration.BlogsSystemConfiguration")
public interface BlogsSystemConfiguration {

	@Meta.AD(
		deflt = "com/liferay/blogs/web/template/dependencies/portlet-display-templates.xml",
		required = false
	)
	public String displayTemplatesConfig();

	@Meta.AD(deflt = "1", required = false)
	public int entryCheckInterval();

	@Meta.AD(deflt = "5", required = false)
	public int linkbackJobInterval();

}