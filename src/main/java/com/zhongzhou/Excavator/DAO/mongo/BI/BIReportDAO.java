package com.zhongzhou.Excavator.DAO.mongo.BI;

import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.model.BI.BIReportMongoData;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@Service( DAOBeanNameList.mongo_bi_BIReport )
public class BIReportDAO {

		@Resource(name=DataSourceList.MONGO_MD_DOCUMENTS)
		Datastore  mongoMorphiaDataStore;
		
		public <T> void insertOneReport( BIReportMongoData<T> data ){
			
			mongoMorphiaDataStore.save( data );
		}
		
		public <T> void insertOneReports( List< BIReportMongoData<T> > data ){
			
			mongoMorphiaDataStore.save( data );
		}
}
