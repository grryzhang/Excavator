package com.zhongzhou.Excavator.service.impl.migration.mailMonitor.DataAdaptor;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.DAO.mongo.BI.BIReportDAO;
import com.zhongzhou.Excavator.model.BI.BIReportMongoData;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatus;
import com.zhongzhou.Excavator.service.migration.mailMonitor.MailDataParser;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;
import com.zhongzhou.common.util.Excel2007Util;

@Service(ServiceNameList.MIGRATION_BI_Normal_Order_Data_Parser)
public class BINormalOrderDataParser implements MailDataParser{
	
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
			
			List<SaleOrderStatus> orders = new ArrayList<SaleOrderStatus>(); 
			
			for (int r = 2; r < sheet.getPhysicalNumberOfRows(); r++) {

				XSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}else{
					
					SaleOrderStatus order = new SaleOrderStatus();
					
					for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
						switch ( c ) {
							case 0:
								order.setCustomerOrderId( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 1:	
								order.setOrderTime( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 2:	
								order.setInvoiceCode( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 3:	
								order.setBusinessUser( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 4:
								order.setCustomerName( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 5:
								order.setCustomerBuyer( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 6:	
								order.setCustomerItemId( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 7:
								order.setItemName( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 8:	
								order.setItemStandard( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 9:	
								order.setItemUnit( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 10:	
								order.setOrderQuantity( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 11:	
								order.setItemPrice( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 12:	
								order.setTotalPrice( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 13:	
								order.setTradeTerm( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 14:	
								order.setDeliveryTime( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 15:	
								order.setProductionEndTime( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 16:	
								order.setQCResult( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 17:	
								order.setQCConfirm( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 18:	
								order.setInvoiceNumber( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 19:	
								order.setShipmentBookingTime( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 20:	
								order.setLoadingPort( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 21:	
								order.setUnloadingPort( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 23:	
								order.setContainerNumber( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 22:	
								order.setShipmentType( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 24:	
								order.setShipmentTime( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 25:	
								order.setPortArrivalTime( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 27:	
								order.setInvoicedTime( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 28:	
								order.setPaymentTerm( Excel2007Util.getCellStringValue( row, c ) );
								break;
						}
					}
					orders.add( order );
				}
			}
			
			
			long currentTime = System.currentTimeMillis();
			List< BIReportMongoData <SaleOrderStatus> > statusList = new ArrayList< BIReportMongoData <SaleOrderStatus> >();
			
			for( SaleOrderStatus orderStatus : orders ){
				BIReportMongoData<SaleOrderStatus> mongoData = new BIReportMongoData<SaleOrderStatus>();
				mongoData.setData(orderStatus);
				mongoData.setModelClass( SaleOrderStatus.class );
				mongoData.setInsertTime( currentTime );
				mongoData.setModelClassName( SaleOrderStatus.class.getName() );
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
