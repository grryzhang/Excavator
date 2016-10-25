package com.zhongzhou.Excavator.web.controller;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongzhou.Excavator.DAO.mongo.system.ServiceLogDAO;
import com.zhongzhou.Excavator.service.impl.network.MinerSteelPriceService;
import com.zhongzhou.Excavator.service.migration.NC.NCPriceService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;
import com.zhongzhou.common.model.log.ServiceLog;
import com.zhongzhou.common.model.web.JsonResponse;

@Controller
public class WebSourceDataController {

	private static Logger log = Logger.getLogger(WebSourceDataController.class);

	@Resource( name=ServiceNameList.NETWORK_Miner_steel_price_service )
	MinerSteelPriceService minerSteelPriceService;
	
	@Resource( name=DAOBeanNameList.mongo_md_system_serviceLog )
	ServiceLogDAO serviceLogDAO;
	
	@RequestMapping(method=RequestMethod.GET, value="/webSourceDataMiner/baojiaSteelcn/{id}")
	@ResponseBody
	public ServiceLog migratePriceFromNC( 
			//@RequestBody PriceSearchParameters searchParameters,
			@PathVariable String id,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		ServiceLog serviceLog = new ServiceLog();
		String success = "Y";
		
		try{
			
			minerSteelPriceService.minerData();
			
		}catch( ServiceRuntimeException e ){
			
			success = "N";
			serviceLog.setExceptionId( e.getExceptionId() );
		}
		
		if( id == null || id.length() < 1 ) id = UUID.randomUUID().toString();
		
		serviceLog.setSuccess(success);
		serviceLog.setLogId(id);
		
		serviceLogDAO.insertLog(serviceLog);
		
		return serviceLog;
	}
}
