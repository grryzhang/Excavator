package com.zhongzhou.Excavator.service.MD;

import java.util.List;

import com.zhongzhou.Excavator.DAO.mongo.MD.InquireStatusDAO.CountResult;
import com.zhongzhou.Excavator.model.BI.InquireStatusSearchParameters;
import com.zhongzhou.Excavator.model.BI.InquiryStatus;
import com.zhongzhou.common.Exception.ServiceRuntimeException;

public interface InquireService {

	public List<InquiryStatus> getLatestReport(InquireStatusSearchParameters searchParameters) throws ServiceRuntimeException;

	public long getLatestReportCount(InquireStatusSearchParameters searchParameters) throws ServiceRuntimeException;

	public List<CountResult> groupLatestReportByBuyer() throws ServiceRuntimeException;

}
