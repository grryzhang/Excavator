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
import com.zhongzhou.Excavator.model.NC.ItemSearchParameters;
import com.zhongzhou.Excavator.model.NC.PriceSearchParameters;
import com.zhongzhou.Excavator.service.impl.migration.NC.PriceServiceImpl;
import com.zhongzhou.Excavator.service.migration.NC.NCItemService;
import com.zhongzhou.Excavator.service.migration.NC.NCPriceService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class NCPriceTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static NCPriceService ncPriceService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			ncPriceService  = (NCPriceService)context.getBean( ServiceNameList.MIGRATION_NC_PriceService );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void testMigrationPriceCategory() {
		
		try{

			ncPriceService.migratePriceCategory();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMigrationPrice() {
		
		try{
			PriceSearchParameters searchParameters = new PriceSearchParameters();
			
			ncPriceService.migratePrice(searchParameters);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}