package com.zhongzhou.Excavator.service.MD;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.zhongzhou.Excavator.model.masterdata.Corporation;
import com.zhongzhou.Excavator.model.masterdata.CorporationGrade;
import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.CorporationStatisticsSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.common.Exception.ServiceRuntimeException;

public interface CorporationService {

	public List<Corporation> selectItemCorporations(ItemSearchParameters searchParameters) throws ServiceRuntimeException;

	public List<Corporation> selectCorporations(CorporationSearchParameters searchParameters) throws ServiceRuntimeException;

	public List<Corporation> selectCorporationsBySameItemCategory(CorporationSearchParameters searchParameters) throws ServiceRuntimeException;

	public long countCorporationsBySameItemCategory(CorporationSearchParameters searchParameters) throws ServiceRuntimeException;
	
	public List<CorporationGrade> selectCorporationGrades(CorporationSearchParameters searchParameters) throws ServiceRuntimeException;
	
	public List<CorporationGrade> selectCorporationGradesBySameItemCategory(CorporationSearchParameters searchParameters) throws ServiceRuntimeException;
	
	public List<Map<String, String>> selectCorporationStatistics(CorporationStatisticsSearchParameters searchParameters) throws ServiceRuntimeException;

	public List<String> selectAllCorporationSource() throws ServiceRuntimeException;

	public List<Corporation> selectScreenCorporation(CorporationStatisticsSearchParameters searchParameters) throws SQLException;

}
