package com.zhongzhou.Excavator.service.migration.mailMonitor;

import java.io.InputStream;

import com.zhongzhou.common.Exception.ServiceRuntimeException;

public interface MailDataParser {

	public void processMailAttachment( InputStream in ) throws ServiceRuntimeException;
}
