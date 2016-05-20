package com.zhongzhou.Excavator.model.NC;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.http.conn.ssl.PrivateKeyStrategy;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.zhongzhou.Excavator.model.BasicStoreDocument;

@Entity( value="external_corporations" )
/**
 * This data is input data, comes from NC, table: BD_CUBASDOCs
 * All columns mapped without changing.
 */
public class CorporationDoc implements BasicStoreDocument {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2541919179294668233L;

	public static final String docSource = "NC.BD_CUBASDOC";

    // Fields from NC
    
    private String ACCDEFAULT;
    
    private String BP1;
    
    private String BP2;
    
    private String CONADDR;
    
    private String CORRESPONDUNIT;
    
    private String CREATETIME;
    
    private String CREATOR;
    
    private BigDecimal CREDITMNY;
    
    private String CUSTCODE;
    
    private String CUSTNAME;
    
    private int CUSTPROP;
    
    private String CUSTSHORTNAME;

    private String DEF1  ;
    private String DEF10 ;
    private String DEF11 ;
    private String DEF12 ;
    private String DEF13 ;
    private String DEF14 ;
    private String DEF15 ;
    private String DEF16 ;
    private String DEF17 ;
    private String DEF18 ;
    private String DEF19 ;
    private String DEF2  ;
    private String DEF20 ;
    private String DEF3  ;
    private String DEF4  ;
    private String DEF5  ;
    private String DEF6  ;
    private String DEF7  ;
    private String DEF8  ;
    private String DEF9  ;

    
    private String DRPNODEFLAG      ; 
    private String ECOTYPESINCEVFIVE; 
    private String EMAIL            ; 
    private String ENGNAME          ; 
    private String FAX1             ; 
    private String FAX2             ; 
    private String FREECUSTFLAG     ; 
    private String ISCONNFLAG       ; 
    private String LEGALBODY        ; 
    private String LINKMAN1         ; 
    private String LINKMAN2         ; 
    private String LINKMAN3         ; 
    private String MEMO             ; 
    private String MNECODE          ; 
    private String MOBILEPHONE1     ; 
    private String MOBILEPHONE2     ; 
    private String MOBILEPHONE3     ; 
    private String MODIFIER         ; 
    private String MODIFYTIME       ; 
    private String PHONE1           ; 
    private String PHONE2           ; 
    private String PHONE3           ; 
    private String PK_AREACL        ; 
    private String PK_CORP          ; 
    private String PK_CORP1         ; 
    private String PK_CUBASDOC      ; 
    private String PK_CUBASDOC1     ; 
    private String PK_PRICEGROUP    ; 
    private BigDecimal REGISTERFUND ;
    private String SALEADDR         ;
    private String SEALFLAG         ;
    private String TAXPAYERID       ;
    private String TRADE            ;
    private Timestamp TS            ;
    private String URL              ;
    private String ZIPCODE          ;
    
    
    //Other parameter not in corporation table
    //From bd_areacl.areaclname
    private String PK_AREACL_NAME;
    
    /* Used for save some infomation for this doc, not from data source */
    private String targetId;
    private Timestamp docCreateTime;
    
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	
	public String getACCDEFAULT() {
		return ACCDEFAULT;
	}
	public void setACCDEFAULT(String aCCDEFAULT) {
		ACCDEFAULT = aCCDEFAULT;
	}
	public String getBP1() {
		return BP1;
	}
	public void setBP1(String bP1) {
		BP1 = bP1;
	}
	public String getBP2() {
		return BP2;
	}
	public void setBP2(String bP2) {
		BP2 = bP2;
	}
	public String getCONADDR() {
		return CONADDR;
	}
	public void setCONADDR(String cONADDR) {
		CONADDR = cONADDR;
	}
	public String getCORRESPONDUNIT() {
		return CORRESPONDUNIT;
	}
	public void setCORRESPONDUNIT(String cORRESPONDUNIT) {
		CORRESPONDUNIT = cORRESPONDUNIT;
	}
	public String getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}
	public String getCREATOR() {
		return CREATOR;
	}
	public void setCREATOR(String cREATOR) {
		CREATOR = cREATOR;
	}
	public BigDecimal getCREDITMNY() {
		return CREDITMNY;
	}
	public void setCREDITMNY(BigDecimal cREDITMNY) {
		CREDITMNY = cREDITMNY;
	}
	public String getCUSTCODE() {
		return CUSTCODE;
	}
	public void setCUSTCODE(String cUSTCODE) {
		CUSTCODE = cUSTCODE;
	}
	public String getCUSTNAME() {
		return CUSTNAME;
	}
	public void setCUSTNAME(String cUSTNAME) {
		CUSTNAME = cUSTNAME;
	}
	public int getCUSTPROP() {
		return CUSTPROP;
	}
	public void setCUSTPROP(int cUSTPROP) {
		CUSTPROP = cUSTPROP;
	}
	public String getCUSTSHORTNAME() {
		return CUSTSHORTNAME;
	}
	public void setCUSTSHORTNAME(String cUSTSHORTNAME) {
		CUSTSHORTNAME = cUSTSHORTNAME;
	}
	public String getDEF1() {
		return DEF1;
	}
	public void setDEF1(String dEF1) {
		DEF1 = dEF1;
	}
	public String getDEF10() {
		return DEF10;
	}
	public void setDEF10(String dEF10) {
		DEF10 = dEF10;
	}
	public String getDEF11() {
		return DEF11;
	}
	public void setDEF11(String dEF11) {
		DEF11 = dEF11;
	}
	public String getDEF12() {
		return DEF12;
	}
	public void setDEF12(String dEF12) {
		DEF12 = dEF12;
	}
	public String getDEF13() {
		return DEF13;
	}
	public void setDEF13(String dEF13) {
		DEF13 = dEF13;
	}
	public String getDEF14() {
		return DEF14;
	}
	public void setDEF14(String dEF14) {
		DEF14 = dEF14;
	}
	public String getDEF15() {
		return DEF15;
	}
	public void setDEF15(String dEF15) {
		DEF15 = dEF15;
	}
	public String getDEF16() {
		return DEF16;
	}
	public void setDEF16(String dEF16) {
		DEF16 = dEF16;
	}
	public String getDEF17() {
		return DEF17;
	}
	public void setDEF17(String dEF17) {
		DEF17 = dEF17;
	}
	public String getDEF18() {
		return DEF18;
	}
	public void setDEF18(String dEF18) {
		DEF18 = dEF18;
	}
	public String getDEF19() {
		return DEF19;
	}
	public void setDEF19(String dEF19) {
		DEF19 = dEF19;
	}
	public String getDEF2() {
		return DEF2;
	}
	public void setDEF2(String dEF2) {
		DEF2 = dEF2;
	}
	public String getDEF20() {
		return DEF20;
	}
	public void setDEF20(String dEF20) {
		DEF20 = dEF20;
	}
	public String getDEF3() {
		return DEF3;
	}
	public void setDEF3(String dEF3) {
		DEF3 = dEF3;
	}
	public String getDEF4() {
		return DEF4;
	}
	public void setDEF4(String dEF4) {
		DEF4 = dEF4;
	}
	public String getDEF5() {
		return DEF5;
	}
	public void setDEF5(String dEF5) {
		DEF5 = dEF5;
	}
	public String getDEF6() {
		return DEF6;
	}
	public void setDEF6(String dEF6) {
		DEF6 = dEF6;
	}
	public String getDEF7() {
		return DEF7;
	}
	public void setDEF7(String dEF7) {
		DEF7 = dEF7;
	}
	public String getDEF8() {
		return DEF8;
	}
	public void setDEF8(String dEF8) {
		DEF8 = dEF8;
	}
	public String getDEF9() {
		return DEF9;
	}
	public void setDEF9(String dEF9) {
		DEF9 = dEF9;
	}
	public String getDRPNODEFLAG() {
		return DRPNODEFLAG;
	}
	public void setDRPNODEFLAG(String dRPNODEFLAG) {
		DRPNODEFLAG = dRPNODEFLAG;
	}
	public String getECOTYPESINCEVFIVE() {
		return ECOTYPESINCEVFIVE;
	}
	public void setECOTYPESINCEVFIVE(String eCOTYPESINCEVFIVE) {
		ECOTYPESINCEVFIVE = eCOTYPESINCEVFIVE;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getENGNAME() {
		return ENGNAME;
	}
	public void setENGNAME(String eNGNAME) {
		ENGNAME = eNGNAME;
	}
	public String getFAX1() {
		return FAX1;
	}
	public void setFAX1(String fAX1) {
		FAX1 = fAX1;
	}
	public String getFAX2() {
		return FAX2;
	}
	public void setFAX2(String fAX2) {
		FAX2 = fAX2;
	}
	public String getFREECUSTFLAG() {
		return FREECUSTFLAG;
	}
	public void setFREECUSTFLAG(String fREECUSTFLAG) {
		FREECUSTFLAG = fREECUSTFLAG;
	}
	public String getISCONNFLAG() {
		return ISCONNFLAG;
	}
	public void setISCONNFLAG(String iSCONNFLAG) {
		ISCONNFLAG = iSCONNFLAG;
	}
	public String getLEGALBODY() {
		return LEGALBODY;
	}
	public void setLEGALBODY(String lEGALBODY) {
		LEGALBODY = lEGALBODY;
	}
	public String getLINKMAN1() {
		return LINKMAN1;
	}
	public void setLINKMAN1(String lINKMAN1) {
		LINKMAN1 = lINKMAN1;
	}
	public String getLINKMAN2() {
		return LINKMAN2;
	}
	public void setLINKMAN2(String lINKMAN2) {
		LINKMAN2 = lINKMAN2;
	}
	public String getLINKMAN3() {
		return LINKMAN3;
	}
	public void setLINKMAN3(String lINKMAN3) {
		LINKMAN3 = lINKMAN3;
	}
	public String getMEMO() {
		return MEMO;
	}
	public void setMEMO(String mEMO) {
		MEMO = mEMO;
	}
	public String getMNECODE() {
		return MNECODE;
	}
	public void setMNECODE(String mNECODE) {
		MNECODE = mNECODE;
	}
	public String getMOBILEPHONE1() {
		return MOBILEPHONE1;
	}
	public void setMOBILEPHONE1(String mOBILEPHONE1) {
		MOBILEPHONE1 = mOBILEPHONE1;
	}
	public String getMOBILEPHONE2() {
		return MOBILEPHONE2;
	}
	public void setMOBILEPHONE2(String mOBILEPHONE2) {
		MOBILEPHONE2 = mOBILEPHONE2;
	}
	public String getMOBILEPHONE3() {
		return MOBILEPHONE3;
	}
	public void setMOBILEPHONE3(String mOBILEPHONE3) {
		MOBILEPHONE3 = mOBILEPHONE3;
	}
	public String getMODIFIER() {
		return MODIFIER;
	}
	public void setMODIFIER(String mODIFIER) {
		MODIFIER = mODIFIER;
	}
	public String getMODIFYTIME() {
		return MODIFYTIME;
	}
	public void setMODIFYTIME(String mODIFYTIME) {
		MODIFYTIME = mODIFYTIME;
	}
	public String getPHONE1() {
		return PHONE1;
	}
	public void setPHONE1(String pHONE1) {
		PHONE1 = pHONE1;
	}
	public String getPHONE2() {
		return PHONE2;
	}
	public void setPHONE2(String pHONE2) {
		PHONE2 = pHONE2;
	}
	public String getPHONE3() {
		return PHONE3;
	}
	public void setPHONE3(String pHONE3) {
		PHONE3 = pHONE3;
	}
	public String getPK_AREACL() {
		return PK_AREACL;
	}
	public void setPK_AREACL(String pK_AREACL) {
		PK_AREACL = pK_AREACL;
	}
	public String getPK_CORP() {
		return PK_CORP;
	}
	public void setPK_CORP(String pK_CORP) {
		PK_CORP = pK_CORP;
	}
	public String getPK_CORP1() {
		return PK_CORP1;
	}
	public void setPK_CORP1(String pK_CORP1) {
		PK_CORP1 = pK_CORP1;
	}
	public String getPK_CUBASDOC() {
		return PK_CUBASDOC;
	}
	public void setPK_CUBASDOC(String pK_CUBASDOC) {
		PK_CUBASDOC = pK_CUBASDOC;
	}
	public String getPK_CUBASDOC1() {
		return PK_CUBASDOC1;
	}
	public void setPK_CUBASDOC1(String pK_CUBASDOC1) {
		PK_CUBASDOC1 = pK_CUBASDOC1;
	}
	public String getPK_PRICEGROUP() {
		return PK_PRICEGROUP;
	}
	public void setPK_PRICEGROUP(String pK_PRICEGROUP) {
		PK_PRICEGROUP = pK_PRICEGROUP;
	}
	public BigDecimal getREGISTERFUND() {
		return REGISTERFUND;
	}
	public void setREGISTERFUND(BigDecimal rEGISTERFUND) {
		REGISTERFUND = rEGISTERFUND;
	}
	public String getSALEADDR() {
		return SALEADDR;
	}
	public void setSALEADDR(String sALEADDR) {
		SALEADDR = sALEADDR;
	}
	public String getSEALFLAG() {
		return SEALFLAG;
	}
	public void setSEALFLAG(String sEALFLAG) {
		SEALFLAG = sEALFLAG;
	}
	public String getTAXPAYERID() {
		return TAXPAYERID;
	}
	public void setTAXPAYERID(String tAXPAYERID) {
		TAXPAYERID = tAXPAYERID;
	}
	public String getTRADE() {
		return TRADE;
	}
	public void setTRADE(String tRADE) {
		TRADE = tRADE;
	}
	public Timestamp getTS() {
		return TS;
	}
	public void setTS(Timestamp tS) {
		TS = tS;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getZIPCODE() {
		return ZIPCODE;
	}
	public void setZIPCODE(String zIPCODE) {
		ZIPCODE = zIPCODE;
	}
	public String getPK_AREACL_NAME() {
		return PK_AREACL_NAME;
	}
	public void setPK_AREACL_NAME(String pK_AREACL_NAME) {
		PK_AREACL_NAME = pK_AREACL_NAME;
	}
	
	@Override
	public String getDocSource() {
		return docSource;
	}
	@Override
	public void setDocSource(String docSource) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getDocSourceId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setDocSourceId(String docSourceId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getDocCreateUser() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setDocCreateUser(String docCreateUser) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getDocCreateUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setDocCreateUserId(String docCreateUserId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Timestamp getDocCreateTime() {
		return this.docCreateTime;
	}
	@Override
	public void setDocCreateTime(Timestamp docCreateTime) {
		this.docCreateTime = docCreateTime;
		
	}
	@Override
	public void setDocLastUpdateTime(Timestamp docLastUpdateTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getDocUpdateUser() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setDocUpdateUser(String docUpdateUser) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getDocUpdateUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setDocUpdateUserId(String docUpdateUserId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Timestamp getDocLastUpdateTime() {
		return this.getTS();
	}
}
