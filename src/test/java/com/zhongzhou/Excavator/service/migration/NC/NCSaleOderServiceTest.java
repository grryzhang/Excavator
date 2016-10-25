package com.zhongzhou.Excavator.service.migration.NC;

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
import com.zhongzhou.Excavator.service.migration.NC.NCItemService;
import com.zhongzhou.Excavator.service.migration.NC.NCPriceService;
import com.zhongzhou.Excavator.service.migration.NC.SaleOrderService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class NCSaleOderServiceTest {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static SaleOrderService saleOrderService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();

			saleOrderService  = (SaleOrderService)context.getBean( ServiceNameList.MIGRATION_NC_SaleOrderService );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPDF2SaleOrder() {
		
		try{
			
			saleOrderService.RTXPDF2SaleOrder();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}