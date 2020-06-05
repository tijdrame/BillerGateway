package com.boa.api.request;

/**
 * RecuPaiementRequest
 */
public class RecuPaiementRequest {

    private String refPaie;
    private String langue, billerCode;

    public String getRefPaie() {
        return this.refPaie;
    }

    public void setRefPaie(String refPaie) {
        this.refPaie = refPaie;
    }

    public String getLangue() {
        return this.langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getBillerCode() {
        return this.billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

}