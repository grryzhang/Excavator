package com.zhongzhou.Excavator.service.impl.MD;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Header;
import com.zhongzhou.Excavator.DAO.mongo.MD.SaleOrderStatusDAO;
import com.zhongzhou.Excavator.model.BI.BIReportMongoData;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatus;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatusHead;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatusSearchParameters;
import com.zhongzhou.Excavator.service.MD.OrderService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;
import com.zhongzhou.common.util.BeanUtil;

@Service(ServiceNameList.MD_Order_Service)
public class OrderServiceImpl implements OrderService {

	@Resource(name=DAOBeanNameList.mongo_md_saleOrderStatus)
	SaleOrderStatusDAO saleOrderStatusDAO;	
	
	@Override
	public List<SaleOrderStatusHead> getLatestReport( SaleOrderStatusSearchParameters searchParameters ) throws ServiceRuntimeException {
		
		try{
			
			List< BIReportMongoData<SaleOrderStatus> > saleOrderStatus = saleOrderStatusDAO.selectLatestReport( searchParameters );
			
			List<SaleOrderStatusHead> list = new ArrayList<SaleOrderStatusHead>();
			
			String orderId = "";
			SaleOrderStatusHead head = new SaleOrderStatusHead();
			for( BIReportMongoData<SaleOrderStatus> report : saleOrderStatus ){
				
				if( !orderId.equals( report.getData().getCustomerOrderId() ) && report.getData().getCustomerOrderId()!=null ){
					head = new SaleOrderStatusHead();
					head = BeanUtil.beanCloneByJakson( report.getData(), head.getClass() );
					head.items = new ArrayList<SaleOrderStatus>();
					orderId = report.getData().getCustomerOrderId();
					
					list.add(head);
				}
				
				if( orderId.equals( report.getData().getCustomerOrderId() ) ){
					
					head.items.add( report.getData() );
				}
			}
			
			return list;
			
		}catch( Exception e ){
			
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override
	public long getLatestReportCount( SaleOrderStatusSearchParameters searchParameters ) throws ServiceRuntimeException {
		
		try{
			
			long result = saleOrderStatusDAO.selectLatestReportCount( searchParameters );
			
			return result;
			
		}catch( Exception e ){
			
			throw new ServiceRuntimeException(e);
		}
	}
		
}
