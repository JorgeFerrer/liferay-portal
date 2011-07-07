<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<h3><liferay-ui:message key="email-notifications" /></h3>

<%
String currentLanguageId = LanguageUtil.getLanguageId(request);
String tabx = "paco";

if (Validator.isNotNull(request.getParameter("currentLanguageId"))) {
	currentLanguageId = request.getParameter("currentLanguageId");
}

String adminEmailFromName = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
String adminEmailFromAddress = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

boolean adminEmailUserAddedEnable = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED + "_" + currentLanguageId);
String adminEmailUserAddedSubject = PrefsPropsUtil.getLocalizedContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_SUBJECT + "_" + currentLanguageId);
String adminEmailUserAddedBody = PrefsPropsUtil.getLocalizedContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_BODY + "_" + currentLanguageId);
String adminEmailUserAddedNoPasswordBody = PrefsPropsUtil.getLocalizedContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_USER_ADDED_NO_PASSWORD_BODY + "_" + currentLanguageId);

String adminEmailPasswordSentSubject = PrefsPropsUtil.getLocalizedContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT + "_" + currentLanguageId);
String adminEmailPasswordSentBody = PrefsPropsUtil.getLocalizedContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_BODY + "_" + currentLanguageId);

String adminEmailPasswordResetSubject = PrefsPropsUtil.getLocalizedContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT + "_" + currentLanguageId);
String adminEmailPasswordResetBody = PrefsPropsUtil.getLocalizedContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_BODY + "_" + currentLanguageId);

String adminEmailVerificationSubject = PrefsPropsUtil.getLocalizedContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_VERIFICATION_SUBJECT + "_" + currentLanguageId);
String adminEmailVerificationBody = PrefsPropsUtil.getLocalizedContent(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_VERIFICATION_BODY + "_" + currentLanguageId);
%>

<liferay-ui:error-marker key="errorSection" value="email_notifications" />

<liferay-ui:tabs
	names="sender,account-created-notification,email-verification-notification,password-changed-notification,password-reset-notification"
	refresh="<%= false %>"
>

	<div class="aui-helper-hidden" id="<portlet:namespace/>languageContainer" >
		<aui:select label="language" name="currentLanguageId">

			<%
			Locale[] locales = LanguageUtil.getAvailableLocales();

			for (int i = 0; i < locales.length; i++) {
				String style = StringPool.BLANK;
			%>

				<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" style="<%= style %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

			<%
			}
			%>

		</aui:select>
	</div>

	<liferay-ui:section>
		<aui:fieldset>
			<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />

			<aui:input cssClass="lfr-input-text-container" label="name" name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_FROM_NAME + "--" %>' type="text" value="<%= adminEmailFromName %>" />

			<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />

			<aui:input cssClass="lfr-input-text-container" label="address" name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_FROM_ADDRESS + "--" %>' type="text" value="<%= adminEmailFromAddress %>" />
		</aui:fieldset>
	</liferay-ui:section>
	<liferay-ui:section>
		<aui:fieldset>
			<aui:input inlineLabel="left" label="enabled" name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_USER_ADDED_ENABLED + "--" %>' type="checkbox" value="<%= adminEmailUserAddedEnable %>" />

			<liferay-ui:error key="emailUserAddedSubject" message="please-enter-a-valid-subject" />

			<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_USER_ADDED_SUBJECT + "_" + currentLanguageId + "--" %>' type="text" value="<%= adminEmailUserAddedSubject %>" />

			<liferay-ui:error key="emailUserAddedBody" message="please-enter-a-valid-body" />

			<aui:field-wrapper label="body-with-password" helpMessage="account-created-notification-body-with-password-help">
				<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" initMethod='<%= "initEmailUserAddedBodyEditor" %>' name="emailUserAddedBody" toolbarSet="email" width="470" />

				<aui:input name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_USER_ADDED_BODY + "_" + currentLanguageId + "--" %>' type="hidden" value="<%= adminEmailUserAddedBody %>" />
			</aui:field-wrapper>

			<liferay-ui:error key="emailUserAddedNoPasswordBody" message="please-enter-a-valid-body" />

			<aui:field-wrapper label="body-without-password" helpMessage="account-created-notification-body-without-password-help">
				<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" initMethod='<%= "initEmailUserAddedNoPasswordBodyEditor" %>' name="emailUserAddedNoPasswordBody" toolbarSet="email" width="470" />

				<aui:input name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_USER_ADDED_NO_PASSWORD_BODY + "_" + currentLanguageId + "--" %>' type="hidden" value="<%= adminEmailUserAddedNoPasswordBody %>" />
			</aui:field-wrapper>

			<div class="terms email-user-add definition-of-terms">
				<%@ include file="/html/portlet/portal_settings/definition_of_terms.jspf" %>
			</div>
		</aui:fieldset>
	</liferay-ui:section>
	<liferay-ui:section>
		<aui:fieldset>
			<liferay-ui:error key="emailPasswordSentSubject" message="please-enter-a-valid-subject" />

			<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT + "_" + currentLanguageId +"--" %>' type="text" value="<%= adminEmailPasswordSentSubject %>" />

			<liferay-ui:error key="emailPasswordSentBody" message="please-enter-a-valid-body" />

			<aui:field-wrapper label="body">
				<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" initMethod='<%= "initEmailPasswordSentBodyEditor" %>' name="emailPasswordSentBody" toolbarSet="email" width="470" />

				<aui:input name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_BODY + "_" + currentLanguageId + "--" %>' type="hidden" value="<%= adminEmailPasswordSentBody %>" />
			</aui:field-wrapper>

			<div class="terms email-password-sent definition-of-terms">
				<%@ include file="/html/portlet/portal_settings/definition_of_terms.jspf" %>
			</div>
		</aui:fieldset>
	</liferay-ui:section>
	<liferay-ui:section>
		<aui:fieldset>
			<liferay-ui:error key="emailPasswordResetSubject" message="please-enter-a-valid-subject" />

			<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT + "_" + currentLanguageId + "--" %>' type="text" value="<%= adminEmailPasswordResetSubject %>" />

			<liferay-ui:error key="emailPasswordResetBody" message="please-enter-a-valid-body" />

			<aui:field-wrapper label="body">
				<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" initMethod='<%= "initEmailPasswordResetBodyEditor" %>' name="emailPasswordResetBody" toolbarSet="email" width="470" />

				<aui:input name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_BODY + "_" + currentLanguageId + "--" %>' type="hidden" value="<%= adminEmailPasswordResetBody %>" />
			</aui:field-wrapper>

			<div class="terms email-password-sent definition-of-terms">
				<%@ include file="/html/portlet/portal_settings/definition_of_terms.jspf" %>
			</div>
		</aui:fieldset>
	</liferay-ui:section>
	<liferay-ui:section>
		<aui:fieldset>
			<liferay-ui:error key="emailVerificationSubject" message="please-enter-a-valid-subject" />

			<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_VERIFICATION_SUBJECT + "_" + currentLanguageId + "--" %>' type="text" value="<%= adminEmailVerificationSubject %>" />

			<liferay-ui:error key="emailVerificationBody" message="please-enter-a-valid-body" />

			<aui:field-wrapper label="body">
				<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" initMethod='<%= "initEmailVerificationBodyEditor" %>' name="emailVerificationBody" toolbarSet="email" width="470" />

				<aui:input name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_VERIFICATION_BODY + "_" + currentLanguageId + "--" %>' type="hidden" value="<%= adminEmailPasswordResetBody %>" />
			</aui:field-wrapper>

			<div class="terms email-verification definition-of-terms">
				<%@ include file="/html/portlet/portal_settings/definition_of_terms.jspf" %>
			</div>
		</aui:fieldset>
	</liferay-ui:section>
</liferay-ui:tabs>

<aui:script>
	function <portlet:namespace />initEmailUserAddedBodyEditor() {
		return "<%= UnicodeFormatter.toString(adminEmailUserAddedBody) %>";
	}

	function <portlet:namespace />initEmailUserAddedNoPasswordBodyEditor() {
		return "<%= UnicodeFormatter.toString(adminEmailUserAddedNoPasswordBody) %>";
	}

	function <portlet:namespace />initEmailPasswordSentBodyEditor() {
		return "<%= UnicodeFormatter.toString(adminEmailPasswordSentBody) %>";
	}

	function <portlet:namespace />initEmailPasswordResetBodyEditor() {
		return "<%= UnicodeFormatter.toString(adminEmailPasswordResetBody) %>";
	}

	function <portlet:namespace />initEmailVerificationBodyEditor() {
		return "<%= UnicodeFormatter.toString(adminEmailVerificationBody) %>";
	}

	function <portlet:namespace />saveEmails() {
		try {
			document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.ADMIN_EMAIL_USER_ADDED_BODY + "_" + currentLanguageId %>--'].value = window['<portlet:namespace />emailUserAddedBody'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.ADMIN_EMAIL_USER_ADDED_NO_PASSWORD_BODY + "_" + currentLanguageId %>--'].value = window['<portlet:namespace />emailUserAddedNoPasswordBody'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.ADMIN_EMAIL_PASSWORD_SENT_BODY + "_" + currentLanguageId %>--'].value = window['<portlet:namespace />emailPasswordSentBody'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_BODY + "_" + currentLanguageId %>--'].value = window['<portlet:namespace />emailPasswordResetBody'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.ADMIN_EMAIL_VERIFICATION_BODY + "_" + currentLanguageId %>--'].value = window['<portlet:namespace />emailVerificationBody'].getHTML();
		}
		catch (e) {
		}
	}

</aui:script>

<aui:script use="aui-base">
	var selectCurrentLanguageId = A.one('#<portlet:namespace />currentLanguageId');
	var languageContainer = A.one('#<portlet:namespace />languageContainer');

	if (selectCurrentLanguageId) {
		selectCurrentLanguageId.on(
			'change',
			function(event) {
				var currentLanguageId = selectCurrentLanguageId.val();

				var redirect = "<portlet:renderURL><portlet:param name="struts_action" value="/portal_settings/edit_company" /></portlet:renderURL>" + "&<portlet:namespace />currentLanguageId=" + currentLanguageId;
				redirect += Liferay.Util.getHistoryParam('<portlet:namespace />');

				document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'update';

				submitForm(document.<portlet:namespace />fm);
			}
		);
	}

	Liferay.after(
		'showTab',
		function(event) {
			var index = event.selectedIndex;

			if (index > 0) {
	        	languageContainer.show();
			}
			else {
				languageContainer.hide();
			}
		}
	);
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.portal_settings.email_notifications.jsp";
%>