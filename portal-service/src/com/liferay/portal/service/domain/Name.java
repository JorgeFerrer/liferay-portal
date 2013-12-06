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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;

@ProviderType
public final class Name {

	public Name(String firstName, String lastName) {

		this(firstName, StringPool.BLANK, lastName, 0, 0);
	}

	public Name(
		String firstName, String middleName, String lastName) {

		this(firstName, middleName, lastName, 0, 0);
	}

	public Name(
		String firstName, String middleName, String lastName, int prefixId,
		int suffixId) {

		_firstName = firstName;
		_middleName = middleName;
		_lastName = lastName;
		_prefixId = prefixId;
		_suffixId = suffixId;
	}

	public String getFirstName() {
		return _firstName;
	}

	public String getMiddleName() {
		return _middleName;
	}

	public String getLastName() {
		return _lastName;
	}

	public int getPrefixId() {
		return _prefixId;
	}

	public int getSuffixId() {
		return _suffixId;
	}

	public void populate(Contact contact) {
		contact.setFirstName(_firstName);
		contact.setMiddleName(_middleName);
		contact.setLastName(_lastName);
		contact.setPrefixId(_prefixId);
		contact.setSuffixId(_suffixId);
	}

	public void populate(User user) {
		user.setFirstName(_firstName);
		user.setMiddleName(_middleName);
		user.setLastName(_lastName);
	}

	private final String _firstName;
	private final String _middleName;
	private final String _lastName;
	private final int _prefixId;
	private final int _suffixId;

}
