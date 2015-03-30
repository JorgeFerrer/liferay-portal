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

package com.liferay.portal.cluster.internal.jgroups;

import com.liferay.portal.cluster.ClusterChannel;
import com.liferay.portal.cluster.ClusterChannelFactory;
import com.liferay.portal.cluster.ClusterReceiver;
import com.liferay.portal.cluster.internal.constants.ClusterPropsKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SocketUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.InetAddress;
import java.net.NetworkInterface;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = ClusterChannelFactory.class)
public class JGroupsClusterChannelFactory implements ClusterChannelFactory {

	@Override
	public ClusterChannel createClusterChannel(
		String channelProperties, String clusterName,
		ClusterReceiver clusterReceiver) {

		return new JGroupsClusterChannel(
			channelProperties, clusterName, clusterReceiver);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		String channelSystemProperties = GetterUtil.getString(
			properties.get(ClusterPropsKeys.CHANNEL_SYSTEM_PROPERTIES));

		if (Validator.isNull(channelSystemProperties)) {
			channelSystemProperties = GetterUtil.getString(
				_props.get(PropsKeys.CLUSTER_LINK_CHANNEL_SYSTEM_PROPERTIES));
		}

		initSystemProperties(channelSystemProperties);

		String autodetectAddress = GetterUtil.getString(
			properties.get(ClusterPropsKeys.AUTODETECT_ADDRESS));

		if (Validator.isNull(autodetectAddress)) {
			autodetectAddress = GetterUtil.getString(
				_props.get(PropsKeys.CLUSTER_LINK_AUTODETECT_ADDRESS));
		}

		initBindAddress(autodetectAddress);
	}

	protected void initBindAddress(String autodetectAddress) {
		if (Validator.isNull(autodetectAddress)) {
			return;
		}

		String host = autodetectAddress;
		int port = 80;

		int index = autodetectAddress.indexOf(CharPool.COLON);

		if (index != -1) {
			host = autodetectAddress.substring(0, index);
			port = GetterUtil.getInteger(
				autodetectAddress.substring(index + 1), port);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Autodetecting JGroups outgoing IP address and interface for " +
					host + ":" + port);
		}

		try {
			SocketUtil.BindInfo bindInfo = SocketUtil.getBindInfo(host, port);

			InetAddress inetAddress = bindInfo.getInetAddress();

			NetworkInterface networkInterface = bindInfo.getNetworkInterface();

			System.setProperty(
				"jgroups.bind_addr", inetAddress.getHostAddress());
			System.setProperty(
				"jgroups.bind_interface", networkInterface.getName());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Setting JGroups outgoing IP address to " +
						inetAddress.getHostAddress() + " and interface to " +
							networkInterface.getName());
			}
		}
		catch (IOException e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to detect bind address for JGroups", e);
			}
		}
	}

	protected void initSystemProperties(String channelSystemProperties) {
		String[] channelSystemPropertiesArray = StringUtil.split(
			channelSystemProperties);

		for (String channelSystemProperty : channelSystemPropertiesArray) {
			int index = channelSystemProperty.indexOf(CharPool.COLON);

			if (index == -1) {
				continue;
			}

			String key = channelSystemProperty.substring(0, index);
			String value = channelSystemProperty.substring(index + 1);

			System.setProperty(key, value);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Setting system property {key=" + key + ", value=" + value +
						"}");
			}
		}
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JGroupsClusterChannelFactory.class);

	private Props _props;

}