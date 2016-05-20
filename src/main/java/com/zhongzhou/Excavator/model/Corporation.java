package com.zhongzhou.Excavator.model;

import java.util.UUID;

public class Corporation extends BasicDBRecord{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6430191385518797855L;

	private String id;
	
	private String name;
	
	private String shortName;
	
	private String eName;
	
	private String areaId;
	
	public Corporation(){};
	
	public Corporation( com.zhongzhou.Excavator.model.NC.CorporationDoc ncCorporationDoc){
		
		this.setName      ( ncCorporationDoc.getCUSTNAME()       );
		this.setShortName ( ncCorporationDoc.getCUSTSHORTNAME()  );
		this.seteName     ( ncCorporationDoc.getENGNAME()        );
		this.setAreaId    ( ncCorporationDoc.getPK_AREACL_NAME() );
	}

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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
}
