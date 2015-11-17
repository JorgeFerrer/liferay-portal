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

package com.liferay.layout.type.controller.single.portlet.application.access.policy;

import com.liferay.layout.type.controller.full.page.application.access.policy.FullPageApplicationLayoutTypeAccessPolicy;
import com.liferay.layout.type.controller.single.portlet.application.constants.SinglePortletApplicationLayoutTypeControllerConstants;
import com.liferay.portal.model.LayoutTypeAccessPolicy;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {"layout.type=" + SinglePortletApplicationLayoutTypeControllerConstants.LAYOUT_TYPE_SINGLE_PORTLET_APPLICATION},
	service = LayoutTypeAccessPolicy.class
)
public class SinglePortletApplicationLayoutTypeAccessPolicy
	extends FullPageApplicationLayoutTypeAccessPolicy {
}