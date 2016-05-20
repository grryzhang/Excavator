package com.zhongzhou.Excavator.DAO.mongo.NC;

import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Service;

@Service( "mongo.NCCorporationDAO" )
public class NCCorporationDAO {

	@Resource(name="mongoMorphiaDataStore")
	Datastore  mongoMorphiaDataStore;
	
	public void insertCorporation( com.zhongzhou.Excavator.model.NC.CorporationDoc corporationDoc ){
		
		mongoMorphiaDataStore.save( corporationDoc );
	}
	
	public void insertCorporation( List<com.zhongzhou.Excavator.model.NC.CorporationDoc> corporationDocs ){
		
		mongoMorphiaDataStore.save( corporationDocs );
	}
	
}
