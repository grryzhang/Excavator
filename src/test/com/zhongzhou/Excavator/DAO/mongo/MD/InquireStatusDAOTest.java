package com.zhongzhou.Excavator.DAO.mongo.MD;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.mongo.MD.InquireStatusDAO;
import com.zhongzhou.Excavator.DAO.mongo.MD.SaleOrderStatusDAO;
import com.zhongzhou.Excavator.DAO.mongo.MD.InquireStatusDAO.CountResult;
import com.zhongzhou.Excavator.DAO.oracle.NC.CorporationDAO;
import com.zhongzhou.Excavator.model.BI.BIReportMongoData;
import com.zhongzhou.Excavator.model.BI.InquireStatusSearchParameters;
import com.zhongzhou.Excavator.model.BI.InquiryStatus;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatus;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatusSearchParameters;

public class InquireStatusDAOTest {
	
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static InquireStatusDAO inquireStatusDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			inquireStatusDAO = context.getBean(InquireStatusDAO.class);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAll(){
		//this.testSelectLatestReport();
		//this.testSelectLatestReportCount();
		this.testGroupLatestReportByBuyer();
	}
	
	public void testGroupLatestReportByBuyer(){
		
		List<CountResult> results = inquireStatusDAO.groupLatestReportByBuyer();
	}
	
	//@Test
	public void testSelectLatestReport(){

		InquireStatusSearchParameters searchParameters = new InquireStatusSearchParameters();
		searchParameters.start = null;
		searchParameters.limit = null;
		//searchParameters.fuzzyQuerys = "16SI0817";
		
		List< BIReportMongoData<InquiryStatus> > result = inquireStatusDAO.selectLatestReport( searchParameters );
		
		InquiryStatus status = result.get(0).getData();
		
		System.out.println( status.getItemNameEN() );
		System.out.println( result.size() );
		
		assertNotNull( status );
	}
	
	//@Test
	public void testSelectLatestReportCount(){

		InquireStatusSearchParameters searchParameters = new InquireStatusSearchParameters();
		//searchParameters.status = "01b94344-7000-4000-8000-000553ad1002";
		
		long result = inquireStatusDAO.selectLatestReportCount( searchParameters );
		
		System.out.println( result );
		
		assertNotNull( result );
	}
}
