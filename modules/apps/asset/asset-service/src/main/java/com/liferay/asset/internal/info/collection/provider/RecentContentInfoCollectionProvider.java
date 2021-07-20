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

package com.liferay.asset.internal.info.collection.provider;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.util.AssetHelper;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.portal.kernel.context.ScopeGroupInvocationContextProvider;
import com.liferay.portal.kernel.context.SiteLayoutInvocationContextProvider;
import com.liferay.portal.kernel.context.UserInvocationContextProvider;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = InfoCollectionProvider.class)
public class RecentContentInfoCollectionProvider
	extends BaseAssetsInfoCollectionProvider
	implements InfoCollectionProvider<AssetEntry> {

	@Override
	public InfoPage<AssetEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			Field.MODIFIED_DATE, "DESC", collectionQuery.getPagination());

		try {
			SearchContext searchContext = _getSearchContext();

			Hits hits = _assetHelper.search(
				searchContext, assetEntryQuery, assetEntryQuery.getStart(),
				assetEntryQuery.getEnd());

			Long count = _assetHelper.searchCount(
				searchContext, assetEntryQuery);

			return InfoPage.of(
				_assetHelper.getAssetEntries(hits),
				collectionQuery.getPagination(), count.intValue());
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries", exception);
		}

		return InfoPage.of(
			Collections.emptyList(), collectionQuery.getPagination(), 0);
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "recent-content");
	}

	private SearchContext _getSearchContext() {
		Group scopeGroup = scopeGroupInvocationContextProvider.getCurrent();
		Layout siteLayout = siteLayoutInvocationContextProvider.getCurrent();
		User user = userInvocationContextProvider.getCurrent();

		SearchContext searchContext = SearchContextFactory.getInstance(
			new long[0], new String[0], new HashMap<>(),
			scopeGroup.getCompanyId(), null, siteLayout, null,
			scopeGroup.getGroupId(), null, user.getUserId());

		searchContext.setSorts(
			SortFactoryUtil.create(Field.MODIFIED_DATE, Sort.LONG_TYPE, true),
			SortFactoryUtil.create(Field.CREATE_DATE, Sort.LONG_TYPE, true));

		return searchContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RecentContentInfoCollectionProvider.class);

	@Reference
	private AssetHelper _assetHelper;

	@Reference(target = "(bundle.symbolic.name=com.liferay.asset.service)")
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	protected ScopeGroupInvocationContextProvider
		scopeGroupInvocationContextProvider;

	@Reference
	protected SiteLayoutInvocationContextProvider
		siteLayoutInvocationContextProvider;

	@Reference
	protected UserInvocationContextProvider
		userInvocationContextProvider;

}