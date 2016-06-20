package com.zhongzhou.Excavator.DAO.mongo.NC;


import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@Service( DAOBeanNameList.mongo_nc_price )
public class NCPriceDAO {

	@Resource(name=DataSourceList.MONGO_MD_DOCUMENTS)
	Datastore  mongoMorphiaDataStore;
	
	public void insertPrice( com.zhongzhou.Excavator.model.NC.Price price ){
		
		mongoMorphiaDataStore.save( price );
	}
	
	public void insertPrices( List<com.zhongzhou.Excavator.model.NC.Price> prices ){
		
		mongoMorphiaDataStore.save( prices );
	}
	
}