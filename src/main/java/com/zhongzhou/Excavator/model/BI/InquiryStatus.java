package com.zhongzhou.Excavator.model.BI;

import org.mongodb.morphia.annotations.Entity;

@Entity( value="BIReports" )
public class InquiryStatus {

	private String customerName;
	
	private String customerBuyer;
	
	private String businessUser;
	
	private String inquiryNumber;
	
	private String inquiryResponseTime;
	
	private String loadingPort;
	
	private String unloadingPort;
	
	private String currency;
	
	private String itemNumber;
	
	private String customerItemNumber;
	
	private String itemNameCN;
	
	private String itemNameEN;
	
	private String itemStandard;
	
	private String packageInfo;
	
	private String itemInfoCN;
	
	private String itemInfoEN;
	
	private String itemUnit;
	
	private String quantityPerInquiry;
	
	private String minimalOrderQuantity;
	
	private String tradeTerm;
	
	private String costPrice;
	
	private String FOBPrice;
	
	private String warranty;
	
	private String inquiryExpiryDate;
	
	private String deliveryTime;
	
	private String paymentType;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerBuyer() {
		return customerBuyer;
	}

	public void setCustomerBuyer(String customerBuyer) {
		this.customerBuyer = customerBuyer;
	}

	public String getBusinessUser() {
		return businessUser;
	}

	public void setBusinessUser(String businessUser) {
		this.businessUser = businessUser;
	}

	public String getInquiryNumber() {
		return inquiryNumber;
	}

	public void setInquiryNumber(String inquiryNumber) {
		this.inquiryNumber = inquiryNumber;
	}

	public String getInquiryResponseTime() {
		return inquiryResponseTime;
	}

	public void setInquiryResponseTime(String inquiryResponseTime) {
		this.inquiryResponseTime = inquiryResponseTime;
	}

	public String getLoadingPort() {
		return loadingPort;
	}

	public void setLoadingPort(String loadingPort) {
		this.loadingPort = loadingPort;
	}

	public String getUnloadingPort() {
		return unloadingPort;
	}

	public void setUnloadingPort(String unloadingPort) {
		this.unloadingPort = unloadingPort;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getCustomerItemNumber() {
		return customerItemNumber;
	}

	public void setCustomerItemNumber(String customerItemNumber) {
		this.customerItemNumber = customerItemNumber;
	}

	public String getItemNameCN() {
		return itemNameCN;
	}

	public void setItemNameCN(String itemNameCN) {
		this.itemNameCN = itemNameCN;
	}

	public String getItemNameEN() {
		return itemNameEN;
	}

	public void setItemNameEN(String itemNameEN) {
		this.itemNameEN = itemNameEN;
	}

	public String getItemStandard() {
		return itemStandard;
	}

	public void setItemStandard(String itemStandard) {
		this.itemStandard = itemStandard;
	}

	public String getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(String packageInfo) {
		this.packageInfo = packageInfo;
	}

	public String getItemInfoCN() {
		return itemInfoCN;
	}

	public void setItemInfoCN(String itemInfoCN) {
		this.itemInfoCN = itemInfoCN;
	}

	public String getItemInfoEN() {
		return itemInfoEN;
	}

	public void setItemInfoEN(String itemInfoEN) {
		this.itemInfoEN = itemInfoEN;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getQuantityPerInquiry() {
		return quantityPerInquiry;
	}

	public void setQuantityPerInquiry(String quantityPerInquiry) {
		this.quantityPerInquiry = quantityPerInquiry;
	}

	public String getMinimalOrderQuantity() {
		return minimalOrderQuantity;
	}

	public void setMinimalOrderQuantity(String minimalOrderQuantity) {
		this.minimalOrderQuantity = minimalOrderQuantity;
	}

	public String getTradeTerm() {
		return tradeTerm;
	}

	public void setTradeTerm(String tradeTerm) {
		this.tradeTerm = tradeTerm;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getFOBPrice() {
		return FOBPrice;
	}

	public void setFOBPrice(String fOBPrice) {
		FOBPrice = fOBPrice;
	}

	public String getWarranty() {
		return warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public String getInquiryExpiryDate() {
		return inquiryExpiryDate;
	}

	public void setInquiryExpiryDate(String inquiryExpiryDate) {
		this.inquiryExpiryDate = inquiryExpiryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
}
