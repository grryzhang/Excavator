package com.zhongzhou.Excavator.UNITTEST.DAO.oracle;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.oracle.CorporationDAO;
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

	//@Test
	public void testSelectCorporations() {
		
		try{
			CorporationSearchParameters searchParameters = new CorporationSearchParameters();
			
			List< CorporationDoc > result = corporationDAO.selectCorporations( searchParameters );
			
			System.out.println( result );
			
			assertNotNull( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectCorporationsWithRowNumber() {
		
		try{
			
			CorporationSearchParameters searchParameters = new CorporationSearchParameters();
			
			searchParameters.setStart(20);
			searchParameters.setEnd(40);
			
			List< CorporationDoc > result = corporationDAO.selectCorporationsWithRowNumber( searchParameters );
			
			System.out.println( result );
			
			System.out.println( result.size() );
			
			assertNotNull( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}