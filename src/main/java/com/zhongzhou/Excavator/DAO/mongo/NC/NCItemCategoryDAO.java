package com.zhongzhou.Excavator.DAO.mongo.NC;

import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@Service( DAOBeanNameList.mongo_nc_itemCategory )
public class NCItemCategoryDAO {

	@Resource(name=DataSourceList.MONGO_MD_DOCUMENTS)
	Datastore  mongoMorphiaDataStore;
	
	public void insertItemCategory( com.zhongzhou.Excavator.model.NC.ItemCategory itemCategory ){
		
		mongoMorphiaDataStore.save( itemCategory );
	}
	
	public void insertItemCategorys( List<com.zhongzhou.Excavator.model.NC.ItemCategory> itemCategorys ){
		
		mongoMorphiaDataStore.save( itemCategorys );
	}
	
}