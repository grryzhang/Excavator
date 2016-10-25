package com.zhongzhou.Excavator.service.migration.ERP;

import java.sql.SQLException;

import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.common.Exception.ServiceRuntimeException;

public interface ERPSupplierService {

	public void migrateSupplierFromMD2ERP( CorporationSearchParameters searchParameters ) throws ServiceRuntimeException;

	public void migrateSupplierGrade2MD() throws SQLException;
}
