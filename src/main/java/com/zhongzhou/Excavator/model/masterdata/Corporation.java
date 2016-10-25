package com.zhongzhou.Excavator.model.masterdata;

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
	
	private String phone;
	
	private String contacts;
	
	private String mail;
	
	private String imgs;
	
	private String introduction;
	
	private String address;
	
	private String managementCertifications;
	
	private String certifications;
	
	private String resource;
	
	private String website;
	
	private String screenLevelId;
	
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCertifications() {
		return certifications;
	}

	public void setCertifications(String certifications) {
		this.certifications = certifications;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getManagementCertifications() {
		return managementCertifications;
	}

	public void setManagementCertifications(String managementCertifications) {
		this.managementCertifications = managementCertifications;
	}

	public String getScreenLevelId() {
		return screenLevelId;
	}

	public void setScreenLevelId(String screenLevelId) {
		this.screenLevelId = screenLevelId;
	}
}
