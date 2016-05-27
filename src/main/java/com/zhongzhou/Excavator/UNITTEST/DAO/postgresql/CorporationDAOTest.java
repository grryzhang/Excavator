package com.zhongzhou.Excavator.UNITTEST.DAO.postgresql;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.postgresql.MD.CorporationDAO;
import com.zhongzhou.Excavator.model.CorporationIntegrationMapping;
import com.zhongzhou.Excavator.model.CorporationIntegrationMappingSearchParameters;
import com.zhongzhou.Excavator.model.NC.CorporationDoc;
import com.zhongzhou.Excavator.model.NC.CorporationSearchParameters;

public class CorporationDAOTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static CorporationDAO corporationDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			corporationDAO = context.getBean(CorporationDAO.class);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSelectCorporations() {
		
		try{
			CorporationIntegrationMappingSearchParameters searchParameters
				= new CorporationIntegrationMappingSearchParameters();
			
			List< CorporationIntegrationMapping > result 
				= corporationDAO.selectCorporationIntegrationMapping( searchParameters );
			
			System.out.println( result );
			
			assertNotNull( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}