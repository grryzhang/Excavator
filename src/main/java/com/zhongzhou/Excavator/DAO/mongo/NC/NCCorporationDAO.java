package com.zhongzhou.Excavator.DAO.mongo.NC;

import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@Service( DAOBeanNameList.mongo_nc_corporation )
public class NCCorporationDAO {

	@Resource(name=DataSourceList.MONGO_MD_DOCUMENTS)
	Datastore  mongoMorphiaDataStore;
	
	public void insertCorporation( com.zhongzhou.Excavator.model.NC.CorporationDoc corporationDoc ){
		
		mongoMorphiaDataStore.save( corporationDoc );
	}
	
	public void insertCorporation( List<com.zhongzhou.Excavator.model.NC.CorporationDoc> corporationDocs ){
		
		mongoMorphiaDataStore.save( corporationDocs );
	}
	
}
