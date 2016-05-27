package com.zhongzhou.Excavator.model;

public class PriceCategory {

	private String id;
	
	private String categoryName;
	
	private String ownerCorpId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getOwnerCorpId() {
		return ownerCorpId;
	}

	public void setOwnerCorpId(String ownerCorpId) {
		this.ownerCorpId = ownerCorpId;
	}
}
