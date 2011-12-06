/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutWrapper;
import com.liferay.portal.model.VirtualLayoutConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 */
public class VirtualLayout extends LayoutWrapper {

	public VirtualLayout(Layout layout, Group virtualGroup) {
		super(layout);

		_virtualGroup = virtualGroup;
	}

	@Override
	public Object clone() {
		return new VirtualLayout((Layout)super.clone(), _virtualGroup);
	}

	@Override
	public String getFriendlyURL() {
		StringBundler sb = new StringBundler(4);

		sb.append(VirtualLayoutConstants.CANONICAL_URL_SEPARATOR);

		try {
			Group group = super.getGroup();

			sb.append(group.getFriendlyURL());
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		sb.append(super.getFriendlyURL());

		return sb.toString();
	}

	@Override
	public Group getGroup() {
		return getHostGroup();
	}

	@Override
	public long getGroupId() {
		return getVirtualGroupId();
	}

	public Group getHostGroup() {
		return _virtualGroup;
	}

	@Override
	public LayoutSet getLayoutSet() {
		if (isPrivateLayout()) {
			return _virtualGroup.getPrivateLayoutSet();
		}

		return _virtualGroup.getPublicLayoutSet();
	}

	@Override
	public String getRegularURL(HttpServletRequest request)
		throws PortalException, SystemException {

		String layoutURL = super.getRegularURL(request);

		return injectVirtualGroupURL(layoutURL);
	}

	@Override
	public String getResetLayoutURL(HttpServletRequest request)
		throws PortalException, SystemException {

		String layoutURL = super.getResetLayoutURL(request);

		return injectVirtualGroupURL(layoutURL);
	}

	@Override
	public String getResetMaxStateURL(HttpServletRequest request)
		throws PortalException, SystemException {

		String layoutURL = super.getResetMaxStateURL(request);

		return injectVirtualGroupURL(layoutURL);
	}

	public long getSourceGroupId() {
		return super.getGroupId();
	}

	public long getVirtualGroupId() {
		return _virtualGroup.getGroupId();
	}

	protected String injectVirtualGroupURL(String layoutURL) {
		try {
			Group group = super.getGroup();

			int pos = layoutURL.indexOf(group.getFriendlyURL());

			return layoutURL.substring(0, pos).concat(
				_virtualGroup.getFriendlyURL()).concat(getFriendlyURL());
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VirtualLayout.class);

	private Group _virtualGroup;

}