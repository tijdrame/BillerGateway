package com.boa.api.response;


public class HistoPaiment {
    private String facturier, numeroTrx,  compteClient, canal, numeroFacture;
    private Double amount;
    private String datePaiement;

    public HistoPaiment() {
    }

    public HistoPaiment(String facturier, String numeroTrx, String compteClient, 
    String canal, Double amount,  String numeroFacture, String datePaiement) {
        this.facturier = facturier;
        this.numeroTrx = numeroTrx;
        this.compteClient = compteClient;
        this.canal = canal;
        this.amount = amount;
        //this.refPaiement = refPaiement;
        this.numeroFacture = numeroFacture;
        this.datePaiement = datePaiement;
    }

    public String getFacturier() {
        return this.facturier;
    }

    public void setFacturier(String facturier) {
        this.facturier = facturier;
    }

    public String getNumeroTrx() {
        return this.numeroTrx;
    }

    public void setNumeroTrx(String numeroTrx) {
        this.numeroTrx = numeroTrx;
    }

    public String getCompteClient() {
        return this.compteClient;
    }

    public void setCompteClient(String compteClient) {
        this.compteClient = compteClient;
    }

    public String getCanal() {
        return this.canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /*public Double getRefPaiement() {
        return this.refPaiement;
    }

    public void setRefPaiement(Double refPaiement) {
        this.refPaiement = refPaiement;
    }*/

    public String getNumeroFacture() {
        return this.numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public String getDatePaiement() {
        return this.datePaiement;
    }

    public void setDatePaiement(String datePaiement) {
        this.datePaiement = datePaiement;
    }

    public HistoPaiment facturier(String facturier) {
        this.facturier = facturier;
        return this;
    }

    public HistoPaiment numeroTrx(String numeroTrx) {
        this.numeroTrx = numeroTrx;
        return this;
    }

    public HistoPaiment compteClient(String compteClient) {
        this.compteClient = compteClient;
        return this;
    }

    public HistoPaiment canal(String canal) {
        this.canal = canal;
        return this;
    }

    public HistoPaiment amount(Double amount) {
        this.amount = amount;
        return this;
    }

    /*public HistoPaiment refPaiement(Double refPaiement) {
        this.refPaiement = refPaiement;
        return this;
    }*/

    public HistoPaiment numeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
        return this;
    }

    public HistoPaiment datePaiement(String datePaiement) {
        this.datePaiement = datePaiement;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " facturier='" + getFacturier() + "'" +
            ", numeroTrx='" + getNumeroTrx() + "'" +
            ", compteClient='" + getCompteClient() + "'" +
            ", canal='" + getCanal() + "'" +
            ", amount='" + getAmount() + "'" +
           // ", refPaiement='" + getRefPaiement() + "'" +
            ", numeroFacture='" + getNumeroFacture() + "'" +
            ", datePaiement='" + getDatePaiement() + "'" +
            "}";
    }

}