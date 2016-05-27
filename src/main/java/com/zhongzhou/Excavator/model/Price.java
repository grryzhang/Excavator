package com.zhongzhou.Excavator.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Price {

	private String id;
	
	private String priceCategoryId;
	
	private String offerCorpId;
	
	private String supplierCorpId;
	
	private String customerCorpId;
	
	private BigDecimal price;
	
	private String currencyId;
	
	private String offerTypeId;
	
	private String unitId;
	
	private String itemId;
	
	private Timestamp lastUpdateTime;
	
	private String updateSysComment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPriceCategoryId() {
		return priceCategoryId;
	}

	public void setPriceCategoryId(String priceCategoryId) {
		this.priceCategoryId = priceCategoryId;
	}

	public String getOfferCorpId() {
		return offerCorpId;
	}

	public void setOfferCorpId(String offerCorpId) {
		this.offerCorpId = offerCorpId;
	}

	public String getSupplierCorpId() {
		return supplierCorpId;
	}

	public void setSupplierCorpId(String supplierCorpId) {
		this.supplierCorpId = supplierCorpId;
	}

	public String getCustomerCorpId() {
		return customerCorpId;
	}

	public void setCustomerCorpId(String customerCorpId) {
		this.customerCorpId = customerCorpId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getOfferTypeId() {
		return offerTypeId;
	}

	public void setOfferTypeId(String offerTypeId) {
		this.offerTypeId = offerTypeId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getUpdateSysComment() {
		return updateSysComment;
	}

	public void setUpdateSysComment(String updateSysComment) {
		this.updateSysComment = updateSysComment;
	}
}
