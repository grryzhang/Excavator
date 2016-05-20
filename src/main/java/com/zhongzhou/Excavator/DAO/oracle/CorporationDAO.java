package com.zhongzhou.Excavator.DAO.oracle;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.NC.CorporationDoc;
import com.zhongzhou.Excavator.model.NC.CorporationSearchParameters;
import com.zhongzhou.Excavator.springsupport.DataSourceList;

@DataSource( dataSource=DataSourceList.NC_ORACLE )  
@Service( "oracle.CorporationDAO" )
public interface CorporationDAO {

	public List< CorporationDoc > selectCorporations( CorporationSearchParameters searchParameters ) throws SQLException;
	
	public List< CorporationDoc > selectCorporationsWithRowNumber( 
			CorporationSearchParameters searchParameters ) throws SQLException;
	
	public Integer countCorporations ( CorporationSearchParameters searchParameters ) throws SQLException;
}
