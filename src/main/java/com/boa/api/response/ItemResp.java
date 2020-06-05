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

    public Double getFeeAmount() {
        return this.feeAmount;
    }

    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getSessionNum() {
        return this.sessionNum;
    }

    public void setSessionNum(String sessionNum) {
        this.sessionNum = sessionNum;
    }

}