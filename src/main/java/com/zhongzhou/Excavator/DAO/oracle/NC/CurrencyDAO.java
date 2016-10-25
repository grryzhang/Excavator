package com.zhongzhou.Excavator.DAO.oracle.NC;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.Annotation.DataSource;
import com.zhongzhou.Excavator.model.NC.CurrencyRate;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@DataSource( dataSource=DataSourceList.NC_ORACLE )  
@Service( DAOBeanNameList.oracle_nc_currency )
public interface CurrencyDAO {

	/**
	 * Here the domesticCurrencyCode is the currency which should be the "1" 
	 * <br>For example, if your want get the exchange between the USD and RMB,
	 * <br>If the domesticCurrencyCode is USD and foreignCurrencyCode is CNY, you will get 6.XXXX.
	 * <br>Otherwise, you will get 0.158XXX.
	 * @param domesticCurrencyCode  ForExample 'USD', 'CNY'
	 * @param foreignCurrencyCode   ForExample 'USD', 'CNY'
	 * @return
	 * @throws SQLException
	 */
	public CurrencyRate selectExchangeRate( 
			@Param( value="domesticCurrencyCode") String domesticCurrencyCode, 
			@Param( value="foreignCurrencyCode" ) String foreignCurrencyCode ) throws SQLException;
}
