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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Contact;

@ProviderType
public final class SocialSns {

	public SocialSns(
		String aimSn, String facebookSn, String icqSn, String jabberSn,
		String msnSn, String mySpaceSn, String skypeSn, String smsSn,
		String twitterSn, String ymSn) {

		_aimSn = _format(aimSn);
		_facebookSn = _format(facebookSn);
		_icqSn = _format(icqSn);
		_jabberSn = _format(jabberSn);
		_msnSn = _format(msnSn);
		_mySpaceSn = _format(mySpaceSn);
		_skypeSn = _format(skypeSn);
		_smsSn = _format(smsSn);
		_twitterSn = _format(twitterSn);
		_ymSn = _format(ymSn);
	}

	public String getAimSn() {
		return _aimSn;
	}

	public String getFacebookSn() {
		return _facebookSn;
	}

	public String getIcqSn() {
		return _icqSn;
	}

	public String getJabberSn() {
		return _jabberSn;
	}

	public String getMsnSn() {
		return _msnSn;
	}

	public String getMySpaceSn() {
		return _mySpaceSn;
	}

	public String getSkypeSn() {
		return _skypeSn;
	}

	public String getSmsSn() {
		return _smsSn;
	}

	public String getTwitterSn() {
		return _twitterSn;
	}

	public String getYmSn() {
		return _ymSn;
	}
	
	public void populate(Contact contact) {
		contact.setAimSn(_aimSn);
		contact.setFacebookSn(_facebookSn);
		contact.setIcqSn(_icqSn);
		contact.setJabberSn(_jabberSn);
		contact.setMsnSn(_msnSn);
		contact.setMySpaceSn(_mySpaceSn);
		contact.setSkypeSn(_skypeSn);
		contact.setSmsSn(_smsSn);
		contact.setTwitterSn(_twitterSn);
		contact.setYmSn(_ymSn);
	}

	private String _format(String value) {
		return StringUtil.toLowerCase(value.trim());
	}

	private String _smsSn;
	private String _aimSn;
	private String _facebookSn;
	private String _icqSn;
	private String _jabberSn;
	private String _msnSn;
	private String _mySpaceSn;
	private String _skypeSn;
	private String _twitterSn;
	private String _ymSn;

}
