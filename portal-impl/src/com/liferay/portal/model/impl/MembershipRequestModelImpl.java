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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestModel;
import com.liferay.portal.model.MembershipRequestSoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the MembershipRequest service. Represents a row in the &quot;MembershipRequest&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.MembershipRequestModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MembershipRequestImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MembershipRequestImpl
 * @see com.liferay.portal.model.MembershipRequest
 * @see com.liferay.portal.model.MembershipRequestModel
 * @generated
 */
@JSON(strict = true)
public class MembershipRequestModelImpl extends BaseModelImpl<MembershipRequest>
	implements MembershipRequestModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a membership request model instance should use the {@link com.liferay.portal.model.MembershipRequest} interface instead.
	 */
	public static final String TABLE_NAME = "MembershipRequest";
	public static final Object[][] TABLE_COLUMNS = {
			{ "membershipRequestId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "createDate", Types.TIMESTAMP },
			{ "comments", Types.VARCHAR },
			{ "replyComments", Types.VARCHAR },
			{ "replyDate", Types.TIMESTAMP },
			{ "replierUserId", Types.BIGINT },
			{ "statusId", Types.INTEGER }
		};
	public static final String TABLE_SQL_CREATE = "create table MembershipRequest (membershipRequestId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate DATE null,comments STRING null,replyComments STRING null,replyDate DATE null,replierUserId LONG,statusId INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table MembershipRequest";
	public static final String ORDER_BY_JPQL = " ORDER BY membershipRequest.createDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY MembershipRequest.createDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.MembershipRequest"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.MembershipRequest"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.model.MembershipRequest"),
			true);
	public static long GROUPID_COLUMN_BITMASK = 1L;
	public static long STATUSID_COLUMN_BITMASK = 2L;
	public static long USERID_COLUMN_BITMASK = 4L;
	public static long CREATEDATE_COLUMN_BITMASK = 8L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static MembershipRequest toModel(MembershipRequestSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		MembershipRequest model = new MembershipRequestImpl();

		model.setMembershipRequestId(soapModel.getMembershipRequestId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setComments(soapModel.getComments());
		model.setReplyComments(soapModel.getReplyComments());
		model.setReplyDate(soapModel.getReplyDate());
		model.setReplierUserId(soapModel.getReplierUserId());
		model.setStatusId(soapModel.getStatusId());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<MembershipRequest> toModels(
		MembershipRequestSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<MembershipRequest> models = new ArrayList<MembershipRequest>(soapModels.length);

		for (MembershipRequestSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.MembershipRequest"));

	public MembershipRequestModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _membershipRequestId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setMembershipRequestId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _membershipRequestId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return MembershipRequest.class;
	}

	@Override
	public String getModelClassName() {
		return MembershipRequest.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("membershipRequestId", getMembershipRequestId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("comments", getComments());
		attributes.put("replyComments", getReplyComments());
		attributes.put("replyDate", getReplyDate());
		attributes.put("replierUserId", getReplierUserId());
		attributes.put("statusId", getStatusId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long membershipRequestId = (Long)attributes.get("membershipRequestId");

		if (membershipRequestId != null) {
			setMembershipRequestId(membershipRequestId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}

		String replyComments = (String)attributes.get("replyComments");

		if (replyComments != null) {
			setReplyComments(replyComments);
		}

		Date replyDate = (Date)attributes.get("replyDate");

		if (replyDate != null) {
			setReplyDate(replyDate);
		}

		Long replierUserId = (Long)attributes.get("replierUserId");

		if (replierUserId != null) {
			setReplierUserId(replierUserId);
		}

		Integer statusId = (Integer)attributes.get("statusId");

		if (statusId != null) {
			setStatusId(statusId);
		}
	}

	@Override
	@JSON
	public long getMembershipRequestId() {
		return _membershipRequestId;
	}

	@Override
	public void setMembershipRequestId(long membershipRequestId) {
		_membershipRequestId = membershipRequestId;
	}

	@Override
	@JSON
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@Override
	@JSON
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	@JSON
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@Override
	@JSON
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask = -1L;

		_createDate = createDate;
	}

	@Override
	@JSON
	public String getComments() {
		if (_comments == null) {
			return StringPool.BLANK;
		}
		else {
			return _comments;
		}
	}

	@Override
	public void setComments(String comments) {
		_comments = comments;
	}

	@Override
	@JSON
	public String getReplyComments() {
		if (_replyComments == null) {
			return StringPool.BLANK;
		}
		else {
			return _replyComments;
		}
	}

	@Override
	public void setReplyComments(String replyComments) {
		_replyComments = replyComments;
	}

	@Override
	@JSON
	public Date getReplyDate() {
		return _replyDate;
	}

	@Override
	public void setReplyDate(Date replyDate) {
		_replyDate = replyDate;
	}

	@Override
	@JSON
	public long getReplierUserId() {
		return _replierUserId;
	}

	@Override
	public void setReplierUserId(long replierUserId) {
		_replierUserId = replierUserId;
	}

	@Override
	public String getReplierUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getReplierUserId(), "uuid",
			_replierUserUuid);
	}

	@Override
	public void setReplierUserUuid(String replierUserUuid) {
		_replierUserUuid = replierUserUuid;
	}

	@Override
	@JSON
	public int getStatusId() {
		return _statusId;
	}

	@Override
	public void setStatusId(int statusId) {
		_columnBitmask |= STATUSID_COLUMN_BITMASK;

		if (!_setOriginalStatusId) {
			_setOriginalStatusId = true;

			_originalStatusId = _statusId;
		}

		_statusId = statusId;
	}

	public int getOriginalStatusId() {
		return _originalStatusId;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			MembershipRequest.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public MembershipRequest toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (MembershipRequest)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		MembershipRequestImpl membershipRequestImpl = new MembershipRequestImpl();

		membershipRequestImpl.setMembershipRequestId(getMembershipRequestId());
		membershipRequestImpl.setGroupId(getGroupId());
		membershipRequestImpl.setCompanyId(getCompanyId());
		membershipRequestImpl.setUserId(getUserId());
		membershipRequestImpl.setCreateDate(getCreateDate());
		membershipRequestImpl.setComments(getComments());
		membershipRequestImpl.setReplyComments(getReplyComments());
		membershipRequestImpl.setReplyDate(getReplyDate());
		membershipRequestImpl.setReplierUserId(getReplierUserId());
		membershipRequestImpl.setStatusId(getStatusId());

		membershipRequestImpl.resetOriginalValues();

		return membershipRequestImpl;
	}

	@Override
	public int compareTo(MembershipRequest membershipRequest) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(),
				membershipRequest.getCreateDate());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MembershipRequest)) {
			return false;
		}

		MembershipRequest membershipRequest = (MembershipRequest)obj;

		long primaryKey = membershipRequest.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		MembershipRequestModelImpl membershipRequestModelImpl = this;

		membershipRequestModelImpl._originalGroupId = membershipRequestModelImpl._groupId;

		membershipRequestModelImpl._setOriginalGroupId = false;

		membershipRequestModelImpl._originalUserId = membershipRequestModelImpl._userId;

		membershipRequestModelImpl._setOriginalUserId = false;

		membershipRequestModelImpl._originalStatusId = membershipRequestModelImpl._statusId;

		membershipRequestModelImpl._setOriginalStatusId = false;

		membershipRequestModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<MembershipRequest> toCacheModel() {
		MembershipRequestCacheModel membershipRequestCacheModel = new MembershipRequestCacheModel();

		membershipRequestCacheModel.membershipRequestId = getMembershipRequestId();

		membershipRequestCacheModel.groupId = getGroupId();

		membershipRequestCacheModel.companyId = getCompanyId();

		membershipRequestCacheModel.userId = getUserId();

		Date createDate = getCreateDate();

		if (createDate != null) {
			membershipRequestCacheModel.createDate = createDate.getTime();
		}
		else {
			membershipRequestCacheModel.createDate = Long.MIN_VALUE;
		}

		membershipRequestCacheModel.comments = getComments();

		String comments = membershipRequestCacheModel.comments;

		if ((comments != null) && (comments.length() == 0)) {
			membershipRequestCacheModel.comments = null;
		}

		membershipRequestCacheModel.replyComments = getReplyComments();

		String replyComments = membershipRequestCacheModel.replyComments;

		if ((replyComments != null) && (replyComments.length() == 0)) {
			membershipRequestCacheModel.replyComments = null;
		}

		Date replyDate = getReplyDate();

		if (replyDate != null) {
			membershipRequestCacheModel.replyDate = replyDate.getTime();
		}
		else {
			membershipRequestCacheModel.replyDate = Long.MIN_VALUE;
		}

		membershipRequestCacheModel.replierUserId = getReplierUserId();

		membershipRequestCacheModel.statusId = getStatusId();

		return membershipRequestCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{membershipRequestId=");
		sb.append(getMembershipRequestId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", comments=");
		sb.append(getComments());
		sb.append(", replyComments=");
		sb.append(getReplyComments());
		sb.append(", replyDate=");
		sb.append(getReplyDate());
		sb.append(", replierUserId=");
		sb.append(getReplierUserId());
		sb.append(", statusId=");
		sb.append(getStatusId());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.MembershipRequest");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>membershipRequestId</column-name><column-value><![CDATA[");
		sb.append(getMembershipRequestId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>comments</column-name><column-value><![CDATA[");
		sb.append(getComments());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>replyComments</column-name><column-value><![CDATA[");
		sb.append(getReplyComments());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>replyDate</column-name><column-value><![CDATA[");
		sb.append(getReplyDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>replierUserId</column-name><column-value><![CDATA[");
		sb.append(getReplierUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusId</column-name><column-value><![CDATA[");
		sb.append(getStatusId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = MembershipRequest.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			MembershipRequest.class
		};
	private long _membershipRequestId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private Date _createDate;
	private String _comments;
	private String _replyComments;
	private Date _replyDate;
	private long _replierUserId;
	private String _replierUserUuid;
	private int _statusId;
	private int _originalStatusId;
	private boolean _setOriginalStatusId;
	private long _columnBitmask;
	private MembershipRequest _escapedModel;
}