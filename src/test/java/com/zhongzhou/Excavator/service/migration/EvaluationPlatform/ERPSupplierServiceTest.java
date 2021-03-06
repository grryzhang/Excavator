package com.zhongzhou.Excavator.service.migration.EvaluationPlatform;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.Excavator.service.impl.migration.NC.PriceServiceImpl;
import com.zhongzhou.Excavator.service.migration.ERP.ERPSupplierService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class ERPSupplierServiceTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static ERPSupplierService erpSupplierService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			erpSupplierService  = (ERPSupplierService)context.getBean( ServiceNameList.MIGRATION_EP_Supplier_Service  );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMigrationSupplier() {
		
		try{
			
			CorporationSearchParameters searchParameters = new CorporationSearchParameters();
			
			searchParameters.setLimit(null);
			searchParameters.setStart(null);

			erpSupplierService.migrateSupplierFromMD2ERP(searchParameters);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}