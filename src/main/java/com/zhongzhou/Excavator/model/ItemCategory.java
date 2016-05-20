package com.zhongzhou.Excavator.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@SuppressWarnings("serial")
public class ItemCategory implements Serializable{
	
	public static final String defaultCategoryGroup = "ItemCategory";

	private String id;
	
	private String name;
	
	private String ename;
	
	private String categoryGroup = defaultCategoryGroup;
	
	private String description;
	
	private Integer level;
	
	private String parentId;
	
	private boolean isLeaf;
	
	private int treeLeftValue;
	
	private int treeRightValue;

	private int status;
	
	private Timestamp lastUpdateTime;
	
	private List<ItemCategory> subCategorys;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryGroup() {
		return categoryGroup;
	}

	public void setCategoryGroup(String categoryGroup) {
		this.categoryGroup = categoryGroup;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public int getTreeLeftValue() {
		return treeLeftValue;
	}

	public void setTreeLeftValue(int treeLeftValue) {
		this.treeLeftValue = treeLeftValue;
	}

	public int getTreeRightValue() {
		return treeRightValue;
	}

	public void setTreeRightValue(int treeRightValue) {
		this.treeRightValue = treeRightValue;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<ItemCategory> getSubCategorys() {
		return subCategorys;
	}

	public void setSubCategorys(List<ItemCategory> subCategorys) {
		this.subCategorys = subCategorys;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<ItemCategory> getChildren() {
		return this.getSubCategorys();
	}
	
	public boolean getExpanded() {
		return !this.getIsLeaf();
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
