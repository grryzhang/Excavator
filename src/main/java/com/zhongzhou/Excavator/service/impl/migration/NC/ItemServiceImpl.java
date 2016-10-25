package com.zhongzhou.Excavator.service.impl.migration.NC;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhongzhou.Excavator.DAO.mongo.NC.NCItemDAO;
import com.zhongzhou.Excavator.model.NC.ItemSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.Corporation;
import com.zhongzhou.Excavator.model.masterdata.CorporationIntegrationMapping;
import com.zhongzhou.Excavator.model.masterdata.CorporationIntegrationMappingSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.InterItem2Category;
import com.zhongzhou.Excavator.model.masterdata.Item;
import com.zhongzhou.Excavator.model.masterdata.ItemCategoryMapping;
import com.zhongzhou.Excavator.model.masterdata.ItemCategoryMappingSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.ItemMapping;
import com.zhongzhou.Excavator.model.masterdata.ItemPackageInfo;
import com.zhongzhou.Excavator.service.migration.NC.NCItemService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;
import com.zhongzhou.common.util.Excel2007Util;

@Service(ServiceNameList.MIGRATION_NC_ItemService)
public class ItemServiceImpl implements NCItemService{
	
	@Resource(name=DAOBeanNameList.oracle_nc_item)
	com.zhongzhou.Excavator.DAO.oracle.NC.ItemDAO ncItemDAO; 
	
	@Resource(name=DAOBeanNameList.postgresql_md_item )
	com.zhongzhou.Excavator.DAO.postgresql.MD.ItemDAO itemDAO;
	
	@Resource( name=DAOBeanNameList.mongo_nc_item )
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
					
					com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters itemSearchParameters = new com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters();
					
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
	
	@Override
	public void migrateItemPackageInfo(ItemSearchParameters searchParameters) throws ServiceRuntimeException {
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
					
					for( com.zhongzhou.Excavator.model.NC.Item doc : items ){
						sourceIds.add( doc.getPK_INVBASDOC() );
					}
					
					com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters itemSearchParameters = new com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters();
					itemSearchParameters.setSourceIds( sourceIds );
					List<ItemMapping> itemSourceMappings = itemDAO.selectItemMappings(itemSearchParameters);
					
					List<ItemPackageInfo> packageInfos = new ArrayList<ItemPackageInfo>();
					
					for( com.zhongzhou.Excavator.model.NC.Item item : items ){
						
						for( ItemMapping sourceMapping : itemSourceMappings ){
							
							if( sourceMapping.getSourceId().equals( item.getPK_INVBASDOC() ) ){
								
								ItemPackageInfo packageInfo = new ItemPackageInfo();
								
								String newId = UUID.randomUUID().toString();
								packageInfo.setId            ( newId );
								packageInfo.setItemId        ( sourceMapping.getItemId() );
								packageInfo.setPackageNumber ( item.getDEF11() );
								packageInfo.setGrossWeight   ( item.getDEF10() );
								packageInfo.setLength        ( item.getDEF7()  );
								packageInfo.setWidth     	 ( item.getDEF8()  );
								packageInfo.setHeight 	 	 ( item.getDEF9()  );
								
								packageInfos.add( packageInfo );
							}
						}
					}
					
					if( packageInfos!= null && packageInfos.size() > 0 ){
						itemDAO.insertItemPackageInfos( packageInfos );
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
	
	@Override
	public void mapItem2Category() {
		
		try{
			ItemSearchParameters searchParameters = new ItemSearchParameters();
			
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
				
				List<String> sourceIds = new ArrayList<String>();
				for( com.zhongzhou.Excavator.model.NC.Item ncItem : items ){
					sourceIds.add( ncItem.getPK_INVBASDOC() );
				}
				com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters mdItemSearchParameters = new com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters();
				mdItemSearchParameters.setSourceIds(sourceIds);
				mdItemSearchParameters.setLimit(null);
				mdItemSearchParameters.setStart(null);
				List<com.zhongzhou.Excavator.model.masterdata.ItemMapping>  mdItemScoureMapping = itemDAO.selectItemMappings(mdItemSearchParameters);
				
				List<String> categorySourceIds = new ArrayList<String>();
				for( com.zhongzhou.Excavator.model.NC.Item ncItem : items ){
					
					categorySourceIds.add( ncItem.getPK_INVCL() );
				}
				ItemCategoryMappingSearchParameters itemCategoryMappingSearchParameters = new ItemCategoryMappingSearchParameters();
				itemCategoryMappingSearchParameters.setSourceIds(categorySourceIds);
				List< ItemCategoryMapping > categorySourceMapping = itemDAO.selectItemCategorysMapping( itemCategoryMappingSearchParameters );
				
				
				List<InterItem2Category> item2CategoryMapping = new ArrayList<InterItem2Category>();
				for( com.zhongzhou.Excavator.model.NC.Item ncItem : items ){
					
					InterItem2Category item2Category = new InterItem2Category();
					
					for( com.zhongzhou.Excavator.model.masterdata.ItemMapping sourceMapping : mdItemScoureMapping ){
						
						if( ncItem.getPK_INVBASDOC().equals( sourceMapping.getSourceId() )  ){
							item2Category.setItemId( sourceMapping.getItemId() );
						}
					}
					
					for( ItemCategoryMapping sourceMapping :categorySourceMapping ){
						if( ncItem.getPK_INVCL().equals( sourceMapping.getSourceId() )  ){
							item2Category.setCategoryId( sourceMapping.getCategoryId() );
						}
					}
					
					if( item2Category.getCategoryId() != null && item2Category.getItemId() != null){
						item2CategoryMapping.add( item2Category );
					}
				}
				
				if( item2CategoryMapping.size() > 0 ){
					itemDAO.insertInterItem2Category( item2CategoryMapping );
				}
				
				migrateStart += batchNumber;
				migrateEnd   += batchNumber;
			}
		}catch( SQLException e ){
			ServiceRuntimeException exception = new ServiceRuntimeException( "Error in mapping NC Item to Category. SQL exception:" , e );
			exception.fillInStackTrace();
			throw exception;
		}
	}
	
	public void updatePackageInfoFromExcel2007( InputStream excelStream ) throws ServiceRuntimeException{
		
		XSSFWorkbook excelBook = null;
		
		try{
			
			excelBook = new XSSFWorkbook( excelStream );
			
			XSSFSheet sheet = excelBook.getSheetAt(0);
			
			if( sheet == null ){
				
				throw new ServiceRuntimeException("Wrong excel file input, no sheet there.");
			}
			
			List<com.zhongzhou.Excavator.model.NC.Item> itemPackages = new ArrayList<com.zhongzhou.Excavator.model.NC.Item>(); 
			
			for (int r = 2; r < sheet.getPhysicalNumberOfRows(); r++) {

				XSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}else{
					
					com.zhongzhou.Excavator.model.NC.Item item = new com.zhongzhou.Excavator.model.NC.Item();
					
					for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
						switch ( c ) {
							
							case 0:
								item.setINVCODE( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 3:
								item.setDEF11(  Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 4:
								item.setDEF10(  Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 5:
								item.setDEF7(  Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 6:
								item.setDEF8(  Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 7:
								item.setDEF9(  Excel2007Util.getCellStringValue( row, c ) );
								break;
						}
					}
					
					itemPackages.add( item );
				}
			}
			
			if( itemPackages.size() > 0 ){
				
				for( int i=0; i<itemPackages.size();  ){
					
					int fromIndex = i;
					int toIndex = fromIndex +50;
					if( toIndex > itemPackages.size()-1 ){
						toIndex = itemPackages.size()-1;
					}
					List<com.zhongzhou.Excavator.model.NC.Item> items = itemPackages.subList(fromIndex, toIndex);
					if( items != null && items.size() > 1 ){
						ncItemDAO.updateItemsPackageInfo( items );
					}
					i= toIndex;
				}
			}
			
		}catch( IOException e ){
			throw new ServiceRuntimeException( e );
		} catch (SQLException e) {
			throw new ServiceRuntimeException( e );
		}finally{
			try{
				excelBook.close();
			}catch(Exception e){
				//TODO
			}
		}
	}
	
	public void updateCustomInfoFromExcel2007( InputStream excelStream ) throws ServiceRuntimeException{
		
		XSSFWorkbook excelBook = null;
		
		try{
			
			excelBook = new XSSFWorkbook( excelStream );
			
			XSSFSheet sheet = excelBook.getSheetAt(0);
			
			if( sheet == null ){
				
				throw new ServiceRuntimeException("Wrong excel file input, no sheet there.");
			}
			
			List<com.zhongzhou.Excavator.model.NC.Item> itemPackages = new ArrayList<com.zhongzhou.Excavator.model.NC.Item>(); 
			
			for (int r = 2; r < sheet.getPhysicalNumberOfRows(); r++) {

				XSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}else{
					
					com.zhongzhou.Excavator.model.NC.Item item = new com.zhongzhou.Excavator.model.NC.Item();
					
					for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
						switch ( c ) {
							
							case 0:
								item.setINVCODE( Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 3:
								item.setDEF1(  Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 4:
								item.setDEF2(  Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 5:
								item.setDEF3(  Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 6:
								item.setDEF4(  Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 7:
								item.setDEF5(  Excel2007Util.getCellStringValue( row, c ) );
								break;
							case 8:
								item.setDEF6(  Excel2007Util.getCellStringValue( row, c ) );
								break;
						}
					}
					
					itemPackages.add( item );
				}
			}
			
			if( itemPackages.size() > 0 ){
				
				for( int i=0; i<itemPackages.size()-1;  ){
					
					int fromIndex = i;
					int toIndex = fromIndex +50;
					if( toIndex > itemPackages.size()-1 ){
						toIndex = itemPackages.size()-1;
					}
					List<com.zhongzhou.Excavator.model.NC.Item> items = itemPackages.subList(fromIndex, toIndex);
					if( items != null && items.size() > 1 ){
						ncItemDAO.updateItemsCustomInfo( items );
					}
					i= toIndex;
				}
			}
			
		}catch( IOException e ){
			throw new ServiceRuntimeException( e );
		} catch (SQLException e) {
			throw new ServiceRuntimeException( e );
		}finally{
			try{
				excelBook.close();
			}catch(Exception e){
				//TODO
			}
		}
	}
}
