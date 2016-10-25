package com.zhongzhou.Excavator.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongzhou.Excavator.model.masterdata.Item;
import com.zhongzhou.Excavator.model.masterdata.ItemPackageInfo;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.Excavator.service.MD.ItemService;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.model.web.JsonResponse;

@Controller
public class ItemController {
	
	@Resource( name = ServiceNameList.MD_Item_Service )
	public ItemService itemService;

	@RequestMapping(method=RequestMethod.POST, value="/MD/item")
	public @ResponseBody JsonResponse getItems( 
			@RequestBody ItemSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();

		List<Item> items   = itemService.getItemsBySearchParameters(searchParameters);
		Integer itemsCount = itemService.getItemsCountBySearchParameters(searchParameters);

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( items );
		result.setTotalResult( itemsCount );
		
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/MD/itemPakcageInfos")
	public @ResponseBody JsonResponse getItemPackageInfos( 
			@RequestBody ItemSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();

		List<ItemPackageInfo> itemPackageInfos = itemService.getItemPackageInfos(searchParameters);

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( itemPackageInfos );
		result.setTotalResult( itemPackageInfos.size() );
		
		return result;
	}
}
