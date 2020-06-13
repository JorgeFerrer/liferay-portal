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

package com.liferay.info.type;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Optional;

/**
 * @author Jorge Ferrer
 */
public class WebImage {

	public WebImage(String url) {
		_url = url;
	}

	public Optional<String> getAltOptional() {
		return Optional.of(_alt);
	}

	public String getUrl() {
		return _url;
	}

	public WebImage setAlt(String alt) {
		_alt = alt;

		return this;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = JSONUtil.put("url", _url);

		if (Validator.isNotNull(_alt)) {
			jsonObject = jsonObject.put("alt", _alt);
		}

		return jsonObject;
	}

	private String _alt;
	private final String _url;

}