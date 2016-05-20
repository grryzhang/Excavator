package com.zhongzhou.Excavator.DAO.postgresql;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.ItemCategory;
import com.zhongzhou.Excavator.model.ItemCategoryMapping;
import com.zhongzhou.Excavator.model.ItemCategorySearchParameters;
import com.zhongzhou.Excavator.model.Item;
import com.zhongzhou.Excavator.model.ItemMapping;
import com.zhongzhou.Excavator.model.ItemSearchParameters;
import com.zhongzhou.Excavator.model.ItemCategoryMappingSearchParameters;
import com.zhongzhou.Excavator.springsupport.DataSourceList;

@DataSource( dataSource=DataSourceList.MASTER_POSTGRESQL )  
@Service ( "postgresql.ItemDAO" )
public interface ItemDAO {
	
	public void insertItems( List<Item> items ) throws SQLException;
	
	public void updateItems( List<Item> items ) throws SQLException;
	
	public void insertItemMappings( List<ItemMapping> itemMappings ) throws SQLException;
	
	public List<ItemMapping> selectItemMappings( ItemSearchParameters searchParameters ) throws SQLException;
	
	public void insertItemCategory( ItemCategory itemCategory ) throws SQLException;
	
	public List< ItemCategory > selectItemCategorys( ItemCategorySearchParameters searchParameters ) throws SQLException;

	public void updateIsLeaf() throws SQLException;
	
	public void updateItemCategory( ItemCategory itemCategory ) throws SQLException;
	
	public void insertItemCategoryMapping( ItemCategoryMapping itemCategoryMapping) throws SQLException;
	
	public List< ItemCategoryMapping > selectItemCategorysMapping( ItemCategoryMappingSearchParameters searchParams ) throws SQLException;
}
