package com.boa.api.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * HistoPaiementClientRequest
 */
@XmlRootElement
public class HistoPaiementClientRequest {
    private String codeFacturier, accountCustomer;
    
    public HistoPaiementClientRequest() {
    }

    public HistoPaiementClientRequest(String codeFacturier, String accountCustomer) {
        this.codeFacturier = codeFacturier;
        this.accountCustomer = accountCustomer;
    }

    public String getCodeFacturier() {
        return this.codeFacturier;
    }

    public void setCodeFacturier(String codeFacturier) {
        this.codeFacturier = codeFacturier;
    }

    public String getAccountCustomer() {
        return this.accountCustomer;
    }

    public void setAccountCustomer(String accountCustomer) {
        this.accountCustomer = accountCustomer;
    }

    public HistoPaiementClientRequest codeFacturier(String codeFacturier) {
        this.codeFacturier = codeFacturier;
        return this;
    }

    public HistoPaiementClientRequest accountCustomer(String accountCustomer) {
        this.accountCustomer = accountCustomer;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " codeFacturier='" + getCodeFacturier() + "'" +
            ", accountCustomer='" + getAccountCustomer() + "'" +
            "}";
    }
}