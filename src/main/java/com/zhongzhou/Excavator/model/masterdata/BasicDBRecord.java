package com.zhongzhou.Excavator.model.masterdata;

import java.io.Serializable;
import java.sql.Timestamp;

public class BasicDBRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 539253001998507543L;

	private Timestamp createTime;
	
	private Timestamp lastUpdateTime;
	
	private String lastUpdateUserId;

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(String lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}
}
