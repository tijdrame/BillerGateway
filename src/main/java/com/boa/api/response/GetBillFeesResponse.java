package com.boa.api.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * GetBillFeesResponse
 */
@XmlRootElement
public class GetBillFeesResponse extends GenericResponse {
    private Double montantFrais;

    public Double getMontantFrais() {
        return this.montantFrais;
    }

    public void setMontantFrais(Double montantFrais) {
        this.montantFrais = montantFrais;
    }

    @Override
    public String toString() {
        return "{" +
            " montantFrais='" + montantFrais + "'" +
            "}";
    }
    
}