package com.zhongzhou.Excavator.service.impl.migration.NC;

import java.sql.SQLException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhongzhou.Excavator.DAO.mongo.NC.NCCorporationDAO;
import com.zhongzhou.Excavator.model.Corporation;
import com.zhongzhou.Excavator.model.CorporationIntegrationMapping;
import com.zhongzhou.Excavator.model.CorporationIntegrationMappingSearchParameters;
import com.zhongzhou.Excavator.model.NC.CorporationSearchParameters;
import com.zhongzhou.Excavator.service.migration.NC.NCCorporationService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;

import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;

/**
 * @author Grry Zhang
 */
@Service(ServiceNameList.MIGRATION_NC_CorporationService)
public class CorporationServiceImpl implements NCCorporationService{

	@Resource(name=DAOBeanNameList.oracle_nc_corporation)
	com.zhongzhou.Excavator.DAO.oracle.NC.CorporationDAO ncCorporationDAO;
	
	@Resource(name=DAOBeanNameList.postgresql_md_corporation)
	com.zhongzhou.Excavator.DAO.postgresql.MD.CorporationDAO masterDataCorporationDAO;
	
	@Resource(name=DAOBeanNameList.mongo_nc_corporation )
	NCCorporationDAO  ncMongoCorporationDAO;
	
	private static int batchNumber = 100;
	
	/**
	 * @author Grry Zhang
	 */
	@Override 
	public void migrateCorporations( CorporationSearchParameters searchParameters ) throws ServiceRuntimeException{
		
		try {
			
			Integer count = ncCorporationDAO.countCorporations( searchParameters );
			
			if( count <= 0 ){
				return;
			}
			
			int migrateStart = 1;
			int migrateEnd = migrateStart + batchNumber - 1;
			
			while( migrateStart < count ){
				
				/* Get data from NC, ${batchNumber} records once*/
				searchParameters.setStart(migrateStart);
				searchParameters.setEnd(migrateEnd);
				
				List<com.zhongzhou.Excavator.model.NC.CorporationDoc> ncCorporationDocs 
					= ncCorporationDAO.selectCorporationsWithRowNumber( searchParameters );
				/* Get data from NC, ${batchNumber} records once*/
				
				List<String> sourceIds;
				
				List<com.zhongzhou.Excavator.model.NC.CorporationDoc> corporationsPendingInsert;
				List<com.zhongzhou.Excavator.model.NC.CorporationDoc> corporationsPendingUpdate;
					
				if( ncCorporationDocs!= null && ncCorporationDocs.size() > 0 ){
					
					corporationsPendingInsert = new ArrayList<com.zhongzhou.Excavator.model.NC.CorporationDoc>();
					corporationsPendingInsert.addAll( ncCorporationDocs );
					corporationsPendingUpdate = new ArrayList<com.zhongzhou.Excavator.model.NC.CorporationDoc>();
					
					/* By data from NC, select which are already existed in the master data, inter table */
					sourceIds = new ArrayList<String>();
					for( com.zhongzhou.Excavator.model.NC.CorporationDoc doc : ncCorporationDocs ){
						sourceIds.add( doc.getPK_CUBASDOC() );
					}
					
					CorporationIntegrationMappingSearchParameters cimsp
						= new CorporationIntegrationMappingSearchParameters();
					cimsp.setSourceIds( sourceIds );
					
					List<CorporationIntegrationMapping> corporationIntegrationMappings 
						= masterDataCorporationDAO.selectCorporationIntegrationMapping( cimsp );
					/* By data from NC, select which are already existed in the master data, inter table */
				
					/* Compare, if existed, move to update list, else, stay them in the insert list */
					for( int i=0; i<corporationsPendingInsert.size() ; i++ ){
						
						for( int j=0; j<corporationIntegrationMappings.size() ; j++ ){
							
							if( corporationsPendingInsert.get(i).getPK_CUBASDOC().equals( corporationIntegrationMappings.get(j).getSourceId() ) ){
								
								corporationsPendingInsert.get(i).setTargetId( corporationIntegrationMappings.get(j).getCorporationId() );
								corporationsPendingUpdate.add( corporationsPendingInsert.get(i) );
								corporationIntegrationMappings.remove(j);
								corporationsPendingInsert.remove(i);
								i--;
								break;
							}
						}
					}
					/* Compare, if existed, move to update list, if not, stay them in the insert list */
					
					if( corporationsPendingInsert!= null && corporationsPendingInsert.size() > 0 ){
						((CorporationServiceImpl) AopContext.currentProxy()).addNCRecords( corporationsPendingInsert );
					}
					if( corporationsPendingUpdate!= null && corporationsPendingUpdate.size() > 0 ){
						((CorporationServiceImpl) AopContext.currentProxy()).changeNCRecords( corporationsPendingUpdate );
					}
				}
			
				migrateStart += batchNumber;
				migrateEnd   += batchNumber;
			}
			
			/*Clear Inserted data, there may some logic duplicate data.*/
			com.zhongzhou.Excavator.model.CorporationSearchParameters clearSearchParameters
			= new com.zhongzhou.Excavator.model.CorporationSearchParameters();
			clearSearchParameters.setLimit(null);
			clearSearchParameters.setOffset(null);
			((CorporationServiceImpl) AopContext.currentProxy()).clearRecords( clearSearchParameters );
			
		} catch (SQLException e) {
			ServiceRuntimeException exception = new ServiceRuntimeException( "Error in migrating corporations. SQL exception:" , e );
			exception.fillInStackTrace();
			throw exception;
		}
	}
	
	/**
	 * @param corporationsPendingUpdate
	 * @author Grry Zhang
	 */
	@Transactional(
			propagation=Propagation.REQUIRED,
			isolation=Isolation.READ_COMMITTED,
			readOnly=false,
			rollbackFor=Exception.class )
	public List<String> addNCRecords( List<com.zhongzhou.Excavator.model.NC.CorporationDoc> corporationsPendingInsert ) throws SQLException{
		
		List<CorporationIntegrationMapping> mappings = new ArrayList<CorporationIntegrationMapping>();
		List<Corporation> masterDataCorporations     = new ArrayList<Corporation>();
		List<String> insertIds = new ArrayList<String>();
		
		Timestamp currentTime = new Timestamp( System.currentTimeMillis() );  
		
		for( int i=0; i<corporationsPendingInsert.size(); i++ ){
			
			com.zhongzhou.Excavator.model.NC.CorporationDoc ncDoc = corporationsPendingInsert.get(i);
			
			String masterDataCorporationId = UUID.randomUUID().toString();
			
			insertIds.add( masterDataCorporationId );
			
			Corporation masterDataCorporation = new Corporation( ncDoc );
			masterDataCorporation.setId(masterDataCorporationId);
			
			masterDataCorporations.add(masterDataCorporation);

			CorporationIntegrationMapping mapping = new CorporationIntegrationMapping();
			mapping.setId( masterDataCorporationId );
			mapping.setCorporationId( masterDataCorporationId );
			mapping.setSourceCode( ncDoc.getCUSTCODE() );
			mapping.setSourceId( ncDoc.getPK_CUBASDOC() );
			mapping.setDataSource( com.zhongzhou.Excavator.model.NC.CorporationDoc.docSource );
			
			mappings.add(mapping);
			
			/*prepare Mongo data*/
			ncDoc.setTargetId( masterDataCorporationId );
			ncDoc.setDocSource( com.zhongzhou.Excavator.model.NC.CorporationDoc.docSource );
			ncDoc.setDocCreateTime( currentTime );
		}
		masterDataCorporationDAO.insertCorporations( masterDataCorporations );
		masterDataCorporationDAO.insertCorporationIntegrationMappings( mappings );
		
		/* prepare Mongo Data */
		ncMongoCorporationDAO.insertCorporation( corporationsPendingInsert );
		
		return insertIds;

		//TODO 
		//insert new corporations doc of master data
	}
	
	/**
	 * The targetId field in data of com.zhongzhou.Excavator.model.NC.CorporationDoc must have value.
	 * <br> Or process will ignore these data without targetId value. 
	 * 
	 * @param corporationsPendingUpdate
	 * 
	 * @author Grry Zhang
	 */
	@Transactional(
			propagation=Propagation.REQUIRED,
			isolation=Isolation.READ_COMMITTED,
			readOnly=false,
			rollbackFor=Exception.class )
	public void changeNCRecords( 
			List<com.zhongzhou.Excavator.model.NC.CorporationDoc> corporationsPendingUpdate )  throws SQLException{
		
		List<Corporation> masterDataCorporations = new ArrayList<Corporation>();
		List<CorporationIntegrationMapping> pendingUpdateMapping = new ArrayList<CorporationIntegrationMapping>(); 
		
		Timestamp currentTime = new Timestamp( System.currentTimeMillis() );  
		
		for( int i=0; i<corporationsPendingUpdate.size(); i++ ){

			com.zhongzhou.Excavator.model.NC.CorporationDoc ncDoc = corporationsPendingUpdate.get(i);
			
			if( ncDoc.getTargetId() != null && ncDoc.getTargetId().length() > 0 ){
				
				Corporation masterDataCorporation = new Corporation( ncDoc );
				masterDataCorporation.setId( ncDoc.getTargetId() );
				
				masterDataCorporations.add(masterDataCorporation);
				
				CorporationIntegrationMapping mapping = new CorporationIntegrationMapping();
				mapping.setSourceCode( ncDoc.getCUSTCODE() );
				mapping.setSourceId( ncDoc.getPK_CUBASDOC() );
				mapping.setDataSource( com.zhongzhou.Excavator.model.NC.CorporationDoc.docSource );
				pendingUpdateMapping.add( mapping );

				/*prepare Mongo data*/
				ncDoc.setDocSource( com.zhongzhou.Excavator.model.NC.CorporationDoc.docSource );
				ncDoc.setDocCreateTime( currentTime );
			}
		}
		
		masterDataCorporationDAO.updateCorporations( masterDataCorporations );
		masterDataCorporationDAO.updateCorporationIntegrationMappings( pendingUpdateMapping );
		/* prepare Mongo Data */
		ncMongoCorporationDAO.insertCorporation( corporationsPendingUpdate );
		
		//TODO update corporations doc of master data
	}
	
	/**
	 * The targetId field in data of com.zhongzhou.Excavator.model.NC.CorporationDoc must have value.
	 * <br> Or process will ignore these data without targetId value. 
	 * 
	 * @param corporationsPendingUpdate
	 * 
	 * @author Grry Zhang
	 */
	@Transactional(
			propagation=Propagation.REQUIRED,
			isolation=Isolation.READ_COMMITTED,
			readOnly=false,
			rollbackFor=Exception.class )
	public void clearRecords( com.zhongzhou.Excavator.model.CorporationSearchParameters searchParameters )  
			throws SQLException{
		
		
		List<Corporation> pendingClearCorporations = masterDataCorporationDAO.selectDuplicateNameCorporations( searchParameters );
		
		Queue<Corporation> pendingClearRecords = new LinkedList<Corporation>( pendingClearCorporations );
		
		List<String> pendingRemoveCorporationIds = new ArrayList<String>();
		
		List< Map<String,String> > pendingChangeCorporationIdsInMapping = new ArrayList< Map<String,String> >();
		

		Corporation theKeepOne = pendingClearRecords.poll();
		while( !pendingClearRecords.isEmpty() ){
			
			if( theKeepOne != null && theKeepOne.getName()!= null ){
				
				Corporation nextOne = pendingClearRecords.poll();
				
				if( theKeepOne.getName().equals( nextOne.getName() ) ){
					
					pendingRemoveCorporationIds.add( nextOne.getId() );
					
					Map<String,String> pendingChangeCorporationIdInMapping = new HashMap<String,String>();
					pendingChangeCorporationIdInMapping.put( "oldCorporationId", nextOne.getId() );
					pendingChangeCorporationIdInMapping.put( "newCorporationId", theKeepOne.getId() );
					pendingChangeCorporationIdsInMapping.add( pendingChangeCorporationIdInMapping );
				}else{
					
					theKeepOne = nextOne;
				}
			}
		}
		
		if( pendingRemoveCorporationIds !=null && pendingRemoveCorporationIds.size() > 0 ){
			masterDataCorporationDAO.deleteCorporation( pendingRemoveCorporationIds );
		}
		if( pendingChangeCorporationIdsInMapping !=null && pendingChangeCorporationIdsInMapping.size() > 0 ){
			masterDataCorporationDAO.updateMappingsCorporationId( pendingChangeCorporationIdsInMapping );
		}
	}

	public static Integer getBatchNumber() {
		return batchNumber;
	}

	public static void setBatchNumber(Integer batchNumber) {
		CorporationServiceImpl.batchNumber = batchNumber;
	}
}
