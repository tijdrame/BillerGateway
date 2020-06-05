package com.boa.api.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * ResponseResponse
 */
@XmlRootElement
public class ResponseResponse {

    private String code, description;


    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" +
            " code='" + code + "'" +
            ", description='" + description + "'" +
            "}";
    }

}