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

<noscript>
	<iframe frameborder="0" height="300" src="${urlNoscript}${keyPublic}" width="500"></iframe><br />

	<textarea cols="40" name="recaptcha_challenge_field" rows="3"></textarea>

	<input name="recaptcha_response_field" type="hidden" value="manual_challenge" />
</noscript>

<@aui["script"] position="inline">
	var RecaptchaOptions = {
		lang : '${locale.language}',
		theme : 'white'
	};
</@>

<script src="${urlScript}${keyPublic}" type="text/javascript"></script>