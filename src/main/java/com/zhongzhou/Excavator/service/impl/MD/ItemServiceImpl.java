package com.zhongzhou.Excavator.service.impl.MD;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.zhongzhou.Excavator.DAO.postgresql.MD.CorporationDAO;
import com.zhongzhou.Excavator.DAO.postgresql.MD.ItemDAO;
import com.zhongzhou.Excavator.DAO.postgresql.MD.PriceDAO;
import com.zhongzhou.Excavator.model.masterdata.Corporation;
import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.Item;
import com.zhongzhou.Excavator.model.masterdata.ItemCategory;
import com.zhongzhou.Excavator.model.masterdata.ItemPackageInfo;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.Price;
import com.zhongzhou.Excavator.service.MD.CorporationService;
import com.zhongzhou.Excavator.service.MD.ItemService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;
import com.zhongzhou.common.util.BeanUtil;

@Service(ServiceNameList.MD_Item_Service)
public class ItemServiceImpl implements ItemService{
	
	@Resource ( name = DAOBeanNameList.postgresql_md_item )
	ItemDAO itemDAO;
	
	@Resource( name = ServiceNameList.MD_Corporation_Service )
	CorporationService corporationService;
	
	@Override
	public List<Item> getItemsBySearchParameters( ItemSearchParameters searchParameters ) throws ServiceRuntimeException{
		
		try {
			
			List<Item> result = itemDAO.selectItems( this.prePrcessItemSearchParameter( searchParameters ) );
			
			Set<String> corporationIds = new HashSet<String>();
			for( Item item : result ){
				
				for( Price price : item.getPrices() ){
					corporationIds.add( price.getCustomerCorpId() );
					corporationIds.add( price.getOfferCorpId() );
				}
			}
			
			CorporationSearchParameters corpSearchParameter = new CorporationSearchParameters();
			corpSearchParameter.setIds( new ArrayList<String>( corporationIds ) );
			List<Corporation> corporations = corporationService.selectCorporations(corpSearchParameter);
			
			for( Item item : result ){
				
				for( Price price : item.getPrices() ){
					
					for( Corporation corporation : corporations ){
						
						if(  price.getCustomerCorporation() != null &&  price.getOfferCorporation() != null ){
							break;
						}
						if( corporation.getId().equals(  price.getCustomerCorpId() ) ){
							price.setCustomerCorporation( corporation );
						}
						if( corporation.getId().equals(  price.getOfferCorpId() ) ){
							price.setOfferCorporation( corporation );
						}
					}
				}
			}
			
			return result;
		} catch (SQLException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override
	public List<ItemPackageInfo> getItemPackageInfos( ItemSearchParameters searchParameters ) throws ServiceRuntimeException{
		
		try {
			
			List<ItemPackageInfo> result = itemDAO.selectItemPackageInfos( searchParameters );
			
			return result;
		} catch (SQLException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override
	public Integer getItemsCountBySearchParameters( ItemSearchParameters searchParameters ) throws ServiceRuntimeException{
		
		try {
			
			Integer result = itemDAO.countItems( this.prePrcessItemSearchParameter( searchParameters ) );
			
			return result;
		} catch (SQLException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override
	public ItemSearchParameters prePrcessItemSearchParameter( ItemSearchParameters searchParameters ) throws ServiceRuntimeException{
		
		try {
			ItemSearchParameters thisSearchParameters = BeanUtil.beanCloneByJakson(searchParameters, ItemSearchParameters.class );
			
			if( thisSearchParameters.getCategoryIds() != null && thisSearchParameters.getCategoryIds().size() > 0 ){
				
				List<ItemCategory> categorys = itemDAO.selectItemCategorysDeep( thisSearchParameters.getCategoryIds() );
				List<String> categoryIds = new ArrayList<String>();
				for( ItemCategory category : categorys ){
					categoryIds.add( category.getId() );
				}
				thisSearchParameters.setCategoryIds( categoryIds );
			}
			
			return thisSearchParameters;
		} catch (SQLException | IOException  e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override
	public List<String> getAllRelatedCategoryIds( List<String> itemCategoryIds ) throws ServiceRuntimeException{
		
		try {
			
			List<String> newItemCategoryIds = new ArrayList<String>();

			List<ItemCategory> allParentCategorys = itemDAO.selectItemCategorysAllParent( itemCategoryIds );
			List<String> allParentCategoryIds = new ArrayList<String>();
			for( ItemCategory category : allParentCategorys ){
				allParentCategoryIds.add( category.getId() );
			}
			
			List<ItemCategory> relatedCategorys = itemDAO.selectItemCategorysDeep( allParentCategoryIds );
			
			for( ItemCategory category : relatedCategorys ){
				newItemCategoryIds.add( category.getId() );
			} 
			
			return newItemCategoryIds;
		} catch (SQLException  e) {
			throw new ServiceRuntimeException(e);
		}
	}
}
