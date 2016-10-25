package com.zhongzhou.Excavator.DAO.oracle;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletRequestEvent;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.oracle.NC.SaleOrderDAO;
import com.zhongzhou.Excavator.model.NC.SaleOrder;
import com.zhongzhou.Excavator.model.NC.SaleOrderSearchParameters;
import com.zhongzhou.Excavator.model.common.OrderBy;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;

public class SaleOrderDAOTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static SaleOrderDAO saleOrderDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			saleOrderDAO = (SaleOrderDAO)context.getBean( DAOBeanNameList.oracle_nc_saleOrder );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testSelectSaleOrder() {
		
		try{
			
			//List<PriceCategory> result = priceDAO.selectPriceCategory();
			
			OrderBy orderBy = new OrderBy();
			orderBy.setIsDesc(true);
			orderBy.setName( "vreceiptcode" );
			
			SaleOrderSearchParameters searchParameters = new SaleOrderSearchParameters();
			searchParameters.setStart(0);
			searchParameters.setEnd(1);
			searchParameters.setVreceiptcodeLike( "16PR%" );
			searchParameters.setOrderby(orderBy);

			List<SaleOrder> result = saleOrderDAO.selectSaleOrdersWithRowNumber(searchParameters);
			assertNotNull( result );
			
			
			Calendar currentDate = Calendar.getInstance();
	        String yearForOrderId = String.valueOf( currentDate.get(Calendar.YEAR) );
	        yearForOrderId = yearForOrderId.substring( yearForOrderId.length() - 2, yearForOrderId.length() );
			for( SaleOrder saleOrder : result ){
				String orderIndex = saleOrder.getVRECEIPTCODE();
				orderIndex = orderIndex.substring( orderIndex.indexOf( yearForOrderId + "PR" ) + 4 , orderIndex.indexOf( "A" ) );
				System.out.println( orderIndex );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}