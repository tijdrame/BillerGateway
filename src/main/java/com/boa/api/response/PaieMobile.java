package com.boa.api.response;

/**
 * PaieMobile
 */
public class PaieMobile {

    private String code, result;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "{" +
            " code='" + code + "'" +
            ", result='" + result + "'" +
            "}";
    }

}