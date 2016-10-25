package com.zhongzhou.Excavator.model.NC;

import java.math.BigDecimal;

public class CurrencyRate {
	
	private String pk_currinfo;
	
	private String pk_currrate;

	private Currency domesticCurrency;
	
	private Currency foreignCurrency;
	
	private BigDecimal rate;

	public String getPk_currinfo() {
		return pk_currinfo;
	}

	public void setPk_currinfo(String pk_currinfo) {
		this.pk_currinfo = pk_currinfo;
	}
	public String getPk_currrate() {
		return pk_currrate;
	}

	public void setPk_currrate(String pk_currrate) {
		this.pk_currrate = pk_currrate;
	}

	public Currency getDomesticCurrency() {
		return domesticCurrency;
	}

	public void setDomesticCurrency(Currency domesticCurrency) {
		this.domesticCurrency = domesticCurrency;
	}

	public Currency getForeignCurrency() {
		return foreignCurrency;
	}

	public void setForeignCurrency(Currency foreignCurrency) {
		this.foreignCurrency = foreignCurrency;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
}
