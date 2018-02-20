<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

String creationType = ParamUtil.getString(request, "creationType");

String groupStarterKitKey = ParamUtil.getString(request, "groupStarterKitKey");

long layoutSetPrototypeId = ParamUtil.getLong(request, "layoutSetPrototypeId");

long parentGroupSearchContainerPrimaryKeys = ParamUtil.getLong(request, "parentGroupSearchContainerPrimaryKeys");

List<GroupCreationStep> groupCreationSteps = siteCreationWizardDisplayContext.getGroupCreationSteps();

String currentCreationStepName = siteCreationWizardDisplayContext.getCurrentCreationStepName();

String creationTypeLabel = StringPool.BLANK;

if (creationType.equals(SiteAdminConstants.CREATION_TYPE_SITE_TEMPLATE)) {
	creationTypeLabel = LanguageUtil.get(request, "site-templates");
}

if (creationType.equals(SiteAdminConstants.CREATION_TYPE_STARTER_KIT)) {
	creationTypeLabel = LanguageUtil.get(request, "starter-kits");
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "sites"), mainURL.toString());
PortalUtil.addPortletBreadcrumbEntry(request, creationTypeLabel, backURL);

String currentCreationStepLabel = siteCreationWizardDisplayContext.getCurrentCreationStepLabel();

PortalUtil.addPortletBreadcrumbEntry(request, currentCreationStepLabel, StringPool.BLANK);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL.toString());

renderResponse.setTitle("add-new-site");
%>

<portlet:actionURL name="saveCreationStep" var="saveCreationStepURL" />

<div class="breadcrumb-container">
	<div class="container-fluid-1280">
		<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
	</div>
</div>

<div class="container-fluid-1280">
	<%@ include file="/site_wizard/wizard_steps.jspf" %>

	<div class="col-md-8 offset-md-2 site-creation-container">
		<div class="site-creation-header">
			<%= HtmlUtil.escape(currentCreationStepLabel) %>
		</div>

		<aui:form action="<%= saveCreationStepURL %>" method="post" name="fm">
			<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
			<aui:input name="creationStepName" type="hidden" value="<%= currentCreationStepName %>" />
			<aui:input name="creationType" type="hidden" value="<%= creationType %>" />
			<aui:input name="currentURL" type="hidden" value="<%= currentURL %>" />
			<aui:input name="groupId" type="hidden" value="<%= siteCreationWizardDisplayContext.getGroupId() %>" />
			<aui:input name="groupStarterKitKey" type="hidden" value="<%= groupStarterKitKey %>" />
			<aui:input name="layoutSetPrototypeId" type="hidden" value="<%= layoutSetPrototypeId %>" />
			<aui:input name="parentGroupSearchContainerPrimaryKeys" type="hidden" value="<%= parentGroupSearchContainerPrimaryKeys %>" />
			<aui:input name="redirect" type="hidden" value="<%= siteCreationWizardDisplayContext.getCreationStepRedirect() %>" />

			<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
			<liferay-ui:error exception="<%= GroupInheritContentException.class %>" message="this-site-cannot-inherit-content-from-its-parent-site" />

			<liferay-ui:error exception="<%= GroupKeyException.class %>">
				<p>
					<liferay-ui:message arguments="<%= new String[] {SiteConstants.NAME_LABEL, SiteConstants.getNameGeneralRestrictions(locale), SiteConstants.NAME_RESERVED_WORDS} %>" key="the-x-cannot-be-x-or-a-reserved-word-such-as-x" />
				</p>

				<p>
					<liferay-ui:message arguments="<%= new String[] {SiteConstants.NAME_LABEL, SiteConstants.NAME_INVALID_CHARACTERS} %>" key="the-x-cannot-contain-the-following-invalid-characters-x" />
				</p>
			</liferay-ui:error>

			<liferay-ui:error exception="<%= GroupParentException.MustNotHaveStagingParent.class %>" message="the-site-cannot-have-a-staging-site-as-its-parent-site" />

			<%
			siteCreationWizardDisplayContext.renderCurrentCreationStep();
			%>

			<aui:button-row>
				<c:choose>
					<c:when test="<%= (groupCreationSteps.size() == 1) %>">
						<aui:button cssClass="btn-lg btn-primary" name="nextCreationStepButton" primary="<%= true %>" value="create" type="submit"/>

						<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" value="cancel" />
					</c:when>
					<c:when test="<%= Validator.isNotNull(siteCreationWizardDisplayContext.getPreviousCreationStepName()) %>">
						<portlet:renderURL var="previousCreationStepURL">
							<portlet:param name="jspPage" value="/site_creation_wizard.jsp" />
							<portlet:param name="redirect" value="<%= redirect %>" />
							<portlet:param name="backURL" value="<%= backURL %>" />
							<portlet:param name="groupId" value="<%= String.valueOf(siteCreationWizardDisplayContext.getGroupId()) %>" />
							<portlet:param name="parentGroupSearchContainerPrimaryKeys" value="<%= String.valueOf(parentGroupSearchContainerPrimaryKeys) %>" />
							<portlet:param name="creationStepName" value="<%= siteCreationWizardDisplayContext.getPreviousCreationStepName() %>" />
							<portlet:param name="creationType" value="creationType" />
						</portlet:renderURL>

						<aui:button cssClass="btn-lg" href="<%= previousCreationStepURL %>" type="cancel" value="previous" />

						<aui:button cssClass="btn-lg btn-next-creation-step" name="nextCreationStepButton" primary="<%= siteCreationWizardDisplayContext.isLastGroupCreationStep() %>" value='<%= (siteCreationWizardDisplayContext.isLastGroupCreationStep()) ? "create" : "next" %>' type="submit" />
					</c:when>
					<c:otherwise>
						<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" value="cancel" />

						<aui:button cssClass="btn-lg btn-next-creation-step" name="nextCreationStepButton" primary="<%= siteCreationWizardDisplayContext.isLastGroupCreationStep() %>" value='<%= (siteCreationWizardDisplayContext.isLastGroupCreationStep()) ? "create" : "next" %>' type="submit" />
					</c:otherwise>
				</c:choose>
			</aui:button-row>
		</aui:form>
	</div>
</div>