package com.zhongzhou.Excavator.DAO.mongo.NC;


import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Service;

@Service( "mongo.NCItemDAO" )
public class NCItemDAO {

	@Resource(name="mongoMorphiaDataStore")
	Datastore  mongoMorphiaDataStore;
	
	public void insertItem( com.zhongzhou.Excavator.model.NC.Item item ){
		
		mongoMorphiaDataStore.save( item );
	}
	
	public void insertItems( List<com.zhongzhou.Excavator.model.NC.Item> items ){
		
		mongoMorphiaDataStore.save( items );
	}
	
}

