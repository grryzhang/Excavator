package com.zhongzhou.Excavator.service.MD;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.model.BI.BIReportMongoData;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatus;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatusHead;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatusSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.Item;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.Excavator.service.MD.ItemService;
import com.zhongzhou.Excavator.service.MD.OrderService;
import com.zhongzhou.Excavator.service.impl.migration.NC.PriceServiceImpl;
import com.zhongzhou.Excavator.service.migration.ERP.ERPSupplierService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class OrderServiceTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	public static OrderService orderService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			orderService  = (OrderService)context.getBean(  ServiceNameList.MD_Order_Service );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll(){
		
		this.testGetLatestReport();
		//this.testGetLatestCount();
	}

	//@Test
	public void testGetLatestReport() {
		
		try{
			
			SaleOrderStatusSearchParameters searchParameters = new SaleOrderStatusSearchParameters();
			searchParameters.start = null;
			searchParameters.limit = null;
			//searchParameters.fuzzyQuerys = "16SI0817";
			
			List<SaleOrderStatusHead> result = orderService.getLatestReport( searchParameters );
			
			System.out.println( result.size() );
			
			assertNotNull( result );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testGetLatestCount() {
		
		try{
			
			SaleOrderStatusSearchParameters searchParameters = new SaleOrderStatusSearchParameters();
			searchParameters.fuzzyQuerys = "16SI0817";
			
			long result = orderService.getLatestReportCount( searchParameters );
			
			System.out.println( result );
			
			assertNotNull( result );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}