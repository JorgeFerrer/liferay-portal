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

package com.liferay.dynamic.data.lists.web.portlet;

import com.liferay.dynamic.data.lists.web.constants.DDLPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.ConfigurationException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatalists.NoSuchRecordException;
import com.liferay.portlet.dynamicdatalists.NoSuchRecordSetException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordService;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetService;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.control-panel-entry-category=site_administration.content",
		"com.liferay.portlet.control-panel-entry-weight=8.0",
		"com.liferay.portlet.css-class-wrapper=portlet-dynamic-data-lists",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portal-javascript=/o/ddm-web/js/custom_fields.js",
		"com.liferay.portlet.header-portal-javascript=/o/ddm-web/js/main.js",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.header-portlet-javascript=/js/main.js",
		"com.liferay.portlet.icon=/icons/dynamic_data_lists.png",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"com.liferay.portlet.webdav-storage-token=dynamic_data_lists",
		"javax.portlet.display-name=Dynamic Data Lists",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class DDLPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			setDDLRecordRequestAttribute(renderRequest);

			setDDLRecordSetRequestAttribute(renderRequest);
		}
		catch (NoSuchRecordException | NoSuchRecordSetException nsre) {

			// Let this slide because the user can manually input an record set
			// key for a new record set that does not yet exist

			if (_log.isDebugEnabled()) {
				_log.debug(nsre, nsre);
			}
		}
		catch (PortalException pe) {
			SessionErrors.add(renderRequest, pe.getClass());
		}

		super.render(renderRequest, renderResponse);
	}

	@Reference
	public void setDDLRecordService(DDLRecordService ddlRecordService) {
		_ddlRecordService = ddlRecordService;
	}

	@Reference
	public void setDDLRecordSetService(
		DDLRecordSetService ddlRecordSetService) {

		_ddlRecordSetService = ddlRecordSetService;
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchRecordException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchRecordSetException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, ConfigurationException.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeAuthenticated.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeCompanyAdmin.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeEnabled.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeInvokedByPost.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeMarketplaceAdmin.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeOmniadmin.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeOwnedByCurrentUser.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBePortletStrutsPath.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.
					MustBeSupportedActionForRole.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustBeValidPortlet.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustHavePermission.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustHaveUserGroupRole.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustHaveUserRole.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustHaveValidGroup.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.
					MustHaveValidPermissionChecker.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustHaveValidPortletId.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.
					MustHaveValidPrincipalName.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.
					MustInitializePermissionChecker.class.getName()) ||
			SessionErrors.contains(
				renderRequest,
				PrincipalException.MustNotBeGroupAdmin.class.getName())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected void setDDLRecordRequestAttribute(RenderRequest renderRequest)
		throws PortalException {

		long recordId = ParamUtil.getLong(renderRequest, "recordId");

		DDLRecord record = null;

		if (recordId > 0) {
			record = _ddlRecordService.getRecord(recordId);
		}

		renderRequest.setAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD, record);
	}

	protected void setDDLRecordSetRequestAttribute(RenderRequest renderRequest)
		throws PortalException {

		long recordSetId = ParamUtil.getLong(renderRequest, "recordSetId");

		DDLRecordSet recordSet = null;

		if (recordSetId > 0) {
			recordSet = _ddlRecordSetService.getRecordSet(recordSetId);
		}

		renderRequest.setAttribute(
			WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET, recordSet);
	}

	private static final Log _log = LogFactoryUtil.getLog(DDLPortlet.class);

	private DDLRecordService _ddlRecordService;
	private DDLRecordSetService _ddlRecordSetService;

}