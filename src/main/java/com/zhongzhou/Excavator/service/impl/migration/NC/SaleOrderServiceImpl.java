package com.zhongzhou.Excavator.service.impl.migration.NC;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.spi.LocationAwareLogger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Rectangle;
import com.zhongzhou.Excavator.DAO.oracle.NC.CurrencyDAO;
import com.zhongzhou.Excavator.DAO.oracle.NC.ItemDAO;
import com.zhongzhou.Excavator.DAO.oracle.NC.PriceDAO;
import com.zhongzhou.Excavator.DAO.oracle.NC.SaleOrderDAO;
import com.zhongzhou.Excavator.DAO.oracle.NC.UnitDAO;
import com.zhongzhou.Excavator.model.NC.CurrencyRate;
import com.zhongzhou.Excavator.model.NC.Item;
import com.zhongzhou.Excavator.model.NC.ItemSearchParameters;
import com.zhongzhou.Excavator.model.NC.Price;
import com.zhongzhou.Excavator.model.NC.PriceSearchParameters;
import com.zhongzhou.Excavator.model.NC.SaleOrder;
import com.zhongzhou.Excavator.model.NC.SaleOrderItem;
import com.zhongzhou.Excavator.model.NC.SaleOrderSearchParameters;
import com.zhongzhou.Excavator.model.NC.Unit;
import com.zhongzhou.Excavator.model.common.OrderBy;
import com.zhongzhou.Excavator.service.migration.NC.SaleOrderService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;
import com.zhongzhou.common.model.Mail;
import com.zhongzhou.common.util.BeanUtil;
import com.zhongzhou.common.util.Http;
import com.zhongzhou.common.util.MailUtil;


@Service(ServiceNameList.MIGRATION_NC_SaleOrderService)
public class SaleOrderServiceImpl implements SaleOrderService {
	
	private Logger logger = Logger.getLogger( SaleOrderServiceImpl.class );  
	
	@Resource( name = DAOBeanNameList.oracle_nc_saleOrder )
	SaleOrderDAO saleOrderDAO;
	
	@Resource( name = DAOBeanNameList.oracle_nc_item )
	ItemDAO itemDAO;
	
	@Resource( name = DAOBeanNameList.oracle_nc_price )
	PriceDAO priceDAO;
	
	@Resource( name = DAOBeanNameList.oracle_nc_currency )
	CurrencyDAO currencyDAO;	
	
	@Resource( name = DAOBeanNameList.oracle_nc_unit )
	UnitDAO unitDAO;
		
	@Override
	public void RTXPDF2SaleOrder() throws ServiceRuntimeException{
		
		try{
			
			Message[] mails = MailUtil.getMails("imap.mxhichina.com", "mail_monitor_po@zhongzhou.net", "Zz-Grp123");
			
			for( Message mail : mails ){

				List<InputStream> oneMailPDFAttachments = MailUtil.getMailAttachments( mail, "\\S*(.pdf)$");
				
				StringBuffer replyMesage = new StringBuffer();
				StringBuffer replySubject = new StringBuffer();
				StringBuffer errorReportAll = new StringBuffer();
				Boolean dataCheck = true;
				
				List<SaleOrder> pendingPostOrders = new ArrayList<SaleOrder>();
				
				for( InputStream in : oneMailPDFAttachments ){
					
					SaleOrder saleOrder = this.RTX_PDF_SaleOrder_Parser( in );
					
					StringBuffer errorReport = new StringBuffer();
					List<SaleOrder> patchedSaleOrders = this.patchSaleOrderData(saleOrder,errorReport);
					
					if( patchedSaleOrders != null && patchedSaleOrders.size() > 0 && errorReport.length() <= 0 ){
						
						pendingPostOrders.addAll( patchedSaleOrders );
						
					}else{
						
						errorReportAll.append( errorReport );
						dataCheck = false;
					}
				}
				
				if( dataCheck ){
					
					for( SaleOrder patchedSaleOrder : pendingPostOrders ){
						
						try{
							
							this.postSaleOrder2NC( patchedSaleOrder );
							
						}catch( Exception e ){
							
							replyMesage.append( "Failed to transport Sale Order:"  );
							replyMesage.append( patchedSaleOrder.getVDEF1() );
							replyMesage.append( " to NC." );
							replyMesage.append( "\r\n<br>");
							replyMesage.append( "Error Message: ");
							replyMesage.append( e.getMessage() );
							replyMesage.append( "\r\n<br>");
						}
							
						replyMesage.append( "Sale Order:"  );
						replyMesage.append( patchedSaleOrder.getVDEF1() );
						replyMesage.append( " is imported, NC number is:");
						replyMesage.append( patchedSaleOrder.getVRECEIPTCODE() );
						replyMesage.append( "\r\n<br>" );
					}	
					
					replySubject.append("The Sale Order PDF(s) is(are) processed.");
				}
				
				if( !dataCheck ){
					
					replyMesage.append( errorReportAll );
					replySubject.append("The Sale Orders in PDF have some wrong info/data, please check and retry.");
				}
				
				InternetAddress fromAddress = MailUtil.getFrom(mail);
				Mail mailReply = new Mail();
				mailReply.setMessage  ( replyMesage.toString() );
				mailReply.setReceiver ( fromAddress );
				mailReply.setSubject  ( replySubject.toString() );
				
				try{
					MailUtil.send(mailReply, "smtp.mxhichina.com", "mail_monitor_po@zhongzhou.net", "Zz-Grp123");
				}catch(Exception e){
					logger.error( e.getMessage() );
				}
				
				MailUtil.deleteMail( mail );
			}
		} catch (UnsupportedEncodingException e) {
			throw new ServiceRuntimeException(e);
		} catch (MessagingException e) {
			throw new ServiceRuntimeException(e);
		} catch (IOException e) {
			throw new ServiceRuntimeException(e);
		} catch ( SQLException e){
			throw new ServiceRuntimeException(e);
		}
	}
	
	/* 
	 * PDF获取后的数据可能需要拆分和做业务上的转换，在这里进行
	 */
	private List<SaleOrder> patchSaleOrderData( SaleOrder saleOrder, StringBuffer result ) throws SQLException {
		
		List<SaleOrder> patchedSaleOrders = new ArrayList<SaleOrder>();
		
		Date date=new Date();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		
		InputStream defaultValuePropertiesInput = this.getClass().getResourceAsStream("/properties/SaleOrderPDFDefault.properties");
		Properties defaultValueProperties = new Properties();
		try {
			defaultValueProperties.load( defaultValuePropertiesInput );
		} catch (IOException e) {
			result.append( "Default value properties file is missing or in failure. \r\n");
		}
		
		/* start : default for RTX sale order*/
		saleOrder.setCRECEIPTTYPE        ( defaultValueProperties.getProperty("head.CRECEIPTTYPE") );
		saleOrder.setCBIZTYPE            ( defaultValueProperties.getProperty("head.CBIZTYPE") );
		saleOrder.setDBILLDATE           ( dateFormater.format(date) ); //constant current time
		saleOrder.setCCUSTOMERID         ( defaultValueProperties.getProperty("head.CCUSTOMERID") );
		saleOrder.setCDEPTID             ( defaultValueProperties.getProperty("head.CDEPTID") );
        saleOrder.setCCALBODYID          ( defaultValueProperties.getProperty("head.CCALBODYID") );
        saleOrder.setCRECEIPTCUSTOMERID  ( defaultValueProperties.getProperty("head.CRECEIPTCUSTOMERID") );
        saleOrder.setCRECEIPTCORPID      ( defaultValueProperties.getProperty("head.CRECEIPTCORPID") );
        saleOrder.setNDISCOUNTRATE       ( defaultValueProperties.getProperty("head.NDISCOUNTRATE") );
        saleOrder.setDMAKEDATE           ( dateFormater.format(date) ); //constant current time
        saleOrder.setCTERMPROTOCOLID     ( defaultValueProperties.getProperty("head.CTERMPROTOCOLID"));
        
        
        ObjectMapper mapper = new ObjectMapper();
        try {
        	
			@SuppressWarnings("unchecked")
			Map<String, List<String> > employee2BuyerMap = ( Map<String, List<String> > )mapper.readValue( defaultValueProperties.getProperty("system.buyer2employee") , Map.class);
			List<String> employees = employee2BuyerMap.get( saleOrder.getVDEF11() );
			if( employees!=null && employees.size() > 0 ){
				saleOrder.setCEMPLOYEEID ( employees.get(0) );
				saleOrder.setCOPERATORID ( employees.get(0) );
			}else{
				result.append ( "SaleOrder import failed, failed to find employee by buyer:" + saleOrder.getVDEF11() + "\r\n");
			}
        } catch (IOException e) {
        	result.append ( "Wrong in getting static propoerty data. \r\n");
        }
		
        SaleOrder saleOrder1002 = null;
        SaleOrder saleOrder1003 = null;
        try{
    		saleOrder1002  = BeanUtil.beanCloneByJakson( saleOrder, SaleOrder.class );
    		saleOrder1002.setPK_CORP             ("101");
    		saleOrder1002.setCSALECORPID         ("S01");
    		saleOrder1002.setItems( new ArrayList<SaleOrderItem>() );
    		
    		
    		saleOrder1003  = BeanUtil.beanCloneByJakson( saleOrder, SaleOrder.class );
    		saleOrder1003.setPK_CORP             ("102");
    		saleOrder1003.setCSALECORPID         ("S02");
    		saleOrder1003.setItems( new ArrayList<SaleOrderItem>() );
    		/* end : default for RTX sale order*/
        }catch(Exception e){
        	result.append ( "SaleOrder import failed, failed to copy saleOrder." + e  + "\r\n");
        }
        
		CurrencyRate rate = currencyDAO.selectExchangeRate( "USD", "CNY" );
		BigDecimal RMB2USDExchangeRate = rate.getRate();
		
		for( SaleOrderItem item : saleOrder.getItems() ){
			
			ItemSearchParameters itemSearchParameters = new ItemSearchParameters();
			itemSearchParameters.setStart(0);
			itemSearchParameters.setEnd(10);
			itemSearchParameters.setInvType( item.getCINVENTORYID() );
			List<Item> items = itemDAO.selectItemsWithRowNumber( itemSearchParameters );
			
			itemSearchParameters.setStart(0);
			itemSearchParameters.setEnd(10);
			itemSearchParameters.setInvType( item.getCINVENTORYID() + " BS");
			List<Item> BCItems = itemDAO.selectItemsWithRowNumber( itemSearchParameters );
			if( BCItems != null && BCItems.size() > 0 ){
				items.addAll( BCItems );
			}
			
			if( items != null && items.size() > 0  ){
				
				Price purchasePrice = null;
				Price salePrice = null;
				
				for( Item productItem : items ){
					
					if( productItem.getINVCODE()!= null && productItem.getINVCODE().indexOf( "P" ) == 0 ){
						
						/* Search the price first, base on price currency type, decide which corp should this order item belong to. */
						PriceSearchParameters priceSearchParameters = new PriceSearchParameters();
						priceSearchParameters.setItemId( productItem.getPK_INVBASDOC() );
						List<Price> prices = priceDAO.selectPrice(priceSearchParameters);
						
						if( prices!= null && prices.size() > 0 ){
							
							List<Price> salePrices = new ArrayList<Price>();

							for( Price price : prices ){
								
								String priceTypeName      = price.getCPRICETYPENAME();
								String priceCategoryName  = price.getCPRICETARIFFNAME();
								if( priceTypeName.indexOf("采购") > 0 || priceCategoryName.indexOf("采购") > 0 ){
									purchasePrice = price;
									break;
								}
							}
							if( purchasePrice == null ){
								result.append ( "SaleOrder inport failed, no valid purchase price, inventoryId:" + item.getCINVENTORYID() + "\r\n");
								break;
							}
							
							for( Price price : prices ){
								
								String priceTypeName      = price.getCPRICETYPENAME();
								String priceCategoryName  = price.getCPRICETARIFFNAME();
								if( priceTypeName.indexOf("售") > 0 || priceCategoryName.indexOf("销售") > 0 ){	
									
									salePrices.add( price );
								}
							}
							if( salePrices.size() == 1 ){
								salePrice = salePrices.get(0);
							}else{
								for( Price price : salePrices ){
									if( price.getPK_CORP().equals( purchasePrice.getPK_CORP() ) ){
										
										if( saleOrder.getCCUSTOMERID().equals( price.getCUSTNAME() ) || saleOrder.getCCUSTOMERID().equals( price.getCUSTSHORTNAME() ) ){
											salePrice = price;
										}
									}
								}
							}
							if( salePrice == null ){
								result.append ( "SaleOrder inport failed, no valid sale price, inventoryId:" + item.getCINVENTORYID() + "\r\n" );
								break;
							}

							BigDecimal itemPrice = salePrice.getNPRICE0();
							
							if( itemPrice == null ){
								
								result.append ( "SaleOrder inport failed, no valid item price on NPRICE0, inventoryId:" + item.getCINVENTORYID() + "\r\n");
								break;
								
							}else if( item.getNORIGINALCURTAXPRICE() != null ){
								
								BigDecimal itemPDFPrice = new BigDecimal( item.getNORIGINALCURTAXPRICE() );
								//In RTX order process, all is the USD price
								
								//TODO warning, the price is differenct.
								/*
								if( itemPrice.compareTo( itemPDFPrice ) != 0 ){
									
								}
								*/
							}
							
							BigDecimal number = new BigDecimal( item.getNNUMBER() );
							BigDecimal unitPrice = itemPrice; //外币价格
							BigDecimal itemTotalPrice = unitPrice.multiply( number ).setScale( 4, BigDecimal.ROUND_HALF_UP);;
							
							BigDecimal RMBPrice = unitPrice.multiply( RMB2USDExchangeRate ).setScale( 4, BigDecimal.ROUND_HALF_UP);
							BigDecimal RMBTotal = RMBPrice.multiply( number ).setScale( 4, BigDecimal.ROUND_HALF_UP);
							
							item.setNORIGINALCURTAXPRICE        ( itemPrice.toString() );
							item.setNORIGINALCURPRICE           ( item.getNORIGINALCURTAXPRICE() );
							item.setNORIGINALCURNETPRICE        ( item.getNORIGINALCURTAXPRICE() );
							item.setNORIGINALCURTAXNETPRICE     ( item.getNORIGINALCURTAXPRICE() );
							item.setNORIGINALCURTAXMNY          ( "0" );
							item.setNORIGINALCURMNY             ( itemTotalPrice.toString() );
							item.setNORIGINALCURSUMMNY          ( itemTotalPrice.toString() );
							item.setNORIGINALCURDISCOUNTMNY     ( "0" );
							item.setNPRICE              ( RMBPrice.toString() );
							item.setNTAXPRICE           ( RMBPrice.toString() );
							item.setNNETPRICE           ( RMBPrice.toString() );
							item.setNTAXNETPRICE        ( RMBPrice.toString() );
							item.setNTAXRATE            ( "0" );
							item.setNTAXMNY             ( "0" );
							item.setNMNY                ( RMBTotal.toString() );
							item.setNSUMMNY             ( RMBTotal.toString() );
							item.setNDISCOUNTMNY        ( RMBTotal.toString() ); 
							
							
							
							if( "1003".equals( purchasePrice.getPK_CORP() ) ){
								item.setPK_CORP( "102" ); 
								saleOrder1003.getItems().add( item );
							}else if( "1002".equals( purchasePrice.getPK_CORP() ) ){
								item.setPK_CORP( "101" ); 
								saleOrder1002.getItems().add( item );
							}else{
								result.append ( "SaleOrder import failed, unexpected Item corp Unitcode:" +  productItem.getCorpUNITCODE() + "\r\n" );
							}
							
						}else{
							result.append ( "SaleOrder inport failed, no valid item price, inventoryId:" + item.getCINVENTORYID() + "\r\n" );
						}

						item.setCINVENTORYID ( productItem.getINVCODE() );//item.setCINVENTORYID ( "0406340004" );
						
						Unit unit = unitDAO.selectUnit( productItem.getPK_MEASDOC() );
						
						if( unit == null ) {
							result.append( "The Unit Code of item:" + item.getCINVENTORYID() + " is missing." + "\r\n");
						
						}else{
							if( unit.getSHORTNAME() != null ){
								item.setCUNITID( unit.getSHORTNAME() );
								
							}else {
								result.append( "The Unit Code of item:" + item.getCINVENTORYID() + " is missing." + "\r\n");
							}
						}
						
						break;
					}
				}
				
				if( purchasePrice == null || salePrice == null ){
					result.append( "SaleOrder inport failed, no valid item Price, inventoryId:" + item.getCINVENTORYID() + "\r\n");
				}
				
			}else{
				
				result.append( "SaleOrder inport failed, no valid item, inventoryId:" + item.getCINVENTORYID() + "\r\n");
			}
	        
			item.setCRECEIPTCORPID      ( defaultValueProperties.getProperty("body.CRECEIPTCORPID") );
			item.setCADVISECALBODYID    ( defaultValueProperties.getProperty("body.CADVISECALBODYID") );
			item.setDCONSIGNDATE        ( dateFormater.format(date) );
			item.setDDELIVERDATE        ( dateFormater.format(date) );
			item.setCCURRENCYTYPEID     ( defaultValueProperties.getProperty("body.CCURRENCYTYPEID") );
			item.setNITEMDISCOUNTRATE   ( defaultValueProperties.getProperty("body.NITEMDISCOUNTRATE") );
			item.setNDISCOUNTRATE       ( defaultValueProperties.getProperty("body.NDISCOUNTRATE") );
			item.setNEXCHANGEOTOBRATE   ( RMB2USDExchangeRate.toString() );
			item.setCOPERATORID         ( saleOrder.getCOPERATORID() );
		}
		
		if( saleOrder1002 != null && saleOrder1002.getItems().size() > 0 ){
			patchedSaleOrders.add( saleOrder1002 );
		}
		if( saleOrder1003 != null && saleOrder1003.getItems().size() > 0  ){
			patchedSaleOrders.add( saleOrder1003 );
		}
		
		return patchedSaleOrders;
	}
	
	/**
	 * 
	 * @param saleOrder
	 * 
	 * @return NC认可的xml 格式 sale Order 
	 * 
	 * @throws UnsupportedEncodingException 生成xml内容时，需要将内容转 UTF-8
	 * @throws DocumentException XML生成
	 * @throws SQLException
	 */
	private String transferSaleOrder2NCXml( SaleOrder saleOrder ) throws UnsupportedEncodingException, DocumentException, SQLException{
	
		SaleOrderSearchParameters searchParameters = new SaleOrderSearchParameters();
		searchParameters.setStart(0);
		searchParameters.setEnd(1);
		searchParameters.setVdef1( saleOrder.getVDEF1() );

		List<SaleOrder> existedOrders = saleOrderDAO.selectSaleOrdersWithRowNumber(searchParameters);
		if( existedOrders != null && existedOrders.size() >0 ){
			
			boolean newOne = true;
			for( SaleOrder existedOrder : existedOrders ){
				
				if( existedOrder.getPK_CORP().equals( saleOrder.getPK_CORP() ) && !"5".equals( existedOrder.getFSTATUS() )  ){
					newOne = false;
					break;
				}
			}
			if( !newOne ){
				throw new ServiceRuntimeException( "The Sale Order:" + saleOrder.getVDEF1() + " is already existed." );
			}
		}
		
		InputStream templateXml = this.getClass().getResourceAsStream("/Template/xml/NCSaleOrder.xml");
		Reader reader = new InputStreamReader( templateXml, "utf-8" );
		
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(reader);

		Element root = doc.getRootElement();
		Element head=(Element)root.selectSingleNode("so_order/so_order_head");
		Element body= (Element)root.selectSingleNode("/ufinterface/so_order/so_order_body");
		Element entryTemplate =(Element)body.selectSingleNode("entry");
		body.remove( entryTemplate );		
		
		Element nodeObj;
		
		nodeObj= (Element)head.selectSingleNode( "vreceiptcode" );
		nodeObj.setText( saleOrder.getVRECEIPTCODE() );
		
		/*From PDF*/
		nodeObj= (Element)head.selectSingleNode( "vdef1" );
		nodeObj.setText( saleOrder.getVDEF1() );
		
		nodeObj= (Element)head.selectSingleNode( "vdef7" );
		nodeObj.setText( saleOrder.getVDEF7() );
		
		nodeObj= (Element)head.selectSingleNode( "vdef9" );
		nodeObj.setText( saleOrder.getVDEF9() );
		
		nodeObj= (Element)head.selectSingleNode( "vdef10" );
		nodeObj.setText( saleOrder.getVRECEIPTCODE() );
		
		nodeObj= (Element)head.selectSingleNode( "vdef11" );
		nodeObj.setText( saleOrder.getVDEF11() );
		
		nodeObj= (Element)head.selectSingleNode( "vdef3" );
		nodeObj.setText( saleOrder.getVDEF3() );
		
		/*Default or From DB*/
		nodeObj= (Element)head.selectSingleNode( "pk_corp" );
		nodeObj.setText( saleOrder.getPK_CORP() );

		nodeObj= (Element)head.selectSingleNode( "creceipttype" );
		nodeObj.setText( saleOrder.getCRECEIPTTYPE() );
		
		nodeObj= (Element)head.selectSingleNode( "cbiztype" );
		nodeObj.setText( saleOrder.getCBIZTYPE() );

		nodeObj= (Element)head.selectSingleNode( "dbilldate" );
		nodeObj.setText( saleOrder.getDBILLDATE() );
		
		nodeObj= (Element)head.selectSingleNode( "ccustomerid" );
		nodeObj.setText( saleOrder.getCCUSTOMERID() );
		
		nodeObj= (Element)head.selectSingleNode( "cdeptid" );
		nodeObj.setText( saleOrder.getCDEPTID() );
		
		nodeObj= (Element)head.selectSingleNode( "cemployeeid" );
		nodeObj.setText( saleOrder.getCEMPLOYEEID() );
		
		nodeObj= (Element)head.selectSingleNode( "coperatorid" );
		nodeObj.setText( saleOrder.getCOPERATORID() );

		nodeObj= (Element)head.selectSingleNode( "csalecorpid" );
		nodeObj.setText( saleOrder.getCSALECORPID() );

		nodeObj= (Element)head.selectSingleNode( "ccalbodyid" );
		nodeObj.setText( saleOrder.getCCALBODYID() ); 
		
		nodeObj= (Element)head.selectSingleNode( "creceiptcustomerid" );
		nodeObj.setText( saleOrder.getCRECEIPTCUSTOMERID() ); 

		nodeObj= (Element)head.selectSingleNode( "creceiptcorpid" );
		nodeObj.setText( saleOrder.getCRECEIPTCORPID() ); 
		
		nodeObj= (Element)head.selectSingleNode( "ndiscountrate" );
		nodeObj.setText( saleOrder.getNDISCOUNTRATE() ); 
		
		nodeObj= (Element)head.selectSingleNode( "dmakedate" );
		nodeObj.setText( saleOrder.getDMAKEDATE() ); 
		
		nodeObj= (Element)head.selectSingleNode( "ctermprotocolid" );
		nodeObj.setText( saleOrder.getCTERMPROTOCOLID() ); 

		for( SaleOrderItem item : saleOrder.getItems() ){
			
			Element entry =(Element)entryTemplate.clone();

			
			/*From PDF*/
			nodeObj= (Element)entry.selectSingleNode( "crowno" );
			nodeObj.setText( item.getCROWNO() );
						
			nodeObj= (Element)entry.selectSingleNode( "cinventoryid" );
			nodeObj.setText( item.getCINVENTORYID() );
			
			nodeObj= (Element)entry.selectSingleNode( "nnumber" );
			nodeObj.setText( item.getNNUMBER() );
			
			/*Default ,From DB, or calculation */
			nodeObj= (Element)entry.selectSingleNode( "pk_corp" );
			nodeObj.setText( item.getPK_CORP() );
			
			nodeObj= (Element)entry.selectSingleNode( "creceiptcorpid" );
			nodeObj.setText( item.getCRECEIPTCORPID() );
			
			nodeObj= (Element)entry.selectSingleNode( "coperatorid" );
			nodeObj.setText( item.getCOPERATORID() );

			nodeObj= (Element)entry.selectSingleNode( "cunitid" );
			nodeObj.setText( item.getCUNITID() );
			
			nodeObj= (Element)entry.selectSingleNode( "cadvisecalbodyid" );
			nodeObj.setText( item.getCADVISECALBODYID() );
			
			nodeObj= (Element)entry.selectSingleNode( "dconsigndate" );
			nodeObj.setText( item.getDCONSIGNDATE() );

			nodeObj= (Element)entry.selectSingleNode( "ddeliverdate" );
			nodeObj.setText( item.getDDELIVERDATE() );
			
			nodeObj= (Element)entry.selectSingleNode( "ccurrencytypeid" );
			nodeObj.setText( item.getCCURRENCYTYPEID() );
			
			nodeObj= (Element)entry.selectSingleNode( "nitemdiscountrate" );
			nodeObj.setText( item.getNITEMDISCOUNTRATE() );
			
			nodeObj= (Element)entry.selectSingleNode( "ndiscountrate" );
			nodeObj.setText( item.getNDISCOUNTRATE() );

			nodeObj= (Element)entry.selectSingleNode( "nexchangeotobrate" );
			nodeObj.setText( item.getNEXCHANGEOTOBRATE() );
			
			nodeObj= (Element)entry.selectSingleNode( "noriginalcurprice" );
			nodeObj.setText( item.getNORIGINALCURPRICE() );
			
			nodeObj= (Element)entry.selectSingleNode( "noriginalcurtaxprice" );
			nodeObj.setText( item.getNORIGINALCURTAXPRICE() );
			
			nodeObj= (Element)entry.selectSingleNode( "noriginalcurnetprice" );
			nodeObj.setText( item.getNORIGINALCURNETPRICE() );
			
			nodeObj= (Element)entry.selectSingleNode( "noriginalcurtaxnetprice" );
			nodeObj.setText( item.getNORIGINALCURTAXNETPRICE() );
			
			nodeObj= (Element)entry.selectSingleNode( "noriginalcurtaxmny " );
			nodeObj.setText( item.getNORIGINALCURTAXMNY () );
			
			nodeObj= (Element)entry.selectSingleNode( "noriginalcurmny" );
			nodeObj.setText( item.getNORIGINALCURMNY() );
			
			nodeObj= (Element)entry.selectSingleNode( "noriginalcursummny" );
			nodeObj.setText( item.getNORIGINALCURSUMMNY() );
			
			nodeObj= (Element)entry.selectSingleNode( "noriginalcurdiscountmny" );
			nodeObj.setText( item.getNORIGINALCURDISCOUNTMNY() );
			
			nodeObj= (Element)entry.selectSingleNode( "nprice" );
			nodeObj.setText( item.getNPRICE() );
			
			nodeObj= (Element)entry.selectSingleNode( "ntaxprice" );
			nodeObj.setText( item.getNTAXPRICE() );
			
			nodeObj= (Element)entry.selectSingleNode( "nnetprice" );
			nodeObj.setText( item.getNNETPRICE() );
			
			nodeObj= (Element)entry.selectSingleNode( "ntaxnetprice" );
			nodeObj.setText( item.getNTAXNETPRICE() );

			nodeObj= (Element)entry.selectSingleNode( "ntaxmny" );
			nodeObj.setText( item.getNTAXMNY() );
			
			nodeObj= (Element)entry.selectSingleNode( "nmny" );
			nodeObj.setText( item.getNMNY() );
			
			nodeObj= (Element)entry.selectSingleNode( "nsummny" );
			nodeObj.setText( item.getNSUMMNY() );
			
			nodeObj= (Element)entry.selectSingleNode( "ndiscountmny" );
			nodeObj.setText( item.getNDISCOUNTMNY() );
			
			body.add( entry );
		}
		
		String xmlString = doc.asXML();
		
		System.out.println( xmlString );
		
 		return xmlString;
	}
	
	private void postSaleOrder2NC( SaleOrder saleOrder ) throws DocumentException, SQLException, IOException{

		/* start : Create the VRECEIPTCODE auto */
        InputStream defaultValuePropertiesInput = this.getClass().getResourceAsStream("/properties/SaleOrderPDFDefault.properties");
		Properties defaultValueProperties = new Properties();
		try {
			defaultValueProperties.load( defaultValuePropertiesInput );
		} catch (IOException e) {
			throw new ServiceRuntimeException( "Default value properties file is missing or in failure. ");
		}
		
		Date date=new Date();
		
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime( date ); 
        String yearForOrderId = String.valueOf( currentDate.get(Calendar.YEAR) );
        yearForOrderId = yearForOrderId.substring( yearForOrderId.length() - 2, yearForOrderId.length() );
        SaleOrderSearchParameters searchParameters = new SaleOrderSearchParameters();
        
		OrderBy orderBy = new OrderBy();
		orderBy.setIsDesc(true);
		orderBy.setName( "vreceiptcode" );
		
		searchParameters.setStart(0);
		searchParameters.setEnd(1);
		searchParameters.setVreceiptcodeLike( yearForOrderId + "PR%" );
		searchParameters.setOrderby(orderBy);
		
		String orderIndex = defaultValueProperties.getProperty("head.orderIndex");
		List<SaleOrder> theLastSaleOrder = null;
		theLastSaleOrder = saleOrderDAO.selectSaleOrdersWithRowNumber(searchParameters);

		if( theLastSaleOrder != null && theLastSaleOrder.size() >0 ){
			orderIndex = theLastSaleOrder.get(0).getVRECEIPTCODE();
			orderIndex = orderIndex.substring( 4 , 7 ); 
			orderIndex = String.valueOf( Integer.parseInt( orderIndex ) + 1 );
			orderIndex = yearForOrderId + "PR" + orderIndex + "A";
		}
		saleOrder.setVRECEIPTCODE( orderIndex );
		/* end : Create the VRECEIPTCODE auto */

		String xmlString = this.transferSaleOrder2NCXml(saleOrder);
		
		String receiver = saleOrder.getPK_CORP(); // 101,102
		String account  = "1";//From NC, static, depend on NC setting
        String resMessage = "";
        	
    	resMessage = Http.post( defaultValueProperties.getProperty("system.NCPort") + "?receiver="+receiver+"&account="+account+"",  xmlString );
    	System.out.println( resMessage );

    	Document resMessageDoc = DocumentHelper.parseText( resMessage );
    	Element resMessageRoot = resMessageDoc.getRootElement();
    	Attribute isSuccessful = resMessageRoot.attribute("successful");
    	
    	
		if( !"Y".equalsIgnoreCase( isSuccessful.getValue() ) ){
			
			throw new ServiceRuntimeException( "PDF sale order import failed at NC side, error report:" + resMessage );
		}
	}
	
	/** 
	 * 只用于将PDF解析出来，然后将内容填入SaleOrder中相应的对象中去
	 * <br> 数据是否正确，不做验证
	 * 
	 * @exception IOException PDF数据流读取错误
	 * 
	 * */
	private SaleOrder RTX_PDF_SaleOrder_Parser( InputStream in ) throws IOException{
		
		SaleOrder saleOrder = new SaleOrder();
		
		String PURCHASE_ORDER_NUMBER = "Purchase Order Number: ";
		String PURCHASE_ORDER_DATE = "Purchase Order Date: ";
		String PHONE = "Phone: ";
		String FAX = "Fax: ";
		String FAX2 = "Fax : ";
		String E_MAIL = "E-mail:";
		String FREIGHT = "Freight: ";
		String TERMS = "Terms: ";
		String EXPECTED_RECEIPT_DATE = "Expected Receipt Date: ";
		String GST = "# GST: ";
		String QST = "# QST: ";
		String BUYER = "Buyer:";
		String USER = "User: ";
		String PHONE_NO = "Phone No.: ";
		String SUBTOTAL = "Subtotal: ";
		String TOTAL = "Total: ";
		String BY = "BY: ";
		String DATE = "DATE : ";
		String BEGIN = "#LN Item No. Description Unit Quantity Receive Unit Price Total Price";
		String BEGIN1 = "Item No. UnitDescription Quantity Unit Price Total Price#LN Receive";
		String END = "PLEASE CONFIRM RECEIPT & SHIP DATE";
		String TOUBEGIN = "PURCHASE ORDER Page: 1";
		String TO = "To: ";
		String SHIP = " Ship";
		String PURCHASE_ORDER = "PURCHASE ORDER";
		String PURCHASE_ORDER_NUMBER2 = "Purchase Order Number";
		
		//Map<String, Object> map = new HashMap<String, Object>();    	     
        PDDocument document = null;        
        try {      
        	RandomAccessBufferedFileInputStream pdFileInputStream = new RandomAccessBufferedFileInputStream( in );  
            PDFParser parser = new PDFParser( pdFileInputStream );  
            parser.parse();  
            document = parser.getPDDocument();  
            
            PDFTextStripper pdfTextStripper = new PDFTextStripper();// new PDFText2HTML();
            String result1 = pdfTextStripper.getText( document );
            pdfTextStripper.setSortByPosition(true);    
            String result = pdfTextStripper.getText( document );
          	//System.out.println( result );

          	result = result.replace(PURCHASE_ORDER_NUMBER, "\n" + PURCHASE_ORDER_NUMBER);
        	result = result.replace(PURCHASE_ORDER_DATE, "\n" + PURCHASE_ORDER_DATE);
        	result = result.replace(PHONE, "\n" + PHONE);
        	result = result.replace(FAX, "\n" + FAX);
        	result = result.replace(E_MAIL, "\n" + E_MAIL);
        	result = result.replace(FREIGHT, "\n" + FREIGHT);
        	result = result.replace(TERMS, "\n" + TERMS);
        	result = result.replace(EXPECTED_RECEIPT_DATE, "\n" + EXPECTED_RECEIPT_DATE);
        	result = result.replace(GST, "\n" + GST);
        	result = result.replace(QST, "\n" + QST);
        	result = result.replace(BUYER, "\n" + BUYER);
        	result = result.replace(USER, "\n" + USER);
        	result = result.replace(PHONE_NO, "\n" + PHONE_NO);
        	result = result.replace(FAX2, "\n" + FAX2);
        	result = result.replace(SUBTOTAL, "\n" + SUBTOTAL);
        	result = result.replace(TOTAL, "\n" + TOTAL);
        	result = result.replace(BY, "\n" + BY);
        	result = result.replace(DATE, "\n" + DATE);        	
        	String[] str = result.split("\n");
        	
        	String str11 = "";
        	
        	for (String string : str) {
        		
				if (string.indexOf(PURCHASE_ORDER_NUMBER) >= 0) {
					
					saleOrder.setVDEF1( getAllNumber(string.substring(PURCHASE_ORDER_NUMBER.length()).replaceAll("\\s*", "") ) );
				} 
				if (string.indexOf(FREIGHT) >= 0) {
					
					String freight = string.substring(FREIGHT.length());
				
					InputStream defaultValuePropertiesInput = this.getClass().getResourceAsStream("/properties/SaleOrderPDFDefault.properties");
					Properties defaultValueProperties = new Properties();
					try {
						defaultValueProperties.load( defaultValuePropertiesInput );
					} catch (IOException e) {
						throw new ServiceRuntimeException( "Default value properties file is missing or in failure. ");
					}	

					ObjectMapper mapper = new ObjectMapper();
					try {
			        	
						@SuppressWarnings("unchecked")
						Map<String, String > freightFilter = ( Map<String, String> )mapper.readValue( defaultValueProperties.getProperty("head.FREIGHT.filter") , Map.class);
						
						for( String key : freightFilter.keySet() ){
							
							Pattern pattern = Pattern.compile( freightFilter.get(key) );  
							Matcher matcher = pattern.matcher( freight );
							
							if( matcher.matches() ){
								freight = key;
								break;
							}
						}
			        } catch (JsonParseException e1) {
						e1.printStackTrace();
					} catch (JsonMappingException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					if( saleOrder.getVDEF7() == null ){
						saleOrder.setVDEF7( freight );
					}
				} 
				if (string.indexOf("Ship ") >= 0 && string.indexOf(" Via:") >= 0) {
					if( saleOrder.getVDEF7() == null ){
						if(string.indexOf("Ship Via:") >= 0){
							String freight = string.substring(string.indexOf("Ship Via:") + 9);
							freight = freight.trim();
							saleOrder.setVDEF7( freight );
						} else {
							String freight = string.substring(string.indexOf("Ship ") + 5,string.indexOf(" Via:") );
							freight = freight.trim();
							saleOrder.setVDEF7( freight );
						}
					}
				}
				if (string.indexOf(BUYER) >= 0) {
					saleOrder.setVDEF11( string.substring(BUYER.length()).trim() );
					
					saleOrder.setVDEF9( string.substring(BUYER.length()).trim() );
				} 
				str11 = str11 + string + "\n";
			}

        	result1 = result1.substring(5);

        	String destination= result1.substring(result1.indexOf(PURCHASE_ORDER));
        	String[] destinations = destination.split("\n");
			saleOrder.setVDEF3(  destinations[3] );

        	saleOrder.setItems( new ArrayList<SaleOrderItem>() );
        	
            for (int i = 0; i < document.getPages().getCount(); i++) {
            	
            	PDPage page = (PDPage) document.getPages().get(i); 
            	
            	if( page != null ){
            		
            		StringWriter sw = new StringWriter(); 
            		PDFTextStripper pst = new PDFTextStripper();
            		pst.setStartPage(i+1);
            		pst.setEndPage(i+1);
            		pst.writeText(document, sw); 
            		
            		pst.setSortByPosition(false);    
                    result1 = pst.getText( document );
                    pst.setSortByPosition(true);    
                    result = pst.getText( document );
                    
                    String newOrderString = result1.substring(result1.indexOf(BEGIN1) + BEGIN1.length() + 1);
            		String[] newOrderStr = newOrderString.split("\n");

            		String newOrderString1 = result.substring(result.indexOf(BEGIN) + BEGIN.length() + 1);
            		String[] newOrderStr1 = newOrderString1.split("\n");
            		List<String> strList = new ArrayList<String>();
            		for (String string : newOrderStr) {
            			if( string == null || string.length() < 1 ) continue;
            			if( string.split(" ").length < 3 ) continue;
            			if (string.indexOf("Transferred") >= 0) continue;
            			strList.add(string);
        			}
            		List<String> strList1 = new ArrayList<String>();
            		for (String string : newOrderStr1) {
            			if( string == null || string.length() < 1 ) continue;
            			if( string.split(" ").length < 3 ) continue;
            			if (string.indexOf("Transferred") >= 0) continue;
        				strList1.add(string);
        			}
            		
            		/*
            		PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                    stripper.setSortByPosition( true );
                    Rectangle2D rect = new Rectangle2D.Double( 10, 280, 275, 60 );
                    stripper.addRegion( "class1", rect );
                    stripper.extractRegions( page );
                    System.out.println( "Text in the area:" + rect );
                    System.out.println( stripper.getTextForRegion( "class1" ) );
            		*/
            		
            		for(int index = 0;index<strList.size();index++){
            			
                    	SaleOrderItem item = new SaleOrderItem();
            			
            			String string = strList.get(index);
            			String string1 = strList1.get(index);
        				String[] string1s = string1.split(" ");
        				
        				String ln = string1s[0];
        				//System.out.println( "LN:" + ln );
        				item.setCROWNO( ln );
        				
        				if( isNumber(ln) ){
        					string = string.substring(0,string.length()-ln.length());
            				String itemno = string1s[1];
            				item.setCINVENTORYID ( itemno );
            				
            				String lastWord = string1s[ string1s.length-1 ].replaceAll( "[\\t\\n\\r]" , ""); // may be the totalPrice
            				String last2Word = string1s[ string1s.length-2 ].replaceAll( "[\\t\\n\\r]" , "");// may be the unitPrice
            				String last3Word = string1s[ string1s.length-3 ].replaceAll( "[\\t\\n\\r]" , "");// may be the number
            				
            				//Here process which one the total price, unit price , number are all fulfilled.
            				if( isDecimal(lastWord) && isDecimal(last2Word) && isDecimal(last3Word) ){
            					
            					string = string.substring(0,string.length()-itemno.length());
                				String description = string.substring(string.indexOf(".",string.indexOf(".")+1) + 3);

                				string = string.substring(0,string.length() - description.length());
                				String totalPrice = string.substring(string.indexOf(" ",string.indexOf(".")+1) + 1);
                				string = string.substring(0,string.length() - totalPrice.length());
                				
                				totalPrice = totalPrice.replaceAll("\\s*", "");
                				if( !isDecimal(totalPrice) ){
                					continue ;
                				}
                				
                				
                				//判断unit是否为空
                				if ( string.length() > 0 && !Character.isDigit(string.charAt(0))) {
                					string = string.substring(string.indexOf(" ") + 1);
                				}	
                				
                				if( string.length() > 0 ){
                					
                					String[] strings = string.split(" ");
                    				boolean b = false;
                    				String quantity = null;
                    				String unitPrice = null;
                    				
                    				while (!b) {
                    					int j = 1;
                    					String tmp = "";
                    					for(int k = 0;k< strings.length - k; k++){
                    						tmp = tmp + strings[k];
                    					}
                    					BigDecimal bd = new BigDecimal(Double.valueOf(strings[strings.length -j]) * Double.valueOf(tmp));
                    					b = Double.valueOf( totalPrice.replace(" ", "")) == bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    					if (b) {
                    						
                    						quantity = Integer.valueOf(tmp).toString();
                    						quantity = quantity.replaceAll("\\s*", "");
                    						
                    						unitPrice = strings[strings.length -j];
                    						unitPrice = unitPrice.replaceAll("\\s*", "");
                    						
                    						item.setNORIGINALCURTAXPRICE( unitPrice ); // PDF来的是否就是含税价？
                    						item.setNNUMBER( quantity );
                    					}
                    				}
                    				
                    				if( !isDecimal(quantity) )  continue ;
        	        				if( !isDecimal(unitPrice) ) continue ;
                				}
                			
            				}else if( isDecimal(lastWord) && isDecimal(last2Word) && last3Word.equals("Each") ){
            					//用来匹配某些订单没有总价，没有单价，只有数量，并且数量间带有空格，例如1 000
            					if( isZero(lastWord ) ){
            						String nnumberString =  last2Word + lastWord;
            						Integer nnumber = Integer.parseInt( nnumberString );
            						item.setNNUMBER( nnumber.toString() );
            					}
            					
            				}else if( isDecimal(lastWord) ){

            					item.setNNUMBER( lastWord );
            				}
            				
            				if( item.getNNUMBER() != null && item.getNNUMBER().length() > 0 ){
            					saleOrder.getItems().add( item );
            				}
        				}
        			}
            	}
            }

    		return saleOrder;
    		
        } finally {  
            if (in != null) {  
                try {  
                	in.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (document != null) {  
                try {  
                    document.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }
            
        }  
	}
	
	private static boolean isZero( String orginal ){
		
		Pattern pattern = Pattern.compile( "^[0]+$" );  
        Matcher isNum = pattern.matcher(orginal);  
        return isNum.matches();  
	}
	
	private static boolean isNumber( String orginal ){
		
		Pattern pattern = Pattern.compile( "\\d+" );  
        Matcher isNum = pattern.matcher(orginal);  
        return isNum.matches();  
	}
	
    private static boolean isDecimal(String orginal){  
    	Pattern pattern = Pattern.compile( "^(\\-|\\+)?[0-9]+(\\.[0-9]+)?$" );  
        Matcher isNum = pattern.matcher(orginal);  
        return isNum.matches();  
    }
    
    private static String getAllNumber(String orginal){  
    	Pattern pattern = Pattern.compile( "[^0-9]" );  
        Matcher isNum = pattern.matcher(orginal);  
        return isNum.replaceAll("");  
    }
}
