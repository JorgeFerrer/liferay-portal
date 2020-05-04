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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.fields.InfoField;
import com.liferay.info.fields.InfoFieldSet;
import com.liferay.info.item.descriptor.InfoItemDescriptor;
import com.liferay.info.item.descriptor.InfoItemDescriptorTracker;
import com.liferay.info.item.descriptor.SubtypedInfoItemDescriptor;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/get_collection_mapping_fields"
	},
	service = MVCResourceCommand.class
)
public class GetCollectionMappingFieldsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		// LPS-111037

		String itemType = ParamUtil.getString(resourceRequest, "itemType");

		if (Objects.equals(DLFileEntryConstants.getClassName(), itemType)) {
			itemType = FileEntry.class.getName();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String itemSubtype = ParamUtil.getString(
			resourceRequest, "itemSubtype");

		try {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			long itemSubtypeLong = GetterUtil.getLong(itemSubtype);

			_addInfoDisplayFields(
				jsonArray, itemType, itemSubtypeLong, themeDisplay.getLocale());
			_addInfoItemFields(
				jsonArray, itemType, itemSubtypeLong, themeDisplay.getLocale());

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonArray);
		}
		catch (Exception exception) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					LanguageUtil.get(
						themeDisplay.getRequest(),
						"an-unexpected-error-occurred")));
		}
	}

	private void _addInfoDisplayFields(
			JSONArray jsonArray, String className, long classTypeId,
			Locale locale)
		throws Exception {

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(className);

		if (infoDisplayContributor == null) {
			return;
		}

		Set<InfoDisplayField> infoDisplayFields =
			infoDisplayContributor.getInfoDisplayFields(classTypeId, locale);

		for (InfoDisplayField infoDisplayField : infoDisplayFields) {
			JSONObject jsonObject = JSONUtil.put(
				"key", infoDisplayField.getKey()
			).put(
				"label", infoDisplayField.getLabel()
			).put(
				"type", infoDisplayField.getType()
			);

			jsonArray.put(jsonObject);
		}
	}

	private void _addInfoItemFields(
			JSONArray jsonArray, String className, long classTypeId,
			Locale locale)
		throws Exception {

		InfoItemDescriptor infoItemDescriptor =
			_infoItemDescriptorTracker.getInfoItemDescriptor(className);

		if (infoItemDescriptor == null) {
			return;
		}

		InfoFieldSet infoFieldSet = null;

		if (infoItemDescriptor instanceof SubtypedInfoItemDescriptor) {
			SubtypedInfoItemDescriptor subtypedInfoItemDescriptor =
				(SubtypedInfoItemDescriptor)infoItemDescriptor;

			infoFieldSet = subtypedInfoItemDescriptor.getInfoFieldSet(
				classTypeId);
		}
		else {
			infoFieldSet = infoItemDescriptor.getInfoFieldSet();
		}

		for (InfoField infoField : infoFieldSet.getAllFields()) {
			JSONObject jsonObject = JSONUtil.put(
				"key", infoField.getName()
			).put(
				"label", infoField.getLabel(locale)
			).put(
				"type", infoField.getType().getName()
			);

			jsonArray.put(jsonObject);
		}
	}

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private InfoItemDescriptorTracker _infoItemDescriptorTracker;
}