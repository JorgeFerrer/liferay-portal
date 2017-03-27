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

package com.liferay.api.platform.core.representation;

import com.liferay.api.platform.core.builder.DataBuilder;
import com.liferay.api.platform.core.builder.SingleRelationBuilder;

/**
 * A resource's representer. It has the task of exposing a resource to other
 * apis.
 *
 * @author Carlos Sierra
 * @author Alejandro Hernández
 */
public interface ResourceRepresenter<T> {

	/**
	 * Declare resource's attributes, representing some of the resource's data.
	 * Use the provided {@link DataBuilder} for that purpose.
	 *
	 * @param dataBuilder data builder used to declare resource's attributes.
	 */
	public void data(DataBuilder<T> dataBuilder);

	/**
	 * Retrieve an object's representation URI.
	 *
	 * @return object's representation URI.
	 */
	public String getResourceURI(T t);

	/**
	 * Retrieve this resource type. Matching one of schema.org
	 *
	 * @see <a href="http://schema.org">schema.org</a>
	 * @return resource's type.
	 */
	public String getType();

	/**
	 * Declare resource's relations with other resources.
	 * Use the provided {@link SingleRelationBuilder} for that purpose.
	 *
	 * @param singleRelationBuilder relation builder used to declare resource's
	 *                              relations.
	 */
	public void relations(SingleRelationBuilder<T> singleRelationBuilder);

}