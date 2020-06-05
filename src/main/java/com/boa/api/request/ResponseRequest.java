package com.boa.api.request;

/**
 * ResponseRequest
 */
public class ResponseRequest {

    private String langue, billerCode, retourCode, serviceName;

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

    public String getRetourCode() {
        return this.retourCode;
    }

    public void setRetourCode(String retourCode) {
        this.retourCode = retourCode;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "{" +
            " langue='" + langue + "'" +
            ", billerCode='" + billerCode + "'" +
            ", retourCode='" + retourCode + "'" +
            ", serviceName='" + serviceName + "'" +
            "}";
    }

}