package com.zhongzhou.Excavator.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongzhou.Excavator.model.NC.PriceSearchParameters;
import com.zhongzhou.Excavator.service.impl.migration.NC.PriceServiceImpl;
import com.zhongzhou.Excavator.service.migration.NC.NCPriceService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.model.web.JsonResponse;

@Controller
public class PriceController {
	
	private static Logger log = Logger.getLogger(PriceController.class);
	
	@Resource(name =ServiceNameList.MIGRATION_NC_PriceService)
	NCPriceService ncPriceService;
	
	@RequestMapping(method=RequestMethod.GET, value="/integration/nc/price/{id}")
	@ResponseBody
	public JsonResponse migratePriceFromNC( 
			//@RequestBody PriceSearchParameters searchParameters,
			@PathVariable String id,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();
		
		System.out.println( id );
		
		//ncPriceService.migratePrice(searchParameters);

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		//result.setData( items );
		//result.setTotalResult( count );
		
		return result;
	}
}
