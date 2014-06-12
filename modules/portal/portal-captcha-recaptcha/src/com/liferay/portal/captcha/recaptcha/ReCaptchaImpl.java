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

package com.liferay.portal.captcha.recaptcha;

import com.liferay.portal.captcha.simplecaptcha.SimpleCaptchaImpl;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Tagnaouti Boubker
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Daniel Sanz
 * @author Raymond Augé
 */
@Component(
	configurationPid = "captcha.engine.recaptcha",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	property = {
		"captcha.max.challenges:Integer=1",
		"captcha.engine.recaptcha.key.private=",
		"captcha.engine.recaptcha.key.public=",
		"captcha.engine.recaptcha.url.script=http://www.google.com/recaptcha/api/challenge?k=",
		"captcha.engine.recaptcha.url.noscript=http://www.google.com/recaptcha/api/noscript?k=",
		"captcha.engine.recaptcha.url.verify=http://www.google.com/recaptcha/api/verify",
		"captcha.engine.simplecaptcha.height:Integer=50",
		"captcha.engine.simplecaptcha.width:Integer=150",
		"captcha.engine.simplecaptcha.background.producers=nl.captcha.backgrounds.FlatColorBackgroundProducer",
		"captcha.engine.simplecaptcha.background.producers=nl.captcha.backgrounds.GradiatedBackgroundProducer",
		"captcha.engine.simplecaptcha.background.producers=nl.captcha.backgrounds.SquigglesBackgroundProducer",
		"captcha.engine.simplecaptcha.background.producers=nl.captcha.backgrounds.TransparentBackgroundProducer",
		"captcha.engine.simplecaptcha.gimpy.renderers=nl.captcha.gimpy.BlockGimpyRenderer",
		"captcha.engine.simplecaptcha.gimpy.renderers=nl.captcha.gimpy.DropShadowGimpyRenderer",
		"captcha.engine.simplecaptcha.gimpy.renderers=nl.captcha.gimpy.FishEyeGimpyRenderer",
		"captcha.engine.simplecaptcha.gimpy.renderers=nl.captcha.gimpy.RippleGimpyRenderer",
		"captcha.engine.simplecaptcha.gimpy.renderers=nl.captcha.gimpy.ShearGimpyRenderer",
		"captcha.engine.simplecaptcha.noise.producers=nl.captcha.noise.CurvedLineNoiseProducer",
		"captcha.engine.simplecaptcha.noise.producers=nl.captcha.noise.StraightLineNoiseProducer",
		"captcha.engine.simplecaptcha.text.producers=com.liferay.portal.captcha.simplecaptcha.PinNumberTextProducer",
		"captcha.engine.simplecaptcha.word.renderers=nl.captcha.text.renderer.DefaultWordRenderer",
		"path=/common/captcha/recaptcha",
		"service.ranking:Integer=-1000"
	},
	service = {
		Captcha.class,
		StrutsAction.class
	}
)
public class ReCaptchaImpl extends SimpleCaptchaImpl {

	@Activate
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void activate(Map properties) {
		super.activate(properties);

		_properties = properties;

		_keyPrivate = MapUtil.getString(
			_properties, CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE);
		_keyPublic = MapUtil.getString(
			_properties, CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC);
		_urlNoscript = MapUtil.getString(
			_properties, CAPTCHA_ENGINE_RECAPTCHA_URL_NOSCRIPT);
		_urlScript = MapUtil.getString(
			_properties, CAPTCHA_ENGINE_RECAPTCHA_URL_SCRIPT);
		_urlVerify = MapUtil.getString(
			_properties, CAPTCHA_ENGINE_RECAPTCHA_URL_VERIFY);
	}


	@Override
	public String getTaglibPath() {
		return _TAGLIB_PATH;
	}

	@Override
	public void serveImage(
		HttpServletRequest request, HttpServletResponse response) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void serveImage(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		throw new UnsupportedOperationException();
	}

	@Override
	protected void addAttributes(HttpServletRequest request, Template template)
		throws CaptchaException {

		super.addAttributes(request, template);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		template.put("keyPublic", _keyPublic);
		template.put("locale", themeDisplay.getLocale());
		template.put(
			"urlNoscript",
			HttpUtil.protocolize(_urlNoscript, request.isSecure()));
		template.put(
			"urlScript", HttpUtil.protocolize(_urlScript, request.isSecure()));
	}

	protected void validateCaptcha(
		ActionRequest actionRequest, String captchaEngineImpl,
		String reCaptchaPrivateKey, String reCaptchaPublicKey) {

		if (!captchaEngineImpl.equals(getClass().getName())) {
			return;
		}

		if (Validator.isNull(reCaptchaPublicKey)) {
			SessionErrors.add(actionRequest, "reCaptchaPublicKey");
		}
		else if (Validator.isNull(reCaptchaPrivateKey)) {
			SessionErrors.add(actionRequest, "reCaptchaPrivateKey");
		}
	}

	@Override
	protected boolean validateChallenge(HttpServletRequest request)
		throws CaptchaException {

		String reCaptchaChallenge = ParamUtil.getString(
			request, "recaptcha_challenge_field");
		String reCaptchaResponse = ParamUtil.getString(
			request, "recaptcha_response_field");

		Http.Options options = new Http.Options();

		options.addPart("challenge", reCaptchaChallenge);

		try {
			options.addPart("privatekey", _keyPrivate);
		}
		catch (SystemException se) {
			_log.error(se, se);
		}

		options.addPart("remoteip", request.getRemoteAddr());
		options.addPart("response", reCaptchaResponse);
		options.setLocation(
			HttpUtil.protocolize(_urlVerify, request.isSecure()));
		options.setPost(true);

		String content = null;

		try {
			content = HttpUtil.URLtoString(options);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

			throw new CaptchaTextException();
		}

		if (content == null) {
			_log.error("reCAPTCHA did not return a result");

			throw new CaptchaTextException();
		}

		String[] messages = content.split("\r?\n");

		if (messages.length < 1) {
			_log.error("reCAPTCHA did not return a valid result: " + content);

			throw new CaptchaTextException();
		}

		return GetterUtil.getBoolean(messages[0]);
	}

	@Override
	protected boolean validateChallenge(PortletRequest portletRequest)
		throws CaptchaException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		request = PortalUtil.getOriginalServletRequest(request);

		return validateChallenge(request);
	}

	public static final String CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE = "captcha.engine.recaptcha.key.private";
	public static final String CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC = "captcha.engine.recaptcha.key.public";
	public static final String CAPTCHA_ENGINE_RECAPTCHA_URL_NOSCRIPT = "captcha.engine.recaptcha.url.noscript";
	public static final String CAPTCHA_ENGINE_RECAPTCHA_URL_SCRIPT = "captcha.engine.recaptcha.url.script";
	public static final String CAPTCHA_ENGINE_RECAPTCHA_URL_VERIFY = "captcha.engine.recaptcha.url.verify";

	private static final String _TAGLIB_PATH =
		Portal.PATH_MAIN + "/common/captcha/recaptcha";

	private static Log _log = LogFactoryUtil.getLog(ReCaptchaImpl.class);

	private String _keyPrivate;
	private String _keyPublic;
	private Map<String, Object> _properties;
	private String _urlNoscript;
	private String _urlScript;
	private String _urlVerify;

}