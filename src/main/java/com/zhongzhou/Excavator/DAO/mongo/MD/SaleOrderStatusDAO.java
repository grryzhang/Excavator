package com.zhongzhou.Excavator.DAO.mongo.MD;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.Accumulator;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.aggregation.Group;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.model.BI.BIReportMongoData;
import com.zhongzhou.Excavator.model.BI.Count;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatus;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatusSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@Service( DAOBeanNameList.mongo_md_saleOrderStatus )
public class SaleOrderStatusDAO {

		@Resource(name=DataSourceList.MONGO_MD_DOCUMENTS)
		Datastore  mongoMorphiaDataStore;
		
		public static final List<String> status = new ArrayList<String>(
			Arrays.asList(
				"01b94344-7000-4000-8000-000553ad1001", //in production
				"01b94344-7000-4000-8000-000553ad1002", //in clearance
				"01b94344-7000-4000-8000-000553ad1003"  //in shipment
			)		
		);
		
		public List< BIReportMongoData<SaleOrderStatus> > selectLatestReport( SaleOrderStatusSearchParameters searchParameters ) {
			
			Query fixedQuery = this.fixQuery(searchParameters );
			
			if( searchParameters.limit != null && searchParameters.start!=null ){
				fixedQuery.limit(searchParameters.limit).offset( searchParameters.start );
			}

			List< BIReportMongoData<SaleOrderStatus> > orderStatus = fixedQuery.asList();
			
			return orderStatus;
		}
		
		public long selectLatestReportCount( SaleOrderStatusSearchParameters searchParameters ) {
			
			Query fixedQuery = this.fixQuery(searchParameters);
			long result = fixedQuery.countAll();
			
			return result;
		}
		
		public long getStatusCount( SaleOrderStatusSearchParameters searchParameters ){
			
			Class<Count> entityClass = Count.class;
			
			AggregationPipeline pipeline = mongoMorphiaDataStore.createAggregation( (new BIReportMongoData<SaleOrderStatus>()).getClass() )
					.match( this.fixQuery( searchParameters ) )
					.group("data.customerOrderId", Group.grouping("count", new Accumulator("$sum", 1) ) );
			Iterator<Count> aggregation = pipeline.aggregate( entityClass );
			
			System.out.println( aggregation.next().count );
			
			return 0;
		}
		
		private Query<BIReportMongoData<SaleOrderStatus>> fixQuery( SaleOrderStatusSearchParameters searchParameters ){
			
			BIReportMongoData<SaleOrderStatus> lastOrderStatus = mongoMorphiaDataStore
					.find( (new BIReportMongoData<SaleOrderStatus>()).getClass() )
					.filter( "modelClassName", SaleOrderStatus.class.getName() )
					.order("-insertTime").get();
				
			Query< BIReportMongoData<SaleOrderStatus> > query =
					(Query<BIReportMongoData<SaleOrderStatus>>)mongoMorphiaDataStore
					.createQuery( (new BIReportMongoData<SaleOrderStatus>() ).getClass() )
					.disableValidation();
			
			query.filter( "insertTime", lastOrderStatus.getInsertTime() );
			
			if( searchParameters.status != null ){
				
				if( status.get(1).equals( searchParameters.status ) ){
					query.criteria("data.productionEndTime").exists().criteria("data.productionEndTime").notEqual("");
					query.criteria("data.shipmentTime").exists().criteria("data.shipmentTime").equal("");
				}else if( status.get(2).equals(  searchParameters.status ) ){
					query.criteria("data.shipmentTime").exists().criteria("data.shipmentTime").notEqual("");
				}
			}
			
			if( searchParameters.fuzzyQuerys != null && searchParameters.fuzzyQuerys.length() > 0 ){
				query.and( 
						query.or( 
								query.criteria("data.customerOrderId").contains( searchParameters.fuzzyQuerys ),
								query.criteria("data.customerBuyer").contains( searchParameters.fuzzyQuerys ),
								query.criteria("data.itemName").contains( searchParameters.fuzzyQuerys ),
								query.criteria("data.itemStandard").contains( searchParameters.fuzzyQuerys ),
								query.criteria("data.customerBuyer").contains( searchParameters.fuzzyQuerys )
						) 
				);		
			}	

			return query;
			
		}
}
