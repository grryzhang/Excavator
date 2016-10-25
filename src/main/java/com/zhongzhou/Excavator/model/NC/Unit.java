package com.zhongzhou.Excavator.model.NC;

import java.sql.Timestamp;

public class Unit {

	private String    BASECODEFLAG;
	private String    DR;
	private String    MEASNAME;
	private String    OPPDIMEN;
	private String    PK_MEASDOC;
	private String    SCALEFACTOR;
	private String    SHORTNAME;
	private Timestamp TS;
	public String getBASECODEFLAG() {
		return BASECODEFLAG;
	}
	public void setBASECODEFLAG(String bASECODEFLAG) {
		BASECODEFLAG = bASECODEFLAG;
	}
	public String getDR() {
		return DR;
	}
	public void setDR(String dR) {
		DR = dR;
	}
	public String getMEASNAME() {
		return MEASNAME;
	}
	public void setMEASNAME(String mEASNAME) {
		MEASNAME = mEASNAME;
	}
	public String getOPPDIMEN() {
		return OPPDIMEN;
	}
	public void setOPPDIMEN(String oPPDIMEN) {
		OPPDIMEN = oPPDIMEN;
	}
	public String getPK_MEASDOC() {
		return PK_MEASDOC;
	}
	public void setPK_MEASDOC(String pK_MEASDOC) {
		PK_MEASDOC = pK_MEASDOC;
	}
	public String getSCALEFACTOR() {
		return SCALEFACTOR;
	}
	public void setSCALEFACTOR(String sCALEFACTOR) {
		SCALEFACTOR = sCALEFACTOR;
	}
	public String getSHORTNAME() {
		return SHORTNAME;
	}
	public void setSHORTNAME(String sHORTNAME) {
		SHORTNAME = sHORTNAME;
	}
	public Timestamp getTS() {
		return TS;
	}
	public void setTS(Timestamp tS) {
		TS = tS;
	}
}
