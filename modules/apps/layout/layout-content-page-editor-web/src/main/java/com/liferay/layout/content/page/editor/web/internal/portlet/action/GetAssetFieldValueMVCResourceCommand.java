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

import com.liferay.asset.info.display.contributor.util.ContentAccessor;
import com.liferay.info.fields.InfoFieldValue;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormProviderTracker;
import com.liferay.info.item.provider.InfoItemProvider;
import com.liferay.info.item.provider.InfoItemProviderTracker;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/get_asset_field_value"
	},
	service = MVCResourceCommand.class
)
public class GetAssetFieldValueMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long classNameId = ParamUtil.getLong(resourceRequest, "classNameId");

		InfoItemFormProvider infoItemFormProvider =
			_infoItemFormProviderTracker.getInfoItemFormProvider(
				_portal.getClassName(classNameId));

		if (infoItemFormProvider == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONFactoryUtil.createJSONObject());

			return;
		}

		InfoItemProvider infoItemProvider =
			_infoItemProviderTracker.getInfoItemProvider(
				_portal.getClassName(classNameId));

		if (infoItemProvider == null) {
			return;
		}

		long classPK = ParamUtil.getLong(resourceRequest, "classPK");

		Object object = infoItemProvider.getInfoItem(classPK);

		if (object == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONFactoryUtil.createJSONObject());

			return;
		}

		String fieldId = ParamUtil.getString(resourceRequest, "fieldId");

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONUtil.put(
			"classNameId", classNameId
		).put(
			"classPK", classPK
		).put(
			"fieldId", fieldId
		);

		String languageId = ParamUtil.getString(
			resourceRequest, "languageId", themeDisplay.getLanguageId());

		InfoFieldValue infoFieldValue = infoItemFormProvider.getInfoFieldValue(
			object, fieldId);

		Object value = null;

		if (infoFieldValue == null) {
			value = StringPool.BLANK;
		}
		else {
			value = infoFieldValue.getValue(
				LocaleUtil.fromLanguageId(languageId));
		}

		if (value instanceof ContentAccessor) {
			ContentAccessor contentAccessor = (ContentAccessor)value;

			value = contentAccessor.getContent();
		}

		jsonObject.put("fieldValue", value);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	@Reference
	private InfoItemFormProviderTracker _infoItemFormProviderTracker;

	@Reference
	private InfoItemProviderTracker _infoItemProviderTracker;

	@Reference
	private Portal _portal;

}