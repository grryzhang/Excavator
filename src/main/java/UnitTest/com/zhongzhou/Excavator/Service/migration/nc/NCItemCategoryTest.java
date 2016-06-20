package UnitTest.com.zhongzhou.Excavator.Service.migration.nc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.oracle.NC.CorporationDAO;
import com.zhongzhou.Excavator.model.NC.CorporationDoc;
import com.zhongzhou.Excavator.model.NC.CorporationSearchParameters;
import com.zhongzhou.Excavator.service.migration.NC.NCItemCategoryService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class NCItemCategoryTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static NCItemCategoryService ncItemCategoryService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			ncItemCategoryService  = (NCItemCategoryService)context.getBean(ServiceNameList.MIGRATION_NC_ItemCategoryService);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMigration() {
		
		try{
			
			ncItemCategoryService.migrateItemCategory();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}