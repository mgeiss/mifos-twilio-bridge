package org.mifos.module.sms.listener;

import java.util.ArrayList;

import org.mifos.module.sms.domain.BulkSmsListenerDomain;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BulkSmsListener {

    final int clientNameindex = 0;
    final int clientBranchindex = 1;
    final int loanDueDateindex = 2;
    final int clientMobileNumberIndex = 3;
    final int loanOverdueDueAmountIndex = 2;
    final int loanOverdueMonthIndex = 7;
    final int guarantorsNameindex = 1;
    final int guarantorsBranchindex = 2;
    final int guarantorsMobileNumberIndex = 7;
    final int loanIdIndex = 4;
    final int clientIdIndex = 5;
    final int productShortNameIndex = 6;
    final int guarantorscomittedShareIndex = 8;
    final int savingIdIndex = 4;
    ArrayList<String> clientNameList = new ArrayList<String>();
    ArrayList<String> clientBranchList = new ArrayList<String>();
    ArrayList<String> LoanDueDateList = new ArrayList<String>();
    ArrayList<String> clientMobileNumberList = new ArrayList<String>();
    ArrayList<String> loanOverdueDueAmountList = new ArrayList<String>();
    ArrayList<String> loanOverdueMonthList = new ArrayList<String>();
    ArrayList<String> guarantorsNameList = new ArrayList<String>();
    ArrayList<String> guarantorsBranchList = new ArrayList<String>();
    ArrayList<String> guarantorsMobileNumberList = new ArrayList<String>();
    ArrayList<String> loanIdList = new ArrayList<String>();
    ArrayList<String> clientIdList = new ArrayList<String>();
    ArrayList<String> productShortNameList = new ArrayList<String>();
    ArrayList<String> guarantorscomittedShareList = new ArrayList<String>();
    ArrayList<String> savingIdList = new ArrayList<String>();

    public BulkSmsListenerDomain ReportDataProcessor(String reportName, ArrayList<JsonObject> reportGivenListData) {
        BulkSmsListenerDomain bulkSmsListenerDomain = null;
        if (reportName.equalsIgnoreCase("Loan Repayment Reminders")) {
            for (final JsonObject ObjectDetails : reportGivenListData) {
                JsonObject objectFromList = ObjectDetails.getAsJsonObject();
                JsonArray arrayFromObject = objectFromList.getAsJsonArray("row");
                String name = arrayFromObject.get(clientNameindex).toString().replaceAll("^\"|\"$", "");
                clientNameList.add(name);
                String branch = arrayFromObject.get(clientBranchindex).toString().replaceAll("^\"|\"$", "");
                clientBranchList.add(branch);
                String duedate = arrayFromObject.get(loanDueDateindex).toString().replaceAll("^\"|\"$", "");
                LoanDueDateList.add(duedate);
                String MobileNo = arrayFromObject.get(clientMobileNumberIndex).toString().replaceAll("^\"|\"$", "");
                clientMobileNumberList.add(MobileNo);
                String loanId = arrayFromObject.get(loanIdIndex).toString().replaceAll("^\"|\"$", "");
                loanIdList.add(loanId);
                String clientId = arrayFromObject.get(clientIdIndex).toString().replaceAll("^\"|\"$", "");
                clientIdList.add(clientId);
                String productName = arrayFromObject.get(productShortNameIndex).toString().replaceAll("^\"|\"$", "");
                productShortNameList.add(productName);
            }
            bulkSmsListenerDomain = new BulkSmsListenerDomain(clientNameList, clientBranchList, LoanDueDateList, clientMobileNumberList,
                    loanIdList, clientIdList, productShortNameList);

        }

        else if (reportName.equalsIgnoreCase("Loan First Overdue Repayment Reminder")
                || reportName.equalsIgnoreCase("Loan Second Overdue Repayment Reminder")
                || reportName.equalsIgnoreCase("DefaultWarning - Clients")) {
            for (final JsonObject ObjectDetails : reportGivenListData) {
                JsonObject objectFromList = ObjectDetails.getAsJsonObject();
                JsonArray arrayFromObject = objectFromList.getAsJsonArray("row");
                String name = arrayFromObject.get(clientNameindex).toString().replaceAll("^\"|\"$", "");
                clientNameList.add(name);
                String branch = arrayFromObject.get(clientBranchindex).toString().replaceAll("^\"|\"$", "");
                clientBranchList.add(branch);
                String amount = arrayFromObject.get(loanOverdueDueAmountIndex).toString().replaceAll("^\"|\"$", "");
                loanOverdueDueAmountList.add(amount);
                String MobileNo = arrayFromObject.get(clientMobileNumberIndex).toString().replaceAll("^\"|\"$", "");
                clientMobileNumberList.add(MobileNo);
                String loanId = arrayFromObject.get(loanIdIndex).toString().replaceAll("^\"|\"$", "");
                loanIdList.add(loanId);
                String clientId = arrayFromObject.get(clientIdIndex).toString().replaceAll("^\"|\"$", "");
                clientIdList.add(clientId);
                String productName = arrayFromObject.get(productShortNameIndex).toString().replaceAll("^\"|\"$", "");
                productShortNameList.add(productName);
                String Month = arrayFromObject.get(loanOverdueMonthIndex).toString().replaceAll("^\"|\"$", "");
                loanOverdueMonthList.add(Month);
            }
            bulkSmsListenerDomain = new BulkSmsListenerDomain(clientNameList, clientBranchList, loanOverdueDueAmountList,
                    clientMobileNumberList, loanIdList, clientIdList, productShortNameList, loanOverdueMonthList);
        } else if (reportName.equalsIgnoreCase("Loan Third Overdue Repayment Reminder")
                || reportName.equalsIgnoreCase("Loan Fourth Overdue Repayment Reminder")) {
            for (final JsonObject ObjectDetails : reportGivenListData) {
                JsonObject objectFromList = ObjectDetails.getAsJsonObject();
                JsonArray arrayFromObject = objectFromList.getAsJsonArray("row");
                String name = arrayFromObject.get(clientNameindex).toString().replaceAll("^\"|\"$", "");
                clientNameList.add(name);
                String gurantorName = arrayFromObject.get(guarantorsNameindex).toString().replaceAll("^\"|\"$", "");
                guarantorsNameList.add(gurantorName);
                String branch = arrayFromObject.get(guarantorsBranchindex).toString().replaceAll("^\"|\"$", "");
                guarantorsBranchList.add(branch);
                String clientMobileNo = arrayFromObject.get(clientMobileNumberIndex).toString().replaceAll("^\"|\"$", "");
                clientMobileNumberList.add(clientMobileNo);
                String loanId = arrayFromObject.get(loanIdIndex).toString().replaceAll("^\"|\"$", "");
                loanIdList.add(loanId);
                String clientId = arrayFromObject.get(clientIdIndex).toString().replaceAll("^\"|\"$", "");
                clientIdList.add(clientId);
                String productName = arrayFromObject.get(productShortNameIndex).toString().replaceAll("^\"|\"$", "");
                productShortNameList.add(productName);
                String mobileNo = arrayFromObject.get(guarantorsMobileNumberIndex).toString().replaceAll("^\"|\"$", "");
                guarantorsMobileNumberList.add(mobileNo);
            }
            bulkSmsListenerDomain = new BulkSmsListenerDomain(clientNameList, guarantorsNameList, guarantorsBranchList,
                    clientMobileNumberList, loanIdList, clientIdList, productShortNameList, guarantorsMobileNumberList, null);
        } else if (reportName.equalsIgnoreCase("DefaultWarning -  guarantors")) {
            for (final JsonObject ObjectDetails : reportGivenListData) {
                JsonObject objectFromList = ObjectDetails.getAsJsonObject();
                JsonArray arrayFromObject = objectFromList.getAsJsonArray("row");
                String name = arrayFromObject.get(clientNameindex).toString().replaceAll("^\"|\"$", "");
                clientNameList.add(name);
                String gurantorName = arrayFromObject.get(guarantorsNameindex).toString().replaceAll("^\"|\"$", "");
                guarantorsNameList.add(gurantorName);
                String branch = arrayFromObject.get(guarantorsBranchindex).toString().replaceAll("^\"|\"$", "");
                guarantorsBranchList.add(branch);
                String clientMobileNo = arrayFromObject.get(clientMobileNumberIndex).toString().replaceAll("^\"|\"$", "");
                clientMobileNumberList.add(clientMobileNo);
                String loanId = arrayFromObject.get(loanIdIndex).toString().replaceAll("^\"|\"$", "");
                loanIdList.add(loanId);
                String clientId = arrayFromObject.get(clientIdIndex).toString().replaceAll("^\"|\"$", "");
                clientIdList.add(clientId);
                String productName = arrayFromObject.get(productShortNameIndex).toString().replaceAll("^\"|\"$", "");
                productShortNameList.add(productName);
                String mobileNo = arrayFromObject.get(guarantorsMobileNumberIndex).toString().replaceAll("^\"|\"$", "");
                guarantorsMobileNumberList.add(mobileNo);
                String comittedShares = arrayFromObject.get(guarantorscomittedShareIndex).toString().replaceAll("^\"|\"$", "");
                guarantorscomittedShareList.add(comittedShares);
            }
            bulkSmsListenerDomain = new BulkSmsListenerDomain(clientNameList, guarantorsNameList, guarantorsBranchList,
                    clientMobileNumberList, loanIdList, clientIdList, productShortNameList, guarantorsMobileNumberList,
                    guarantorscomittedShareList, null);
        }

        if (reportName.equalsIgnoreCase("DormancyWarning - Clients")) {
            for (final JsonObject ObjectDetails : reportGivenListData) {
                JsonObject objectFromList = ObjectDetails.getAsJsonObject();
                JsonArray arrayFromObject = objectFromList.getAsJsonArray("row");
                String name = arrayFromObject.get(clientNameindex).toString().replaceAll("^\"|\"$", "");
                clientNameList.add(name);
                String branch = arrayFromObject.get(clientBranchindex).toString().replaceAll("^\"|\"$", "");
                clientBranchList.add(branch);
                String MobileNo = arrayFromObject.get(clientMobileNumberIndex).toString().replaceAll("^\"|\"$", "");
                clientMobileNumberList.add(MobileNo);
                String SavingId = arrayFromObject.get(loanIdIndex).toString().replaceAll("^\"|\"$", "");
                savingIdList.add(SavingId);
                String clientId = arrayFromObject.get(clientIdIndex).toString().replaceAll("^\"|\"$", "");
                clientIdList.add(clientId);
                String productName = arrayFromObject.get(productShortNameIndex).toString().replaceAll("^\"|\"$", "");
                productShortNameList.add(productName);
            }
            bulkSmsListenerDomain = new BulkSmsListenerDomain(clientNameList, clientBranchList, clientMobileNumberList, savingIdList,
                    clientIdList, productShortNameList, null, null, null, null, null);

        }

        return bulkSmsListenerDomain;
    }

}