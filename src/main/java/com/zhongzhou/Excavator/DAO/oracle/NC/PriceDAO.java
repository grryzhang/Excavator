package com.zhongzhou.Excavator.DAO.oracle.NC;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.NC.Price;
import com.zhongzhou.Excavator.model.NC.PriceCategory;
import com.zhongzhou.Excavator.model.NC.PriceSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@DataSource( dataSource=DataSourceList.NC_ORACLE )  
@Service ( DAOBeanNameList.oracle_nc_price )
public interface PriceDAO {

	public List<PriceCategory> selectPriceCategory() throws SQLException;
	
	public Integer countPrice( PriceSearchParameters searchParameters ) throws SQLException;
	
	public List<Price> selectPrice( PriceSearchParameters searchParameters ) throws SQLException;
	
	public List<Price> selectPriceWithRow( PriceSearchParameters searchParameters ) throws SQLException;
	
}
