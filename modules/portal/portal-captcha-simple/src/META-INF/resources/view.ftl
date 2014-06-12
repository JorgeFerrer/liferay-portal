<#--
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
-->

<#assign aui = PortalJspTagLibs["/WEB-INF/tld/aui.tld"] />
<#assign liferay_ui = PortalJspTagLibs["/WEB-INF/tld/liferay-ui.tld"] />

<#assign url = request.getAttribute("liferay-ui:captcha:url")>

<#if (captchaEnabled)>
	<div class="taglib-captcha">
		<img alt="<@liferay_ui["message"] key="text-to-identify" />" class="captcha" id="${namespace}captcha" src="${url}" />

		<@liferay_ui["icon"] cssClass="refresh" iconCssClass="icon-refresh" id="refreshCaptcha" label=false localizeMessage=true message="refresh-captcha" url="javascript:;" />

		<@aui["input"] label="text-verification" name="captchaText" size="10" type="text" value="">
			<@aui["validator"] name="required" />
		</@>
	</div>

	<@aui["script"] use="aui-base">
		A.one('#${namespace}refreshCaptcha').on(
			'click',
			function() {
				var url = Liferay.Util.addParams('t=' + A.Lang.now(), '${url}');

				A.one('#${namespace}captcha').attr('src', url);
			}
		);
	</@>
</#if>