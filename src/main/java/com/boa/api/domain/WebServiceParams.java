package com.boa.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * WebServiceParams
 */
@Entity
@Table(name = "BOA_BILM_WEBSERVICES_PARAMS_T")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WebServiceParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "PARAM_ID")
    private Long id;

    @Column(name = "PARAM_NAME")
    private String paramName;

    @Column(name = "ORDRE")
    private Integer ordre;

    @Column(name = "PARAM_TYPE")
    private String paramType;

    @Column(name = "PARAM_SENS")
    private String paramSens;

    @Column(name = "TAILLE")
    private Integer taille;

    @Column(name = "PARAM_NAME_CORRESP")
    private String paramNameCorresp;

    @Column(name = "PARENTRESP")
    private String parentResponse;

    @ManyToOne
    @JoinColumn(name = "SERVICE_ID")
    private WebServices webServices;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamName() {
        return this.paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Integer getOrdre() {
        return this.ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public String getParamType() {
        return this.paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamSens() {
        return this.paramSens;
    }

    public void setParamSens(String paramSens) {
        this.paramSens = paramSens;
    }

    public Integer getTaille() {
        return this.taille;
    }

    public void setTaille(Integer taille) {
        this.taille = taille;
    }

    public String getParamNameCorresp() {
        return this.paramNameCorresp;
    }

    public void setParamNameCorresp(String paramNameCorresp) {
        this.paramNameCorresp = paramNameCorresp;
    }

    public WebServices getWebServices() {
        return this.webServices;
    }

    public void setWebServices(WebServices webServices) {
        this.webServices = webServices;
    }

    public String getParentResponse() {
        return this.parentResponse;
    }

    public void setParentResponse(String parentResponse) {
        this.parentResponse = parentResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WebServiceParams)) {
            return false;
        }
        return id != null && id.equals(((WebServiceParams) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + id + "'" +
            ", paramName='" + paramName + "'" +
            ", ordre='" + ordre + "'" +
            ", paramType='" + paramType + "'" +
            ", paramSens='" + paramSens + "'" +
            ", taille='" + taille + "'" +
            ", paramNameCorresp='" + paramNameCorresp + "'" +
            ", webServices='" + webServices + "'" +
            "}";
    }

}