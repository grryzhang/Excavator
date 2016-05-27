package com.zhongzhou.Excavator.model.NC;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.mongodb.morphia.annotations.Entity;

import com.zhongzhou.Excavator.model.BasicStoreDocument;

@Entity( value="external_price" )
public class Price implements BasicStoreDocument {
	
	public static final String dataSource = "NC.PRM_TARIFFCURLIST";
	private String 		targetId;
	private Timestamp   docCreateTime;

	private String      CPRICETARIFF_BID;
	private String		PK_CORP;
	private String 		UNITNAME;
    private String 		PK_CUBASDOC;
    private String 		CUSTSHORTNAME;
    private BigDecimal  NPRICE0;
    private BigDecimal  NPRICE1;
    private String 		PK_CURRTYPE;
    private String 		CURRTYPENAME;
    private String      CPRICETYPECODE;
    private String 		CPRICETYPENAME;
    private String 		MEASNAME;
    private String 		PK_MEASDOC;
    private String 		PK_INVBASDOC;
    private String 		CPRICETARIFFNAME;
    private String 		CPRICETARIFFID;
    private Timestamp 	TS;
    
	public String getCPRICETARIFF_BID() {
		return CPRICETARIFF_BID;
	}
	public void setCPRICETARIFF_BID(String cPRICETARIFF_BID) {
		CPRICETARIFF_BID = cPRICETARIFF_BID;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public Timestamp getDocCreateTime() {
		return docCreateTime;
	}
	public void setDocCreateTime(Timestamp docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	public String getPK_CORP() {
		return PK_CORP;
	}
	public void setPK_CORP(String pK_CORP) {
		PK_CORP = pK_CORP;
	}
	public String getUNITNAME() {
		return UNITNAME;
	}
	public void setUNITNAME(String uNITNAME) {
		UNITNAME = uNITNAME;
	}
	public String getPK_CUBASDOC() {
		return PK_CUBASDOC;
	}
	public void setPK_CUBASDOC(String pK_CUBASDOC) {
		PK_CUBASDOC = pK_CUBASDOC;
	}
	public String getCUSTSHORTNAME() {
		return CUSTSHORTNAME;
	}
	public void setCUSTSHORTNAME(String cUSTSHORTNAME) {
		CUSTSHORTNAME = cUSTSHORTNAME;
	}
	public BigDecimal getNPRICE0() {
		return NPRICE0;
	}
	public void setNPRICE0(BigDecimal nPRICE0) {
		NPRICE0 = nPRICE0;
	}
	public BigDecimal getNPRICE1() {
		return NPRICE1;
	}
	public void setNPRICE1(BigDecimal nPRICE1) {
		NPRICE1 = nPRICE1;
	}
	public String getPK_CURRTYPE() {
		return PK_CURRTYPE;
	}
	public void setPK_CURRTYPE(String pK_CURRTYPE) {
		PK_CURRTYPE = pK_CURRTYPE;
	}
	public String getCURRTYPENAME() {
		return CURRTYPENAME;
	}
	public void setCURRTYPENAME(String cURRTYPENAME) {
		CURRTYPENAME = cURRTYPENAME;
	}
	public String getCPRICETYPENAME() {
		return CPRICETYPENAME;
	}
	public void setCPRICETYPENAME(String cPRICETYPENAME) {
		CPRICETYPENAME = cPRICETYPENAME;
	}
	public String getCPRICETYPECODE() {
		return CPRICETYPECODE;
	}
	public void setCPRICETYPECODE(String cPRICETYPECODE) {
		CPRICETYPECODE = cPRICETYPECODE;
	}
	public String getMEASNAME() {
		return MEASNAME;
	}
	public void setMEASNAME(String mEASNAME) {
		MEASNAME = mEASNAME;
	}
	public String getPK_MEASDOC() {
		return PK_MEASDOC;
	}
	public void setPK_MEASDOC(String pK_MEASDOC) {
		PK_MEASDOC = pK_MEASDOC;
	}
	public String getPK_INVBASDOC() {
		return PK_INVBASDOC;
	}
	public void setPK_INVBASDOC(String pK_INVBASDOC) {
		PK_INVBASDOC = pK_INVBASDOC;
	}
	public String getCPRICETARIFFNAME() {
		return CPRICETARIFFNAME;
	}
	public void setCPRICETARIFFNAME(String cPRICETARIFFNAME) {
		CPRICETARIFFNAME = cPRICETARIFFNAME;
	}
	public String getCPRICETARIFFID() {
		return CPRICETARIFFID;
	}
	public void setCPRICETARIFFID(String cPRICETARIFFID) {
		CPRICETARIFFID = cPRICETARIFFID;
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
