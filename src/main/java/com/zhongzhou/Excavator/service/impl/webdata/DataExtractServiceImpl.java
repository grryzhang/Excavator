package com.zhongzhou.Excavator.service.impl.webdata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

import com.zhongzhou.common.Exception.ServiceRuntimeException;

import Template.Template;
import Template.TemplateNotExistedException;

public class DataExtractServiceImpl {

	public String extractXhtml2Xml( String xhtml, Template template ) throws ServiceRuntimeException{
		
		try{
			
			TransformerFactory factory = TransformerFactory.newInstance();
			
			InputStream templateXml = this.getClass().getResourceAsStream( template.getTemplatePath() );
			if( templateXml == null ){
				throw new TemplateNotExistedException("Template with path "+template.getTemplatePath()+" is not existed.");
			}
			
	        Source xslt = new StreamSource( templateXml );
	        Transformer transformer = factory.newTransformer(xslt);
	        
	        InputStream inputStream = new ByteArrayInputStream(xhtml.getBytes());
	        Source source = new StreamSource( inputStream );
	        
	        OutputStream outputSteam = new ByteArrayOutputStream();
	        
	        transformer.transform(source, new StreamResult(outputSteam) );
	        
	        String result = outputSteam.toString();
	        
	        return result;
	        
		}catch( TransformerException e){
			
			throw new ServiceRuntimeException( e );
		}
	}
}
