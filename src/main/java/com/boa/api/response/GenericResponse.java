package com.boa.api.response;

import java.time.Instant;

public class GenericResponse {
    protected String code;
    protected String description;
    protected Instant dateResponse;
    
    /*
     * private String idClient;
     * 
     * public String getIdClient() { return this.idClient; } public void
     * setIdClient(String idClient) { this.idClient = idClient; }
     */

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

    public Instant getDateResponse() {
        return this.dateResponse;
    }

    public void setDateResponse(Instant dateResponse) {
        this.dateResponse = dateResponse;
    }
}