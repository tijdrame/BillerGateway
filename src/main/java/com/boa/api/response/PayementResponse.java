package com.boa.api.response;

import java.time.Instant;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * PayementResponse
 */
@XmlRootElement
public class PayementResponse extends GenericResponse{
    protected String code;
    protected String description;
    protected Instant dateResponse;
    private AnnulationPaiement annulationPaiement;
    private String annulationCode, annulationMsg;
    public AnnulationPaiement getAnnulationPaiement() {
        return this.annulationPaiement;
    }

    public void setAnnulationPaiement(AnnulationPaiement annulationPaiement) {
        this.annulationPaiement = annulationPaiement;
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

    public String getAnnulationCode() {
        return this.annulationCode;
    }

    public void setAnnulationCode(String annulationCode) {
        this.annulationCode = annulationCode;
    }

    public String getAnnulationMsg() {
        return this.annulationMsg;
    }

    public void setAnnulationMsg(String annulationMsg) {
        this.annulationMsg = annulationMsg;
    }

    @Override
    public String toString() {
        return "{" +
            " code='" + code + "'" +
            ", description='" + description + "'" +
            ", dateResponse='" + dateResponse + "'" +
           // ", annulationPaiement='" + annulationPaiement + "'" +
            ", annulationCode='" + annulationCode + "'" +
            ", annulationMsg='" + annulationMsg + "'" +
            "}";
    }

}