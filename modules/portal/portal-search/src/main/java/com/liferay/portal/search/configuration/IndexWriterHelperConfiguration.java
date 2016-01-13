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

package com.liferay.portal.search.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.metatype.annotations.ObjectClassDefinitionExt;

/**
 * @author Michael C. Han
 */
@ObjectClassDefinitionExt(category = "platform")
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.IndexWriterHelperConfiguration",
	localization = "content/Language",
	name = "%index.writer.helper.configuration.name"
)
public interface IndexWriterHelperConfiguration {

	@Meta.AD(
		deflt = "true", description = "%index-commit-immediately-help",
		required = false
	)
	public boolean indexCommitImmediately();

	@Meta.AD(deflt = "false", required = false)
	public boolean indexReadOnly();

}