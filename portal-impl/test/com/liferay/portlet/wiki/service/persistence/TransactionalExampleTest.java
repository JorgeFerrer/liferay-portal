/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.util.AbstractIntegrationTransactionalTestCase;
import com.liferay.portlet.wiki.model.WikiNode;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Miguel Pastor
 */
public class TransactionalExampleTest
	extends AbstractIntegrationTransactionalTestCase {

	@Test
	public void create() throws Exception {

		long pk = nextLong();

		WikiNode wikiNode = _persistence.create(pk);

		wikiNode.setUuid(randomString());

		wikiNode.setGroupId(nextLong());

		wikiNode.setCompanyId(nextLong());

		wikiNode.setUserId(nextLong());

		wikiNode.setUserName(randomString());

		wikiNode.setCreateDate(nextDate());

		wikiNode.setModifiedDate(nextDate());

		wikiNode.setName(randomString());

		wikiNode.setDescription(randomString());

		wikiNode.setLastPostDate(nextDate());

		_persistence.update(wikiNode, false);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		_persistence = (WikiNodePersistence)PortalBeanLocatorUtil.locate(WikiNodePersistence.class.getName());
	}

	private WikiNodePersistence _persistence;

}