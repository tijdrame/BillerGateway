package com.boa.api.repository;

import com.boa.api.domain.WebServices;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

/**
 * WebServicesRepository
 */
public interface WebServicesRepository extends JpaRepository<WebServices, Long> {

    
    //@Query("select  BOA_BILM_GATEWAY_PKG.get_ws_details(JIRAMA,Check_ref_ptf) from BillerT where billeCode=JIRAMA")
    
    @Query("Select w from WebServices w where w.serviceNameCorresp=?1 and w.billerT.billerCode=?2")
    public WebServices findServiceByNameandBiller(String serviceName, String billerId);

    @Procedure(procedureName = "BOA_BILM_GATEWAY_PKG.GET_WS_DETAILS")
    public String getWebServiceByParams(@Param("P_BILLER_CODE") String billerT, 
    @Param("P_WEBSERVICE_NAME") String serviceName);
}