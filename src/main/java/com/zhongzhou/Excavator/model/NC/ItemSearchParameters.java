package com.zhongzhou.Excavator.model.NC;

import java.sql.Timestamp;

public class ItemSearchParameters {
	
	private Integer start = 0;
	
	private Integer end = 100;

	private Timestamp updateTimeStart;
	
	private Timestamp updateTimeEnd;

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public Timestamp getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Timestamp updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Timestamp getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Timestamp updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}
}
