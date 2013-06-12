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
String separator = " x ";
String maxDisplayHeight = "";
String maxDisplayWidth = "";
String maxResolutionHeight = "";
String maxResolutionWidth = "";
String minDisplayHeight = "";
String minDisplayWidth = "";
String minResolutionHeight = "";
String minResolutionWidth = "";

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

	maxDisplayHeight = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_DISPLAY_MAX_HEIGHT));
	maxDisplayWidth = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_DISPLAY_MAX_WIDTH));
	maxResolutionHeight = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_RESOLUTION_MAX_HEIGHT));
	maxResolutionWidth = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_RESOLUTION_MAX_WIDTH));
	minDisplayHeight = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_DISPLAY_MIN_HEIGHT));
	minDisplayWidth = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_DISPLAY_MIN_WIDTH));
	minResolutionHeight = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_RESOLUTION_MIN_HEIGHT));
	minResolutionWidth = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_RESOLUTION_MIN_WIDTH));
}

Set<Dimensions> knownDisplaySizes = DeviceDetectionUtil.getKnownDisplaySizes();
Set<Dimensions> knownScreenResolutions = DeviceDetectionUtil.getKnownScreenResolutions();
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


<div class="row-fields">
	<liferay-ui:message key="minimum-display-size" />

	<aui:input
		cssClass="custom-min-display-field aui-field-digits"
		id="<%= SimpleRuleHandler.PROPERTY_DISPLAY_MIN_WIDTH %>"
		inlineField="<%= true %>"
		label="width"
		name="<%= SimpleRuleHandler.PROPERTY_DISPLAY_MIN_WIDTH %>"
		style="max-width:50px;"
		value="<%= minDisplayWidth %>"
		/>
	<%= separator %>
	<aui:input
		cssClass="custom-min-display-field aui-field-digits"
		id="<%= SimpleRuleHandler.PROPERTY_DISPLAY_MIN_HEIGHT %>"
		inlineField="<%= true %>"
		label="height"
		name="<%= SimpleRuleHandler.PROPERTY_DISPLAY_MIN_HEIGHT %>"
		style="max-width:50px;"
		value="<%= minDisplayHeight %>"
		/>

	(
	<aui:select
		helpMessage='<%= PluginPackageUtil.isInstalled("wurfl-web") ? StringPool.BLANK : "known-dispay-sizes-help" %>'
		id="min-known-dispay-sizes"
		inlineField="<%= true %>"
		inlineLabel="true"
		label="known-dispay-sizes"
		multiple="<%= false %>"
		name="min-known-dispay-sizes"
		style="max-width:100px;"
		>

		<aui:option label="" />

		<%
		for (Dimensions knownDisplaySize : knownDisplaySizes) {
		%>

			<aui:option label="<%= knownDisplaySize.getWidth() + separator + knownDisplaySize.getHeight() %>" />

		<%
		}
		%>

	</aui:select>
	)
</div>

<div class="row-fields">
	<liferay-ui:message key="maximum-display-size" />

	<aui:input
		cssClass="custom-max-display-field aui-field-digits"
		id="<%= SimpleRuleHandler.PROPERTY_DISPLAY_MAX_WIDTH %>"
		inlineField="<%= true %>"
		label="width"
		name="<%= SimpleRuleHandler.PROPERTY_DISPLAY_MAX_WIDTH %>"
		style="max-width:50px;"
		value="<%= maxDisplayWidth %>"
		/>
	<%= separator %>
	<aui:input
		cssClass="custom-max-display-field aui-field-digits"
		id="<%= SimpleRuleHandler.PROPERTY_DISPLAY_MAX_HEIGHT %>"
		inlineField="<%= true %>"
		label="height"
		name="<%= SimpleRuleHandler.PROPERTY_DISPLAY_MAX_HEIGHT %>"
		style="max-width:50px;"
		value="<%= maxDisplayHeight %>"
		/>

	(
	<aui:select
		helpMessage='<%= PluginPackageUtil.isInstalled("wurfl-web") ? StringPool.BLANK : "known-dispay-sizes-help" %>'
		id="max-known-dispay-sizes"
		inlineField="<%= true %>"
		inlineLabel="true"
		label="known-dispay-sizes"
		multiple="<%= false %>"
		name="max-known-dispay-sizes"
		style="max-width:100px;"
		>

		<aui:option label="" />

		<%
		for (Dimensions knownDisplaySize : knownDisplaySizes) {
		%>

			<aui:option label="<%= knownDisplaySize.getWidth() + separator + knownDisplaySize.getHeight() %>" />

		<%
		}
		%>

	</aui:select>
	)
</div>

<h3><liferay-ui:message key="screen-resolution" /></h3>

<div class="row-fields">
	<liferay-ui:message key="minimum-screen-resolution" />

	<aui:input
		cssClass="custom-min-resolution-field aui-field-digits"
		id="<%= SimpleRuleHandler.PROPERTY_RESOLUTION_MIN_WIDTH %>"
		inlineField="<%= true %>"
		label="width"
		name="<%= SimpleRuleHandler.PROPERTY_RESOLUTION_MIN_WIDTH %>"
		style="max-width:50px;"
		value="<%= minResolutionWidth %>"
		/>
	<%= separator %>
	<aui:input
		cssClass="custom-min-resolution-field aui-field-digits"
		id="<%= SimpleRuleHandler.PROPERTY_RESOLUTION_MIN_HEIGHT %>"
		inlineField="<%= true %>"
		label="height"
		name="<%= SimpleRuleHandler.PROPERTY_RESOLUTION_MIN_HEIGHT %>"
		style="max-width:50px;"
		value="<%= minResolutionHeight %>"
		/>

	(
	<aui:select
		helpMessage='<%= PluginPackageUtil.isInstalled("wurfl-web") ? StringPool.BLANK : "known-screen-resolutions-help" %>'
		id="min-known-screen-resolutions"
		inlineField="<%= true %>"
		inlineLabel="true"
		label="known-screen-resolutions"
		multiple="<%= false %>"
		name="min-known-screen-resolutions"
		style="max-width:100px;"
		>

		<aui:option label="" />

		<%
		for (Dimensions knownScreenResolution : knownScreenResolutions) {
		%>

			<aui:option label="<%= knownScreenResolution.getWidth() + separator + knownScreenResolution.getHeight() %>" />

		<%
		}
		%>

	</aui:select>
	)
</div>

<div class="row-fields">
	<liferay-ui:message key="maximum-screen-resolution" />

	<aui:input
		cssClass="custom-max-resolution-field aui-field-digits"
		id="<%= SimpleRuleHandler.PROPERTY_RESOLUTION_MAX_WIDTH %>"
		inlineField="<%= true %>"
		label="width"
		name="<%= SimpleRuleHandler.PROPERTY_RESOLUTION_MAX_WIDTH %>"
		style="max-width:50px;"
		value="<%= maxResolutionWidth %>"
		/>
	<%= separator %>
	<aui:input
		cssClass="custom-max-resolution-field aui-field-digits"
		id="<%= SimpleRuleHandler.PROPERTY_RESOLUTION_MAX_HEIGHT %>"
		inlineField="<%= true %>"
		label="height"
		name="<%= SimpleRuleHandler.PROPERTY_RESOLUTION_MAX_HEIGHT %>"
		style="max-width:50px;"
		value="<%= maxResolutionHeight %>"
		/>

	(
	<aui:select
		helpMessage='<%= PluginPackageUtil.isInstalled("wurfl-web") ? StringPool.BLANK : "known-screen-resolutions-help" %>'
		id="max-known-screen-resolutions"
		inlineField="<%= true %>"
		inlineLabel="true"
		label="known-screen-resolutions"
		multiple="<%= false %>"
		name="max-known-screen-resolutions"
		style="max-width:100px;"
		>

		<aui:option label="" />

		<%
		for (Dimensions knownScreenResolution : knownScreenResolutions) {
		%>

			<aui:option label="<%= knownScreenResolution.getWidth() + separator + knownScreenResolution.getHeight() %>" />

		<%
		}
		%>

	</aui:select>
	)

</div>


<script>



	YUI().use(
		'aui-node',
		function(Y) {
			var maxKnownSizes = Y.one('#<portlet:namespace/>max-known-dispay-sizes');
			var maxSizeHeight = Y.one('#<portlet:namespace/><%= SimpleRuleHandler.PROPERTY_DISPLAY_MAX_HEIGHT %>');
			var maxSizeWidth = Y.one('#<portlet:namespace/><%= SimpleRuleHandler.PROPERTY_DISPLAY_MAX_WIDTH %>');

			var minKnownSizes = Y.one('#<portlet:namespace/>min-known-dispay-sizes');
			var minSizeHeight = Y.one('#<portlet:namespace/><%= SimpleRuleHandler.PROPERTY_DISPLAY_MIN_HEIGHT %>');
			var minSizeWidth = Y.one('#<portlet:namespace/><%= SimpleRuleHandler.PROPERTY_DISPLAY_MIN_WIDTH %>');

			var maxKnownResolutions = Y.one('#<portlet:namespace/>max-known-screen-resolutions');
			var maxResWidth = Y.one('#<portlet:namespace/><%= SimpleRuleHandler.PROPERTY_RESOLUTION_MAX_WIDTH %>');
			var maxResHeight = Y.one('#<portlet:namespace/><%= SimpleRuleHandler.PROPERTY_RESOLUTION_MAX_HEIGHT %>');

			var minKnownResolutions = Y.one('#<portlet:namespace/>min-known-screen-resolutions');
			var minResWidth = Y.one('#<portlet:namespace/><%= SimpleRuleHandler.PROPERTY_RESOLUTION_MIN_WIDTH %>');
			var minResHeight = Y.one('#<portlet:namespace/><%= SimpleRuleHandler.PROPERTY_RESOLUTION_MIN_HEIGHT %>');

			maxKnownSizes.on(
			    'change',
			    function() {
			  		var sizes = maxKnownSizes.val().split("<%= separator %>");
			  		maxSizeWidth.val(sizes[0]);
			  		maxSizeHeight.val(sizes[1]);
			    }
			);

			minKnownSizes.on(
			    'change',
			    function() {
			  		var sizes = minKnownSizes.val().split("<%= separator %>");
			  		minSizeWidth.val(sizes[0]);
			  		minSizeHeight.val(sizes[1]);
			    }
			);

			maxKnownResolutions.on(
				'change',
				function() {
					var sizes = maxKnownResolutions.val().split("<%= separator %>");
					maxResWidth.val(sizes[0]);
					maxResHeight.val(sizes[1]);
			  	}
			);

			minKnownResolutions.on(
				'change',
				function() {
					var sizes = minKnownResolutions.val().split("<%= separator %>");
					minResWidth.val(sizes[0]);
					minResHeight.val(sizes[1]);
			  	}
			);

			Y.all('.custom-max-display-field').on(
				'change',
				function() {
					maxKnownSizes.set('selectedIndex', 0);
			  	}
			);


			Y.all('.custom-min-display-field').on(
				'change',
				function() {
					minKnownSizes.set('selectedIndex', 0);
			  	}
			);

			Y.all('.custom-max-resolution-field').on(
				'change',
				function() {
					maxKnownResolutions.set('selectedIndex', 0);
			  	}
			);

			Y.all('.custom-min-resolution-field').on(
				'change',
				function() {
					minKnownResolutions.set('selectedIndex', 0);
			  	}
			);
		}
	);
</script>