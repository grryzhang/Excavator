package com.zhongzhou.Excavator.service.impl.migration.NC;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.DAO.postgresql.MD.ItemDAO;
import com.zhongzhou.Excavator.DAO.postgresql.MD.CorporationDAO;
import com.zhongzhou.Excavator.DAO.postgresql.MD.CurrencyDAO;
import com.zhongzhou.Excavator.DAO.postgresql.MD.PriceDAO;
import com.zhongzhou.Excavator.Exception.ServiceRuntimeException;
import com.zhongzhou.Excavator.model.Corporation;
import com.zhongzhou.Excavator.model.CorporationIntegrationMapping;
import com.zhongzhou.Excavator.model.CorporationIntegrationMappingSearchParameters;
import com.zhongzhou.Excavator.model.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.CurrencyMapping;
import com.zhongzhou.Excavator.model.CurrencyMappingSearchParameters;
import com.zhongzhou.Excavator.model.Item;
import com.zhongzhou.Excavator.model.ItemMapping;
import com.zhongzhou.Excavator.model.ItemSearchParameters;
import com.zhongzhou.Excavator.model.ItemUnitMapping;
import com.zhongzhou.Excavator.model.ItemUnitMappingSearchParameters;
import com.zhongzhou.Excavator.model.Price;
import com.zhongzhou.Excavator.model.PriceCategory;
import com.zhongzhou.Excavator.model.PriceCategoryMapping;
import com.zhongzhou.Excavator.model.PriceCategoryMappingSearchParameters;
import com.zhongzhou.Excavator.model.PriceMapping;
import com.zhongzhou.Excavator.model.PriceMappingSearchParameters;
import com.zhongzhou.Excavator.model.NC.PriceSearchParameters;
import com.zhongzhou.Excavator.service.migration.NC.NCPriceService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

@Service(ServiceNameList.MIGRATION_NC_PriceService)
public class PriceServiceImpl implements NCPriceService{
	
	private static int batchNumber = 200;
	
	@Resource( name=DAOBeanNameList.oracle_nc_price )
	private com.zhongzhou.Excavator.DAO.oracle.NC.PriceDAO ncPriceDAO;
	
	@Resource( name=DAOBeanNameList.postgresql_md_price )
	private PriceDAO mdPriceDAO;
	
	@Resource( name=DAOBeanNameList.postgresql_md_currency )
	private CurrencyDAO mdCurrencyDAO;
	
	@Resource( name=DAOBeanNameList.postgresql_md_corporation )
	private CorporationDAO corporationDAO;
	
	@Resource ( name=DAOBeanNameList.postgresql_md_item )
	private ItemDAO mdItemDAO;
	
	@Override
	public void migratePriceCategory() throws ServiceRuntimeException {
		
		try {
			
			List<com.zhongzhou.Excavator.model.NC.PriceCategory> pendingInsert = ncPriceDAO.selectPriceCategory();
			
			if( pendingInsert!= null && pendingInsert.size() > 0 ){
			
				List<com.zhongzhou.Excavator.model.NC.PriceCategory> pendingUpdate = new ArrayList<com.zhongzhou.Excavator.model.NC.PriceCategory>();
				
				PriceCategoryMappingSearchParameters PCMSearchParameters = new PriceCategoryMappingSearchParameters();
				List<PriceCategoryMapping> priceCategoryMappings = mdPriceDAO.selectPriceCategoryMappings( PCMSearchParameters );

				/* Compare, if existed, move to update list, else, stay them in the insert list */
				for( int i=0; i<pendingInsert.size() ; i++ ){
					
					for( int j=0; j<priceCategoryMappings.size() ; j++ ){
						
						if( pendingInsert.get(i).getCPRICETARIFFID().equals( priceCategoryMappings.get(j).getSourceId() ) ){
							
							pendingUpdate.add( pendingInsert.get(i) );
							pendingInsert.get(i).setTargetId( priceCategoryMappings.get(j).getPriceCategoryId() );
							priceCategoryMappings.remove(j);
							pendingInsert.remove(i);
							i--;
							break;
						}
					}
				}
				
				if( pendingInsert!= null && pendingInsert.size() > 0 ){
					((PriceServiceImpl) AopContext.currentProxy()).addNCPriceCategorys( pendingInsert );
				}
				if( pendingUpdate!= null && pendingUpdate.size() > 0 ){
					((PriceServiceImpl) AopContext.currentProxy()).changeNCPriceCategorys( pendingUpdate );
				}
			}
			
		} catch (SQLException e) {
			
			ServiceRuntimeException exception = new ServiceRuntimeException( "Error in migrating NC Price Category. SQL exception:" , e );
			exception.fillInStackTrace();
			throw exception;
		}
	}
	
	@Override
	public void migratePrice( PriceSearchParameters searchParameters ) throws ServiceRuntimeException {
		
		try {
			
			Integer count = ncPriceDAO.countPrice(searchParameters);
			if( count <= 0 ){
				return;
			}
			
			int migrateStart = 1;
			int migrateEnd = migrateStart + batchNumber - 1;
			
			while( migrateStart < count ){
				
				/* Get data from NC, ${batchNumber} records once*/
				searchParameters.setStart(migrateStart);
				searchParameters.setEnd(migrateEnd);
				List<com.zhongzhou.Excavator.model.NC.Price> pendingMigrate = ncPriceDAO.selectPriceWithRow(searchParameters);
				/* Get data from NC, ${batchNumber} records once*/
				
				if( pendingMigrate!= null && pendingMigrate.size() > 0 ){
					
					List<com.zhongzhou.Excavator.model.NC.Price> pendingInsert = new ArrayList<com.zhongzhou.Excavator.model.NC.Price>();
					pendingInsert.addAll(pendingMigrate);
					List<com.zhongzhou.Excavator.model.NC.Price> pendingUpdate = new ArrayList<com.zhongzhou.Excavator.model.NC.Price>();
					
					/* get the mapping data, to check which one should be insert, which one should be update */
					List<String> sourceIds = new ArrayList<String>();
					for( com.zhongzhou.Excavator.model.NC.Price doc : pendingInsert ){
						sourceIds.add( doc.getPK_INVBASDOC() );
					}
					PriceMappingSearchParameters mappingSearchParameters = new PriceMappingSearchParameters();
					mappingSearchParameters.setSourceIds( sourceIds );
					List<PriceMapping> mappings = mdPriceDAO.selectPriceMappings( mappingSearchParameters );
					/* get the mapping data, to check which one should be insert, which one should be update */
					
					/* Compare, if existed, move to update list, else, stay them in the insert list */
					for( int i=0; i<pendingInsert.size() ; i++ ){
						
						for( int j=0; j<mappings.size() ; j++ ){
							
							if( pendingInsert.get(i).getCPRICETARIFF_BID().equals( mappings.get(j).getSourceId() ) ){
								
								pendingUpdate.add( pendingInsert.get(i) );
								pendingInsert.get(i).setTargetId( mappings.get(j).getPriceId() );
								mappings.remove(j);
								pendingInsert.remove(i);
								i--;
								break;
							}
						}
					}
					/* Compare, if existed, move to update list, if not, stay them in the insert list */
					
					if( pendingInsert!= null && pendingInsert.size() > 0 ){
						((PriceServiceImpl) AopContext.currentProxy()).addNCPrice( pendingInsert );
					}
					if( pendingUpdate!= null && pendingUpdate.size() > 0 ){
						//((PriceServiceImpl) AopContext.currentProxy()).changeNCPrice( pendingUpdate );
					}
				}
				
				migrateStart += batchNumber;
				migrateEnd   += batchNumber;
			}

		} catch (SQLException e) {
			
			ServiceRuntimeException exception = new ServiceRuntimeException( "Error in migrating NC Price Category. SQL exception:" , e );
			exception.fillInStackTrace();
			throw exception;
		}
	}
	
	/**
	 * @author Grry Zhang
	 */
	@Transactional(
			propagation=Propagation.REQUIRED,
			isolation=Isolation.READ_COMMITTED,
			readOnly=false,
			rollbackFor=Exception.class )
	public List<String> addNCPrice( List<com.zhongzhou.Excavator.model.NC.Price> ncPendingInsert ) throws SQLException{
		
		List<PriceMapping> priceMappings = new ArrayList<PriceMapping>();
		List<Price> prices = new ArrayList<Price>();
		List<String> insertIds = new ArrayList<String>();
		
		Timestamp currentTime = new Timestamp( System.currentTimeMillis() );  
		
	
		for( int i=0; i<ncPendingInsert.size(); i++ ){
			
			com.zhongzhou.Excavator.model.NC.Price ncDoc = ncPendingInsert.get(i);
			
			String newPriceId = UUID.randomUUID().toString();
			
			insertIds.add( newPriceId );
			
			/* Set id */
			Price price = new Price();
			price.setId( newPriceId );
			
			/* Set currency_id */
			CurrencyMappingSearchParameters searchParameters = new CurrencyMappingSearchParameters();
			searchParameters.setSourceId( ncDoc.getPK_CURRTYPE() );
			List<CurrencyMapping> currencyMappings = mdCurrencyDAO.selectCurrencyMappings(searchParameters);
			if( currencyMappings != null && currencyMappings.size() > 0 ){
				price.setCurrencyId( currencyMappings.get(0).getCurrencyId() );
			}
			
			/* Set supplier, offer, customer, price */
			CorporationIntegrationMappingSearchParameters CIMSearchParameter = new CorporationIntegrationMappingSearchParameters();
			CIMSearchParameter.setSourceId( ncDoc.getPK_CORP() );
			List<CorporationIntegrationMapping> corInterMappings = corporationDAO.selectCorporationIntegrationMapping(CIMSearchParameter);

			CIMSearchParameter = new CorporationIntegrationMappingSearchParameters();
			CIMSearchParameter.setSourceId( ncDoc.getPK_CUBASDOC() );
			List<CorporationIntegrationMapping> priceCorpMappings = corporationDAO.selectCorporationIntegrationMapping(CIMSearchParameter);
			
			
			/*
			 * "0abe02c7-3df2-4986-a4a5-1a5db681fcc2";"sale_price";"采购报价";"price.type";1
			 * "b0ac1b13-5d3f-49ca-8435-5c935473253a";"purchase_price";"参考售价";"price.type";2
			 */
			
			if( "01".equals( ncDoc.getCPRICETYPECODE() ) ){
				if( corInterMappings != null && corInterMappings.size() > 0 ){
					price.setOfferCorpId( corInterMappings.get(0).getCorporationId() );
				}
				if( priceCorpMappings != null && currencyMappings.size() > 0 ){
					price.setCustomerCorpId( priceCorpMappings.get(0).getCorporationId() );
				}
				price.setPrice( ncDoc.getNPRICE0() );
				price.setOfferTypeId( "b0ac1b13-5d3f-49ca-8435-5c935473253a" );
			}else {	
				if( currencyMappings != null && currencyMappings.size() > 0 ){
					price.setCustomerCorpId( corInterMappings.get(0).getCorporationId() );
				}
				if( priceCorpMappings != null && currencyMappings.size() > 0 ){
					price.setOfferCorpId( priceCorpMappings.get(0).getCorporationId() );
					price.setSupplierCorpId( priceCorpMappings.get(0).getCorporationId() );
				}
				price.setPrice( ncDoc.getNPRICE1() );
				price.setOfferTypeId( "0abe02c7-3df2-4986-a4a5-1a5db681fcc2" );
			}
			
			/* Set item_id */
			ItemSearchParameters itemSearchParameters = new ItemSearchParameters();
			itemSearchParameters.setSourceId( ncDoc.getPK_INVBASDOC() );
			List<ItemMapping> itemMappings = mdItemDAO.selectItemMappings( itemSearchParameters );
			if( itemMappings != null && itemMappings.size() > 0 ){
				price.setItemId( itemMappings.get(0).getItemId() );
			}
			
			price.setLastUpdateTime(currentTime);
			
			PriceCategoryMappingSearchParameters PCMSearchParameters = new PriceCategoryMappingSearchParameters();
			PCMSearchParameters.setSourceId( ncDoc.getCPRICETARIFFID() );
			List<PriceCategoryMapping> priceCategoryMappings = mdPriceDAO.selectPriceCategoryMappings( PCMSearchParameters );
			if( priceCategoryMappings != null && priceCategoryMappings.size() > 0 ){
				price.setPriceCategoryId( priceCategoryMappings.get(0).getPriceCategoryId() );
			}
			
			ItemUnitMappingSearchParameters IUMSearchParameters = new ItemUnitMappingSearchParameters();
			IUMSearchParameters.setSourceId( ncDoc.getPK_MEASDOC() );
			List<ItemUnitMapping> itemUnitMappings = mdItemDAO.selectItemUnitMappings( IUMSearchParameters );
			if( itemUnitMappings != null && itemUnitMappings.size() > 0 ){
				price.setUnitId( itemUnitMappings.get(0).getItemUnitId() );
			}

		
			PriceMapping priceMapping = new PriceMapping();
			priceMapping.setId( newPriceId );
			priceMapping.setDataSource( ncDoc.dataSource );
			priceMapping.setSourceId( ncDoc.getCPRICETARIFF_BID() );
			priceMapping.setPriceId( newPriceId );
			
			prices.add( price );
			priceMappings.add( priceMapping );
			
			/*prepare Mongo data*/
			ncDoc.setDocCreateTime( currentTime );
		}
		mdPriceDAO.insertPrice( prices );
		mdPriceDAO.insertPriceMapping( priceMappings );
		
		return insertIds;

		//TODO 
		//insert new corporations doc of master data
	}
	
	/**
	 * @author Grry Zhang
	 */
	@Transactional(
			propagation=Propagation.REQUIRED,
			isolation=Isolation.READ_COMMITTED,
			readOnly=false,
			rollbackFor=Exception.class )
	public List<String> addNCPriceCategorys( List<com.zhongzhou.Excavator.model.NC.PriceCategory> ncPendingInsert ) throws SQLException{
		
		List<PriceCategoryMapping> priceCategoryMappings = new ArrayList<PriceCategoryMapping>();
		List<PriceCategory> priceCategorys = new ArrayList<PriceCategory>();
		List<String> insertIds = new ArrayList<String>();
		
		Timestamp currentTime = new Timestamp( System.currentTimeMillis() );  
		
		CorporationIntegrationMappingSearchParameters searchParameters = new CorporationIntegrationMappingSearchParameters();
		ArrayList<String> sourceIds = new ArrayList<String>();
		for( int i=0; i<ncPendingInsert.size(); i++ ){
			sourceIds.add( ncPendingInsert.get(i).getPK_CORP() );
		}
		searchParameters.setSourceIds(sourceIds);
		List<CorporationIntegrationMapping> corporations = corporationDAO.selectCorporationIntegrationMapping( searchParameters );

		for( int i=0; i<ncPendingInsert.size(); i++ ){
			
			com.zhongzhou.Excavator.model.NC.PriceCategory ncDoc = ncPendingInsert.get(i);
			
			String newItemId = UUID.randomUUID().toString();
			
			insertIds.add( newItemId );
			
			PriceCategory priceCategory = new PriceCategory();
			priceCategory.setId( newItemId );
			priceCategory.setCategoryName( ncDoc.getCPRICETARIFFNAME() );
			for( CorporationIntegrationMapping mapping : corporations  ){
				if( mapping.getSourceId().equals( ncDoc.getPK_CORP() ) ){
					priceCategory.setOwnerCorpId( mapping.getCorporationId() );
				}
			}
			priceCategorys.add(priceCategory);

			PriceCategoryMapping itemMapping = new PriceCategoryMapping();
			itemMapping.setId( newItemId );
			itemMapping.setDataSource( ncDoc.dataSource );
			itemMapping.setSourceId( ncDoc.getCPRICETARIFFID() );
			itemMapping.setPriceCategoryId(newItemId);
			
			priceCategoryMappings.add( itemMapping );
			
			/*prepare Mongo data*/
			ncDoc.setDocCreateTime( currentTime );
		}
		mdPriceDAO.insertPriceCategorys(priceCategorys);
		mdPriceDAO.insertPriceCategoryMappings( priceCategoryMappings );
		
		return insertIds;

		//TODO 
		//insert new corporations doc of master data
	}
	
	/**
	 * @author Grry Zhang
	 */
	@Transactional(
			propagation=Propagation.REQUIRED,
			isolation=Isolation.READ_COMMITTED,
			readOnly=false,
			rollbackFor=Exception.class )
	public List<String> changeNCPriceCategorys( List<com.zhongzhou.Excavator.model.NC.PriceCategory> ncPendingUpdate ) throws SQLException{
		
		List<String> updateIds = new ArrayList<String>();
		
		Timestamp currentTime = new Timestamp( System.currentTimeMillis() );  
		
		List<PriceCategory> priceCategorys = mdPriceDAO.selectPriceCategorys();

		for( int i=0; i<ncPendingUpdate.size(); i++ ){
			
			com.zhongzhou.Excavator.model.NC.PriceCategory ncDoc = ncPendingUpdate.get(i);
			
			if( ncPendingUpdate.get(i).getTargetId() != null ){
				
				PriceCategory priceCategory = new PriceCategory();
				priceCategory.setId( ncPendingUpdate.get(i).getTargetId() );
				priceCategorys.add(priceCategory);
				priceCategory.setCategoryName( ncDoc.getCPRICETARIFFNAME() );
				
				updateIds.add( ncPendingUpdate.get(i).getTargetId() );
			}
			
			/*prepare Mongo data*/
			ncDoc.setDocCreateTime( currentTime );
		}
		mdPriceDAO.updatePriceCategory(priceCategorys);
		
		return updateIds;

		//TODO 
		//insert new corporations doc of master data
	}

}
