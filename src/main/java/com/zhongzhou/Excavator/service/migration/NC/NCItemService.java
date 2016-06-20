package com.zhongzhou.Excavator.service.migration.NC;

import com.zhongzhou.Excavator.model.NC.ItemSearchParameters;
import com.zhongzhou.common.Exception.ServiceRuntimeException;

/**
 * Refer the ServiceNameList.MIGRATION_NC_ItemService for implements Class Name
 * @author Grry Zhang
 *
 */
public interface NCItemService {

	public void migrateItem( ItemSearchParameters searchParameters ) throws ServiceRuntimeException;
}
