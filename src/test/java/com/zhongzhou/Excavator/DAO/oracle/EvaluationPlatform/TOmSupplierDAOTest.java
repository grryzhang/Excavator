package com.zhongzhou.Excavator.DAO.oracle.EvaluationPlatform;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.oracle.EvaluationPlatform.SupplierDAO;
import com.zhongzhou.Excavator.DAO.postgresql.MD.CorporationDAO;
import com.zhongzhou.Excavator.model.EvaluationPlatform.TOmSupplier;
import com.zhongzhou.Excavator.model.EvaluationPlatform.TOmSupplierSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.Corporation;
import com.zhongzhou.Excavator.model.masterdata.CorporationGrade;
import com.zhongzhou.Excavator.model.masterdata.CorporationGradeItem;
import com.zhongzhou.Excavator.model.masterdata.CorporationGradeSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.Excavator.service.impl.migration.EvaluationPlatform.ERPSupplierServiceImpl;
import com.zhongzhou.Excavator.service.migration.ERP.ERPSupplierService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;


public class TOmSupplierDAOTest {

	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static SupplierDAO supplierDAO;
	private static CorporationDAO corporationDAO;
	private static ERPSupplierService erpSupplierService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			supplierDAO     = (SupplierDAO)context.getBean( DAOBeanNameList.oracle_erp_supplier  );	
			corporationDAO  = (CorporationDAO)context.getBean( DAOBeanNameList.postgresql_md_corporation  );
			erpSupplierService = (ERPSupplierService)context.getBean( ServiceNameList.MIGRATION_EP_Supplier_Service );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll() throws Exception{
		
		this.testMigrateSupplierGrade();
	}
	
	
	public void testMigrateSupplierGrade()throws SQLException{
		
		try{
			erpSupplierService.migrateSupplierGrade2MD();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testMigrationSupplier() {
		
		try{
			
			TOmSupplierSearchParameters searchParameters = new TOmSupplierSearchParameters();
			
			searchParameters.setFPARENTNO("9907");

			List<TOmSupplier> result = supplierDAO.selectTOmSupplier(searchParameters);
			
			System.out.println( result );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
