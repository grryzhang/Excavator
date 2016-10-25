package com.zhongzhou.Excavator.model.BI;

import org.mongodb.morphia.annotations.Entity;

@Entity( value="BIReports" )
public class BIReportMongoData<T> {

	private long insertTime;
	
	private String modelClassName;
	
	private Class<T> modelClass;
	
	private T data;

	public long getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}

	public String getModelClassName() {
		return modelClassName;
	}

	public void setModelClassName(String modelClassName) {
		this.modelClassName = modelClassName;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Class<T> getModelClass() {
		return modelClass;
	}

	public void setModelClass(Class<T> modelClass) {
		this.modelClass = modelClass;
	}
}
