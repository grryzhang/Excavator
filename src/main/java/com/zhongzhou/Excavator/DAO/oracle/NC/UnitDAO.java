package com.zhongzhou.Excavator.DAO.oracle.NC;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.NC.Unit;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@DataSource( dataSource=DataSourceList.NC_ORACLE )  
@Service( DAOBeanNameList.oracle_nc_unit )
public interface UnitDAO {
	
	public Unit selectUnit( @Param( value="pk_measdoc" ) String id ) throws SQLException;
}
