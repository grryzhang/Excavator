package com.zhongzhou.Excavator.service.migration.NC;

import com.zhongzhou.Excavator.Exception.ServiceRuntimeException;
import com.zhongzhou.Excavator.model.NC.ItemSearchParameters;

public interface NCItemService {

	public void migrateItem( ItemSearchParameters searchParameters ) throws ServiceRuntimeException;
}
