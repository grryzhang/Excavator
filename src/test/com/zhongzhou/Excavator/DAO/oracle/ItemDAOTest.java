package com.zhongzhou.Excavator.DAO.oracle;

import static org.junit.Assert.*;

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

import com.zhongzhou.Excavator.DAO.oracle.NC.ItemDAO;
import com.zhongzhou.Excavator.model.NC.Item;
import com.zhongzhou.Excavator.model.NC.ItemCategory;
import com.zhongzhou.Excavator.model.NC.ItemCategorySearchParameters;
import com.zhongzhou.Excavator.model.NC.ItemSearchParameters;

public class ItemDAOTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static ItemDAO itemDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			MockServletContext msc = new MockServletContext();  
			context.setServletContext(msc);
			
			MockHttpServletRequest request =  new MockHttpServletRequest("POST","/index.do");   
			request.setAttribute("test", "value");
			
			RequestContextListener listener = new RequestContextListener();
			listener.requestInitialized(new ServletRequestEvent(msc, request));
			
			RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
			
			itemDAO = context.getBean(ItemDAO.class);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSelectItem() {
		
		try{
			
			ItemSearchParameters searchParameters = new ItemSearchParameters();
			
			searchParameters.setStart( 0 );
			searchParameters.setEnd( 50 );
			
			List<Item> result = itemDAO.selectItemsWithRowNumber( searchParameters );
			
			System.out.println( result );
			System.out.println( result.size() );
			
			assertNotNull( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testSelectCatagory() {
		
		try{
			
			ItemCategorySearchParameters searchParameters = new ItemCategorySearchParameters();
			
			List<ItemCategory> result = itemDAO.selectItemCategorysWithRowNumber( searchParameters );
			
			System.out.println( result );
			
			assertNotNull( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}