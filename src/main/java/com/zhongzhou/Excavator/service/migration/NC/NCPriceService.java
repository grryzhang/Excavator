package com.zhongzhou.Excavator.service.migration.NC;

import com.zhongzhou.Excavator.Exception.ServiceRuntimeException;
import com.zhongzhou.Excavator.model.NC.PriceSearchParameters;

/**
 * Refer the ServiceNameList.MIGRATION_NC_PriceService for implements Class Name
 * @author Grry Zhang
 *
 */
public interface NCPriceService {

	public void migratePriceCategory() throws ServiceRuntimeException;
	
	public void migratePrice(PriceSearchParameters searchParameters) throws ServiceRuntimeException;
}
