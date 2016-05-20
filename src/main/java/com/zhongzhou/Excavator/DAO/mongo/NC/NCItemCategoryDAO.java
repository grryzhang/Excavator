package com.zhongzhou.Excavator.DAO.mongo.NC;

import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Service;

@Service( "mongo.NCItemCategoryDAO" )
public class NCItemCategoryDAO {

	@Resource(name="mongoMorphiaDataStore")
	Datastore  mongoMorphiaDataStore;
	
	public void insertItemCategory( com.zhongzhou.Excavator.model.NC.ItemCategory itemCategory ){
		
		mongoMorphiaDataStore.save( itemCategory );
	}
	
	public void insertItemCategorys( List<com.zhongzhou.Excavator.model.NC.ItemCategory> itemCategorys ){
		
		mongoMorphiaDataStore.save( itemCategorys );
	}
	
}
