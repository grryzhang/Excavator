package com.zhongzhou.Excavator.service.webdata;


import java.io.UnsupportedEncodingException;

import javax.xml.transform.TransformerException;

import org.dom4j.DocumentException;

import com.zhongzhou.common.Exception.ServiceRuntimeException;

import Template.Template;

public interface DataExtractService {

	public String extractXhtml2Xml( String xhtml, Template template ) 
			throws ServiceRuntimeException;
}
