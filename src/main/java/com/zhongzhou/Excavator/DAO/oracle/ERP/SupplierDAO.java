package com.zhongzhou.Excavator.DAO.oracle.ERP;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.ERP.TDimZtree;
import com.zhongzhou.Excavator.model.ERP.TOmSupplier;
import com.zhongzhou.Excavator.model.ERP.TOmSupplierSearchParameters;
import com.zhongzhou.Excavator.model.NC.CorporationDoc;
import com.zhongzhou.Excavator.model.NC.CorporationSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@DataSource( dataSource=DataSourceList.ERP_ORACLE )  
@Service( DAOBeanNameList.oracle_erp_supplier )
public interface SupplierDAO {

	public void insertSuppliers( List<TOmSupplier> TOmSuppliers ) throws SQLException;
	
	public BigInteger selectMaxNumberByParentno( @Param("FPARENTNO")  String FPARENTNO ) throws SQLException;
	
	public List<TOmSupplier> selectTOmSupplier( TOmSupplierSearchParameters searchParameters ) throws SQLException;
	
	public void insertSupplierZTrees( List<TDimZtree> TDimZtrees ) throws SQLException;
}
