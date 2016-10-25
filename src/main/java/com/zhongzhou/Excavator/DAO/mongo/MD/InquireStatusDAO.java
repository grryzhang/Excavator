package com.zhongzhou.Excavator.DAO.mongo.MD;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.Accumulator;
import org.mongodb.morphia.aggregation.Group;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.model.BI.BIReportMongoData;
import com.zhongzhou.Excavator.model.BI.InquireStatusSearchParameters;
import com.zhongzhou.Excavator.model.BI.InquiryStatus;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@Service( DAOBeanNameList.mongo_md_inquireStatus )
public class InquireStatusDAO {

		@Resource(name=DataSourceList.MONGO_MD_DOCUMENTS)
		Datastore  mongoMorphiaDataStore;
		
		
		public List< BIReportMongoData<InquiryStatus> > selectLatestReport( InquireStatusSearchParameters searchParameters ) {
			
			Query fixedQuery = this.fixQuery(searchParameters );
			
			if( searchParameters.limit != null && searchParameters.start!=null ){
				fixedQuery.limit(searchParameters.limit).offset( searchParameters.start );
			}

			List< BIReportMongoData<InquiryStatus> > orderStatus = fixedQuery.asList();
			
			return orderStatus;
		}
		
		public long selectLatestReportCount( InquireStatusSearchParameters searchParameters ) {
			
			Query fixedQuery = this.fixQuery(searchParameters);
			long result = fixedQuery.countAll();
			
			return result;
		}
		
		public List<CountResult> groupLatestReportByBuyer() {
			
			BIReportMongoData<InquiryStatus> lastOrderStatus = mongoMorphiaDataStore
					.find( (new BIReportMongoData<InquiryStatus>()).getClass() )
					.filter( "modelClassName", InquiryStatus.class.getName() )
					.order("-insertTime").get();
				
			Query< BIReportMongoData<InquiryStatus> > query =
					(Query<BIReportMongoData<InquiryStatus>>)mongoMorphiaDataStore
					.createQuery( (new BIReportMongoData<InquiryStatus>() ).getClass() )
					.disableValidation();
			
			query.filter( "insertTime", lastOrderStatus.getInsertTime() );
			
			Iterator<InquireStatusDAO.CountResult> result = mongoMorphiaDataStore
					.createAggregation( (new BIReportMongoData<InquiryStatus>() ).getClass() )
				    .match( query )
					.group( "data.customerBuyer", Group.grouping("count", new Accumulator("$sum", 1) ) )
					.aggregate(CountResult.class);
			
			List<CountResult> results = new ArrayList<CountResult>();
			while( result.hasNext() ){
				results.add( result.next() );
			}
			
			return results;
		}
		
		@Entity 
	    public static class CountResult { 
	 
	        @Id 
	        public String customerBuyer; 
	        public Integer count; 
	    } 
		
		private Query<BIReportMongoData<InquiryStatus>> fixQuery( InquireStatusSearchParameters searchParameters ){
			
			BIReportMongoData<InquiryStatus> lastOrderStatus = mongoMorphiaDataStore
					.find( (new BIReportMongoData<InquiryStatus>()).getClass() )
					.filter( "modelClassName", InquiryStatus.class.getName() )
					.order("-insertTime").get();
				
			Query< BIReportMongoData<InquiryStatus> > query =
					(Query<BIReportMongoData<InquiryStatus>>)mongoMorphiaDataStore
					.createQuery( (new BIReportMongoData<InquiryStatus>() ).getClass() )
					.disableValidation();
			
			query.filter( "insertTime", lastOrderStatus.getInsertTime() );
			
			if( searchParameters.fuzzyQuerys != null && searchParameters.fuzzyQuerys.length() > 0 ){
				query.and( 
						query.or(
								query.criteria("data.inquiryNumber").contains( searchParameters.fuzzyQuerys ),
								query.criteria("data.customerBuyer").contains( searchParameters.fuzzyQuerys ),
								query.criteria("data.itemNameEN").contains( searchParameters.fuzzyQuerys )
						) 
				);		
			}	
			
			return query;
			
		}
}
