package com.boa.api.response;

/**
 * CheckFactoryResponse
 */
public class ItemResp  {

    private String billNum;    
    private String custumerRef;    
    private String billDate;    
    private Double billAmount;    
    private String requierNumber;
    private String customerName, sessionNum;
    private Double feeAmount;
    private String numTransaction, numReference;


    public ItemResp() {
    }

    public ItemResp(String billNum, String custumerRef, String billDate, Double billAmount, String requierNumber, String customerName, String sessionNum, Double feeAmount, String numTransaction, String numReference) {
        this.billNum = billNum;
        this.custumerRef = custumerRef;
        this.billDate = billDate;
        this.billAmount = billAmount;
        this.requierNumber = requierNumber;
        this.customerName = customerName;
        this.sessionNum = sessionNum;
        this.feeAmount = feeAmount;
        this.numTransaction = numTransaction;
        this.numReference = numReference;
    }

    public String getBillNum() {
        return this.billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getCustumerRef() {
        return this.custumerRef;
    }

    public void setCustumerRef(String custumerRef) {
        this.custumerRef = custumerRef;
    }

    public String getBillDate() {
        return this.billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public Double getBillAmount() {
        return this.billAmount;
    }

    public void setBillAmount(Double billAmount) {
        this.billAmount = billAmount;
    }

    public String getRequierNumber() {
        return this.requierNumber;
    }

    public void setRequierNumber(String requierNumber) {
        this.requierNumber = requierNumber;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSessionNum() {
        return this.sessionNum;
    }

    public void setSessionNum(String sessionNum) {
        this.sessionNum = sessionNum;
    }

    public Double getFeeAmount() {
        return this.feeAmount;
    }

    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getNumTransaction() {
        return this.numTransaction;
    }

    public void setNumTransaction(String numTransaction) {
        this.numTransaction = numTransaction;
    }

    public String getNumReference() {
        return this.numReference;
    }

    public void setNumReference(String numReference) {
        this.numReference = numReference;
    }

    public ItemResp billNum(String billNum) {
        setBillNum(billNum);
        return this;
    }

    public ItemResp custumerRef(String custumerRef) {
        setCustumerRef(custumerRef);
        return this;
    }

    public ItemResp billDate(String billDate) {
        setBillDate(billDate);
        return this;
    }

    public ItemResp billAmount(Double billAmount) {
        setBillAmount(billAmount);
        return this;
    }

    public ItemResp requierNumber(String requierNumber) {
        setRequierNumber(requierNumber);
        return this;
    }

    public ItemResp customerName(String customerName) {
        setCustomerName(customerName);
        return this;
    }

    public ItemResp sessionNum(String sessionNum) {
        setSessionNum(sessionNum);
        return this;
    }

    public ItemResp feeAmount(Double feeAmount) {
        setFeeAmount(feeAmount);
        return this;
    }

    public ItemResp numTransaction(String numTransaction) {
        setNumTransaction(numTransaction);
        return this;
    }

    public ItemResp numReference(String numReference) {
        setNumReference(numReference);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " billNum='" + getBillNum() + "'" +
            ", custumerRef='" + getCustumerRef() + "'" +
            ", billDate='" + getBillDate() + "'" +
            ", billAmount='" + getBillAmount() + "'" +
            ", requierNumber='" + getRequierNumber() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", sessionNum='" + getSessionNum() + "'" +
            ", feeAmount='" + getFeeAmount() + "'" +
            ", numTransaction='" + getNumTransaction() + "'" +
            ", numReference='" + getNumReference() + "'" +
            "}";
    }

}