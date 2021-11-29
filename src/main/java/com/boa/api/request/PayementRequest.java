package com.boa.api.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * PayementRequest
 */
@XmlRootElement
public class PayementRequest {

    private String billerCode, billNum, cashingRef, paymentDate, phoneNumber, langue;
    private String  deviceId, customerAccount, billRefTrx, description, channelType;
    private String sessionNum, custumerRef, telBiller;
    private Double amount, fees;
    private String  compteDeb, compteCredit, devise, pays, dispo, val, libAuto, codopsc, urlLink, content;

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

    public String getCashingRef() {
        return this.cashingRef;
    }

    public void setCashingRef(String cashingRef) {
        this.cashingRef = cashingRef;
    }

    public String getPaymentDate() {
        return this.paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCustomerAccount() {
        return this.customerAccount;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }

    public String getBillRefTrx() {
        return this.billRefTrx;
    }

    public void setBillRefTrx(String billRefTrx) {
        this.billRefTrx = billRefTrx;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getSessionNum() {
        return this.sessionNum;
    }

    public void setSessionNum(String sessionNum) {
        this.sessionNum = sessionNum;
    }

    public String getCustumerRef() {
        return this.custumerRef;
    }

    public void setCustumerRef(String custumerRef) {
        this.custumerRef = custumerRef;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTelBiller() {
        return this.telBiller;
    }

    public void setTelBiller(String telBiller) {
        this.telBiller = telBiller;
    }

    public Double getFees() {
        return this.fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public String getCompteDeb() {
        return this.compteDeb;
    }

    public void setCompteDeb(String compteDeb) {
        this.compteDeb = compteDeb;
    }

    public String getCompteCredit() {
        return this.compteCredit;
    }

    public void setCompteCredit(String compteCredit) {
        this.compteCredit = compteCredit;
    }

    public String getDevise() {
        return this.devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getPays() {
        return this.pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getDispo() {
        return this.dispo = "DISPONIBLE";
    }

    public void setDispo(String dispo) {
        this.dispo = dispo;
    }

    public String getVal() {
        return this.val = "V";
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getLibAuto() {
        return this.libAuto = "PAYEMENT FACTURE";
    }

    public void setLibAuto(String libAuto) {
        this.libAuto = libAuto;
    }

    public String getCodopsc() {
        return this.codopsc;// = "GAB";
    }

    public void setCodopsc(String codopsc) {
        this.codopsc = codopsc;
    }

    public String getUrlLink() {
        return this.urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "{" +
            " billerCode='" + billerCode + "'" +
            ", billNum='" + billNum + "'" +
            ", cashingRef='" + cashingRef + "'" +
            ", paymentDate='" + paymentDate + "'" +
            ", phoneNumber='" + phoneNumber + "'" +
            ", langue='" + langue + "'" +
            ", deviceId='" + deviceId + "'" +
            ", customerAccount='" + customerAccount + "'" +
            ", billRefTrx='" + billRefTrx + "'" +
            ", description='" + description + "'" +
            ", channelType='" + channelType + "'" +
            ", sessionNum='" + sessionNum + "'" +
            ", custumerRef='" + custumerRef + "'" +
            ", telBiller='" + telBiller + "'" +
            ", amount='" + amount + "'" +
            ", fees='" + fees + "'" +
            ", compteDeb='" + compteDeb + "'" +
            ", compteCredit='" + compteCredit + "'" +
            ", devise='" + devise + "'" +
            ", pays='" + pays + "'" +
            ", dispo='" + dispo + "'" +
            ", val='" + val + "'" +
            ", libAuto='" + libAuto + "'" +
            ", codopsc='" + codopsc + "'" +
            ", urlLink='" + urlLink + "'" +
            ", content='" + content + "'" +
            "}";
    }

}