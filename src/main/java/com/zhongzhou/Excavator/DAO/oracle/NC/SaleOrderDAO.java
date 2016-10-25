package com.zhongzhou.Excavator.DAO.oracle.NC;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.NC.SaleOrder;
import com.zhongzhou.Excavator.model.NC.SaleOrderSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@DataSource( dataSource=DataSourceList.NC_ORACLE )  
@Service ( DAOBeanNameList.oracle_nc_saleOrder )
public interface SaleOrderDAO {
	
	public List<SaleOrder> selectSaleOrders( SaleOrderSearchParameters searchParameters ) throws SQLException;
	
	public List<SaleOrder> selectSaleOrdersWithRowNumber( SaleOrderSearchParameters searchParameters ) throws SQLException;
	
}
