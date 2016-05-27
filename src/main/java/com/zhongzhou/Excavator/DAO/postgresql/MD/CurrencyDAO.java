package com.zhongzhou.Excavator.DAO.postgresql.MD;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.CurrencyMapping;
import com.zhongzhou.Excavator.model.CurrencyMappingSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@DataSource( dataSource=DataSourceList.MASTER_POSTGRESQL )  
@Service ( DAOBeanNameList.postgresql_md_currency )
public interface CurrencyDAO {

	public List<CurrencyMapping> selectCurrencyMappings( CurrencyMappingSearchParameters searchParameters ) throws SQLException;
} 
