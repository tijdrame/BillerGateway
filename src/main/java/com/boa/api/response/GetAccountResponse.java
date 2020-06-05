package com.boa.api.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * GetAccountResponse
 */
@XmlRootElement
public class GetAccountResponse extends GenericResponse{

    private String numAccount;
    private ExceptionResponse exceptionResponse;


    public String getNumAccount() {
        return this.numAccount;
    }

    public void setNumAccount(String numAccount) {
        this.numAccount = numAccount;
    }

    public ExceptionResponse getExceptionResponse() {
        return this.exceptionResponse;
    }

    public void setExceptionResponse(ExceptionResponse exceptionResponse) {
        this.exceptionResponse = exceptionResponse;
    }

    @Override
    public String toString() {
        return "{" +
            " numAccount='" + numAccount + "'" +
            ", exceptionResponse='" + exceptionResponse + "'" +
            "}";
    }
    
}