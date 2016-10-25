package com.zhongzhou.Excavator.model.masterdata;

import java.util.List;

public class CorporationStatisticsSearchParameters {

	private String status;
	
	private List<String> categoryIds;
	
	private List<String> resources;
	
	private Integer limit = 30;
	
	private Integer start = 0;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<String> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public List<String> getResources() {
		return resources;
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
}
