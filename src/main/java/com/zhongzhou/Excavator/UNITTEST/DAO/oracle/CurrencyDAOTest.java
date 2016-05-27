package com.zhongzhou.Excavator.UNITTEST.DAO.oracle;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.postgresql.MD.CurrencyDAO;
import com.zhongzhou.Excavator.model.CurrencyMapping;
import com.zhongzhou.Excavator.model.CurrencyMappingSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;

public class CurrencyDAOTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static CurrencyDAO currencyDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			currencyDAO = (CurrencyDAO)context.getBean( DAOBeanNameList.postgresql_md_currency );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSelectCurrencyMapping() {
		
		try{
			
			//List<PriceCategory> result = priceDAO.selectPriceCategory();
			
			CurrencyMappingSearchParameters searchParameters = new CurrencyMappingSearchParameters();
			
			List<CurrencyMapping> results  = currencyDAO.selectCurrencyMappings(searchParameters);
			
			System.out.println( results );
			System.out.println( results.size() );
			
			assertNotNull( results );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}