package com.boa.api.request;

public class GetBillsByRefJiramaReq {

    private String vref;
    private String refenca;
    private String telcli, channel;
    private String langue, billerCode;

    public String getVref() {
        return this.vref;
    }

    public void setVref(String vref) {
        this.vref = vref;
    }

    public String getRefenca() {
        return refenca;
    }

    public void setRefenca(String refenca) {
        this.refenca = refenca;
    }

    public String getTelcli() {
        return telcli;
    }

    public void setTelcli(String telcli) {
        this.telcli = telcli;
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

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

}