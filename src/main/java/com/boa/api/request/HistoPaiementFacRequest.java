package com.boa.api.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HistoPaiementFacRequest {
    private String codeFacturier;

    public String getCodeFacturier() {
        return this.codeFacturier;
    }

    public void setCodeFacturier(String codeFacturier) {
        this.codeFacturier = codeFacturier;
    }

    @Override
    public String toString() {
        return "{" +
            " codeFacturier='" + codeFacturier + "'" +
            "}";
    }

}