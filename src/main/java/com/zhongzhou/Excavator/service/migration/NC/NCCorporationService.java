package com.zhongzhou.Excavator.service.migration.NC;

import com.zhongzhou.Excavator.Exception.ServiceRuntimeException;
import com.zhongzhou.Excavator.model.NC.CorporationSearchParameters;

public interface NCCorporationService {

	public void migrateCorporations( CorporationSearchParameters searchParameters ) throws ServiceRuntimeException;
}
