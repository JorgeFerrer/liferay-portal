/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.domain;

import aQute.bnd.annotation.ProviderType;
import com.liferay.portal.model.UserGroupRole;

import java.util.List;

@ProviderType
public final class Memberships {

	public static class Builder {
		public Builder organizations(long[] organizationIds) {
			_organizationIds = organizationIds;

			return this;
		}

		public Builder roles(long[] roleIds) {
			_roleIds = roleIds;

			return this;
		}

		public Builder sites(long[] groupIds) {
			_groupIds = groupIds;

			return this;
		}

		public Builder userGroups(long[] userGroupIds) {
			_userGroupIds = userGroupIds;

			return this;
		}

		public Memberships build() {
			return new Memberships(this);
		}

		private long[] _groupIds;
		private long[] _organizationIds;
		private long[] _roleIds;
		private long[] _userGroupIds;
		private List<UserGroupRole> _userGroupRoles = null;
	}

	private Memberships(Builder builder) {
		_groupIds = builder._groupIds;
		_organizationIds = builder._organizationIds;
		_roleIds = builder._roleIds;
		_userGroupRoles = builder._userGroupRoles;
		_userGroupIds = builder._userGroupIds;
	}

	public long[] getGroupIds() {
		return _groupIds;
	}

	public long[] getOrganizationIds() {
		return _organizationIds;
	}

	public long[] getRoleIds() {
		return _roleIds;
	}

	public List<UserGroupRole> getUserGroupRoles() {
		return _userGroupRoles;
	}

	public long[] getUserGroupIds() {
		return _userGroupIds;
	}

	public void setRoleIds(long[] roleIds) {
		_roleIds = roleIds;
	}

	private final long[] _groupIds;
	private final long[] _organizationIds;
	private long[] _roleIds;
	private final List<UserGroupRole> _userGroupRoles;
	private final long[] _userGroupIds;
}
