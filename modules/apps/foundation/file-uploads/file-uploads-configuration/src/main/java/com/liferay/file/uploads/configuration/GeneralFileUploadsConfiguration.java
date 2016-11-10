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

package com.liferay.file.uploads.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.util.PropsKeys;

/**
 * @author Drew Brokke
 */
@ExtendedObjectClassDefinition(
	category = "foundation", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.file.uploads.configuration.GeneralFileUploadsConfiguration",
	localization = "content/Language",
	name = "general.file.uploads.configuration.name"
)
public interface GeneralFileUploadsConfiguration {

	@Meta.AD(
		deflt = "104857600",
		id = PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE, required = false
	)
	public long uploadServletRequestImplMaxSize();

	@Meta.AD(
		deflt = "", id = PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR,
		required = false
	)
	public String uploadServletRequestImplTempDir();

}