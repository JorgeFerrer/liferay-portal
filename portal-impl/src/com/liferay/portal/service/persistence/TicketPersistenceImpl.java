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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchTicketException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.model.impl.TicketImpl;
import com.liferay.portal.model.impl.TicketModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the ticket service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TicketPersistence
 * @see TicketUtil
 * @generated
 */
public class TicketPersistenceImpl extends BasePersistenceImpl<Ticket>
	implements TicketPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link TicketUtil} to access the ticket persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = TicketImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, TicketImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, TicketImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			TicketModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_KEY = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, TicketImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByKey",
			new String[] { String.class.getName() },
			TicketModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_KEY = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKey",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, TicketImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, TicketImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the ticket in the entity cache if it is enabled.
	 *
	 * @param ticket the ticket
	 */
	public void cacheResult(Ticket ticket) {
		EntityCacheUtil.putResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketImpl.class, ticket.getPrimaryKey(), ticket);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY,
			new Object[] { ticket.getKey() }, ticket);

		ticket.resetOriginalValues();
	}

	/**
	 * Caches the tickets in the entity cache if it is enabled.
	 *
	 * @param tickets the tickets
	 */
	public void cacheResult(List<Ticket> tickets) {
		for (Ticket ticket : tickets) {
			if (EntityCacheUtil.getResult(
						TicketModelImpl.ENTITY_CACHE_ENABLED, TicketImpl.class,
						ticket.getPrimaryKey()) == null) {
				cacheResult(ticket);
			}
			else {
				ticket.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all tickets.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(TicketImpl.class.getName());
		}

		EntityCacheUtil.clearCache(TicketImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ticket.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Ticket ticket) {
		EntityCacheUtil.removeResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketImpl.class, ticket.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(ticket);
	}

	@Override
	public void clearCache(List<Ticket> tickets) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Ticket ticket : tickets) {
			EntityCacheUtil.removeResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
				TicketImpl.class, ticket.getPrimaryKey());

			clearUniqueFindersCache(ticket);
		}
	}

	protected void clearUniqueFindersCache(Ticket ticket) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_KEY,
			new Object[] { ticket.getKey() });
	}

	/**
	 * Creates a new ticket with the primary key. Does not add the ticket to the database.
	 *
	 * @param ticketId the primary key for the new ticket
	 * @return the new ticket
	 */
	public Ticket create(long ticketId) {
		Ticket ticket = new TicketImpl();

		ticket.setNew(true);
		ticket.setPrimaryKey(ticketId);

		return ticket;
	}

	/**
	 * Removes the ticket with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ticketId the primary key of the ticket
	 * @return the ticket that was removed
	 * @throws com.liferay.portal.NoSuchTicketException if a ticket with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Ticket remove(long ticketId)
		throws NoSuchTicketException, SystemException {
		return remove(Long.valueOf(ticketId));
	}

	/**
	 * Removes the ticket with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ticket
	 * @return the ticket that was removed
	 * @throws com.liferay.portal.NoSuchTicketException if a ticket with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Ticket remove(Serializable primaryKey)
		throws NoSuchTicketException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Ticket ticket = (Ticket)session.get(TicketImpl.class, primaryKey);

			if (ticket == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTicketException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ticket);
		}
		catch (NoSuchTicketException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Ticket removeImpl(Ticket ticket) throws SystemException {
		ticket = toUnwrappedModel(ticket);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, ticket);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(ticket);

		return ticket;
	}

	@Override
	public Ticket updateImpl(com.liferay.portal.model.Ticket ticket,
		boolean merge) throws SystemException {
		ticket = toUnwrappedModel(ticket);

		boolean isNew = ticket.isNew();

		TicketModelImpl ticketModelImpl = (TicketModelImpl)ticket;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ticket, merge);

			ticket.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !TicketModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else {
			if ((ticketModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ticketModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { Long.valueOf(ticketModelImpl.getCompanyId()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		EntityCacheUtil.putResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketImpl.class, ticket.getPrimaryKey(), ticket);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY,
				new Object[] { ticket.getKey() }, ticket);
		}
		else {
			if ((ticketModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_KEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { ticketModelImpl.getOriginalKey() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_KEY, args);
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_KEY, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY,
					new Object[] { ticket.getKey() }, ticket);
			}
		}

		return ticket;
	}

	protected Ticket toUnwrappedModel(Ticket ticket) {
		if (ticket instanceof TicketImpl) {
			return ticket;
		}

		TicketImpl ticketImpl = new TicketImpl();

		ticketImpl.setNew(ticket.isNew());
		ticketImpl.setPrimaryKey(ticket.getPrimaryKey());

		ticketImpl.setTicketId(ticket.getTicketId());
		ticketImpl.setCompanyId(ticket.getCompanyId());
		ticketImpl.setCreateDate(ticket.getCreateDate());
		ticketImpl.setClassNameId(ticket.getClassNameId());
		ticketImpl.setClassPK(ticket.getClassPK());
		ticketImpl.setKey(ticket.getKey());
		ticketImpl.setType(ticket.getType());
		ticketImpl.setExtraInfo(ticket.getExtraInfo());
		ticketImpl.setExpirationDate(ticket.getExpirationDate());

		return ticketImpl;
	}

	/**
	 * Returns the ticket with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the ticket
	 * @return the ticket
	 * @throws com.liferay.portal.NoSuchModelException if a ticket with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Ticket findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the ticket with the primary key or throws a {@link com.liferay.portal.NoSuchTicketException} if it could not be found.
	 *
	 * @param ticketId the primary key of the ticket
	 * @return the ticket
	 * @throws com.liferay.portal.NoSuchTicketException if a ticket with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Ticket findByPrimaryKey(long ticketId)
		throws NoSuchTicketException, SystemException {
		Ticket ticket = fetchByPrimaryKey(ticketId);

		if (ticket == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + ticketId);
			}

			throw new NoSuchTicketException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				ticketId);
		}

		return ticket;
	}

	/**
	 * Returns the ticket with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ticket
	 * @return the ticket, or <code>null</code> if a ticket with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Ticket fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the ticket with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ticketId the primary key of the ticket
	 * @return the ticket, or <code>null</code> if a ticket with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Ticket fetchByPrimaryKey(long ticketId) throws SystemException {
		Ticket ticket = (Ticket)EntityCacheUtil.getResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
				TicketImpl.class, ticketId);

		if (ticket == _nullTicket) {
			return null;
		}

		if (ticket == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				ticket = (Ticket)session.get(TicketImpl.class,
						Long.valueOf(ticketId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (ticket != null) {
					cacheResult(ticket);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
						TicketImpl.class, ticketId, _nullTicket);
				}

				closeSession(session);
			}
		}

		return ticket;
	}

	/**
	 * Returns all the tickets where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching tickets
	 * @throws SystemException if a system exception occurred
	 */
	public List<Ticket> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the tickets where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @return the range of matching tickets
	 * @throws SystemException if a system exception occurred
	 */
	public List<Ticket> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the tickets where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching tickets
	 * @throws SystemException if a system exception occurred
	 */
	public List<Ticket> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<Ticket> list = (List<Ticket>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Ticket ticket : list) {
				if ((companyId != ticket.getCompanyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_TICKET_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(TicketModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<Ticket>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ticket in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket
	 * @throws com.liferay.portal.NoSuchTicketException if a matching ticket could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Ticket findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchTicketException, SystemException {
		List<Ticket> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTicketException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last ticket in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ticket
	 * @throws com.liferay.portal.NoSuchTicketException if a matching ticket could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Ticket findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchTicketException, SystemException {
		int count = countByCompanyId(companyId);

		List<Ticket> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTicketException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the tickets before and after the current ticket in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param ticketId the primary key of the current ticket
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ticket
	 * @throws com.liferay.portal.NoSuchTicketException if a ticket with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Ticket[] findByCompanyId_PrevAndNext(long ticketId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchTicketException, SystemException {
		Ticket ticket = findByPrimaryKey(ticketId);

		Session session = null;

		try {
			session = openSession();

			Ticket[] array = new TicketImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, ticket, companyId,
					orderByComparator, true);

			array[1] = ticket;

			array[2] = getByCompanyId_PrevAndNext(session, ticket, companyId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Ticket getByCompanyId_PrevAndNext(Session session, Ticket ticket,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_TICKET_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(TicketModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ticket);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Ticket> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the ticket where key = &#63; or throws a {@link com.liferay.portal.NoSuchTicketException} if it could not be found.
	 *
	 * @param key the key
	 * @return the matching ticket
	 * @throws com.liferay.portal.NoSuchTicketException if a matching ticket could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Ticket findByKey(String key)
		throws NoSuchTicketException, SystemException {
		Ticket ticket = fetchByKey(key);

		if (ticket == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("key=");
			msg.append(key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTicketException(msg.toString());
		}

		return ticket;
	}

	/**
	 * Returns the ticket where key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param key the key
	 * @return the matching ticket, or <code>null</code> if a matching ticket could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Ticket fetchByKey(String key) throws SystemException {
		return fetchByKey(key, true);
	}

	/**
	 * Returns the ticket where key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param key the key
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching ticket, or <code>null</code> if a matching ticket could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Ticket fetchByKey(String key, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { key };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_KEY,
					finderArgs, this);
		}

		if (result instanceof Ticket) {
			Ticket ticket = (Ticket)result;

			if (!Validator.equals(key, ticket.getKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_TICKET_WHERE);

			if (key == null) {
				query.append(_FINDER_COLUMN_KEY_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_KEY_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_KEY_KEY_2);
				}
			}

			query.append(TicketModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (key != null) {
					qPos.add(key);
				}

				List<Ticket> list = q.list();

				result = list;

				Ticket ticket = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY,
						finderArgs, list);
				}
				else {
					ticket = list.get(0);

					cacheResult(ticket);

					if ((ticket.getKey() == null) ||
							!ticket.getKey().equals(key)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY,
							finderArgs, ticket);
					}
				}

				return ticket;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_KEY,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Ticket)result;
			}
		}
	}

	/**
	 * Returns all the tickets.
	 *
	 * @return the tickets
	 * @throws SystemException if a system exception occurred
	 */
	public List<Ticket> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the tickets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @return the range of tickets
	 * @throws SystemException if a system exception occurred
	 */
	public List<Ticket> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the tickets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of tickets
	 * @throws SystemException if a system exception occurred
	 */
	public List<Ticket> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Ticket> list = (List<Ticket>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_TICKET);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_TICKET.concat(TicketModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Ticket>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Ticket>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the tickets where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (Ticket ticket : findByCompanyId(companyId)) {
			remove(ticket);
		}
	}

	/**
	 * Removes the ticket where key = &#63; from the database.
	 *
	 * @param key the key
	 * @return the ticket that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Ticket removeByKey(String key)
		throws NoSuchTicketException, SystemException {
		Ticket ticket = findByKey(key);

		return remove(ticket);
	}

	/**
	 * Removes all the tickets from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Ticket ticket : findAll()) {
			remove(ticket);
		}
	}

	/**
	 * Returns the number of tickets where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching tickets
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TICKET_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of tickets where key = &#63;.
	 *
	 * @param key the key
	 * @return the number of matching tickets
	 * @throws SystemException if a system exception occurred
	 */
	public int countByKey(String key) throws SystemException {
		Object[] finderArgs = new Object[] { key };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_KEY,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TICKET_WHERE);

			if (key == null) {
				query.append(_FINDER_COLUMN_KEY_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_KEY_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_KEY_KEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (key != null) {
					qPos.add(key);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_KEY, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of tickets.
	 *
	 * @return the number of tickets
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_TICKET);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the ticket persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Ticket")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Ticket>> listenersList = new ArrayList<ModelListener<Ticket>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Ticket>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(TicketImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortalPreferencesPersistence.class)
	protected PortalPreferencesPersistence portalPreferencesPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_TICKET = "SELECT ticket FROM Ticket ticket";
	private static final String _SQL_SELECT_TICKET_WHERE = "SELECT ticket FROM Ticket ticket WHERE ";
	private static final String _SQL_COUNT_TICKET = "SELECT COUNT(ticket) FROM Ticket ticket";
	private static final String _SQL_COUNT_TICKET_WHERE = "SELECT COUNT(ticket) FROM Ticket ticket WHERE ";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "ticket.companyId = ?";
	private static final String _FINDER_COLUMN_KEY_KEY_1 = "ticket.key IS NULL";
	private static final String _FINDER_COLUMN_KEY_KEY_2 = "ticket.key = ?";
	private static final String _FINDER_COLUMN_KEY_KEY_3 = "(ticket.key IS NULL OR ticket.key = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ticket.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Ticket exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Ticket exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(TicketPersistenceImpl.class);
	private static Ticket _nullTicket = new TicketImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Ticket> toCacheModel() {
				return _nullTicketCacheModel;
			}
		};

	private static CacheModel<Ticket> _nullTicketCacheModel = new CacheModel<Ticket>() {
			public Ticket toEntityModel() {
				return _nullTicket;
			}
		};
}