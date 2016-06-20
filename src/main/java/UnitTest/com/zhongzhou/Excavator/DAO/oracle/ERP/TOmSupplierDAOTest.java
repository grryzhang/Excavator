package UnitTest.com.zhongzhou.Excavator.DAO.oracle.ERP;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.oracle.ERP.SupplierDAO;
import com.zhongzhou.Excavator.model.ERP.TOmSupplier;
import com.zhongzhou.Excavator.model.ERP.TOmSupplierSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;


public class TOmSupplierDAOTest {

	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static SupplierDAO supplierDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			supplierDAO  = (SupplierDAO)context.getBean( DAOBeanNameList.oracle_erp_supplier  );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMigrationSupplier() {
		
		try{
			
			TOmSupplierSearchParameters searchParameters = new TOmSupplierSearchParameters();
			
			searchParameters.setFPARENTNO("9907");

			List<TOmSupplier> result = supplierDAO.selectTOmSupplier(searchParameters);
			
			System.out.println( result );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
