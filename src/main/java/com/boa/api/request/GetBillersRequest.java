package com.boa.api.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * GetBillersRequest
 */
@XmlRootElement
public class GetBillersRequest {

    private String country;

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "{" +
            " country='" + country + "'" +
            "}";
    }

}