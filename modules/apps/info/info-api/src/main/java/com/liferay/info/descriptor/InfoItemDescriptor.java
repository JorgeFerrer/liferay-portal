package com.liferay.info.descriptor;

import com.liferay.info.display.contributor.InfoDisplayField;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

public interface InfoItemDescriptor<T> {

	public Set<InfoDisplayField> getInfoDisplayFields();

	public Map<String, Object> getInfoDisplayFieldsValues(
		T t, Locale locale);

	public default Object getInfoDisplayFieldValue(
		T t, String fieldName, Locale locale) {

		Map<String, Object> infoDisplayFieldsValues =
			getInfoDisplayFieldsValues(t, locale);

		return infoDisplayFieldsValues.get(fieldName);
	}

}
