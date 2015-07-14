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

package com.liferay.blogs.web.display.context;

import com.liferay.blogs.web.configuration.BlogsPortletInstanceConfiguration;
import com.liferay.blogs.web.context.util.BlogsWebRequestHelper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.theme.ThemeDisplay;

/**
 * @author Sergio González
 */
public class BlogsPortletInstanceSettingsHelper {

	public BlogsPortletInstanceSettingsHelper(
		BlogsWebRequestHelper blogsWebRequestHelper) {

		_blogsWebRequestHelper = blogsWebRequestHelper;

		_blogsPortletInstanceConfiguration =
			_blogsWebRequestHelper.getBlogsPortletInstanceConfiguration();
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = _blogsPortletInstanceConfiguration.displayStyle();

		return _displayStyle;
	}

	public long getDisplayStyleGroupId(long defaultDisplayStyleGroupId) {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_blogsPortletInstanceConfiguration.displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				_blogsWebRequestHelper.getThemeDisplay();

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public int getPageDelta() {
		if (_pageDelta != null) {
			return _pageDelta;
		}

		_pageDelta = GetterUtil.getInteger(
			_blogsPortletInstanceConfiguration.pageDelta());

		return _pageDelta;
	}

	public String getSocialBookmarksDisplayPosition() {
		if (_socialBookmarksDisplayPosition != null) {
			return _socialBookmarksDisplayPosition;
		}

		_socialBookmarksDisplayPosition =
			_blogsPortletInstanceConfiguration.socialBookmarksDisplayPosition();

		return _socialBookmarksDisplayPosition;
	}

	public String getSocialBookmarksDisplayStyle() {
		if (_socialBookmarksDisplayStyle != null) {
			return _socialBookmarksDisplayStyle;
		}

		_socialBookmarksDisplayStyle =
			_blogsPortletInstanceConfiguration.socialBookmarksDisplayStyle();

		return _socialBookmarksDisplayStyle;
	}

	public String getSocialBookmarksTypes() {
		if (_socialBookmarksTypes != null) {
			return _socialBookmarksTypes;
		}

		_socialBookmarksTypes =
			_blogsPortletInstanceConfiguration.socialBookmarksDisplayStyle();

		return _socialBookmarksTypes;
	}

	public Boolean isEnableCommentRatings() {
		if (_enableCommentRatings != null) {
			return _enableCommentRatings;
		}

		_enableCommentRatings =
			_blogsPortletInstanceConfiguration.enableCommentRatings();

		return _enableCommentRatings;
	}

	public Boolean isEnableComments() {
		if (_enableComments != null) {
			return _enableComments;
		}

		_enableComments = _blogsPortletInstanceConfiguration.enableComments();

		return _enableComments;
	}

	public boolean isEnableFlags() {
		if (_enableFlags != null) {
			return _enableFlags;
		}

		_enableFlags = _blogsPortletInstanceConfiguration.enableFlags();

		return _enableFlags;
	}

	public boolean isEnableRatings() {
		if (_enableRatings != null) {
			return _enableRatings;
		}

		_enableRatings = _blogsPortletInstanceConfiguration.enableRatings();

		return _enableRatings;
	}

	public boolean isEnableRelatedAssets() {
		if (_enableRelatedAssets != null) {
			return _enableRelatedAssets;
		}

		_enableRelatedAssets =
			_blogsPortletInstanceConfiguration.enableRelatedAssets();

		return _enableRelatedAssets;
	}

	public boolean isEnableSocialBookmarks() {
		if (_enableSocialBookmarks != null) {
			return _enableSocialBookmarks;
		}

		_enableSocialBookmarks =
			_blogsPortletInstanceConfiguration.enableSocialBookmarks();

		return _enableSocialBookmarks;
	}

	private final BlogsPortletInstanceConfiguration
		_blogsPortletInstanceConfiguration;
	private final BlogsWebRequestHelper _blogsWebRequestHelper;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private Boolean _enableCommentRatings;
	private Boolean _enableComments;
	private Boolean _enableFlags;
	private Boolean _enableRatings;
	private Boolean _enableRelatedAssets;
	private Boolean _enableSocialBookmarks;
	private Integer _pageDelta;
	private String _socialBookmarksDisplayPosition;
	private String _socialBookmarksDisplayStyle;
	private String _socialBookmarksTypes;

}