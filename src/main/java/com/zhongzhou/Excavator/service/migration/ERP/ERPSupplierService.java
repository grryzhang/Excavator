package com.zhongzhou.Excavator.service.migration.ERP;

import com.zhongzhou.Excavator.model.CorporationSearchParameters;
import com.zhongzhou.common.Exception.ServiceRuntimeException;

public interface ERPSupplierService {

	public void migrateSupplierFromMD2ERP( CorporationSearchParameters searchParameters ) throws ServiceRuntimeException;
}
