package com.boa.api.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * GetBillFeesRequest
 */
@XmlRootElement
public class GetBillFeesRequest {

    private String billerCode, typeCanal, montant;

    public String getBillerCode() {
        return this.billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public String getTypeCanal() {
        return this.typeCanal;
    }

    public void setTypeCanal(String typeCanal) {
        this.typeCanal = typeCanal;
    }

    public String getMontant() {
        return this.montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "{" +
            " billerCode='" + billerCode + "'" +
            ", typeCanal='" + typeCanal + "'" +
            ", montant='" + montant + "'" +
            "}";
    }

}