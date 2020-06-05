package com.boa.api.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * RecuPaiementResponse
 */
@XmlRootElement
public class RecuPaiementResponse extends GenericResponse {

    private String numPaiement;
    private String datePaiement;
    private ExceptionResponse exceptionResponse;

    public String getNumPaiement() {
        return this.numPaiement;
    }

    public void setNumPaiement(String numPaiement) {
        this.numPaiement = numPaiement;
    }

    public String getDatePaiement() {
        return this.datePaiement;
    }

    public void setDatePaiement(String datePaiement) {
        this.datePaiement = datePaiement;
    }

    public ExceptionResponse getExceptionResponse() {
        return this.exceptionResponse;
    }

    public void setExceptionResponse(ExceptionResponse exceptionResponse) {
        this.exceptionResponse = exceptionResponse;
    }

}