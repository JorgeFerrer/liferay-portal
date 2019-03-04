package com.liferay.fragment.importer;

import java.io.File;
import java.util.List;

/**
 * @author Jorge Ferrer
 */
public interface FragmentsImporter {

	List<String> importFile(
			long userId, long groupId, long fragmentCollectionId, File file,
			boolean overwrite)
		throws Exception;

}
