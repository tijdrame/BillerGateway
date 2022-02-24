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
    
    @Query("Select w from WebServices w inner join fetch w.serviceParams sp where w.serviceNameCorresp=?1 and w.billerT.billerCode=?2 order by sp.ordre")
    public WebServices findServiceByNameandBiller(String serviceName, String billerId);

    /*@Procedure(procedureName = "BOA_BILM_GATEWAY_PKG.GET_WS_DETAILS")
    public String getWebServiceByParams(@Param("P_BILLER_CODE") String billerT, 
    @Param("P_WEBSERVICE_NAME") String serviceName);*/


    @Procedure(value = "BOA_BILM_GATEWAY_PKG.GET_PAYLOAD", outputParameterName= "P_PAYLOAD")
    public String getPayload(@Param("P_BILLER_CODE") String billerCode,  @Param("P_SERVICE_NAME") String serviceName,
    @Param("P_PARAM1") String param1, @Param("P_PARAM2")String param2, @Param("P_PARAM3") String param3, @Param("P_PARAM4")String param4,
    @Param("P_PARAM5") String param5, @Param("P_PARAM6")String param6, @Param("P_PARAM7") String param7, @Param("P_PARAM8")String param8,
    @Param("P_PARAM9") String param9, @Param("P_PARAM10")String param10, @Param("P_PARAM11") String param11, @Param("P_PARAM12")String param12,
    @Param("P_PARAM13") String param13, @Param("P_PARAM14")String param14, @Param("P_PARAM15") String param15, @Param("P_PARAM16")String param16,
    @Param("P_PARAM17") String param17, @Param("P_PARAM18")String param18, @Param("P_PARAM19") String param19, @Param("P_PARAM20")String param20,
    @Param("P_PARAM21") String param21, @Param("P_PARAM22")String param22, @Param("P_PARAM23") String param23, @Param("P_PARAM24")String param24,
    @Param("P_PARAM25") String param25, @Param("P_PARAM26")String param26, @Param("P_PARAM27") String param27, @Param("P_PARAM28")String param28,
    @Param("P_PARAM29") String param29, @Param("P_PARAM30")String param30///, @Param("P_PAYLOAD") String payload //@Param("P_PARAM32")String param32
    );

    @Procedure( value = "BOA_BILM_GATEWAY_PKG.GET_PAYLOAD1", outputParameterName= "P_PAYLOAD")
    public String getPayload1(@Param("P_BILLER_CODE") String billerCode,  @Param("P_SERVICE_NAME") String serviceName
    );
}