package com.zhongzhou.Excavator.model;

import java.util.ArrayList;
import java.util.List;

public class CorporationIntegrationMappingSearchParameters {

	private List<String> sourceIds;
	
	private String sourceId;

	public List<String> getSourceIds() {
		return sourceIds;
	}

	public void setSourceIds(List<String> sourceIds) {
		this.sourceIds = sourceIds;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
		
		sourceIds = new ArrayList<String>();
		sourceIds.add(sourceId);
	}
}
