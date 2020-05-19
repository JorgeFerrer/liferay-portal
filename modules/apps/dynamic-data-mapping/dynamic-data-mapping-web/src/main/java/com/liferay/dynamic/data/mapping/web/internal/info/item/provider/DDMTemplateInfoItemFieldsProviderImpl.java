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

package com.liferay.dynamic.data.mapping.web.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.info.item.provider.DDMTemplateInfoItemFieldsProvider;
import com.liferay.dynamic.data.mapping.kernel.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.info.fields.InfoField;
import com.liferay.info.fields.InfoFieldSetEntry;
import com.liferay.info.fields.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(service = DDMTemplateInfoItemFieldsProvider.class)
public class DDMTemplateInfoItemFieldsProviderImpl
	implements DDMTemplateInfoItemFieldsProvider {

	@Override
	public List<InfoFieldSetEntry> getInfoItemFieldSetEntries(
			long ddmStructureId)
		throws NoSuchStructureException {

		List<InfoFieldSetEntry> infoFieldSetEntries = new ArrayList<>();

		try {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.getDDMStructure(ddmStructureId);

			List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

			Stream<DDMTemplate> stream = ddmTemplates.stream();

			Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

			infoFieldSetEntries.addAll(
				stream.map(
					ddmTemplate -> new InfoField(
						InfoLocalizedValue.localize(
							getClass(),
							ddmTemplate.getName(locale) + StringPool.SPACE +
								StringPool.STAR),
						_getTemplateFieldName(ddmTemplate),
						TextInfoFieldType.INSTANCE)
				).collect(
					Collectors.toList()
				));
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw noSuchStructureException;
		}
		catch (PortalException portalException) {
			throw new RuntimeException("Unexpected exception", portalException);
		}

		return infoFieldSetEntries;
	}

	private String _getTemplateFieldName(DDMTemplate ddmTemplate) {
		String templateKey = ddmTemplate.getTemplateKey();

		return PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
			templateKey.replaceAll("\\W", "_");
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}