package com.zhongzhou.Excavator.model;

import java.util.List;

public class CorporationSearchParameters {
	
	private List<String> ids;

	private Integer limit = 30;
	
	private Integer offset = 0;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}
}

