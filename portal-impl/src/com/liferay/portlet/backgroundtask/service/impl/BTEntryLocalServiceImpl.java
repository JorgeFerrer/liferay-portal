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

package com.liferay.portlet.backgroundtask.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.backgroundtask.model.BTEntry;
import com.liferay.portlet.backgroundtask.model.BTEntryConstants;
import com.liferay.portlet.backgroundtask.service.base.BTEntryLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Daniel Kocsis
 * @author Michael C. Han
 */
public class BTEntryLocalServiceImpl extends BTEntryLocalServiceBaseImpl {

	@Override
	public BTEntry addEntry(
			long userId, long groupId, String name,
			String[] servletContextNames, Class<?> taskExecutorClass,
			Map<String, Serializable> taskContextMap,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		final long entryId = counterLocalService.increment();

		BTEntry entry = btEntryPersistence.create(entryId);

		entry.setCompanyId(user.getCompanyId());
		entry.setCreateDate(serviceContext.getCreateDate(now));
		entry.setGroupId(groupId);
		entry.setModifiedDate(serviceContext.getModifiedDate(now));
		entry.setUserId(userId);
		entry.setUserName(user.getFullName());
		entry.setName(name);
		entry.setServletContextNames(StringUtil.merge(servletContextNames));
		entry.setTaskExecutorClassName(taskExecutorClass.getName());

		if (taskContextMap != null) {
			String taskContext = JSONFactoryUtil.serialize(taskContextMap);

			entry.setTaskContext(taskContext);
		}

		entry.setStatus(BTEntryConstants.STATUS_NEW);

		btEntryPersistence.update(entry);

		TransactionCommitCallbackRegistryUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Message message = new Message();

					message.put("entryId", entryId);

					MessageBusUtil.sendMessage(
						DestinationNames.BACKGROUND_TASK, message);

					return null;
				}

			});

		return entry;
	}

	@Override
	public void addEntryAttachment(
			long userId, long entryId, String fileName, File file)
		throws PortalException, SystemException {

		BTEntry entry = getBTEntry(entryId);

		Folder folder = entry.addAttachmentsFolder();

		PortletFileRepositoryUtil.addPortletFileEntry(
			entry.getGroupId(), userId, BTEntry.class.getName(),
			entry.getPrimaryKey(), PortletKeys.BACKGROUND_TASK,
			folder.getFolderId(), file, fileName, null);
	}

	@Override
	public void addEntryAttachment(
			long userId, long entryId, String fileName, InputStream inputStream)
		throws PortalException, SystemException {

		BTEntry entry = getBTEntry(entryId);

		Folder folder = entry.addAttachmentsFolder();

		PortletFileRepositoryUtil.addPortletFileEntry(
			entry.getGroupId(), userId, BTEntry.class.getName(),
			entry.getPrimaryKey(), PortletKeys.BACKGROUND_TASK,
			folder.getFolderId(), inputStream, fileName, null);
	}

	@Override
	public BTEntry deleteEntry(BTEntry entry)
		throws PortalException, SystemException {

		long folderId = entry.getAttachmentsFolderId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			PortletFileRepositoryUtil.deleteFolder(folderId);
		}

		return btEntryPersistence.remove(entry);
	}

	@Override
	public BTEntry fetchEntry(long entryId) throws SystemException {
		return btEntryPersistence.fetchByPrimaryKey(entryId);
	}

	@Override
	public List<BTEntry> getEntries(long groupId, String taskExecutorClassName)
		throws SystemException {

		return btEntryPersistence.findByG_T(groupId, taskExecutorClassName);
	}

	@Override
	public List<BTEntry> getEntries(
			long groupId, String taskExecutorClassName, int status)
		throws SystemException {

		return btEntryPersistence.findByG_T_S(
			groupId, taskExecutorClassName, status);
	}

	@Override
	public BTEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return btEntryPersistence.findByPrimaryKey(entryId);
	}

	@Override
	public BTEntry updateEntry(
			long entryId, Map<String, Serializable> taskContextMap, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Date now = new Date();

		BTEntry entry = btEntryPersistence.findByPrimaryKey(entryId);

		entry.setModifiedDate(serviceContext.getModifiedDate(now));

		if (taskContextMap != null) {
			String taskContext = JSONFactoryUtil.serialize(taskContextMap);

			entry.setTaskContext(taskContext);
		}

		if ((status == BTEntryConstants.STATUS_FAILED) ||
			(status == BTEntryConstants.STATUS_SUCCESSFUL)) {

			entry.setCompleted(true);
			entry.setCompletionDate(now);
		}

		entry.setStatus(status);

		btEntryPersistence.update(entry);

		return entry;
	}

}