package com.zhongzhou.Excavator.DAO.postgresql.MD;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.NC.CorporationDoc;
import com.zhongzhou.Excavator.model.masterdata.Corporation;
import com.zhongzhou.Excavator.model.masterdata.CorporationGrade;
import com.zhongzhou.Excavator.model.masterdata.CorporationGradeItem;
import com.zhongzhou.Excavator.model.masterdata.CorporationIntegrationMapping;
import com.zhongzhou.Excavator.model.masterdata.CorporationIntegrationMappingSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.CorporationStatisticsSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@DataSource( dataSource=DataSourceList.MASTER_POSTGRESQL )  
@Service ( DAOBeanNameList.postgresql_md_corporation )
public interface CorporationDAO {

	public void insertCorporation( Corporation corporation ) throws SQLException;
	
	/**
	 * Batch Operation,not support id generation, all data of uuid type fields should be generated first.
	 * @param corporations
	 * @throws SQLException
	 */
	public void insertCorporations( List<Corporation> corporations ) throws SQLException;
	
	public void updateCorporation( Corporation corporation ) throws SQLException;
	
	public void updateCorporations( List<Corporation> corporations ) throws SQLException;
	
	public void insertCorporationIntegrationMapping( CorporationIntegrationMapping corporationIntegrationMapping ) throws SQLException;
	
	public int deleteCorporation( List<String> ids ) throws SQLException;

	public int countDuplicateNameCorporation( CorporationSearchParameters searchParameters ) throws SQLException;
	
	/**
	 * The duplicate records in result should be close to each other.
	 * <br> Suggest, the SQL bonded to this method should have order query sentence, order by name.
	 * <br> Because, in order to improve the performance, the upper service will use Queue to process the result.
	 */
	public List<Corporation> selectDuplicateNameCorporations( CorporationSearchParameters searchParameters) throws SQLException;
	
	public Long countCorporations( CorporationSearchParameters searchParameters) throws SQLException;
	
	public List<Corporation> selectCorporations( CorporationSearchParameters searchParameters) throws SQLException;

	public List<Corporation> selectItemCorporations( ItemSearchParameters searchParameters ) throws SQLException;
	
	/**
	 * Batch Operation, not support id generation, all data of uuid type fields should be generated first.
	 * @param corporations
	 * @throws SQLException
	 */
	public void insertCorporationIntegrationMappings( List<CorporationIntegrationMapping> mappings) throws SQLException;
	
	public void updateCorporationIntegrationMapping( CorporationIntegrationMapping corporationIntegrationMapping ) throws SQLException;

	public void updateCorporationIntegrationMappings( List<CorporationIntegrationMapping> mappings) throws SQLException;
	
	
	/**
	 * It should only be used for data clean.
	 * 
	 * @param data
	 * <br> Type is Map(String,String),  means  Map(old corporation Id, new corporation Id)
	 * @throws SQLException
	 */
	public void updateMappingsCorporationId( List< Map<String,String> > data ) throws SQLException;
	
	public List<CorporationIntegrationMapping> selectCorporationIntegrationMapping
		(CorporationIntegrationMappingSearchParameters searchParameter) throws SQLException;
	
	
	public void insertCorporationGrade    ( List<CorporationGrade> grades         ) throws SQLException;
	
	public void insertCorporationGradeItem( List<CorporationGradeItem> gradeItems ) throws SQLException;
	
	public List<CorporationGrade> selectCorporationGrades ( CorporationSearchParameters searchParameters ) throws SQLException;
	
	public List<CorporationGrade> selectLatestCorporationGrades ( CorporationSearchParameters searchParameters ) throws SQLException;
	
	public  List<CorporationGrade> selectCorporationGradesByItemCategoryIds ( @Param(value="categoryIds") List<String> ids  ) throws SQLException;
	
	public List<Map<String, String> > selectCorporationStatistics ( CorporationStatisticsSearchParameters searchParameters ) throws SQLException;
	
	public List<Corporation> selectScreenCorporation ( CorporationStatisticsSearchParameters searchParameters ) throws SQLException;
	
	public List<String> selectAllCorporationSource () throws SQLException;
	
}


