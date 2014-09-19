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

package com.liferay.amazon.rankings.web.portlet.validator;

import com.liferay.amazon.rankings.web.model.AmazonRankings;
import com.liferay.amazon.rankings.web.util.AmazonRankingsUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=com_liferay_amazon_rankings_web_portlet_AmazonRankingsPortlet"
	}
)
public class AmazonRankingsPreferencesValidator
	implements PreferencesValidator {

	@Override
	public void validate(PortletPreferences portletPreferences)
		throws ValidatorException {

		List<String> badIsbns = new ArrayList<String>();

		String amazonAccessKeyId = portletPreferences.getValue(
			"amazon.access.key.id", StringPool.BLANK);
		String amazonAssociateTag = portletPreferences.getValue(
			"amazon.associate.tag" , StringPool.BLANK);
		String amazonSecretAccessKey = portletPreferences.getValue(
			"amazon.secret.access.key", StringPool.BLANK);
		String[] isbns = portletPreferences.getValues(
			"isbns", StringPool.EMPTY_ARRAY);

		for (String isbn : isbns) {
			AmazonRankings amazonRankings =
				AmazonRankingsUtil.getAmazonRankings(
					amazonAccessKeyId, amazonAssociateTag,
					amazonSecretAccessKey, isbn);

			if (amazonRankings == null) {
				badIsbns.add(isbn);

				if (_log.isInfoEnabled()) {
					_log.info("Invalid ISBN " + isbn);
				}
			}
		}

		if (!badIsbns.isEmpty()) {
			throw new ValidatorException("Unable to retrieve ISBNs", badIsbns);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AmazonRankingsPreferencesValidator.class);

}