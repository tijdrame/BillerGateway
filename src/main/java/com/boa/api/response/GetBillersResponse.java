package com.boa.api.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * GetBillersResponse
 */
@XmlRootElement
public class GetBillersResponse extends GenericResponse {
    private List <Biller> billers;

    public List<Biller> getBillers() {
        if(billers==null || billers.isEmpty()) billers = new ArrayList<>();
        return this.billers;
    }

    public void setBillers(List<Biller> billers) {
        this.billers = billers;
    }

    
    
}