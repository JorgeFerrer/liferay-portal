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

import com.liferay.asset.info.display.item.provider.AssetEntryInfoItemFieldsProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMStructureInfoItemFieldsProvider;
import com.liferay.dynamic.data.mapping.kernel.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldsProvider;
import com.liferay.info.fields.InfoField;
import com.liferay.info.fields.InfoFieldSetEntry;
import com.liferay.info.fields.InfoForm;
import com.liferay.info.fields.InfoFormValues;
import com.liferay.info.fields.type.ImageInfoFieldType;
import com.liferay.info.fields.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.NoSuchClassTypeException;
import com.liferay.info.item.NoSuchInfoItemException;
import com.liferay.info.item.fields.ClassNameInfoItemFieldsProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;

import java.util.ArrayList;
import java.util.Collection;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class JournalArticleInfoItemFormProvider
	implements InfoItemFormProvider<JournalArticle> {

	@Override
	public InfoForm getInfoForm() {
		InfoForm infoForm = new InfoForm(JournalArticle.class.getName());

		infoForm.addAll(_getJournalArticleFields());

		infoForm.addAll(
			_classNameInfoItemFieldsProvider.getFields(
				JournalArticle.class.getName()));

		infoForm.addAll(
			_assetEntryInfoItemFieldsProvider.getFields(
				AssetEntry.class.getName()));

		infoForm.addAll(
			_expandoInfoItemFieldsProvider.getFields(
				JournalArticle.class.getName()));

		return infoForm;
	}

	@Override
	public InfoForm getInfoForm(JournalArticle article) {
		DDMStructure ddmStructure = article.getDDMStructure();

		long ddmStructureId = ddmStructure.getStructureId();

		try {
			return getInfoForm(ddmStructureId);
		}
		catch (NoSuchClassTypeException noSuchClassTypeException) {
			throw new RuntimeException(
				"Cannot find structure " + ddmStructureId,
				noSuchClassTypeException);
		}
	}

	@Override
	public InfoForm getInfoForm(long ddmStructureId)
		throws NoSuchClassTypeException {

		InfoForm infoForm = getInfoForm();

		try {
			infoForm.addAll(
				_ddmStructureInfoItemFieldsProvider.getInfoItemFields(
					ddmStructureId));
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw new NoSuchClassTypeException(
				ddmStructureId, noSuchStructureException);
		}

		return infoForm;
	}

	@Override
	public InfoFormValues getInfoFormValues(JournalArticle journalArticle) {
		InfoFormValues infoFormValues = new InfoFormValues();

		infoFormValues.setInfoItemClassPKReference(
			new InfoItemClassPKReference(
				JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey()));

		try {
			infoFormValues.addAll(
				_assetEntryInfoItemFieldsProvider.getFieldValues(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey()));
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			throw new RuntimeException(
				"Unexpected exception which should never occur",
				noSuchInfoItemException);
		}

		infoFormValues.addAll(
			_expandoInfoItemFieldsProvider.getFieldValues(
				JournalArticle.class.getName(), journalArticle));
		infoFormValues.addAll(
			_classNameInfoItemFieldsProvider.getFieldValues(
				JournalArticle.class.getName(), journalArticle));

		return infoFormValues;
	}

	private Collection<InfoFieldSetEntry> _getJournalArticleFields() {
		Collection<InfoFieldSetEntry> journalArticleFields = new ArrayList<>();

		journalArticleFields.add(_titleInfoField);

		journalArticleFields.add(_descriptionInfoField);

		journalArticleFields.add(_summaryInfoField);

		journalArticleFields.add(_smallImageInfoField);

		journalArticleFields.add(_authorNameInfoField);

		journalArticleFields.add(_authorProfileImageInfoField);

		journalArticleFields.add(_lastEditorNameInfoField);

		journalArticleFields.add(_lastEditorProfileImageInfoField);

		journalArticleFields.add(_publishDateInfoField);

		return journalArticleFields;
	}

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

	@Reference
	private DDMStructureInfoItemFieldsProvider
		_ddmStructureInfoItemFieldsProvider;

	private final InfoField _descriptionInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "description"), "description",
		TextInfoFieldType.INSTANCE);

	@Reference
	private ExpandoInfoItemFieldsProvider _expandoInfoItemFieldsProvider;

	private final InfoField _lastEditorNameInfoField = new InfoField(
		InfoLocalizedValue.localize(
			"com.liferay.journal.lang", "last-editor-name"),
		"lastEditorName", TextInfoFieldType.INSTANCE);
	private final InfoField _lastEditorProfileImageInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "last-editor-profile-image"),
		"lastEditorProfileImage", ImageInfoFieldType.INSTANCE);
	private final InfoField _publishDateInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "publish-date"), "publishDate",
		TextInfoFieldType.INSTANCE);
	private final InfoField _smallImageInfoField = new InfoField(
		InfoLocalizedValue.localize("com.liferay.journal.lang", "small-image"),
		"smallImage", ImageInfoFieldType.INSTANCE);
	private final InfoField _summaryInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "summary"), "summary",
		TextInfoFieldType.INSTANCE);
	private final InfoField _titleInfoField = new InfoField(
		InfoLocalizedValue.localize(getClass(), "title"), "title",
		TextInfoFieldType.INSTANCE);

}