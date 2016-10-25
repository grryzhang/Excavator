package com.zhongzhou.Excavator.model.NC;

import java.sql.Timestamp;

import org.mongodb.morphia.annotations.Entity;

import com.zhongzhou.Excavator.model.masterdata.BasicStoreDocument;

@Entity( value="external_price_category" )
public class PriceCategory implements BasicStoreDocument {
	
	public static final String dataSource = "NC.PRM_TARIFF";
	private String targetId;
	private Timestamp docCreateTime;

	private String BCURRENTTARIFF	 ; 
	private String CCREATERID	     ; 
	private String CDEFCURRENCYID	 ; 
	private String CDEFMEASDOCID	 ; 
	private String CDOWNPRICETARIFFID;
	private String CPRICETARIFFCODE	 ;
	private String CPRICETARIFFID	 ; 
	private String CPRICETARIFFNAME	 ;
	private String DCREATEDATE	     ; 
	private int    DR	             ; 
	private int    FCURRENCYDIM	     ;
	private int    FCUSTCLDIM	     ; 
	private int    FCUSTOMERDIM	     ;
	private int    FINVCLASSDIM	     ;
	private int    FINVDIM	         ; 
	private int    FMEASDIM	         ;
	private int    FRECAREADIM	     ; 
	private int    FSALEORGDIM	     ; 
	private String PK_CORP	         ; 
	private Timestamp TS	         ;
	
	
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public static String getDataSource() {
		return dataSource;
	}
	public String getBCURRENTTARIFF() {
		return BCURRENTTARIFF;
	}
	public void setBCURRENTTARIFF(String bCURRENTTARIFF) {
		BCURRENTTARIFF = bCURRENTTARIFF;
	}
	public String getCCREATERID() {
		return CCREATERID;
	}
	public void setCCREATERID(String cCREATERID) {
		CCREATERID = cCREATERID;
	}
	public String getCDEFCURRENCYID() {
		return CDEFCURRENCYID;
	}
	public void setCDEFCURRENCYID(String cDEFCURRENCYID) {
		CDEFCURRENCYID = cDEFCURRENCYID;
	}
	public String getCDEFMEASDOCID() {
		return CDEFMEASDOCID;
	}
	public void setCDEFMEASDOCID(String cDEFMEASDOCID) {
		CDEFMEASDOCID = cDEFMEASDOCID;
	}
	public String getCDOWNPRICETARIFFID() {
		return CDOWNPRICETARIFFID;
	}
	public void setCDOWNPRICETARIFFID(String cDOWNPRICETARIFFID) {
		CDOWNPRICETARIFFID = cDOWNPRICETARIFFID;
	}
	public String getCPRICETARIFFCODE() {
		return CPRICETARIFFCODE;
	}
	public void setCPRICETARIFFCODE(String cPRICETARIFFCODE) {
		CPRICETARIFFCODE = cPRICETARIFFCODE;
	}
	public String getCPRICETARIFFID() {
		return CPRICETARIFFID;
	}
	public void setCPRICETARIFFID(String cPRICETARIFFID) {
		CPRICETARIFFID = cPRICETARIFFID;
	}
	public String getCPRICETARIFFNAME() {
		return CPRICETARIFFNAME;
	}
	public void setCPRICETARIFFNAME(String cPRICETARIFFNAME) {
		CPRICETARIFFNAME = cPRICETARIFFNAME;
	}
	public String getDCREATEDATE() {
		return DCREATEDATE;
	}
	public void setDCREATEDATE(String dCREATEDATE) {
		DCREATEDATE = dCREATEDATE;
	}
	public int getDR() {
		return DR;
	}
	public void setDR(int dR) {
		DR = dR;
	}
	public int getFCURRENCYDIM() {
		return FCURRENCYDIM;
	}
	public void setFCURRENCYDIM(int fCURRENCYDIM) {
		FCURRENCYDIM = fCURRENCYDIM;
	}
	public int getFCUSTCLDIM() {
		return FCUSTCLDIM;
	}
	public void setFCUSTCLDIM(int fCUSTCLDIM) {
		FCUSTCLDIM = fCUSTCLDIM;
	}
	public int getFCUSTOMERDIM() {
		return FCUSTOMERDIM;
	}
	public void setFCUSTOMERDIM(int fCUSTOMERDIM) {
		FCUSTOMERDIM = fCUSTOMERDIM;
	}
	public int getFINVCLASSDIM() {
		return FINVCLASSDIM;
	}
	public void setFINVCLASSDIM(int fINVCLASSDIM) {
		FINVCLASSDIM = fINVCLASSDIM;
	}
	public int getFINVDIM() {
		return FINVDIM;
	}
	public void setFINVDIM(int fINVDIM) {
		FINVDIM = fINVDIM;
	}
	public int getFMEASDIM() {
		return FMEASDIM;
	}
	public void setFMEASDIM(int fMEASDIM) {
		FMEASDIM = fMEASDIM;
	}
	public int getFRECAREADIM() {
		return FRECAREADIM;
	}
	public void setFRECAREADIM(int fRECAREADIM) {
		FRECAREADIM = fRECAREADIM;
	}
	public int getFSALEORGDIM() {
		return FSALEORGDIM;
	}
	public void setFSALEORGDIM(int fSALEORGDIM) {
		FSALEORGDIM = fSALEORGDIM;
	}
	public String getPK_CORP() {
		return PK_CORP;
	}
	public void setPK_CORP(String pK_CORP) {
		PK_CORP = pK_CORP;
	}
	public Timestamp getTS() {
		return TS;
	}
	public void setTS(Timestamp tS) {
		TS = tS;
	}
	@Override
	public String getDocSource() {
		// TODO Auto-generated method stub
		return null;
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
		return docCreateTime;
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
		// TODO Auto-generated method stub
		return null;
	} 
}
