package com.boa.api.web.rest;

import java.net.URISyntaxException;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import com.boa.api.request.BillerByCodeRequest;
import com.boa.api.request.CheckCustomerRequest;
import com.boa.api.request.CheckFactoryRequest;
import com.boa.api.request.GetAccountRequest;
import com.boa.api.request.GetBillFeesRequest;
import com.boa.api.request.GetBillRequest;
import com.boa.api.request.GetBillersRequest;
import com.boa.api.request.GetBillsByNumRequest;
import com.boa.api.request.GetBillsByRefJiramaReq;
import com.boa.api.request.GetBillsByRefRequest;
import com.boa.api.request.NotificationPaiementRequest;
import com.boa.api.request.PayementRequest;
import com.boa.api.request.PaymentReceiptRequest;
import com.boa.api.request.RecuPaiementRequest;
import com.boa.api.request.ResponseRequest;
import com.boa.api.response.BillerByCodeResponse;
import com.boa.api.response.CheckCustomerResponse;
import com.boa.api.response.CheckFactoryResponse;
import com.boa.api.response.CnssCheckFactResponse;
import com.boa.api.response.FactureDispoResponse;
import com.boa.api.response.GenericResponse;
import com.boa.api.response.GetAccountResponse;
import com.boa.api.response.GetBillFeesResponse;
import com.boa.api.response.GetBillGenericResponse;
import com.boa.api.response.GetBillersResponse;
import com.boa.api.response.GetBillsByRefResponse;
import com.boa.api.response.NotificationPaiementResponse;
import com.boa.api.response.PayementResponse;
import com.boa.api.response.RecuPaiementResponse;
import com.boa.api.response.ResponseResponse;
import com.boa.api.service.ApiService;
import com.boa.api.service.GeneriqueApiService;
import com.boa.api.service.util.ICodeDescResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * ApiResource
 */
@RestController
@RequestMapping("/api")
@Api(description = "JIRAMA", tags = "JIRAMA Web Services.")
public class ApiResource {

    private final Logger log = LoggerFactory.getLogger(ApiResource.class);
    private final ApiService apiService;
    private final GeneriqueApiService generiqueApiService;

    public ApiResource(ApiService apiService, GeneriqueApiService generiqueApiService) {
        this.apiService = apiService;
        this.generiqueApiService = generiqueApiService;
    }

    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, path = "/getBill")
    public ResponseEntity<CheckFactoryResponse> checkFactory(@RequestBody GetBillRequest billRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to getBill : {}", billRequest);
        /*
         * CheckFactoryRequest cardsRequest = new CheckFactoryRequest();
         * cardsRequest.setRefenca(billRequest.getCashingRef());
         * cardsRequest.setTelcli(billRequest.getRequierNumber());
         * cardsRequest.setVnumFact(billRequest.getBillNum());
         * cardsRequest.setLangue(billRequest.getLangue());
         * cardsRequest.setBillerCode(billRequest.getBillerCode());
         * cardsRequest.setChannel(billRequest.getChannelType()); CheckFactoryResponse
         * response = new CheckFactoryResponse(); if
         * (controleParam(cardsRequest.getVnumFact()) ||
         * controleParam(cardsRequest.getTelcli()) ||
         * controleParam(cardsRequest.getRefenca()) ||
         * controleParam(cardsRequest.getLangue()) ||
         * controleParam(cardsRequest.getChannel())) {
         * response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
         * response.setDateResponse(Instant.now());
         * response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION); return
         * ResponseEntity.ok().header("Authorization",
         * request.getHeader("Authorization")).body(response); }
         */
        /*
         * if(billRequest.getBillerCode().equalsIgnoreCase(billerJirama)){ response =
         * apiService.checkFactoryJir(cardsRequest, request); return
         * ResponseEntity.ok().header("Authorization",
         * request.getHeader("Authorization")).body(response); }else
         * if(billRequest.getBillerCode().equalsIgnoreCase(billerCnss)){
         */
        CheckFactoryResponse response = generiqueApiService.checkFactory(billRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
        /*
         * } GetBillGenericResponse billGenericResponse = new GetBillGenericResponse();
         * billGenericResponse.setCode("404");
         * billGenericResponse.setDateResponse(Instant.now());
         * billGenericResponse.setDescription("Biller not found"); return
         * ResponseEntity.ok().header("Authorization",
         * request.getHeader("Authorization")).body(billGenericResponse);
         */

    }

    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, path = "/paymentReceipt")
    public ResponseEntity<RecuPaiementResponse> recuPaiement(@RequestBody PaymentReceiptRequest paymentReceiptRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to recuPaiement : {}", paymentReceiptRequest);
        RecuPaiementRequest cardsRequest = new RecuPaiementRequest();
        cardsRequest.setRefPaie(paymentReceiptRequest.getPayBillRef());
        cardsRequest.setLangue(paymentReceiptRequest.getLangue());
        cardsRequest.setBillerCode(paymentReceiptRequest.getBillerCode());
        RecuPaiementResponse response = new RecuPaiementResponse();
        if (controleParam(cardsRequest.getRefPaie()) || controleParam(cardsRequest.getLangue())) {
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
        }
        response = apiService.recuPaiement(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
    }

    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, path = "/getBillsByRef")
    public ResponseEntity<GetBillsByRefResponse> getBillsByRef(@RequestBody GetBillsByRefRequest billsByRefRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to getBillsByRef : {}", billsByRefRequest);
        GetBillsByRefJiramaReq cardsRequest = new GetBillsByRefJiramaReq();
        cardsRequest.setRefenca(billsByRefRequest.getCashingRef());
        cardsRequest.setTelcli(billsByRefRequest.getRequierNumber());
        cardsRequest.setLangue(billsByRefRequest.getLangue());
        cardsRequest.setBillerCode(billsByRefRequest.getBillerCode());
        cardsRequest.setVref(billsByRefRequest.getBillRef());
        cardsRequest.setChannel(billsByRefRequest.getChannelType());
        GetBillsByRefResponse response = new GetBillsByRefResponse();
        /*
         * if (controleParam(cardsRequest.getVref()) ||
         * controleParam(cardsRequest.getTelcli()) ||
         * controleParam(cardsRequest.getRefenca()) ||
         * controleParam(cardsRequest.getLangue()) ||
         * controleParam(cardsRequest.getChannel())) {
         * response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
         * response.setDateResponse(Instant.now());
         * response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION); return
         * ResponseEntity.ok().header("Authorization",
         * request.getHeader("Authorization")).body(response); }
         */
        response = generiqueApiService.getBillsByRef(billsByRefRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
    }

    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, path = "/getBillsByNum")
    public ResponseEntity<GetBillsByRefResponse> getBillsByNum(@RequestBody GetBillsByNumRequest billsByNumRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to getBillsByNum : {}", billsByNumRequest);
        CheckFactoryRequest cardsRequest = new CheckFactoryRequest();
        cardsRequest.setRefenca(billsByNumRequest.getCashingRef());
        cardsRequest.setTelcli(billsByNumRequest.getRequierNumber());
        cardsRequest.setVnumFact(billsByNumRequest.getBillNum());
        cardsRequest.setLangue(billsByNumRequest.getLangue());
        cardsRequest.setBillerCode(billsByNumRequest.getBillerCode());
        cardsRequest.setChannel(billsByNumRequest.getChannelType());

        GetBillsByRefResponse response = new GetBillsByRefResponse();
        /*
         * if (controleParam(cardsRequest.getVnumFact()) ||
         * controleParam(cardsRequest.getTelcli()) ||
         * controleParam(cardsRequest.getRefenca()) ||
         * controleParam(cardsRequest.getLangue()) ||
         * controleParam(cardsRequest.getChannel())) {
         * response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
         * response.setDateResponse(Instant.now());
         * response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION); return
         * ResponseEntity.ok().header("Authorization",
         * request.getHeader("Authorization")).body(response); }
         */
        response = apiService.getBillsByNum(billsByNumRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
    }

    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE }, path = "/getBillerAccount")
    public ResponseEntity<GetAccountResponse> getBillerAccount(@RequestBody GetAccountRequest accountRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to getBillerAccount : {}", accountRequest);

        GetAccountResponse response = new GetAccountResponse();
        if (controleParam(accountRequest.getAccountType()) || controleParam(accountRequest.getBillerCode())) {
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
        }
        response = apiService.getBillerAccount(accountRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
    }

    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, path = "/checkCustomer")
    public ResponseEntity<CheckCustomerResponse> checkCustomer(@RequestBody CheckCustomerRequest checkCustomerRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to checkCustomer : {}", checkCustomerRequest);

        CheckCustomerResponse response = new CheckCustomerResponse();
        if (controleParam(checkCustomerRequest.getBillerCode()) || controleParam(checkCustomerRequest.getBillRef())
                || controleParam(checkCustomerRequest.getLangue())
                || controleParam(checkCustomerRequest.getCashingRef())) {
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
        }
        response = apiService.checkCustomer(checkCustomerRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
    }

    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, path = "/getBillers")
    public ResponseEntity<GetBillersResponse> getBillers(@RequestBody GetBillersRequest billersRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to billersRequest : {}", billersRequest);

        GetBillersResponse response = new GetBillersResponse();
        if (controleParam(billersRequest.getCountry())) {
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
        }
        response = apiService.getBillers(billersRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
    }

    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, path = "/getBillFees")
    public ResponseEntity<GetBillFeesResponse> getBillFees(@RequestBody GetBillFeesRequest billFeesRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to getBillFees : {}", billFeesRequest);

        GetBillFeesResponse response = new GetBillFeesResponse();
        if (controleParam(billFeesRequest.getBillerCode()) || controleParam(billFeesRequest.getMontant())
                || controleParam(billFeesRequest.getTypeCanal())) {
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
        }
        response = apiService.getBillFees(billFeesRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
    }

    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, path = "/payBill")
    public ResponseEntity<PayementResponse> payBill(@RequestBody PayementRequest payementRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to payBill : {}", payementRequest);

        PayementResponse response = new PayementResponse();
        if (controleParam(payementRequest.getBillerCode()) || controleParam(payementRequest.getBillNum())
        /*
         * || controleParam(payementRequest.getCashingRef())||
         * controleParam(payementRequest.getPaymentDate()) ||
         * controleParam(payementRequest.getPhoneNumber())||
         * controleParam(payementRequest.getDeviceId()) ||
         * controleParam(payementRequest.getDescription())||
         * controleParam(payementRequest.getLangue()) ||
         * controleParam(payementRequest.getCustomerAccount())||
         * controleParam(payementRequest.getBillRefTrx()) ||
         * controleParam(payementRequest.getChannelType())
         */) {
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
        }
        response = generiqueApiService.payBill(payementRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);
    }

    @PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, path = "/notificationPaiement")
    public ResponseEntity<NotificationPaiementResponse> notificationPaiement(
            @RequestBody NotificationPaiementRequest nPaiementRequest, HttpServletRequest request)
            throws URISyntaxException {
        log.info("REST request for notificationPaiement : {}", nPaiementRequest);

        NotificationPaiementResponse response = generiqueApiService.notificationPaiement(nPaiementRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(response);

    }

    /*
     * @PostMapping("/factureDisponible") public
     * ResponseEntity<FactureDispoResponse> factureDisponible(HttpServletRequest
     * request) throws URISyntaxException {
     * log.debug("REST request to recuPaiement : "); /* FactureDispoResponse
     * response = new FactureDispoResponse(); if
     * (controleParam(cardsRequest.getRefPaie())) {
     * response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
     * response.setDateResponse(Instant.now());
     * response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION); return
     * ResponseEntity.ok().header("Authorization",
     * request.getHeader("Authorization")).body(response); } FactureDispoResponse
     * response = apiService.factureDisponible(request); return
     * ResponseEntity.ok().header("Authorization",
     * request.getHeader("Authorization")).body(response); }
     */

    @PostMapping("/getResponseCode")
    public ResponseEntity<ResponseResponse> getResponseCode(@RequestBody ResponseRequest responseRequest)
            throws URISyntaxException {
        log.debug("REST request to getResponseCode : [{}] ", responseRequest);
        ResponseResponse response = apiService.getResponse(responseRequest);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/getBillerByCode")
    public ResponseEntity<BillerByCodeResponse> getBillerByCode(@RequestBody BillerByCodeRequest billerByCodeRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to getBillerByCode : [{}] ", billerByCodeRequest);
        BillerByCodeResponse response = apiService.getBillerByCode(billerByCodeRequest, request);
        return ResponseEntity.ok().body(response);
    }

    private Boolean controleParam(String param) {
        Boolean flag = false;
        if (StringUtils.isEmpty(param))
            flag = true;
        return flag;
    }

}