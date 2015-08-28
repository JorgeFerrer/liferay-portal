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

package com.liferay.amazon.rankings.web.configuration;

import aQute.bnd.annotation.xml.XMLAttribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Raymond Augé
 */
public interface Hint {

	public static final String NS =
		"http://www.liferay.com/xsd/meta-type-hints_7_0_0";
	public static final String PREFIX = "mh";
	public static final String EMBED_IN = "*";

	@XMLAttribute(
		embedIn = Hint.EMBED_IN,
		namespace = Hint.NS,
		prefix = Hint.PREFIX)
	@Retention(RetentionPolicy.CLASS)
	@Target({ElementType.TYPE})
	public @interface Category {
		String category();
	}

	@XMLAttribute(
		embedIn = Hint.EMBED_IN,
		namespace = Hint.NS,
		prefix = Hint.PREFIX)
	@Retention(RetentionPolicy.CLASS)
	@Target(ElementType.METHOD)
	public @interface NumericInput {
		boolean disabled() default false;
		boolean hidden() default false;
		boolean readonly() default false;
		double step() default 1;
		NumericType type() default NumericType.NUMBER;
	}

	enum NumericType {
		NUMBER, RANGE
	}

	@XMLAttribute(
		embedIn = Hint.EMBED_IN,
		namespace = Hint.NS,
		prefix = Hint.PREFIX)
	@Retention(RetentionPolicy.CLASS)
	@Target(ElementType.METHOD)
	public @interface OptionInput {
		OptionType type() default OptionType.SELECT;
	}

	enum OptionType {
		CHECKBOX, RADIO, SELECT
	}

	@XMLAttribute(
		embedIn = Hint.EMBED_IN,
		namespace = Hint.NS,
		prefix = Hint.PREFIX)
	@Retention(RetentionPolicy.CLASS)
	@Target(ElementType.METHOD)
	public @interface StringInput {
		boolean disabled() default false;
		boolean hidden() default false;
		boolean localized() default false;
		int maxlength() default 4000;
		String pattern() default "";
		boolean readonly() default false;
		int size() default 256;
		StringType type() default StringType.TEXT;
	}

	enum StringType {
		COLOR, DATE, DATETIME, EDITOR, EMAIL, TEXT, TEXTAREA, TIME
	}

}