package com.liferay.info.descriptor;

import java.util.Locale;

public interface InfoItemDescriptorProvider<T> {

	public InfoItemDescriptor getInfoItemDescriptor();

	public default InfoItemDescriptor getInfoItemDescriptor(long classTypeId) {
		return getInfoItemDescriptor();
	}

	public InfoItemDescriptor getInfoItemDescriptorWithValues(T t);

	public InfoItemDescriptor getInfoItemDescriptorWithValues(
		T t, long classTypeId);

}
