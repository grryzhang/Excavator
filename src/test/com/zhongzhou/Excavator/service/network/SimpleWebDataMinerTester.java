package com.zhongzhou.Excavator.service.network;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.zhongzhou.Excavator.DAO.oracle.NC.CorporationDAO;
import com.zhongzhou.Excavator.DAO.postgresql.MD.WebSourcePriceDAO;
import com.zhongzhou.Excavator.model.NC.CorporationDoc;
import com.zhongzhou.Excavator.model.NC.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.NC.ItemSearchParameters;
import com.zhongzhou.Excavator.model.NC.PriceSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.WebSourcePrice;
import com.zhongzhou.Excavator.service.impl.migration.NC.PriceServiceImpl;
import com.zhongzhou.Excavator.service.impl.network.MinerSteelPriceService;
import com.zhongzhou.Excavator.service.migration.NC.NCItemService;
import com.zhongzhou.Excavator.service.migration.NC.NCPriceService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.util.BeanUtil;
import com.zhongzhou.common.util.Http;

public class SimpleWebDataMinerTester {
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	private static MinerSteelPriceService minerSteelPriceService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			minerSteelPriceService = (MinerSteelPriceService)context.getBean( ServiceNameList.NETWORK_Miner_steel_price_service );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void sercieTest() throws FailingHttpStatusCodeException, MalformedURLException, IOException, TransformerException, DocumentException, SQLException{
		
		//minerSteelPriceService.minerData();
		testGetPage();
	}
	
	public void testGetPage() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		
		String url = "https://s.1688.com/selloffer/offer_search.htm?keywords=%C7%A7%BD%EF%B6%A5&button_click=top&earseDirect=false&n=y";
		String html = Http.simpleWebPageMiner(url);
		
		System.out.println( html );
	}
	
	//@Test
	public void testGetData() {
		
		try{

			String url = "http://www.baojia.steelcn.cn/";
			String html = Http.simpleWebPageMiner(url);
			
			TransformerFactory factory = TransformerFactory.newInstance();
			
			InputStream templateXml = this.getClass().getResourceAsStream("/Template/xml/xslt/steelPriceFromBaojiasteelcn.xml");
	        Source xslt = new StreamSource( templateXml );
	        Transformer transformer = factory.newTransformer(xslt);
	        
	        InputStream inputStream = new ByteArrayInputStream(html.getBytes());
	        Source source = new StreamSource( inputStream );
	        
	        OutputStream outputSteam = new ByteArrayOutputStream();
	        
	        transformer.transform(source, new StreamResult(outputSteam) );
	        
	        String result = outputSteam.toString();
	        
	        System.out.println( result );
	        
	        Calendar currentDate = Calendar.getInstance();
	        String yearString = String.valueOf( currentDate.get(Calendar.YEAR) );
	        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
	        	
	        	System.out.println( prices );
	        }
	        /* xml to json directly
	        ObjectMapper objectMapper = new ObjectMapper();
	        XmlMapper xmlMapper = new XmlMapper();
	        StringWriter w = new StringWriter();  
	        JsonParser jsonParser = xmlMapper.getFactory().createParser( result );
	        JsonGenerator jg = objectMapper.getFactory().createGenerator(w);  
	        while (jsonParser.nextToken() != null) {  
                jg.copyCurrentEvent(jsonParser);  
            }  
	        jsonParser.close();  
            jg.close();
            
            System.out.println( w.toString() );
            */
            
	        /* json to List<object> directly, but you should make sure the json format is correct and clean
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, WebSourcePrice.class);
            List<WebSourcePrice> price = objectMapper.readValue( w.toString() , javaType);
            
            System.out.println( price.size() );
            */
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void getPage() {
		
		try{

			String url = "http://www.baojia.steelcn.cn/";
			String html = Http.simpleWebPageMiner(url);
			
			System.out.println( html );
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testXslt() throws TransformerException{
		
		TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("D:\\temp\\xslttest.xsl"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File("D:\\temp\\xslttest.xml"));
        transformer.transform(text, new StreamResult(new File("D:\\temp\\output.xml")));
	}
}