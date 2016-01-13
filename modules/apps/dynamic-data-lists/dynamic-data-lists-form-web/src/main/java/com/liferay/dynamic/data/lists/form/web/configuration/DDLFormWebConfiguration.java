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

package com.liferay.dynamic.data.lists.form.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.metatype.annotations.ObjectClassDefinitionExt;

/**
 * @author Lino Alves
 */
@ObjectClassDefinitionExt(
	category = "productivity", scope = ObjectClassDefinitionExt.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.dynamic.data.lists.form.web.configuration.DDLFormWebConfiguration",
	localization = "content/Language", name = "%ddl.form.web.configuration.name"
)
public interface DDLFormWebConfiguration {

	@Meta.AD(
		deflt = "descriptive", optionLabels = {"Descriptive", "List", "Icon"},
		optionValues = {"descriptive", "list", "icon"}, required = false
	)
	public String defaultDisplayView();

}