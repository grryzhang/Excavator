package com.zhongzhou.Excavator.model.NC;

import java.util.ArrayList;
import java.util.List;

import com.zhongzhou.Excavator.model.common.OrderBy;

public class SaleOrderSearchParameters {
	
	private String vdef1;
	
	private String vreceiptcodeLike;
	
	private Integer start = 0;
	
	private Integer end = 100;
	
	public OrderBy orderby;

	public List<OrderBy> orderbys;

	public String getVdef1() {
		return vdef1;
	}

	public void setVdef1(String vdef1) {
		this.vdef1 = vdef1;
	}

	public String getVreceiptcodeLike() {
		return vreceiptcodeLike;
	}

	public void setVreceiptcodeLike(String vreceiptcodeLike) {
		this.vreceiptcodeLike = vreceiptcodeLike;
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

	public OrderBy getOrderby() {
		return orderby;
	}

	public void setOrderby( OrderBy orderby ) {
		this.orderbys = new ArrayList<OrderBy>();
		this.orderbys.add( orderby );
		this.orderby = orderby;
	}
	
	public List<OrderBy> getOrderbys() {
		return orderbys;
	}
	
	public void setOrderbys( List<OrderBy> orderbys) {
		this.orderbys = orderbys;
	}
}
