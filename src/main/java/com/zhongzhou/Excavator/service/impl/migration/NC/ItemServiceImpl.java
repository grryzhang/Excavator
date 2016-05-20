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

import com.zhongzhou.Excavator.DAO.mongo.NC.NCItemDAO;
import com.zhongzhou.Excavator.Exception.ServiceRuntimeException;
import com.zhongzhou.Excavator.model.Corporation;
import com.zhongzhou.Excavator.model.CorporationIntegrationMapping;
import com.zhongzhou.Excavator.model.CorporationIntegrationMappingSearchParameters;
import com.zhongzhou.Excavator.model.ItemMapping;
import com.zhongzhou.Excavator.model.Item;
import com.zhongzhou.Excavator.model.NC.ItemSearchParameters;
import com.zhongzhou.Excavator.service.migration.NC.NCCorporationService;
import com.zhongzhou.Excavator.service.migration.NC.NCItemService;

@Service("NCItemService")
public class ItemServiceImpl implements NCItemService{
	
	@Resource(name="oracle.ItemDAO")
	com.zhongzhou.Excavator.DAO.oracle.ItemDAO ncItemDAO; 
	
	@Resource(name="postgresql.ItemDAO")
	com.zhongzhou.Excavator.DAO.postgresql.ItemDAO itemDAO;
	
	@Resource( name="mongo.NCItemDAO" )
	NCItemDAO mongoNCItemDAO;
	
	private static int batchNumber = 200;

	@Override
	public void migrateItem(ItemSearchParameters searchParameters) throws ServiceRuntimeException {
		try {
		
			Integer count = ncItemDAO.countItems( searchParameters );
			
			if( count <= 0 ){
				return;
			}
			
			int migrateStart = 1;
			int migrateEnd = migrateStart + batchNumber - 1;
			
			while( migrateStart < count ){
				
				/* Get data from NC, ${batchNumber} records once*/
				searchParameters.setStart(migrateStart);
				searchParameters.setEnd(migrateEnd);
				List<com.zhongzhou.Excavator.model.NC.Item> items = ncItemDAO.selectItemsWithRowNumber(searchParameters);
				/* Get data from NC, ${batchNumber} records once*/

				if( items!= null && items.size() > 0 ){
					
					List<String> sourceIds = new ArrayList<String>();
					
					List<com.zhongzhou.Excavator.model.NC.Item> pendingInsert = new ArrayList<com.zhongzhou.Excavator.model.NC.Item>();
					pendingInsert.addAll(items);
					List<com.zhongzhou.Excavator.model.NC.Item> pendingUpdate = new ArrayList<com.zhongzhou.Excavator.model.NC.Item>();
					
					for( com.zhongzhou.Excavator.model.NC.Item doc : items ){
						sourceIds.add( doc.getPK_INVBASDOC() );
					}
					
					com.zhongzhou.Excavator.model.ItemSearchParameters itemSearchParameters = new com.zhongzhou.Excavator.model.ItemSearchParameters();
					
					List<ItemMapping> itemSourceMappings = itemDAO.selectItemMappings(itemSearchParameters);
					
					/* Compare, if existed, move to update list, else, stay them in the insert list */
					for( int i=0; i<pendingInsert.size() ; i++ ){
						
						for( int j=0; j<itemSourceMappings.size() ; j++ ){
							
							if( pendingInsert.get(i).getPK_INVBASDOC().equals( itemSourceMappings.get(j).getSourceId() ) ){
								
								pendingUpdate.add( pendingInsert.get(i) );
								pendingInsert.get(i).setTargetId( itemSourceMappings.get(j).getItemId() );
								itemSourceMappings.remove(j);
								pendingInsert.remove(i);
								i--;
								break;
							}
						}
					}
					/* Compare, if existed, move to update list, if not, stay them in the insert list */
					
					if( pendingInsert!= null && pendingInsert.size() > 0 ){
						((ItemServiceImpl) AopContext.currentProxy()).addNCRecords( pendingInsert );
					}
					if( pendingUpdate!= null && pendingUpdate.size() > 0 ){
						((ItemServiceImpl) AopContext.currentProxy()).changeNCRecords( pendingUpdate );
					}
				}
				
				migrateStart += batchNumber;
				migrateEnd   += batchNumber;
			}
				
		} catch (SQLException e) {
			
			ServiceRuntimeException exception = new ServiceRuntimeException( "Error in migrating NC Item. SQL exception:" , e );
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
	public List<String> addNCRecords( List<com.zhongzhou.Excavator.model.NC.Item> ncItemsPendingInsert ) throws SQLException{
		
		List<ItemMapping> itemMappings = new ArrayList<ItemMapping>();
		List<Item> items = new ArrayList<Item>();
		List<String> insertIds = new ArrayList<String>();
		
		Timestamp currentTime = new Timestamp( System.currentTimeMillis() );  
		
		for( int i=0; i<ncItemsPendingInsert.size(); i++ ){
			
			com.zhongzhou.Excavator.model.NC.Item ncDoc = ncItemsPendingInsert.get(i);
			
			String newItemId = UUID.randomUUID().toString();
			
			insertIds.add( newItemId );
			
			Item item = new Item();
			item.setId( newItemId );
			item.setName(  ncDoc.getINVNAME() );
			item.seteName( ncDoc.getFORINVNAME() );
			item.setItemCode( ncDoc.getINVCODE() );
			item.setItemType( ncDoc.getINVTYPE() );
			item.setSpecification( ncDoc.getINVSPEC() );
			item.setLastUpdateTime( currentTime );
			
			items.add(item);

			ItemMapping itemMapping = new ItemMapping();
			itemMapping.setId( newItemId );
			itemMapping.setDataSource( ncDoc.getDocSource() );
			itemMapping.setItemId( newItemId );
			itemMapping.setItemCode( ncDoc.getINVCODE() );
			itemMapping.setSourceId( ncDoc.getPK_INVBASDOC() );
			
			itemMappings.add( itemMapping );
			
			/*prepare Mongo data*/
			ncDoc.setDocCreateTime( currentTime );
		}
		itemDAO.insertItems(items);
		itemDAO.insertItemMappings( itemMappings );
		
		/* prepare Mongo Data */
		mongoNCItemDAO.insertItems( ncItemsPendingInsert );
		
		return insertIds;

		//TODO 
		//insert new corporations doc of master data
	}
	
	@Transactional(
			propagation=Propagation.REQUIRED,
			isolation=Isolation.READ_COMMITTED,
			readOnly=false,
			rollbackFor=Exception.class )
	public void changeNCRecords( 
			List<com.zhongzhou.Excavator.model.NC.Item> ncItemsPendingUpdate )  throws SQLException{
	
		List<Item> items = new ArrayList<Item>();
		
		Timestamp currentTime = new Timestamp( System.currentTimeMillis() );  
		
		for( int i=0; i<ncItemsPendingUpdate.size(); i++ ){

			com.zhongzhou.Excavator.model.NC.Item ncDoc = ncItemsPendingUpdate.get(i);
			
			if( ncDoc.getTargetId() != null && ncDoc.getTargetId().length() > 0 ){
				
				Item item = new Item();
				item.setId( ncDoc.getTargetId() );
				item.setName(  ncDoc.getINVNAME() );
				item.seteName( ncDoc.getFORINVNAME() );
				item.setItemCode( ncDoc.getINVCODE() );
				item.setItemType( ncDoc.getINVTYPE() );
				item.setSpecification( ncDoc.getINVSPEC() );
				item.setLastUpdateTime( currentTime );
				
				items.add(item);

				/*prepare Mongo data*/
				ncDoc.setDocSource( com.zhongzhou.Excavator.model.NC.Item.dataSource );
				ncDoc.setDocCreateTime( currentTime );
			}
		}
		
		itemDAO.updateItems( items );
		/* prepare Mongo Data */
		mongoNCItemDAO.insertItems( ncItemsPendingUpdate );
	}
}
