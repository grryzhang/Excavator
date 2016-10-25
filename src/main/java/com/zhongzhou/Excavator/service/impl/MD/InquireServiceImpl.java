package com.zhongzhou.Excavator.service.impl.MD;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Header;
import com.zhongzhou.Excavator.DAO.mongo.MD.InquireStatusDAO;
import com.zhongzhou.Excavator.DAO.mongo.MD.SaleOrderStatusDAO;
import com.zhongzhou.Excavator.DAO.mongo.MD.InquireStatusDAO.CountResult;
import com.zhongzhou.Excavator.model.BI.BIReportMongoData;
import com.zhongzhou.Excavator.model.BI.InquireStatusSearchParameters;
import com.zhongzhou.Excavator.model.BI.InquiryStatus;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatus;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatusHead;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatusSearchParameters;
import com.zhongzhou.Excavator.service.MD.InquireService;
import com.zhongzhou.Excavator.service.MD.OrderService;
import com.zhongzhou.Excavator.springsupport.injectlist.DAOBeanNameList;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.Exception.ServiceRuntimeException;
import com.zhongzhou.common.util.BeanUtil;

@Service(ServiceNameList.MD_Inquire_Service)
public class InquireServiceImpl implements InquireService {

	@Resource(name=DAOBeanNameList.mongo_md_inquireStatus)
	InquireStatusDAO inquireStatusDAO;	
	
	@Override
	public List<InquiryStatus> getLatestReport( InquireStatusSearchParameters searchParameters ) throws ServiceRuntimeException {
		
		try{
			
			List< BIReportMongoData<InquiryStatus> > status = inquireStatusDAO.selectLatestReport( searchParameters );
			
			List<InquiryStatus> list = new ArrayList<InquiryStatus>();
			
			for( BIReportMongoData<InquiryStatus> report : status ){
				
				list.add( report.getData() );
			}
			
			return list;
			
		}catch( Exception e ){
			
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override
	public long getLatestReportCount( InquireStatusSearchParameters searchParameters ) throws ServiceRuntimeException {
		
		try{
			
			long result = inquireStatusDAO.selectLatestReportCount( searchParameters );
			
			return result;
			
		}catch( Exception e ){
			
			throw new ServiceRuntimeException(e);
		}
	}
	
	@Override
	public List<CountResult> groupLatestReportByBuyer() throws ServiceRuntimeException {
		
		try{
			
			List<CountResult> result = inquireStatusDAO.groupLatestReportByBuyer();
			
			return result;
			
		}catch( Exception e ){
			
			throw new ServiceRuntimeException(e);
		}
	}
}
