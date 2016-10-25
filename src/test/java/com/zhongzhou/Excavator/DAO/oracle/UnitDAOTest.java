package com.zhongzhou.Excavator.DAO.oracle;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.oracle.NC.UnitDAO;
import com.zhongzhou.Excavator.model.NC.CurrencyRate;
import com.zhongzhou.Excavator.model.NC.Unit;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;

public class UnitDAOTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static UnitDAO unitDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			unitDAO = (UnitDAO)context.getBean( DAOBeanNameList.oracle_nc_unit );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSelectCurrencyMapping() {
		
		try{
			
			Unit unit = unitDAO.selectUnit("0001A110000000000NO5");
			
			System.out.println( unit );
			
			assertNotNull( unit );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}