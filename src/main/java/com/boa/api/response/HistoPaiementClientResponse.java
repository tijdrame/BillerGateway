package com.boa.api.response;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class HistoPaiementClientResponse extends GenericResponse{
    private String code, description;
    private Instant dateResponse;
    private List<HistoPaiment> histoPaiments;
    

    public HistoPaiementClientResponse() {
    }

    public HistoPaiementClientResponse(String code, String description, Instant dateResponse, List<HistoPaiment> histoPaiments) {
        this.code = code;
        this.description = description;
        this.dateResponse = dateResponse;
        this.histoPaiments = histoPaiments;
    }

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

    public List<HistoPaiment> getHistoPaiments() {
        if(histoPaiments==null || histoPaiments.isEmpty()) histoPaiments = new ArrayList<>();
        return this.histoPaiments;
    }

    public void setHistoPaiments(List<HistoPaiment> histoPaiments) {
        this.histoPaiments = histoPaiments;
    }

    public HistoPaiementClientResponse code(String code) {
        this.code = code;
        return this;
    }

    public HistoPaiementClientResponse description(String description) {
        this.description = description;
        return this;
    }

    public HistoPaiementClientResponse dateResponse(Instant dateResponse) {
        this.dateResponse = dateResponse;
        return this;
    }

    public HistoPaiementClientResponse histoPaiments(List<HistoPaiment> histoPaiments) {
        this.histoPaiments = histoPaiments;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateResponse='" + getDateResponse() + "'" +
            ", histoPaiments='" + getHistoPaiments() + "'" +
            "}";
    }


    
    
}