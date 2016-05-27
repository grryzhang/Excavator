package com.zhongzhou.Excavator.UNITTEST.DAO.postgresql;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.postgresql.MD.ItemDAO;
import com.zhongzhou.Excavator.model.ItemCategory;
import com.zhongzhou.Excavator.model.ItemCategoryMapping;
import com.zhongzhou.Excavator.model.ItemCategoryMappingSearchParameters;
import com.zhongzhou.Excavator.model.ItemCategorySearchParameters;

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
			
			itemDAO = context.getBean(ItemDAO.class);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void testSelectSourceMapping() {
		
		try{
			
			ItemCategoryMappingSearchParameters searchParameters = new ItemCategoryMappingSearchParameters();
			
			List<ItemCategoryMapping> result = itemDAO.selectItemCategorysMapping( searchParameters );
			
			System.out.println( result );
			
			assertNotNull( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectItemCategory() {
		
		try{
			
			List<String> sourceIds = new ArrayList<String>();
			
			ItemCategorySearchParameters searchParameters = new ItemCategorySearchParameters();
			//searchParameters.setSourceIds(sourceIds);
			
			List<ItemCategory> result = itemDAO.selectItemCategorys( searchParameters );
			
			System.out.println( result );
			
			assertNotNull( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}