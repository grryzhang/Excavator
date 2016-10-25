package com.zhongzhou.Excavator.service.migration.NC;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.oracle.NC.CorporationDAO;
import com.zhongzhou.Excavator.model.NC.CorporationDoc;
import com.zhongzhou.Excavator.model.NC.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.NC.ItemSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.Item;
import com.zhongzhou.Excavator.service.migration.NC.NCItemService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class NCItemTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static NCItemService ncItemService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			ncItemService  = (NCItemService)context.getBean(ServiceNameList.MIGRATION_NC_ItemService);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll(){
		
		this.testMigrateItemPackageInfo();
		//this.testMapItem2Category();
	}
	
	public void testMapItem2Category(){
		
		try{
			ncItemService.mapItem2Category();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testMigration() {
		
		try{
			ItemSearchParameters ncItemSearchParameter = new ItemSearchParameters();
			
			ncItemService.migrateItem( ncItemSearchParameter );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testMigrateItemPackageInfo () {
		
		try{
			ItemSearchParameters ncItemSearchParameter = new ItemSearchParameters();
			
			ncItemService.migrateItemPackageInfo( ncItemSearchParameter );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testUpdatePackageInfoFromExcel() throws FileNotFoundException{
		
		File excelFile = new File("C:/Users/zhanghuanping/Desktop/包装信息尺寸.xlsx");
		InputStream excelStream = new FileInputStream( excelFile );
		
		ncItemService.updatePackageInfoFromExcel2007(excelStream);
	}
	
	//@Test
	public void testUpdateCustomInfoFromExcel() throws FileNotFoundException{
		
		File excelFile = new File("C:/Users/zhanghuanping/Desktop/102PartRich物料单证信息更新导入表.xlsx");
		InputStream excelStream = new FileInputStream( excelFile );
		
		ncItemService.updateCustomInfoFromExcel2007(excelStream);
	}
}