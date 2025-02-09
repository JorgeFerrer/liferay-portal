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

package com.liferay.external.data.source.test;

import com.liferay.external.data.source.test.model.TestEntity;
import com.liferay.external.data.source.test.service.TestEntityLocalServiceUtil;
import com.liferay.external.data.source.test.service.persistence.TestEntityPersistence;
import com.liferay.external.data.source.test.service.persistence.TestEntityUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class DataSourceTest {

	@Before
	public void setUp() {
		_persistence = TestEntityUtil.getPersistence();

		_dataSource = _persistence.getDataSource();
	}

	@Test
	public void testDataSource() throws Exception {
		Assert.assertNotSame(InfrastructureUtil.getDataSource(), _dataSource);
	}

	@Test
	public void testUpdate() throws Exception {
		try (Connection connection = _dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from TestEntity")) {

			preparedStatement.executeUpdate();
		}

		long pk = RandomTestUtil.nextLong();

		TestEntity testEntity = _persistence.create(pk);

		testEntity.setData(DataSourceTest.class.getName());

		TestEntityLocalServiceUtil.addTestEntity(testEntity);

		DataSource portalDataSource = InfrastructureUtil.getDataSource();

		try (Connection connection = portalDataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from TestEntity");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertFalse(resultSet.next());
		}

		try (Connection connection = _dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from TestEntity");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(pk, resultSet.getLong("id_"));
			Assert.assertEquals(
				DataSourceTest.class.getName(), resultSet.getString("data_"));
		}
	}

	private DataSource _dataSource;
	private TestEntityPersistence _persistence;

}