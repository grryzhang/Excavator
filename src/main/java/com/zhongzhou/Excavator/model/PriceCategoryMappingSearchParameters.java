package com.zhongzhou.Excavator.model;

import java.util.ArrayList;
import java.util.List;

public class PriceCategoryMappingSearchParameters {

	private String sourceId;
	
	private List<String> sourceIds;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
		this.sourceIds = new ArrayList<String>();
		sourceIds.add(sourceId);
	}

	public List<String> getSourceIds() {
		return sourceIds;
	}

	public void setSourceIds(List<String> sourceIds) {
		this.sourceIds = sourceIds;
	}
	
	
}
