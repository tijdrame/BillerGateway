package com.boa.api.request;

/**
 * GetAccountRequest
 */
public class GetAccountRequest {

    private String billerCode, accountType;


    public String getBillerCode() {
        return this.billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    
    @Override
    public String toString() {
        return "{" +
            " billerCode='" + billerCode + "'" +
            ", accountType='" + accountType + "'" +
            "}";
    }

}