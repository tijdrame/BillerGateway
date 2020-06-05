package com.boa.api.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * CheckFactoryResponse
 */
@XmlRootElement
public class CheckFactoryResponse extends GenericResponse {

    private String billNum;    
    private String custumerRef;    
    private String billDate;    
    private Double billAmount;    
    private String requierNumber;
    private String customerName;
    private String sessionNum;
    private String  code, description;
    private Double feeAmount;
    private ExceptionResponse exceptionResponse;

    public CheckFactoryResponse() {}

    /*public CheckFactoryResponse(String billerJirama, String billerCnss) {
        this.billerCnss = billerCnss;
        this.billerJirama = billerJirama;
	}*/

    public String getBillNum() {
        return this.billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getCustumerRef() {
        return this.custumerRef;
    }

    public void setCustumerRef(String custumerRef) {
        this.custumerRef = custumerRef;
    }

    public String getBillDate() {
        return this.billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public Double getBillAmount() {
        return this.billAmount;
    }

    public void setBillAmount(Double billAmount) {
        this.billAmount = billAmount;
    }

    public String getRequierNumber() {
        return this.requierNumber;
    }

    public void setRequierNumber(String requierNumber) {
        this.requierNumber = requierNumber;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSessionNum() {
        return this.sessionNum;
    }

    public void setSessionNum(String sessionNum) {
        this.sessionNum = sessionNum;
    }

    public ExceptionResponse getExceptionResponse() {
        return this.exceptionResponse;
    }

    public void setExceptionResponse(ExceptionResponse exceptionResponse) {
        this.exceptionResponse = exceptionResponse;
    }

    public Double getFeeAmount() {
        return this.feeAmount;
    }

    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /*public String getBillerJirama() {
        return this.billerJirama;
    }

    public void setBillerJirama(String billerJirama) {
        this.billerJirama = billerJirama;
    }

    public String getBillerCnss() {
        return this.billerCnss;
    }

    public void setBillerCnss(String billerCnss) {
        this.billerCnss = billerCnss;
    }*/

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" +
            " billNum='" + getBillNum() + "'" +
            ", custumerRef='" + getCustumerRef() + "'" +
            ", billDate='" + getBillDate() + "'" +
            ", billAmount='" + getBillAmount() + "'" +
            ", requierNumber='" + getRequierNumber() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", sessionNum='" + getSessionNum() + "'" +
            ", feeAmount='" + getFeeAmount() + "'" +
            ", code='" + code + "'" +
            ", exceptionResponse='" + getExceptionResponse() + "'" +
            "}";
    }


}