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
	
	public void insertItem( com.zhongzhou.Excavator.model.NC.Item item ){
		
		mongoMorphiaDataStore.save( item );
	}
	
	public void insertItems( List<com.zhongzhou.Excavator.model.NC.Item> items ){
		
		mongoMorphiaDataStore.save( items );
	}
	
}