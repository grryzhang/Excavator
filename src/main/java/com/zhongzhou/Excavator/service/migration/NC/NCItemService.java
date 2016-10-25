package com.zhongzhou.Excavator.service.migration.NC;

import java.io.InputStream;

import com.zhongzhou.Excavator.model.NC.ItemSearchParameters;
import com.zhongzhou.common.Exception.ServiceRuntimeException;

/**
 * Refer the ServiceNameList.MIGRATION_NC_ItemService for implements Class Name
 * @author Grry Zhang
 *
 */
public interface NCItemService {

	public void migrateItem( ItemSearchParameters searchParameters ) throws ServiceRuntimeException;
	
	public void mapItem2Category()  throws ServiceRuntimeException;
	
	public void updatePackageInfoFromExcel2007( InputStream excelStream ) throws ServiceRuntimeException;
	
	public void updateCustomInfoFromExcel2007( InputStream excelStream ) throws ServiceRuntimeException;

	public void migrateItemPackageInfo(ItemSearchParameters searchParameters) throws ServiceRuntimeException;
}
