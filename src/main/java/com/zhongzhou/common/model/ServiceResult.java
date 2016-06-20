package com.zhongzhou.common.model;

import java.sql.Timestamp;

public interface ServiceResult {
	
	public void setProcessStatus( String status );
	
	public String getProcessStatus();
	
	public void setServiceName( String serviceName );
	
	public String getServiceName();
	
	public void setProcessLog( String serviceName );
	
	public String getProcessLog();
	
	public void setExceptionLog( String serviceName );
	
	public String getExceptionLog();
	
	public void setStartTime( Timestamp startTime );
	
	public Timestamp getStartTime();
	
	public void setEndTime( Timestamp startTime );
	
	public Timestamp getEndTime();
}
