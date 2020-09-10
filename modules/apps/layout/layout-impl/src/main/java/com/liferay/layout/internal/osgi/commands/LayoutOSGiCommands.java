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

package com.liferay.layout.internal.osgi.commands;

import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.serializer.LayoutStructureItemJSONSerializer;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import org.apache.felix.service.command.Descriptor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=exportAsJSON", "osgi.command.scope=layout"
	},
	service = LayoutOSGiCommands.class
)
public class LayoutOSGiCommands {

	@Descriptor("Get Page Definition JSON for a given layout by its plid")
	public String exportAsJSON(long plid) throws PortalException {
		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return "Layout with plid " + plid + " cannot be found";
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(layout.getGroupId(), plid);

		if (layoutPageTemplateStructure == null) {
			return "Layout with plid " + plid + " does not have a structure";
		}

		long segmentsExperienceId = SegmentsExperienceConstants.ID_DEFAULT;

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		return _layoutStructureItemJSONSerializer.toJSONString(
			layout, layoutStructure.getMainItemId(), false, false,
			segmentsExperienceId);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutStructureItemJSONSerializer
		_layoutStructureItemJSONSerializer;

}