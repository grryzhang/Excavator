package com.zhongzhou.Excavator.service.impl.MD;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.DAO.postgresql.MD.CorporationDAO;
import com.zhongzhou.Excavator.DAO.postgresql.MD.ItemDAO;
import com.zhongzhou.Excavator.model.masterdata.Corporation;
import com.zhongzhou.Excavator.model.masterdata.CorporationGrade;
import com.zhongzhou.Excavator.model.masterdata.CorporationSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.CorporationStatisticsSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.ItemCategory;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.Excavator.service.MD.CorporationService;
import com.zhongzhou.Excavator.service.MD.ItemService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;
import com.zhongzhou.common.util.BeanUtil;

@Service(ServiceNameList.MD_Corporation_Service)
public class CorporationServiceImpl implements CorporationService {
	
	@Resource( name = DAOBeanNameList.postgresql_md_corporation )
	CorporationDAO corporationDAO;
	
	@Resource ( name = DAOBeanNameList.postgresql_md_item )
	ItemDAO itemDAO;
	
	@Resource( name = ServiceNameList.MD_Item_Service)
	ItemService itemService;
	
	@Override
	public List<Corporation> selectItemCorporations( ItemSearchParameters searchParameters ) throws ServiceRuntimeException{
		
		try{
			ItemSearchParameters thisSearchParameters = itemService.prePrcessItemSearchParameter( searchParameters );
			thisSearchParameters.setStart(null);
			thisSearchParameters.setLimit(null);
			List< Corporation > result = corporationDAO.selectItemCorporations( thisSearchParameters );
			
			return result;
		} catch ( SQLException e ) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override
	public List<Corporation> selectCorporations( CorporationSearchParameters searchParameters ) throws ServiceRuntimeException{
		
		try{
			List< Corporation > result = corporationDAO.selectCorporations( searchParameters );
			
			for( Corporation corporation : result ){
				if( corporation.geteName() == null ) corporation.seteName( corporation.getName() );
				if( corporation.geteName() == null ) corporation.seteName( corporation.getShortName() );
			}
			
			return result;
		} catch ( SQLException e ) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override
	public List<Corporation> selectCorporationsBySameItemCategory( CorporationSearchParameters searchParameters  )throws ServiceRuntimeException{
		
		try{

			List<String> categoryIds = itemDAO.selectItemCategoryIdsOfCorporations ( searchParameters.getIds() );
			if( searchParameters.getItemCategoryIds() != null ){
				categoryIds.addAll( searchParameters.getItemCategoryIds() );
			}
			
			List<ItemCategory> categorys = itemDAO.selectItemCategorysDeep( categoryIds );

			categoryIds = new ArrayList<String>();
			for( ItemCategory category : categorys ){
				categoryIds.add( category.getId() );
			}
			
			List<String> realtedCategoryIds = itemService.getAllRelatedCategoryIds( categoryIds );
			
			CorporationSearchParameters currentSearchParameters = BeanUtil.beanCloneByJakson(searchParameters, CorporationSearchParameters.class);
			currentSearchParameters.setItemCategoryIds( realtedCategoryIds );
			currentSearchParameters.setIds( null );
			
			List<Corporation> corps = corporationDAO.selectCorporations(currentSearchParameters);
			
			return corps;
		} catch ( SQLException | IOException e ) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override
	public long countCorporationsBySameItemCategory( CorporationSearchParameters searchParameters  )throws ServiceRuntimeException{
		
		try{

			List<String> categoryIds = itemDAO.selectItemCategoryIdsOfCorporations ( searchParameters.getIds() );
			if( searchParameters.getItemCategoryIds() != null ){
				categoryIds.addAll( searchParameters.getItemCategoryIds() );
			}
			
			List<ItemCategory> categorys = itemDAO.selectItemCategorysDeep( categoryIds );

			categoryIds = new ArrayList<String>();
			for( ItemCategory category : categorys ){
				categoryIds.add( category.getId() );
			}
			
			List<String> realtedCategoryIds = itemService.getAllRelatedCategoryIds( categoryIds );
			
			CorporationSearchParameters currentSearchParameters = BeanUtil.beanCloneByJakson(searchParameters, CorporationSearchParameters.class);
			currentSearchParameters.setItemCategoryIds( realtedCategoryIds );
			currentSearchParameters.setIds( null );
			
			Long count = corporationDAO.countCorporations(currentSearchParameters);
			
			return count;
		} catch ( SQLException | IOException e ) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override
	public List<CorporationGrade> selectCorporationGrades( CorporationSearchParameters searchParameters )throws ServiceRuntimeException{
		
		try{
			List< CorporationGrade > result = corporationDAO.selectCorporationGrades( searchParameters );
			
			return result;
		} catch ( SQLException e ) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override
	public List<CorporationGrade> selectCorporationGradesBySameItemCategory( CorporationSearchParameters searchParameters  )throws ServiceRuntimeException{
		
		try{

			List<String> categoryIds = itemDAO.selectItemCategoryIdsOfCorporations ( searchParameters.getIds() );
			if( categoryIds == null || categoryIds.size() < 1 ) return null; 
			
			List<ItemCategory> categorys = itemDAO.selectItemCategorysDeep( categoryIds );
			categoryIds = new ArrayList<String>();
			for( ItemCategory category : categorys ){
				categoryIds.add( category.getId() );
			}
			
			List<String> realtedCategoryIds = itemService.getAllRelatedCategoryIds( categoryIds );
			
			CorporationSearchParameters currentSearchParameters = BeanUtil.beanCloneByJakson(searchParameters, CorporationSearchParameters.class);
			currentSearchParameters.setItemCategoryIds( realtedCategoryIds );
			currentSearchParameters.setIds( null );
			
			List<CorporationGrade> grades = corporationDAO.selectLatestCorporationGrades(currentSearchParameters);

			/*
			List<String> sameItemCategoryCorporationId = new ArrayList<String>();
			for( CorporationGrade grade : grades ){
				sameItemCategoryCorporationId.add( grade.getCorporationId() );
			}
			
			CorporationSearchParameters searchParameters = new  CorporationSearchParameters();
			searchParameters.setIds( sameItemCategoryCorporationId );
			List< Corporation > corporations = corporationDAO.selectCorporations( searchParameters );
			for( CorporationGrade grade : grades ){
				
				for( Corporation corporation : corporations ){
					if( grade.getCorporationId().equals( corporation.getId() ) ){
						grade.setCorporation( corporation );
						break;
					}
				}
			}
			*/
			
			return grades;
		} catch ( SQLException | IOException e ) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override
	public List< Map<String, String> > selectCorporationStatistics( CorporationStatisticsSearchParameters searchParameters ) throws ServiceRuntimeException{
		
		try{
			List<ItemCategory> categorys = itemDAO.selectItemCategorysDeep( searchParameters.getCategoryIds() );
			List<String> categoryIds = new ArrayList<String>();
			for( ItemCategory category : categorys ){
				categoryIds.add( category.getId() );
			}
			
			CorporationStatisticsSearchParameters localSearchParameters = BeanUtil.beanCloneByJakson(searchParameters, CorporationStatisticsSearchParameters.class );
			localSearchParameters.setCategoryIds( categoryIds );
			
			List< Map<String,String> > initials = corporationDAO.selectCorporationStatistics( localSearchParameters );
			
			localSearchParameters.setStatus("2");
			List< Map<String,String> > confirmes = corporationDAO.selectCorporationStatistics( localSearchParameters );
			
			localSearchParameters.setStatus("3");
			List< Map<String,String> > recommands = corporationDAO.selectCorporationStatistics( localSearchParameters );
			
			//List< Map<String,String> > finaResult = new ArrayList< Map<String,String> > (); 
			
			for( Map<String,String> initial : initials ){
				
				initial.put("initial", initial.get("sum") );
				for( Map<String,String> confirme : confirmes  ){
					if( confirme.get("categoryId").equals( initial.get("categoryId") ) 
							&& confirme.get("resource") != null
							&& initial.get("resource") != null
							&& confirme.get("resource").equals( initial.get("resource") ) ){
						initial.put("confirmed", confirme.get("sum") );
						break;
					}
				}
				
				for( Map<String,String> recommand : recommands  ){
					if( recommand.get("categoryId").equals( initial.get("categoryId") ) 
							&& recommand.get("resource") != null
							&& initial.get("resource") != null
							&& recommand.get("resource").equals( initial.get("resource") ) ){
						initial.put("recommand", recommand.get("sum") );
						break;
					}
				}
			}
			
			return initials;
			
		} catch ( SQLException | IOException e ) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override 
	public List<Corporation> selectScreenCorporation ( CorporationStatisticsSearchParameters searchParameters ) throws ServiceRuntimeException{
		
		try{
			return corporationDAO.selectScreenCorporation(searchParameters);
		}catch( SQLException e ){
			
			throw new ServiceRuntimeException(e);
		}
	}
	
	
	@Override
	public List<String> selectAllCorporationSource() throws ServiceRuntimeException{
		
		try{
			
			return corporationDAO.selectAllCorporationSource();
			
		} catch ( SQLException e ) {
			throw new ServiceRuntimeException(e);
		}
	}
}
