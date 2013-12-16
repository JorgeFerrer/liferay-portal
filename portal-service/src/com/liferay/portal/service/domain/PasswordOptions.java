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

package com.liferay.portal.service.domain;

import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

@ProviderType
public final class PasswordOptions {
	public PasswordOptions(String password) {
		this(password, false, null,null);
	}

	public PasswordOptions(String password, boolean passwordReset) {
		this(password, passwordReset, null, null);
	}

	public PasswordOptions(
			String password, boolean passwordReset,
			String reminderQueryQuestion, String reminderQueryAnswer) {

		_password = password;
		_passwordReset = passwordReset;
		_reminderQueryAnswer = reminderQueryAnswer;
		_reminderQueryQuestion = reminderQueryQuestion;
	}

	public String getPassword() {
		return _password;
	}

	public String getReminderQueryAnswer() {
		return _reminderQueryAnswer;
	}

	public String getReminderQueryQuestion() {
		return _reminderQueryQuestion;
	}

	public Serializable isAutoPassword() {
		return Validator.isNull(_password);
	}

	public boolean isPasswordReset() {
		return _passwordReset;
	}

	private final String _password;
	private final boolean _passwordReset;
	private final String _reminderQueryQuestion;
	private final String _reminderQueryAnswer;

}