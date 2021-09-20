package com.boa.api.request;

/**
 * RecuPaiementRequest
 */
public class RecuPaiementRequest {

    private String /*refPaie,*/ billNum, cashingRef;
    private String langue, billerCode;

    /*public String getRefPaie() {
        return this.refPaie;
    }

    public void setRefPaie(String refPaie) {
        this.refPaie = refPaie;
    }*/

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

    
    public String getCashingRef() {
        return this.cashingRef;
    }

    public void setCashingRef(String cashingRef) {
        this.cashingRef = cashingRef;
    }

    public String getBillNum() {
        return this.billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    
    @Override
    public String toString() {
        return "{" +
            " billNum='" + billNum + "'" +
            ", cashingRef='" + cashingRef + "'" +
            ", langue='" + langue + "'" +
            ", billerCode='" + billerCode + "'" +
            "}";
    }

}