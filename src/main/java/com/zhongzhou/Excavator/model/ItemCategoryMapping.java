package com.zhongzhou.Excavator.model;

public class ItemCategoryMapping {

	private String id;
	
	private String categoryId;
	
	private String sourceId;
	
	private String dataSource;
	
	private String sourceParentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getSourceParentId() {
		return sourceParentId;
	}

	public void setSourceParentId(String sourceParentId) {
		this.sourceParentId = sourceParentId;
	}
}
