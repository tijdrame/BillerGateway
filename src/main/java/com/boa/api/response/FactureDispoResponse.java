package com.boa.api.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * FactureDispoResponse
 */
@XmlRootElement
public class FactureDispoResponse extends GenericResponse{
    
    private List<FacuresDispo> facturesDispos = new ArrayList<>();

    public List<FacuresDispo> getFacturesDispos() {
        return this.facturesDispos;
    }

    public void setFacturesDispos(List<FacuresDispo> facturesDispos) {
        this.facturesDispos = facturesDispos;
    }
    
}

