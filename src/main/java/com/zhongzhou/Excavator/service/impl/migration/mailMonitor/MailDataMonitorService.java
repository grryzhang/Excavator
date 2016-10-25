package com.zhongzhou.Excavator.service.impl.migration.mailMonitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhongzhou.Excavator.model.NC.SaleOrder;
import com.zhongzhou.Excavator.service.migration.mailMonitor.MailDataParser;
import com.zhongzhou.Excavator.springsupport.SpringContextHolder;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.Excavator.web.controller.PriceController;
import com.zhongzhou.common.Exception.ServiceRuntimeException;
import com.zhongzhou.common.model.Mail;
import com.zhongzhou.common.util.BeanUtil;
import com.zhongzhou.common.util.MailUtil;

@Service(ServiceNameList.MIGRATION_BI_Mail_Data_Monitor_Service)
public class MailDataMonitorService {

	private static Logger logger = Logger.getLogger(MailDataMonitorService.class);
	
	private static Map<String, MailConfig> mailConfigs;
	private static Map<String, MailDataParserAdaptorConfig> mailDataParserAdaptorConfig;
	
	@Autowired
	private SpringContextHolder springContextHolder;
	
	public MailDataMonitorService(){
		
		try{
			
			if( mailConfigs == null || mailConfigs.size() < 1 ){
				
				File propertiesFile = ResourceUtils.getFile("classpath:properties/DataMonitorMails.properties");
				InputStream defaultValuePropertiesInput = new FileInputStream( propertiesFile );
				Properties defaultValueProperties = new Properties();
				defaultValueProperties.load( defaultValuePropertiesInput );
		        	
		        mailConfigs = (Map<String, MailConfig>)BeanUtil.MapJaksonUnSerializer( defaultValueProperties.getProperty("default.mail.Properties"), Map.class, String.class , MailConfig.class );
			}
			
			if( mailDataParserAdaptorConfig == null || mailDataParserAdaptorConfig.size() < 1 ){
				
				File propertiesFile = ResourceUtils.getFile("classpath:properties/DataMonitorMails.properties");
				InputStream defaultValuePropertiesInput = new FileInputStream( propertiesFile );
				Properties defaultValueProperties = new Properties();
				defaultValueProperties.load( defaultValuePropertiesInput );
		        	
				mailDataParserAdaptorConfig = (Map<String, MailDataParserAdaptorConfig>)BeanUtil.MapJaksonUnSerializer( defaultValueProperties.getProperty("default.mail.parserAdaptor"), Map.class, String.class , MailDataParserAdaptorConfig.class );
			}
		} catch (JsonParseException e1) {
			logger.error( "Wrong Mail_Data_Monitor properties. Failed in reading properties file." );
		} catch (JsonMappingException e1) {
			logger.error( "Wrong Mail_Data_Monitor properties. Failed in reading properties file." );
		} catch (IOException e1) {
			logger.error( "Wrong Mail_Data_Monitor properties. Failed in reading properties file." );
		}
	}
	
	/*
	 * EX: 
	 * <br>mailType = "BI daily business data monitor"
	 * <br>adaptorType = "BI Normal Order"
	 * 
	 * */
	public void processDataInMail( String mailType, String adaptorType ) throws ServiceRuntimeException{
		
		try {
			
			MailConfig mailConfig = this.mailConfigs.get( mailType );
			MailDataParserAdaptorConfig adaptorConfig = this.mailDataParserAdaptorConfig.get( adaptorType );
			if( mailConfig == null ){
				throw new ServiceRuntimeException( "No such configure for this mailType." );
			}
			if( adaptorConfig == null ){
				throw new ServiceRuntimeException( "No such configure for this mail adaptorType." );
			}
			
			MailDataParser mailDataParser = (MailDataParser)springContextHolder.getBean( adaptorConfig.getAdaptorServiceName() );

			Message[] mails = MailUtil.getMails( mailConfig.getImapUrl(), mailConfig.getMailAddress() , mailConfig.getPassword() );
			for( Message mail : mails ){
				
				String subject = mail.getSubject();
				if( !subject.matches( adaptorConfig.getMailNameRegular() ) ){
					continue;
				}

				List<InputStream> oneMailPDFAttachments = MailUtil.getMailAttachments( mail, adaptorConfig.getAttachmentNameRegular() );
				StringBuffer replyMesage = new StringBuffer();
				
				for( InputStream in : oneMailPDFAttachments ){
					
					mailDataParser.processMailAttachment(in);
				}
				
				MailUtil.deleteMail( mail );
			}
		} catch (UnsupportedEncodingException e) {
			throw new ServiceRuntimeException(e);
		} catch (MessagingException e) {
			throw new ServiceRuntimeException(e);
		} catch (IOException e) {
			throw new ServiceRuntimeException(e);
		}
	}
}
