package com.boa.api.web.rest;

import java.net.URISyntaxException;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import com.boa.api.domain.Tracking;
import com.boa.api.request.HistoPaiementClientRequest;
import com.boa.api.request.HistoPaiementFacRequest;
import com.boa.api.response.HistoPaiementClientResponse;
import com.boa.api.service.HistoriquePaiementService;
import com.boa.api.service.util.ICodeDescResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(description = "JIRAMA", tags = "JIRAMA Web Services.")
public class HistoriquePaiementResource {

    private final HistoriquePaiementService historiquePaiementService;
    private final Logger log = LoggerFactory.getLogger(ApiResource.class);

    public HistoriquePaiementResource(HistoriquePaiementService historiquePaiementService) {
        this.historiquePaiementService = historiquePaiementService;
    }

    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE },
    consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, path = "/getCustomerPayBillsHistory")
    public ResponseEntity<HistoPaiementClientResponse> histoClient(@RequestBody HistoPaiementClientRequest paiementClientRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to recuPaiement : {}", paiementClientRequest);
        
        HistoPaiementClientResponse response = new HistoPaiementClientResponse();
        if (controleParam(paiementClientRequest.getAccountCustomer()) || controleParam(paiementClientRequest.getCodeFacturier())) {
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
        }
        response = historiquePaiementService.histoClient(paiementClientRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
    }

    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE },
    consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, path = "/getBillerPayBillsHistory")
    public ResponseEntity<HistoPaiementClientResponse> histoFacturier(@RequestBody HistoPaiementFacRequest facRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to recuPaiement : {}", facRequest);
        
        HistoPaiementClientResponse response = new HistoPaiementClientResponse();
        if (controleParam(facRequest.getCodeFacturier())) {
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
        }
        response = historiquePaiementService.histofacturier(facRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
    }

    private Boolean controleParam(String param) {
        Boolean flag = false;
        if (StringUtils.isEmpty(param))
            flag = true;
        return flag;
    }

}