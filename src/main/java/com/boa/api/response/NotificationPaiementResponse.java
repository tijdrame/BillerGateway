package com.boa.api.response;

public class NotificationPaiementResponse extends GenericResponse{
    
    private Double amount;
    private String reference;
    private String numberTransaction;

    private String varString1;
    private String varString2;
    private String varString3;

    public NotificationPaiementResponse() {
    }

    public NotificationPaiementResponse(Double amount, String reference, String numberTransaction, String varString1, String varString2, String varString3) {
        this.amount = amount;
        this.reference = reference;
        this.numberTransaction = numberTransaction;
        this.varString1 = varString1;
        this.varString2 = varString2;
        this.varString3 = varString3;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNumberTransaction() {
        return this.numberTransaction;
    }

    public void setNumberTransaction(String numberTransaction) {
        this.numberTransaction = numberTransaction;
    }

    public String getVarString1() {
        return this.varString1;
    }

    public void setVarString1(String varString1) {
        this.varString1 = varString1;
    }

    public String getVarString2() {
        return this.varString2;
    }

    public void setVarString2(String varString2) {
        this.varString2 = varString2;
    }

    public String getVarString3() {
        return this.varString3;
    }

    public void setVarString3(String varString3) {
        this.varString3 = varString3;
    }

    public NotificationPaiementResponse amount(Double amount) {
        setAmount(amount);
        return this;
    }

    public NotificationPaiementResponse reference(String reference) {
        setReference(reference);
        return this;
    }

    public NotificationPaiementResponse numberTransaction(String numberTransaction) {
        setNumberTransaction(numberTransaction);
        return this;
    }

    public NotificationPaiementResponse varString1(String varString1) {
        setVarString1(varString1);
        return this;
    }

    public NotificationPaiementResponse varString2(String varString2) {
        setVarString2(varString2);
        return this;
    }

    public NotificationPaiementResponse varString3(String varString3) {
        setVarString3(varString3);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " amount='" + getAmount() + "'" +
            ", reference='" + getReference() + "'" +
            ", numberTransaction='" + getNumberTransaction() + "'" +
            ", varString1='" + getVarString1() + "'" +
            ", varString2='" + getVarString2() + "'" +
            ", varString3='" + getVarString3() + "'" +
            "}";
    }

}
