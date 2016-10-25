package com.zhongzhou.Excavator.service.webdata;

import com.zhongzhou.common.Exception.ServiceRuntimeException;

public interface HttpService {

	public String HttpGetPage( String url ) throws ServiceRuntimeException;
		
}
