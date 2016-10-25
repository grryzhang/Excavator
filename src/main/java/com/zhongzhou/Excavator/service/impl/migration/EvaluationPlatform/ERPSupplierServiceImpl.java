package com.zhongzhou.Excavator.service.impl.migration.EvaluationPlatform;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhongzhou.Excavator.DAO.postgresql.MD.CorporationDAO;
import com.zhongzhou.Excavator.model.EvaluationPlatform.TDimZtree;
import com.zhongzhou.Excavator.model.EvaluationPlatform.TOmSupplier;
import com.zhongzhou.Excavator.model.EvaluationPlatform.TOmSupplierSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.Corporation;
import com.zhongzhou.Excavator.model.masterdata.CorporationGrade;
import com.zhongzhou.Excavator.model.masterdata.CorporationGradeItem;
import com.zhongzhou.Excavator.model.masterdata.CorporationGradeSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.Excavator.service.impl.migration.NC.CorporationServiceImpl;
import com.zhongzhou.Excavator.service.migration.ERP.ERPSupplierService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;

@Service( ServiceNameList.MIGRATION_EP_Supplier_Service )
public class ERPSupplierServiceImpl implements ERPSupplierService {
	
	@Resource(name=DAOBeanNameList.postgresql_md_corporation)
	private com.zhongzhou.Excavator.DAO.postgresql.MD.CorporationDAO postgresMDCorporationDAO;
	
	@Resource(name=DAOBeanNameList.oracle_erp_supplier)
	private com.zhongzhou.Excavator.DAO.oracle.EvaluationPlatform.SupplierDAO supplierDAO; 
	
	@Resource(name=DAOBeanNameList.postgresql_md_corporation)
	CorporationDAO corporationDAO;	
	
	private static final String ncSupplierPreNumber = "9907"; 
	private static final String ncSupplierParentName = "NC供应商";
	
	private static int batchNumber = 100;
	
	@Override
	public void migrateSupplierGrade2MD()throws SQLException{
		
		try{
			CorporationSearchParameters corpSearchParameter = new CorporationSearchParameters();
			Long count = corporationDAO.countCorporations( corpSearchParameter );
			
			List<CorporationGrade> grades = new ArrayList<CorporationGrade>();
			
			for( int i=0 ; i<=count ; i=i+50 ){
				corpSearchParameter = new CorporationSearchParameters();
				corpSearchParameter.setStart(i);
				corpSearchParameter.setLimit(50);
				List<Corporation> corporations = corporationDAO.selectCorporations( corpSearchParameter );
				
				List<String> ids = new ArrayList<String>();
				for( Corporation corp : corporations ){
					ids.add( corp.getId() );
				}
				CorporationGradeSearchParameters searchParameters = new CorporationGradeSearchParameters();
				searchParameters.setCorporationIds( ids );
				List<CorporationGrade> tempGrades = supplierDAO.selectSupplierGrades(searchParameters);
				grades.addAll(tempGrades);
			}
			
			List<CorporationGrade> newGrades = new ArrayList<CorporationGrade>();
			List<CorporationGradeItem> gradeItems = new ArrayList<CorporationGradeItem>();
			for( CorporationGrade grade : grades ){
				
				boolean existed = false;
				
				CorporationSearchParameters searchParameters = new CorporationSearchParameters();
				searchParameters.setItemCategoryIds( new ArrayList<String>( Arrays.asList( grade.getCorporationId() ) ) );
				List<CorporationGrade> existedGrades = corporationDAO.selectCorporationGrades(searchParameters);
				
				for( CorporationGrade existedGrade : existedGrades ){
					
					if( existedGrade.getGradeId().equals( grade.getGradeId() ) ){
						existed = true;
						break;
					}
				}
				
				if( !existed ){
					
					newGrades.add( grade );
					gradeItems.addAll( grade.getGradeItems() );
				}
			}
			
			corporationDAO.insertCorporationGrade(newGrades);
			corporationDAO.insertCorporationGradeItem(gradeItems);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void migrateSupplierFromMD2ERP( CorporationSearchParameters searchParameters ) throws ServiceRuntimeException{
		
		try {
			Long countCorporation = postgresMDCorporationDAO.countCorporations(searchParameters);
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
				searchParameters.setStart ( migrateOffset  );
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
					
					List<TOmSupplier>   ERPSuppliersPendingInsert   = new ArrayList<TOmSupplier>();
					List<TDimZtree>     ERPTDimZtreePendingInsert   = new ArrayList<TDimZtree>();
					List<TOmSupplier>   ERPSuppliersPendingUpdate   = new ArrayList<TOmSupplier>();
					List<TDimZtree>     ERPTDimZtreePendingUpdate   = new ArrayList<TDimZtree>();
					
					/* Compare, if existed, move to update list, else, stay them in the insert list */
					for( int i=0; i<corporationsPendingInsert.size() ; i++ ){
						
						for( int j=0; j<existedSuppliers.size() ; j++ ){
							
							/* Compare, if existed, move to update list, if not, stay them in the insert list */
							if( corporationsPendingInsert.get(i).getId().equals( existedSuppliers.get(j).getFID() ) ){
								
								
								TOmSupplier ERPSupplier = new TOmSupplier();
								
								ERPSupplier.setFID            ( corporationsPendingInsert.get(i).getId() );
								ERPSupplier.setFNAME          ( corporationsPendingInsert.get(i).getName() );
								ERPSupplier.setFNAME_EN       ( corporationsPendingInsert.get(i).geteName() );
								ERPSupplier.setFNUMBER        ( existedSuppliers.get(j).getFNUMBER() );
								ERPSupplier.setFPARENTNO      ( ncSupplierPreNumber );
								ERPSupplier.setFSHORTNAME     ( corporationsPendingInsert.get(i).getShortName() );
								//ERPSupplier.setINDUSTRY     ( iNDUSTRY);
								//ERPSupplier.setINDUSTRY_EN  ( iNDUSTRY_EN);
								
								ERPSuppliersPendingUpdate.add( ERPSupplier );
								
								TDimZtree ERPSupplierZtree = new TDimZtree();
								ERPSupplierZtree.setFID          ( corporationsPendingInsert.get(i).getId() );
								ERPSupplierZtree.setFISLEAF      ( "1" );
								ERPSupplierZtree.setFLEVEL       ( "2" );
								ERPSupplierZtree.setFNAME        ( corporationsPendingInsert.get(i).getName() );
								ERPSupplierZtree.setFNUMBER      ( existedSuppliers.get(j).getFNUMBER()  );
								ERPSupplierZtree.setFPARENTNAME  ( ncSupplierParentName );
								ERPSupplierZtree.setFPARENTNO	 ( ncSupplierPreNumber );
								ERPSupplierZtree.setFTYPE        ( "[1]供应商" );
								ERPSupplierZtree.setFTYPE_LANG   ( "[1]Supplier" );
								
								ERPTDimZtreePendingUpdate.add( ERPSupplierZtree );
								
								existedSuppliers.remove(j);
								corporationsPendingInsert.remove(i);
								i--;
								break;
							}
						}
					}
					
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
					/* currently we onle insert data which not existed. */
					if( ERPSuppliersPendingInsert.size() > 0 ){
						supplierDAO.insertSuppliers      ( ERPSuppliersPendingInsert );
					}
					if( ERPTDimZtreePendingInsert.size() > 0 ){
						supplierDAO.insertSupplierZTrees ( ERPTDimZtreePendingInsert );
					}
					if(ERPSuppliersPendingUpdate.size() > 0 ){
						supplierDAO.updateSuppliers( ERPSuppliersPendingUpdate );
					}
					if(ERPTDimZtreePendingUpdate.size() > 0 ){
						supplierDAO.updateSupplierZTrees( ERPTDimZtreePendingUpdate );
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
