package com.zhongzhou.Excavator.service.migration.MD;

import java.io.InputStream;
import java.util.List;

import com.zhongzhou.common.Exception.ServiceRuntimeException;



public interface CorporationDataService {
	
	public void saveCorporationInfoFromExcel2007(
			InputStream excelStream, 
			String priceOfferCorporationId,
			List<String> customerCorporationIds, 
			List<String> dummyItemIdForCategoryMapping, 
			String screenLevelId,
			String resource) throws ServiceRuntimeException;

	public void savPriceInfoFromExcel2007(
			InputStream excelStream,
			String itemCategoryId,
			String defaultItemName) throws ServiceRuntimeException;
}
