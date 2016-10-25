package com.zhongzhou.Excavator.service.impl.migration.mailMonitor.DataAdaptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.DAO.mongo.BI.BIReportDAO;
import com.zhongzhou.Excavator.model.BI.BIReportMongoData;
import com.zhongzhou.Excavator.model.BI.InquiryStatus;
import com.zhongzhou.Excavator.service.migration.mailMonitor.MailDataParser;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;
import com.zhongzhou.common.util.Excel2007Util;

@Service(ServiceNameList.MIGRATION_BI_Inquire_Data_Parser)
public class BIInquiryDataParser implements MailDataParser{
	
	@Resource(name=DAOBeanNameList.mongo_bi_BIReport)
	BIReportDAO  biReportDAO;

	@Override
	public void processMailAttachment( InputStream inputStream ){
		
		XSSFWorkbook excelBook = null;
		
		try{
			
			excelBook = new XSSFWorkbook( inputStream );
			
			XSSFSheet sheet = excelBook.getSheetAt(0);
			
			if( sheet == null ){
				
				throw new ServiceRuntimeException("Wrong excel file input, no sheet there.");
			}
			
			List<InquiryStatus> inquiryStatuses = new ArrayList<InquiryStatus>(); 
			
			for (int r = 2; r < sheet.getPhysicalNumberOfRows(); r++) {

				XSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}else{
					
					InquiryStatus status = new InquiryStatus();
					
					for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
						switch ( c ) {
							case 0:
								status.setCustomerName( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 1:	
								status.setCustomerBuyer( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 2:	
								status.setBusinessUser( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 3:
								status.setInquiryNumber( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 4:
								status.setInquiryResponseTime( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 5:	
								status.setLoadingPort( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 6:
								status.setUnloadingPort( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 7:	
								status.setCurrency( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 8:	
								status.setItemNumber( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 9:	
								status.setCustomerItemNumber( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 10:	
								status.setItemNameCN( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 11:	
								status.setItemNameEN( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 12:	
								status.setItemStandard( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 13:	
								status.setPackageInfo( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 14:	
								status.setItemInfoCN( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 15:	
								status.setItemInfoEN( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 16:	
								status.setItemUnit( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 17:	
								status.setQuantityPerInquiry( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 18:	
								status.setMinimalOrderQuantity( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 19:	
								status.setTradeTerm( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 20:	
								status.setCostPrice( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 21:	
								status.setFOBPrice( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 22:	
								status.setWarranty( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 23:	
								status.setInquiryExpiryDate( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 24:	
								status.setDeliveryTime( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 25:	
								status.setPaymentType( Excel2007Util.getCellStringValue( row, c ) );
								break;
						}
					}
					inquiryStatuses.add( status );
				}
			}
			
			
			long currentTime = System.currentTimeMillis();
			List< BIReportMongoData <InquiryStatus> > statusList = new ArrayList< BIReportMongoData <InquiryStatus> >();
			
			for( InquiryStatus status : inquiryStatuses ){
				BIReportMongoData<InquiryStatus> mongoData = new BIReportMongoData<InquiryStatus>();
				mongoData.setData(status);
				mongoData.setModelClass( InquiryStatus.class );
				mongoData.setInsertTime( currentTime );
				mongoData.setModelClassName( InquiryStatus.class.getName() );
				statusList.add( mongoData );
			}
			
			biReportDAO.insertOneReports( statusList );
			
		}catch( IOException e ){
			throw new ServiceRuntimeException( e );
		}finally{
			try{
				excelBook.close();
			}catch(Exception e){
				//TODO
			}
		}
	}
}
