package com.boa.api.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * GetBillsByRefRequest
 */
@XmlRootElement
public class GetBillsByRefRequest {
    private String billerCode, billRef, cashingRef, requierNumber;
    private String langue, channelType;

    public String getBillerCode() {
        return this.billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public String getBillRef() {
        return this.billRef;
    }

    public void setBillRef(String billRef) {
        this.billRef = billRef;
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
            ", billRef='" + billRef + "'" +
            ", cashingRef='" + cashingRef + "'" +
            ", requierNumber='" + requierNumber + "'" +
            ", channelType='" + channelType + "'" +
            "}";
    }

}