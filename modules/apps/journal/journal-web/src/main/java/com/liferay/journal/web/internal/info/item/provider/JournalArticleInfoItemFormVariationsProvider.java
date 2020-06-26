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

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemFormVariationsProvider.class)
public class JournalArticleInfoItemFormVariationsProvider
	implements InfoItemFormVariationsProvider<JournalArticle> {

	@Override
	public Collection<InfoItemFormVariation> getInfoItemFormVariations(
		long[] scopeGroupIds) {

		List<InfoItemFormVariation> infoItemFormVariations = new ArrayList<>();

		List<DDMStructure> ddmStructures =
			DDMStructureManagerUtil.getStructures(
				scopeGroupIds,
				PortalUtil.getClassNameId(JournalArticle.class.getName()));

		for (DDMStructure ddmStructure : ddmStructures) {
			infoItemFormVariations.add(
				new InfoItemFormVariation(
					String.valueOf(ddmStructure.getStructureId()),
					new DDMStructureNameInfoLocalizedValue(ddmStructure)));
		}

		return infoItemFormVariations;
	}

	private class DDMStructureNameInfoLocalizedValue
		implements InfoLocalizedValue<String> {

		public DDMStructureNameInfoLocalizedValue(DDMStructure ddmStructure) {
			_ddmStructure = ddmStructure;
		}

		@Override
		public Set<Locale> getAvailableLocales() {
			String[] availableLanguageIds =
				_ddmStructure.getAvailableLanguageIds();

			Set<Locale> availableLocales = new HashSet<>(
				availableLanguageIds.length);

			for (String availableLanguageId : availableLanguageIds) {
				availableLocales.add(
					LanguageUtil.getLocale(availableLanguageId));
			}

			return availableLocales;
		}

		@Override
		public Locale getDefaultLocale() {
			return LanguageUtil.getLocale(_ddmStructure.getDefaultLanguageId());
		}

		@Override
		public String getValue() {
			return getValue(getDefaultLocale());
		}

		@Override
		public String getValue(Locale locale) {
			return _ddmStructure.getName(locale);
		}

		private final DDMStructure _ddmStructure;

	}

}