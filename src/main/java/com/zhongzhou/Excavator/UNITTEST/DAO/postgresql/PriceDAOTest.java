package com.zhongzhou.Excavator.UNITTEST.DAO.postgresql;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequestEvent;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.postgresql.MD.PriceDAO;
import com.zhongzhou.Excavator.model.PriceCategoryMapping;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;

public class PriceDAOTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static PriceDAO priceDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			priceDAO = (PriceDAO)context.getBean( DAOBeanNameList.postgresql_md_price );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSelectItem() {
		
		try{
			
			
			List<PriceCategoryMapping> result = priceDAO.selectPriceCategoryMappings( null );
			
			System.out.println( result );
			System.out.println( result.size() );
			
			assertNotNull( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}