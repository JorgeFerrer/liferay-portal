<%--
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
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.util.RSSUtil" %>

<%
ResourceURL baseResourceURL = (ResourceURL)request.getAttribute("liferay-ui:rss:baseResourceURL");
String baseURL = (String)request.getAttribute("liferay-ui:rss:baseURL");
int delta = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:rss:delta"));
String displayStyle = (String)request.getAttribute("liferay-ui:rss:displayStyle");
String[] format = (String[])request.getAttribute("liferay-ui:rss:format");
String name = (String)request.getAttribute("liferay-ui:rss:name");

if (baseResourceURL != null) {
	baseResourceURL.setCacheability(ResourceURL.FULL);

	if ((delta != SearchContainer.DEFAULT_DELTA) || !displayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT) || Validator.isNotNull(name)) {
		if (delta != SearchContainer.DEFAULT_DELTA) {
			baseResourceURL.setParameter("max", String.valueOf(delta));
		}

		if (!displayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
			baseResourceURL.setParameter("displayStyle", displayStyle);
		}

		if (Validator.isNotNull(name)) {
			baseResourceURL.setParameter("title", name);
		}
	}
}
else if (Validator.isNotNull(baseURL)) {
	if ((delta != SearchContainer.DEFAULT_DELTA) || !displayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
		StringBundler url = new StringBundler(baseURL);

		if (delta != SearchContainer.DEFAULT_DELTA) {
			url.append("&max=");
			url.append(delta);
		}

		if (!displayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
			url.append("&displayStyle=");
			url.append(displayStyle);
		}

		if (Validator.isNotNull(name)) {
			url.append("&title=");
			url.append(name);
		}

		baseURL = url.toString();
	}
}
%>

<liferay-ui:icon-list>

	<%
	for (String feedFormat : RSSUtil.FEED_FORMATS) {
		if (ArrayUtil.contains(format, feedFormat)) {
		%>

			<liferay-ui:icon
				image="rss"
				label="<%= true %>"
				message="<%= feedFormat %>"
				target="_blank"
				url="<%= _getRssURL(baseResourceURL, baseURL, feedFormat) %>"
			/>

		<%
		}
	}
	%>

</liferay-ui:icon-list>

<%!
private static String _getRssURL(ResourceURL baseResourceURL, String baseURL, String feedFormat) {
	String rssFormatType = RSSUtil.getFormatType(feedFormat);
	double rssFormatVersion = RSSUtil.getFormatVersion(feedFormat);

	if (baseResourceURL != null) {
		ResourceURL resourceURL = baseResourceURL;

		if (!rssFormatType.equals(RSSUtil.TYPE_DEFAULT) || (rssFormatVersion != RSSUtil.VERSION_DEFAULT)) {
			if (!rssFormatType.equals(RSSUtil.TYPE_DEFAULT)) {
				resourceURL.setParameter("type", rssFormatType);
			}

			if (rssFormatVersion != RSSUtil.VERSION_DEFAULT) {
				resourceURL.setParameter("version", String.valueOf(rssFormatVersion));
			}
		}

		return resourceURL.toString();
	}
	else if (baseURL != null) {
		StringBundler url = new StringBundler(baseURL);

		if (!rssFormatType.equals(RSSUtil.TYPE_DEFAULT) || (rssFormatVersion != RSSUtil.VERSION_DEFAULT)) {
			if (!rssFormatType.equals(RSSUtil.TYPE_DEFAULT)) {
				url.append("&type=");
				url.append(rssFormatType);
			}

			if (rssFormatVersion != RSSUtil.VERSION_DEFAULT) {
				url.append("&version=");
				url.append(String.valueOf(rssFormatVersion));
			}
		}

		return url.toString();
	}

	return StringPool.BLANK;
}
%>