package com.zhongzhou.Excavator.service.MD;

import java.util.List;

import com.zhongzhou.Excavator.model.masterdata.Item;
import com.zhongzhou.Excavator.model.masterdata.ItemPackageInfo;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.common.Exception.ServiceRuntimeException;

public interface ItemService {
	
	public List<Item> getItemsBySearchParameters(ItemSearchParameters searchParameters)  throws ServiceRuntimeException;

	public Integer getItemsCountBySearchParameters(ItemSearchParameters searchParameters) throws ServiceRuntimeException;

	/**
	 * Before doing item search, some parameters need some preProcess, this interface would handle this requirement
	 * <br>Return a deep clone of input searchParameters ( in default implement, it's a JSON clone )
	 * @param searchParameters
	 * @return
	 * @throws ServiceRuntimeException
	 */
	public ItemSearchParameters prePrcessItemSearchParameter(ItemSearchParameters searchParameters) throws ServiceRuntimeException;

	public List<String> getAllRelatedCategoryIds(List<String> itemCategoryIds) throws ServiceRuntimeException;

	public List<ItemPackageInfo> getItemPackageInfos(ItemSearchParameters searchParameters) throws ServiceRuntimeException;

}
