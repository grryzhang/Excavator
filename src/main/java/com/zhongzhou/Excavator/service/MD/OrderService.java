package com.zhongzhou.Excavator.service.MD;

import java.util.List;

import com.zhongzhou.Excavator.model.BI.SaleOrderStatusHead;
import com.zhongzhou.Excavator.model.BI.SaleOrderStatusSearchParameters;
import com.zhongzhou.common.Exception.ServiceRuntimeException;

public interface OrderService {

	public List<SaleOrderStatusHead> getLatestReport(SaleOrderStatusSearchParameters searchParameters) throws ServiceRuntimeException;

	public long getLatestReportCount(SaleOrderStatusSearchParameters searchParameters) throws ServiceRuntimeException;
}
