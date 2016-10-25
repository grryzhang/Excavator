package com.zhongzhou.Excavator.model.masterdata;

import java.util.ArrayList;
import java.util.List;

public class PriceCategorySearchParameter {

	private String categoryName;
	
	private String ownerCorpId;
	
	private List<String> categoryNames;
	
	private List<String> ownerCorpIds;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
		if( categoryNames == null ){
			categoryNames = new ArrayList<String>();
		}
		categoryNames.add( categoryName );
	}

	public String getOwnerCorpId() {
		return ownerCorpId;
	}

	public void setOwnerCorpId(String ownerCorpId) {
		this.ownerCorpId = ownerCorpId;
		if( ownerCorpIds == null ){
			ownerCorpIds = new ArrayList<String>();
		}
		ownerCorpIds.add( ownerCorpId );
	}

	public List<String> getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(List<String> categoryNames) {
		this.categoryNames = categoryNames;
	}

	public List<String> getOwnerCorpIds() {
		return ownerCorpIds;
	}

	public void setOwnerCorpIds(List<String> ownerCorpIds) {
		this.ownerCorpIds = ownerCorpIds;
	}
	
	
}
