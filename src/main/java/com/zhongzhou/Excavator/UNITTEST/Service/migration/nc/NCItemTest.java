package com.zhongzhou.Excavator.UNITTEST.Service.migration.nc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.oracle.CorporationDAO;
import com.zhongzhou.Excavator.model.NC.CorporationDoc;
import com.zhongzhou.Excavator.model.NC.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.NC.ItemSearchParameters;
import com.zhongzhou.Excavator.service.migration.NC.NCCorporationService;
import com.zhongzhou.Excavator.service.migration.NC.NCItemCategoryService;
import com.zhongzhou.Excavator.service.migration.NC.NCItemService;

public class NCItemTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static NCItemService ncItemService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			ncItemService  = (NCItemService)context.getBean("NCItemService");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMigration() {
		
		try{
			ItemSearchParameters ncItemSearchParameter = new ItemSearchParameters();
			
			ncItemService.migrateItem( ncItemSearchParameter );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}