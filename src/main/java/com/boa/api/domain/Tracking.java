package com.boa.api.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Tracking.
 */
@Entity
@Table(name = "BOA_BILM_TRACKING_T")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tracking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tracking_seq_gen")
    @SequenceGenerator(name = "tracking_seq_gen", sequenceName = "tracking_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "token_tr")
    private String tokenTr;

    @Column(name = "response_tr")
    private String responseTr;

    @Column(name = "code_response")
    private String codeResponse;

    @Column(name = "end_point_tr")
    private String endPointTr;

    @Column(name = "date_request")
    private Instant dateRequest;

    @Column(name = "date_response")
    private Instant dateResponse;

    @Column(name = "login_acteur")
    private String loginActeur;

    @Lob
    @Column(name = "request_tr")
    private String requestTr;

    @Column(name = "cnps_transaction_Id")
    private String cnpsTransactionId;

    @Column(name = "cnps_montant_bill")
    private Double cnpsMontantBill;

    @Column(name = "cnps_canal")
    private String cnpsCanal;


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTokenTr() {
        return tokenTr;
    }

    public Tracking tokenTr(String tokenTr) {
        this.tokenTr = tokenTr;
        return this;
    }

    public void setTokenTr(String tokenTr) {
        this.tokenTr = tokenTr;
    }

    public String getResponseTr() {
        return responseTr;
    }

    public Tracking responseTr(String responseTr) {
        this.responseTr = responseTr;
        return this;
    }

    public void setResponseTr(String responseTr) {
        this.responseTr = responseTr;
    }

    public String getCodeResponse() {
        return codeResponse;
    }

    public Tracking codeResponse(String codeResponse) {
        this.codeResponse = codeResponse;
        return this;
    }

    public void setCodeResponse(String codeResponse) {
        this.codeResponse = codeResponse;
    }

    public String getEndPointTr() {
        return endPointTr;
    }

    public Tracking endPointTr(String endPointTr) {
        this.endPointTr = endPointTr;
        return this;
    }

    public void setEndPointTr(String endPointTr) {
        this.endPointTr = endPointTr;
    }

    public Instant getDateRequest() {
        return dateRequest;
    }

    public Tracking dateRequest(Instant dateRequest) {
        this.dateRequest = dateRequest;
        return this;
    }

    public void setDateRequest(Instant dateRequest) {
        this.dateRequest = dateRequest;
    }

    public Instant getDateResponse() {
        return dateResponse;
    }

    public Tracking dateResponse(Instant dateResponse) {
        this.dateResponse = dateResponse;
        return this;
    }

    public void setDateResponse(Instant dateResponse) {
        this.dateResponse = dateResponse;
    }

    public String getLoginActeur() {
        return loginActeur;
    }

    public Tracking loginActeur(String loginActeur) {
        this.loginActeur = loginActeur;
        return this;
    }

    public void setLoginActeur(String loginActeur) {
        this.loginActeur = loginActeur;
    }

    public String getRequestTr() {
        return requestTr;
    }

    public Tracking requestTr(String requestTr) {
        this.requestTr = requestTr;
        return this;
    }

    public void setRequestTr(String requestTr) {
        this.requestTr = requestTr;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Tracking() {
    }

    public Tracking(Long id, String tokenTr, String responseTr, String codeResponse, String endPointTr, Instant dateRequest, Instant dateResponse, String loginActeur, String requestTr, String cnpsTransactionId, Double cnpsMontantBill) {
        this.id = id;
        this.tokenTr = tokenTr;
        this.responseTr = responseTr;
        this.codeResponse = codeResponse;
        this.endPointTr = endPointTr;
        this.dateRequest = dateRequest;
        this.dateResponse = dateResponse;
        this.loginActeur = loginActeur;
        this.requestTr = requestTr;
        this.cnpsTransactionId = cnpsTransactionId;
        this.cnpsMontantBill = cnpsMontantBill;
    }

    public String getCnpsTransactionId() {
        return this.cnpsTransactionId;
    }

    public void setCnpsTransactionId(String cnpsTransactionId) {
        this.cnpsTransactionId = cnpsTransactionId;
    }

    public Double getCnpsMontantBill() {
        return this.cnpsMontantBill;
    }

    public void setCnpsMontantBill(Double cnpsMontantBill) {
        this.cnpsMontantBill = cnpsMontantBill;
    }

    public Tracking id(Long id) {
        setId(id);
        return this;
    }

    public Tracking cnpsTransactionId(String cnpsTransactionId) {
        setCnpsTransactionId(cnpsTransactionId);
        return this;
    }

    public Tracking cnpsMontantBill(Double cnpsMontantBill) {
        setCnpsMontantBill(cnpsMontantBill);
        return this;
    }

    public String getCnpsCanal() {
        return this.cnpsCanal;
    }

    public void setCnpsCanal(String cnpsCanal) {
        this.cnpsCanal = cnpsCanal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tracking)) {
            return false;
        }
        return id != null && id.equals(((Tracking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tracking{" +
            "id=" + getId() +
            ", tokenTr='" + getTokenTr() + "'" +
            ", responseTr='" + getResponseTr() + "'" +
            ", codeResponse='" + getCodeResponse() + "'" +
            ", endPointTr='" + getEndPointTr() + "'" +
            ", dateRequest='" + getDateRequest() + "'" +
            ", dateResponse='" + getDateResponse() + "'" +
            ", loginActeur='" + getLoginActeur() + "'" +
            ", requestTr='" + getRequestTr() + "'" +
            ", cnpsTransactionId='" + getCnpsTransactionId() + "'" +
            ", cnpsMontantBill='" + getCnpsMontantBill() + "'" +
            ", cnpsCanal='" + getCnpsCanal() + "'" +
            "}";
    }
}
