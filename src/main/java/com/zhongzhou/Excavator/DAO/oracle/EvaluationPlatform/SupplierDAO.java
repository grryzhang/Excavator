package com.zhongzhou.Excavator.DAO.oracle.EvaluationPlatform;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.EvaluationPlatform.TDimZtree;
import com.zhongzhou.Excavator.model.EvaluationPlatform.TOmSupplier;
import com.zhongzhou.Excavator.model.EvaluationPlatform.TOmSupplierSearchParameters;
import com.zhongzhou.Excavator.model.NC.CorporationDoc;
import com.zhongzhou.Excavator.model.NC.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.CorporationGrade;
import com.zhongzhou.Excavator.model.masterdata.CorporationGradeSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@DataSource( dataSource=DataSourceList.EvaluationPlatform_ORACLE )  
@Service( DAOBeanNameList.oracle_erp_supplier )
public interface SupplierDAO {

	public void insertSuppliers( List<TOmSupplier> TOmSuppliers ) throws SQLException;
	
	public void insertSupplierZTrees( List<TDimZtree> TDimZtrees ) throws SQLException;
	
	public void updateSuppliers( List<TOmSupplier> TOmSuppliers ) throws SQLException;
	
	public void updateSupplierZTrees( List<TDimZtree> TDimZtrees ) throws SQLException;
	
	public BigInteger selectMaxNumberByParentno( @Param("FPARENTNO")  String FPARENTNO ) throws SQLException;
	
	public List<TOmSupplier> selectTOmSupplier( TOmSupplierSearchParameters searchParameters ) throws SQLException;
	
	public List<CorporationGrade> selectSupplierGrades ( CorporationGradeSearchParameters searchParameters ) throws SQLException;
}
