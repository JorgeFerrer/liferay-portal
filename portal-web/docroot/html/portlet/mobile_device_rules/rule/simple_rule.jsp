<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/mobile_device_rules/init.jsp" %>

<%
MDRRule rule = (MDRRule)request.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE);

Set<String> operatingSystems = Collections.emptySet();
int tablet = 0;
String displayHeight = "";
String displayWidth = "";
String resolutionHeight = "";
String resolutionWidth = "";

if (rule != null) {
	UnicodeProperties typeSettingsProperties = rule.getTypeSettingsProperties();

	operatingSystems = SetUtil.fromArray(StringUtil.split(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_OS)));

	String tabletString = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_TABLET));

	if (tabletString.equals(StringPool.TRUE)) {
		tablet = 1;
	}
	else if (tabletString.equals(StringPool.FALSE)) {
		tablet = 2;
	}

	displayHeight = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_DISPLAY_HEIGHT));
	displayWidth = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_DISPLAY_WIDTH));
	resolutionHeight = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_RESOLUTION_HEIGHT));
	resolutionWidth = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_RESOLUTION_WIDTH));
}
%>

<h3><liferay-ui:message key="generic-product-information" /></h3>

<aui:select helpMessage='<%= PluginPackageUtil.isInstalled("wurfl-web") ? StringPool.BLANK : "os-help" %>' multiple="<%= true %>" name="os">

	<aui:option label="any-os" selected="<%= operatingSystems.isEmpty() %>" value="" />

	<%
	Set<VersionableName> knownOperationSystems = DeviceDetectionUtil.getKnownOperatingSystems();

	for (VersionableName knownOperationSystem : knownOperationSystems) {
	%>

		<aui:option label="<%= knownOperationSystem.getName() %>" selected="<%= operatingSystems.contains(knownOperationSystem.getName()) %>" />

	<%
	}
	%>

</aui:select>

<aui:select label="device-type" name="tablet">
	<aui:option label="any" selected="<%= tablet == 0 %>" value="" />
	<aui:option label="tablets" selected="<%= tablet == 1 %>" value="<%= true %>" />
	<aui:option label="other-devices" selected="<%= tablet == 2 %>" value="<%= false %>" />
</aui:select>

<h3><liferay-ui:message key="screen-size-in-millimiters" /></h3>

<aui:select
	id="known-dispay-sizes"
	label="known-dispay-sizes"
	multiple="<%= false %>"
	name="known-dispay-sizes"
	>

	<aui:option label="" />

	<%
	Set<Dimensions> knownDisplaySizes = DeviceDetectionUtil.getKnownDisplaySizes();

	for (Dimensions knownDisplaySize : knownDisplaySizes) {
	%>

		<aui:option label='<%= knownDisplaySize.getWidth() + " x " + knownDisplaySize.getHeight() %>' />

	<%
	}
	%>

</aui:select>


<div class="row-fields">
	<aui:input
		cssClass="custom-display-field aui-field-digits"
		id="<%= SimpleRuleHandler.PROPERTY_DISPLAY_WIDTH %>"
		inlineField="<%= true %>"
		label="width"
		name="<%= SimpleRuleHandler.PROPERTY_DISPLAY_WIDTH %>"
		value="<%= displayWidth %>"
		/>
	X
	<aui:input
		cssClass="custom-display-field aui-field-digits"
		id="<%= SimpleRuleHandler.PROPERTY_DISPLAY_HEIGHT %>"
		inlineField="<%= true %>"
		label="height"
		name="<%= SimpleRuleHandler.PROPERTY_DISPLAY_HEIGHT %>"
		value="<%= displayHeight %>"
		/>
</div>


<h3><liferay-ui:message key="screen-resolution" /></h3>

<aui:select
	id="known-screen-resolutions"
	label="known-screen-resolutions"
	multiple="<%= false %>"
	name="known-screen-resolutions"
	>

	<aui:option label="" />

	<%
	Set<Dimensions> knownScreenResolutions = DeviceDetectionUtil.getKnownScreenResolutions();

	for (Dimensions knownScreenResolution : knownScreenResolutions) {
	%>

		<aui:option label='<%= knownScreenResolution.getWidth() + " x " + knownScreenResolution.getHeight() %>' />

	<%
	}
	%>

</aui:select>

<div class="row-fields">
	<aui:input
		cssClass="custom-resolution-field aui-field-digits"
		id="<%= SimpleRuleHandler.PROPERTY_RESOLUTION_WIDTH %>"
		inlineField="<%= true %>"
		label="width"
		name="<%= SimpleRuleHandler.PROPERTY_RESOLUTION_WIDTH %>"
		value="<%= resolutionWidth %>"
		/>
	X
	<aui:input
		cssClass="custom-resolution-field aui-field-digits"
		id="<%= SimpleRuleHandler.PROPERTY_RESOLUTION_HEIGHT %>"
		inlineField="<%= true %>"
		label="height"
		name="<%= SimpleRuleHandler.PROPERTY_RESOLUTION_HEIGHT %>"
		value="<%= resolutionHeight %>"
		/>
</div>

<script>



	YUI().use(
		'aui-node',
		function(Y) {
			var knownSizes = Y.one('#<portlet:namespace/>known-dispay-sizes');
			var sizeWidth = Y.one('#<portlet:namespace/><%= SimpleRuleHandler.PROPERTY_DISPLAY_WIDTH %>');
			var sizeHeight = Y.one('#<portlet:namespace/><%= SimpleRuleHandler.PROPERTY_DISPLAY_HEIGHT %>');

			var knownResolutions = Y.one('#<portlet:namespace/>known-screen-resolutions');
			var resWidth = Y.one('#<portlet:namespace/><%= SimpleRuleHandler.PROPERTY_RESOLUTION_WIDTH %>');
			var resHeight = Y.one('#<portlet:namespace/><%= SimpleRuleHandler.PROPERTY_RESOLUTION_HEIGHT %>');

			knownSizes.on(
			    'change',
			    function() {
			  		var sizes = knownSizes.val().split(" x ");
			  		sizeWidth.val(sizes[0]);
			  		sizeHeight.val(sizes[1]);
			    }
			);

			knownResolutions.on(
				'change',
				function() {
					var sizes = knownResolutions.val().split(" x ");
					resWidth.val(sizes[0]);
					resHeight.val(sizes[1]);
			  	}
			);

			Y.all('.custom-display-field').on(
				'change',
				function() {
					knownSizes.set('selectedIndex', 0);
			  	}
			);

			Y.all('.custom-resolution-field').on(
				'change',
				function() {
					knownResolutions.set('selectedIndex', 0);
			  	}
			);
		}
	);
</script>