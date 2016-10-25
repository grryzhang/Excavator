package com.zhongzhou.Excavator.service.impl.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.zhongzhou.Excavator.DAO.postgresql.MD.WebSourcePriceDAO;
import com.zhongzhou.Excavator.model.masterdata.WebSourcePrice;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;
import com.zhongzhou.common.util.Http;

/**
 * @author Grry Zhang
 */
@Service(ServiceNameList.NETWORK_Miner_steel_price_service)
public class MinerSteelPriceService {
	
	@Resource( name=DAOBeanNameList.postgresql_web_source_price )
	WebSourcePriceDAO webSourcePriceDAO;

	public void minerData() throws ServiceRuntimeException{

		String url = "http://www.baojia.steelcn.cn/";
		String html;
		try {
			html = Http.simpleWebPageMiner(url);

			File templateXml = ResourceUtils.getFile("classpath:Template/xml/xslt/steelPriceFromBaojiasteelcn.xml");
			
			InputStreamReader templateXmlInputSteamReader = new InputStreamReader( new FileInputStream( templateXml ) , "UTF-8" );
	        Source xslt = new StreamSource( templateXmlInputSteamReader );
	        
	        
	        InputStream htmlByteInputStream = new ByteArrayInputStream( html.getBytes( "UTF-8" ) );
	        InputStreamReader inputStreamReader = new InputStreamReader( htmlByteInputStream , "UTF-8" );
	        Source source = new StreamSource( inputStreamReader );
	        
	        OutputStream outputStream = new ByteArrayOutputStream();
	        
			TransformerFactory factory = TransformerFactory.newInstance();
	        Transformer transformer = factory.newTransformer(xslt);
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");  
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");  
	        transformer.setOutputProperty(OutputKeys.INDENT, "no");  
	        
	        transformer.transform(source, new StreamResult(outputStream) );
	        
	        String result = outputStream.toString();
	        
	        System.out.println( result );
	        
	        Calendar currentDate = Calendar.getInstance();
	        String yearString = String.valueOf( currentDate.get(Calendar.YEAR) );

	        SAXReader reader = new SAXReader();
	        Document document = reader.read( new ByteArrayInputStream(result.getBytes("UTF-8")) );
	        Element root = document.getRootElement();
	        
	        List<Element> childList = root.elements("price");
	        
	        if( childList!= null && childList.size() > 0 ){
	        	
	        	List<WebSourcePrice> prices = new ArrayList<WebSourcePrice>();
	        	for( Element element : childList ){
		        	
	        		try{
	        			
	        			WebSourcePrice price = new WebSourcePrice();
			        	price.setItemName   ( element.elementText( "itemName" ) );
			        	price.setPrice      ( new BigDecimal( element.elementText( "priceNumber" ) ) );
			        	price.setLocation   ( element.elementText( "location" ) );
			        	price.setDate       ( Timestamp.valueOf( yearString + "-" + element.elementText( "date" ) + " 00:00:00" ) ) ;
			        	price.setGroup      ( "www.baojia.steelcn.cn" );
			        	price.setSource     ( "www.baojia.steelcn.cn" );
			        	
			        	prices.add( price );
			        	
	        		}catch( NumberFormatException e ){
	        			
	        		}
		        }
	        	
	        	webSourcePriceDAO.insertWebSourcePrice( prices );
	        }
		} catch ( FailingHttpStatusCodeException e ) {
			throw new ServiceRuntimeException( e );
		} catch ( IOException e) {
			throw new ServiceRuntimeException( e );
		} catch (SQLException e) {
			throw new ServiceRuntimeException( e );
		} catch (TransformerConfigurationException e) {
			throw new ServiceRuntimeException( e );
		} catch (TransformerException e) {
			throw new ServiceRuntimeException( e );
		} catch (DocumentException e) {
			throw new ServiceRuntimeException( e );
		}
	}
}
