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

package com.liferay.info.internal.display.contributor;

import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.internal.util.GenericsUtil;
import com.liferay.info.item.NoSuchInfoItemException;
import com.liferay.info.item.provider.InfoItemProvider;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jorge Ferrer
 */
public class InfoDisplayContributorInfoItemProviderWrapper
	implements InfoItemProvider {

	public InfoDisplayContributorInfoItemProviderWrapper(
		InfoDisplayContributor infoDisplayContributor) {

		_infoDisplayContributor = infoDisplayContributor;
	}

	@Override
	public Object getInfoItem(long classPK) throws NoSuchInfoItemException {
		try {
			InfoDisplayObjectProvider  infoDisplayObjectProvider =
				_infoDisplayContributor.getInfoDisplayObjectProvider(classPK);

			return infoDisplayObjectProvider.getDisplayObject();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public String getItemClassName() {
		return GenericsUtil.getItemClassName(_infoDisplayContributor);
	}

	private final InfoDisplayContributor _infoDisplayContributor;

}