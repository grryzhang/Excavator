package com.zhongzhou.Excavator.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongzhou.Excavator.model.masterdata.Corporation;
import com.zhongzhou.Excavator.model.masterdata.CorporationGrade;
import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.CorporationStatisticsSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.Excavator.service.MD.CorporationService;
import com.zhongzhou.Excavator.service.MD.ItemService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.model.web.JsonResponse;

@Controller
public class CorporationController {
	
	@Resource( name = ServiceNameList.MD_Item_Service )
	public ItemService itemService;
	
	@Resource( name = ServiceNameList.MD_Corporation_Service )
	public CorporationService corporationService;

	@RequestMapping(method=RequestMethod.POST, value="/MD/Item/Corporation")
	public @ResponseBody JsonResponse getItemCorporation( 
			@RequestBody ItemSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();

		List<Corporation> corporations = corporationService.selectItemCorporations(searchParameters);

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( corporations );
		result.setTotalResult( corporations.size() );
		
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/MD/Corporation")
	public @ResponseBody JsonResponse getCorporation( 
			@RequestBody CorporationSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();

		List<Corporation> corporations = corporationService.selectCorporations(searchParameters);

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( corporations );
		result.setTotalResult( corporations.size() );
		
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/MD/CorporationGrade")
	public @ResponseBody JsonResponse getCorporationGrade( 
			@RequestBody CorporationSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();

		List<CorporationGrade> corporationGrades = corporationService.selectCorporationGrades(searchParameters);

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( corporationGrades );
		result.setTotalResult( corporationGrades.size() );
		
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/MD/CorporationsWithSameItemCategory")
	public @ResponseBody JsonResponse getCorporationsWithSameItemCategory( 
			@RequestBody CorporationSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();

		List<Corporation> corporationGrades = corporationService.selectCorporationsBySameItemCategory(searchParameters);
		Long count = corporationService.countCorporationsBySameItemCategory(searchParameters);

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( corporationGrades );
		result.setTotalResult( count );
		
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/MD/CorporationGradeWithSameItemCategory")
	public @ResponseBody JsonResponse getCorporationGradesBySameItamCategory( 
			@RequestBody CorporationSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();

		List<CorporationGrade> corporationGrades = corporationService.selectCorporationGradesBySameItemCategory( searchParameters );

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( corporationGrades );
		result.setTotalResult( corporationGrades.size() );
		
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/MD/CorporationStatistics")
	public @ResponseBody JsonResponse getCorporationStatistics( 
			@RequestBody CorporationStatisticsSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();

		List< Map<String, String> > corporationStatistics = corporationService.selectCorporationStatistics( searchParameters );

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( corporationStatistics );
		result.setTotalResult( corporationStatistics.size() );
		
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/MD/ScreenCorporation")
	public @ResponseBody JsonResponse getCorporationResource( 
			@RequestBody CorporationStatisticsSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();

		List< Corporation > resources = corporationService.selectScreenCorporation( searchParameters );

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( resources );
		result.setTotalResult( resources.size() );
		
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/MD/CorporationResource")
	public @ResponseBody JsonResponse getCorporationResource( 
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();

		List< String > resources = corporationService.selectAllCorporationSource();

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( resources );
		result.setTotalResult( resources.size() );
		
		return result;
	}
}
