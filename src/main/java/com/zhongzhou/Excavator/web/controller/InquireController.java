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

import com.zhongzhou.Excavator.DAO.mongo.MD.InquireStatusDAO.CountResult;
import com.zhongzhou.Excavator.model.BI.InquireStatusSearchParameters;
import com.zhongzhou.Excavator.model.BI.InquiryStatus;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatusHead;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatusSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.Item;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.Excavator.service.MD.InquireService;
import com.zhongzhou.Excavator.service.MD.OrderService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.model.web.JsonResponse;

@Controller
public class InquireController {

	@Resource( name =  ServiceNameList.MD_Inquire_Service )
	InquireService inquireService;
	
	@RequestMapping(method=RequestMethod.POST, value="/MD/inquireStatus")
	public @ResponseBody JsonResponse getInquireStatus( 
			@RequestBody InquireStatusSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();

		List<InquiryStatus> status = inquireService.getLatestReport(searchParameters);
		long count = inquireService.getLatestReportCount(searchParameters);

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( status );
		result.setTotalResult( count );
		
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/MD/inquireGroupCount")
	public @ResponseBody JsonResponse getInquireGroupCount( 
			@RequestBody InquireStatusSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();

		List<CountResult> status = inquireService.groupLatestReportByBuyer();

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( status );
		result.setTotalResult( status.size() );
		
		return result;
	}
}
