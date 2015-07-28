package org.mifos.module.sms.domain;

import java.util.ArrayList;

public class BulkSmsListenerDomain {

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
    ArrayList<String> SavingIdList = new ArrayList<String>();

    public BulkSmsListenerDomain() {}

    public BulkSmsListenerDomain(ArrayList<String> clientNameList, ArrayList<String> clientBranchList, ArrayList<String> loanDueDateList,
            ArrayList<String> clientMobileNumberList, ArrayList<String> loanIdList, ArrayList<String> clientIdList,
            ArrayList<String> productShortNameList) {
        this.clientNameList = clientNameList;
        this.clientBranchList = clientBranchList;
        this.LoanDueDateList = loanDueDateList;
        this.clientMobileNumberList = clientMobileNumberList;
        this.loanIdList = loanIdList;
        this.clientIdList = clientIdList;
        this.productShortNameList = productShortNameList;
    }

    public BulkSmsListenerDomain(ArrayList<String> clientNameList, ArrayList<String> clientBranchList,
            ArrayList<String> clientMobileNumberList, ArrayList<String> savingIdList, ArrayList<String> clientIdList,
            ArrayList<String> productShortNameList, ArrayList<String> loanDueDateList, ArrayList<String> loanOverdueDueAmountList,
            ArrayList<String> loanOverdueMonthList, ArrayList<String> guarantorsNameList, ArrayList<String> guarantorsBranchList) {
        super();
        this.clientNameList = clientNameList;
        this.clientBranchList = clientBranchList;
        this.clientMobileNumberList = clientMobileNumberList;
        this.SavingIdList = savingIdList;
        this.clientIdList = clientIdList;
        this.productShortNameList = productShortNameList;
        this.LoanDueDateList = loanDueDateList;
        this.loanOverdueDueAmountList = loanOverdueDueAmountList;
        this.loanOverdueMonthList = loanOverdueMonthList;
        this.guarantorsNameList = guarantorsNameList;
        this.guarantorsBranchList = guarantorsBranchList;
    }

    public BulkSmsListenerDomain(ArrayList<String> clientNameList, ArrayList<String> clientBranchList,
            ArrayList<String> loanOverdueDueAmountList, ArrayList<String> clientMobileNumberList, ArrayList<String> loanIdList,
            ArrayList<String> clientIdList, ArrayList<String> productShortNameList, ArrayList<String> loanOverdueMonthList) {
        super();
        this.clientNameList = clientNameList;
        this.clientBranchList = clientBranchList;
        this.loanOverdueDueAmountList = loanOverdueDueAmountList;
        this.clientMobileNumberList = clientMobileNumberList;
        this.loanIdList = loanIdList;
        this.clientIdList = clientIdList;
        this.productShortNameList = productShortNameList;
        this.loanOverdueMonthList = loanOverdueMonthList;
    }

    public ArrayList<String> getClientNameList() {
        return this.clientNameList;
    }

    public BulkSmsListenerDomain(ArrayList<String> clientNameList, ArrayList<String> guarantorsNameList,
            ArrayList<String> guarantorsBranchList, ArrayList<String> clientMobileNumberList, ArrayList<String> clientIdList,
            ArrayList<String> loanIdList, ArrayList<String> productShortNameList, ArrayList<String> guarantorsMobileNumberList,
            ArrayList<String> loanDueDateList) {
        super();
        this.clientNameList = clientNameList;
        this.guarantorsNameList = guarantorsNameList;
        this.guarantorsBranchList = guarantorsBranchList;
        this.clientMobileNumberList = clientMobileNumberList;
        this.clientIdList = clientIdList;
        this.loanIdList = loanIdList;
        this.productShortNameList = productShortNameList;
        this.guarantorsMobileNumberList = guarantorsMobileNumberList;
        this.LoanDueDateList = loanDueDateList;
    }

    public BulkSmsListenerDomain(ArrayList<String> clientNameList, ArrayList<String> guarantorsNameList,
            ArrayList<String> guarantorsBranchList, ArrayList<String> clientMobileNumberList, ArrayList<String> loanIdList,
            ArrayList<String> clientIdList, ArrayList<String> productShortNameList, ArrayList<String> guarantorsMobileNumberList,
            ArrayList<String> guarantorscomittedShareList, ArrayList<String> loanDueDateList) {
        super();
        this.clientNameList = clientNameList;
        this.guarantorsNameList = guarantorsNameList;
        this.guarantorsBranchList = guarantorsBranchList;
        this.clientMobileNumberList = clientMobileNumberList;
        this.loanIdList = loanIdList;
        this.clientIdList = clientIdList;
        this.productShortNameList = productShortNameList;
        this.guarantorsMobileNumberList = guarantorsMobileNumberList;
        this.guarantorscomittedShareList = guarantorscomittedShareList;
        this.LoanDueDateList = loanDueDateList;
    }

    public ArrayList<String> getSavingIdList() {
        return this.SavingIdList;
    }

    public void setSavingIdList(ArrayList<String> savingIdList) {
        this.SavingIdList = savingIdList;
    }

    public ArrayList<String> getGuarantorscomittedShareList() {
        return this.guarantorscomittedShareList;
    }

    public void setGuarantorscomittedShareList(ArrayList<String> guarantorscomittedShareList) {
        this.guarantorscomittedShareList = guarantorscomittedShareList;
    }

    public void setClientNameList(ArrayList<String> clientNameList) {
        this.clientNameList = clientNameList;
    }

    public ArrayList<String> getClientBranchList() {
        return this.clientBranchList;
    }

    public void setClientBranchList(ArrayList<String> clientBranchList) {
        this.clientBranchList = clientBranchList;
    }

    public ArrayList<String> getLoanDueDateList() {
        return this.LoanDueDateList;
    }

    public void setLoanDueDateList(ArrayList<String> loanDueDateList) {
        this.LoanDueDateList = loanDueDateList;
    }

    public ArrayList<String> getClientMobileNumberList() {
        return this.clientMobileNumberList;
    }

    public void setClientMobileNumberList(ArrayList<String> clientMobileNumberList) {
        this.clientMobileNumberList = clientMobileNumberList;
    }

    public ArrayList<String> getLoanOverdueDueAmountList() {
        return this.loanOverdueDueAmountList;
    }

    public void setLoanOverdueDueAmountList(ArrayList<String> loanOverdueDueAmountList) {
        this.loanOverdueDueAmountList = loanOverdueDueAmountList;
    }

    public ArrayList<String> getLoanOverdueMonthList() {
        return this.loanOverdueMonthList;
    }

    public void setLoanOverdueMonthList(ArrayList<String> loanOverdueMonthList) {
        this.loanOverdueMonthList = loanOverdueMonthList;
    }

    public ArrayList<String> getGuarantorsNameList() {
        return this.guarantorsNameList;
    }

    public void setGuarantorsNameList(ArrayList<String> guarantorsNameList) {
        this.guarantorsNameList = guarantorsNameList;
    }

    public ArrayList<String> getGuarantorsBranchList() {
        return this.guarantorsBranchList;
    }

    public void setGuarantorsBranchList(ArrayList<String> guarantorsBranchList) {
        this.guarantorsBranchList = guarantorsBranchList;
    }

    public ArrayList<String> getGuarantorsMobileNumberList() {
        return this.guarantorsMobileNumberList;
    }

    public void setGuarantorsMobileNumberList(ArrayList<String> guarantorsMobileNumberList) {
        this.guarantorsMobileNumberList = guarantorsMobileNumberList;
    }

    public ArrayList<String> getLoanIdList() {
        return this.loanIdList;
    }

    public void setLoanIdList(ArrayList<String> loanIdList) {
        this.loanIdList = loanIdList;
    }

    public ArrayList<String> getClientIdList() {
        return this.clientIdList;
    }

    public void setClientIdList(ArrayList<String> clientIdList) {
        this.clientIdList = clientIdList;
    }

    public ArrayList<String> getProductShortNameList() {
        return this.productShortNameList;
    }

    public void setProductShortNameList(ArrayList<String> productShortNameList) {
        this.productShortNameList = productShortNameList;
    }

}
