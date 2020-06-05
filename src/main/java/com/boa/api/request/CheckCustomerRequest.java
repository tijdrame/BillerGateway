package com.boa.api.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * CheckCustomerRequest
 */
@XmlRootElement
public class CheckCustomerRequest {

    private String billerCode, billRef, cashingRef;
    private String langue;

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

    public String getLangue() {
        return this.langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    @Override
    public String toString() {
        return "{" +
            " billerCode='" + billerCode + "'" +
            ", billRef='" + billRef + "'" +
            ", cashingRef='" + cashingRef + "'" +
            ", langue='" + langue + "'" +
            "}";
    }
}