package org.mifos.module.sms.domain;

import java.util.ArrayList;
import com.google.gson.JsonObject;

public class LoanFirstAndSecondOverdueRepaymentReminder {
	    
	private  ArrayList<JsonObject> data;

	public LoanFirstAndSecondOverdueRepaymentReminder() {
		super();
	}

	public ArrayList<JsonObject> getData() {
		return data;
	}

	public void setData(ArrayList<JsonObject> data) {
		this.data = data;
	}
	

}
