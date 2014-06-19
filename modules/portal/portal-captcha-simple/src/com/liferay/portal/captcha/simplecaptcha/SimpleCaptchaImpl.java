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

package com.liferay.portal.captcha.simplecaptcha;

import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.captcha.CaptchaMaxChallengesException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.WebKeys;
import com.liferay.registry.util.StringPlus;
import com.liferay.util.freemarker.FreeMarkerTaglibFactoryUtil;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.ServletContextHashModel;

import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;

import java.io.IOException;

import java.net.URL;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.captcha.backgrounds.BackgroundProducer;
import nl.captcha.gimpy.GimpyRenderer;
import nl.captcha.noise.NoiseProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.TextProducer;
import nl.captcha.text.renderer.WordRenderer;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Sanz
 * @author Raymond Augé
 */
@Component(
	configurationPid = "captcha.engine.simplecaptcha",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	immediate = true,
	property = {
		"captcha.max.challenges:Integer=1",
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
		"path=/common/captcha/simple_captcha",
		"service.ranking:Integer=-10000" // low priority
	},
	service = {
		Captcha.class,
		StrutsAction.class
	}
)
public class SimpleCaptchaImpl extends BaseStrutsAction implements Captcha {

	@Activate
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void activate(Map properties) {
		_properties = properties;

		_captchaMaxChallenges = MapUtil.getInteger(
			properties, PropsKeys.CAPTCHA_MAX_CHALLENGES);
		_captchaHeight = MapUtil.getInteger(
			properties, CAPTCHA_ENGINE_SIMPLECAPTCHA_HEIGHT);
		_captchaWidth = MapUtil.getInteger(
			properties, CAPTCHA_ENGINE_SIMPLECAPTCHA_WIDTH);

		initBackgroundProducers();
		initGimpyRenderers();
		initNoiseProducers();
		initTextProducers();
		initWordRenderers();

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		String templateId = "/META-INF/resources/view.ftl";

		URL url = classLoader.getResource(templateId);

		_templateResource = new URLTemplateResource(templateId, url);

		HttpServlet httpServlet = new HttpServlet() {

			@Override
			public ServletContext getServletContext() {
				return _servletContext;
			}

		};

		_servletContextHashModel = new ServletContextHashModel(
			httpServlet, ObjectWrapper.DEFAULT_WRAPPER);

		_taglibsFactory = FreeMarkerTaglibFactoryUtil.createTaglibFactory(
			_servletContext);
	}

	@Override
	public void check(HttpServletRequest request) throws CaptchaException {
		if (!isEnabled(request)) {
			return;
		}

		if (!validateChallenge(request)) {
			incrementCounter(request);

			checkMaxChallenges(request);

			throw new CaptchaTextException();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("CAPTCHA text is valid");
		}
	}

	@Override
	public void check(PortletRequest portletRequest) throws CaptchaException {
		if (!isEnabled(portletRequest)) {
			return;
		}

		if (!validateChallenge(portletRequest)) {
			incrementCounter(portletRequest);

			checkMaxChallenges(portletRequest);

			throw new CaptchaTextException();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("CAPTCHA text is valid");
		}
	}

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		boolean captchaEnabled = false;

		try {
			captchaEnabled = isEnabled(request);
		}
		catch (CaptchaMaxChallengesException cmce) {
			captchaEnabled = true;
		}

		if (captchaEnabled) {
			processTemplate(request, response);
		}

		return null;
	}

	@Override
	public String getTaglibPath() {
		return _TAGLIB_PATH;
	}

	@Override
	public boolean isEnabled(HttpServletRequest request)
		throws CaptchaException {

		checkMaxChallenges(request);

		if (_captchaMaxChallenges >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isEnabled(PortletRequest portletRequest)
		throws CaptchaException {

		checkMaxChallenges(portletRequest);

		if (_captchaMaxChallenges >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void serveImage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		HttpSession session = request.getSession();

		nl.captcha.Captcha simpleCaptcha = getSimpleCaptcha();

		session.setAttribute(WebKeys.CAPTCHA_TEXT, simpleCaptcha.getAnswer());

		response.setContentType(ContentTypes.IMAGE_PNG);

		CaptchaServletUtil.writeImage(
			response.getOutputStream(), simpleCaptcha.getImage());
	}

	@Override
	public void serveImage(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		PortletSession portletSession = resourceRequest.getPortletSession();

		nl.captcha.Captcha simpleCaptcha = getSimpleCaptcha();

		portletSession.setAttribute(
			WebKeys.CAPTCHA_TEXT, simpleCaptcha.getAnswer());

		resourceResponse.setContentType(ContentTypes.IMAGE_PNG);

		CaptchaServletUtil.writeImage(
			resourceResponse.getPortletOutputStream(),
			simpleCaptcha.getImage());
	}

	protected void addAttributes(HttpServletRequest request, Template template)
		throws CaptchaException {

		template.put("captchaEnabled", isEnabled(request));
	}

	protected void checkMaxChallenges(HttpServletRequest request)
		throws CaptchaMaxChallengesException {

		if (_captchaMaxChallenges > 0) {
			HttpSession session = request.getSession();

			Integer count = (Integer)session.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			checkMaxChallenges(count);
		}
	}

	protected void checkMaxChallenges(Integer count)
		throws CaptchaMaxChallengesException {

		if ((count != null) && (count > _captchaMaxChallenges)) {
			throw new CaptchaMaxChallengesException();
		}
	}

	protected void checkMaxChallenges(PortletRequest portletRequest)
		throws CaptchaMaxChallengesException {

		if (_captchaMaxChallenges > 0) {
			PortletSession portletSession = portletRequest.getPortletSession();

			Integer count = (Integer)portletSession.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			checkMaxChallenges(count);
		}
	}

	protected BackgroundProducer getBackgroundProducer() {
		if (_backgroundProducers.length == 1) {
			return _backgroundProducers[0];
		}

		int pos = RandomUtil.nextInt(_backgroundProducers.length);

		return _backgroundProducers[pos];
	}

	protected GimpyRenderer getGimpyRenderer() {
		if (_gimpyRenderers.length == 1) {
			return _gimpyRenderers[0];
		}

		int pos = RandomUtil.nextInt(_gimpyRenderers.length);

		return _gimpyRenderers[pos];
	}

	protected int getHeight() {
		return _captchaHeight;
	}

	protected NoiseProducer getNoiseProducer() {
		if (_noiseProducers.length == 1) {
			return _noiseProducers[0];
		}

		int pos = RandomUtil.nextInt(_noiseProducers.length);

		return _noiseProducers[pos];
	}

	protected nl.captcha.Captcha getSimpleCaptcha() {
		nl.captcha.Captcha.Builder captchaBuilder =
			new nl.captcha.Captcha.Builder(getWidth(), getHeight());

		captchaBuilder.addText(getTextProducer(), getWordRenderer());
		captchaBuilder.addBackground(getBackgroundProducer());
		captchaBuilder.gimp(getGimpyRenderer());
		captchaBuilder.addNoise(getNoiseProducer());
		captchaBuilder.addBorder();

		return captchaBuilder.build();
	}

	protected TextProducer getTextProducer() {
		if (_textProducers.length == 1) {
			return _textProducers[0];
		}

		int pos = RandomUtil.nextInt(_textProducers.length);

		return _textProducers[pos];
	}

	protected int getWidth() {
		return _captchaWidth;
	}

	protected WordRenderer getWordRenderer() {
		if (_wordRenderers.length == 1) {
			return _wordRenderers[0];
		}

		int pos = RandomUtil.nextInt(_wordRenderers.length);

		return _wordRenderers[pos];
	}

	protected void incrementCounter(HttpServletRequest request) {
		if ((_captchaMaxChallenges > 0) &&
			Validator.isNotNull(request.getRemoteUser())) {

			HttpSession session = request.getSession();

			Integer count = (Integer)session.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			session.setAttribute(
				WebKeys.CAPTCHA_COUNT, incrementCounter(count));
		}
	}

	protected Integer incrementCounter(Integer count) {
		if (count == null) {
			count = new Integer(1);
		}
		else {
			count = new Integer(count.intValue() + 1);
		}

		return count;
	}

	protected void incrementCounter(PortletRequest portletRequest) {
		if ((_captchaMaxChallenges > 0) &&
			Validator.isNotNull(portletRequest.getRemoteUser())) {

			PortletSession portletSession = portletRequest.getPortletSession();

			Integer count = (Integer)portletSession.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			portletSession.setAttribute(
				WebKeys.CAPTCHA_COUNT, incrementCounter(count));
		}
	}

	protected void initBackgroundProducers() {
		List<String> backgroundProducerClassNames = StringPlus.asList(
			_properties.get(CAPTCHA_ENGINE_SIMPLECAPTCHA_BACKGROUND_PRODUCERS));

		_backgroundProducers = new BackgroundProducer[
			backgroundProducerClassNames.size()];

		for (int i = 0; i < backgroundProducerClassNames.size(); i++) {
			String backgroundProducerClassName =
				backgroundProducerClassNames.get(i);

			_backgroundProducers[i] = getInstance(
				BackgroundProducer.class, backgroundProducerClassName);
		}
	}

	protected void initGimpyRenderers() {
		List<String> gimpyRendererClassNames = StringPlus.asList(
			_properties.get(CAPTCHA_ENGINE_SIMPLECAPTCHA_GIMPY_RENDERERS));

		_gimpyRenderers = new GimpyRenderer[
			gimpyRendererClassNames.size()];

		for (int i = 0; i < gimpyRendererClassNames.size(); i++) {
			String gimpyRendererClassName = gimpyRendererClassNames.get(i);

			_gimpyRenderers[i] = getInstance(
				GimpyRenderer.class, gimpyRendererClassName);
		}
	}

	protected void initNoiseProducers() {
		List<String> noiseProducerClassNames = StringPlus.asList(
			_properties.get(CAPTCHA_ENGINE_SIMPLECAPTCHA_NOISE_PRODUCERS));

		_noiseProducers = new NoiseProducer[noiseProducerClassNames.size()];

		for (int i = 0; i < noiseProducerClassNames.size(); i++) {
			String noiseProducerClassName = noiseProducerClassNames.get(i);

			_noiseProducers[i] = getInstance(
				NoiseProducer.class, noiseProducerClassName);
		}
	}

	protected void initTextProducers() {
		List<String> textProducerClassNames = StringPlus.asList(
			_properties.get(CAPTCHA_ENGINE_SIMPLECAPTCHA_TEXT_PRODUCERS));

		_textProducers = new TextProducer[textProducerClassNames.size()];

		for (int i = 0; i < textProducerClassNames.size(); i++) {
			String textProducerClassName = textProducerClassNames.get(i);

			_textProducers[i] = getInstance(
				TextProducer.class,	textProducerClassName);
		}
	}

	protected void initWordRenderers() {
		List<String> wordRendererClassNames = StringPlus.asList(
			_properties.get(CAPTCHA_ENGINE_SIMPLECAPTCHA_WORD_RENDERERS));

		_wordRenderers = new WordRenderer[wordRendererClassNames.size()];

		for (int i = 0; i < wordRendererClassNames.size(); i++) {
			String wordRendererClassName = wordRendererClassNames.get(i);

			_wordRenderers[i] = getInstance(
				WordRenderer.class, wordRendererClassName);
		}
	}

	protected boolean validateChallenge(HttpServletRequest request)
		throws CaptchaException {

		HttpSession session = request.getSession();

		String captchaText = (String)session.getAttribute(WebKeys.CAPTCHA_TEXT);

		if (captchaText == null) {
			_log.error(
				"CAPTCHA text is null. User " + request.getRemoteUser() +
					" may be trying to circumvent the CAPTCHA.");

			throw new CaptchaTextException();
		}

		boolean valid = captchaText.equals(
			ParamUtil.getString(request, "captchaText"));

		if (valid) {
			session.removeAttribute(WebKeys.CAPTCHA_TEXT);
		}

		return valid;
	}

	protected boolean validateChallenge(PortletRequest portletRequest)
		throws CaptchaException {

		PortletSession portletSession = portletRequest.getPortletSession();

		String captchaText = (String)portletSession.getAttribute(
			WebKeys.CAPTCHA_TEXT);

		if (captchaText == null) {
			_log.error(
				"CAPTCHA text is null. User " + portletRequest.getRemoteUser() +
					" may be trying to circumvent the CAPTCHA.");

			throw new CaptchaTextException();
		}

		boolean valid = captchaText.equals(
			ParamUtil.getString(portletRequest, "captchaText"));

		if (valid) {
			portletSession.removeAttribute(WebKeys.CAPTCHA_TEXT);
		}

		return valid;
	}

	private <E> E getInstance(
		Class<E> type, String backgroundProducerClassName) {

		try {
			Class<? extends E> clazz = Class.forName(
				backgroundProducerClassName).asSubclass(type);

			return clazz.newInstance();
		}
		catch (Exception e) {
			return null;
		}
	}

	private void processTemplate(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, _templateResource, false);

		template.put("Application", _servletContextHashModel);

		template.put("PortalJspTagLibs", _taglibsFactory);

		HttpRequestHashModel httpRequestHashModel = new HttpRequestHashModel(
			request, response, ObjectWrapper.DEFAULT_WRAPPER);

		template.prepare(request);

		template.put("Request", httpRequestHashModel);

		String namespace = StringPool.BLANK;

		PortletResponse portletResponse =
			(PortletResponse)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse != null) {
			namespace = portletResponse.getNamespace();
		}

		template.put("namespace", namespace);

		addAttributes(request, template);

		try {
			template.processTemplate(response.getWriter());
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}
	}

	@Reference(target = "(original.bean=true)")
	private void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	/* Used by the DS lifecycle */
	@SuppressWarnings("unused")
	private void unsetServletContext(ServletContext servletContext) {
		_servletContext = null;
	}

	private static final String CAPTCHA_ENGINE_SIMPLECAPTCHA_BACKGROUND_PRODUCERS = "captcha.engine.simplecaptcha.background.producers";
	private static final String CAPTCHA_ENGINE_SIMPLECAPTCHA_GIMPY_RENDERERS = "captcha.engine.simplecaptcha.gimpy.renderers";
	private static final String CAPTCHA_ENGINE_SIMPLECAPTCHA_HEIGHT = "captcha.engine.simplecaptcha.height";
	private static final String CAPTCHA_ENGINE_SIMPLECAPTCHA_NOISE_PRODUCERS = "captcha.engine.simplecaptcha.noise.producers";
	private static final String CAPTCHA_ENGINE_SIMPLECAPTCHA_TEXT_PRODUCERS = "captcha.engine.simplecaptcha.text.producers";
	private static final String CAPTCHA_ENGINE_SIMPLECAPTCHA_WIDTH = "captcha.engine.simplecaptcha.width";
	private static final String CAPTCHA_ENGINE_SIMPLECAPTCHA_WORD_RENDERERS = "captcha.engine.simplecaptcha.word.renderers";

	private static final String _TAGLIB_PATH =
		Portal.PATH_MAIN + "/common/captcha/simple_captcha";

	private static Log _log = LogFactoryUtil.getLog(SimpleCaptchaImpl.class);

	private BackgroundProducer[] _backgroundProducers;
	private int _captchaHeight;
	private int _captchaMaxChallenges;
	private int _captchaWidth;
	private GimpyRenderer[] _gimpyRenderers;
	private NoiseProducer[] _noiseProducers;
	private Map<String, Object> _properties;
	private ServletContext _servletContext;
	private ServletContextHashModel _servletContextHashModel;
	private TemplateHashModel _taglibsFactory;
	private TemplateResource _templateResource;
	private TextProducer[] _textProducers;
	private WordRenderer[] _wordRenderers;

}