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

import com.zhongzhou.Excavator.DAO.mongo.NC.NCItemCategoryDAO;
import com.zhongzhou.Excavator.DAO.oracle.ItemDAO;
import com.zhongzhou.Excavator.Exception.ServiceRuntimeException;
import com.zhongzhou.Excavator.model.Corporation;
import com.zhongzhou.Excavator.model.CorporationIntegrationMapping;
import com.zhongzhou.Excavator.model.ItemCategoryMapping;
import com.zhongzhou.Excavator.model.ItemCategoryMappingSearchParameters;
import com.zhongzhou.Excavator.model.ItemCategory;
import com.zhongzhou.Excavator.model.ItemCategorySearchParameters;
import com.zhongzhou.Excavator.service.migration.NC.NCItemCategoryService;

@Service("NCItemCategoryService")
public class ItemCategoryServiceImpl implements NCItemCategoryService{

	@Resource(name="oracle.ItemDAO")
	com.zhongzhou.Excavator.DAO.oracle.ItemDAO ncItemDAO;
	
	@Resource(name="postgresql.ItemDAO")
	com.zhongzhou.Excavator.DAO.postgresql.ItemDAO itemDAO;
	
	@Resource( name="mongo.NCItemCategoryDAO" )
	NCItemCategoryDAO ncMongoItemCategoryDAO;
	
	private static int batchNumber = 100;
	
	@Override
	public void migrateItemCategory() throws ServiceRuntimeException {
	
		try {
			
			Integer count = ncItemDAO.countItemCategorys();
			
			if( count <= 0 ){
				return;
			}
			
			int migrateStart = 1;
			int migrateEnd = migrateStart + batchNumber - 1;
			
			while( migrateStart < count ){
				
				com.zhongzhou.Excavator.model.NC.ItemCategorySearchParameters searchParameters 
					= new com.zhongzhou.Excavator.model.NC.ItemCategorySearchParameters();
				
				/* Get data from NC, ${batchNumber} records once*/
				searchParameters.setStart(migrateStart);
				searchParameters.setEnd(migrateEnd);
				//Here when we search the data from NC, we must be sure that the parent node is on the top of its tree in the list
				List<com.zhongzhou.Excavator.model.NC.ItemCategory> itemcategorys = ncItemDAO.selectItemCategorysWithRowNumber( searchParameters );
				/* Get data from NC, ${batchNumber} records once*/
					
				List<com.zhongzhou.Excavator.model.NC.ItemCategory> itemCategoryPendingInsert;
				List<com.zhongzhou.Excavator.model.NC.ItemCategory> itemCategoryPendingUpdate;
				List<com.zhongzhou.Excavator.model.NC.ItemCategory> itemCategoryStructureUpdate;
				
				if( itemcategorys!= null && itemcategorys.size() > 0 ){
					
					itemCategoryPendingInsert   = new ArrayList<com.zhongzhou.Excavator.model.NC.ItemCategory>();
					itemCategoryPendingInsert.addAll( itemcategorys );
					itemCategoryPendingUpdate   = new ArrayList<com.zhongzhou.Excavator.model.NC.ItemCategory>();
					itemCategoryStructureUpdate = new ArrayList<com.zhongzhou.Excavator.model.NC.ItemCategory>();
					
					/* By data from NC, select which are already existed in the master data, inter table */
					List<String> sourceIds = new ArrayList<String>();
					for( com.zhongzhou.Excavator.model.NC.ItemCategory itemCategory : itemcategorys ){
						sourceIds.add( itemCategory.getPK_INVCL() );
					}
					ItemCategoryMappingSearchParameters itemCategoryMappingSearchParameters = new ItemCategoryMappingSearchParameters();
					itemCategoryMappingSearchParameters.setSourceIds( sourceIds );
					List<ItemCategoryMapping> itemCategorysMapping = itemDAO.selectItemCategorysMapping( itemCategoryMappingSearchParameters );
					/* By data from NC, select which are already existed in the master data, inter table */
					
					/* Compare, if existed, move to update list, else, stay them in the insert list */
					for( int i=0; i<itemCategoryPendingInsert.size() ; i++ ){
						
						for( int j=0; j<itemCategorysMapping.size() ; j++ ){
							
							if( itemCategoryPendingInsert.get(i).getPK_INVCL().equals( itemCategorysMapping.get(j).getSourceId() ) ){
								
								
								if( 
									//The structure is changed, the parent is not the previous one.
									(
										itemCategoryPendingInsert.get(i).getPARENT_ID() != null
										&& !itemCategoryPendingInsert.get(i).getPARENT_ID().equals( itemCategorysMapping.get(j).getSourceId() )
									)
									|| itemCategoryPendingInsert.get(i).getPARENT_ID() != itemCategorysMapping.get(j).getSourceId()
									){
									
									itemCategoryStructureUpdate.add( itemCategoryPendingInsert.get(i) );
								}else{
								
									//itemCategoryPendingInsert.get(i).setTargetId( corporationIntegrationMappings.get(j).getCorporationId() );
									itemCategoryPendingUpdate.add( itemCategoryPendingInsert.get(i) );
								}
								itemCategorysMapping.remove(j);
								itemCategoryPendingInsert.remove(i);
								i--;
								break;
							}
						}
					}
					/* Compare, if existed, move to update list, if not, stay them in the insert list */
					
					for( com.zhongzhou.Excavator.model.NC.ItemCategory ncItemCategory : itemCategoryPendingInsert ){
						((ItemCategoryServiceImpl) AopContext.currentProxy()).addNCRecord( ncItemCategory );
					}
					for( com.zhongzhou.Excavator.model.NC.ItemCategory ncItemCategory : itemCategoryPendingUpdate ){
						((ItemCategoryServiceImpl) AopContext.currentProxy()).changeNCRecord( ncItemCategory );
					}
				}
				
				itemDAO.updateIsLeaf();

				migrateStart += batchNumber;
				migrateEnd   += batchNumber;
			}
		} catch (SQLException e) {
			ServiceRuntimeException exception = new ServiceRuntimeException( "Error in migrating NC ItemCategory. SQL exception:" , e );
			exception.fillInStackTrace();
			throw exception;
		}
	}
	
	
	/**
	 * Can not do batch insert in one session with read committed isolation.
	 * <br>
	 * <br>
	 * In one list, there may contain the target object and it's parent. <br>
	 * If the parent of one is not existed, or not committed before processing
	 * the child, the operation will throw a run time exception
	 * "Parent is not existed."
	 * 
	 * @param corporationsPendingUpdate
	 * @author Grry Zhang
	 * 
	 * @throws ServiceRuntimeException
	 *             "Parent is not existed."
	 */
	@Transactional(
			propagation = Propagation.REQUIRED, 
			isolation = Isolation.READ_COMMITTED, 
			readOnly = false, 
			rollbackFor = Exception.class)
	public void addNCRecord(com.zhongzhou.Excavator.model.NC.ItemCategory itemCategoryPendingInsert)
			throws ServiceRuntimeException, SQLException {

		String parentId = itemCategoryPendingInsert.getPARENT_ID();
		List<String> parentIds = new ArrayList<String>();
		parentIds.add(parentId);
		
		ItemCategory itemCategory = new ItemCategory();
		ItemCategoryMapping itemCategoryMapping = new ItemCategoryMapping();

		List<ItemCategory> parentCategorys;
		
		if (parentId != null) {

			ItemCategorySearchParameters itemCategorySearchParameters = new ItemCategorySearchParameters();
			itemCategorySearchParameters.setSourceIds(parentIds);
			parentCategorys = itemDAO.selectItemCategorys( itemCategorySearchParameters );
			
		}else{
			
			ItemCategorySearchParameters itemCategorySearchParameters = new ItemCategorySearchParameters();
			itemCategorySearchParameters.setLevel(0);
			parentCategorys = itemDAO.selectItemCategorys( itemCategorySearchParameters );
		}
		
		if ( parentCategorys == null || parentCategorys.size() < 1) {
			throw new ServiceRuntimeException(
					"Error in adding a new catagory, parent category is not existed. Wrong parent id:" + parentId);
		} else {

			String itemCategoryId = UUID.randomUUID().toString();

			itemCategory.setId            ( itemCategoryId );
			itemCategory.setParentId	  ( parentCategorys.get(0).getId() );
			itemCategory.setName		  ( itemCategoryPendingInsert.getINVCLASSNAME() );
			itemCategory.setEname		  ( itemCategoryPendingInsert.getFORINVNAME() );
			itemCategory.setLevel		  ( parentCategorys.get(0).getLevel() + 1 );
			itemCategory.setParentId	  ( parentCategorys.get(0).getId() );
			itemCategory.setIsLeaf		  ( Boolean.TRUE );
			itemCategory.setTreeLeftValue ( parentCategorys.get(0).getTreeRightValue() );
			itemCategory.setTreeRightValue( parentCategorys.get(0).getTreeRightValue() + 1 );
			itemCategory.setStatus		  ( 1 );

			String mappingId = UUID.randomUUID().toString();

			itemCategoryMapping.setId               ( mappingId );
			itemCategoryMapping.setCategoryId       ( itemCategoryId );
			itemCategoryMapping.setSourceId         ( itemCategoryPendingInsert.getPK_INVCL() );
			itemCategoryMapping.setDataSource       ( com.zhongzhou.Excavator.model.NC.ItemCategory.docSource );
			itemCategoryMapping.setSourceParentId   ( itemCategoryPendingInsert.getPARENT_ID() );
		}
		
		
		itemDAO.insertItemCategory(itemCategory);
		itemDAO.insertItemCategoryMapping( itemCategoryMapping );
		
		ncMongoItemCategoryDAO.insertItemCategory( itemCategoryPendingInsert );
	}
	
	/**
	 * Currently, it does not support the node parent change.
	 * 
	 * @param corporationsPendingUpdate
	 * @author Grry Zhang
	 * 
	 * @throws ServiceRuntimeException
	 *             "Parent is not existed."
	 */
	@Transactional(
			propagation = Propagation.REQUIRED, 
			isolation = Isolation.READ_COMMITTED, 
			readOnly = false, 
			rollbackFor = Exception.class)
	public void changeNCRecord( com.zhongzhou.Excavator.model.NC.ItemCategory itemCategoryPendingUpdate )
			throws ServiceRuntimeException, SQLException {

		String id = itemCategoryPendingUpdate.getPK_INVCL();
		List<String> ids = new ArrayList<String>();
		ids.add(id);
		
		ItemCategorySearchParameters itemCategorySearchParameters = new ItemCategorySearchParameters();
		itemCategorySearchParameters.setSourceIds( ids );
		List<ItemCategory> categorys = itemDAO.selectItemCategorys( itemCategorySearchParameters );
		
		if ( categorys != null && categorys.size()>0 ) {
			categorys.get(0).setName ( itemCategoryPendingUpdate.getINVCLASSNAME() );
			categorys.get(0).setEname( itemCategoryPendingUpdate.getFORINVNAME() );
		}
		
		itemCategoryPendingUpdate.setLastUpdateTime( new Timestamp( System.currentTimeMillis() ) );
		
		itemDAO.updateItemCategory( categorys.get(0) );
		ncMongoItemCategoryDAO.insertItemCategory( itemCategoryPendingUpdate );
	}
}
