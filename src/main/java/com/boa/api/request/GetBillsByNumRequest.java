package com.boa.api.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * GetBillsByNumRequest
 */
@XmlRootElement
public class GetBillsByNumRequest {

    private String billerCode, billNum, cashingRef, requierNumber;
    private String langue, channelType;


    public String getBillerCode() {
        return this.billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public String getBillNum() {
        return this.billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getCashingRef() {
        return this.cashingRef;
    }

    public void setCashingRef(String cashingRef) {
        this.cashingRef = cashingRef;
    }

    public String getRequierNumber() {
        return this.requierNumber;
    }

    public void setRequierNumber(String requierNumber) {
        this.requierNumber = requierNumber;
    }

    public String getLangue() {
        return this.langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    @Override
    public String toString() {
        return "{" +
            " billerCode='" + billerCode + "'" +
            ", billNum='" + billNum + "'" +
            ", cashingRef='" + cashingRef + "'" +
            ", requierNumber='" + requierNumber + "'" +
            ", channel='" + channelType + "'" +
            "}";
    }
}