package org.mifos.module.sms.service;

import java.util.Date;

import org.mifos.module.sms.domain.LoanRepaymentSmsReminder;


import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

public interface MifosLoanRepaymentsmsReminderService {		    

	    @GET("/runreports/{report_name}")
	    public LoanRepaymentSmsReminder findLoanReminder(@Header("Authorization") String authorization,
	                             @Header("X-Mifos-Platform-TenantId") String tenantIdentifier,
	                             @Path("report_name") final String report_name,@Query("R_startDate") final String date);
	                             
	

}
