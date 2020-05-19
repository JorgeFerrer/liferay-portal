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

package com.liferay.blogs.web.internal.info.item.fields;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldsProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldsProvider;
import com.liferay.info.fields.InfoField;
import com.liferay.info.fields.InfoFieldSetEntry;
import com.liferay.info.fields.InfoFieldValue;
import com.liferay.info.fields.InfoForm;
import com.liferay.info.fields.InfoFormValues;
import com.liferay.info.fields.type.ImageInfoFieldType;
import com.liferay.info.fields.type.TextInfoFieldType;
import com.liferay.info.fields.type.URLInfoFieldType;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.NoSuchInfoItemException;
import com.liferay.info.item.fields.ClassNameInfoItemFieldsProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.text.Format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
@Component(
	immediate = true, property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class BlogsEntryInfoItemFormProvider
	implements InfoItemFormProvider<BlogsEntry> {

	@Override
	public InfoForm getInfoForm() {
		InfoForm infoForm = new InfoForm(BlogsEntry.class.getName());

		infoForm.addAll(_getBlogsEntryInfoFieldSetEntries());

		infoForm.add(
			_classNameInfoItemFieldsProvider.getInfoFieldSet(
				BlogsEntry.class.getName()));

		infoForm.add(
			_assetEntryInfoItemFieldsProvider.getInfoFieldSet(
				AssetEntry.class.getName()));

		infoForm.add(
			_expandoInfoItemFieldsProvider.getInfoFieldSet(
				BlogsEntry.class.getName()));

		return infoForm;
	}

	@Override
	public InfoFormValues getInfoFormValues(BlogsEntry blogsEntry) {
		InfoFormValues infoFormValues = new InfoFormValues();

		infoFormValues.addAll(_getBlogsEntryInfoFieldValues(blogsEntry));

		infoFormValues.setInfoItemClassPKReference(
			new InfoItemClassPKReference(
				BlogsEntry.class.getName(), blogsEntry.getEntryId()));

		try {
			infoFormValues.addAll(
				_assetEntryInfoItemFieldsProvider.getInfoFieldValues(
					BlogsEntry.class.getName(), blogsEntry.getEntryId()));
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			throw new RuntimeException(
				"Unexpected exception which should never occur",
				noSuchInfoItemException);
		}

		infoFormValues.addAll(
			_expandoInfoItemFieldsProvider.getInfoFieldValues(
				BlogsEntry.class.getName(), blogsEntry));
		infoFormValues.addAll(
			_classNameInfoItemFieldsProvider.getInfoFieldValues(
				BlogsEntry.class.getName(), blogsEntry));

		return infoFormValues;
	}

	private Collection<InfoFieldSetEntry> _getBlogsEntryInfoFieldSetEntries() {
		Collection<InfoFieldSetEntry> blogsEntryFields = new ArrayList<>();

		blogsEntryFields.add(_titleInfoField);

		blogsEntryFields.add(_subtitleInfoField);

		blogsEntryFields.add(_descriptionInfoField);

		blogsEntryFields.add(_smallImageInfoField);

		blogsEntryFields.add(_coverImageInfoField);

		blogsEntryFields.add(_coverImageCaptionInfoField);

		blogsEntryFields.add(_authorNameInfoField);

		blogsEntryFields.add(_authorProfileImageInfoField);

		blogsEntryFields.add(_publishDateInfoField);

		blogsEntryFields.add(_displayPageUrlInfoField);

		blogsEntryFields.add(_contentInfoField);

		return blogsEntryFields;
	}

	private List<InfoFieldValue<Object>> _getBlogsEntryInfoFieldValues(
		BlogsEntry blogsEntry) {

		List<InfoFieldValue<Object>> blogsEntryFieldValues = new ArrayList<>();

		ThemeDisplay themeDisplay = _getThemeDisplay();

		try {
			blogsEntryFieldValues.add(
				new InfoFieldValue<>(_titleInfoField, blogsEntry.getTitle()));

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					_subtitleInfoField, blogsEntry.getSubtitle()));

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					_descriptionInfoField, blogsEntry.getDescription()));

			if (themeDisplay != null) {
				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						_smallImageInfoField,
						_getImageJSONObject(
							blogsEntry.getSmallImageAlt(),
							blogsEntry.getSmallImageURL(themeDisplay))));
			}

			if (themeDisplay != null) {
				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						_coverImageInfoField,
						_getImageJSONObject(
							blogsEntry.getCoverImageAlt(),
							blogsEntry.getCoverImageURL(themeDisplay))));
			}

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					_coverImageCaptionInfoField,
					blogsEntry.getCoverImageCaption()));

			User user = _userLocalService.fetchUser(blogsEntry.getUserId());

			if (user != null) {
				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						_authorNameInfoField, user.getFullName()));

				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						_authorProfileImageInfoField,
						_getImageJSONObject(
							user.getFullName(),
							user.getPortraitURL(themeDisplay))));
			}

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					_publishDateInfoField,
					_getDateValue(blogsEntry.getDisplayDate())));

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					_displayPageUrlInfoField, _getDisplayPageURL(blogsEntry)));

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					_contentInfoField, blogsEntry.getContent()));

			return blogsEntryFieldValues;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private String _getDateValue(Date date) {
		if (date == null) {
			return StringPool.BLANK;
		}

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			locale);

		return dateFormatDateTime.format(date);
	}

	private String _getDisplayPageURL(BlogsEntry blogsEntry)
		throws PortalException {

		return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
			BlogsEntry.class.getName(), blogsEntry.getEntryId(),
			_getThemeDisplay());
	}

	private JSONObject _getImageJSONObject(String alt, String url) {
		return JSONUtil.put(
			"alt", alt
		).put(
			"url", url
		);
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private AssetEntryInfoItemFieldsProvider _assetEntryInfoItemFieldsProvider;

	private final InfoField _authorNameInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "author-name"), "authorName",
		TextInfoFieldType.INSTANCE);
	private final InfoField _authorProfileImageInfoField = new InfoField(
		InfoLocalizedValue.localize(
			"com.liferay.journal.lang", "author-profile-image"),
		"authorProfileImage", ImageInfoFieldType.INSTANCE);

	@Reference
	private ClassNameInfoItemFieldsProvider _classNameInfoItemFieldsProvider;

	private final InfoField _contentInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "content"), "content",
		TextInfoFieldType.INSTANCE);
	private final InfoField _coverImageCaptionInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "cover-image-caption"),
		"coverImageCaption", TextInfoFieldType.INSTANCE);
	private final InfoField _coverImageInfoField = new InfoField(
		InfoLocalizedValue.localize("com.liferay.journal.lang", "cover-image"),
		"coverImage", ImageInfoFieldType.INSTANCE);
	private final InfoField _descriptionInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "description"), "description",
		TextInfoFieldType.INSTANCE);
	private final InfoField _displayPageUrlInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "display-page-url"),
		"displayPageURL", URLInfoFieldType.INSTANCE);

	@Reference
	private ExpandoInfoItemFieldsProvider _expandoInfoItemFieldsProvider;

	private final InfoField _publishDateInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "publish-date"), "publishDate",
		TextInfoFieldType.INSTANCE);
	private final InfoField _smallImageInfoField = new InfoField(
		InfoLocalizedValue.localize("com.liferay.journal.lang", "small-image"),
		"smallImage", ImageInfoFieldType.INSTANCE);
	private final InfoField _subtitleInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "subtitle"), "subtitle",
		TextInfoFieldType.INSTANCE);
	private final InfoField _titleInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "title"), "title",
		TextInfoFieldType.INSTANCE);

	@Reference
	private UserLocalService _userLocalService;

}