package org.mifos.module.sms.domain;

import java.util.ArrayList;

import com.google.gson.JsonObject;

public class LoanThirdAndFourthOverdueRepaymentReminder {
	private  ArrayList<JsonObject> data;

	public LoanThirdAndFourthOverdueRepaymentReminder() {
		super();
	}

	public ArrayList<JsonObject> getData() {
		return data;
	}

	public void setData(ArrayList<JsonObject> data) {
		this.data = data;
	}
	
	

}
