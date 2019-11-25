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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.portal.kernel.model.AttachedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.change.tracking.CTModel;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the DDMTemplateLink service. Represents a row in the &quot;DDMTemplateLink&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLink
 * @generated
 */
@ProviderType
public interface DDMTemplateLinkModel
	extends AttachedModel, BaseModel<DDMTemplateLink>, CTModel<DDMTemplateLink>,
			MVCCModel, ShardedModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a ddm template link model instance should use the {@link DDMTemplateLink} interface instead.
	 */

	/**
	 * Returns the primary key of this ddm template link.
	 *
	 * @return the primary key of this ddm template link
	 */
	@Override
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this ddm template link.
	 *
	 * @param primaryKey the primary key of this ddm template link
	 */
	@Override
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this ddm template link.
	 *
	 * @return the mvcc version of this ddm template link
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this ddm template link.
	 *
	 * @param mvccVersion the mvcc version of this ddm template link
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the ct collection ID of this ddm template link.
	 *
	 * @return the ct collection ID of this ddm template link
	 */
	@Override
	public long getCtCollectionId();

	/**
	 * Sets the ct collection ID of this ddm template link.
	 *
	 * @param ctCollectionId the ct collection ID of this ddm template link
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId);

	/**
	 * Returns the template link ID of this ddm template link.
	 *
	 * @return the template link ID of this ddm template link
	 */
	public long getTemplateLinkId();

	/**
	 * Sets the template link ID of this ddm template link.
	 *
	 * @param templateLinkId the template link ID of this ddm template link
	 */
	public void setTemplateLinkId(long templateLinkId);

	/**
	 * Returns the company ID of this ddm template link.
	 *
	 * @return the company ID of this ddm template link
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this ddm template link.
	 *
	 * @param companyId the company ID of this ddm template link
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the fully qualified class name of this ddm template link.
	 *
	 * @return the fully qualified class name of this ddm template link
	 */
	@Override
	public String getClassName();

	public void setClassName(String className);

	/**
	 * Returns the class name ID of this ddm template link.
	 *
	 * @return the class name ID of this ddm template link
	 */
	@Override
	public long getClassNameId();

	/**
	 * Sets the class name ID of this ddm template link.
	 *
	 * @param classNameId the class name ID of this ddm template link
	 */
	@Override
	public void setClassNameId(long classNameId);

	/**
	 * Returns the class pk of this ddm template link.
	 *
	 * @return the class pk of this ddm template link
	 */
	@Override
	public long getClassPK();

	/**
	 * Sets the class pk of this ddm template link.
	 *
	 * @param classPK the class pk of this ddm template link
	 */
	@Override
	public void setClassPK(long classPK);

	/**
	 * Returns the template ID of this ddm template link.
	 *
	 * @return the template ID of this ddm template link
	 */
	public long getTemplateId();

	/**
	 * Sets the template ID of this ddm template link.
	 *
	 * @param templateId the template ID of this ddm template link
	 */
	public void setTemplateId(long templateId);

}