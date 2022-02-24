package com.boa.api.service;

import javax.persistence.StoredProcedureQuery;

import com.boa.api.domain.WebServices;
import com.boa.api.repository.WebServicesRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * WebServicesService
 */
@Service
@Transactional
public class WebServicesService {
    
    private final Logger log = LoggerFactory.getLogger(WebServicesService.class);

    private final WebServicesRepository webServicesRepository;
    public WebServicesService(WebServicesRepository webServicesRepository){
            this.webServicesRepository = webServicesRepository;
    }

    public WebServices getWebServiceByParams(String billerCode, String nameWs) {
        log.info("getWebServiceByParams=== [{}, {}]", billerCode, nameWs);
        //return webServicesRepository.getWebServiceByParams(billerCode, nameWs);
        return webServicesRepository.findServiceByNameandBiller(nameWs, billerCode);
    }

    public String getPayload(String billerCode, String serviceName, String param1, String param2, String param3, String param4, String param5,
    String param6, String param7, String param8, String param9, String param10, String param11, String param12, String param13, String param14, String param15,
    String param16, String param17, String param18, String param19, String param20, String param21, String param22, String param23, String param24, 
    String param25, String param26, String param27, String param28, String param29, String param30) {
        return webServicesRepository.getPayload(billerCode, serviceName, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, 
        param12, param13, param14, param15, param16, param17, param18, param19, param20, param21, param22, param23, param24, param25, param26, param27, param28, 
        param29, param30);
    }

    public String getPayload1(String billerCode, String serviceName) {
        return webServicesRepository.getPayload1(billerCode, serviceName);
    }
}