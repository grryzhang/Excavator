package com.zhongzhou.Excavator.model.NC;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.mongodb.morphia.annotations.Entity;

import com.zhongzhou.Excavator.model.masterdata.BasicStoreDocument;

@Entity( value="external_items" )
public class Item implements BasicStoreDocument{
	
	public static final String dataSource = "NC.BD_INVBASDOC";
	
	private String ASSET             ; 
	private String ASSISTUNIT        ; 
	private String AUTOBALANCEMEAS   ; 
	private String CREATETIME        ; 
	private String CREATOR           ; 
	private String DEF1              ; 
	private String DEF10             ; 
	private String DEF11             ; 
	private String DEF12             ; 
	private String DEF13             ; 
	private String DEF14             ; 
	private String DEF15             ; 
	private String DEF16             ; 
	private String DEF17             ; 
	private String DEF18             ; 
	private String DEF19             ; 
	private String DEF2              ; 
	private String DEF20             ; 
	private String DEF3              ; 
	private String DEF4              ; 
	private String DEF5              ; 
	private String DEF6              ; 
	private String DEF7              ; 
	private String DEF8              ; 
	private String DEF9              ; 
	private String DISCOUNTFLAG      ; 
	private BigDecimal DR            ; 
	private String FORINVNAME        ; 
	private String FREE1             ; 
	private String FREE2             ; 
	private String FREE3             ; 
	private String FREE4             ; 
	private String FREE5             ; 
	private String GRAPHID           ; 
	private String HEIGHT            ; 
	private String INVBARCODE        ; 
	private String INVCODE           ; 
	private String INVMNECODE        ; 
	private String INVNAME           ; 
	private String INVPINPAI         ; 
	private String INVSHORTNAME      ; 
	private String INVSPEC           ; 
	private String INVTYPE           ; 
	private String ISELECTRANS       ; 
	private String ISMNGSTOCKBYGRSWT ; 
	private String ISRETAIL          ; 
	private String ISSTOREBYCONVERT  ; 
	private String LABORFLAG         ; 
	private String LENGTH            ; 
	private String MEMO              ; 
	private String MODIFIER          ; 
	private String MODIFYTIME        ; 
	private String PK_ASSETCATEGORY  ; 
	private String PK_CORP           ; 
	private String PK_INVBASDOC      ; 
	private String PK_INVCL          ; 
	private String PK_MEASDOC        ; 
	private String PK_MEASDOC1       ; 
	private String PK_MEASDOC2       ; 
	private String PK_MEASDOC3       ; 
	private String PK_MEASDOC5       ; 
	private String PK_MEASDOC6       ; 
	private String PK_PRODLINE       ; 
	private String PK_TAXITEMS       ; 
	private String SEALFLAG          ; 
	private String SETPARTSFLAG      ; 
	private String SHIPUNITNUM       ; 
	private String STOREUNITNUM      ; 
	private Timestamp TS             ; 
	private String UNITVOLUME        ; 
	private String UNITWEIGHT        ; 
	private String WEITUNITNUM       ; 
	private String WIDTH             ;
	
	/* Joined column */
	private String corpUNITCODE ;
	
	/* Not from the data source */
	private Timestamp docCreateTime  ;
	
	private String targetId;
	
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getASSET() {
		return ASSET;
	}
	public void setASSET(String aSSET) {
		ASSET = aSSET;
	}
	public String getASSISTUNIT() {
		return ASSISTUNIT;
	}
	public void setASSISTUNIT(String aSSISTUNIT) {
		ASSISTUNIT = aSSISTUNIT;
	}
	public String getAUTOBALANCEMEAS() {
		return AUTOBALANCEMEAS;
	}
	public void setAUTOBALANCEMEAS(String aUTOBALANCEMEAS) {
		AUTOBALANCEMEAS = aUTOBALANCEMEAS;
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
	public String getDISCOUNTFLAG() {
		return DISCOUNTFLAG;
	}
	public void setDISCOUNTFLAG(String dISCOUNTFLAG) {
		DISCOUNTFLAG = dISCOUNTFLAG;
	}
	public BigDecimal getDR() {
		return DR;
	}
	public void setDR(BigDecimal dR) {
		DR = dR;
	}
	public String getFORINVNAME() {
		return FORINVNAME;
	}
	public void setFORINVNAME(String fORINVNAME) {
		FORINVNAME = fORINVNAME;
	}
	public String getFREE1() {
		return FREE1;
	}
	public void setFREE1(String fREE1) {
		FREE1 = fREE1;
	}
	public String getFREE2() {
		return FREE2;
	}
	public void setFREE2(String fREE2) {
		FREE2 = fREE2;
	}
	public String getFREE3() {
		return FREE3;
	}
	public void setFREE3(String fREE3) {
		FREE3 = fREE3;
	}
	public String getFREE4() {
		return FREE4;
	}
	public void setFREE4(String fREE4) {
		FREE4 = fREE4;
	}
	public String getFREE5() {
		return FREE5;
	}
	public void setFREE5(String fREE5) {
		FREE5 = fREE5;
	}
	public String getGRAPHID() {
		return GRAPHID;
	}
	public void setGRAPHID(String gRAPHID) {
		GRAPHID = gRAPHID;
	}
	public String getHEIGHT() {
		return HEIGHT;
	}
	public void setHEIGHT(String hEIGHT) {
		HEIGHT = hEIGHT;
	}
	public String getINVBARCODE() {
		return INVBARCODE;
	}
	public void setINVBARCODE(String iNVBARCODE) {
		INVBARCODE = iNVBARCODE;
	}
	public String getINVCODE() {
		return INVCODE;
	}
	public void setINVCODE(String iNVCODE) {
		INVCODE = iNVCODE;
	}
	public String getINVMNECODE() {
		return INVMNECODE;
	}
	public void setINVMNECODE(String iNVMNECODE) {
		INVMNECODE = iNVMNECODE;
	}
	public String getINVNAME() {
		return INVNAME;
	}
	public void setINVNAME(String iNVNAME) {
		INVNAME = iNVNAME;
	}
	public String getINVPINPAI() {
		return INVPINPAI;
	}
	public void setINVPINPAI(String iNVPINPAI) {
		INVPINPAI = iNVPINPAI;
	}
	public String getINVSHORTNAME() {
		return INVSHORTNAME;
	}
	public void setINVSHORTNAME(String iNVSHORTNAME) {
		INVSHORTNAME = iNVSHORTNAME;
	}
	public String getINVSPEC() {
		return INVSPEC;
	}
	public void setINVSPEC(String iNVSPEC) {
		INVSPEC = iNVSPEC;
	}
	public String getINVTYPE() {
		return INVTYPE;
	}
	public void setINVTYPE(String iNVTYPE) {
		INVTYPE = iNVTYPE;
	}
	public String getISELECTRANS() {
		return ISELECTRANS;
	}
	public void setISELECTRANS(String iSELECTRANS) {
		ISELECTRANS = iSELECTRANS;
	}
	public String getISMNGSTOCKBYGRSWT() {
		return ISMNGSTOCKBYGRSWT;
	}
	public void setISMNGSTOCKBYGRSWT(String iSMNGSTOCKBYGRSWT) {
		ISMNGSTOCKBYGRSWT = iSMNGSTOCKBYGRSWT;
	}
	public String getISRETAIL() {
		return ISRETAIL;
	}
	public void setISRETAIL(String iSRETAIL) {
		ISRETAIL = iSRETAIL;
	}
	public String getISSTOREBYCONVERT() {
		return ISSTOREBYCONVERT;
	}
	public void setISSTOREBYCONVERT(String iSSTOREBYCONVERT) {
		ISSTOREBYCONVERT = iSSTOREBYCONVERT;
	}
	public String getLABORFLAG() {
		return LABORFLAG;
	}
	public void setLABORFLAG(String lABORFLAG) {
		LABORFLAG = lABORFLAG;
	}
	public String getLENGTH() {
		return LENGTH;
	}
	public void setLENGTH(String lENGTH) {
		LENGTH = lENGTH;
	}
	public String getMEMO() {
		return MEMO;
	}
	public void setMEMO(String mEMO) {
		MEMO = mEMO;
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
	public String getPK_ASSETCATEGORY() {
		return PK_ASSETCATEGORY;
	}
	public void setPK_ASSETCATEGORY(String pK_ASSETCATEGORY) {
		PK_ASSETCATEGORY = pK_ASSETCATEGORY;
	}
	public String getPK_CORP() {
		return PK_CORP;
	}
	public void setPK_CORP(String pK_CORP) {
		PK_CORP = pK_CORP;
	}
	public String getPK_INVBASDOC() {
		return PK_INVBASDOC;
	}
	public void setPK_INVBASDOC(String pK_INVBASDOC) {
		PK_INVBASDOC = pK_INVBASDOC;
	}
	public String getPK_INVCL() {
		return PK_INVCL;
	}
	public void setPK_INVCL(String pK_INVCL) {
		PK_INVCL = pK_INVCL;
	}
	public String getPK_MEASDOC() {
		return PK_MEASDOC;
	}
	public void setPK_MEASDOC(String pK_MEASDOC) {
		PK_MEASDOC = pK_MEASDOC;
	}
	public String getPK_MEASDOC1() {
		return PK_MEASDOC1;
	}
	public void setPK_MEASDOC1(String pK_MEASDOC1) {
		PK_MEASDOC1 = pK_MEASDOC1;
	}
	public String getPK_MEASDOC2() {
		return PK_MEASDOC2;
	}
	public void setPK_MEASDOC2(String pK_MEASDOC2) {
		PK_MEASDOC2 = pK_MEASDOC2;
	}
	public String getPK_MEASDOC3() {
		return PK_MEASDOC3;
	}
	public void setPK_MEASDOC3(String pK_MEASDOC3) {
		PK_MEASDOC3 = pK_MEASDOC3;
	}
	public String getPK_MEASDOC5() {
		return PK_MEASDOC5;
	}
	public void setPK_MEASDOC5(String pK_MEASDOC5) {
		PK_MEASDOC5 = pK_MEASDOC5;
	}
	public String getPK_MEASDOC6() {
		return PK_MEASDOC6;
	}
	public void setPK_MEASDOC6(String pK_MEASDOC6) {
		PK_MEASDOC6 = pK_MEASDOC6;
	}
	public String getPK_PRODLINE() {
		return PK_PRODLINE;
	}
	public void setPK_PRODLINE(String pK_PRODLINE) {
		PK_PRODLINE = pK_PRODLINE;
	}
	public String getPK_TAXITEMS() {
		return PK_TAXITEMS;
	}
	public void setPK_TAXITEMS(String pK_TAXITEMS) {
		PK_TAXITEMS = pK_TAXITEMS;
	}
	public String getSEALFLAG() {
		return SEALFLAG;
	}
	public void setSEALFLAG(String sEALFLAG) {
		SEALFLAG = sEALFLAG;
	}
	public String getSETPARTSFLAG() {
		return SETPARTSFLAG;
	}
	public void setSETPARTSFLAG(String sETPARTSFLAG) {
		SETPARTSFLAG = sETPARTSFLAG;
	}
	public String getSHIPUNITNUM() {
		return SHIPUNITNUM;
	}
	public void setSHIPUNITNUM(String sHIPUNITNUM) {
		SHIPUNITNUM = sHIPUNITNUM;
	}
	public String getSTOREUNITNUM() {
		return STOREUNITNUM;
	}
	public void setSTOREUNITNUM(String sTOREUNITNUM) {
		STOREUNITNUM = sTOREUNITNUM;
	}
	public Timestamp getTS() {
		return TS;
	}
	public void setTS(Timestamp tS) {
		TS = tS;
	}
	public String getUNITVOLUME() {
		return UNITVOLUME;
	}
	public void setUNITVOLUME(String uNITVOLUME) {
		UNITVOLUME = uNITVOLUME;
	}
	public String getUNITWEIGHT() {
		return UNITWEIGHT;
	}
	public void setUNITWEIGHT(String uNITWEIGHT) {
		UNITWEIGHT = uNITWEIGHT;
	}
	public String getWEITUNITNUM() {
		return WEITUNITNUM;
	}
	public void setWEITUNITNUM(String wEITUNITNUM) {
		WEITUNITNUM = wEITUNITNUM;
	}
	public String getWIDTH() {
		return WIDTH;
	}
	public void setWIDTH(String wIDTH) {
		WIDTH = wIDTH;
	}
	public String getCorpUNITCODE() {
		return corpUNITCODE;
	}
	public void setCorpUNITCODE(String corpUNITCODE) {
		this.corpUNITCODE = corpUNITCODE;
	} 
	
	@Override
	public String getDocSource() {
		return dataSource;
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
