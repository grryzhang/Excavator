package com.zhongzhou.common.model.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

public class JsonResponse implements Serializable {
	
	private static final long serialVersionUID = 571211199485773026L;

	private boolean success = true;
	
	private boolean doRedirect = false;
	
	private String redirectUrl;
		
	private String actionMessage;
	
	private String actionType;
	
	private Object data;
	
	private int totalResult;
	
	private Map<String,Object> otherAttributes;
	
	public JsonResponse(){
		
	}
	
	public <T> JsonResponse( Set<ConstraintViolation<T>> beanValidationFailures ){
		
		this.success = false;
		this.actionType = "validation";
		data = new HashMap<String,Object>();
		StringBuffer message = new StringBuffer("<p>");
				
        for (ConstraintViolation<?> failure : beanValidationFailures) {  
        	message.append( failure.getMessage() + "<p>" );
        }  
        this.actionMessage = message.toString();
	}
	
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isDoRedirect() {
		return doRedirect;
	}

	public void setDoRedirect(boolean doRedirect) {
		this.doRedirect = doRedirect;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getActionMessage() {
		return actionMessage;
	}

	public void setActionMessage(String actionMessage) {
		this.actionMessage = actionMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	} 
	
	public void putData( Object data ){
		
		this.data = data;
	}

	public Map<String, Object> getOtherAttributes() {
		return otherAttributes;
	}

	public void setOtherAttributes(String key , Object otherAttribute) {
		if( this.otherAttributes == null ){
			this.otherAttributes = new HashMap<String,Object>();
		}
		this.otherAttributes.put(key, otherAttribute);
	}
	
	public void setOtherAttributes(Map<String, Object> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	
}
