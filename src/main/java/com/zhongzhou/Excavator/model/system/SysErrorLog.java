package com.zhongzhou.Excavator.model.system;

import java.sql.Timestamp;

public class SysErrorLog {
	
	public String exceptionId;

	public String className;
	
	public String classInstanceName;
	
	public String methodName;
	
	public String inputArgs;
	
	public String exceptionTrace;
	
	public Timestamp exceptionTime;

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassInstanceName() {
		return classInstanceName;
	}

	public void setClassInstanceName(String classInstanceName) {
		this.classInstanceName = classInstanceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getInputArgs() {
		return inputArgs;
	}

	public void setInputArgs( String inputArgs ) {
		this.inputArgs = inputArgs;
	}

	public String getExceptionTrace() {
		return exceptionTrace;
	}

	public void setExceptionTrace(String exceptionTrace) {
		this.exceptionTrace = exceptionTrace;
	}

	public Timestamp getExceptionTime() {
		return exceptionTime;
	}

	public void setExceptionTime(Timestamp exceptionTime) {
		this.exceptionTime = exceptionTime;
	}
}
