package com.boa.api.response;

/**
 * AnnulationPaiement
 */
public class AnnulationPaiement {

    private String code, resultat;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResultat() {
        return this.resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    @Override
    public String toString() {
        return "{" +
            " code='" + code + "'" +
            ", resultat='" + resultat + "'" +
            "}";
    }

}