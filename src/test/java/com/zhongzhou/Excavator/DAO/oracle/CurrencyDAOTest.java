package com.zhongzhou.Excavator.DAO.oracle;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.oracle.NC.CurrencyDAO;
import com.zhongzhou.Excavator.model.NC.CurrencyRate;
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
			
			currencyDAO = (CurrencyDAO)context.getBean( DAOBeanNameList.oracle_nc_currency );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSelectCurrencyMapping() {
		
		try{
			
			CurrencyRate rate = currencyDAO.selectExchangeRate("USD", "CNY");
			
			System.out.println( rate );
			
			assertNotNull( rate );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}