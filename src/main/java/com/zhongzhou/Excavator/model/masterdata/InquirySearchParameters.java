package com.zhongzhou.Excavator.model.masterdata;

import java.util.List;

import com.zhongzhou.Excavator.model.pagingSearchParameter;

public class InquirySearchParameters implements pagingSearchParameter{

	public List<String> ids;
	
	public List<String> inquiryNumbers;
	
	private Integer start = 0;
	
	private Integer limit = 30;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public List<String> getInquiryNumbers() {
		return inquiryNumbers;
	}

	public void setInquiryNumbers(List<String> inquiryNumbers) {
		this.inquiryNumbers = inquiryNumbers;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
