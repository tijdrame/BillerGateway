package com.boa.api.response;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * GetBillsByRefResponse
 */
@XmlRootElement
public class GetBillsByRefResponse extends GenericResponse{
    
    private List<ItemResp> billList;
    private ExceptionResponse exceptionResponse;
    protected String code;
    protected String description;
    protected Instant dateResponse;

    public List<ItemResp> getBillList() {
        if(billList==null || billList.isEmpty()) billList = new ArrayList<>();
        return this.billList;
    }

    public void setBillList(List<ItemResp> billList) {
        this.billList = billList;
    }

    public ExceptionResponse getExceptionResponse() {
        return this.exceptionResponse;
    }

    public void setExceptionResponse(ExceptionResponse exceptionResponse) {
        this.exceptionResponse = exceptionResponse;
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
    
}

