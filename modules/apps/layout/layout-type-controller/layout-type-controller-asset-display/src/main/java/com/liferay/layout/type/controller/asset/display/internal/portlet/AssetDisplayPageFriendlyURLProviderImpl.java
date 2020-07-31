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

package com.liferay.layout.type.controller.asset.display.internal.portlet;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = AssetDisplayPageFriendlyURLProvider.class)
public class AssetDisplayPageFriendlyURLProviderImpl
	implements AssetDisplayPageFriendlyURLProvider {

	@Override
	public String getFriendlyURL(
			String className, long classPK, ThemeDisplay themeDisplay)
		throws PortalException {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderTracker.getLayoutDisplayPageProvider(className);

		if (layoutDisplayPageProvider == null) {
			return null;
		}

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(classPK);

		if (layoutDisplayPageObjectProvider == null) {
			return null;
		}

		if (!AssetDisplayPageUtil.hasAssetDisplayPage(
				themeDisplay.getScopeGroupId(),
				layoutDisplayPageObjectProvider.getClassNameId(),
				layoutDisplayPageObjectProvider.getClassPK(),
				layoutDisplayPageObjectProvider.getClassTypeId())) {

			return null;
		}

		StringBundler sb = new StringBundler(3);

		Group group = _groupLocalService.getGroup(
			layoutDisplayPageObjectProvider.getGroupId());

		sb.append(
			_portal.getGroupFriendlyURL(
				group.getPublicLayoutSet(), themeDisplay));

		sb.append(layoutDisplayPageProvider.getURLSeparator());
		sb.append(
			layoutDisplayPageObjectProvider.getURLTitle(themeDisplay.getLocale()));

		return sb.toString();
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private Portal _portal;

}