package com.zhongzhou.Excavator.model.masterdata;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhongzhou.common.override.Jackson.JacksonTimestampSerializer;

public class CorporationGrade {

	private String       id;
	private String       corporationId;
	private String       corporationGroup;
	private String 		 gradeId;
	private BigDecimal   gradeScore;
	private String       gradeLevel;
	
	@JsonSerialize(using = JacksonTimestampSerializer.class )
	private Timestamp    createTime;
	
	private List<CorporationGradeItem> gradeItems;
	
	private Corporation corporation;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCorporationId() {
		return corporationId;
	}
	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}
	public String getCorporationGroup() {
		return corporationGroup;
	}
	public void setCorporationGroup(String corporationGroup) {
		this.corporationGroup = corporationGroup;
	}
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public BigDecimal getGradeScore() {
		return gradeScore;
	}
	public void setGradeScore(BigDecimal gradeScore) {
		this.gradeScore = gradeScore;
	}
	public String getGradeLevel() {
		return gradeLevel;
	}
	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public List<CorporationGradeItem> getGradeItems() {
		return gradeItems;
	}
	public void setGradeItems(List<CorporationGradeItem> gradeItems) {
		this.gradeItems = gradeItems;
	}
	public Corporation getCorporation() {
		return corporation;
	}
	public void setCorporation(Corporation corporation) {
		this.corporation = corporation;
	}
}
