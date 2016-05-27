package com.zhongzhou.Excavator.model;

public class Currency {

	private String id;
	
	private String currencyName;
	
	private String currencyCode;
	
	private String currencySign;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencySign() {
		return currencySign;
	}

	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}
}
