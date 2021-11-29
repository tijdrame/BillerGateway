package com.boa.api.response;

/**
 * BillerByCodeResponse
 */
public class BillerByCodeResponse extends GenericResponse{

    private Integer bILLERID;
    private String bILLERCODE;
    private String nAME;
    private String pAYS;
    private String cHANNEL;
    private String eMAIL;
    private String tELEPHONE;
    private String sTATUS;
    private String aDDRESS;
    private String lOGO;
    private String wEBSITE;
    private String bILLERCATEGORY;
    private String dEVISE;
    private String libelleCategory;


    public Integer getBILLERID() {
        return this.bILLERID;
    }

    public void setBILLERID(Integer bILLERID) {
        this.bILLERID = bILLERID;
    }

    public String getBILLERCODE() {
        return this.bILLERCODE;
    }

    public void setBILLERCODE(String bILLERCODE) {
        this.bILLERCODE = bILLERCODE;
    }

    public String getNAME() {
        return this.nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public String getPAYS() {
        return this.pAYS;
    }

    public void setPAYS(String pAYS) {
        this.pAYS = pAYS;
    }

    public String getCHANNEL() {
        return this.cHANNEL;
    }

    public void setCHANNEL(String cHANNEL) {
        this.cHANNEL = cHANNEL;
    }

    public String getEMAIL() {
        return this.eMAIL;
    }

    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    public String getTELEPHONE() {
        return this.tELEPHONE;
    }

    public void setTELEPHONE(String tELEPHONE) {
        this.tELEPHONE = tELEPHONE;
    }

    public String getSTATUS() {
        return this.sTATUS;
    }

    public void setSTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }

    public String getADDRESS() {
        return this.aDDRESS;
    }

    public void setADDRESS(String aDDRESS) {
        this.aDDRESS = aDDRESS;
    }

    public String getLOGO() {
        return this.lOGO;
    }

    public void setLOGO(String lOGO) {
        this.lOGO = lOGO;
    }

    public String getWEBSITE() {
        return this.wEBSITE;
    }

    public void setWEBSITE(String wEBSITE) {
        this.wEBSITE = wEBSITE;
    }

    public String getBILLERCATEGORY() {
        return this.bILLERCATEGORY;
    }

    public void setBILLERCATEGORY(String bILLERCATEGORY) {
        this.bILLERCATEGORY = bILLERCATEGORY;
    }

    public String getDEVISE() {
        return this.dEVISE;
    }

    public void setDEVISE(String dEVISE) {
        this.dEVISE = dEVISE;
    }

    public String getLibelleCategory() {
        return this.libelleCategory;
    }

    public void setLibelleCategory(String libelleCategory) {
        this.libelleCategory = libelleCategory;
    }

    
    @Override
    public String toString() {
        return "{" +
            " bILLERID='" + bILLERID + "'" +
            ", bILLERCODE='" + bILLERCODE + "'" +
            ", nAME='" + nAME + "'" +
            ", pAYS='" + pAYS + "'" +
            ", cHANNEL='" + cHANNEL + "'" +
            ", eMAIL='" + eMAIL + "'" +
            ", tELEPHONE='" + tELEPHONE + "'" +
            ", sTATUS='" + sTATUS + "'" +
            ", aDDRESS='" + aDDRESS + "'" +
            //", lOGO='" + lOGO + "'" +
            ", wEBSITE='" + wEBSITE + "'" +
            ", bILLERCATEGORY='" + bILLERCATEGORY + "'" +
            ", dEVISE='" + dEVISE + "'" +
            ", LIBELLE_GATEGORY='" + libelleCategory + "'" +
            "}";
    }
}