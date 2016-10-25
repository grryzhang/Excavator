package com.zhongzhou.Excavator.model.masterdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ItemSearchParameters {
	
	private List<String> ids;

	private List<String> sourceIds;
	
	private List<String> fuzzyQuerys;
	
	private String itemCode;
	
	private String itemType;
	
	private String customerCorpId;
	
	private String priceListCustomerCorpId;
	
	private List<String> supplierCorpIds;
	
	private List<String> categoryIds;

	private Integer orderLevel;
	
	private Integer start = 0;
	
	private Integer limit = 30;
	
	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public List<String> getSourceIds() {
		return sourceIds;
	}

	public void setSourceIds(List<String> sourceIds) {
		this.sourceIds = sourceIds;
	}

	public List<String> getFuzzyQuerys() {
		return fuzzyQuerys;
	}

	public void setFuzzyQuerys(List<String> fuzzyQuerys) {
		this.fuzzyQuerys = fuzzyQuerys;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getCustomerCorpId() {
		return customerCorpId;
	}

	public void setCustomerCorpId(String customerCorpId) {
		this.customerCorpId = customerCorpId;
	}

	public String getPriceListCustomerCorpId() {
		return priceListCustomerCorpId;
	}

	public void setPriceListCustomerCorpId(String priceListCustomerCorpId) {
		this.priceListCustomerCorpId = priceListCustomerCorpId;
	}

	public List<String> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<String> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public Integer getOrderLevel() {
		return orderLevel;
	}

	public void setOrderLevel(Integer orderLevel) {
		this.orderLevel = orderLevel;
	}

	public List<String> getSupplierCorpIds() {
		return supplierCorpIds;
	}

	public void setSupplierCorpIds(List<String> supplierCorpIds) {
		this.supplierCorpIds = supplierCorpIds;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
}
