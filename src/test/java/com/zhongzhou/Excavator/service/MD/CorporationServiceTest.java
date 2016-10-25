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
import com.zhongzhou.Excavator.model.masterdata.Corporation;
import com.zhongzhou.Excavator.model.masterdata.CorporationGrade;
import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.Item;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.Excavator.service.MD.CorporationService;
import com.zhongzhou.Excavator.service.MD.ItemService;
import com.zhongzhou.Excavator.service.impl.migration.NC.PriceServiceImpl;
import com.zhongzhou.Excavator.service.migration.ERP.ERPSupplierService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class CorporationServiceTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	public static CorporationService corporationService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			corporationService  = (CorporationService)context.getBean(  ServiceNameList.MD_Corporation_Service );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll(){
		
		//testSelectItemCorporations();
		testSelectCorporationsBySameItemCategory();
		//testSelectCorporationGrades();
		//this.testSelectCorporationGradesBySameItemCategory();
	}
	
	public void testSelectItemCorporations() {
		
		try{
			ItemSearchParameters searchParameters = new ItemSearchParameters();
			searchParameters.setCategoryIds( new ArrayList( Arrays.asList("27a0797f-804f-426c-b407-f2598c44b1fa") ) );
			//searchParameters.setOrderLevel( 0 );
			
			List<Corporation> result   = corporationService.selectItemCorporations(searchParameters);
			
			System.out.println( result.size() );
			System.out.println( result );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testSelectCorporationsBySameItemCategory(){
		
		try{
			CorporationSearchParameters searchParameters = new CorporationSearchParameters();
			searchParameters.setIds( new ArrayList<String>( Arrays.asList("52e02867-6029-49ea-9f0d-184474d618bf") ) );//宝钢
			//searchParameters.setOrderLevel( 0 );
			
			List<Corporation> result = corporationService.selectCorporationsBySameItemCategory(searchParameters);
			Long total = corporationService.countCorporationsBySameItemCategory(searchParameters);
			
			for( Corporation corporation : result ){
				System.out.println(corporation.getName());
			}
			
			System.out.println( result.size() );
			System.out.println( result );
			System.out.println( total );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testSelectCorporationGrades() {
		
		try{
			CorporationSearchParameters searchParameters = new CorporationSearchParameters();
			searchParameters.setIds( new ArrayList( Arrays.asList("52e02867-6029-49ea-9f0d-184474d618bf") ) );
			
			List<CorporationGrade> result   = corporationService.selectCorporationGrades(searchParameters);
			
			System.out.println( result.size() );
			System.out.println( result );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSelectCorporationGradesBySameItemCategory() {
		
		try{
			CorporationSearchParameters searchParameters = new CorporationSearchParameters();
			searchParameters.setIds( new ArrayList( Arrays.asList("52e02867-6029-49ea-9f0d-184474d618bf") ) );
			//searchParameters.setCorporationGroups( new ArrayList( Arrays.asList("9901") ) );
			
			List<CorporationGrade> items   = corporationService.selectCorporationGradesBySameItemCategory(searchParameters);
			
			System.out.println( items.size() );
			System.out.println( items );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}