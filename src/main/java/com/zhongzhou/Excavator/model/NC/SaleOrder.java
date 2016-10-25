package com.zhongzhou.Excavator.model.NC;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SaleOrder {

	private String BCOOPTOPO              ; 
	private String BFREECUSTFLAG          ;
	private String BINITFLAG              ;
	private String BINVOICENDFLAG         ;
	private String BOUTENDFLAG            ;
	private String BOVERDATE              ;
	private String BPAYENDFLAG            ;
	private String BPOCOOPTOME            ;
	private String BRECEIPTENDFLAG        ;
	private String BRETINVFLAG            ;
	private String BTRANSENDFLAG          ;
	private String CAPPROVEID             ;
	private String CBALTYPEID             ;
	private String CBIZTYPE               ;
	private String CCALBODYID             ;
	private String CCOOPPOHID             ;
	private String CCREDITNUM             ;
	private String CCUSTBANKID            ;
	private String CCUSTOMERID            ;
	private String CDEPTID                ;
	private String CEMPLOYEEID            ;
	private String CFREECUSTID            ;
	private String COPERATORID            ;
	private String CRECEIPTAREAID         ;
	private String CRECEIPTCORPID         ;
	private String CRECEIPTCUSTOMERID     ;
	private String CRECEIPTTYPE           ;
	private String CSALECORPID            ;
	private String CSALEID                ;
	private String CTERMPROTOCOLID        ;
	private String CTRANSMODEID           ;
	private String CWAREHOUSEID           ;
	private String DAPPROVEDATE           ;
	private String DAUDITTIME             ;
	private String DBILLDATE              ;
	private String DBILLTIME              ;
	private String DMAKEDATE              ;
	private String DMODITIME              ;
	private String DR                     ;
	private String EDITAUTHOR             ;
	private String EDITDATE               ;
	private String EDITIONNUM             ;
	private String FINVOICECLASS          ;
	private String FINVOICETYPE           ;
	private String FSTATUS                ;
	private String IBALANCEFLAG           ;
	private String IPRINTCOUNT            ;
	private String NACCOUNTPERIOD         ;
	private String NDISCOUNTRATE          ;
	private String NEVALUATECARRIAGE      ;
	private String NHEADSUMMNY            ;
	private String NPRECEIVEMNY           ;
	private String NPRECEIVERATE          ;
	private String NSUBSCRIPTION          ;
	private String PK_CORP                ;
	private String PK_DEFDOC1             ;
	private String PK_DEFDOC10            ;
	private String PK_DEFDOC11            ;
	private String PK_DEFDOC12            ;
	private String PK_DEFDOC13            ;
	private String PK_DEFDOC14            ;
	private String PK_DEFDOC15            ;
	private String PK_DEFDOC16            ;
	private String PK_DEFDOC17            ;
	private String PK_DEFDOC18            ;
	private String PK_DEFDOC19            ;
	private String PK_DEFDOC2             ;
	private String PK_DEFDOC20            ;
	private String PK_DEFDOC3             ;
	private String PK_DEFDOC4             ;
	private String PK_DEFDOC5             ;
	private String PK_DEFDOC6             ;
	private String PK_DEFDOC7             ;
	private String PK_DEFDOC8             ;
	private String PK_DEFDOC9             ;
	private Timestamp TS                  ;
	private String VACCOUNTYEAR           ;
	private String VDEF1                  ;
	private String VDEF10                 ;
	private String VDEF11                 ;
	private String VDEF12                 ;
	private String VDEF13                 ;
	private String VDEF14                 ;
	private String VDEF15                 ;
	private String VDEF16                 ;
	private String VDEF17                 ;
	private String VDEF18                 ;
	private String VDEF19                 ;
	private String VDEF2                  ;
	private String VDEF20                 ;
	private String VDEF3                  ;
	private String VDEF4                  ;
	private String VDEF5                  ;
	private String VDEF6                  ;
	private String VDEF7                  ;
	private String VDEF8                  ;
	private String VDEF9                  ;
	private String VEDITREASON            ;
	private String VNOTE                  ;
	private String VRECEIPTCODE           ;
	private String VRECEIVEADDRESS        ;
	
	private List<SaleOrderItem> items;
	
	public List<SaleOrderItem> getItems() {
		
		return this.items;
	}
	public void setItems(List<SaleOrderItem> items) {
		this.items = items;
	}
	public String getBCOOPTOPO() {
		return BCOOPTOPO;
	}
	public void setBCOOPTOPO(String bCOOPTOPO) {
		BCOOPTOPO = bCOOPTOPO;
	}
	public String getBFREECUSTFLAG() {
		return BFREECUSTFLAG;
	}
	public void setBFREECUSTFLAG(String bFREECUSTFLAG) {
		BFREECUSTFLAG = bFREECUSTFLAG;
	}
	public String getBINITFLAG() {
		return BINITFLAG;
	}
	public void setBINITFLAG(String bINITFLAG) {
		BINITFLAG = bINITFLAG;
	}
	public String getBINVOICENDFLAG() {
		return BINVOICENDFLAG;
	}
	public void setBINVOICENDFLAG(String bINVOICENDFLAG) {
		BINVOICENDFLAG = bINVOICENDFLAG;
	}
	public String getBOUTENDFLAG() {
		return BOUTENDFLAG;
	}
	public void setBOUTENDFLAG(String bOUTENDFLAG) {
		BOUTENDFLAG = bOUTENDFLAG;
	}
	public String getBOVERDATE() {
		return BOVERDATE;
	}
	public void setBOVERDATE(String bOVERDATE) {
		BOVERDATE = bOVERDATE;
	}
	public String getBPAYENDFLAG() {
		return BPAYENDFLAG;
	}
	public void setBPAYENDFLAG(String bPAYENDFLAG) {
		BPAYENDFLAG = bPAYENDFLAG;
	}
	public String getBPOCOOPTOME() {
		return BPOCOOPTOME;
	}
	public void setBPOCOOPTOME(String bPOCOOPTOME) {
		BPOCOOPTOME = bPOCOOPTOME;
	}
	public String getBRECEIPTENDFLAG() {
		return BRECEIPTENDFLAG;
	}
	public void setBRECEIPTENDFLAG(String bRECEIPTENDFLAG) {
		BRECEIPTENDFLAG = bRECEIPTENDFLAG;
	}
	public String getBRETINVFLAG() {
		return BRETINVFLAG;
	}
	public void setBRETINVFLAG(String bRETINVFLAG) {
		BRETINVFLAG = bRETINVFLAG;
	}
	public String getBTRANSENDFLAG() {
		return BTRANSENDFLAG;
	}
	public void setBTRANSENDFLAG(String bTRANSENDFLAG) {
		BTRANSENDFLAG = bTRANSENDFLAG;
	}
	public String getCAPPROVEID() {
		return CAPPROVEID;
	}
	public void setCAPPROVEID(String cAPPROVEID) {
		CAPPROVEID = cAPPROVEID;
	}
	public String getCBALTYPEID() {
		return CBALTYPEID;
	}
	public void setCBALTYPEID(String cBALTYPEID) {
		CBALTYPEID = cBALTYPEID;
	}
	public String getCBIZTYPE() {
		return CBIZTYPE;
	}
	public void setCBIZTYPE(String cBIZTYPE) {
		CBIZTYPE = cBIZTYPE;
	}
	public String getCCALBODYID() {
		return CCALBODYID;
	}
	public void setCCALBODYID(String cCALBODYID) {
		CCALBODYID = cCALBODYID;
	}
	public String getCCOOPPOHID() {
		return CCOOPPOHID;
	}
	public void setCCOOPPOHID(String cCOOPPOHID) {
		CCOOPPOHID = cCOOPPOHID;
	}
	public String getCCREDITNUM() {
		return CCREDITNUM;
	}
	public void setCCREDITNUM(String cCREDITNUM) {
		CCREDITNUM = cCREDITNUM;
	}
	public String getCCUSTBANKID() {
		return CCUSTBANKID;
	}
	public void setCCUSTBANKID(String cCUSTBANKID) {
		CCUSTBANKID = cCUSTBANKID;
	}
	public String getCCUSTOMERID() {
		return CCUSTOMERID;
	}
	public void setCCUSTOMERID(String cCUSTOMERID) {
		CCUSTOMERID = cCUSTOMERID;
	}
	public String getCDEPTID() {
		return CDEPTID;
	}
	public void setCDEPTID(String cDEPTID) {
		CDEPTID = cDEPTID;
	}
	public String getCEMPLOYEEID() {
		return CEMPLOYEEID;
	}
	public void setCEMPLOYEEID(String cEMPLOYEEID) {
		CEMPLOYEEID = cEMPLOYEEID;
	}
	public String getCFREECUSTID() {
		return CFREECUSTID;
	}
	public void setCFREECUSTID(String cFREECUSTID) {
		CFREECUSTID = cFREECUSTID;
	}
	public String getCOPERATORID() {
		return COPERATORID;
	}
	public void setCOPERATORID(String cOPERATORID) {
		COPERATORID = cOPERATORID;
	}
	public String getCRECEIPTAREAID() {
		return CRECEIPTAREAID;
	}
	public void setCRECEIPTAREAID(String cRECEIPTAREAID) {
		CRECEIPTAREAID = cRECEIPTAREAID;
	}
	public String getCRECEIPTCORPID() {
		return CRECEIPTCORPID;
	}
	public void setCRECEIPTCORPID(String cRECEIPTCORPID) {
		CRECEIPTCORPID = cRECEIPTCORPID;
	}
	public String getCRECEIPTCUSTOMERID() {
		return CRECEIPTCUSTOMERID;
	}
	public void setCRECEIPTCUSTOMERID(String cRECEIPTCUSTOMERID) {
		CRECEIPTCUSTOMERID = cRECEIPTCUSTOMERID;
	}
	public String getCRECEIPTTYPE() {
		return CRECEIPTTYPE;
	}
	public void setCRECEIPTTYPE(String cRECEIPTTYPE) {
		CRECEIPTTYPE = cRECEIPTTYPE;
	}
	public String getCSALECORPID() {
		return CSALECORPID;
	}
	public void setCSALECORPID(String cSALECORPID) {
		CSALECORPID = cSALECORPID;
	}
	public String getCSALEID() {
		return CSALEID;
	}
	public void setCSALEID(String cSALEID) {
		CSALEID = cSALEID;
	}
	public String getCTERMPROTOCOLID() {
		return CTERMPROTOCOLID;
	}
	public void setCTERMPROTOCOLID(String cTERMPROTOCOLID) {
		CTERMPROTOCOLID = cTERMPROTOCOLID;
	}
	public String getCTRANSMODEID() {
		return CTRANSMODEID;
	}
	public void setCTRANSMODEID(String cTRANSMODEID) {
		CTRANSMODEID = cTRANSMODEID;
	}
	public String getCWAREHOUSEID() {
		return CWAREHOUSEID;
	}
	public void setCWAREHOUSEID(String cWAREHOUSEID) {
		CWAREHOUSEID = cWAREHOUSEID;
	}
	public String getDAPPROVEDATE() {
		return DAPPROVEDATE;
	}
	public void setDAPPROVEDATE(String dAPPROVEDATE) {
		DAPPROVEDATE = dAPPROVEDATE;
	}
	public String getDAUDITTIME() {
		return DAUDITTIME;
	}
	public void setDAUDITTIME(String dAUDITTIME) {
		DAUDITTIME = dAUDITTIME;
	}
	public String getDBILLDATE() {
		return DBILLDATE;
	}
	public void setDBILLDATE(String dBILLDATE) {
		DBILLDATE = dBILLDATE;
	}
	public String getDBILLTIME() {
		return DBILLTIME;
	}
	public void setDBILLTIME(String dBILLTIME) {
		DBILLTIME = dBILLTIME;
	}
	public String getDMAKEDATE() {
		return DMAKEDATE;
	}
	public void setDMAKEDATE(String dMAKEDATE) {
		DMAKEDATE = dMAKEDATE;
	}
	public String getDMODITIME() {
		return DMODITIME;
	}
	public void setDMODITIME(String dMODITIME) {
		DMODITIME = dMODITIME;
	}
	public String getDR() {
		return DR;
	}
	public void setDR(String dR) {
		DR = dR;
	}
	public String getEDITAUTHOR() {
		return EDITAUTHOR;
	}
	public void setEDITAUTHOR(String eDITAUTHOR) {
		EDITAUTHOR = eDITAUTHOR;
	}
	public String getEDITDATE() {
		return EDITDATE;
	}
	public void setEDITDATE(String eDITDATE) {
		EDITDATE = eDITDATE;
	}
	public String getEDITIONNUM() {
		return EDITIONNUM;
	}
	public void setEDITIONNUM(String eDITIONNUM) {
		EDITIONNUM = eDITIONNUM;
	}
	public String getFINVOICECLASS() {
		return FINVOICECLASS;
	}
	public void setFINVOICECLASS(String fINVOICECLASS) {
		FINVOICECLASS = fINVOICECLASS;
	}
	public String getFINVOICETYPE() {
		return FINVOICETYPE;
	}
	public void setFINVOICETYPE(String fINVOICETYPE) {
		FINVOICETYPE = fINVOICETYPE;
	}
	public String getFSTATUS() {
		return FSTATUS;
	}
	public void setFSTATUS(String fSTATUS) {
		FSTATUS = fSTATUS;
	}
	public String getIBALANCEFLAG() {
		return IBALANCEFLAG;
	}
	public void setIBALANCEFLAG(String iBALANCEFLAG) {
		IBALANCEFLAG = iBALANCEFLAG;
	}
	public String getIPRINTCOUNT() {
		return IPRINTCOUNT;
	}
	public void setIPRINTCOUNT(String iPRINTCOUNT) {
		IPRINTCOUNT = iPRINTCOUNT;
	}
	public String getNACCOUNTPERIOD() {
		return NACCOUNTPERIOD;
	}
	public void setNACCOUNTPERIOD(String nACCOUNTPERIOD) {
		NACCOUNTPERIOD = nACCOUNTPERIOD;
	}
	public String getNDISCOUNTRATE() {
		return NDISCOUNTRATE;
	}
	public void setNDISCOUNTRATE(String nDISCOUNTRATE) {
		NDISCOUNTRATE = nDISCOUNTRATE;
	}
	public String getNEVALUATECARRIAGE() {
		return NEVALUATECARRIAGE;
	}
	public void setNEVALUATECARRIAGE(String nEVALUATECARRIAGE) {
		NEVALUATECARRIAGE = nEVALUATECARRIAGE;
	}
	public String getNHEADSUMMNY() {
		return NHEADSUMMNY;
	}
	public void setNHEADSUMMNY(String nHEADSUMMNY) {
		NHEADSUMMNY = nHEADSUMMNY;
	}
	public String getNPRECEIVEMNY() {
		return NPRECEIVEMNY;
	}
	public void setNPRECEIVEMNY(String nPRECEIVEMNY) {
		NPRECEIVEMNY = nPRECEIVEMNY;
	}
	public String getNPRECEIVERATE() {
		return NPRECEIVERATE;
	}
	public void setNPRECEIVERATE(String nPRECEIVERATE) {
		NPRECEIVERATE = nPRECEIVERATE;
	}
	public String getNSUBSCRIPTION() {
		return NSUBSCRIPTION;
	}
	public void setNSUBSCRIPTION(String nSUBSCRIPTION) {
		NSUBSCRIPTION = nSUBSCRIPTION;
	}
	public String getPK_CORP() {
		return PK_CORP;
	}
	public void setPK_CORP(String pK_CORP) {
		PK_CORP = pK_CORP;
	}
	public String getPK_DEFDOC1() {
		return PK_DEFDOC1;
	}
	public void setPK_DEFDOC1(String pK_DEFDOC1) {
		PK_DEFDOC1 = pK_DEFDOC1;
	}
	public String getPK_DEFDOC10() {
		return PK_DEFDOC10;
	}
	public void setPK_DEFDOC10(String pK_DEFDOC10) {
		PK_DEFDOC10 = pK_DEFDOC10;
	}
	public String getPK_DEFDOC11() {
		return PK_DEFDOC11;
	}
	public void setPK_DEFDOC11(String pK_DEFDOC11) {
		PK_DEFDOC11 = pK_DEFDOC11;
	}
	public String getPK_DEFDOC12() {
		return PK_DEFDOC12;
	}
	public void setPK_DEFDOC12(String pK_DEFDOC12) {
		PK_DEFDOC12 = pK_DEFDOC12;
	}
	public String getPK_DEFDOC13() {
		return PK_DEFDOC13;
	}
	public void setPK_DEFDOC13(String pK_DEFDOC13) {
		PK_DEFDOC13 = pK_DEFDOC13;
	}
	public String getPK_DEFDOC14() {
		return PK_DEFDOC14;
	}
	public void setPK_DEFDOC14(String pK_DEFDOC14) {
		PK_DEFDOC14 = pK_DEFDOC14;
	}
	public String getPK_DEFDOC15() {
		return PK_DEFDOC15;
	}
	public void setPK_DEFDOC15(String pK_DEFDOC15) {
		PK_DEFDOC15 = pK_DEFDOC15;
	}
	public String getPK_DEFDOC16() {
		return PK_DEFDOC16;
	}
	public void setPK_DEFDOC16(String pK_DEFDOC16) {
		PK_DEFDOC16 = pK_DEFDOC16;
	}
	public String getPK_DEFDOC17() {
		return PK_DEFDOC17;
	}
	public void setPK_DEFDOC17(String pK_DEFDOC17) {
		PK_DEFDOC17 = pK_DEFDOC17;
	}
	public String getPK_DEFDOC18() {
		return PK_DEFDOC18;
	}
	public void setPK_DEFDOC18(String pK_DEFDOC18) {
		PK_DEFDOC18 = pK_DEFDOC18;
	}
	public String getPK_DEFDOC19() {
		return PK_DEFDOC19;
	}
	public void setPK_DEFDOC19(String pK_DEFDOC19) {
		PK_DEFDOC19 = pK_DEFDOC19;
	}
	public String getPK_DEFDOC2() {
		return PK_DEFDOC2;
	}
	public void setPK_DEFDOC2(String pK_DEFDOC2) {
		PK_DEFDOC2 = pK_DEFDOC2;
	}
	public String getPK_DEFDOC20() {
		return PK_DEFDOC20;
	}
	public void setPK_DEFDOC20(String pK_DEFDOC20) {
		PK_DEFDOC20 = pK_DEFDOC20;
	}
	public String getPK_DEFDOC3() {
		return PK_DEFDOC3;
	}
	public void setPK_DEFDOC3(String pK_DEFDOC3) {
		PK_DEFDOC3 = pK_DEFDOC3;
	}
	public String getPK_DEFDOC4() {
		return PK_DEFDOC4;
	}
	public void setPK_DEFDOC4(String pK_DEFDOC4) {
		PK_DEFDOC4 = pK_DEFDOC4;
	}
	public String getPK_DEFDOC5() {
		return PK_DEFDOC5;
	}
	public void setPK_DEFDOC5(String pK_DEFDOC5) {
		PK_DEFDOC5 = pK_DEFDOC5;
	}
	public String getPK_DEFDOC6() {
		return PK_DEFDOC6;
	}
	public void setPK_DEFDOC6(String pK_DEFDOC6) {
		PK_DEFDOC6 = pK_DEFDOC6;
	}
	public String getPK_DEFDOC7() {
		return PK_DEFDOC7;
	}
	public void setPK_DEFDOC7(String pK_DEFDOC7) {
		PK_DEFDOC7 = pK_DEFDOC7;
	}
	public String getPK_DEFDOC8() {
		return PK_DEFDOC8;
	}
	public void setPK_DEFDOC8(String pK_DEFDOC8) {
		PK_DEFDOC8 = pK_DEFDOC8;
	}
	public String getPK_DEFDOC9() {
		return PK_DEFDOC9;
	}
	public void setPK_DEFDOC9(String pK_DEFDOC9) {
		PK_DEFDOC9 = pK_DEFDOC9;
	}
	public Timestamp getTS() {
		return TS;
	}
	public void setTS(Timestamp tS) {
		TS = tS;
	}
	public String getVACCOUNTYEAR() {
		return VACCOUNTYEAR;
	}
	public void setVACCOUNTYEAR(String vACCOUNTYEAR) {
		VACCOUNTYEAR = vACCOUNTYEAR;
	}
	public String getVDEF1() {
		return VDEF1;
	}
	public void setVDEF1(String vDEF1) {
		VDEF1 = vDEF1;
	}
	public String getVDEF10() {
		return VDEF10;
	}
	public void setVDEF10(String vDEF10) {
		VDEF10 = vDEF10;
	}
	public String getVDEF11() {
		return VDEF11;
	}
	public void setVDEF11(String vDEF11) {
		VDEF11 = vDEF11;
	}
	public String getVDEF12() {
		return VDEF12;
	}
	public void setVDEF12(String vDEF12) {
		VDEF12 = vDEF12;
	}
	public String getVDEF13() {
		return VDEF13;
	}
	public void setVDEF13(String vDEF13) {
		VDEF13 = vDEF13;
	}
	public String getVDEF14() {
		return VDEF14;
	}
	public void setVDEF14(String vDEF14) {
		VDEF14 = vDEF14;
	}
	public String getVDEF15() {
		return VDEF15;
	}
	public void setVDEF15(String vDEF15) {
		VDEF15 = vDEF15;
	}
	public String getVDEF16() {
		return VDEF16;
	}
	public void setVDEF16(String vDEF16) {
		VDEF16 = vDEF16;
	}
	public String getVDEF17() {
		return VDEF17;
	}
	public void setVDEF17(String vDEF17) {
		VDEF17 = vDEF17;
	}
	public String getVDEF18() {
		return VDEF18;
	}
	public void setVDEF18(String vDEF18) {
		VDEF18 = vDEF18;
	}
	public String getVDEF19() {
		return VDEF19;
	}
	public void setVDEF19(String vDEF19) {
		VDEF19 = vDEF19;
	}
	public String getVDEF2() {
		return VDEF2;
	}
	public void setVDEF2(String vDEF2) {
		VDEF2 = vDEF2;
	}
	public String getVDEF20() {
		return VDEF20;
	}
	public void setVDEF20(String vDEF20) {
		VDEF20 = vDEF20;
	}
	public String getVDEF3() {
		return VDEF3;
	}
	public void setVDEF3(String vDEF3) {
		VDEF3 = vDEF3;
	}
	public String getVDEF4() {
		return VDEF4;
	}
	public void setVDEF4(String vDEF4) {
		VDEF4 = vDEF4;
	}
	public String getVDEF5() {
		return VDEF5;
	}
	public void setVDEF5(String vDEF5) {
		VDEF5 = vDEF5;
	}
	public String getVDEF6() {
		return VDEF6;
	}
	public void setVDEF6(String vDEF6) {
		VDEF6 = vDEF6;
	}
	public String getVDEF7() {
		return VDEF7;
	}
	public void setVDEF7(String vDEF7) {
		VDEF7 = vDEF7;
	}
	public String getVDEF8() {
		return VDEF8;
	}
	public void setVDEF8(String vDEF8) {
		VDEF8 = vDEF8;
	}
	public String getVDEF9() {
		return VDEF9;
	}
	public void setVDEF9(String vDEF9) {
		VDEF9 = vDEF9;
	}
	public String getVEDITREASON() {
		return VEDITREASON;
	}
	public void setVEDITREASON(String vEDITREASON) {
		VEDITREASON = vEDITREASON;
	}
	public String getVNOTE() {
		return VNOTE;
	}
	public void setVNOTE(String vNOTE) {
		VNOTE = vNOTE;
	}
	public String getVRECEIPTCODE() {
		return VRECEIPTCODE;
	}
	public void setVRECEIPTCODE(String vRECEIPTCODE) {
		VRECEIPTCODE = vRECEIPTCODE;
	}
	public String getVRECEIVEADDRESS() {
		return VRECEIVEADDRESS;
	}
	public void setVRECEIVEADDRESS(String vRECEIVEADDRESS) {
		VRECEIVEADDRESS = vRECEIVEADDRESS;
	}
}
