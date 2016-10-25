package com.zhongzhou.Excavator.DAO.postgresql.MD;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSessionException;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.masterdata.Price;
import com.zhongzhou.Excavator.model.masterdata.PriceCategory;
import com.zhongzhou.Excavator.model.masterdata.PriceCategoryMapping;
import com.zhongzhou.Excavator.model.masterdata.PriceCategoryMappingSearchParameters;
import com.zhongzhou.Excavator.model.masterdata.PriceMapping;
import com.zhongzhou.Excavator.model.masterdata.PriceMappingSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@DataSource( dataSource=DataSourceList.MASTER_POSTGRESQL )  
@Service ( DAOBeanNameList.postgresql_md_price )
public interface PriceDAO {
	
	public void insertPrices( List<Price> price ) throws SQLException;
	
	public void updatePrice( List<Price> price ) throws SQLException;
	
	public void insertPriceMappings( List<PriceMapping> PriceMapping ) throws SQLException;
	
	public List<PriceMapping> selectPriceMappings( PriceMappingSearchParameters searchParameters ) throws SQLException;

	public List<PriceCategoryMapping> selectPriceCategoryMappings( PriceCategoryMappingSearchParameters searchParameters ) throws SQLException;
	
	public void insertPriceCategoryMappings( List<PriceCategoryMapping> priceCategroyMappings ) throws SQLException;
	
	public void insertPriceCategorys( List<PriceCategory> priceCategorys ) throws SQLException;
	
	public List<PriceCategory> selectPriceCategorys() throws SQLException;
	
	public void updatePriceCategory( List<PriceCategory> priceCategorys ) throws SQLException;
}
