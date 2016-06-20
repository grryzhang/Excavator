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
import com.zhongzhou.Excavator.service.migration.NC.NCCorporationService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class NCCorporationTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static NCCorporationService ncCorporationService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			ncCorporationService  = (NCCorporationService)context.getBean(ServiceNameList.MIGRATION_NC_CorporationService);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMigration() {
		
		try{
			
			CorporationSearchParameters searchParameters = new CorporationSearchParameters();
			
			ncCorporationService.migrateCorporations( searchParameters );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}