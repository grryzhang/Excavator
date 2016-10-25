package com.zhongzhou.Excavator.model.masterdata;

import java.util.List;

public class CorporationSearchParameters {
	
	private List<String> ids;
	
	private List<String> names;

	private List<String> enames;
	
	private List<String> corporationGroups;
	
	private List<String> itemCategoryIds;
	
	private Integer limit = 30;
	
	private Integer start = 0;

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

	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<String> getEnames() {
		return enames;
	}

	public void setEnames(List<String> enames) {
		this.enames = enames;
	}

	public List<String> getCorporationGroups() {
		return corporationGroups;
	}

	public void setCorporationGroups(List<String> corporationGroups) {
		this.corporationGroups = corporationGroups;
	}

	public List<String> getItemCategoryIds() {
		return itemCategoryIds;
	}

	public void setItemCategoryIds(List<String> itemCategoryIds) {
		this.itemCategoryIds = itemCategoryIds;
	}
}


