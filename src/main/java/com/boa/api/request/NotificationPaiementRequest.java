package com.boa.api.request;

public class NotificationPaiementRequest {
    private String billerCode, billNum;
    private String langue, channelType;
    private String referencePaiement, customerName, billerReference;
    private Double amount;
    private String paiementMethod, paiementMode, declarationType, status, devise;

    private String varString1;
    private String varString2;
    private String varString3;
    private String varString4;

    public NotificationPaiementRequest() {
    }

    public NotificationPaiementRequest(String billerCode, String billNum, String langue, String channelType, String referencePaiement, String customerName, String billerReference, Double amount, String paiementMethod, String paiementMode, String declarationType, String status, String devise, String varString1, String varString2, String varString3) {
        this.billerCode = billerCode;
        this.billNum = billNum;
        this.langue = langue;
        this.channelType = channelType;
        this.referencePaiement = referencePaiement;
        this.customerName = customerName;
        this.billerReference = billerReference;
        this.amount = amount;
        this.paiementMethod = paiementMethod;
        this.paiementMode = paiementMode;
        this.declarationType = declarationType;
        this.status = status;
        this.devise = devise;
        this.varString1 = varString1;
        this.varString2 = varString2;
        this.varString3 = varString3;
    }

    public String getBillerCode() {
        return this.billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public String getBillNum() {
        return this.billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getLangue() {
        return this.langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getReferencePaiement() {
        return this.referencePaiement;
    }

    public void setReferencePaiement(String referencePaiement) {
        this.referencePaiement = referencePaiement;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBillerReference() {
        return this.billerReference;
    }

    public void setBillerReference(String billerReference) {
        this.billerReference = billerReference;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaiementMethod() {
        return this.paiementMethod;
    }

    public void setPaiementMethod(String paiementMethod) {
        this.paiementMethod = paiementMethod;
    }

    public String getPaiementMode() {
        return this.paiementMode;
    }

    public void setPaiementMode(String paiementMode) {
        this.paiementMode = paiementMode;
    }

    public String getDeclarationType() {
        return this.declarationType;
    }

    public void setDeclarationType(String declarationType) {
        this.declarationType = declarationType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDevise() {
        return this.devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
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

    public NotificationPaiementRequest billerCode(String billerCode) {
        setBillerCode(billerCode);
        return this;
    }

    public NotificationPaiementRequest billNum(String billNum) {
        setBillNum(billNum);
        return this;
    }

    public NotificationPaiementRequest langue(String langue) {
        setLangue(langue);
        return this;
    }

    public NotificationPaiementRequest channelType(String channelType) {
        setChannelType(channelType);
        return this;
    }

    public NotificationPaiementRequest referencePaiement(String referencePaiement) {
        setReferencePaiement(referencePaiement);
        return this;
    }

    public NotificationPaiementRequest customerName(String customerName) {
        setCustomerName(customerName);
        return this;
    }

    public NotificationPaiementRequest billerReference(String billerReference) {
        setBillerReference(billerReference);
        return this;
    }

    public NotificationPaiementRequest amount(Double amount) {
        setAmount(amount);
        return this;
    }

    public NotificationPaiementRequest paiementMethod(String paiementMethod) {
        setPaiementMethod(paiementMethod);
        return this;
    }

    public NotificationPaiementRequest paiementMode(String paiementMode) {
        setPaiementMode(paiementMode);
        return this;
    }

    public NotificationPaiementRequest declarationType(String declarationType) {
        setDeclarationType(declarationType);
        return this;
    }

    public NotificationPaiementRequest status(String status) {
        setStatus(status);
        return this;
    }

    public NotificationPaiementRequest devise(String devise) {
        setDevise(devise);
        return this;
    }

    public NotificationPaiementRequest varString1(String varString1) {
        setVarString1(varString1);
        return this;
    }

    public NotificationPaiementRequest varString2(String varString2) {
        setVarString2(varString2);
        return this;
    }

    public NotificationPaiementRequest varString3(String varString3) {
        setVarString3(varString3);
        return this;
    }

    public String getVarString4() {
        return this.varString4;
    }

    public void setVarString4(String varString4) {
        this.varString4 = varString4;
    }

    @Override
    public String toString() {
        return "{" +
            " billerCode='" + getBillerCode() + "'" +
            ", billNum='" + getBillNum() + "'" +
            ", langue='" + getLangue() + "'" +
            ", channelType='" + getChannelType() + "'" +
            ", referencePaiement='" + getReferencePaiement() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", billerReference='" + getBillerReference() + "'" +
            ", amount='" + getAmount() + "'" +
            ", paiementMethod='" + getPaiementMethod() + "'" +
            ", paiementMode='" + getPaiementMode() + "'" +
            ", declarationType='" + getDeclarationType() + "'" +
            ", status='" + getStatus() + "'" +
            ", devise='" + getDevise() + "'" +
            ", varString1='" + getVarString1() + "'" +
            ", varString2='" + getVarString2() + "'" +
            ", varString3='" + getVarString3() + "'" +
            ", varString4='" + getVarString4() + "'" +
            "}";
    }
}