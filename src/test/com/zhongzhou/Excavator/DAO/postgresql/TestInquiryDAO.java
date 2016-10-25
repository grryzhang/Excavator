package com.zhongzhou.Excavator.DAO.postgresql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	
	private static com.zhongzhou.Excavator.DAO.MSSQL.FUTONG.InquiryDAO MSSQLInquiryDAO;
	private static com.zhongzhou.Excavator.DAO.postgresql.MD.InquiryDAO MDInquiryDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			MSSQLInquiryDAO = context.getBean(com.zhongzhou.Excavator.DAO.MSSQL.FUTONG.InquiryDAO.class);	
			MDInquiryDAO    = context.getBean(com.zhongzhou.Excavator.DAO.postgresql.MD.InquiryDAO.class);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAll() throws SQLException{
		//this.testInsertInquiryFromFUTONG();
		this.testInsertAndUpdateInquiryFromFUTONG();
	} 
	
	public void testInsertInquiryFromFUTONG() throws SQLException{
		
		Integer count = MSSQLInquiryDAO.countActiveInquiryAtQuoteLevelFromFUTONG();
		InquirySearchParameters searchParameters = new InquirySearchParameters();
		searchParameters.setStart( 0 );
		searchParameters.setLimit( count );
		
		List<Inquiry> inquiries = MSSQLInquiryDAO.selectActiveInquiryFromFUTONG( searchParameters );
		
		for( Inquiry inquiry : inquiries ){
			
			String uuid = UUID.randomUUID().toString();
			
			inquiry.setId( uuid );
		}
		
		MDInquiryDAO.insertInquiries(inquiries);
	}
	
	public void testInsertAndUpdateInquiryFromFUTONG() throws SQLException{
		
		Integer count = MSSQLInquiryDAO.countActiveInquiryAtQuoteLevelFromFUTONG();
		int batch = 100;
		for( int start=0; start < count ; start=start+batch  ){
			
			InquirySearchParameters searchParameters = new InquirySearchParameters();
			searchParameters.setStart( start );
			searchParameters.setLimit( batch );
			List<Inquiry> inquiries = MSSQLInquiryDAO.selectActiveInquiryFromFUTONG( searchParameters );
			
			List<Inquiry> pendingInsert = new ArrayList<Inquiry>() ;
			List<Inquiry> pendingUpdate = new ArrayList<Inquiry>() ;
			
			List<InquiryItem> pendingInsertItems = new ArrayList<InquiryItem>() ;
			List<InquiryItem> pendingUpdateItems = new ArrayList<InquiryItem>() ;
			
			List<QuoteItem> pendingInsertQuoteItems = new ArrayList<QuoteItem>() ;
			List<QuoteItem> pendingUpdateQuoteItems = new ArrayList<QuoteItem>() ;

			List<String> inquiryNumbers = new ArrayList<String>();
			for( Inquiry inquiry : inquiries ){
				inquiryNumbers.add( inquiry.getInquiryNumber() );
			}
			InquirySearchParameters MDInquirysearchParameters = new InquirySearchParameters();
			MDInquirysearchParameters.setInquiryNumbers(inquiryNumbers);
			List<Inquiry> result =  MDInquiryDAO.selectInquiries(MDInquirysearchParameters);
			
			for( Inquiry inquiry : inquiries ){
				
				boolean isInsert = true; 
				for( Inquiry MDInquiry : result ){
					
					if( inquiry.getInquiryNumber().equals( MDInquiry.getInquiryNumber() ) ){

						inquiry.setId( MDInquiry.getId() );
						pendingUpdate.add( inquiry );
						
						for( InquiryItem inquiryItem : inquiry.getInquiryItems() ){
							
							boolean itemIsInsert = true;
							for( InquiryItem MDInquiryItem : MDInquiry.getInquiryItems() ){
								
								if( inquiryItem.getTempItemNumber().equals( MDInquiryItem.getTempItemNumber() )
									|| inquiryItem.getCustomerItemCode().equals( MDInquiryItem.getCustomerItemCode() ) ){
									
									inquiryItem.setId( MDInquiryItem.getId() );
									inquiryItem.setInquiryId( MDInquiryItem.getInquiryId() );
									pendingUpdateItems.add( inquiryItem );
									
									for( QuoteItem quoteItem : inquiryItem.getQuoteItems() ){
										
										boolean quoteItemIsInsert = true;
										for( QuoteItem MDQuoteItem : MDInquiryItem.getQuoteItems()  ){
											
											if( quoteItem.getItemCDescription()==null && MDQuoteItem.getItemCDescription()==null
												|| (quoteItem.getItemCDescription()!=null && quoteItem.getItemCDescription().equals( MDQuoteItem.getItemCDescription() ) ) ){
												
												quoteItem.setId( MDQuoteItem.getId() );
												quoteItem.setInquiryItemId( MDQuoteItem.getInquiryItemId() );
												pendingUpdateQuoteItems.add( quoteItem );
												quoteItemIsInsert = false;
												break;
											}
										}
										if( quoteItemIsInsert ){
											quoteItem.setInquiryItemId( inquiryItem.getId() );
											String uuid = UUID.randomUUID().toString();
											quoteItem.setId( uuid );
											
											pendingInsertQuoteItems.add( quoteItem );
										}
									}
									
									itemIsInsert = false;
									break;
								}
							}
							if( itemIsInsert ){
								
								inquiryItem.setInquiryId( inquiry.getId() );
								String uuid = UUID.randomUUID().toString();
								inquiryItem.setId( uuid );
								
								pendingInsertItems.add( inquiryItem );
								
								for( QuoteItem quoteItem : inquiryItem.getQuoteItems() ){
									
									quoteItem.setInquiryItemId(uuid);
									uuid = UUID.randomUUID().toString();
									quoteItem.setId( uuid );
									pendingInsertQuoteItems.add( quoteItem );
								}
							}
						}

						isInsert = false;
						break;
					}
				}
				if( isInsert ){
					
					String uuid = UUID.randomUUID().toString();
					inquiry.setId( uuid );
					pendingInsert.add( inquiry );
					
					for( InquiryItem inquiryItem : inquiry.getInquiryItems() ){
						inquiryItem.setInquiryId(uuid);
						uuid = UUID.randomUUID().toString();
						inquiryItem.setId( uuid );
						pendingInsertItems.add(inquiryItem);
						
						for( QuoteItem quoteItem : inquiryItem.getQuoteItems() ){
							
							quoteItem.setInquiryItemId(uuid);
							uuid = UUID.randomUUID().toString();
							quoteItem.setId( uuid );
							pendingInsertQuoteItems.add( quoteItem );
						}
					}
				}
			}	
			

			if( pendingInsert!= null && pendingInsert.size() > 0 ){
				MDInquiryDAO.insertInquiries(pendingInsert);
			}
			if( pendingUpdate!= null && pendingUpdate.size() > 0 ){
				MDInquiryDAO.updateInquiries(pendingUpdate);
			}
			
			if( pendingInsertItems!= null && pendingInsertItems.size() > 0 ){
				MDInquiryDAO.insertInquiryItems(pendingInsertItems);
			}
			if( pendingUpdateItems!= null && pendingUpdateItems.size() > 0 ){
				MDInquiryDAO.updateInquiryItems(pendingUpdateItems);
			}
			
		

			/*
			List<QuoteItem> pendingInsertQuoteItems = new ArrayList<QuoteItem>() ;
			List<QuoteItem> pendingUpdateQuoteItems = new ArrayList<QuoteItem>() ;
			
			for( InquiryItem insertInquiryItem : pendingInsertItems ){
				pendingInsertQuoteItems.addAll( insertInquiryItem.getQuoteItems() );
			}
			
			for( InquiryItem updateInquiryItem : pendingUpdateItems ){
				pendingUpdateQuoteItems.addAll( updateInquiryItem.getQuoteItems() );
			}
			*/
		}
	}
}