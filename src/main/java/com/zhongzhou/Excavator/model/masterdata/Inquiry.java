package com.zhongzhou.Excavator.model.masterdata;

import java.sql.Timestamp;
import java.util.List;

public class Inquiry {

	private Integer status;
	private String id;
	private String inquiryNumber;
	private String customerName;
	private String customerBuyer;
	private String inquiryOperator;
	private Timestamp inquiryDate;
	private String payMode;
	private String shipmentPort;
	private String dischargePort;
	private String currencyId;
	private String currencyName;
	private String priceTerm;
	
	List<InquiryItem> inquiryItems;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInquiryNumber() {
		return inquiryNumber;
	}
	public void setInquiryNumber(String inquiryNumber) {
		this.inquiryNumber = inquiryNumber;
	}
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
	public String getInquiryOperator() {
		return inquiryOperator;
	}
	public void setInquiryOperator(String inquiryOperator) {
		this.inquiryOperator = inquiryOperator;
	}
	public Timestamp getInquiryDate() {
		return inquiryDate;
	}
	public void setInquiryDate(Timestamp inquiryDate) {
		this.inquiryDate = inquiryDate;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getShipmentPort() {
		return shipmentPort;
	}
	public void setShipmentPort(String shipmentPort) {
		this.shipmentPort = shipmentPort;
	}
	public String getDischargePort() {
		return dischargePort;
	}
	public void setDischargePort(String dischargePort) {
		this.dischargePort = dischargePort;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public List<InquiryItem> getInquiryItems() {
		return inquiryItems;
	}
	public void setInquiryItems(List<InquiryItem> inquiryItems) {
		this.inquiryItems = inquiryItems;
	}
	public String getPriceTerm() {
		return priceTerm;
	}
	public void setPriceTerm(String priceTerm) {
		this.priceTerm = priceTerm;
	}
}
