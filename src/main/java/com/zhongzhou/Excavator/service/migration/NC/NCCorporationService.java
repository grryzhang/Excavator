package com.zhongzhou.Excavator.service.migration.NC;

import com.zhongzhou.Excavator.model.NC.CorporationSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;

/**
 * Refer the ServiceNameList.MIGRATION_NC_CorporationService for implements Class Name
 * @author Grry Zhang
 *
 */
public interface NCCorporationService {

	public void migrateCorporations( CorporationSearchParameters searchParameters ) throws ServiceRuntimeException;
}
