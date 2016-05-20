package com.zhongzhou.Excavator.model;

import java.util.UUID;

/**
 * This object used for migrating Data from NC to master data plant.
 * <br> First we have to insert the mapping relation ship, 
 * <br> Consider data insulation, the NC id should not in plant master data table.
 */
public class CorporationIntegrationMapping {

	private String id;
	
	private String corporationId;
	
	private String sourceId;
	
	private String sourceCode;
	
	private String dataSource;

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

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
}
