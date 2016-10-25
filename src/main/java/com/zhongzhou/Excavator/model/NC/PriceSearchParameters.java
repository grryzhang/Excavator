package com.zhongzhou.Excavator.model.NC;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PriceSearchParameters {
	
	private String priceType;
	
	//cinvbasdocid
	private String itemId;
	
	private List<String> itemIds;
	
	private String customerName;
	
	private List<String> customerNames;

	private Integer start;
	
	private Integer end;
	
	private Timestamp startTS;
	
	private Timestamp endTS;
	
	

	public String getCustomerName() {
		if( this.getCustomerNames() != null && this.getCustomerNames().size()>0 ){
			return this.getCustomerNames().get(0);
		}
		return null;	
	}
	
	public void setCustomerName(String customerName) {
		this.setCustomerNames( new ArrayList<String>() );
		this.getCustomerNames().add( customerName );
	}

	public List<String> getCustomerNames() {
		return customerNames;
	}

	public void setCustomerNames(List<String> customerNames) {
		this.customerNames = customerNames;
	}

	public String getItemId() {
		if( this.getItemIds() != null && this.getItemIds().size()>0 ){
			return this.getItemIds().get(0);
		}
		return null;
	}

	public void setItemId(String itemId) {
		this.setItemIds( new ArrayList<String>() );
		this.getItemIds().add( itemId );
	}

	public List<String> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<String> itemIds) {
		this.itemIds = itemIds;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public Timestamp getStartTS() {
		return startTS;
	}

	public void setStartTS(Timestamp startTS) {
		this.startTS = startTS;
	}

	public Timestamp getEndTS() {
		return endTS;
	}

	public void setEndTS(Timestamp endTS) {
		this.endTS = endTS;
	}
}
