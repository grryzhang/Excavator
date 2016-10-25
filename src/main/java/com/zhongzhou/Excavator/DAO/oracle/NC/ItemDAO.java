package com.zhongzhou.Excavator.DAO.oracle.NC;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.NC.Item;
import com.zhongzhou.Excavator.model.NC.ItemCategory;
import com.zhongzhou.Excavator.model.NC.ItemCategorySearchParameters;
import com.zhongzhou.Excavator.model.NC.ItemSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@DataSource( dataSource=DataSourceList.NC_ORACLE )  
@Service( DAOBeanNameList.oracle_nc_item )
public interface ItemDAO {
	
	public List<Item> selectItemsWithRowNumber( ItemSearchParameters searchParameters ) throws SQLException;
	
	public List<Item> selectItems( ItemSearchParameters searchParameters ) throws SQLException;
	
	public int countItems( ItemSearchParameters searchParameters ) throws SQLException;

	public List< ItemCategory > selectItemCategorys( ItemCategorySearchParameters searchParameters ) throws SQLException;
	
	public List<ItemCategory> selectItemCategorysWithRowNumber ( ItemCategorySearchParameters searchParameters ) throws SQLException;
	
	public int countItemCategorys() throws SQLException;
	
	public void updateItemsPackageInfo( List<Item> items ) throws SQLException; 
	
	public void updateItemsCustomInfo( List<Item> items ) throws SQLException; 
}
