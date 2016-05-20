package com.zhongzhou.Excavator.Exception;

import java.util.UUID;

@SuppressWarnings("serial")
public class ServiceRuntimeException extends RuntimeException {
	
	private String exceptionId;

	public ServiceRuntimeException(String message){
		super(message);
		this.setExceptionId( UUID.randomUUID().toString() );
	}

	public ServiceRuntimeException(String message,Throwable cause){
		
		super(message,cause);
		
		if( cause instanceof ServiceRuntimeException ){
			this.setExceptionId( ((ServiceRuntimeException)cause).getExceptionId()  );
		}else{
			this.setExceptionId( UUID.randomUUID().toString() );
		}
	}

	public ServiceRuntimeException(Throwable cause){
		super(cause);
		
		if( cause instanceof ServiceRuntimeException ){
			this.setExceptionId( ((ServiceRuntimeException)cause).getExceptionId()  );
		}else{
			this.setExceptionId( UUID.randomUUID().toString() );
		}
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}
}
