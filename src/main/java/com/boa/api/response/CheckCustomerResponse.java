package com.boa.api.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * CheckCustomerResponse
 */
@XmlRootElement
public class CheckCustomerResponse extends GenericResponse{
    private String referenceClient, referenceEncaissement;

    public String getReferenceClient() {
        return this.referenceClient;
    }

    public void setReferenceClient(String referenceClient) {
        this.referenceClient = referenceClient;
    }

    public String getReferenceEncaissement() {
        return this.referenceEncaissement;
    }

    public void setReferenceEncaissement(String referenceEncaissement) {
        this.referenceEncaissement = referenceEncaissement;
    }

    @Override
    public String toString() {
        return "{" +
            " referenceClient='" + referenceClient + "'" +
            ", referenceEncaissement='" + referenceEncaissement + "'" +
            "}";
    }
    
}