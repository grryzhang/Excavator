package com.zhongzhou.Excavator.DAO.postgresql;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.postgresql.MD.CorporationDAO;
import com.zhongzhou.Excavator.DAO.postgresql.MD.ItemDAO;
import com.zhongzhou.Excavator.model.masterdata.Corporation;
import com.zhongzhou.Excavator.model.masterdata.CorporationGrade;
import com.zhongzhou.Excavator.model.masterdata.CorporationIntegrationMapping;
import com.zhongzhou.Excavator.model.masterdata.CorporationIntegrationMappingSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.CorporationStatisticsSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.ItemCategory;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.common.util.BeanUtil;

public class CorporationDAOTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static CorporationDAO corporationDAO;
	private static ItemDAO itemDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			corporationDAO = context.getBean(CorporationDAO.class);	
			itemDAO = (ItemDAO)context.getBean(DAOBeanNameList.postgresql_md_item);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll(){
		//testSelectCorporation();
		//this.testSelectCorporations();
		//this.testSelectItemCorporations();
		//this.testSelectCorporationGrades();
		//this.testSelectCorporationGradesByItemCategoryIds();
		testSelectCorporationStatistics();
	}
	
	public void testSelectCorporationStatistics(){
		
		try{
			
			CorporationStatisticsSearchParameters searchParameters = new CorporationStatisticsSearchParameters();
			List<String> categoryIds = new ArrayList<String>();
			categoryIds.add("56068a36-ce3e-4fd1-a672-ebb455af376c");
			categoryIds.add("27a0797f-804f-426c-b407-f2598c44b1fa");
			searchParameters.setCategoryIds(categoryIds);
			
			List< Map<String,String> > corporations = corporationDAO.selectCorporationStatistics( searchParameters );
			
			for( Map<String,String> content : corporations ){
				System.out.println( content.get("sum") );
				System.out.println( content.get("category_id") );
				System.out.println( content.get("resource") );
			}
			
			System.out.println( corporations );
			System.out.println( corporations.size() );
			
			assertNotNull( corporations );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void testSelectCorporation(){
		
		try{
			
			CorporationSearchParameters searchParameters = new CorporationSearchParameters();
			List<String> ids = new ArrayList<String>();
			ids.add("52e02867-6029-49ea-9f0d-184474d618bf");
			searchParameters.setIds(ids);
			
			List<Corporation> corporations = corporationDAO.selectCorporations(searchParameters);
			
			System.out.println( corporations );
			System.out.println( corporations.size() );
			
			assertNotNull( corporations );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testSelectCorporationGradesByItemCategoryIds(){
		
		try{
			List<String> ids = new ArrayList<String>();
			ids.add("eae2ac68-6c26-4132-bafb-61ec680e5efc");
			ids.add("dc5df275-819b-4659-be8a-7d5fdbbcf15c");
			ids.add("27a0797f-804f-426c-b407-f2598c44b1fa");
			
			List<CorporationGrade> grades = corporationDAO.selectCorporationGradesByItemCategoryIds( ids );
			
			System.out.println( grades );
			System.out.println( grades.size() );
			
			assertNotNull( grades );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testSelectCorporationGrades() {
		
		CorporationSearchParameters searchParameters = new CorporationSearchParameters();
		
		try {
			List<CorporationGrade> grades = corporationDAO.selectCorporationGrades(searchParameters);
			
			System.out.println( grades );
			System.out.println( grades.size() );
			assertNotNull( grades );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testSelectItemCorporations() {
		
		try{
			ItemSearchParameters searchParameters = new ItemSearchParameters();
			searchParameters.setCategoryIds( new ArrayList<String>(Arrays.asList( "e2112a64-46f2-4e1d-a161-c36555394cd6")) );
			
			
			ItemSearchParameters thisSearchParameters = BeanUtil.beanCloneByJakson(searchParameters, ItemSearchParameters.class );
			
			if( thisSearchParameters.getCategoryIds() != null && thisSearchParameters.getCategoryIds().size() > 0 ){
				
				List<ItemCategory> categorys = itemDAO.selectItemCategorysDeep( thisSearchParameters.getCategoryIds() );
				List<String> categoryIds = new ArrayList<String>();
				for( ItemCategory category : categorys ){
					categoryIds.add( category.getId() );
				}
				thisSearchParameters.setCategoryIds( categoryIds );
			}
			
			
			
			searchParameters.setStart(null);
			searchParameters.setLimit(null);
			List< Corporation > result = corporationDAO.selectItemCorporations( thisSearchParameters );
			
			System.out.println( result );
			System.out.println( result.size() );
			assertNotNull( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testSelectCorporations() {
		
		try{
			CorporationIntegrationMappingSearchParameters searchParameters
				= new CorporationIntegrationMappingSearchParameters();
		
			List< CorporationIntegrationMapping > result 
				= corporationDAO.selectCorporationIntegrationMapping( searchParameters );
			
			System.out.println( result );
			
			assertNotNull( result );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}