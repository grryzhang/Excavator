package com.zhongzhou.Excavator.DAO.mongo;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.model.system.SysErrorLog;

@Service( "mongo.SysErrorLogDAO" )
public class SysErrorLogDAO {

	@Resource(name="mongoMorphiaDataStore")
	Datastore  mongoMorphiaDataStore;
	
	public void insertLog( SysErrorLog sysErrorLog ){
		
		mongoMorphiaDataStore.save( sysErrorLog );
	}
}
