package com.zhongzhou.Excavator.DAO.MSSQL.FUTONG;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.model.masterdata.Inquiry;
import com.zhongzhou.Excavator.model.masterdata.InquiryItem;
import com.zhongzhou.Excavator.model.masterdata.InquirySearchParameters;
import com.zhongzhou.Excavator.model.masterdata.QuoteItem;

public class TestInquiryDAO {
	
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static InquiryDAO inquiryDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			inquiryDAO = context.getBean(InquiryDAO.class);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAll() throws SQLException{
		//this.testSelectLatestReport();
		this.testSelectInquiryFromFUTONG();
	} 
	
	public void testSelectInquiryFromFUTONG() throws SQLException{
		
		InquirySearchParameters searchParameters = new InquirySearchParameters();
		searchParameters.setInquiryNumbers( new ArrayList<String>( Arrays.asList("16PRQ0006") ) );
		List<Inquiry> inquiries = inquiryDAO.selectActiveInquiryFromFUTONG( searchParameters );
		
		System.out.println( inquiries.size() );
		
		for( Inquiry inquiry : inquiries ){
			
			for( InquiryItem inquiryItem : inquiry.getInquiryItems() ){
				
				for( QuoteItem quatoItem : inquiryItem.getQuoteItems() ){
					
					System.out.println( quatoItem.getSupplierName() );
				}
			}
		}
	}
}