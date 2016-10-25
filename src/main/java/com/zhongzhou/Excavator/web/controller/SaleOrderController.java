package com.zhongzhou.Excavator.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongzhou.Excavator.model.BI.SaleOrderStatusHead;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatusSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.Item;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.Excavator.service.MD.OrderService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.model.web.JsonResponse;

@Controller
public class SaleOrderController {

	@Resource( name =  ServiceNameList.MD_Order_Service )
	OrderService orderService;
	
	@RequestMapping(method=RequestMethod.POST, value="/MD/saleOrderStatus")
	public @ResponseBody JsonResponse getItemById( 
			@RequestBody SaleOrderStatusSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();

		List<SaleOrderStatusHead> status = orderService.getLatestReport(searchParameters);
		long count = orderService.getLatestReportCount(searchParameters);

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( status );
		result.setTotalResult( count );
		
		return result;
	}
}
