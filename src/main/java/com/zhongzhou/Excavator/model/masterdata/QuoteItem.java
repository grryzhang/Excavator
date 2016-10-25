package com.zhongzhou.Excavator.model.masterdata;

import java.math.BigDecimal;

public class QuoteItem {

	private String id;
	private String inquiryItemId;
	private String tempItemNumber;
	private String customerItemCode;
	private String itemCName;
	private String itemCDescription;
	private BigDecimal minOrderNumber;
	private String priceTerm;
	private BigDecimal unitPrice;
	private BigDecimal unitFobPrice;
	private String qualityGuarantee;
	private String validPeriod;
	private String validDate;
	private String currencyId;
	private String currencyName;
	private String supplierName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getItemCName() {
		return itemCName;
	}
	public void setItemCName(String itemCName) {
		this.itemCName = itemCName;
	}
	public String getItemCDescription() {
		return itemCDescription;
	}
	public void setItemCDescription(String itemCDescription) {
		this.itemCDescription = itemCDescription;
	}
	public String getInquiryItemId() {
		return inquiryItemId;
	}
	public void setInquiryItemId(String inquiryItemId) {
		this.inquiryItemId = inquiryItemId;
	}
	public BigDecimal getMinOrderNumber() {
		return minOrderNumber;
	}
	public void setMinOrderNumber(BigDecimal minOrderNumber) {
		this.minOrderNumber = minOrderNumber;
	}
	public String getPriceTerm() {
		return priceTerm;
	}
	public void setPriceTerm(String priceTerm) {
		this.priceTerm = priceTerm;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getUnitFobPrice() {
		return unitFobPrice;
	}
	public void setUnitFobPrice(BigDecimal unitFobPrice) {
		this.unitFobPrice = unitFobPrice;
	}
	public String getQualityGuarantee() {
		return qualityGuarantee;
	}
	public void setQualityGuarantee(String qualityGuarantee) {
		this.qualityGuarantee = qualityGuarantee;
	}
	public String getValidPeriod() {
		return validPeriod;
	}
	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
}
