package com.zhongzhou.Excavator.model.masterdata;

import java.util.ArrayList;
import java.util.List;

public class CorporationGradeSearchParameters {

	private String corporationId;
	
	private List<String> corporationIds;

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
		this.corporationIds = new ArrayList<String>();
		corporationIds.add(corporationId);
	}

	public List<String> getCorporationIds() {
		return corporationIds;
	}

	public void setCorporationIds(List<String> corporationIds) {
		this.corporationIds = corporationIds;
	}
}
