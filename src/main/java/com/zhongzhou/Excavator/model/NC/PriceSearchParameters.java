package com.zhongzhou.Excavator.model.NC;

import java.sql.Timestamp;

public class PriceSearchParameters {
	
	private String priceType;

	private Integer start;
	
	private Integer end;
	
	private Timestamp startTS;
	
	private Timestamp endTS;

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

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

	public Timestamp getStartTS() {
		return startTS;
	}

	public void setStartTS(Timestamp startTS) {
		this.startTS = startTS;
	}

	public Timestamp getEndTS() {
		return endTS;
	}

	public void setEndTS(Timestamp endTS) {
		this.endTS = endTS;
	}
}
