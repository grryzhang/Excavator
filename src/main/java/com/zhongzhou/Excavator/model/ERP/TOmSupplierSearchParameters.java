package com.zhongzhou.Excavator.model.ERP;

import java.util.ArrayList;
import java.util.List;

public class TOmSupplierSearchParameters {

	private String FID;
	
	private List<String> FIDs;
	
	private String FPARENTNO;
	
	private List<String> FPARENTNOs;
	
	private String FNUMBER;
	
	private List<String> FNUMBERs;

	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		
		FID = fID;
		this.FIDs = new ArrayList<String>();
		FIDs.add( this.FID );
	}

	public List<String> getFIDs() {
		return FIDs;
	}

	public void setFIDs(List<String> fIDs) {
		FIDs = fIDs;
	}

	public String getFPARENTNO() {
		return FPARENTNO;
	}

	public void setFPARENTNO(String fPARENTNO) {
		FPARENTNO = fPARENTNO;
		this.FPARENTNOs = new ArrayList<String>();
		FPARENTNOs.add( this.FPARENTNO );
	}

	public List<String> getFPARENTNOs() {
		return FPARENTNOs;
	}

	public void setFPARENTNOs(List<String> fPARENTNOs) {
		FPARENTNOs = fPARENTNOs;
	}

	public String getFNUMBER() {
		return FNUMBER;
	}

	public void setFNUMBER(String fNUMBER) {
		FNUMBER = fNUMBER;
	}

	public List<String> getFNUMBERs() {
		return FNUMBERs;
	}

	public void setFNUMBERs(List<String> fNUMBERs) {
		FNUMBERs = fNUMBERs;
		this.FNUMBERs = new ArrayList<String>();
		FNUMBERs.add( this.FNUMBER );
	}
}
