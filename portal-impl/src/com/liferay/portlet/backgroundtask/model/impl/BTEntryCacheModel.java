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

package com.liferay.portlet.backgroundtask.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.backgroundtask.model.BTEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing BTEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see BTEntry
 * @generated
 */
public class BTEntryCacheModel implements CacheModel<BTEntry>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{btEntryId=");
		sb.append(btEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", servletContextNames=");
		sb.append(servletContextNames);
		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);
		sb.append(", taskContext=");
		sb.append(taskContext);
		sb.append(", completed=");
		sb.append(completed);
		sb.append(", completionDate=");
		sb.append(completionDate);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BTEntry toEntityModel() {
		BTEntryImpl btEntryImpl = new BTEntryImpl();

		btEntryImpl.setBtEntryId(btEntryId);
		btEntryImpl.setGroupId(groupId);
		btEntryImpl.setCompanyId(companyId);
		btEntryImpl.setUserId(userId);

		if (userName == null) {
			btEntryImpl.setUserName(StringPool.BLANK);
		}
		else {
			btEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			btEntryImpl.setCreateDate(null);
		}
		else {
			btEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			btEntryImpl.setModifiedDate(null);
		}
		else {
			btEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			btEntryImpl.setName(StringPool.BLANK);
		}
		else {
			btEntryImpl.setName(name);
		}

		if (servletContextNames == null) {
			btEntryImpl.setServletContextNames(StringPool.BLANK);
		}
		else {
			btEntryImpl.setServletContextNames(servletContextNames);
		}

		if (taskExecutorClassName == null) {
			btEntryImpl.setTaskExecutorClassName(StringPool.BLANK);
		}
		else {
			btEntryImpl.setTaskExecutorClassName(taskExecutorClassName);
		}

		if (taskContext == null) {
			btEntryImpl.setTaskContext(StringPool.BLANK);
		}
		else {
			btEntryImpl.setTaskContext(taskContext);
		}

		btEntryImpl.setCompleted(completed);

		if (completionDate == Long.MIN_VALUE) {
			btEntryImpl.setCompletionDate(null);
		}
		else {
			btEntryImpl.setCompletionDate(new Date(completionDate));
		}

		btEntryImpl.setStatus(status);

		btEntryImpl.resetOriginalValues();

		return btEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		btEntryId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		servletContextNames = objectInput.readUTF();
		taskExecutorClassName = objectInput.readUTF();
		taskContext = objectInput.readUTF();
		completed = objectInput.readBoolean();
		completionDate = objectInput.readLong();
		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(btEntryId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (servletContextNames == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(servletContextNames);
		}

		if (taskExecutorClassName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(taskExecutorClassName);
		}

		if (taskContext == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(taskContext);
		}

		objectOutput.writeBoolean(completed);
		objectOutput.writeLong(completionDate);
		objectOutput.writeInt(status);
	}

	public long btEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String servletContextNames;
	public String taskExecutorClassName;
	public String taskContext;
	public boolean completed;
	public long completionDate;
	public int status;
}