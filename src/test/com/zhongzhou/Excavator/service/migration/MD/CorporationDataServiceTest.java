package com.zhongzhou.Excavator.service.migration.MD;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.zhongzhou.Excavator.service.migration.MD.CorporationDataService;
import com.zhongzhou.Excavator.service.migration.NC.NCItemService;
import com.zhongzhou.Excavator.service.migration.NC.NCPriceService;
import com.zhongzhou.Excavator.service.migration.NC.SaleOrderService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class CorporationDataServiceTest {

	private static XmlWebApplicationContext context;
	private static String[] configs = { "classpath:applicationContext.xml" };

	private static CorporationDataService corporationDataService;

	@BeforeClass
	public static void configTest() {

		try {
			context = new XmlWebApplicationContext();
			context.setConfigLocations(configs);

			context.refresh();

			corporationDataService = (CorporationDataService) context
					.getBean(ServiceNameList.MD_DATA_Corporation_Service);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testList() {

		this.testSaveCorporationEnameFromExcel2007();
		//this.testSavePriceInfoFromExcel2007();
	}
	
	//@Test
	public void testSavePriceInfoFromExcel2007() {

		try {
			
			File excelFile = new File("C:/Users/zhanghuanping/Desktop/数据/SQL/供应商报价导入.xlsx");
			InputStream excelStream = new FileInputStream( excelFile );
			corporationDataService.savPriceInfoFromExcel2007(
					excelStream,                   // resource
					"27a0797f-804f-426c-b407-f2598c44b1fa",
					"TrailWheel"
			);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	// cn.made-in-china.com 中国制造
	// 1688
	// ccn.mofcom.gov.cn 中国商品网
	// CantonFair
	// @Test
	public void testSaveCorporationEnameFromExcel2007() {

		try {
			
			File excelFile = new File("C:/Users/zhanghuanping/Desktop/数据/SQL/供应商导入.xlsx");
			InputStream excelStream = new FileInputStream( excelFile );

			List<String> customerIds = new ArrayList<String>( Arrays.asList( "2a8a7c1e-c9ad-43fe-8332-4818a0e5e6d5" ) );
			List<String> dummyItemIds = new ArrayList<String>( Arrays.asList( "eaf774eb-e0d9-4ad1-94fe-c4c30bf27ce7" ) );
			
			corporationDataService.saveCorporationInfoFromExcel2007(
					excelStream,
					"4cdd74f3-d583-4370-8143-8e0dc2aa9426", // offer corporation:part rich
					customerIds,
					dummyItemIds,
					"f8b7d05d-a976-41a5-b2a1-d1f6eda6d3c9", // screen level
					"CantonFair"                            // resource
			);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}