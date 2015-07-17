package org.mifos.module.sms.service;

import org.mifos.module.sms.domain.BulkSms;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

public interface MifosBulkSmsService {

    @GET("/runreports/{report_name}")
    public BulkSms findLoanReminder(@Header("Authorization") String authorization,
            @Header("X-Mifos-Platform-TenantId") String tenantIdentifier, @Path("report_name") final String report_name,
            @Query("R_startDate") final String date);

}
