package com.boa.api.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * PaymentReceiptRequest
 */
@XmlRootElement
public class PaymentReceiptRequest {

    private String billerCode, payBillRef;
    private String langue;


    public String getBillerCode() {
        return this.billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public String getPayBillRef() {
        return this.payBillRef;
    }

    public void setPayBillRef(String payBillRef) {
        this.payBillRef = payBillRef;
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
            ", payBillRef='" + payBillRef + "'" +
            "}";
    }

}