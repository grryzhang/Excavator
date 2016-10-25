package com.zhongzhou.Excavator.model.NC;

import java.sql.Timestamp;

import org.mongodb.morphia.annotations.Entity;

@Entity( value="external_item_categorys" )

public class ItemCategory {
	
	public static final String docSource = "NC.BC_INVCL";
	
	private String ENDFLAG;
	
	//private String FORINVNAME;
	
	private String INVCLASSCODE;
	
	private int INVCLASSLEV;
	
	private String INVCLASSNAME;
	
	private String FORINVNAME;
	
	private String ISELECTRANS;
	
	private String PK_CORP;
	
	private String PK_INVCL;
	
	private Timestamp TS;
	
	private String PARENT_ID;
	
	private Timestamp lastUpdateTime;

	public String getENDFLAG() {
		return ENDFLAG;
	}

	public void setENDFLAG(String eNDFLAG) {
		ENDFLAG = eNDFLAG;
	}

	public String getINVCLASSCODE() {
		return INVCLASSCODE;
	}

	public void setINVCLASSCODE(String iNVCLASSCODE) {
		INVCLASSCODE = iNVCLASSCODE;
	}

	public int getINVCLASSLEV() {
		return INVCLASSLEV;
	}

	public void setINVCLASSLEV(int iNVCLASSLEV) {
		INVCLASSLEV = iNVCLASSLEV;
	}

	public String getFORINVNAME() {
		return FORINVNAME;
	}

	public void setFORINVNAME(String fORINVNAME) {
		FORINVNAME = fORINVNAME;
	}

	public String getINVCLASSNAME() {
		return INVCLASSNAME;
	}

	public void setINVCLASSNAME(String iNVCLASSNAME) {
		INVCLASSNAME = iNVCLASSNAME;
	}

	public String getISELECTRANS() {
		return ISELECTRANS;
	}

	public void setISELECTRANS(String iSELECTRANS) {
		ISELECTRANS = iSELECTRANS;
	}

	public String getPK_CORP() {
		return PK_CORP;
	}

	public void setPK_CORP(String pK_CORP) {
		PK_CORP = pK_CORP;
	}

	public String getPK_INVCL() {
		return PK_INVCL;
	}

	public void setPK_INVCL(String pK_INVCL) {
		PK_INVCL = pK_INVCL;
	}

	public Timestamp getTS() {
		return TS;
	}

	public void setTS(Timestamp tS) {
		TS = tS;
	}

	public String getPARENT_ID() {
		return PARENT_ID;
	}

	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
