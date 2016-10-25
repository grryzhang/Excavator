package com.zhongzhou.Excavator.service.migration.NC;

import com.zhongzhou.common.Exception.ServiceRuntimeException;

/**
 * Refer the ServiceNameList.MIGRATION_NC_ItemCategoryService for implements Class Name
 * @author Grry Zhang
 *
 */
public interface NCItemCategoryService {

	public void migrateItemCategory( ) throws ServiceRuntimeException;
}
