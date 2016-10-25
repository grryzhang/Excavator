package com.zhongzhou.Excavator.model.masterdata;

import java.math.BigDecimal;
import java.util.List;

public class InquiryItem {

	private String id;
	private String inquiryId;
	private String tempItemNumber;
	private String customerItemCode;
	private String itemEname;
	private String itemEDescription;
	private String itemSpecification;
	private String itemUnit;
	private BigDecimal minOrderNumber;
	private BigDecimal purchaseNumber;
	
	private List<QuoteItem> quoteItems;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInquiryId() {
		return inquiryId;
	}
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}
	public String getTempItemNumber() {
		return tempItemNumber;
	}
	public void setTempItemNumber(String tempItemNumber) {
		this.tempItemNumber = tempItemNumber;
	}
	public String getCustomerItemCode() {
		return customerItemCode;
	}
	public void setCustomerItemCode(String customerItemCode) {
		this.customerItemCode = customerItemCode;
	}
	public String getItemEname() {
		return itemEname;
	}
	public void setItemEname(String itemEname) {
		this.itemEname = itemEname;
	}
	public String getItemEDescription() {
		return itemEDescription;
	}
	public void setItemEDescription(String itemEDescription) {
		this.itemEDescription = itemEDescription;
	}
	public String getItemSpecification() {
		return itemSpecification;
	}
	public void setItemSpecification(String itemSpecification) {
		this.itemSpecification = itemSpecification;
	}
	public String getItemUnit() {
		return itemUnit;
	}
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	public BigDecimal getPurchaseNumber() {
		return purchaseNumber;
	}
	public void setPurchaseNumber(BigDecimal purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}
	public List<QuoteItem> getQuoteItems() {
		return quoteItems;
	}
	public void setQuoteItems(List<QuoteItem> quoteItems) {
		this.quoteItems = quoteItems;
	}
	public BigDecimal getMinOrderNumber() {
		return minOrderNumber;
	}
	public void setMinOrderNumber(BigDecimal minOrderNumber) {
		this.minOrderNumber = minOrderNumber;
	}
}
