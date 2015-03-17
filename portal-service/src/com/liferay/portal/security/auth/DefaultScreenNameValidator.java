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

package com.liferay.portal.security.auth;

import java.util.Locale;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 */
public class DefaultScreenNameValidator implements ScreenNameValidator {

	public static final String CYRUS = "cyrus";

	public static final String POSTFIX = "postfix";

	@Override
	public boolean validate(long companyId, String screenName) {
		if (Validator.isEmailAddress(screenName)) {

			_description = "the-screen-name-cannot-be-an-email-address";

			return false;
		}
		else if (StringUtil.equalsIgnoreCase(screenName, CYRUS) ||
			StringUtil.equalsIgnoreCase(screenName, POSTFIX)) {

			_arguments = "CYRUS, POSTFIX";
			_description = "the-screen-name-cannot-be-a-reserved-word";

			return false;
		}

		return !hasInvalidChars(screenName);
	}

	@Override
	public String getDescription(Locale locale) {
		if (_arguments != null) {
			return LanguageUtil.format(locale, _description, _arguments, false);
		}

		return LanguageUtil.get(locale, _description);
	}

	@Override
	public String getJSValidation() {
		return "function(val) {" +
					"var pattern = new RegExp('[^A-Za-z0-9" +
						getSpecialChars() + "]');" +
					"if (val.match(pattern)) {" +
						"return false;" +
					"}" +
					"return true;" +
				"}";
	}

	@Override
	public String getJSValidationErrorMessage(Locale locale) {
		return LanguageUtil.format(locale,
			"the-screen-name-must-contain-only-alphanumeric",
			getSpecialChars(), false);
	}

	private String getSpecialChars() {
		if (_specialChars == null) {
			String specialChars = PropsUtil.get(
				PropsKeys.USERS_SCREEN_NAME_SPECIAL_CHARACTERS);

			_specialChars = specialChars.replaceAll(StringPool.SLASH, StringPool.BLANK);
		}

		return _specialChars;
	}

	private boolean hasInvalidChars(String screenName) {
		String validChars = "[A-Za-z0-9" + getSpecialChars() + "]+";

		if (!screenName.matches(validChars)) {
			_arguments = getSpecialChars();
			_description = "the-screen-name-must-contain-only-alphanumeric";

			return true;
		}

		return false;
	}

	private String _arguments;
	private String _description;
	private String _specialChars;

}