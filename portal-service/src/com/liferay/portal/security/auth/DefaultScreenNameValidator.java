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
		if (Validator.isEmailAddress(screenName) ||
			StringUtil.equalsIgnoreCase(screenName, CYRUS) ||
			StringUtil.equalsIgnoreCase(screenName, POSTFIX) ||
			hasInvalidChars(screenName)) {

			return false;
		}
		else {
			return true;
		}
	}

	private boolean hasInvalidChars(String screenName) {
		String specialChars = PropsUtil.get(PropsKeys.USERS_SCREEN_NAME_SPECIAL_CHARACTERS);

		specialChars.replaceAll(StringPool.SLASH, StringPool.BLANK);

		String validChars = "[A-Za-z0-9" + specialChars + "]+";

		return !screenName.matches(validChars);
	}

}