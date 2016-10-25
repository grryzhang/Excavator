package com.zhongzhou.Excavator.model.masterdata;

import java.math.BigDecimal;

public class CorporationGradeItem {

	private String itemId;
	private String gradeId;
	private String gradeItemId;
	private BigDecimal gradeItemScore;
	private BigDecimal gradeItemWeight;
	private String gradeItemGroup;
	private String gradeItem;
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public String getGradeItemId() {
		return gradeItemId;
	}
	public void setGradeItemId(String gradeItemId) {
		this.gradeItemId = gradeItemId;
	}
	public BigDecimal getGradeItemScore() {
		return gradeItemScore;
	}
	public void setGradeItemScore(BigDecimal gradeItemScore) {
		this.gradeItemScore = gradeItemScore;
	}
	public BigDecimal getGradeItemWeight() {
		return gradeItemWeight;
	}
	public void setGradeItemWeight(BigDecimal gradeItemWeight) {
		this.gradeItemWeight = gradeItemWeight;
	}
	public String getGradeItemGroup() {
		return gradeItemGroup;
	}
	public void setGradeItemGroup(String gradeItemGroup) {
		this.gradeItemGroup = gradeItemGroup;
	}
	public String getGradeItem() {
		return gradeItem;
	}
	public void setGradeItem(String gradeItem) {
		this.gradeItem = gradeItem;
	}
	
	
}
