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

package com.liferay.contacts.messaging;

import com.liferay.contacts.service.ClpSerializer;
import com.liferay.contacts.util.ContactsExtensionsUtil;
import com.liferay.portal.kernel.messaging.HotDeployMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.ClassResolverUtil;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortletClassInvoker;

/**
 * @author Ryan Park
 */
public class ContactsHotDeployMessageListener extends HotDeployMessageListener {

	public ContactsHotDeployMessageListener(String... servletContextNames) {
		super(servletContextNames);
	}

	@Override
	protected void onDeploy(Message message) throws Exception {
		if (_registerMethodKey == null) {
			try {
				_registerMethodKey = new MethodKey(
					ClassResolverUtil.resolveByPortletClassLoader(
						"com.liferay.chat.util.ChatExtensionsUtil",
						"chat-portlet"),
					"register", String.class, String.class);
			}
			catch (RuntimeException re) {
				return;
			}
		}

		PortletClassInvoker.invoke(
			"1_WAR_chatportlet", _registerMethodKey,
			ClpSerializer.getServletContextName(), "/chat/view.jsp");
	}

	@Override
	protected void onUndeploy(Message message) throws Exception {
		ContactsExtensionsUtil.unregister(
			message.getString("servletContextName"));
	}

	private MethodKey _registerMethodKey;

}