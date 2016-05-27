package com.zhongzhou.Excavator.UNITTEST.DAO.oracle;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequestEvent;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.oracle.NC.PriceDAO;
import com.zhongzhou.Excavator.model.NC.Price;
import com.zhongzhou.Excavator.model.NC.PriceCategory;
import com.zhongzhou.Excavator.model.NC.PriceSearchParameters;
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
			
			priceDAO = (PriceDAO)context.getBean( DAOBeanNameList.oracle_nc_price );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSelectItem() {
		
		try{
			
			//List<PriceCategory> result = priceDAO.selectPriceCategory();
			
			PriceSearchParameters searchParameters = new PriceSearchParameters();
			Integer count = priceDAO.countPrice(searchParameters);
			System.out.println( count );
			
			searchParameters = new PriceSearchParameters();
			searchParameters.setStartTS( Timestamp.valueOf("2016-4-11 00:00:00") );
			List<Price> result = priceDAO.selectPrice(searchParameters);
			
			System.out.println( result );
			System.out.println( result.size() );
			
			assertNotNull( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}