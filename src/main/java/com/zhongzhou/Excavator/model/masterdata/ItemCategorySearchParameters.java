package com.zhongzhou.Excavator.model.masterdata;

import java.util.List;

public class ItemCategorySearchParameters {
	
	private Integer level;

	private List<String> sourceIds;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<String> getSourceIds() {
		return sourceIds;
	}

	public void setSourceIds(List<String> sourceIds) {
		this.sourceIds = sourceIds;
	}
}
