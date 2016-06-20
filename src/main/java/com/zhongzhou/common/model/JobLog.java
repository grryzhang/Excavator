package com.zhongzhou.common.model;

import java.util.List;

public interface JobLog {

	public String setId();
	
	public String getId();
	
	public List<ServiceResult> getAllResult();
	
	public ServiceResult getServiceResult( String logId  );
	
	public void appendNewResult( ServiceResult result );
}
