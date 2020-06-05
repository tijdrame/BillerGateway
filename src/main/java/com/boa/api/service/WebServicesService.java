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
}