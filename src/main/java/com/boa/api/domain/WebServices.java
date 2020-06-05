package com.boa.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;

/**
 * WebServices
 */
@Entity
@Table(name = "BOA_BILM_WEBSERVICES_T")
/*@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "WebServices.GET_WS_DETAILS", 
    procedureName = "BOA_BILM_GATEWAY_PKG.GET_WS_DETAILS",
    resultClasses = WebServices.class,
    parameters = {
        @StoredProcedureParameter(name = "P_BILLER_CODE ", type = String.class, mode = ParameterMode.IN),
        @StoredProcedureParameter(name = "P_WEBSERVICE_NAME ", type = String.class, mode = ParameterMode.IN)
    }
    )
})*/
@NamedStoredProcedureQuery(
  name="BOA_BILM_GATEWAY_PKG.GET_WS_DETAILS",
  procedureName="BOA_BILM_GATEWAY_PKG.GET_WS_DETAILS",
  //resultClasses = { String.class },
  parameters = {
    @StoredProcedureParameter(name = "P_BILLER_CODE ", type = String.class, mode = ParameterMode.IN),
    @StoredProcedureParameter(name = "P_WEBSERVICE_NAME ", type = String.class, mode = ParameterMode.IN)
    //,@StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "ws")
}
)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WebServices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "SERVICE_ID")
    private Long id;

    @Column(name = "SERVICE_NAME")
    private String serviceName;

    @Column(name = "END_POINT")
    private String endPoint;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PROTOCOLE")
    private String protocole;

    @Column(name = "XML_REQUEST")
    private String xmlRequest;

    @Column(name = "RESPONSE_PATH")
    private String responsePath;

    @Column(name = "END_POINT_EXPOSE")
    private String endPointExpose;

    @Column(name = "SERVICE_NAME_CORRESP")
    private String serviceNameCorresp;

    @ManyToOne
    //@JsonIgnoreProperties("billerT")
    @JoinColumn(name = "BILLER_ID")
    private BillerT billerT;

    @Column(name = "TOKEN_VALUE")
    private String token;

    @OneToMany(mappedBy = "webServices", fetch = FetchType.LAZY)
    //@JoinColumn(name = "")
    private List<WebServiceParams> serviceParams;

    @Column(name = "ATRIBUTE_01")
    private String attribute01;

    @Column(name = "ATRIBUTE_02")
    private String attribute02;

    public WebServices() {
    }

    public WebServices(Long id, String serviceName, String endPoint, String status, BillerT billerT) {
        this.id = id;
        this.serviceName = serviceName;
        this.endPoint = endPoint;
        this.status = status;
        this.billerT = billerT;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEndPoint() {
        return this.endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BillerT getBillerT() {
        return this.billerT;
    }

    public void setBillerT(BillerT billerT) {
        this.billerT = billerT;
    }

    public WebServices id(Long id) {
        this.id = id;
        return this;
    }

    public WebServices serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public WebServices endPoint(String endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    public WebServices status(String status) {
        this.status = status;
        return this;
    }

    public WebServices billerT(BillerT billerT) {
        this.billerT = billerT;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WebServices)) {
            return false;
        }
        //return Objects.equals(id, webServices.id);
        return id != null && id.equals(((WebServices) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    public String getProtocole() {
        return this.protocole;
    }

    public void setProtocole(String protocole) {
        this.protocole = protocole;
    }

    public String getXmlRequest() {
        return this.xmlRequest;
    }

    public void setXmlRequest(String xmlRequest) {
        this.xmlRequest = xmlRequest;
    }

    public String getResponsePath() {
        return this.responsePath;
    }

    public void setResponsePath(String responsePath) {
        this.responsePath = responsePath;
    }

    public String getEndPointExpose() {
        return this.endPointExpose;
    }

    public void setEndPointExpose(String endPointExpose) {
        this.endPointExpose = endPointExpose;
    }

    public WebServices protocole(String protocole) {
        this.protocole = protocole;
        return this;
    }

    public WebServices xmlRequest(String xmlRequest) {
        this.xmlRequest = xmlRequest;
        return this;
    }

    public WebServices responsePath(String responsePath) {
        this.responsePath = responsePath;
        return this;
    }

    public WebServices endPointExpose(String endPointExpose) {
        this.endPointExpose = endPointExpose;
        return this;
    }


    public String getServiceNameCorresp() {
        return this.serviceNameCorresp;
    }

    public void setServiceNameCorresp(String serviceNameCorresp) {
        this.serviceNameCorresp = serviceNameCorresp;
    }


    public List<WebServiceParams> getServiceParams() {
        if(serviceParams==null) serviceParams = new ArrayList<>();
        return this.serviceParams;
    }

    public void setServiceParams(List<WebServiceParams> serviceParams) {
        this.serviceParams = serviceParams;
    }

    public WebServices serviceParams(List<WebServiceParams> serviceParams) {
        this.serviceParams = serviceParams;
        return this;
    }
    

    public String getAttribute01() {
        return this.attribute01;
    }

    public void setAttribute01(String attribute01) {
        this.attribute01 = attribute01;
    }

    public String getAttribute02() {
        return this.attribute02;
    }

    public void setAttribute02(String attribute02) {
        this.attribute02 = attribute02;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", endPoint='" + getEndPoint() + "'" +
            ", status='" + getStatus() + "'" +
            ", billerT='" + getBillerT() + "'" +
            " protocole='" + protocole + "'" +
            ", xmlRequest='" + xmlRequest + "'" +
            ", responsePath='" + responsePath + "'" +
            ", endPointExpose='" + endPointExpose + "'" +
            "}";
    }
    
}