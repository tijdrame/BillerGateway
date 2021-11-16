package com.boa.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import com.boa.api.config.ApplicationProperties;
import com.boa.api.domain.BillerT;
import com.boa.api.domain.ParamFiliale;
import com.boa.api.domain.Tracking;
import com.boa.api.domain.TransactionGlobal;
import com.boa.api.repository.ParamFilialeRepository;
import com.boa.api.request.BillerByCodeRequest;
import com.boa.api.request.CheckCustomerRequest;
import com.boa.api.request.CheckFactoryRequest;
import com.boa.api.request.GetAccountRequest;
import com.boa.api.request.GetBillFeesRequest;
import com.boa.api.request.GetBillersByCategRequest;
import com.boa.api.request.GetBillersRequest;
import com.boa.api.request.GetBillsByNumRequest;
import com.boa.api.request.GetBillsByRefJiramaReq;
import com.boa.api.request.PayementRequest;
import com.boa.api.request.RecuPaiementRequest;
import com.boa.api.request.ResponseRequest;
import com.boa.api.response.AnnulationPaiement;
import com.boa.api.response.Biller;
import com.boa.api.response.BillerByCodeResponse;
import com.boa.api.response.CategBillersResponse;
import com.boa.api.response.CheckCustomerResponse;
import com.boa.api.response.CheckFactoryResponse;
import com.boa.api.response.ExceptionResponse;
import com.boa.api.response.FactureDispoResponse;
import com.boa.api.response.FacuresDispo;
import com.boa.api.response.GenericResponse;
import com.boa.api.response.GetAccountResponse;
import com.boa.api.response.GetBillFeesResponse;
import com.boa.api.response.GetBillersResponse;
import com.boa.api.response.GetBillsByRefResponse;
import com.boa.api.response.ItemResp;
import com.boa.api.response.PayementResponse;
import com.boa.api.response.RecuPaiementResponse;
import com.boa.api.response.ResponseResponse;
import com.boa.api.service.util.ICodeDescResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * ApiService
 */
@Service
@Transactional
public class ApiService {

    private final Logger log = LoggerFactory.getLogger(ApiService.class);

    private final ParamFilialeRepository paramFilialeRepository;
    private final TrackingService trackingService;
    private final UserService userService;
    private final TransactionGlobalService transactionGlobalService;
    private final BillerTService billerTService;
    private final ApplicationProperties applicationProperties;

    public ApiService(ParamFilialeRepository paramFilialeRepository, TrackingService trackingService,
            UserService userService, TransactionGlobalService transactionGlobalService, BillerTService billerTService,
            ApplicationProperties applicationProperties) {
        this.paramFilialeRepository = paramFilialeRepository;
        this.trackingService = trackingService;
        this.userService = userService;
        this.transactionGlobalService = transactionGlobalService;
        this.billerTService = billerTService;
        this.applicationProperties = applicationProperties;
    }

    // getBill
    public CheckFactoryResponse checkFactory(CheckFactoryRequest cardsRequest, HttpServletRequest request) {
        log.info("enter in checkFactory [{}]", cardsRequest);
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("checkFactory");
        Tracking tracking = new Tracking();
        CheckFactoryResponse genericResponse = new CheckFactoryResponse();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");

        if (filiale == null) {
            genericResponse = (CheckFactoryResponse) clientAbsent(genericResponse, tracking, "getBill",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);

            return genericResponse;
        }
        OutputStream os = null;
        try {

            log.info("end point wso2== [{}]", filiale.getEndPoint());
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setRequestProperty("Accept", "application/xml");

            StringBuilder builder = new StringBuilder();
            /*
             * builder.append("<send_request><request>"); builder.append(
             * "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:WebservicePlus\">"
             * ); builder.append("<soapenv:Body><urn:Check_facture_ptf><urn:vnum_fact>" +
             * cardsRequest.getVnumFact() + "</urn:vnum_fact>");
             * builder.append("<urn:vRefenca>" + cardsRequest.getRefenca() +
             * "</urn:vRefenca><urn:telcli>" + cardsRequest.getTelcli() + "</urn:telcli>");
             * builder.append("</urn:Check_facture_ptf></soapenv:Body></soapenv:Envelope>");
             * builder.append("</request>"); builder.append( "<url_link>" +
             * applicationProperties.getJirama() +
             * "</url_link><url_content>xml</url_content>");
             * builder.append("</send_request>");
             */
            builder.append("<send_request><request>");
            builder.append(
                    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:WebservicePlus\">");
            builder.append("<soapenv:Body><urn:Check_fact_cbarre><urn:vnum_fact>" + cardsRequest.getVnumFact()
                    + "</urn:vnum_fact>");
            builder.append("<urn:telcli>" + cardsRequest.getTelcli() + "</urn:telcli>");
            builder.append("</urn:Check_fact_cbarre></soapenv:Body></soapenv:Envelope>");
            builder.append("</request>");
            builder.append(
                    "<url_link>" + applicationProperties.getJirama() + "</url_link><url_content>xml</url_content>");
            builder.append("</send_request>");

            /*
             * String jsonString = ""; jsonString = new JSONObject().put("vnum_fact",
             * cardsRequest.getVnumFact()) .put("refenca",
             * cardsRequest.getRefenca()).put("telcli",
             * cardsRequest.getTelcli()).toString();
             */
            log.info("execute check Factory req ====== [{}]", builder.toString());
            tracking.setRequestTr(builder.toString());
            os = conn.getOutputStream();
            byte[] postDataBytes = builder.toString().getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            // log.info("resp code [{}]", conn.);
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                // System.out.println("err=" + result);
                log.info("resp ===== [{}]", result);
                obj = new JSONObject(result).getJSONObject("response").getJSONObject("Envelope").getJSONObject("Body");
                log.info("test== [{}]", obj.getJSONObject("Check_fact_cbarreResponse"));
                if (obj.getJSONObject("Check_fact_cbarreResponse")
                        // obj.getJSONObject("Check_facture_ptfResponse")
                        .toString().contains("P000")
                        || obj.getJSONObject("Check_fact_cbarreResponse").toString().contains("N000")
                        || obj.getJSONObject("Check_fact_cbarreResponse").toString().contains("?")) {
                    JSONObject errObj = obj.getJSONObject("Check_fact_cbarreResponse");
                    ExceptionResponse exceptionResponse = new ExceptionResponse();
                    log.error("msg err = [{}]", errObj.get("Check_fact_cbarreResult"));
                    String[] tabErr = new String[2];

                    tabErr = errObj.get("Check_fact_cbarreResult").toString().split("#");
                    exceptionResponse.setNumber(tabErr[0].trim());
                    exceptionResponse.setDescription(tabErr.length==2 ? tabErr[1].trim() : "");
                    ResponseRequest responseRequest = new ResponseRequest();
                    responseRequest.setBillerCode(cardsRequest.getBillerCode().toUpperCase());
                    responseRequest.setLangue(cardsRequest.getLangue());
                    responseRequest.setRetourCode(tabErr[0].trim());
                    responseRequest.setServiceName(ICodeDescResponse.SERVICE_CHECK_FACTURE_PTF);
                    ResponseResponse responseResponse = getResponse(responseRequest);
                    genericResponse.setCode((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.ECHEC_CODE
                            : responseResponse.getCode());
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse
                            .setDescription((responseResponse == null || responseResponse.getCode().equals("0000"))
                                    ? ICodeDescResponse.ECHEC_DESCRIPTION
                                    : responseResponse.getDescription());

                    genericResponse.setExceptionResponse(exceptionResponse);
                    // .getJSONObject("Fault").getString("faultstring"));
                    tracking.setCodeResponse((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.ECHEC_CODE
                            : responseResponse.getCode());
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("getBill");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    // trackingService.save(tracking);
                    os.close();
                    // return genericResponse;
                } else if (obj.toString().contains("Check_fact_cbarreResult")) {
                    log.info("succ == [{}]", obj.toString());
                    String succesOb = obj.getJSONObject("Check_fact_cbarreResponse")
                            .getString("Check_fact_cbarreResult");
                    log.info("succc === [{}]", succesOb.toString());
                    String[] tabSuccess = succesOb.split("#");
                    Double amount = Double.valueOf(tabSuccess[3].trim()) * 100;
                    genericResponse.setBillAmount(amount);
                    genericResponse.setCustomerName(tabSuccess[5].trim());
                    genericResponse.setRequierNumber(tabSuccess[4].trim());
                    genericResponse.setBillNum(tabSuccess[0].trim());
                    genericResponse.setSessionNum(tabSuccess[6].trim().replace("%", ""));
                    genericResponse.setCustumerRef(tabSuccess[1].trim());
                    genericResponse.setBillDate(tabSuccess[2].trim());

                    ResponseRequest responseRequest = new ResponseRequest();
                    responseRequest.setBillerCode(cardsRequest.getBillerCode());
                    responseRequest.setLangue(cardsRequest.getLangue());
                    responseRequest.setRetourCode("200");
                    responseRequest.setServiceName(ICodeDescResponse.SERVICE_CHECK_FACTURE_PTF);

                    ResponseResponse responseResponse = getResponse(responseRequest);

                    genericResponse = (CheckFactoryResponse) clientAbsent(genericResponse, tracking, "getBill",
                            (responseResponse == null || responseResponse.getCode().equals("0000"))
                                    ? ICodeDescResponse.SUCCES_CODE
                                    : responseResponse.getCode(),
                            (responseResponse == null || responseResponse.getCode().equals("0000"))
                                    ? ICodeDescResponse.SUCCES_DESCRIPTION
                                    : responseResponse.getDescription(),
                            request.getRequestURI(), tab[1]);

                    GetBillFeesRequest billFeesRequest = new GetBillFeesRequest();
                    billFeesRequest.setBillerCode(cardsRequest.getBillerCode());
                    billFeesRequest.setMontant(genericResponse.getBillAmount().toString());
                    billFeesRequest.setTypeCanal(cardsRequest.getChannel());
                    GetBillFeesResponse billFeesResponse = getBillFees(billFeesRequest, request);
                    if (billFeesResponse != null)
                        genericResponse.setFeeAmount(billFeesResponse.getMontantFrais().doubleValue() * 100);

                    os.close();
                    // return genericResponse;
                }
            } else {
                // resp != 200
                genericResponse = (CheckFactoryResponse) clientAbsent(genericResponse, tracking, "getBill",
                        ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.ECHEC_DESCRIPTION, request.getRequestURI(),
                        tab[1]);
                os.close();
                // return genericResponse;
            }

        } catch (

        Exception e) {
            try {
                os.close();
            } catch (IOException e1) {
                log.error("errorrr== [{}]", e1.getMessage());
            }
            log.error("errorrr== [{}]", e.getMessage());
            genericResponse = (CheckFactoryResponse) clientAbsent(genericResponse, tracking, "getBill",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.FILIALE_ABSENT_DESC + e.getMessage(),
                    request.getRequestURI(), tab[1]);
        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public RecuPaiementResponse recuPaiement(RecuPaiementRequest cardsRequest, HttpServletRequest request) {
        log.info("Enter in recuPaiement == [{}]", cardsRequest);
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("checkFactory");
        Tracking tracking = new Tracking();
        RecuPaiementResponse genericResponse = new RecuPaiementResponse();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");

        if (filiale == null) {
            genericResponse = (RecuPaiementResponse) clientAbsent(genericResponse, tracking, "paymentReceipt",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);

            return genericResponse;
        }
        OutputStream os = null;
        try {

            CheckFactoryRequest checkFactoryRequest = new CheckFactoryRequest();
            checkFactoryRequest.setBillerCode(cardsRequest.getBillerCode());
            checkFactoryRequest.setLangue(cardsRequest.getLangue());
            checkFactoryRequest.setRefenca(cardsRequest.getCashingRef());
            checkFactoryRequest.setVnumFact(cardsRequest.getBillNum());
            CheckFactoryResponse checkFactoryResponse = checkFactory(checkFactoryRequest, request);
            log.info("CheckFactoryResponse <==> [{}]", checkFactoryResponse.toString());
            if (checkFactoryResponse == null || (checkFactoryResponse.getExceptionResponse() != null
                    && !checkFactoryResponse.getExceptionResponse().getNumber().contains("P0000")
                    && checkFactoryResponse.getBillAmount() == null)) {
                genericResponse = (RecuPaiementResponse) clientAbsent(genericResponse, tracking,
                        "getBill in recu facture", ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.FACTURE_NON_TROUVE,
                        request.getRequestURI(), tab[1]);
                return genericResponse;
            }
            String numSession = checkFactoryResponse.getSessionNum() != null
                    ? checkFactoryResponse.getSessionNum().trim().replace("%", "")
                    : checkFactoryResponse.getExceptionResponse().getDescription().trim().replace("%", "");
            log.info("after test prepare to send with numSession= [{}]", numSession);
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setRequestProperty("Accept", "application/xml");

            StringBuilder builder = new StringBuilder();
            builder.append("<send_request><request>");
            builder.append(
                    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:WebservicePlus\">");
            builder.append("<soapenv:Body><urn:Recu_paiement><urn:Ref_paie>" + numSession + "</urn:Ref_paie>");
            builder.append("</urn:Recu_paiement></soapenv:Body></soapenv:Envelope>");
            builder.append("</request>");
            builder.append(
                    "<url_link>" + applicationProperties.getJirama() + "</url_link><url_content>xml</url_content>");
            builder.append("</send_request>");

            log.info(" req xml for Recu_paiement ====== [{}]", builder.toString());
            tracking.setRequestTr(builder.toString());
            os = conn.getOutputStream();
            byte[] postDataBytes = builder.toString().getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("result ===== [{}]", result);
                obj = new JSONObject(result).getJSONObject("response").getJSONObject("Envelope").getJSONObject("Body");
                log.info("obj res ===== [{}]", obj.toString());
                if (obj.getJSONObject("Recu_paiementResponse").getString("Recu_paiementResult").contains("RP000")) {
                    JSONObject errObj = obj.getJSONObject("Recu_paiementResponse");
                    ExceptionResponse exceptionResponse = new ExceptionResponse();
                    log.error("msg err = [{}]", errObj.get("Recu_paiementResult"));
                    String[] tabErr = new String[2];
                    String errResult = "";
                    ResponseRequest responseRequest = new ResponseRequest();
                    if (errObj.getString("Recu_paiementResult").contains("#")) {
                        tabErr = errObj.get("Recu_paiementResult").toString().split("#");
                        responseRequest.setRetourCode(tabErr[0].trim());
                    } else {
                        errResult = errObj.getString("Recu_paiementResult");
                        responseRequest.setRetourCode(errResult);
                    }

                    responseRequest.setBillerCode(cardsRequest.getBillerCode().toUpperCase());
                    responseRequest.setLangue(cardsRequest.getLangue());
                    responseRequest.setServiceName(ICodeDescResponse.SERVICE_RECU_PAIEMENT);
                    ResponseResponse responseResponse = getResponse(responseRequest);

                    exceptionResponse.setNumber(tabErr[0] != null ? tabErr[0] : errResult);
                    exceptionResponse.setDescription(tabErr[1] != null ? tabErr[1] : "");
                    genericResponse.setCode((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.ECHEC_CODE
                            : responseResponse.getCode());
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse
                            .setDescription((responseResponse == null || responseResponse.getCode().equals("0000"))
                                    ? ICodeDescResponse.ECHEC_DESCRIPTION
                                    : responseResponse.getDescription());

                    genericResponse.setExceptionResponse(exceptionResponse);
                    // .getJSONObject("Fault").getString("faultstring"));
                    tracking.setCodeResponse((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.ECHEC_CODE
                            : responseResponse.getCode());
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("paymentReceipt");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    // trackingService.save(tracking);
                    os.close();
                    // return genericResponse;
                } else if (!obj.getJSONObject("Recu_paiementResponse").toString().contains("RP000")) {
                    log.info("succ == [{}]", obj.toString());
                    String succesOb = obj.getJSONObject("Recu_paiementResponse").getString("Recu_paiementResult");
                    log.info("succc === [{}]", succesOb.toString());
                    String[] tabSuccess = succesOb.split("#");

                    ResponseRequest responseRequest = new ResponseRequest();
                    responseRequest.setBillerCode(cardsRequest.getBillerCode());
                    responseRequest.setLangue(cardsRequest.getLangue());
                    responseRequest.setRetourCode(ICodeDescResponse.SUCCES_CODE);
                    responseRequest.setServiceName(ICodeDescResponse.SERVICE_RECU_PAIEMENT);
                    ResponseResponse responseResponse = getResponse(responseRequest);

                    genericResponse.setDatePaiement(tabSuccess[0].trim());
                    genericResponse.setNumPaiement(tabSuccess[1].trim());
                    genericResponse.setCode((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.SUCCES_CODE
                            : responseResponse.getCode());
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse
                            .setDescription((responseResponse == null || responseResponse.getCode().equals("0000"))
                                    ? ICodeDescResponse.SUCCES_DESCRIPTION
                                    : responseResponse.getDescription());

                    tracking.setCodeResponse((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.SUCCES_CODE
                            : responseResponse.getCode());
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("paymentReceipt");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());

                    os.close();
                }
            } else {
                // resp != 200
                genericResponse = (RecuPaiementResponse) clientAbsent(genericResponse, tracking, "paymentReceipt",
                        ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.ECHEC_DESCRIPTION, request.getRequestURI(),
                        tab[1]);
                os.close();
                // return genericResponse;
            }

        } catch (

        Exception e) {
            try {
                os.close();
            } catch (IOException e1) {
                log.error("errorrr== [{}]", e1.getMessage());
            }
            log.error("errorrr== [{}]", e.getMessage());
            genericResponse = (RecuPaiementResponse) clientAbsent(genericResponse, tracking, "paymentReceipt",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.FILIALE_ABSENT_DESC + e.getMessage(),
                    request.getRequestURI(), tab[1]);
            // return genericResponse;

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public CheckCustomerResponse checkCustomer(CheckCustomerRequest checkCustomerRequest, HttpServletRequest request) {
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("checkFactory");
        Tracking tracking = new Tracking();
        CheckCustomerResponse genericResponse = new CheckCustomerResponse();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");

        if (filiale == null) {
            genericResponse = (CheckCustomerResponse) clientAbsent(genericResponse, tracking, "checkCustomer",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);

            return genericResponse;
        }
        OutputStream os = null;
        try {

            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setRequestProperty("Accept", "application/xml");

            StringBuilder builder = new StringBuilder();
            builder.append("<send_request><request>");
            builder.append(
                    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:WebservicePlus\">");
            builder.append(
                    "<soapenv:Body><urn:Check_client><urn:vref>" + checkCustomerRequest.getBillRef() + "</urn:vref>");
            builder.append("<urn:vRefenca>" + checkCustomerRequest.getCashingRef() + "</urn:vRefenca>");
            builder.append("</urn:Check_client></soapenv:Body></soapenv:Envelope>");
            builder.append("</request>");
            builder.append(
                    "<url_link>" + applicationProperties.getJirama() + "</url_link><url_content>xml</url_content>");
            builder.append("</send_request>");

            log.info(" req xml ====== [{}]", builder.toString());
            tracking.setRequestTr(builder.toString());
            os = conn.getOutputStream();
            byte[] postDataBytes = builder.toString().getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("result ===== [{}]", result);
                obj = new JSONObject(result).getJSONObject("response").getJSONObject("Envelope").getJSONObject("Body");
                log.info("obj res ===== [{}]", obj.toString());
                if (obj.getJSONObject("Check_clientResponse").getString("Check_clientResult").contains("C000")) {
                    JSONObject errObj = obj.getJSONObject("Check_clientResponse");
                    ExceptionResponse exceptionResponse = new ExceptionResponse();
                    log.error("msg err = [{}]", errObj.get("Check_clientResult"));
                    String[] tabErr = new String[2];

                    tabErr = errObj.get("Check_clientResult").toString().split("#");
                    ResponseRequest responseRequest = new ResponseRequest();
                    responseRequest.setBillerCode(checkCustomerRequest.getBillerCode().toUpperCase());
                    responseRequest.setLangue(checkCustomerRequest.getLangue());
                    responseRequest.setRetourCode(tabErr[0].trim());
                    responseRequest.setServiceName(ICodeDescResponse.SERVICE_CHECK_CLIENT);
                    ResponseResponse responseResponse = getResponse(responseRequest);

                    // exceptionResponse.setNumber(tabErr[0]);
                    // exceptionResponse.setDescription(tabErr[1]);
                    genericResponse.setCode((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.ECHEC_CODE
                            : responseResponse.getCode());
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse
                            .setDescription((responseResponse == null || responseResponse.getCode().equals("0000"))
                                    ? ICodeDescResponse.ECHEC_DESCRIPTION
                                    : responseResponse.getDescription());

                    // genericResponse.setExceptionResponse(exceptionResponse);
                    // .getJSONObject("Fault").getString("faultstring"));
                    tracking.setCodeResponse((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.ECHEC_CODE
                            : responseResponse.getCode());
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("checkClient");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    // trackingService.save(tracking);
                    os.close();
                    // return genericResponse;
                } else if (!obj.getJSONObject("Check_clientResponse").getString("Check_clientResult")
                        .contains("C000")) {
                    log.info("succ == [{}]", obj.toString());
                    String succesOb = obj.getJSONObject("Check_clientResponse").getString("Check_clientResult");
                    log.info("succc === [{}]", succesOb.toString());
                    String[] tabSuccess = succesOb.split("-");

                    ResponseRequest responseRequest = new ResponseRequest();
                    responseRequest.setBillerCode(checkCustomerRequest.getBillerCode());
                    responseRequest.setLangue(checkCustomerRequest.getLangue());
                    responseRequest.setRetourCode(ICodeDescResponse.SUCCES_CODE);
                    responseRequest.setServiceName(ICodeDescResponse.SERVICE_CHECK_CLIENT);
                    ResponseResponse responseResponse = getResponse(responseRequest);

                    genericResponse.setReferenceClient(tabSuccess[0].trim());
                    genericResponse.setReferenceEncaissement(tabSuccess[1].trim());
                    genericResponse.setCode((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.SUCCES_CODE
                            : responseResponse.getCode());
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse
                            .setDescription((responseResponse == null || responseResponse.getCode().equals("0000"))
                                    ? ICodeDescResponse.SUCCES_DESCRIPTION
                                    : responseResponse.getDescription());

                    tracking.setCodeResponse((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.SUCCES_CODE
                            : responseResponse.getCode());
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("checkClient");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());

                    os.close();
                }
            } else {
                // resp != 200
                genericResponse = (CheckCustomerResponse) clientAbsent(genericResponse, tracking, "checkClient",
                        ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.ECHEC_DESCRIPTION, request.getRequestURI(),
                        tab[1]);
                os.close();
                // return genericResponse;
            }

        } catch (

        Exception e) {
            try {
                os.close();
            } catch (IOException e1) {
                log.error("errorrr== [{}]", e1.getMessage());
            }
            log.error("errorrr== [{}]", e.getMessage());
            genericResponse = (CheckCustomerResponse) clientAbsent(genericResponse, tracking, "checkClient",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.FILIALE_ABSENT_DESC + e.getMessage(),
                    request.getRequestURI(), tab[1]);
            // return genericResponse;

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public GetBillsByRefResponse getBillsByRef(GetBillsByRefJiramaReq cardsRequest, HttpServletRequest request) {
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("checkFactory");
        Tracking tracking = new Tracking();
        GetBillsByRefResponse genericResponse = new GetBillsByRefResponse();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");

        if (filiale == null) {
            genericResponse = (GetBillsByRefResponse) clientAbsent(genericResponse, tracking, "getBillsByRef",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);

            return genericResponse;
        }
        OutputStream os = null;
        try {

            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setRequestProperty("Accept", "application/xml");

            StringBuilder builder = new StringBuilder();
            builder.append("<send_request><request>");
            builder.append(
                    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:WebservicePlus\">");
            builder.append("<soapenv:Body><urn:Check_ref_ptf><urn:vref>" + cardsRequest.getVref() + "</urn:vref>");
            builder.append("<urn:vRefenca>" + cardsRequest.getRefenca() + "</urn:vRefenca><urn:telcli>"
                    + cardsRequest.getTelcli() + "</urn:telcli>");
            builder.append("</urn:Check_ref_ptf></soapenv:Body></soapenv:Envelope>");
            builder.append("</request>");
            builder.append(
                    "<url_link>" + applicationProperties.getJirama() + "</url_link><url_content>xml</url_content>");
            builder.append("</send_request>");
            /*
             * String jsonString = ""; jsonString = new JSONObject().put("vnum_fact",
             * cardsRequest.getVnumFact()) .put("refenca",
             * cardsRequest.getRefenca()).put("telcli",
             * cardsRequest.getTelcli()).toString();
             */
            log.info(" req xml ====== [{}]", builder.toString());
            tracking.setRequestTr(builder.toString());
            os = conn.getOutputStream();
            byte[] postDataBytes = builder.toString().getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            // log.info("resp code [{}]", conn.);
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                // System.out.println("err=" + result);
                log.info("result ===== [{}]", result);
                obj = new JSONObject(result).getJSONObject("response").getJSONObject("Envelope").getJSONObject("Body");
                log.info("obj res ===== [{}]", obj.toString());
                // log.info("test== [{}]",
                // obj.getJSONObject("response").getJSONObject("Body").getJSONObject("Check_facture_ptfResponse"));
                if (obj.getJSONObject("Check_ref_ptfResponse").getString("Check_ref_ptfResult").contains("R000")) {
                    JSONObject errObj = obj.getJSONObject("Check_ref_ptfResponse");
                    ExceptionResponse exceptionResponse = new ExceptionResponse();
                    log.error("msg err = [{}]", errObj.get("Check_ref_ptfResult"));
                    String[] tabErr = new String[2];

                    tabErr = errObj.get("Check_ref_ptfResult").toString().split("#");

                    ResponseRequest responseRequest = new ResponseRequest();
                    responseRequest.setBillerCode(cardsRequest.getBillerCode().toUpperCase());
                    responseRequest.setLangue(cardsRequest.getLangue());
                    responseRequest.setRetourCode(tabErr[0].trim());
                    responseRequest.setServiceName(ICodeDescResponse.SERVICE_CHECK_REF_PTF);

                    ResponseResponse responseResponse = getResponse(responseRequest);

                    exceptionResponse.setNumber(tabErr[0]);
                    exceptionResponse
                            .setDescription((responseResponse == null || responseResponse.getCode().equals("0000"))
                                    ? ICodeDescResponse.ECHEC_DESCRIPTION
                                    : responseResponse.getDescription());
                    genericResponse.setCode((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.ECHEC_CODE
                            : responseResponse.getCode());
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse
                            .setDescription((responseResponse == null || responseResponse.getCode().equals("0000"))
                                    ? ICodeDescResponse.ECHEC_DESCRIPTION
                                    : responseResponse.getDescription());

                    genericResponse.setExceptionResponse(exceptionResponse);
                    // .getJSONObject("Fault").getString("faultstring"));
                    tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("getBillsByRef");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    os.close();
                } else if (!obj.getJSONObject("Check_ref_ptfResponse").getString("Check_ref_ptfResult")
                        .contains("R000")) {
                    log.info("succ == [{}]", obj.toString());
                    String succesOb = removeLastChar(
                            obj.getJSONObject("Check_ref_ptfResponse").getString("Check_ref_ptfResult"));

                    log.info("success === [{}]", succesOb);
                    // succesOb = succesOb.replaceAll("##", "#");
                    String[] tabSuccess = succesOb.split(Pattern.quote("$"));
                    log.info("taill tab = [{}]", tabSuccess.length);
                    for (int i = 0; i < tabSuccess.length; i++) {
                        String[] tabTemp = tabSuccess[i].split("#");
                        ItemResp itemResp = new ItemResp();
                        itemResp.setBillAmount(Double.valueOf(tabTemp[3]) * 100);
                        itemResp.setBillDate(tabTemp[2]);
                        itemResp.setBillNum(tabTemp[0]);
                        itemResp.setRequierNumber(tabTemp[4]);
                        itemResp.setCustomerName(tabTemp[6]);
                        itemResp.setCustumerRef(tabTemp[1]);
                        itemResp.setSessionNum(tabTemp[5]);

                        GetBillFeesRequest billFeesRequest = new GetBillFeesRequest();
                        billFeesRequest.setBillerCode(cardsRequest.getBillerCode());
                        billFeesRequest.setMontant(itemResp.getBillAmount().toString());
                        billFeesRequest.setTypeCanal(cardsRequest.getChannel());
                        GetBillFeesResponse billFeesResponse = getBillFees(billFeesRequest, request);
                        if (billFeesResponse != null)
                            itemResp.setFeeAmount(billFeesResponse.getMontantFrais().doubleValue() * 100);

                        genericResponse.getBillList().add(itemResp);
                    }
                    log.info("before call responseRequest [{}]", genericResponse);
                    ResponseRequest responseRequest = new ResponseRequest();
                    responseRequest.setBillerCode(cardsRequest.getBillerCode());
                    responseRequest.setLangue(cardsRequest.getLangue());
                    responseRequest.setRetourCode(ICodeDescResponse.SUCCES_CODE);
                    responseRequest.setServiceName(ICodeDescResponse.SERVICE_CHECK_REF_PTF);
                    ResponseResponse responseResponse = getResponse(responseRequest);

                    genericResponse.setCode((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.SUCCES_CODE
                            : responseResponse.getCode());
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse
                            .setDescription((responseResponse == null || responseResponse.getCode().equals("0000"))
                                    ? ICodeDescResponse.SUCCES_DESCRIPTION
                                    : responseResponse.getDescription());

                    tracking.setCodeResponse((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.SUCCES_CODE
                            : responseResponse.getCode());
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("getBillsByRef");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());

                    os.close();
                }
            } else {
                genericResponse = (GetBillsByRefResponse) clientAbsent(genericResponse, tracking, "getBillsByRef",
                        ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.ECHEC_DESCRIPTION, request.getRequestURI(),
                        tab[1]);
                os.close();
                // return genericResponse;
            }

        } catch (

        Exception e) {
            /*try {
                os.close();
            } catch (IOException e1) {
                log.error("errorrr== [{}]", e1.getMessage());
            }*/
            log.error("errorrr== [{}]", e.getMessage());
            genericResponse = (GetBillsByRefResponse) clientAbsent(genericResponse, tracking, "getBillsByRef",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.FILIALE_ABSENT_DESC + e.getMessage(),
                    request.getRequestURI(), tab[1]);

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public GetBillsByRefResponse getBillsByNum(GetBillsByNumRequest getBillsByNumRequest, HttpServletRequest request) {
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        CheckFactoryRequest cardsRequest = new CheckFactoryRequest();
        cardsRequest.setBillerCode(getBillsByNumRequest.getBillerCode());
        cardsRequest.setLangue(getBillsByNumRequest.getLangue());
        cardsRequest.setRefenca(getBillsByNumRequest.getCashingRef());
        cardsRequest.setTelcli(getBillsByNumRequest.getRequierNumber());
        cardsRequest.setVnumFact(getBillsByNumRequest.getBillNum());
        cardsRequest.setChannel(getBillsByNumRequest.getChannelType());
        GetBillsByRefResponse billsByRefResponse = new GetBillsByRefResponse();
        CheckFactoryResponse checkFactoryResponse = checkFactory(cardsRequest, request);
        if (checkFactoryResponse != null && checkFactoryResponse.getBillNum() != null) {
            GetBillsByRefJiramaReq billsByRefJiramaReq = new GetBillsByRefJiramaReq();
            billsByRefJiramaReq.setRefenca(cardsRequest.getRefenca());
            billsByRefJiramaReq.setTelcli(cardsRequest.getTelcli());
            billsByRefJiramaReq.setVref(checkFactoryResponse.getCustumerRef());
            billsByRefJiramaReq.setBillerCode(getBillsByNumRequest.getBillerCode());
            billsByRefJiramaReq.setLangue(getBillsByNumRequest.getLangue());
            billsByRefJiramaReq.setChannel(getBillsByNumRequest.getChannelType());
            billsByRefResponse = getBillsByRef(billsByRefJiramaReq, request);
            Tracking tracking = new Tracking();
            tracking.setRequestTr(cardsRequest.toString());
            tracking.setCodeResponse(billsByRefResponse.getCode() + "");
            tracking.setDateResponse(Instant.now());
            tracking.setEndPointTr("getBillsByNum");
            tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
            tracking.setResponseTr(checkFactoryResponse.toString());
            tracking.setTokenTr(tab[1]);
            tracking.setDateRequest(Instant.now());
            trackingService.save(tracking);
            return billsByRefResponse;
        }
        billsByRefResponse.setCode(
                checkFactoryResponse != null ? checkFactoryResponse.getCode() : ICodeDescResponse.ECHEC_CODE + "");
        billsByRefResponse.setDateResponse(Instant.now());
        billsByRefResponse.setDescription(checkFactoryResponse != null ? checkFactoryResponse.getDescription()
                : ICodeDescResponse.ECHEC_DESCRIPTION + "");

        Tracking tracking = new Tracking();
        tracking.setCodeResponse(
                checkFactoryResponse != null ? checkFactoryResponse.getCode() : ICodeDescResponse.ECHEC_CODE + "");
        tracking.setDateResponse(Instant.now());
        tracking.setEndPointTr("getBillsByNum");
        tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
        tracking.setResponseTr(checkFactoryResponse.toString());
        tracking.setTokenTr(tab[1]);
        tracking.setDateRequest(Instant.now());
        trackingService.save(tracking);
        return billsByRefResponse;
    }

    public GetAccountResponse getBillerAccount(GetAccountRequest accountRequest, HttpServletRequest request) {
        log.info("enter in getBillerAccount [{}]", accountRequest);
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("getBillerAccount");
        Tracking tracking = new Tracking();
        GetAccountResponse genericResponse = new GetAccountResponse();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");

        if (filiale == null) {
            genericResponse = (GetAccountResponse) clientAbsent(genericResponse, tracking, "getBillerAccount",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);

            return genericResponse;
        }
        OutputStream os = null;
        try {

            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            String jsonString = "";
            jsonString = new JSONObject().put("code_biller", accountRequest.getBillerCode())
                    .put("account_type", accountRequest.getAccountType()).toString();

            log.info(" req json ====== [{}]", jsonString);
            tracking.setRequestTr(jsonString);
            os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("result ===== [{}]", result);
                obj = new JSONObject(result).getJSONObject("get_billeraccount_response").getJSONObject("response");
                log.info("obj res ===== [{}]", obj.toString());
                if (obj.toString().contains("rien")) {
                    JSONObject errObj = new JSONObject(result).getJSONObject("get_billeraccount_response")
                            .getJSONObject("response");
                    log.error("msg err = [{}]", errObj.toString());

                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);

                    tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE);
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("getBillerAccount");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    os.close();
                } else if (!obj.toString().contains("rien")) {
                    log.info("succ == [{}]", obj.toString());

                    log.info("success ===  [{}]", obj.toString());

                    genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                    genericResponse.setNumAccount(obj.getString("CMSG"));
                    tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE);
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("getBillerAccount");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());

                    os.close();
                }
            } else {
                genericResponse = (GetAccountResponse) clientAbsent(genericResponse, tracking, "getBillerAccount",
                        ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.ECHEC_DESCRIPTION, request.getRequestURI(),
                        tab[1]);
                os.close();
            }

        } catch (Exception e) {
            try {
                os.close();
            } catch (IOException e1) {
                log.error("errorrr== [{}]", e1.getMessage());
            }
            log.error("errorrr== [{}]", e.getMessage());
            genericResponse = (GetAccountResponse) clientAbsent(genericResponse, tracking, "getBillerAccount",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.FILIALE_ABSENT_DESC + e.getMessage(),
                    request.getRequestURI(), tab[1]);

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public GetBillersResponse getBillers(GetBillersRequest billersRequest, HttpServletRequest request) {
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("getBillers");
        Tracking tracking = new Tracking();
        GetBillersResponse genericResponse = new GetBillersResponse();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");

        if (filiale == null) {
            genericResponse = (GetBillersResponse) clientAbsent(genericResponse, tracking, "getBillers",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);

            return genericResponse;
        }
        OutputStream os = null;
        try {

            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            // conn.setRequestProperty("Accept", "application/json");

            String jsonString = "";
            jsonString = new JSONObject().put("country", billersRequest.getCountry()).toString();

            log.info(" req json ====== [{}]", jsonString);
            tracking.setRequestTr(jsonString);
            os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("result ===== [{}]", result);
                obj = new JSONObject(result);// ("billersList");

                if (obj == null || obj.getJSONObject("billersList") == null
                        || obj.getJSONObject("billersList").get("billersList") == null) {
                    log.info("obj res ===== [{}]", obj.toString());
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);

                    tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE);
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("getBiller");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    os.close();
                }
                if (obj != null && obj.getJSONObject("billersList") != null
                        && obj.getJSONObject("billersList").get("billersList") != null) {
                    obj = new JSONObject(result).getJSONObject("billersList");// ("billersList");
                    // log.info("succ == [{}]", obj.toString());

                    log.info("success ===  [{}]", obj.toString());

                    genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                    if (obj.get("billersList") instanceof JSONArray) {
                        log.info("array ===[{}]", obj.getJSONArray("billersList"));
                        for (int i = 0; i < obj.getJSONArray("billersList").length(); i++) {
                            Biller biller = new Biller();
                            JSONObject myObj = obj.getJSONArray("billersList").getJSONObject(i).getJSONObject("biller");
                            biller.setAddress(myObj.getString("ADDRESS"));
                            biller.setBillerCategory(myObj.getString("BILLER_CATEGORY"));
                            biller.setBillerCode(myObj.getString("BILLER_CODE"));
                            biller.setBillerId(myObj.getLong("BILLER_ID"));
                            biller.setChannel(myObj.getString("CHANNEL"));
                            biller.setEmail(myObj.getString("EMAIL"));
                            biller.setLogo(myObj.getString("LOGO"));
                            biller.setName(myObj.getString("NAME"));
                            biller.setPays(myObj.getString("PAYS"));
                            biller.setStatus(myObj.getString("STATUS"));
                            biller.setTelephone(myObj.getString("TELEPHONE"));
                            biller.setWebsite(myObj.getString("WEBSITE"));
                            biller.setLibelleCategory(myObj.getString("LIBELLE_GATEGORY"));

                            genericResponse.getBillers().add(biller);
                        }
                    } else {
                        Biller biller = new Biller();
                        JSONObject myObj = obj.getJSONObject("billersList").getJSONObject("biller");
                        biller.setAddress(myObj.getString("ADDRESS"));
                        biller.setBillerCategory(myObj.getString("BILLER_CATEGORY"));
                        biller.setBillerCode(myObj.getString("BILLER_CODE"));
                        biller.setBillerId(myObj.getLong("BILLER_ID"));
                        biller.setChannel(myObj.getString("CHANNEL"));
                        biller.setEmail(myObj.getString("EMAIL"));
                        biller.setLogo(myObj.getString("LOGO"));
                        biller.setName(myObj.getString("NAME"));
                        biller.setPays(myObj.getString("PAYS"));
                        biller.setStatus(myObj.getString("STATUS"));
                        biller.setTelephone(myObj.getString("TELEPHONE"));
                        biller.setWebsite(myObj.getString("WEBSITE"));
                        biller.setLibelleCategory(myObj.getString("LIBELLE_GATEGORY"));
                        genericResponse.getBillers().add(biller);
                    }
                    tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE);
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("getBillers");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());

                    os.close();
                }
            } else {
                genericResponse = (GetBillersResponse) clientAbsent(genericResponse, tracking, "getBillerAccount",
                        ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.ECHEC_DESCRIPTION, request.getRequestURI(),
                        tab[1]);
                os.close();
                // return genericResponse;
            }

        } catch (Exception e) {
            try {
                os.close();
            } catch (IOException e1) {
                log.error("errorrr== [{}]", e1.getMessage());
            }
            log.error("errorrr== [{}]", e.getMessage());
            genericResponse = (GetBillersResponse) clientAbsent(genericResponse, tracking, "getBiller",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.FILIALE_ABSENT_DESC + e.getMessage(),
                    request.getRequestURI(), tab[1]);

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public GetBillFeesResponse getBillFees(GetBillFeesRequest billFeesRequest, HttpServletRequest request) {
        log.info("===enter in getBillFees [{}]", billFeesRequest);
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("getBillFees");
        Tracking tracking = new Tracking();
        GetBillFeesResponse genericResponse = new GetBillFeesResponse();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");

        if (filiale == null) {
            genericResponse = (GetBillFeesResponse) clientAbsent(genericResponse, tracking, "getBillFees",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);

            return genericResponse;
        }
        OutputStream os = null;
        try {

            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            // conn.setRequestProperty("Accept", "application/json");

            String jsonString = "";
            jsonString = new JSONObject().put("p_CodeBiller", billFeesRequest.getBillerCode())
                    .put("p_TypeCanal", billFeesRequest.getTypeCanal()).put("P_Montant", billFeesRequest.getMontant())
                    .toString();

            log.info(" req json ====== [{}]", jsonString);
            tracking.setRequestTr(jsonString);
            os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("result ===== [{}]", result);
                obj = new JSONObject(result).getJSONObject("getBillFees").getJSONObject("getBillFees");
                log.info("obj res ===== [{}]", obj.toString());
                if (obj.toString().contains("-1")) {
                    // JSONObject errObj = obj.
                    // log.error("msg err = [{}]", errObj.toString());

                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);

                    tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE);
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("getBillFees");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    os.close();
                } else if (!obj.toString().contains("-1")) {
                    //log.info("succ == [{}]", obj.toString());

                    log.info("success ===  [{}]", obj.toString());

                    genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                    genericResponse.setMontantFrais(obj.getDouble("P_MontantFrais"));
                    tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE);
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("getBillFees");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());

                    os.close();
                }
            } else {
                genericResponse = (GetBillFeesResponse) clientAbsent(genericResponse, tracking, "getBillFees",
                        ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.ECHEC_DESCRIPTION, request.getRequestURI(),
                        tab[1]);
                os.close();
            }

        } catch (Exception e) {
            /*try {
                os.close();
            } catch (IOException e1) {
                log.error("errorrr==> [{}]", e1.getMessage());
            }*/
            log.error("errorrr== [{}]", e.getMessage());
            genericResponse = (GetBillFeesResponse) clientAbsent(genericResponse, tracking, "getBillFees",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.FILIALE_ABSENT_DESC + e.getMessage(),
                    request.getRequestURI(), tab[1]);

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public PayementResponse payBill(PayementRequest payementRequest, HttpServletRequest request) {
        log.info("====Enter in payBill [{}]", payementRequest);
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("payBill");
        Tracking tracking = new Tracking();
        PayementResponse genericResponse = new PayementResponse();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");

        if (filiale == null) {
            genericResponse = (PayementResponse) clientAbsent(genericResponse, tracking, "payBill",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);

            return genericResponse;
        }
        OutputStream os = null;
        try {
            CheckFactoryRequest checkFactoryRequest = new CheckFactoryRequest();
            checkFactoryRequest.setBillerCode(payementRequest.getBillerCode());
            checkFactoryRequest.setLangue(payementRequest.getLangue());
            checkFactoryRequest.setRefenca(payementRequest.getCashingRef());
            checkFactoryRequest.setTelcli(payementRequest.getPhoneNumber());
            checkFactoryRequest.setVnumFact(payementRequest.getBillNum());
            checkFactoryRequest.setChannel(payementRequest.getChannelType());
            CheckFactoryResponse checkFactoryResponse = checkFactory(checkFactoryRequest, request);
            if (checkFactoryResponse == null || checkFactoryResponse.getBillAmount() == null) {
                genericResponse = (PayementResponse) clientAbsent(genericResponse, tracking, "getBill in paiement",
                        checkFactoryResponse.getCode(), checkFactoryResponse.getDescription(), request.getRequestURI(),
                        tab[1]);
                return genericResponse;
            }

            GetAccountRequest accountRequest = new GetAccountRequest();
            accountRequest.setAccountType(ICodeDescResponse.ACCOUNT_PRINCIPAL);
            accountRequest.setBillerCode(payementRequest.getBillerCode());
            GetAccountResponse accountResponse = getBillerAccount(accountRequest, request);
            if (accountResponse == null || accountResponse.getNumAccount() == null) {
                genericResponse = (PayementResponse) clientAbsent(genericResponse, tracking,
                        "getBillAccount in paiement", ICodeDescResponse.ECHEC_CODE,
                        ICodeDescResponse.ACCOUNT_PRINCIPAL_NON_TROUVE, request.getRequestURI(), tab[1]);
                return genericResponse;
            }
            BillerByCodeRequest billerByCodeRequest = new BillerByCodeRequest();
            billerByCodeRequest.setBillerCode(payementRequest.getBillerCode());
            BillerByCodeResponse billerByCodeResponse = getBillerByCode(billerByCodeRequest, request);
            log.info("afetr calling request [{}] response[{}] ", billerByCodeRequest, billerByCodeResponse);
            if (billerByCodeResponse == null || billerByCodeResponse.getBILLERCODE() == null) {
                genericResponse = (PayementResponse) clientAbsent(genericResponse, tracking,
                        "getBillerByCode in paiement", ICodeDescResponse.ECHEC_CODE,
                        ICodeDescResponse.BILLER_NON_TROUVE, request.getRequestURI(), tab[1]);
                return genericResponse;
            }
            log.info("before calling getBillFees ");
            GetBillFeesRequest billFeesRequest = new GetBillFeesRequest();
            billFeesRequest.setBillerCode(payementRequest.getBillerCode());
            billFeesRequest.setMontant(checkFactoryResponse.getBillAmount().toString());
            billFeesRequest.setTypeCanal(payementRequest.getChannelType());
            log.info("====before calling getBillFees in payBill [{}]", billFeesRequest);
            GetBillFeesResponse billFeesResponse = getBillFees(billFeesRequest, request);
            if (billFeesResponse == null || billFeesResponse.getMontantFrais() == null) {
                genericResponse = (PayementResponse) clientAbsent(genericResponse, tracking, "getBillFees in paiement",
                        ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.FEES_NON_TROUVE, request.getRequestURI(),
                        tab[1]);
                return genericResponse;
            }

            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/xml");
            // conn.setRequestProperty("Accept", "application/xml");

            StringBuilder builder = new StringBuilder();
            builder.append("<send_request><request>");
            builder.append(
                    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:WebservicePlus\">");
            builder.append("<soapenv:Body><urn:Paie_Mobile><urn:vNUM_SESSION>" + checkFactoryResponse.getSessionNum()
                    + "</urn:vNUM_SESSION>");
            builder.append("<urn:vnum_fact>" + payementRequest.getBillNum() + "</urn:vnum_fact>");
            builder.append("<urn:vref_client>" + checkFactoryResponse.getCustumerRef() + "</urn:vref_client>");

            builder.append("<urn:vdate_paie>" + payementRequest.getPaymentDate() + "</urn:vdate_paie>");
            builder.append("<urn:vtel_client>" + payementRequest.getPhoneNumber() + "</urn:vtel_client>");
            Integer amount = checkFactoryResponse.getBillAmount().intValue() / 100;
            builder.append("<urn:vmontant>" + amount + "</urn:vmontant>");
            builder.append("<urn:vtel_jirama>" + billerByCodeResponse.getTELEPHONE() + "</urn:vtel_jirama>");
            builder.append("</urn:Paie_Mobile></soapenv:Body></soapenv:Envelope>");
            builder.append("</request>");
            builder.append(
                    "<url_link>" + applicationProperties.getJirama() + "</url_link><url_content>xml</url_content>");
            builder.append("<compteDebit>" + payementRequest.getCustomerAccount() + "</compteDebit>");
            builder.append("<compteCredit>" + accountResponse.getNumAccount() + "</compteCredit>");
            builder.append("<montantFact>" + amount + "</montantFact>");
            Integer fees = billFeesResponse.getMontantFrais().intValue();
            builder.append("<montantFrais>" + fees + "</montantFrais>");
            builder.append("<libelle>" + payementRequest.getDescription() + "</libelle>");
            builder.append("<deviseFact>" + billerByCodeResponse.getDEVISE() + "</deviseFact>");
            builder.append("<dispo>" + "DISPONIBLE" + "</dispo>"); // FIXME
            builder.append("<val>" + "V" + "</val>");
            builder.append("<libAuto>" + "PAYEMENT FACTURE" + "</libAuto>");
            builder.append("<country>" + billerByCodeResponse.getPAYS() + "</country>");
            // builder.append("<country>" + "BF" + "</country>");
            builder.append("<codopsc>" + "GAB" + "</codopsc>");
            builder.append("<numRefTx>" + payementRequest.getBillRefTrx() + "</numRefTx>");

            builder.append("</send_request>");

            log.info(" req xml ====== [{}]", builder.toString());
            tracking.setRequestTr(builder.toString());
            os = conn.getOutputStream();
            byte[] postDataBytes = builder.toString().getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("result ===== [{}]", result);
                obj = new JSONObject(result).getJSONObject("paie_mobile").getJSONObject("response");
                log.info("obj res ===== [{}]", obj.toString());
                if (obj.toString().contains("P000")) {
                    JSONObject errObj = obj.getJSONObject("paiemobile");
                    ExceptionResponse exceptionResponse = new ExceptionResponse();
                    log.error("msg err = [{}]", errObj.toString());
                    String[] tabErr = new String[2];
                    // errObj = errObj.getJSONObject("PMSG")
                    tabErr = errObj.getJSONObject("PMSG").getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Paie_MobileResponse").getString("Paie_MobileResult").split("#");

                    ResponseRequest responseRequest = new ResponseRequest();
                    responseRequest.setBillerCode(payementRequest.getBillerCode());
                    responseRequest.setLangue(payementRequest.getLangue());
                    responseRequest.setRetourCode(tabErr[0].trim());
                    responseRequest.setServiceName(ICodeDescResponse.SERVICE_PAIEMENT);

                    ResponseResponse responseResponse = getResponse(responseRequest);

                    exceptionResponse.setNumber(tabErr[0].trim());
                    exceptionResponse.setDescription(tabErr[1]);
                    genericResponse.setCode((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.ECHEC_CODE
                            : responseResponse.getCode());
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse
                            .setDescription((responseResponse == null || responseResponse.getCode().equals("0000"))
                                    ? ICodeDescResponse.ECHEC_DESCRIPTION
                                    : responseResponse.getDescription());

                    AnnulationPaiement annulationPaiement = new AnnulationPaiement();
                    log.info("err= [{}]", errObj.toString());
                    log.info("err= [{}]", obj.toString());
                    annulationPaiement.setCode(obj.getJSONObject("annulation").getString("PCOD"));
                    annulationPaiement.setResultat(obj.getJSONObject("annulation").getString("PMSG"));
                    genericResponse.setAnnulationPaiement(annulationPaiement);
                    // PaieMobile paieMobile = new PaieMobile();
                    // genericResponse.setPaieMobile(paieMobile);
                    // genericResponse.setExceptionResponse(exceptionResponse); 
                    TransactionGlobal trG = createTransaction(payementRequest, genericResponse.getCode(),
                            checkFactoryResponse.getCustomerName(), accountResponse.getNumAccount(),
                            billFeesResponse.getMontantFrais(), checkFactoryResponse.getBillNum(),
                            checkFactoryResponse.getCustumerRef(), checkFactoryResponse.getBillAmount(), "");
                    log.info("transaction saved [{}]", trG);

                    tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("payBill");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    os.close();
                } else if (!obj.toString().contains("P000")) {
                    log.info("succ == [{}]", obj.toString());
                    // String succesOb = removeLastChar(
                    // obj.getJSONObject("Paie_MobileResponse").getString("Paie_MobileResult"));

                    // log.info("success === [{}]", succesOb);

                    ResponseRequest responseRequest = new ResponseRequest();
                    responseRequest.setBillerCode(payementRequest.getBillerCode());
                    responseRequest.setLangue(payementRequest.getLangue());
                    responseRequest.setRetourCode(obj.getString("PCOD"));
                    responseRequest.setServiceName(ICodeDescResponse.SERVICE_PAIE_MOBILE);
                    ResponseResponse responseResponse = getResponse(responseRequest);

                    genericResponse.setCode((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? responseRequest.getRetourCode()
                            : responseResponse.getCode());
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse
                            .setDescription((responseResponse == null || responseResponse.getCode().equals("0000"))
                                    ? obj.getString("PMSG")
                                    : responseResponse.getDescription());

                    TransactionGlobal trG = createTransaction(payementRequest, genericResponse.getCode(),
                            checkFactoryResponse.getCustomerName(), accountResponse.getNumAccount(),
                            billFeesResponse.getMontantFrais(), checkFactoryResponse.getBillNum(),
                            checkFactoryResponse.getCustumerRef(), checkFactoryResponse.getBillAmount(), "");
                    log.info("transaction saved [{}]", trG);

                    tracking.setCodeResponse((responseResponse == null || responseResponse.getCode().equals("0000"))
                            ? ICodeDescResponse.SUCCES_CODE
                            : responseResponse.getCode());
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("payBill");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());

                    os.close();
                }
            } else {
                genericResponse = (PayementResponse) clientAbsent(genericResponse, tracking, "payBill",
                        ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.ECHEC_DESCRIPTION, request.getRequestURI(),
                        tab[1]);
                os.close();
            }

        } catch (Exception e) {
            try {
                os.close();
            } catch (IOException e1) {
                log.error("errorrr== [{}]", e1.getMessage());
            }
            log.error("errorrr== [{}]", e.getMessage());
            genericResponse = (PayementResponse) clientAbsent(genericResponse, tracking, "payBill",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.FILIALE_ABSENT_DESC + e.getMessage(),
                    request.getRequestURI(), tab[1]);

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public TransactionGlobal createTransaction(PayementRequest payementRequest, String codeRetour, String nom,
            String creditAccount, Double frais, String numFacture, String client, Double montant, String msgFr) {
        Long idBill = billerTService.findByBillerCode(payementRequest.getBillerCode());
        if (idBill == null)
            return null;
        BillerT billerT = new BillerT();
        billerT.setId(idBill);
        // if(billerT==null || billerT.getId()==null)return null;
        // nom = nom!=null?nom:"";
        TransactionGlobal transactionGlobal = new TransactionGlobal();
        transactionGlobal.setBillerT(billerT);
        transactionGlobal.setChannel(payementRequest.getChannelType());
        transactionGlobal.setClient(client);
        transactionGlobal.setCodeRetour(codeRetour);
        transactionGlobal.setCreatedDate(LocalDate.now());
        transactionGlobal.setCreditAccount(creditAccount);
        transactionGlobal.setDatePaiement(LocalDate.now());
        transactionGlobal.setDebitAccount(payementRequest.getCustomerAccount());
        transactionGlobal.setDeviceId(payementRequest.getDeviceId());
        transactionGlobal.setEcheance("");
        transactionGlobal.setFrais(frais);
        transactionGlobal.setMontant(Double.valueOf(montant));
        transactionGlobal.setNom(nom);
        transactionGlobal.setNumeroFacture(numFacture);
        transactionGlobal.setPrenom(nom);
        transactionGlobal.setReferenceFacture(null);
        transactionGlobal.setTelephone(payementRequest.getPhoneNumber());
        transactionGlobal.setBeneficiaire(payementRequest.getBillerCode());
        transactionGlobal.setReferenceTransaction(payementRequest.getBillRefTrx());
        transactionGlobal.setMessageFr(msgFr);
        TransactionGlobal trG = transactionGlobalService.save(transactionGlobal);
        return trG;
    }

    public TransactionGlobal createTransactionB(String billerCode, String canal, String compteDeb,String reference, String codeRetour,
    String nom, String creditAccount, Double frais, String numFacture, String client, Double montant,
    String msgFr, String telephone){
        Long idBill = billerTService.findByBillerCode(billerCode);
        if(idBill==null) return null;
        BillerT billerT = new BillerT();
        billerT.setId(idBill);
        //if(billerT==null || billerT.getId()==null)return null;
        //nom = nom!=null?nom:"";
        TransactionGlobal transactionGlobal = new TransactionGlobal();
        transactionGlobal.setBillerT(billerT);
        transactionGlobal.setChannel(canal);
        transactionGlobal.setClient(client);
        transactionGlobal.setCodeRetour(codeRetour);
        transactionGlobal.setCreatedDate(LocalDate.now());
        transactionGlobal.setCreditAccount(creditAccount);
        transactionGlobal.setDatePaiement(LocalDate.now());
        transactionGlobal.setDebitAccount(compteDeb);
        transactionGlobal.setDeviceId("");
        transactionGlobal.setEcheance("");
        transactionGlobal.setFrais(frais);
        transactionGlobal.setMontant(montant);
        transactionGlobal.setNom(nom);
        transactionGlobal.setNumeroFacture(numFacture);
        transactionGlobal.setPrenom(nom);
        transactionGlobal.setReferenceFacture(null);
        transactionGlobal.setTelephone(telephone);
        transactionGlobal.setBeneficiaire(billerCode);
        transactionGlobal.setReferenceTransaction(reference);
        //transactionGlobal.setMessageEn(msgEn);
        transactionGlobal.setMessageFr(msgFr);
        TransactionGlobal trG = transactionGlobalService.save(transactionGlobal);
        return trG;
    }

    public BillerByCodeResponse getBillerByCode(BillerByCodeRequest byCodeRequest, HttpServletRequest request) {
        log.info("enter in getBillerByCode [{}]", byCodeRequest);
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("getBillerByCode");
        Tracking tracking = new Tracking();
        BillerByCodeResponse genericResponse = new BillerByCodeResponse();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");

        if (filiale == null) {
            genericResponse = (BillerByCodeResponse) clientAbsent(genericResponse, tracking, "getBillerByCode",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);

            return genericResponse;
        }
        OutputStream os = null;
        try {

            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            // conn.setRequestProperty("Accept", "application/json");

            String jsonString = "";
            jsonString = new JSONObject().put("billerCode", byCodeRequest.getBillerCode()).toString();

            log.info(" req json ====== [{}]", jsonString);
            tracking.setRequestTr(jsonString);
            os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("result ===== [{}]", result);
                obj = new JSONObject(result);// .getJSONObject("biller");
                if (obj.isNull("biller")) {// ||obj.toString().contains("null")
                    // JSONObject errObj = obj.
                    log.info("obj res ===== [{}]", obj.toString());

                    // log.error("msg err = [{}]", errObj.toString());

                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);

                    tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE);
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("getBillerByCode");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    os.close();
                } else if (obj != null && obj.getJSONObject("biller").toString() != null && obj.getJSONObject("biller").get("biller") != null) {
                    obj = new JSONObject(result).getJSONObject("biller");
                    log.info("succ == [{}]", obj.toString());

                    log.info("success ===  [{}]", obj.toString());

                    genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                    obj = obj.getJSONObject("biller");
                    genericResponse.setADDRESS(obj.getString("ADDRESS"));
                    genericResponse.setBILLERCATEGORY(obj.getString("BILLER_CATEGORY"));
                    genericResponse.setBILLERCODE(obj.getString("BILLER_CODE"));
                    genericResponse.setBILLERID(obj.getInt("BILLER_ID"));
                    genericResponse.setCHANNEL(obj.getString("CHANNEL"));
                    genericResponse.setDEVISE(obj.getString("DEVISE"));
                    genericResponse.setEMAIL(obj.getString("EMAIL"));
                    genericResponse.setLOGO(obj.getString("LOGO"));
                    genericResponse.setNAME(obj.getString("NAME"));
                    genericResponse.setPAYS(obj.getString("PAYS"));
                    genericResponse.setSTATUS(obj.getString("STATUS"));
                    genericResponse.setTELEPHONE(obj.getString("TELEPHONE"));
                    genericResponse.setWEBSITE(obj.getString("WEBSITE"));
                    genericResponse.setLIBELLE_GATEGORY(obj.getString("LIBELLE_GATEGORY"));
                    tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE);
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr("getBillerByCode");
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());

                    os.close();
                }
            } else {
                genericResponse = (BillerByCodeResponse) clientAbsent(genericResponse, tracking, "getBillerByCode",
                        ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.ECHEC_DESCRIPTION, request.getRequestURI(),
                        tab[1]);
                os.close();
            }

        } catch (Exception e) {
            try {
                os.close();
            } catch (IOException e1) {
                log.error("errorrr== [{}]", e1.getMessage());
            }
            log.error("errorrr== [{}]", e.getMessage());
            genericResponse = (BillerByCodeResponse) clientAbsent(genericResponse, tracking, "getBillerByCode",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.FILIALE_ABSENT_DESC + e.getMessage(),
                    request.getRequestURI(), tab[1]);

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public FactureDispoResponse factureDisponible(HttpServletRequest request) {
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("factureDisponible");
        Tracking tracking = new Tracking();
        FactureDispoResponse genericResponse = new FactureDispoResponse();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");

        if (filiale == null) {
            genericResponse = (FactureDispoResponse) clientAbsent(genericResponse, tracking, request.getRequestURI(),
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);

            return genericResponse;
        }
        OutputStream os = null;
        try {

            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonString = "";
            // jsonString = new JSONObject().put("ref_paie",
            // cardsRequest.getRefPaie()).toString();
            log.info("recuPaiement ====== [{}]", jsonString);
            tracking.setRequestTr(jsonString);
            os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            // log.info("resp code [{}]", conn.);
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                // System.out.println("err=" + result);
                log.info("err ===== [{}]", result);
                obj = new JSONObject(result);
                if (obj.getJSONObject("Envelope").getJSONObject("Body").getJSONObject("Facture_dispoResponse")
                        .toString().contains("null")) {
                    // JSONObject errObj = obj.getJSONObject("Envelope").getJSONObject("Body")
                    // .getJSONObject("Recu_paiementResponse");
                    ExceptionResponse exceptionResponse = new ExceptionResponse();
                    // log.error("msg err = [{}]", errObj.get("Check_facture_ptfResult"));
                    // String[] tabErr =
                    // errObj.get("Facture_dispoResponse").toString().split(":");//FIXME

                    exceptionResponse.setNumber(ICodeDescResponse.ECHEC_CODE + "");
                    exceptionResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);
                    // genericResponse.setExceptionResponse(exceptionResponse);
                    // .getJSONObject("Fault").getString("faultstring"));
                    tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    // trackingService.save(tracking);
                    os.close();
                    // return genericResponse;
                } else if (obj.getJSONObject("Envelope").getJSONObject("Body").getJSONObject("Facture_dispoResponse")
                        .toString().contains("Facture_dispoResult")) {
                    log.info("succ == [{}]", obj.toString());
                    String succesOb = obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Facture_dispoResponse").getString("Facture_dispoResult");
                    log.info("succc === [{}]", succesOb.toString());
                    String[] tabSuccess = succesOb.split("#");

                    // JSONArray myArray = TODO
                    FacuresDispo facuresDispo = new FacuresDispo();
                    facuresDispo.setAnneeMois(tabSuccess[1]);
                    facuresDispo.setSite(tabSuccess[0]);
                    genericResponse.getFacturesDispos().add(facuresDispo);

                    genericResponse = (FactureDispoResponse) clientAbsent(genericResponse, tracking,
                            request.getRequestURI(), ICodeDescResponse.SUCCES_CODE,
                            ICodeDescResponse.SUCCES_DESCRIPTION, request.getRequestURI(), tab[1]);
                    os.close();
                    // return genericResponse;
                }
            } else {
                // resp != 200
                genericResponse = (FactureDispoResponse) clientAbsent(genericResponse, tracking,
                        request.getRequestURI(), ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.ECHEC_DESCRIPTION,
                        request.getRequestURI(), tab[1]);
                os.close();
                // return genericResponse;
            }

        } catch (Exception e) {
            try {
                os.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                log.error("errorrr 1== [{}]", e1.getMessage());
            }
            log.error("errorrr 0== [{}]", e.getMessage());
            e.printStackTrace();
            genericResponse = (FactureDispoResponse) clientAbsent(genericResponse, tracking, request.getRequestURI(),
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.FILIALE_ABSENT_DESC + e.getMessage(),
                    request.getRequestURI(), tab[1]);
            // return genericResponse;

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    /**
     * Methode pour logger si le client est absent
     * 
     * @param genericResponse
     * @param tracking
     * @param filiale
     * @param code
     * @param description
     * @param request
     * @param token
     * @return GenericResponse
     */
    public GenericResponse clientAbsent(GenericResponse genericResponse, Tracking tracking, String filiale, String code,
            String description, String request, String token) {
        genericResponse.setCode(code);
        genericResponse.setDateResponse(Instant.now());
        genericResponse.setDescription(description);
        tracking.setCodeResponse(code + "");
        tracking.setDateResponse(Instant.now());
        tracking.setEndPointTr(filiale);
        tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
        tracking.setDateRequest(Instant.now());
        tracking.setResponseTr(description);
        tracking.setTokenTr(token);
        tracking.setRequestTr(request);
        trackingService.save(tracking);
        return genericResponse;
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    public ResponseResponse getResponse(ResponseRequest responseRequest) {
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("wsResponseCode");
        // Tracking tracking = new Tracking();
        ResponseResponse genericResponse = new ResponseResponse();

        if (filiale == null) {
            return genericResponse;
        }
        OutputStream os = null;
        try {

            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String langue = "fr";
            if (responseRequest.getLangue().equalsIgnoreCase("fr")
                    || responseRequest.getLangue().equalsIgnoreCase("en"))
                langue = responseRequest.getLangue().toLowerCase();
            String jsonString = "";
            jsonString = new JSONObject().put("code_biller", responseRequest.getBillerCode())
                    .put("code_retour", responseRequest.getRetourCode())
                    .put("nom_service", responseRequest.getServiceName()).put("langue", langue).toString();

            // jsonString = new JSONObject().put("ref_paie",
            // cardsRequest.getRefPaie()).toString();
            log.info("wsResponseCode ====== [{}]", jsonString);
            // tracking.setRequestTr(jsonString);
            os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("err ===== [{}]", result);
                obj = new JSONObject(result);
                if (obj.getJSONObject("get_code_response").getJSONObject("response").toString().contains("CCOD")) {
                    JSONObject succObj = obj.getJSONObject("get_code_response").getJSONObject("response");
                    genericResponse.setCode(succObj.getString("CCOD"));
                    genericResponse.setDescription(succObj.getString("CMSG"));
                    os.close();
                }
            } else {
                os.close();
                return null;
            }

        } catch (Exception e) {
            try {
                os.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                log.error("errorrr 1== [{}]", e1.getMessage());
            }
            log.error("errorrr 0== [{}]", e.getMessage());
            e.printStackTrace();
            return null;
            // return genericResponse;

        }
        return genericResponse;
    }

    public GetBillersResponse getBillersByCateg(GetBillersByCategRequest billersRequest, HttpServletRequest request) {
        log.info("in getBillersByCateg [{}]", billersRequest);
        GetBillersResponse genResponse = new GetBillersResponse();
        try {
            GetBillersRequest billRequest = new GetBillersRequest();
            billRequest.setCountry(billersRequest.getCountry());
            GetBillersResponse billersResp = getBillers(billRequest, request);
            if (billersResp.getCode() == "200" && !billersResp.getBillers().isEmpty()) {
                for (Biller biller : billersResp.getBillers()) {
                    if (biller.getBillerCategory().equals(billersRequest.getCategorie())) {
                        genResponse.getBillers().add(biller);
                    }
                }
                genResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                genResponse.setDateResponse(Instant.now());
                genResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
            }
            else {
                genResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                genResponse.setDateResponse(Instant.now());
                genResponse.setDescription(ICodeDescResponse.NO_BILLER_FOR_THIS_CATEG);
            }

        } catch (Exception e) {
            log.error("Error in getBillersByCateg = ", e);
            genResponse.setCode(ICodeDescResponse.ECHEC_CODE);
            genResponse.setDateResponse(Instant.now());
            genResponse.setDescription(ICodeDescResponse.ECHEC_CODE + " =" + e.getMessage());
        }
        return genResponse;
    }

    public CategBillersResponse getCategByCountry(GetBillersRequest billersRequest, HttpServletRequest request) {
        log.info("in getCategByCountry [{}]", billersRequest);
        CategBillersResponse genResponse = new CategBillersResponse();
        try {
            GetBillersRequest billRequest = new GetBillersRequest();
            billRequest.setCountry(billersRequest.getCountry());
            GetBillersResponse billersResp = getBillers(billRequest, request);
            if (billersResp.getCode() == "200" && !billersResp.getBillers().isEmpty()) {
                for (Biller biller : billersResp.getBillers()) {
                    if (biller.getPays().equals(billersRequest.getCountry())) {
                        genResponse.getCategories().add(biller.getBillerCategory());
                    }
                }
                genResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                genResponse.setDateResponse(Instant.now());
                genResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
            }
            else {
                genResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                genResponse.setDateResponse(Instant.now());
                genResponse.setDescription(ICodeDescResponse.NO_BILLER_FOR_THIS_CATEG);
            }

        } catch (Exception e) {
            log.error("Error in getCategByCountry = ", e);
            genResponse.setCode(ICodeDescResponse.ECHEC_CODE);
            genResponse.setDateResponse(Instant.now());
            genResponse.setDescription(ICodeDescResponse.ECHEC_CODE + " =" + e.getMessage());
        }
        return genResponse;
    }

    /*
     * public static void main(String[] args) { String str =
     * "231190419217357#23151240500 #201904#15057##201912201126001600#MME BAKOARISOA VIRGINIE $231190219096397#23151240500 #201902#15057##201912201126001600#MME BAKOARISOA VIRGINIE $23118061265996#23151240500 #201806#101312##201912201126001600#MME BAKOARISOA VIRGINIE"
     * ; str = str.replaceAll("##", "#"); String[] tab =
     * str.split(Pattern.quote("$")); //str = removeLastChar(str); for (String it :
     * tab) { System.out.println("it="+it); }
     * 
     * }
     */
}