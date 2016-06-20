package com.zhongzhou.Excavator.service.impl.migration.ERP;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhongzhou.Excavator.model.Corporation;
import com.zhongzhou.Excavator.model.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.ERP.TDimZtree;
import com.zhongzhou.Excavator.model.ERP.TOmSupplier;
import com.zhongzhou.Excavator.model.ERP.TOmSupplierSearchParameters;
import com.zhongzhou.Excavator.service.impl.migration.NC.CorporationServiceImpl;
import com.zhongzhou.Excavator.service.migration.ERP.ERPSupplierService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;

@Service( ServiceNameList.MIGRATION_ERP_SupplierService )
public class ERPSupplierServiceImpl implements ERPSupplierService {
	
	@Resource(name=DAOBeanNameList.postgresql_md_corporation)
	private com.zhongzhou.Excavator.DAO.postgresql.MD.CorporationDAO postgresMDCorporationDAO;
	
	@Resource(name=DAOBeanNameList.oracle_erp_supplier)
	private com.zhongzhou.Excavator.DAO.oracle.ERP.SupplierDAO supplierDAO; 
	
	private static final String ncSupplierPreNumber = "9907"; 
	private static final String ncSupplierParentName = "NC供应商";
	
	private static int batchNumber = 100;

	public void migrateSupplierFromMD2ERP( CorporationSearchParameters searchParameters ) throws ServiceRuntimeException{
		
		try {
			Integer countCorporation = postgresMDCorporationDAO.countCorporations(searchParameters);
			if( countCorporation <= 0 ){
				return;
			}
			
			int migrateOffset  = 0;
			int migrateLimit   = batchNumber;
			
			BigInteger maxNumber = supplierDAO.selectMaxNumberByParentno( ncSupplierPreNumber );
			if( maxNumber == null ){
				maxNumber = new BigInteger("9907000100");
			}
			
			while( migrateOffset < countCorporation ){
				
				/* Get data from NC, ${batchNumber} records once*/
				searchParameters.setLimit  ( migrateLimit  );
				searchParameters.setOffset ( migrateOffset  );
				List<Corporation> corporationsPendingInsert = postgresMDCorporationDAO.selectCorporations(searchParameters);
				List<Corporation> corporationsPendingUpdate = new ArrayList<Corporation>();
				
				if( corporationsPendingInsert!= null && corporationsPendingInsert.size() > 0 ){
					
					List<String> FIDs = new ArrayList<String>();
					for( Corporation corporation : corporationsPendingInsert ){
						FIDs.add( corporation.getId() );
					}
					TOmSupplierSearchParameters supplierSearchParameters = new TOmSupplierSearchParameters();
					supplierSearchParameters.setFPARENTNO( ncSupplierPreNumber );
					supplierSearchParameters.setFIDs( FIDs );
					List<TOmSupplier> existedSuppliers = supplierDAO.selectTOmSupplier( supplierSearchParameters );
					
					/* Compare, if existed, move to update list, else, stay them in the insert list */
					for( int i=0; i<corporationsPendingInsert.size() ; i++ ){
						
						for( int j=0; j<existedSuppliers.size() ; j++ ){
							
							if( corporationsPendingInsert.get(i).getId().equals( existedSuppliers.get(j).getFID() ) ){
								
								corporationsPendingUpdate.add( corporationsPendingInsert.get(i) );
								existedSuppliers.remove(j);
								corporationsPendingInsert.remove(i);
								i--;
								break;
							}
						}
					}
					/* Compare, if existed, move to update list, if not, stay them in the insert list */
					
					List<TOmSupplier>   ERPSuppliersPendingInsert   = new ArrayList<TOmSupplier>();
					List<TDimZtree>     ERPTDimZtreePendingInsert   = new ArrayList<TDimZtree>();
					List<TOmSupplier>   ERPSuppliersPendingUpdate   = new ArrayList<TOmSupplier>();
					List<TDimZtree>     ERPTDimZtreePendingUpdate   = new ArrayList<TDimZtree>();
					
					for( Corporation corporation : corporationsPendingInsert ){
			
						TOmSupplier ERPSupplier = new TOmSupplier();
						maxNumber = maxNumber.add( new BigInteger("1") ) ;
						
						ERPSupplier.setFID            ( corporation.getId() );
						ERPSupplier.setFNAME          ( corporation.getName() );
						ERPSupplier.setFNAME_EN       ( corporation.geteName() );
						ERPSupplier.setFNUMBER        ( maxNumber.toString() );
						ERPSupplier.setFPARENTNO      ( ncSupplierPreNumber );
						ERPSupplier.setFSHORTNAME     ( corporation.getShortName() );
						//ERPSupplier.setINDUSTRY     ( iNDUSTRY);
						//ERPSupplier.setINDUSTRY_EN  (  iNDUSTRY_EN);
						
						ERPSuppliersPendingInsert.add( ERPSupplier );
						
						TDimZtree ERPSupplierZtree = new TDimZtree();
						ERPSupplierZtree.setFID          ( corporation.getId() );
						ERPSupplierZtree.setFISLEAF      ( "1" );
						ERPSupplierZtree.setFLEVEL       ( "2" );
						ERPSupplierZtree.setFNAME        ( corporation.getName() );
						ERPSupplierZtree.setFNUMBER      ( maxNumber.toString() );
						ERPSupplierZtree.setFPARENTNAME  ( ncSupplierParentName );
						ERPSupplierZtree.setFPARENTNO	 ( ncSupplierPreNumber );
						ERPSupplierZtree.setFTYPE        ( "[1]供应商" );
						ERPSupplierZtree.setFTYPE_LANG   ( "[1]Supplier" );
						
						ERPTDimZtreePendingInsert.add( ERPSupplierZtree );
					}
					
					for( Corporation corporation : corporationsPendingUpdate ){
						
						TOmSupplier ERPSupplier = new TOmSupplier();
						
						ERPSupplier.setFID            ( corporation.getId() );
						ERPSupplier.setFNAME          ( corporation.getName() );
						ERPSupplier.setFNAME_EN       ( corporation.geteName() );
						ERPSupplier.setFNUMBER        ( maxNumber.toString() );
						ERPSupplier.setFPARENTNO      ( ncSupplierPreNumber );
						ERPSupplier.setFSHORTNAME     ( corporation.getShortName() );
						//ERPSupplier.setINDUSTRY     ( iNDUSTRY);
						//ERPSupplier.setINDUSTRY_EN  ( iNDUSTRY_EN);
						
						ERPSuppliersPendingUpdate.add( ERPSupplier );
						
						TDimZtree ERPSupplierZtree = new TDimZtree();
						ERPSupplierZtree.setFID          ( corporation.getId() );
						ERPSupplierZtree.setFISLEAF      ( "1" );
						ERPSupplierZtree.setFLEVEL       ( "2" );
						ERPSupplierZtree.setFNAME        ( corporation.getName() );
						ERPSupplierZtree.setFNUMBER      ( maxNumber.toString() );
						ERPSupplierZtree.setFPARENTNAME  ( ncSupplierParentName );
						ERPSupplierZtree.setFPARENTNO	 ( ncSupplierPreNumber );
						ERPSupplierZtree.setFTYPE        ( "[1]供应商" );
						ERPSupplierZtree.setFTYPE_LANG   ( "[1]Supplier" );
						
						ERPTDimZtreePendingUpdate.add( ERPSupplierZtree );
					}
					/* currently we onle insert data which not existed. */
					if( ERPSuppliersPendingInsert.size() > 0 ){
						supplierDAO.insertSuppliers      ( ERPSuppliersPendingInsert );
					}
					if( ERPTDimZtreePendingInsert.size() > 0 ){
						supplierDAO.insertSupplierZTrees ( ERPTDimZtreePendingInsert );
					}
				}
				/* Get data from NC, ${batchNumber} records once*/
				
				migrateOffset += batchNumber;
			}
			
		} catch (SQLException e) {
			
			ServiceRuntimeException exception = new ServiceRuntimeException( "Error in migrating corporations from MD 2 ERP. SQL exception:" , e );
			exception.fillInStackTrace();
			throw exception;
		}
	}
}
