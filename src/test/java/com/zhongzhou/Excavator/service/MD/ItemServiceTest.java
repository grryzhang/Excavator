package com.zhongzhou.Excavator.service.MD;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.Item;
import com.zhongzhou.Excavator.model.masterdata.ItemPackageInfo;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.Excavator.service.MD.ItemService;
import com.zhongzhou.Excavator.service.impl.migration.NC.PriceServiceImpl;
import com.zhongzhou.Excavator.service.migration.ERP.ERPSupplierService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class ItemServiceTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	public static ItemService itemService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			itemService  = (ItemService)context.getBean(  ServiceNameList.MD_Item_Service );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll(){
		
		//this.testItemSelect();
		this.testGetItemPackageInfos();
	}
	
	public void testGetItemPackageInfos(){
		
		try{
			ItemSearchParameters searchParameters = new ItemSearchParameters();
			searchParameters.setIds( new ArrayList<String>( Arrays.asList( "35c58d15-cf1d-4f16-a85a-485196d3e871") ) );

			
			List<ItemPackageInfo> pacakgeInfos  = itemService.getItemPackageInfos(searchParameters);
			
			System.out.println( pacakgeInfos );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testItemSelect() {
		
		try{
			ItemSearchParameters searchParameters = new ItemSearchParameters();
			searchParameters.setCategoryIds( new ArrayList<String>( Arrays.asList( "e2112a64-46f2-4e1d-a161-c36555394cd6") ) );
			searchParameters.setPriceListCustomerCorpId( "2a8a7c1e-c9ad-43fe-8332-4818a0e5e6d5" );
			searchParameters.setOrderLevel( 0 );
			//searchParameters.setSupplierCorpIds( new ArrayList<String>( Arrays.asList( "52e02867-6029-49ea-9f0d-184474d618bf") ) );
			
			List<Item> items   = itemService.getItemsBySearchParameters(searchParameters);
			
			System.out.println( items );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}