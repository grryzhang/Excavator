package com.zhongzhou.common.model;

import java.sql.Timestamp;

public class ServiceResultBasicImpl implements ServiceResult {
	
	private String processStatus;
	
	private String serviceName;
	
	private String processLog;
	
	private String exceptionLog;
	
	private Timestamp startTime;
	
	private Timestamp endTime;

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getProcessLog() {
		return processLog;
	}

	public void setProcessLog(String processLog) {
		this.processLog = processLog;
	}

	public String getExceptionLog() {
		return exceptionLog;
	}

	public void setExceptionLog(String exceptionLog) {
		this.exceptionLog = exceptionLog;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
}
