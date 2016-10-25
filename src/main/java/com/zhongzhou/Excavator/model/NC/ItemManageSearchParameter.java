package com.zhongzhou.Excavator.model.NC;

import java.util.ArrayList;
import java.util.List;

public class ItemManageSearchParameter {

	private String invcode;
	
	private List<String> invcodes;

	public String getInvcode() {
		return invcode;
	}

	public void setInvcode(String invcode) {
		this.invcode = invcode;
		this.invcodes = new ArrayList<String>();
		invcodes.add( invcode );
	}

	public List<String> getInvcodes() {
		return invcodes;
	}

	public void setInvcodes(List<String> invcodes) {
		this.invcodes = invcodes;
	}
	
	
}
