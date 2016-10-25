package com.zhongzhou.Excavator.service.migration.emailMonitor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.DAO.oracle.NC.CorporationDAO;
import com.zhongzhou.Excavator.model.NC.CorporationDoc;
import com.zhongzhou.Excavator.model.NC.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.NC.ItemSearchParameters;
import com.zhongzhou.Excavator.model.NC.PriceSearchParameters;
import com.zhongzhou.Excavator.service.impl.migration.NC.PriceServiceImpl;
import com.zhongzhou.Excavator.service.impl.migration.mailMonitor.MailDataMonitorService;
import com.zhongzhou.Excavator.service.migration.NC.NCItemService;
import com.zhongzhou.Excavator.service.migration.NC.NCPriceService;
import com.zhongzhou.Excavator.service.migration.NC.SaleOrderService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class MailDataMonitorServiceTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static MailDataMonitorService mailDataMonitorService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();

			mailDataMonitorService = (MailDataMonitorService)context.getBean( ServiceNameList.MIGRATION_BI_Mail_Data_Monitor_Service );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testList(){
		
		this.testBINormalOrder();
		this.testBIInquiry();
	}
	
	//@Test
	public void testBINormalOrder() {
		
		try{
			
			mailDataMonitorService.processDataInMail("BI daily business data monitor", "BI Normal Order");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testBIInquiry() {
		
		try{
			
			mailDataMonitorService.processDataInMail("BI daily business data monitor", "BI Inquiry");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}