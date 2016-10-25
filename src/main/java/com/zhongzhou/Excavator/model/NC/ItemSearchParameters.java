package com.zhongzhou.Excavator.model.NC;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ItemSearchParameters {
	
	private Integer start = 0;
	
	private Integer end = 100;
	
	private String pkInvbasdoc;
	
	private List<String> pkInvbasdocs;
	
	private String invType;
	
	private List<String> invTypes;

	private Timestamp updateTimeStart;
	
	private Timestamp updateTimeEnd;

	public String getInvType() {
		return invType;
	}

	public void setInvType(String invType) {
		this.invTypes = new ArrayList<String>();
		this.invType = invType;
		this.invTypes.add( this.invType );
	}

	public List<String> getInvTypes() {
		return invTypes;
	}

	public void setInvTypes(List<String> invTypes) {
		this.invTypes = invTypes;
	}

	public String getPkInvbasdoc() {
		return pkInvbasdoc;
	}

	public void setPkInvbasdoc(String pkInvbasdoc) {
		this.pkInvbasdocs = new ArrayList<String>();
		this.pkInvbasdoc = pkInvbasdoc;
		this.pkInvbasdocs.add( this.pkInvbasdoc );
	}

	public List<String> getPkInvbasdocs() {
		return pkInvbasdocs;
	}

	public void setPkInvbasdocs(List<String> pkInvbasdocs) {
		this.pkInvbasdocs = pkInvbasdocs;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public Timestamp getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Timestamp updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Timestamp getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Timestamp updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}
}
