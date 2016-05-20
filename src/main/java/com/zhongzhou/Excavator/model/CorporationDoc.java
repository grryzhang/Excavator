package com.zhongzhou.Excavator.model;

public class CorporationDoc extends Corporation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 608159229729538061L;

	private com.zhongzhou.Excavator.model.NC.CorporationDoc ncCorporation;
	
	public com.zhongzhou.Excavator.model.NC.CorporationDoc getNcCorporation() {
		return ncCorporation;
	}

	public void setNcCorporation(com.zhongzhou.Excavator.model.NC.CorporationDoc ncCorporation) {
		this.ncCorporation = ncCorporation;
	}
}
