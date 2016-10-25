package com.zhongzhou.Excavator.DAO.mongo.MD;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.mongo.MD.SaleOrderStatusDAO;
import com.zhongzhou.Excavator.DAO.oracle.NC.CorporationDAO;
import com.zhongzhou.Excavator.model.BI.BIReportMongoData;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatus;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatusSearchParameters;

public class SaleOrderStatusDAOTest {
	
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static SaleOrderStatusDAO saleOrderStatusDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			saleOrderStatusDAO = context.getBean(SaleOrderStatusDAO.class);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAll(){
		//this.testSelectLatestReport();
		this.testSelectLatestReportGroup();
	}
	
	//@Test
	public void testSelectLatestReport(){

		SaleOrderStatusSearchParameters searchParameters = new SaleOrderStatusSearchParameters();
		searchParameters.fuzzyQuerys = "16SI0817";
		
		List< BIReportMongoData<SaleOrderStatus> > result = saleOrderStatusDAO.selectLatestReport( searchParameters );
		
		SaleOrderStatus status = result.get(0).getData();
		
		System.out.println( status.getBusinessUser() );
		System.out.println( result.size() );
		
		assertNotNull( status );
	}
	
	//@Test
	public void testSelectLatestReportGroup(){

		SaleOrderStatusSearchParameters searchParameters = new SaleOrderStatusSearchParameters();
		searchParameters.status = "01b94344-7000-4000-8000-000553ad1002";
		
		long result = saleOrderStatusDAO.getStatusCount( searchParameters );
		
		System.out.println( result );
		
		assertNotNull( result );
	}
}
