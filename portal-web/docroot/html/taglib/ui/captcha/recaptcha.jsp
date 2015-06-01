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

<%@ include file="/html/taglib/ui/captcha/init.jsp" %>

<script src="<%= PropsValues.CAPTCHA_ENGINE_RECAPTCHA_URL_SCRIPT %>?hl=<%= locale.getLanguage() %>" type="text/javascript"></script>

<div class="g-recaptcha" data-sitekey="<%= PrefsPropsUtil.getString(PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC, PropsValues.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC) %>"></div>

<noscript>
	<div style="width: 302px; height: 352px;">
		<div style="width: 302px; height: 352px; position: relative;">
			<div style="width: 302px; height: 352px; position: absolute;">
				<iframe frameborder="0" scrolling="no" src="<%= PropsValues.CAPTCHA_ENGINE_RECAPTCHA_URL_NOSCRIPT %><%= PrefsPropsUtil.getString(PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC, PropsValues.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC) %>" style="width: 302px; height:352px; border-style: none;"></iframe>
			</div>
			<div style="width: 250px; height: 80px; position: absolute; border-style: none; bottom: 21px; left: 25px; margin: 0px; padding: 0px; right: 25px;">
				<textarea class="g-recaptcha-response" id="g-recaptcha-response" name="g-recaptcha-response" style="width: 250px; height: 80px; border: 1px solid #c1c1c1; margin: 0px; padding: 0px; resize: none;" value=""></textarea>
			</div>
		</div>
	</div>
</noscript>