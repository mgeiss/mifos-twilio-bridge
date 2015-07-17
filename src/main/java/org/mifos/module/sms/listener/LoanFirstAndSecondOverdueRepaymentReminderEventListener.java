package org.mifos.module.sms.listener;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.mifos.module.sms.domain.BulkSmsListenerDomain;
import org.mifos.module.sms.domain.EventSource;
import org.mifos.module.sms.domain.EventSourceDetail;
import org.mifos.module.sms.domain.LoanFirstAndSecondOverdueRepaymentReminder;
import org.mifos.module.sms.domain.LoanFirstAndSecondOverdueRepaymentReminderResponse;
import org.mifos.module.sms.domain.SMSBridgeConfig;
import org.mifos.module.sms.event.LoanFirstAndSecondOverdueRepaymentReminderEvent;
import org.mifos.module.sms.exception.SMSGatewayException;
import org.mifos.module.sms.parser.JsonParser;
import org.mifos.module.sms.provider.RestAdapterProvider;
import org.mifos.module.sms.provider.SMSGateway;
import org.mifos.module.sms.provider.SMSGatewayProvider;
import org.mifos.module.sms.repository.EventSourceDetailRepository;
import org.mifos.module.sms.repository.EventSourceRepository;
import org.mifos.module.sms.repository.SMSBridgeConfigRepository;
import org.mifos.module.sms.service.MifosLoanFirstAndSecondOverdueRepaymentReminderService;
import org.mifos.module.sms.util.AuthorizationTokenBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

import com.google.gson.JsonObject;

@Component
public class LoanFirstAndSecondOverdueRepaymentReminderEventListener implements
        ApplicationListener<LoanFirstAndSecondOverdueRepaymentReminderEvent> {

    @Value("${message.template.loanFirstAndSecondOverdueRepaymentReminder}")
    private String messageTemplate;
/*
    private static final Logger logger = LoggerFactory.getLogger(LoanFirstAndSecondOverdueRepaymentReminderEventListener.class);
    private static final boolean debug = true;
    private final SMSBridgeConfigRepository smsBridgeConfigRepository;
    private final EventSourceRepository eventSourceRepository;
    private final RestAdapterProvider restAdapterProvider;
    private final SMSGatewayProvider smsGatewayProvider;
    private final EventSourceDetailRepository eventSourceDetailRepository;
    private final JsonParser jsonParser;
    private int totalMessageSent = 0;
    private int totalMessageUnsent = 0;

    @Autowired
    public LoanFirstAndSecondOverdueRepaymentReminderEventListener(final SMSBridgeConfigRepository smsBridgeConfigRepository,
            final EventSourceRepository eventSourceRepository, final RestAdapterProvider restAdapterProvider,
            final SMSGatewayProvider smsGatewayProvider, final JsonParser jsonParser,
            final EventSourceDetailRepository eventSourceDetailRepository) {
        super();
        this.smsBridgeConfigRepository = smsBridgeConfigRepository;
        this.eventSourceRepository = eventSourceRepository;
        this.restAdapterProvider = restAdapterProvider;
        this.smsGatewayProvider = smsGatewayProvider;
        this.jsonParser = jsonParser;
        this.eventSourceDetailRepository = eventSourceDetailRepository;
    }

    @Transactional
    @Override
    public void onApplicationEvent(LoanFirstAndSecondOverdueRepaymentReminderEvent loanFirstAndSecondOverdueRepaymentReminderEvent) {

        logger.info(" Send Sms to Loan  Overdue Repayment reminder  to  client  is in ` process ...");

        final EventSource eventSource = this.eventSourceRepository.findOne(loanFirstAndSecondOverdueRepaymentReminderEvent.getEventId());

        final SMSBridgeConfig smsBridgeConfig = this.smsBridgeConfigRepository.findByTenantId(eventSource.getTenantId());
        if (smsBridgeConfig == null) {
            logger.error("Unknown tenant " + eventSource.getTenantId() + "!");
            return;
        }
/
        final LoanFirstAndSecondOverdueRepaymentReminderResponse loanFirstAndSecondOverdueRepaymentReminderResponse = this.jsonParser
                .parse(eventSource.getPayload(), LoanFirstAndSecondOverdueRepaymentReminderResponse.class);
        final String reportName = loanFirstAndSecondOverdueRepaymentReminderResponse.getReportName();
        final String date = loanFirstAndSecondOverdueRepaymentReminderResponse.getDate();
        final RestAdapter restAdapter = this.restAdapterProvider.get(smsBridgeConfig);
        BulkSmsListener bulkSmsListener = new BulkSmsListener();
        final MifosLoanFirstAndSecondOverdueRepaymentReminderService LoanFirstAndSecondOverdueRepaymentReminderService = restAdapter
                .create(MifosLoanFirstAndSecondOverdueRepaymentReminderService.class);
        final LoanFirstAndSecondOverdueRepaymentReminder loanFirstAndSecondOverdueRepaymentReminder = LoanFirstAndSecondOverdueRepaymentReminderService
                .findLoanReminder(AuthorizationTokenBuilder.token(smsBridgeConfig.getMifosToken()).build(), smsBridgeConfig.getTenantId(),
                        reportName, date);
        final VelocityContext velocityContext = new VelocityContext();
        final ArrayList<JsonObject> loanFirstAndSecondOverdueRepaymentReminder_clientdata = loanFirstAndSecondOverdueRepaymentReminder
                .getData();
        BulkSmsListenerDomain bulkSmsListenerDomain = bulkSmsListener.ReportDataProcessor(reportName,
                loanFirstAndSecondOverdueRepaymentReminder_clientdata);
        ArrayList<String> clientNameList = bulkSmsListenerDomain.getClientNameList();
        ArrayList<String> clientBranchList = bulkSmsListenerDomain.getClientBranchList();
        ArrayList<String> loanOverdueDueAmountList = bulkSmsListenerDomain.getLoanOverdueDueAmountList();
        ArrayList<String> loanOverdueMonthList = bulkSmsListenerDomain.getLoanOverdueMonthList();
        ArrayList<String> clientMobileNumberList = bulkSmsListenerDomain.getClientMobileNumberList();
        ArrayList<String> entityIdList = bulkSmsListenerDomain.getLoanIdList();
        ArrayList<String> clientIdList = bulkSmsListenerDomain.getClientIdList();
        ArrayList<String> productShortNameList = bulkSmsListenerDomain.getProductShortNameList();
        for (int i = 0; i < clientMobileNumberList.size(); i++) {
            EventSourceDetail eventSourceDetail = new EventSourceDetail();
            try {
//                ArrayList<EventSourceDetail> detail = eventSourceDetailRepository.findByEntityIdandMobileNumberandProcessed(
//                        entityIdList.get(i), clientMobileNumberList.get(i), Boolean.TRUE);

                velocityContext.put("name", clientNameList.get(i));
                velocityContext.put("branch", clientBranchList.get(i));
                velocityContext.put("overdueamount", loanOverdueDueAmountList.get(i));
                velocityContext.put("month", loanOverdueMonthList.get(i));
                String mobileNo = clientMobileNumberList.get(i);
                eventSourceDetail.setEntity_Mobile_No(mobileNo);
                eventSourceDetail.setEntityName(clientNameList.get(i));
                eventSourceDetail.setEventId(eventSource.getId());
                eventSourceDetail.setTenantId(eventSource.getTenantId());
                eventSourceDetail.setAction(eventSource.getAction());
                eventSourceDetail.setEntity(eventSource.getEntity());
                eventSourceDetail.setPayload(eventSource.getPayload());
                eventSourceDetail.setEntityId(entityIdList.get(i));
                eventSourceDetail.setProcessed(Boolean.FALSE);
                eventSourceDetail.setEntitydescription("clientId:" + clientIdList.get(i) + " " + "clientName:" + clientNameList.get(i)
                        + "productshort_Name:" + productShortNameList.get(i));
                final Date now = new Date();
                eventSourceDetail.setCreatedOn(now);
                eventSourceDetail.setLastModifiedOn(now);
                if (mobileNo.equals("null") || mobileNo.equalsIgnoreCase("NA") || mobileNo.equalsIgnoreCase("  ") || mobileNo.length() <= 0) {
                    eventSourceDetail.setErrorMessage("Mobile number is not Valid");
                    this.eventSourceDetailRepository.save(eventSourceDetail);
                    if (reportName.equalsIgnoreCase("Loan First Overdue Repayment Reminder")) {
                        logger.info("Loan First Overdue Repayment Reminder Sms  to\n" + "clientName: " + clientNameList.get(i) + " \n"
                                + "MobileNo: " + mobileNo + "\n" + "loanId: " + entityIdList.get(i) + "\n" + "productName: "
                                + productShortNameList.get(i) + "\n event processed!");
                        totalMessageUnsent = totalMessageUnsent + 1;
                    } else if (reportName.equalsIgnoreCase("Loan Second Overdue Repayment Reminder")) {
                        logger.info("Loan Second Overdue Repayment Reminder Sms  to\n" + "clientName: " + clientNameList.get(i) + " \n"
                                + "MobileNo: " + mobileNo + "\n" + "loanId: " + entityIdList.get(i) + "\n" + "productName: "
                                + productShortNameList.get(i) + "\n event processed!");
                        totalMessageUnsent = totalMessageUnsent + 1;
                    }
                } else if (mobileNo != null) {
                    long diffInDays = 0;
                    if (detail.size() != 0) {
                        int size = detail.size() - 1;
                        eventSourceDetail = detail.get(size);
                        Date lastModifiedate = eventSourceDetail.getLastModifiedOn();
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date crrentDate = df.parse(date);
                        long timeDiff = crrentDate.getTime() - lastModifiedate.getTime();
                        diffInDays = timeDiff / (24 * 60 * 60 * 1000);
                    }
                    if (detail.size() == 0 || diffInDays >= 31) {
                        final StringWriter stringWriter = new StringWriter();
                        Velocity.evaluate(velocityContext, stringWriter, "loanFirstAndSecondOverdueRepaymentReminder", this.messageTemplate);
                        final SMSGateway smsGateway = this.smsGatewayProvider.get(smsBridgeConfig.getSmsProvider());
                        smsGateway.sendMessage(smsBridgeConfig, mobileNo, stringWriter.toString());
                        eventSource.setProcessed(Boolean.TRUE);
                        eventSourceDetail.setProcessed(Boolean.TRUE);
                        this.eventSourceDetailRepository.save(eventSourceDetail);
                        if (reportName.equalsIgnoreCase("Loan First Overdue Repayment Reminder")) {
                            logger.info("Loan First Overdue Repayment Reminder Sms  to\n" + "clientName: " + clientNameList.get(i) + " \n"
                                    + "MobileNo: " + mobileNo + "\n" + "loanId: " + entityIdList.get(i) + "\n" + "productName: "
                                    + productShortNameList.get(i) + "\n event processed!");
                            totalMessageSent = totalMessageSent + 1;
                        } else if (reportName.equalsIgnoreCase("Loan Second Overdue Repayment Reminder")) {
                            logger.info("Loan Second Overdue Repayment Reminder Sms  to\n" + "clientName: " + clientNameList.get(i) + " \n"
                                    + "MobileNo: " + mobileNo + "\n" + "loanId: " + entityIdList.get(i) + "\n" + "productName: "
                                    + productShortNameList.get(i) + "\n event processed!");
                            totalMessageSent = totalMessageSent + 1;
                        }
                    }
                }

            } catch (RetrofitError rer) {
                if (rer.getResponse().getStatus() == 404) {
                    logger.info("Client not found!");
                }
                eventSource.setProcessed(Boolean.FALSE);
                eventSource.setErrorMessage(rer.getMessage());
                eventSourceDetail.setProcessed(Boolean.FALSE);
                eventSourceDetail.setErrorMessage(rer.getMessage());

            } catch (SMSGatewayException sgex) {
                eventSource.setProcessed(Boolean.FALSE);
                eventSource.setErrorMessage(sgex.getMessage());
                eventSourceDetail.setProcessed(Boolean.FALSE);
                eventSourceDetail.setErrorMessage(sgex.getMessage());

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            eventSource.setLastModifiedOn(new Date());
            eventSourceDetail.setLastModifiedOn(new Date());
            this.eventSourceRepository.save(eventSource);
        }
        logger.info(" Total Number of clients to sent loan Overdue repayment is Message is " + clientMobileNumberList.size());
        logger.info(" number of Message sent succesuffuly" + totalMessageSent);
        logger.info("number of Message be  unsucessful" + totalMessageUnsent);

    }
    */

    @Override
    public void onApplicationEvent(LoanFirstAndSecondOverdueRepaymentReminderEvent event) {
        // TODO Auto-generated method stub
        
    }
}
