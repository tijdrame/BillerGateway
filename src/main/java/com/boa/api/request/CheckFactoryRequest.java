package com.boa.api.request;

import java.util.HashMap;
import java.util.Map;


public class CheckFactoryRequest {

    private String vnumFact;
    private String refenca;
    private String telcli;
    private String billerCode;
    private String langue;
    private String channel;
    private Map<Integer, InnerType> ordonnerParams = new HashMap<>();

    private String billerJirama;
    private String billerCnss;

    public CheckFactoryRequest() {}

    public CheckFactoryRequest(String billerJirama, String billerCnss) {
        this.billerCnss = billerCnss;
        this.billerJirama = billerJirama;
	}

	public String getVnumFact() {
        return vnumFact;
    }

    public void setVnumFact(String vnumFact) {
        this.vnumFact = vnumFact;
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

    public String getBillerCode() {
        return this.billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public String getLangue() {
        return this.langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


    public Map<Integer,InnerType> getOrdonnerParams() {
        if(ordonnerParams.isEmpty() && billerCode.equalsIgnoreCase(billerJirama)){
            InnerType innerType1 = new InnerType();
            innerType1.valueChamp = getVnumFact();
            InnerType innerType2 = new InnerType();
            innerType2.valueChamp = getRefenca();
            InnerType innerType3 = new InnerType();
            innerType3.valueChamp = getTelcli();
            this.ordonnerParams.put(1, innerType1);
            this.ordonnerParams.put(2, innerType2);
            this.ordonnerParams.put(3, innerType3);
        }else if(ordonnerParams.isEmpty() && billerCode.equalsIgnoreCase(billerCnss)){

        }
        return this.ordonnerParams;
    }

    public void setOrdonnerParams(Map<Integer, InnerType> ordonnerParams) {
        this.ordonnerParams = ordonnerParams;
    }
    

    @Override
    public String toString() {
        return "{" +
            " vnumFact='" + vnumFact + "'" +
            ", refenca='" + refenca + "'" +
            ", telcli='" + telcli + "'" +
            ", channel='" + channel + "'" +
            "}";
    }

    public String getBillerJirama() {
        return this.billerJirama;
    }

    public void setBillerJirama(String billerJirama) {
        this.billerJirama = billerJirama;
    }

    public String getBillerCnss() {
        return this.billerCnss;
    }

    public void setBillerCnss(String billerCnss) {
        this.billerCnss = billerCnss;
    }

    public class InnerType {
        public String valueChamp;     
    }
}