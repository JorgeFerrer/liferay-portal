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

package com.liferay.portlet.messageboards.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.messageboards.model.MBCategory;

/**
 * The persistence interface for the message boards category service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBCategoryPersistenceImpl
 * @see MBCategoryUtil
 * @generated
 */
@ProviderType
public interface MBCategoryPersistence extends BasePersistence<MBCategory> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBCategoryUtil} to access the message boards category persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the message boards categories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the message boards categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first message boards category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the first message boards category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last message boards category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the last message boards category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set where uuid = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory[] findByUuid_PrevAndNext(
		long categoryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Removes all the message boards categories where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of message boards categories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching message boards categories
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the message boards category where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchCategoryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the message boards category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByUUID_G(
		java.lang.String uuid, long groupId);

	/**
	* Returns the message boards category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache);

	/**
	* Removes the message boards category where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the message boards category that was removed
	*/
	public com.liferay.portlet.messageboards.model.MBCategory removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the number of message boards categories where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching message boards categories
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the message boards categories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the message boards categories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first message boards category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the first message boards category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last message boards category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the last message boards category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory[] findByUuid_C_PrevAndNext(
		long categoryId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Removes all the message boards categories where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of message boards categories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching message boards categories
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the message boards categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the message boards categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first message boards category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the first message boards category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last message boards category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the last message boards category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set where groupId = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory[] findByGroupId_PrevAndNext(
		long categoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns all the message boards categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByGroupId(
		long groupId);

	/**
	* Returns a range of all the message boards categories that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set of message boards categories that the user has permission to view where groupId = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory[] filterFindByGroupId_PrevAndNext(
		long categoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Removes all the message boards categories where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of message boards categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching message boards categories
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of message boards categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching message boards categories that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the message boards categories where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the message boards categories where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first message boards category in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the first message boards category in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last message boards category in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the last message boards category in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set where companyId = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory[] findByCompanyId_PrevAndNext(
		long categoryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Removes all the message boards categories where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of message boards categories where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching message boards categories
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the message boards categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long parentCategoryId);

	/**
	* Returns a range of all the message boards categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long parentCategoryId, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByG_P_First(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the first message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByG_P_First(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByG_P_Last(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the last message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByG_P_Last(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory[] findByG_P_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P(
		long groupId, long parentCategoryId);

	/**
	* Returns a range of all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P(
		long groupId, long parentCategoryId, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories that the user has permissions to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P(
		long groupId, long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set of message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory[] filterFindByG_P_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @return the matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P(
		long groupId, long[] parentCategoryIds);

	/**
	* Returns a range of all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P(
		long groupId, long[] parentCategoryIds, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P(
		long groupId, long[] parentCategoryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns all the message boards categories where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long[] parentCategoryIds);

	/**
	* Returns a range of all the message boards categories where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long[] parentCategoryIds, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long[] parentCategoryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the message boards categories where groupId = &#63; and parentCategoryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	*/
	public void removeByG_P(long groupId, long parentCategoryId);

	/**
	* Returns the number of message boards categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the number of matching message boards categories
	*/
	public int countByG_P(long groupId, long parentCategoryId);

	/**
	* Returns the number of message boards categories where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @return the number of matching message boards categories
	*/
	public int countByG_P(long groupId, long[] parentCategoryIds);

	/**
	* Returns the number of message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the number of matching message boards categories that the user has permission to view
	*/
	public int filterCountByG_P(long groupId, long parentCategoryId);

	/**
	* Returns the number of message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @return the number of matching message boards categories that the user has permission to view
	*/
	public int filterCountByG_P(long groupId, long[] parentCategoryIds);

	/**
	* Returns all the message boards categories where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_S(
		long groupId, int status);

	/**
	* Returns a range of all the message boards categories where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_S(
		long groupId, int status, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first message boards category in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the first message boards category in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last message boards category in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the last message boards category in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory[] findByG_S_PrevAndNext(
		long categoryId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns all the message boards categories that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_S(
		long groupId, int status);

	/**
	* Returns a range of all the message boards categories that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_S(
		long groupId, int status, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories that the user has permissions to view where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set of message boards categories that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory[] filterFindByG_S_PrevAndNext(
		long categoryId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Removes all the message boards categories where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public void removeByG_S(long groupId, int status);

	/**
	* Returns the number of message boards categories where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching message boards categories
	*/
	public int countByG_S(long groupId, int status);

	/**
	* Returns the number of message boards categories that the user has permission to view where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching message boards categories that the user has permission to view
	*/
	public int filterCountByG_S(long groupId, int status);

	/**
	* Returns all the message boards categories where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByC_S(
		long companyId, int status);

	/**
	* Returns a range of all the message boards categories where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByC_S(
		long companyId, int status, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first message boards category in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByC_S_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the first message boards category in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByC_S_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last message boards category in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByC_S_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the last message boards category in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByC_S_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory[] findByC_S_PrevAndNext(
		long categoryId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Removes all the message boards categories where companyId = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public void removeByC_S(long companyId, int status);

	/**
	* Returns the number of message boards categories where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching message boards categories
	*/
	public int countByC_S(long companyId, int status);

	/**
	* Returns all the message boards categories where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P_S(
		long groupId, long parentCategoryId, int status);

	/**
	* Returns a range of all the message boards categories where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P_S(
		long groupId, long parentCategoryId, int status, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P_S(
		long groupId, long parentCategoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByG_P_S_First(
		long groupId, long parentCategoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the first message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByG_P_S_First(
		long groupId, long parentCategoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByG_P_S_Last(
		long groupId, long parentCategoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the last message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByG_P_S_Last(
		long groupId, long parentCategoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory[] findByG_P_S_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @return the matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P_S(
		long groupId, long parentCategoryId, int status);

	/**
	* Returns a range of all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P_S(
		long groupId, long parentCategoryId, int status, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories that the user has permissions to view where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P_S(
		long groupId, long parentCategoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set of message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory[] filterFindByG_P_S_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @return the matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P_S(
		long groupId, long[] parentCategoryIds, int status);

	/**
	* Returns a range of all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P_S(
		long groupId, long[] parentCategoryIds, int status, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P_S(
		long groupId, long[] parentCategoryIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns all the message boards categories where groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P_S(
		long groupId, long[] parentCategoryIds, int status);

	/**
	* Returns a range of all the message boards categories where groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P_S(
		long groupId, long[] parentCategoryIds, int status, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories where groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P_S(
		long groupId, long[] parentCategoryIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the message boards categories where groupId = &#63; and parentCategoryId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	*/
	public void removeByG_P_S(long groupId, long parentCategoryId, int status);

	/**
	* Returns the number of message boards categories where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @return the number of matching message boards categories
	*/
	public int countByG_P_S(long groupId, long parentCategoryId, int status);

	/**
	* Returns the number of message boards categories where groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @return the number of matching message boards categories
	*/
	public int countByG_P_S(long groupId, long[] parentCategoryIds, int status);

	/**
	* Returns the number of message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @return the number of matching message boards categories that the user has permission to view
	*/
	public int filterCountByG_P_S(long groupId, long parentCategoryId,
		int status);

	/**
	* Returns the number of message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @return the number of matching message boards categories that the user has permission to view
	*/
	public int filterCountByG_P_S(long groupId, long[] parentCategoryIds,
		int status);

	/**
	* Returns all the message boards categories where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByNotC_G_P(
		long categoryId, long groupId, long parentCategoryId);

	/**
	* Returns a range of all the message boards categories where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByNotC_G_P(
		long categoryId, long groupId, long parentCategoryId, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByNotC_G_P(
		long categoryId, long groupId, long parentCategoryId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first message boards category in the ordered set where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByNotC_G_P_First(
		long categoryId, long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the first message boards category in the ordered set where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByNotC_G_P_First(
		long categoryId, long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last message boards category in the ordered set where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByNotC_G_P_Last(
		long categoryId, long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the last message boards category in the ordered set where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByNotC_G_P_Last(
		long categoryId, long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns all the message boards categories that the user has permission to view where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByNotC_G_P(
		long categoryId, long groupId, long parentCategoryId);

	/**
	* Returns a range of all the message boards categories that the user has permission to view where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByNotC_G_P(
		long categoryId, long groupId, long parentCategoryId, int start, int end);

	/**
	* Returns an ordered range of all the message boards categories that the user has permissions to view where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByNotC_G_P(
		long categoryId, long groupId, long parentCategoryId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns all the message boards categories that the user has permission to view where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63;.
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @return the matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByNotC_G_P(
		long[] categoryIds, long groupId, long[] parentCategoryIds);

	/**
	* Returns a range of all the message boards categories that the user has permission to view where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByNotC_G_P(
		long[] categoryIds, long groupId, long[] parentCategoryIds, int start,
		int end);

	/**
	* Returns an ordered range of all the message boards categories that the user has permission to view where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByNotC_G_P(
		long[] categoryIds, long groupId, long[] parentCategoryIds, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns all the message boards categories where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByNotC_G_P(
		long[] categoryIds, long groupId, long[] parentCategoryIds);

	/**
	* Returns a range of all the message boards categories where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByNotC_G_P(
		long[] categoryIds, long groupId, long[] parentCategoryIds, int start,
		int end);

	/**
	* Returns an ordered range of all the message boards categories where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByNotC_G_P(
		long[] categoryIds, long groupId, long[] parentCategoryIds, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the message boards categories where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; from the database.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	*/
	public void removeByNotC_G_P(long categoryId, long groupId,
		long parentCategoryId);

	/**
	* Returns the number of message boards categories where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the number of matching message boards categories
	*/
	public int countByNotC_G_P(long categoryId, long groupId,
		long parentCategoryId);

	/**
	* Returns the number of message boards categories where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63;.
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @return the number of matching message boards categories
	*/
	public int countByNotC_G_P(long[] categoryIds, long groupId,
		long[] parentCategoryIds);

	/**
	* Returns the number of message boards categories that the user has permission to view where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the number of matching message boards categories that the user has permission to view
	*/
	public int filterCountByNotC_G_P(long categoryId, long groupId,
		long parentCategoryId);

	/**
	* Returns the number of message boards categories that the user has permission to view where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63;.
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @return the number of matching message boards categories that the user has permission to view
	*/
	public int filterCountByNotC_G_P(long[] categoryIds, long groupId,
		long[] parentCategoryIds);

	/**
	* Returns all the message boards categories where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByNotC_G_P_S(
		long categoryId, long groupId, long parentCategoryId, int status);

	/**
	* Returns a range of all the message boards categories where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByNotC_G_P_S(
		long categoryId, long groupId, long parentCategoryId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the message boards categories where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByNotC_G_P_S(
		long categoryId, long groupId, long parentCategoryId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first message boards category in the ordered set where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByNotC_G_P_S_First(
		long categoryId, long groupId, long parentCategoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the first message boards category in the ordered set where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByNotC_G_P_S_First(
		long categoryId, long groupId, long parentCategoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last message boards category in the ordered set where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByNotC_G_P_S_Last(
		long categoryId, long groupId, long parentCategoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the last message boards category in the ordered set where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category, or <code>null</code> if a matching message boards category could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByNotC_G_P_S_Last(
		long categoryId, long groupId, long parentCategoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns all the message boards categories that the user has permission to view where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @return the matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByNotC_G_P_S(
		long categoryId, long groupId, long parentCategoryId, int status);

	/**
	* Returns a range of all the message boards categories that the user has permission to view where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByNotC_G_P_S(
		long categoryId, long groupId, long parentCategoryId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the message boards categories that the user has permissions to view where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByNotC_G_P_S(
		long categoryId, long groupId, long parentCategoryId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns all the message boards categories that the user has permission to view where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @return the matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByNotC_G_P_S(
		long[] categoryIds, long groupId, long[] parentCategoryIds, int status);

	/**
	* Returns a range of all the message boards categories that the user has permission to view where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByNotC_G_P_S(
		long[] categoryIds, long groupId, long[] parentCategoryIds, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the message boards categories that the user has permission to view where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories that the user has permission to view
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByNotC_G_P_S(
		long[] categoryIds, long groupId, long[] parentCategoryIds, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns all the message boards categories where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @return the matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByNotC_G_P_S(
		long[] categoryIds, long groupId, long[] parentCategoryIds, int status);

	/**
	* Returns a range of all the message boards categories where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByNotC_G_P_S(
		long[] categoryIds, long groupId, long[] parentCategoryIds, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the message boards categories where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByNotC_G_P_S(
		long[] categoryIds, long groupId, long[] parentCategoryIds, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the message boards categories where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; and status = &#63; from the database.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	*/
	public void removeByNotC_G_P_S(long categoryId, long groupId,
		long parentCategoryId, int status);

	/**
	* Returns the number of message boards categories where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @return the number of matching message boards categories
	*/
	public int countByNotC_G_P_S(long categoryId, long groupId,
		long parentCategoryId, int status);

	/**
	* Returns the number of message boards categories where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @return the number of matching message boards categories
	*/
	public int countByNotC_G_P_S(long[] categoryIds, long groupId,
		long[] parentCategoryIds, int status);

	/**
	* Returns the number of message boards categories that the user has permission to view where categoryId &ne; &#63; and groupId = &#63; and parentCategoryId = &#63; and status = &#63;.
	*
	* @param categoryId the category ID
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param status the status
	* @return the number of matching message boards categories that the user has permission to view
	*/
	public int filterCountByNotC_G_P_S(long categoryId, long groupId,
		long parentCategoryId, int status);

	/**
	* Returns the number of message boards categories that the user has permission to view where categoryId &ne; all &#63; and groupId = &#63; and parentCategoryId = any &#63; and status = &#63;.
	*
	* @param categoryIds the category IDs
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param status the status
	* @return the number of matching message boards categories that the user has permission to view
	*/
	public int filterCountByNotC_G_P_S(long[] categoryIds, long groupId,
		long[] parentCategoryIds, int status);

	/**
	* Caches the message boards category in the entity cache if it is enabled.
	*
	* @param mbCategory the message boards category
	*/
	public void cacheResult(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory);

	/**
	* Caches the message boards categories in the entity cache if it is enabled.
	*
	* @param mbCategories the message boards categories
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBCategory> mbCategories);

	/**
	* Creates a new message boards category with the primary key. Does not add the message boards category to the database.
	*
	* @param categoryId the primary key for the new message boards category
	* @return the new message boards category
	*/
	public com.liferay.portlet.messageboards.model.MBCategory create(
		long categoryId);

	/**
	* Removes the message boards category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the message boards category
	* @return the message boards category that was removed
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory remove(
		long categoryId)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	public com.liferay.portlet.messageboards.model.MBCategory updateImpl(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory);

	/**
	* Returns the message boards category with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchCategoryException} if it could not be found.
	*
	* @param categoryId the primary key of the message boards category
	* @return the message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory findByPrimaryKey(
		long categoryId)
		throws com.liferay.portlet.messageboards.NoSuchCategoryException;

	/**
	* Returns the message boards category with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param categoryId the primary key of the message boards category
	* @return the message boards category, or <code>null</code> if a message boards category with the primary key could not be found
	*/
	public com.liferay.portlet.messageboards.model.MBCategory fetchByPrimaryKey(
		long categoryId);

	/**
	* Returns all the message boards categories.
	*
	* @return the message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findAll();

	/**
	* Returns a range of all the message boards categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the message boards categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message boards categories
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the message boards categories from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of message boards categories.
	*
	* @return the number of message boards categories
	*/
	public int countAll();
}