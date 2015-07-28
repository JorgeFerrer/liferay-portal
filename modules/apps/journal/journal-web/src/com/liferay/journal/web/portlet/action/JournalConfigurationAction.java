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

package com.liferay.journal.web.portlet.action;

import com.liferay.journal.configuration.JournalGroupServiceConfiguration;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.web.context.util.JournalWebRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.BaseJSPConfigurationPropertiesAction;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.settings.ConfigurationProperties;
import com.liferay.portal.kernel.settings.ModifiableConfigurationProperties;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + JournalPortletKeys.JOURNAL},
	service = ConfigurationAction.class
)
public class JournalConfigurationAction
	extends BaseJSPConfigurationPropertiesAction {

	@Override
	public String getJspPath(HttpServletRequest request) {
		return "/configuration.jsp";
	}

	@Override
	public void postProcess(
			long companyId, PortletRequest portletRequest,
			ConfigurationProperties configurationProperties)
		throws PortalException {

		ModifiableConfigurationProperties modifiableConfigurationProperties =
			configurationProperties.getModifiableConfigurationProperties();

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		JournalWebRequestHelper journalWebRequestHelper =
			new JournalWebRequestHelper(request);

		JournalGroupServiceConfiguration journalGroupServiceConfiguration =
			journalWebRequestHelper.getJournalGroupServiceConfiguration();

		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleAddedBody",
			journalGroupServiceConfiguration.emailArticleAddedBody());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleAddedSubject",
			journalGroupServiceConfiguration.emailArticleAddedSubject());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleApprovalDeniedBody",
			journalGroupServiceConfiguration.emailArticleApprovalDeniedBody());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleApprovalDeniedSubject",
			journalGroupServiceConfiguration.
				emailArticleApprovalDeniedSubject());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleApprovalGrantedBody",
			journalGroupServiceConfiguration.emailArticleApprovalGrantedBody());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleApprovalGrantedSubject",
			journalGroupServiceConfiguration.
				emailArticleApprovalGrantedSubject());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleApprovalRequestedBody",
			journalGroupServiceConfiguration.
				emailArticleApprovalRequestedBody());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleApprovalRequestedSubject",
			journalGroupServiceConfiguration.
				emailArticleApprovalRequestedSubject());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleMovedFromFolderBody",
			journalGroupServiceConfiguration.emailArticleMovedFromFolderBody());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleMovedFromFolderSubject",
			journalGroupServiceConfiguration.
				emailArticleMovedFromFolderSubject());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleMovedToFolderBody",
			journalGroupServiceConfiguration.emailArticleMovedToFolderBody());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleMovedToFolderSubject",
			journalGroupServiceConfiguration.
				emailArticleMovedToFolderSubject());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleReviewBody",
			journalGroupServiceConfiguration.emailArticleReviewBody());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleReviewSubject",
			journalGroupServiceConfiguration.emailArticleReviewSubject());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleUpdatedBody",
			journalGroupServiceConfiguration.emailArticleUpdatedBody());
		removeDefaultValue(
			portletRequest, modifiableConfigurationProperties,
			"emailArticleUpdatedSubject",
			journalGroupServiceConfiguration.emailArticleUpdatedSubject());
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateEmail(actionRequest, "emailArticleAdded");
		validateEmail(actionRequest, "emailArticleApprovalDenied");
		validateEmail(actionRequest, "emailArticleApprovalGranted");
		validateEmail(actionRequest, "emailArticleApprovalRequested");
		validateEmail(actionRequest, "emailArticleReview");
		validateEmail(actionRequest, "emailArticleUpdated");
		validateEmailFrom(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}