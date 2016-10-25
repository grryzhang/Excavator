package com.zhongzhou.Excavator.DAO.postgresql;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.postgresql.MD.ItemDAO;
import com.zhongzhou.Excavator.model.masterdata.Item;
import com.zhongzhou.Excavator.model.masterdata.ItemCategory;
import com.zhongzhou.Excavator.model.masterdata.ItemCategoryMapping;
import com.zhongzhou.Excavator.model.masterdata.ItemCategoryMappingSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.ItemCategorySearchParameters;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;

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
	
	@Test
	public void testAll(){
		this.testSelectItem();
		//this.testSelectItemCategorysDeep();
		//this.testSelectItemCategoryIdsOfCorporations();
	}
	
	//@Test
	public void testSelectItemCategoryIdsOfCorporations(){
		
		try{
			List<String> ids = new ArrayList<String>();
			ids.add("52e02867-6029-49ea-9f0d-184474d618bf");
			
			List<String> itemCategoryIds = itemDAO.selectItemCategoryIdsOfCorporations( ids );
			
			System.out.println( itemCategoryIds );
			System.out.println( itemCategoryIds.size() );
			
			assertNotNull( itemCategoryIds );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testSelectItemCategorysDeep(){
		
		try{
			List<String> ids = new ArrayList<String>();
			ids.add("184973f6-bfd6-4962-9a8a-6f3927797780");
			ids.add("e2112a64-46f2-4e1d-a161-c36555394cd6");
			
			List<ItemCategory> itemCategorys = itemDAO.selectItemCategorysDeep( ids );
			
			System.out.println( itemCategorys.size() );
			
			assertNotNull( itemCategorys );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testSelectItem(){
		
		try{
			//List<String> fuzzyQuerys = new ArrayList<String>();
			//fuzzyQuerys.add("101006");
			ItemSearchParameters searchParameters = new ItemSearchParameters();
			//searchParameters.setCategoryIds( new ArrayList<String>( Arrays.asList("56068a36-ce3e-4fd1-a672-ebb455af376c") ) );
			//searchParameters.setOrderLevel( 0 );
			//searchParameters.setPriceListCustomerCorpId( "2a8a7c1e-c9ad-43fe-8332-4818a0e5e6d5" );
			searchParameters.setItemType("X99154N");
			
			List<Item> result = itemDAO.selectItems( searchParameters );
			
			System.out.println( result );
			System.out.println( result.size() );
			
			assertNotNull( result );
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
	
	//@Test
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