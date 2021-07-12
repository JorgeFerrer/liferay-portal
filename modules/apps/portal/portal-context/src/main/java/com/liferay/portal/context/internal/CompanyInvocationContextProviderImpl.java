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

package com.liferay.portal.context.internal;

import com.liferay.portal.kernel.context.CompanyInvocationContextProvider;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = CompanyInvocationContextProvider.class)
public class CompanyInvocationContextProviderImpl
	implements CompanyInvocationContextProvider {

	@Override
	public Company getCurrent() {
		if (isPresent()) {
			return _userLocalService.fetchCompany(getCompanyId());
		}

		return null;
	}

	@Override
	public boolean isPresent() {
		Long companyId = getCompanyId();

		if ((companyId == null) || (companyId == 0)) {
			return false;
		}

		return true;
	}

	protected Long getCompanyId() {
		return CompanyThreadLocal.getCompanyId();
	}

	@Reference
	private CompanyLocalService _userLocalService;

}