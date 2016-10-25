package com.zhongzhou.Excavator.DAO.postgresql.MD;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.masterdata.InterItem2Category;
import com.zhongzhou.Excavator.model.masterdata.Item;
import com.zhongzhou.Excavator.model.masterdata.ItemCategory;
import com.zhongzhou.Excavator.model.masterdata.ItemCategoryMapping;
import com.zhongzhou.Excavator.model.masterdata.ItemCategoryMappingSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.ItemCategorySearchParameters;
import com.zhongzhou.Excavator.model.masterdata.ItemMapping;
import com.zhongzhou.Excavator.model.masterdata.ItemPackageInfo;
import com.zhongzhou.Excavator.model.masterdata.ItemSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.ItemUnitMapping;
import com.zhongzhou.Excavator.model.masterdata.ItemUnitMappingSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@DataSource( dataSource=DataSourceList.MASTER_POSTGRESQL )  
@Service ( DAOBeanNameList.postgresql_md_item )
public interface ItemDAO {
	
	public void insertItems( List<Item> items ) throws SQLException;
	
	public void insertItemPackageInfos( List<ItemPackageInfo> itemPackageInfos ) throws SQLException;
	
	public void updateItems( List<Item> items ) throws SQLException;
	
	public List<Item> selectItems( ItemSearchParameters searchParameters ) throws SQLException;
	
	public List<ItemPackageInfo> selectItemPackageInfos ( ItemSearchParameters searchParameters ) throws SQLException;
	
	public Integer countItems( ItemSearchParameters searchParameters ) throws SQLException;
	
	public void insertItemMappings( List<ItemMapping> itemMappings ) throws SQLException;
	
	public List<ItemMapping> selectItemMappings( ItemSearchParameters searchParameters ) throws SQLException;
	
	public void insertItemCategory( ItemCategory itemCategory ) throws SQLException;
	
	public List< ItemCategory > selectItemCategorys( ItemCategorySearchParameters searchParameters ) throws SQLException;

	public List< ItemCategory > selectItemCategorysDeep( @Param(value="categoryIds") List<String> ids ) throws SQLException;
	
	public List< ItemCategory > selectItemCategorysAllParent( @Param(value="categoryIds") List<String> ids ) throws SQLException;
	
	public List<String> selectItemCategoryIdsOfCorporations ( @Param(value="corporationIds") List<String> ids ) throws SQLException;
	
	public void updateIsLeaf() throws SQLException;
	
	public void updateItemCategory( ItemCategory itemCategory ) throws SQLException;
	
	public void insertItemCategoryMapping( ItemCategoryMapping itemCategoryMapping) throws SQLException;
	
	public List< ItemCategoryMapping > selectItemCategorysMapping( ItemCategoryMappingSearchParameters searchParams ) throws SQLException;
	
	public List<ItemUnitMapping> selectItemUnitMappings( ItemUnitMappingSearchParameters searchParameters ) throws SQLException;
	
	public void insertInterItem2Category( List<InterItem2Category> ii2c ) throws SQLException;
}
