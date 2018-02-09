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

package com.liferay.portal.security.auth.verifier.internal.request.parameter.module.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.security.auth.verifier.internal.module.configuration.BaseAuthVerifierConfiguration;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(
	category = "api-authentication",
	factoryInstanceLabelAttribute = "urlsIncludes"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.security.auth.verifier.internal.request.parameter.module.configuration.RequestParameterAuthVerifierConfiguration",
	localization = "content/Language",
	name = "request-parameter-auth-verifier-configuration-name"
)
public interface RequestParameterAuthVerifierConfiguration
	extends BaseAuthVerifierConfiguration {
}