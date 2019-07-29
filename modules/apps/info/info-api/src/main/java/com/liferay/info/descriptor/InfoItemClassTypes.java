package com.liferay.info.descriptor;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

public interface InfoItemClassTypes<T> {

	public List<ClassType> getClassTypes(long groupId)
		throws PortalException;

}
