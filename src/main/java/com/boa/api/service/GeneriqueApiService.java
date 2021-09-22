package com.boa.api.service;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.boa.api.config.ApplicationProperties;
import com.boa.api.domain.Tracking;
import com.boa.api.domain.TransactionGlobal;
import com.boa.api.domain.WebServiceParams;
import com.boa.api.domain.WebServices;
import com.boa.api.request.BillerByCodeRequest;
import com.boa.api.request.CheckFactoryRequest;
import com.boa.api.request.GetAccountRequest;
import com.boa.api.request.GetBillFeesRequest;
import com.boa.api.request.GetBillRequest;
import com.boa.api.request.GetBillsByRefJiramaReq;
import com.boa.api.request.GetBillsByRefRequest;
import com.boa.api.request.NotificationPaiementRequest;
import com.boa.api.request.PayementRequest;
import com.boa.api.request.ResponseRequest;
import com.boa.api.response.AnnulationPaiement;
import com.boa.api.response.BillerByCodeResponse;
import com.boa.api.response.CheckFactoryResponse;
import com.boa.api.response.ExceptionResponse;
import com.boa.api.response.GetAccountResponse;
import com.boa.api.response.GetBillFeesResponse;
import com.boa.api.response.GetBillsByRefResponse;
import com.boa.api.response.ItemResp;
import com.boa.api.response.NotificationPaiementResponse;
import com.boa.api.response.PayementResponse;
import com.boa.api.response.ResponseResponse;
import com.boa.api.service.util.ICodeDescResponse;
import com.boa.api.service.util.Utils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * GeneriqueApiService
 */
@Service
@Transactional
public class GeneriqueApiService {

    @Value("${check.factory.jir}")
    private String checkFactJir;

    @Value("${check.factory.cnss}")
    private String checkFactCnss;

    @Value("${biller.jirama}")
    private String billerJirama;

    @Value("${biller.cnss}")
    private String billerCnss;
    private final ApiService apiService;
    private final Logger log = LoggerFactory.getLogger(GeneriqueApiService.class);
    // private final ParamFilialeRepository paramFilialeRepository;
    private final WebServicesService webServicesService;
    private final UserService userService;
    private final Utils utils;
    private final TrackingService trackingService;
    // private final Initializer initializer;
    @Value("${getBill.service}")
    private String getBillService;

    @Value("${getBillsByRef.service}")
    private String getBillsByRefService;

    @Value("${paiement.service}")
    private String payBillService;

    @Value("${notification.service}")
    private String notifPaiementService;
    private final ApplicationProperties applicationProperties;


    public GeneriqueApiService(ApiService apiService, WebServicesService webServicesService, UserService userService,
            TrackingService trackingService, Utils utils, ApplicationProperties applicationProperties) {
        this.apiService = apiService;
        // this.paramFilialeRepository = paramFilialeRepository;
        this.webServicesService = webServicesService;
        this.userService = userService;
        this.trackingService = trackingService;
        this.utils = utils;
        this.applicationProperties = applicationProperties;
        // this.initializer = initializer;
    }

    public CheckFactoryResponse checkFactory(GetBillRequest billRequest, HttpServletRequest request) {
        // atribute01 split, atribute02 post ou get
        // JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an
        // object.
        CheckFactoryResponse genericResponse = new CheckFactoryResponse();
        
        Tracking tracking = new Tracking();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        WebServices webServices = webServicesService.getWebServiceByParams(billRequest.getBillerCode(), getBillService);
        log.info("ws = [{}]", webServices);
        if (webServices == null) {
            return genericResponse = (CheckFactoryResponse) apiService.clientAbsent(genericResponse, tracking,
                    getBillService, ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    billRequest.toString(), tab[1]);
        }
        String requestParam = webServices.getXmlRequest();
        // set params In based in nameCorresp
        for (WebServiceParams it : webServices.getServiceParams()) {
            if (it.getParamSens().equalsIgnoreCase("I")) {
                log.info("inf = [{}]", it.getParamNameCorresp());
                try {
                    Object fs = new PropertyDescriptor(it.getParamNameCorresp(), GetBillRequest.class).getReadMethod()
                            .invoke(billRequest);
                    log.info("invoke = [{}]", fs);
                    requestParam = requestParam.replace("#" + it.getOrdre() + "#", fs.toString());
                } catch (Exception e1) {
                    log.error("err = [{}]", e1.getMessage());
                    e1.fillInStackTrace();
                    return genericResponse = (CheckFactoryResponse) apiService.clientAbsent(genericResponse, tracking,
                            getBillService, ICodeDescResponse.ECHEC_CHAMP_CODE, ICodeDescResponse.ECHEC_CHAMP_DESC,
                            billRequest.toString(), tab[1]);
                }

            }
        }
        log.info("req param after 3 = [{}]", requestParam);
        //if(billRequest.getBillerCode().equalsIgnoreCase("jirama")){
        if(billRequest.getBillerCode().equalsIgnoreCase(applicationProperties.getCodeJirama())){
            ObjectMapper mapper = new ObjectMapper();
            CheckFactoryRequest cardsRequest = new CheckFactoryRequest();
            try {
                cardsRequest = mapper.readValue(requestParam, CheckFactoryRequest.class);
                genericResponse = apiService.checkFactory(cardsRequest, request);
                return genericResponse;
            } catch (Exception e) {
                String msgErr = "Exception lors du parsing de CheckFactoryRequest " + requestParam;
                log.info(msgErr+" {}", e);
            }
            
        }
        URL url;
        OutputStream os = null;
        String urlRequest = webServices.getEndPointExpose();
        try {
            if (webServices.getAttribute02().equalsIgnoreCase("GET")) {
                urlRequest += requestParam;
            }
            // url = new URL(webServices.getEndPointExpose());
            url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String typApp = webServices.getProtocole().equalsIgnoreCase("xml") ? "application/xml" : "application/json";
            conn.setDoOutput(true);
            conn.setRequestMethod(webServices.getAttribute02().toUpperCase());
            conn.setRequestProperty("Content-Type", typApp);
            // conn.setRequestProperty("Accept", typApp);
            String result = "";
            if (webServices.getToken() != null)
                conn.setRequestProperty("Authorization", webServices.getToken());
            StringBuilder builder = new StringBuilder();
            String reqStr = "";
            if (!webServices.getAttribute02().equalsIgnoreCase("GET") && webServices.getAttribute01() != null) {
                builder.append(headerRequest());// TODO remove when direct
                builder.append(requestParam);
                builder.append(footerRequest(webServices.getEndPoint(), webServices.getProtocole().toLowerCase()));

                log.info("request to send [{}]", builder.toString());

                reqStr = builder.toString();
                os = conn.getOutputStream();
                byte[] postDataBytes = builder.toString().getBytes();

                os.write(postDataBytes);
                os.flush();
            } else if (!webServices.getAttribute02().equalsIgnoreCase("GET")){
                os = conn.getOutputStream();
                byte[] postDataBytes = requestParam.getBytes();

                os.write(postDataBytes);
                os.flush();
                conn.connect();
                reqStr = urlRequest;
            }else {
                os = conn.getOutputStream();
                // byte[] postDataBytes = requestParam.getBytes();

                // os.write(postDataBytes);
                os.flush();
                conn.connect();
                reqStr = urlRequest;
            }
            tracking.setRequestTr(reqStr);

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                genericResponse.setCode(String.valueOf(conn.getResponseCode()));
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String ligne = br.readLine();
                while (ligne != null) {
                    result += ligne;
                    ligne = br.readLine();
                }
                log.info("resp ===== [{}]", result);
                tracking.setResponseTr(result);
                if (webServices.getProtocole().equalsIgnoreCase("xml")) {
                    String split = null;
                    if (webServices.getAttribute01() != null)
                        split = webServices.getAttribute01();
                    if (split != null) {
                        String[] pathResp = webServices.getResponsePath().split(split);
                        log.info("taille path resp [{}]", pathResp.length);
                        obj = new JSONObject(result).getJSONObject(pathResp[0]);
                        if (obj != null) {
                            result = obj.toString();
                            log.info("result [{}]", result);

                            if (result != null && result.contains(pathResp[pathResp.length - 1])) { // service balise
                                                                                                    // resp princ, &
                                                                                                    // isArray
                                JSONObject itObj = new JSONObject();
                                itObj = obj;
                                for (int i = 1; i < pathResp.length - 1; i++) {
                                    log.info("result ===> [{}]", result);
                                    log.info("pathResp[i] => [{}] et {}", pathResp[i], i);
                                    itObj = itObj.getJSONObject(pathResp[i]);
                                    log.info("iiObj [{}]", itObj.toString());
                                }
                                result = itObj.getString(pathResp[pathResp.length - 1]);
                                log.info("ok [{}]", result);

                                // if(webServices.getProtocole().equalsIgnoreCase("xml")){
                                // deb split
                                String[] tabSuccess = result.split(split);
                                if (tabSuccess.length > 2) {

                                    genericResponse.setBillAmount(Double.valueOf(tabSuccess[3].trim()));
                                    genericResponse.setCustomerName(tabSuccess[5].trim());
                                    genericResponse.setRequierNumber(tabSuccess[4].trim());
                                    genericResponse.setBillNum(tabSuccess[0].trim());
                                    genericResponse.setSessionNum(tabSuccess[6].trim().replace("%", ""));
                                    genericResponse.setCustumerRef(tabSuccess[1].trim());
                                    genericResponse.setBillDate(tabSuccess[2].trim());

                                    ResponseRequest responseRequest = new ResponseRequest();
                                    responseRequest.setBillerCode(billRequest.getBillerCode());
                                    responseRequest.setLangue(billRequest.getLangue());
                                    responseRequest.setRetourCode("200");
                                    responseRequest.setServiceName(webServices.getServiceName());

                                    ResponseResponse responseResponse = apiService.getResponse(responseRequest);

                                    genericResponse = (CheckFactoryResponse) apiService.clientAbsent(genericResponse,
                                            tracking, "getBill",
                                            (responseResponse == null || responseResponse.getCode().equals("0000"))
                                                    ? ICodeDescResponse.SUCCES_CODE
                                                    : responseResponse.getCode(),
                                            (responseResponse == null || responseResponse.getCode().equals("0000"))
                                                    ? ICodeDescResponse.SUCCES_DESCRIPTION
                                                    : responseResponse.getDescription(),
                                            tracking.getRequestTr(), tab[1]);

                                    GetBillFeesRequest billFeesRequest = new GetBillFeesRequest();
                                    billFeesRequest.setBillerCode(billRequest.getBillerCode());
                                    billFeesRequest.setMontant(genericResponse.getBillAmount().toString());
                                    billFeesRequest.setTypeCanal(billRequest.getChannelType());
                                    GetBillFeesResponse billFeesResponse = apiService.getBillFees(billFeesRequest,
                                            request);
                                    if (billFeesResponse != null)
                                        genericResponse.setFeeAmount(billFeesResponse.getMontantFrais());
                                } else { // bad resp split
                                    ExceptionResponse exceptionResponse = new ExceptionResponse();

                                    exceptionResponse.setNumber(tabSuccess[0]);
                                    exceptionResponse.setDescription(tabSuccess[1]);
                                    ResponseRequest responseRequest = new ResponseRequest();
                                    responseRequest.setBillerCode(billRequest.getBillerCode().toUpperCase());
                                    responseRequest.setLangue(billRequest.getLangue());
                                    responseRequest.setRetourCode(tabSuccess[0].trim());
                                    responseRequest.setServiceName(webServices.getServiceName());
                                    ResponseResponse responseResponse = apiService.getResponse(responseRequest);
                                    genericResponse.setCode(
                                            (responseResponse == null || responseResponse.getCode().equals("0000"))
                                                    ? ICodeDescResponse.ECHEC_CODE
                                                    : responseResponse.getCode());
                                    genericResponse.setDateResponse(Instant.now());
                                    genericResponse.setDescription(
                                            (responseResponse == null || responseResponse.getCode().equals("0000"))
                                                    ? ICodeDescResponse.ECHEC_DESCRIPTION
                                                    : responseResponse.getDescription());

                                    genericResponse.setExceptionResponse(exceptionResponse);
                                    tracking.setCodeResponse(
                                            (responseResponse == null || responseResponse.getCode().equals("0000"))
                                                    ? ICodeDescResponse.ECHEC_CODE
                                                    : responseResponse.getCode());
                                    tracking.setDateResponse(Instant.now());
                                    tracking.setEndPointTr(webServices.getServiceNameCorresp());
                                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                                    // tracking.setResponseTr(result);
                                    tracking.setTokenTr(tab[1]);
                                    tracking.setDateRequest(Instant.now());
                                }
                            } // END Split
                            else {// response normal
                                log.info("resp normal");
                                constructResp(webServices, result, genericResponse, billRequest.getBillerCode(),
                                        billRequest.getLangue(), tracking, request, tab[1]);
                            }

                        } else {
                            // obj = null
                            constructResp(webServices, null, genericResponse, billRequest.getBillerCode(),
                                    billRequest.getLangue(), tracking, request, tab[1]);
                        }

                    } else { // without split
                        constructResp(webServices, result, genericResponse, billRequest.getBillerCode(),
                                billRequest.getLangue(), tracking, request, tab[1]);
                    }

                } else {// json 200
                    log.info("json ==> [{}]", result);
                    constructResp(webServices, result, genericResponse, billRequest.getBillerCode(),
                            billRequest.getLangue(), tracking, request, tab[1]);
                }

            } else {// !=200
                genericResponse.setCode(String.valueOf(conn.getResponseCode()));
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String ligne = br.readLine();
                while (ligne != null) {
                    result += ligne;
                    ligne = br.readLine();
                }
                log.info("resp ===== [{}]", result);
                if (webServices.getProtocole().equalsIgnoreCase("xml")) {

                } else {
                    // resp !=200 for json
                    constructResp(webServices, result, genericResponse, billRequest.getBillerCode(),
                            billRequest.getLangue(), tracking, request, tab[1]);
                }
            }
            // os.close();
        } catch (Exception e) {
            log.info("exception occur = [{}]", e);
        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public GetBillsByRefResponse getBillsByRef(GetBillsByRefRequest cardsRequest, HttpServletRequest request) {
        GetBillsByRefResponse genericResponse = new GetBillsByRefResponse();
        Tracking tracking = new Tracking();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        WebServices webServices = webServicesService.getWebServiceByParams(cardsRequest.getBillerCode(),
                getBillsByRefService);
        // log.info("ws = [{}]", webServices);
        if (webServices == null) {
            return genericResponse = (GetBillsByRefResponse) apiService.clientAbsent(genericResponse, tracking,
                    getBillService, ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    cardsRequest.toString(), tab[1]);
        }
        String requestParam = webServices.getXmlRequest();
        // set params In based in nameCorresp
        for (WebServiceParams it : webServices.getServiceParams()) {
            if (it.getParamSens().equalsIgnoreCase("I")) {
                log.info("inf = [{}]", it.getParamNameCorresp());
                try {
                    Object fs = new PropertyDescriptor(it.getParamNameCorresp(), GetBillsByRefRequest.class)
                            .getReadMethod().invoke(cardsRequest);
                    log.info("invoke = [{}]", fs);
                    requestParam = requestParam.replace("#" + it.getOrdre() + "#", fs.toString());
                } catch (Exception e1) {
                    return genericResponse = (GetBillsByRefResponse) apiService.clientAbsent(genericResponse, tracking,
                            getBillsByRefService, ICodeDescResponse.ECHEC_CHAMP_CODE,
                            ICodeDescResponse.ECHEC_CHAMP_DESC, cardsRequest.toString(), tab[1]);
                }
            }
        }
        
        log.info("req getBillsByRef to send = [{}]", requestParam);
        if(cardsRequest.getBillerCode().equalsIgnoreCase(applicationProperties.getCodeJirama())){
            ObjectMapper mapper = new ObjectMapper();
            GetBillsByRefJiramaReq billsByRefJiramaReq = new GetBillsByRefJiramaReq();
            try {
                billsByRefJiramaReq = mapper.readValue(requestParam, GetBillsByRefJiramaReq.class);
                genericResponse = apiService.getBillsByRef(billsByRefJiramaReq, request);
                return genericResponse;
            } catch (Exception e) {
                String msgErr = "Exception lors du parsing de GetBillsByRefJiramaReq =" + requestParam;
                log.info(msgErr+" {}", e);
            }
        }
        URL url;
        OutputStream os = null;
        String urlRequest = webServices.getEndPointExpose();
        try {
            if (webServices.getAttribute02().equalsIgnoreCase("GET")) {
                urlRequest += requestParam;
            }
            // url = new URL(webServices.getEndPointExpose());
            url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String typApp = webServices.getProtocole().equalsIgnoreCase("xml") ? "application/xml" : "application/json";
            conn.setDoOutput(true);
            conn.setRequestMethod(webServices.getAttribute02().toUpperCase());
            conn.setRequestProperty("Content-Type", typApp);
            conn.setRequestProperty("Accept", typApp);
            String result = "";
            if (webServices.getToken() != null)
                conn.setRequestProperty("Authorization", webServices.getToken());
            StringBuilder builder = new StringBuilder();
            String reqStr = "";
            if (!webServices.getAttribute02().equalsIgnoreCase("GET") && webServices.getAttribute01() != null) {
                builder.append(headerRequest());
                builder.append(requestParam);
                builder.append(footerRequest(webServices.getEndPoint(), webServices.getProtocole().toLowerCase()));
                // log.info("request to send [{}]", builder.toString());

                reqStr = builder.toString();
                os = conn.getOutputStream();
                byte[] postDataBytes = builder.toString().getBytes();

                os.write(postDataBytes);
                os.flush();
            } else if (!webServices.getAttribute02().equalsIgnoreCase("GET")){
                os = conn.getOutputStream();
                byte[] postDataBytes = requestParam.getBytes();

                os.write(postDataBytes);
                os.flush();
                conn.connect();
                reqStr = urlRequest;
            }else {
                os = conn.getOutputStream();
                // byte[] postDataBytes = requestParam.getBytes();

                // os.write(postDataBytes);
                os.flush();
                conn.connect();
                reqStr = urlRequest;
            }
            tracking.setRequestTr(reqStr);
            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                genericResponse.setCode(String.valueOf(conn.getResponseCode()));
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String ligne = br.readLine();
                while (ligne != null) {
                    result += ligne;
                    ligne = br.readLine();
                }
                log.info("resp ===== [{}]", result);
                tracking.setResponseTr(result);
                if (webServices.getProtocole().equalsIgnoreCase("xml")) {
                    String split = null;
                    if (webServices.getAttribute01() != null)
                        split = webServices.getAttribute01();
                    if (split != null) {
                        String[] pathResp = webServices.getResponsePath().split(split);
                        log.info("taille path resp [{}]", pathResp.length);
                        obj = new JSONObject(result).getJSONObject(pathResp[0]);
                        if (obj != null) {
                            result = obj.toString();
                            log.info("result [{}]", result);

                            if (result != null && result.contains(pathResp[pathResp.length - 1])) { // service balise
                                                                                                    // resp princ, &
                                                                                                    // isArray
                                JSONObject itObj = new JSONObject();
                                // JsonObject rootobj = root.getAsJsonObject();
                                itObj = obj;
                                for (int i = 1; i < pathResp.length - 1; i++) {
                                    log.info("result ===> [{}]", result);
                                    log.info("pathResp[i] => [{}] et {}", pathResp[i], i);
                                    itObj = itObj.getJSONObject(pathResp[i]);
                                    log.info("iiObj [{}]", itObj.toString());
                                }
                                result = itObj.getString(pathResp[pathResp.length - 1]);
                                log.info("ok [{}]", result);

                                // if(webServices.getProtocole().equalsIgnoreCase("xml")){
                                // deb split
                                String[] tabSucc1 = result.split(split);
                                if (tabSucc1.length > 2) {
                                    String[] tabSuccess = result.split(Pattern.quote("$"));
                                    log.info("taill tab = [{}]", tabSuccess.length);
                                    for (int i = 0; i < tabSuccess.length; i++) {
                                        String[] tabTemp = tabSuccess[i].split(split);
                                        ItemResp itemResp = new ItemResp();
                                        itemResp.setBillAmount(Double.valueOf(tabTemp[3]));
                                        itemResp.setBillDate(tabTemp[2]);
                                        itemResp.setBillNum(tabTemp[0]);
                                        itemResp.setRequierNumber(tabTemp[4]);
                                        itemResp.setCustomerName(tabTemp[6]);
                                        itemResp.setCustumerRef(tabTemp[1]);
                                        itemResp.setSessionNum(tabTemp[5]);

                                        GetBillFeesRequest billFeesRequest = new GetBillFeesRequest();
                                        billFeesRequest.setBillerCode(cardsRequest.getBillerCode());
                                        billFeesRequest.setMontant(itemResp.getBillAmount().toString());
                                        billFeesRequest.setTypeCanal(cardsRequest.getChannelType());
                                        GetBillFeesResponse billFeesResponse = apiService.getBillFees(billFeesRequest,
                                                request);
                                        if (billFeesResponse != null)
                                            itemResp.setFeeAmount(billFeesResponse.getMontantFrais());

                                        genericResponse.getBillList().add(itemResp);
                                    }
                                    ResponseRequest responseRequest = new ResponseRequest();
                                    responseRequest.setBillerCode(cardsRequest.getBillerCode());
                                    responseRequest.setLangue(cardsRequest.getLangue());
                                    responseRequest.setRetourCode(ICodeDescResponse.SUCCES_CODE);
                                    responseRequest.setServiceName(ICodeDescResponse.SERVICE_CHECK_REF_PTF);
                                    ResponseResponse responseResponse = apiService.getResponse(responseRequest);

                                    genericResponse.setCode(
                                            (responseResponse == null || responseResponse.getCode().equals("0000"))
                                                    ? ICodeDescResponse.SUCCES_CODE
                                                    : responseResponse.getCode());
                                    genericResponse.setDateResponse(Instant.now());
                                    genericResponse.setDescription(
                                            (responseResponse == null || responseResponse.getCode().equals("0000"))
                                                    ? ICodeDescResponse.SUCCES_DESCRIPTION
                                                    : responseResponse.getDescription());

                                    tracking.setCodeResponse(
                                            (responseResponse == null || responseResponse.getCode().equals("0000"))
                                                    ? ICodeDescResponse.SUCCES_CODE
                                                    : responseResponse.getCode());
                                    tracking.setDateResponse(Instant.now());
                                    tracking.setEndPointTr("getBillsByRef");
                                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                                    tracking.setResponseTr(result);
                                    tracking.setTokenTr(tab[1]);
                                    tracking.setDateRequest(Instant.now());

                                } else { // bad resp split
                                    String[] tabErr = new String[2];

                                    tabErr = result.split(split);

                                    ResponseRequest responseRequest = new ResponseRequest();
                                    responseRequest.setBillerCode(cardsRequest.getBillerCode().toUpperCase());
                                    responseRequest.setLangue(cardsRequest.getLangue());
                                    responseRequest.setRetourCode(tabErr[0].trim());
                                    responseRequest.setServiceName(ICodeDescResponse.SERVICE_CHECK_REF_PTF);

                                    ResponseResponse responseResponse = apiService.getResponse(responseRequest);

                                    ExceptionResponse exceptionResponse = new ExceptionResponse();
                                    exceptionResponse.setNumber(tabErr[0]);
                                    exceptionResponse.setDescription(
                                            (responseResponse == null || responseResponse.getCode().equals("0000"))
                                                    ? ICodeDescResponse.ECHEC_DESCRIPTION
                                                    : responseResponse.getDescription());
                                    genericResponse.setCode(
                                            (responseResponse == null || responseResponse.getCode().equals("0000"))
                                                    ? ICodeDescResponse.ECHEC_CODE
                                                    : responseResponse.getCode());
                                    genericResponse.setDateResponse(Instant.now());
                                    genericResponse.setDescription(
                                            (responseResponse == null || responseResponse.getCode().equals("0000"))
                                                    ? ICodeDescResponse.ECHEC_DESCRIPTION
                                                    : responseResponse.getDescription());

                                    genericResponse.setExceptionResponse(exceptionResponse);
                                    // .getJSONObject("Fault").getString("faultstring"));
                                    tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE + "");
                                    tracking.setDateResponse(Instant.now());
                                    tracking.setEndPointTr(getBillsByRefService);
                                    tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
                                    tracking.setResponseTr(result);
                                    tracking.setTokenTr(tab[1]);
                                    tracking.setDateRequest(Instant.now());
                                }
                            } // END Split
                            else {// response normal
                                log.info("resp normal");
                                constructRespBillsRef(webServices, result, genericResponse,
                                        cardsRequest.getBillerCode(), cardsRequest.getLangue(), tracking, request,
                                        tab[1]);
                            }

                        } else {
                            // obj = null
                            constructRespBillsRef(webServices, null, genericResponse, cardsRequest.getBillerCode(),
                                    cardsRequest.getLangue(), tracking, request, tab[1]);
                        }

                    } else { // without split
                        constructRespBillsRef(webServices, result, genericResponse, cardsRequest.getBillerCode(),
                                cardsRequest.getLangue(), tracking, request, tab[1]);
                    }

                } else {// json 200
                    log.info("json ==> [{}]", result);
                    constructRespBillsRef(webServices, result, genericResponse, cardsRequest.getBillerCode(),
                            cardsRequest.getLangue(), tracking, request, tab[1]);

                }

            } else {// !=200
                genericResponse.setCode(String.valueOf(conn.getResponseCode()));
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String ligne = br.readLine();
                while (ligne != null) {
                    result += ligne;
                    ligne = br.readLine();
                }
                log.info("resp ===== [{}]", result);
                if (webServices.getProtocole().equalsIgnoreCase("xml")) {

                } else {
                    // resp !=200 for json
                    constructRespBillsRef(webServices, result, genericResponse, cardsRequest.getBillerCode(),
                            cardsRequest.getLangue(), tracking, request, tab[1]);
                }
            }
            // os.close();
        } catch (Exception e) {
            log.info("exception occur = [{}]", e.getMessage());
        }

        trackingService.save(tracking);
        return genericResponse;
    }

    private CheckFactoryResponse constructResp(WebServices webServices, String result,
            CheckFactoryResponse genericResponse, String billerCode, String langue, Tracking tracking,
            HttpServletRequest request, String token) throws Exception {
        log.info("json [{}]", result);
        if (result == null) {
            genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
            genericResponse.setDescription(ICodeDescResponse.RESPONSE_INC);
            genericResponse.setDateResponse(Instant.now());
            return genericResponse;
        }

        for (WebServiceParams it : webServices.getServiceParams()) {
            JSONObject object = new JSONObject(result);
            if (it.getParamSens().equalsIgnoreCase("O")) {
                log.info("p -> [{} ]", it.getParamName());
                if (object.toString().contains(it.getParamName())) {
                    Field variableName = genericResponse.getClass().getDeclaredField(it.getParamNameCorresp());
                    variableName.setAccessible(true);
                    if (it.getParentResponse() != null) {
                        String[] tt = it.getParentResponse().split("#");
                        for (int i = 0; i < tt.length; i++) {
                            if (object.toString().contains(tt[i])) {
                                log.info("tti [{}]", tt[i]);
                                object = object.getJSONObject(tt[i]);
                            }
                        }
                    }
                    if (it.getParamNameCorresp().equalsIgnoreCase("billAmount")
                            || it.getParamNameCorresp().equalsIgnoreCase("feeAmount")) {
                        Double amount = Double.valueOf(!StringUtils.isEmpty(object.get(it.getParamName()))
                                ? object.get(it.getParamName()).toString()
                                : "0");
                        variableName.set(genericResponse, amount);
                    } else
                        variableName.set(genericResponse, (object.get(it.getParamName())).toString());
                    // variableName.set(genericResponse, object.get(it.getParamName()).toString());
                }
            }
        }

        log.info("resp [{}]", genericResponse);
        ResponseRequest responseRequest = new ResponseRequest();
        responseRequest.setBillerCode(billerCode);
        responseRequest.setLangue(langue);
        // genericResponse.setCode(code);
        responseRequest.setRetourCode(genericResponse.getCode());
        responseRequest.setServiceName(webServices.getServiceName());

        ResponseResponse responseResponse = apiService.getResponse(responseRequest);
        genericResponse = (CheckFactoryResponse) apiService.clientAbsent(genericResponse, tracking,
                webServices.getServiceName(),
                (responseResponse == null /* || responseResponse.getCode().equals("0000") */)
                        ? genericResponse.getCode()
                        : responseResponse.getCode(),
                (responseResponse == null /* || responseResponse.getCode().equals("0000") */)
                        ? genericResponse.getDescription()
                        : responseResponse.getDescription(),
                request.getRequestURI(), token);
        return genericResponse;
    }

    private GetBillsByRefResponse constructRespBillsRef(WebServices webServices, String result,
            GetBillsByRefResponse genericResponse, String billerCode, String langue, Tracking tracking,
            HttpServletRequest request, String token) throws Exception {
        log.info("json [{}]", result);
        if (result == null) {
            genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
            genericResponse.setDescription(ICodeDescResponse.RESPONSE_INC);
            genericResponse.setDateResponse(Instant.now());
            return genericResponse;
        }
        JSONObject object = new JSONObject(result);
        JSONArray jsonArray = null;
        Field variableName = null;
        Boolean flag = false;
        List<ItemResp> itemResps = new ArrayList<>();
        for (WebServiceParams it : webServices.getServiceParams()) {
            if (it.getParamSens().equalsIgnoreCase("O")) {
                log.info("p -> [{}]", it.getParamName());
                if (object.toString().contains(it.getParamName())) {
                    Boolean resp = objectHasProperty(genericResponse, it.getParamNameCorresp());
                    if (resp) {
                        variableName = genericResponse.getClass().getDeclaredField(it.getParamNameCorresp());
                        variableName.setAccessible(true);
                        if (it.getParentResponse() != null) {
                            String[] tt = it.getParentResponse().split("#");
                            for (int i = 0; i < tt.length; i++) {
                                if (object.toString().contains(tt[i])) {
                                    log.info("tti [{}]", tt[i]);
                                    object = object.getJSONObject(tt[i]);
                                }
                            }
                        }
                        variableName.set(genericResponse, object.get(it.getParamName()).toString());
                    } else if (!flag) {
                        if (it.getParentResponse() != null) {
                            String[] tt = it.getParentResponse().split("#");
                            for (int i = 0; i < tt.length; i++) {
                                if (object.toString().contains(tt[i])) {
                                    log.info("tti [{}]", tt[i]);
                                    // add
                                    if (object.get(tt[i]) instanceof JSONArray)
                                        jsonArray = object.getJSONArray(tt[i]);
                                    else
                                        object = object.getJSONObject(tt[i]);
                                    if (jsonArray != null) {
                                        flag = true;
                                        for (int y = 0; y < jsonArray.length(); y++) {
                                            ItemResp itemResp = new ItemResp();
                                            JSONObject itTemp = jsonArray.getJSONObject(y);
                                            for (WebServiceParams itT : webServices.getServiceParams()) {
                                                if (itT.getParamSens().equalsIgnoreCase("O")) {
                                                    log.info("p => [{} et {}]", itT.getParamName(),
                                                            itT.getParamNameCorresp());
                                                    if (itTemp.get(it.getParamName()) != null) {
                                                        resp = objectHasProperty(itemResp, itT.getParamNameCorresp());
                                                        if (resp) {
                                                            Field variableName2 = itemResp.getClass()
                                                                    .getDeclaredField(itT.getParamNameCorresp());
                                                            variableName2.setAccessible(true);
                                                            if (itT.getParamNameCorresp().equalsIgnoreCase("billAmount")
                                                                    || itT.getParamNameCorresp()
                                                                            .equalsIgnoreCase("feeAmount")) {
                                                                Double amount = Double.valueOf(!StringUtils
                                                                        .isEmpty(object.get(itT.getParamName()))
                                                                                ? itTemp.get(itT.getParamName())
                                                                                        .toString()
                                                                                : "0");
                                                                variableName2.set(itemResp, amount);
                                                            } else
                                                                variableName2.set(itemResp,
                                                                        (itTemp.get(itT.getParamName())).toString());
                                                        }
                                                    }
                                                }
                                            }
                                            itemResps.add(itemResp);
                                            genericResponse.setBillList(itemResps);
                                            // genericResponse.getBillList().add(itemResp);
                                        }
                                        // genericResponse.getBillList().add(itemResp);
                                    }
                                    // end add
                                }
                            }
                        }
                    }
                }
            }
        }
        if (genericResponse.getBillList() == null || genericResponse.getBillList().size() == 0) {
            // log.info("ob= {}", object);
            log.info("resu= {}", result);

            ItemResp itemResp = new ItemResp();
            for (WebServiceParams it : webServices.getServiceParams()) {
                object = new JSONObject(result);
                if (it.getParamSens().equalsIgnoreCase("O")) {
                    log.info("p -> [{} - {}]", it.getParamName(), it.getParamNameCorresp());
                    if (object.toString().contains(it.getParamName())) {
                        if (it.getParentResponse() != null) {
                            String[] tt = it.getParentResponse().split("#");
                            for (int i = 0; i < tt.length; i++) {
                                if (object.toString().contains(tt[i])) {
                                    log.info("tti [{}]", tt[i]);
                                    object = object.getJSONObject(tt[i]);
                                }
                            }
                            Boolean resp = objectHasProperty(itemResp, it.getParamNameCorresp());
                            if (resp) {
                                Field variableName2 = itemResp.getClass().getDeclaredField(it.getParamNameCorresp());
                                variableName2.setAccessible(true);

                                if (it.getParamNameCorresp().equalsIgnoreCase("billAmount")
                                        || it.getParamNameCorresp().equalsIgnoreCase("feeAmount")) {

                                    Double amount = Double.valueOf(!StringUtils.isEmpty(object.get(it.getParamName()))
                                            ? object.get(it.getParamName()).toString()
                                            : "0");
                                    variableName2.set(itemResp, amount);
                                } else
                                    variableName2.set(itemResp, (object.get(it.getParamName())).toString());
                            } else {
                                variableName = genericResponse.getClass().getDeclaredField(it.getParamNameCorresp());
                                variableName.setAccessible(true);
                                variableName.set(genericResponse, object.get(it.getParamName()).toString());
                            }

                        }

                    }
                }
            }
            itemResps.add(itemResp);
            genericResponse.setBillList(itemResps);
        }

        log.info("resp [{}]", genericResponse);
        ResponseRequest responseRequest = new ResponseRequest();
        responseRequest.setBillerCode(billerCode);
        responseRequest.setLangue(langue);
        // genericResponse.setCode(code);
        responseRequest.setRetourCode(genericResponse.getCode());
        responseRequest.setServiceName(webServices.getServiceName());

        ResponseResponse responseResponse = apiService.getResponse(responseRequest);
        genericResponse = (GetBillsByRefResponse) apiService.clientAbsent(genericResponse, tracking,
                webServices.getServiceName(),
                (responseResponse == null /* || responseResponse.getCode().equals("0000") */)
                        ? genericResponse.getCode()
                        : responseResponse.getCode(),
                (responseResponse == null /* || responseResponse.getCode().equals("0000") */)
                        ? genericResponse.getDescription()
                        : responseResponse.getDescription(),
                request.getRequestURI(), token);
        return genericResponse;
    }

    private String headerRequest() {
        StringBuilder builder = new StringBuilder();
        builder.append("<send_request><request>");
        return builder.toString();
    }

    private String footerRequest(String link, String content) {
        StringBuilder builder = new StringBuilder();
        builder.append("</request>");
        builder.append("<url_link>" + link + "</url_link>");
        builder.append("<url_content>" + content + "</url_content>");
        builder.append("</send_request>");
        return builder.toString();
    }

    private Boolean objectHasProperty(Object obj, String propertyName) {
        List<Field> properties = getAllFields(obj);
        for (Field field : properties) {
            if (field.getName().equalsIgnoreCase(propertyName)) {
                return true;
            }
        }
        return false;
    }

    private static List<Field> getAllFields(Object obj) {
        List<Field> fields = new ArrayList<Field>();
        getAllFieldsRecursive(fields, obj.getClass());
        return fields;
    }

    private static List<Field> getAllFieldsRecursive(List<Field> fields, Class<?> type) {
        for (Field field : type.getDeclaredFields()) {
            fields.add(field);
        }

        if (type.getSuperclass() != null) {
            fields = getAllFieldsRecursive(fields, type.getSuperclass());
        }

        return fields;
    }

    public PayementResponse payBill(PayementRequest payementRequest, HttpServletRequest request) {
        PayementResponse genericResponse = new PayementResponse();
        Tracking tracking = new Tracking();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        WebServices webServices = webServicesService.getWebServiceByParams(payementRequest.getBillerCode(),
                payBillService);
        // log.info("ws = [{}]", webServices);
        if (webServices == null) {
            return genericResponse = (PayementResponse) apiService.clientAbsent(genericResponse, tracking,
                    getBillService, ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    payementRequest.toString(), tab[1]);
        }
        String requestParam = webServices.getXmlRequest();
        // set params In based in nameCorresp
        for (WebServiceParams it : webServices.getServiceParams()) {
            if (it.getParamSens().equalsIgnoreCase("I")) {
                log.info("inf = [{}]", it.getParamNameCorresp());
                try {
                    Object fs = new PropertyDescriptor(it.getParamNameCorresp(), PayementRequest.class).getReadMethod()
                            .invoke(payementRequest);
                    log.info("invoke = [{}]", fs);
                    requestParam = requestParam.replace("#" + it.getOrdre() + "#", fs.toString());
                } catch (Exception e1) {
                    return genericResponse = (PayementResponse) apiService.clientAbsent(genericResponse, tracking,
                            payBillService, ICodeDescResponse.ECHEC_CHAMP_CODE, ICodeDescResponse.ECHEC_CHAMP_DESC,
                            payementRequest.toString(), tab[1]);
                }
            }
        }
        log.info("req PayementRequest  = [{}]", requestParam);
        if(payementRequest.getBillerCode().equalsIgnoreCase(applicationProperties.getCodeJirama())){
            ObjectMapper mapper = new ObjectMapper();
            PayementRequest jirPayementRequest = new PayementRequest();
            try {
                jirPayementRequest = mapper.readValue(requestParam, PayementRequest.class);
                genericResponse = apiService.payBill(jirPayementRequest, request);
                return genericResponse;
            } catch (Exception e) {
                String msgErr = "Exception lors du parsing de PayementRequest =" + requestParam;
                log.info(msgErr+" {}", e);
            }
        }

        GetBillRequest billRequest = new GetBillRequest();
        billRequest.setBillerCode(payementRequest.getBillerCode());// TODO payementRequest.getBillerCode()
        billRequest.setLangue(payementRequest.getLangue());
        billRequest.setBillNum(payementRequest.getBillNum());
        billRequest.setCashingRef(payementRequest.getCashingRef());
        billRequest.setChannelType(payementRequest.getChannelType());
        billRequest.setRequierNumber(payementRequest.getPhoneNumber());
        CheckFactoryResponse checkFactoryResponse = checkFactory(billRequest, request);
        if (checkFactoryResponse == null || checkFactoryResponse.getBillAmount() == null) {
            genericResponse = (PayementResponse) apiService.clientAbsent(genericResponse, tracking,
                    "getBill in paiement", ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.FACTURE_NON_TROUVE,
                    request.getRequestURI(), tab[1]);
            return genericResponse;
        }

        GetAccountRequest accountRequest = new GetAccountRequest();
        accountRequest.setAccountType(ICodeDescResponse.ACCOUNT_PRINCIPAL);
        accountRequest.setBillerCode(payementRequest.getBillerCode());
        GetAccountResponse accountResponse = apiService.getBillerAccount(accountRequest, request);
        if (accountResponse == null || accountResponse.getNumAccount() == null) {
            genericResponse = (PayementResponse) apiService.clientAbsent(genericResponse, tracking,
                    "getBillAccount in paiement", ICodeDescResponse.ECHEC_CODE,
                    ICodeDescResponse.ACCOUNT_PRINCIPAL_NON_TROUVE, request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        BillerByCodeRequest billerByCodeRequest = new BillerByCodeRequest();
        billerByCodeRequest.setBillerCode(payementRequest.getBillerCode());
        BillerByCodeResponse billerByCodeResponse = apiService.getBillerByCode(billerByCodeRequest, request);
        if (billerByCodeResponse == null || billerByCodeResponse.getBILLERCODE() == null) {
            genericResponse = (PayementResponse) apiService.clientAbsent(genericResponse, tracking,
                    "getBillerByCode in paiement", ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.BILLER_NON_TROUVE,
                    request.getRequestURI(), tab[1]);
            return genericResponse;
        }

        GetBillFeesRequest billFeesRequest = new GetBillFeesRequest();
        billFeesRequest.setBillerCode(payementRequest.getBillerCode());// TODO JIRAMA pour tester le mock
        billFeesRequest.setMontant(checkFactoryResponse.getBillAmount().toString());
        billFeesRequest.setTypeCanal(payementRequest.getChannelType());
        GetBillFeesResponse billFeesResponse = apiService.getBillFees(billFeesRequest, request);
        if (billFeesResponse == null || billFeesResponse.getMontantFrais() == null) {
            genericResponse = (PayementResponse) apiService.clientAbsent(genericResponse, tracking,
                    "getBillFees in paiement", ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.FEES_NON_TROUVE,
                    request.getRequestURI(), tab[1]);
            return genericResponse;
        }

        payementRequest.setSessionNum(checkFactoryResponse.getSessionNum());
        payementRequest.setCustumerRef(checkFactoryResponse.getCustumerRef());
        payementRequest.setTelBiller(billerByCodeResponse.getTELEPHONE());
        payementRequest.setAmount(checkFactoryResponse.getBillAmount());
        payementRequest.setFees(billFeesResponse.getMontantFrais());
        payementRequest.setUrlLink(webServices.getEndPoint());
        payementRequest.setCompteDeb(payementRequest.getCustomerAccount());
        payementRequest.setCompteCredit(accountResponse.getNumAccount());
        payementRequest.setDevise(billerByCodeResponse.getDEVISE());
        payementRequest.setPays(billerByCodeResponse.getPAYS());
        payementRequest.setContent(webServices.getProtocole().toUpperCase());

        

        URL url;
        OutputStream os = null;
        String urlRequest = webServices.getEndPointExpose();
        try {
            if (webServices.getAttribute02().equalsIgnoreCase("GET")) {
                urlRequest += requestParam;
            }
            url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String typApp = webServices.getProtocole().equalsIgnoreCase("xml") ? "application/xml" : "application/json";
            conn.setDoOutput(true);
            conn.setRequestMethod(webServices.getAttribute02().toUpperCase());
            conn.setRequestProperty("Content-Type", typApp);
            conn.setRequestProperty("Accept", typApp);
            String result = "";
            if (webServices.getToken() != null)
                conn.setRequestProperty("Authorization", webServices.getToken());
            StringBuilder builder = new StringBuilder();
            String reqStr = "";
            if (!webServices.getAttribute02().equalsIgnoreCase("GET") && webServices.getAttribute01() != null) {
                builder.append(requestParam);
                reqStr = builder.toString();
                os = conn.getOutputStream();
                byte[] postDataBytes = builder.toString().getBytes();
                log.info("req= [{}]", reqStr);
                os.write(postDataBytes);
                os.flush();
            } else if (!webServices.getAttribute02().equalsIgnoreCase("GET")){
                os = conn.getOutputStream();
                byte[] postDataBytes = requestParam.getBytes();

                os.write(postDataBytes);
                os.flush();
                conn.connect();
                reqStr = urlRequest;
            }else {
                os = conn.getOutputStream();
                // byte[] postDataBytes = requestParam.getBytes();

                // os.write(postDataBytes);
                os.flush();
                conn.connect();
                reqStr = urlRequest;
            }
            tracking.setRequestTr(reqStr);
            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                genericResponse.setCode(String.valueOf(conn.getResponseCode()));
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String ligne = br.readLine();
                while (ligne != null) {
                    result += ligne;
                    ligne = br.readLine();
                }
                log.info("resp ===== [{}]", result);
                tracking.setResponseTr(result);
                if (webServices.getProtocole().equalsIgnoreCase("xml")) {
                    String split = null;
                    if (webServices.getAttribute01() != null)
                        split = webServices.getAttribute01();
                    if (split != null) {
                        // br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        // result = br.readLine();
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

                            ResponseResponse responseResponse = apiService.getResponse(responseRequest);

                            exceptionResponse.setNumber(tabErr[0].trim());
                            exceptionResponse.setDescription(tabErr[1]);
                            genericResponse
                                    .setCode((responseResponse == null || responseResponse.getCode().equals("0000"))
                                            ? ICodeDescResponse.ECHEC_CODE
                                            : responseResponse.getCode());
                            genericResponse.setDateResponse(Instant.now());
                            genericResponse.setDescription(
                                    (responseResponse == null || responseResponse.getCode().equals("0000"))
                                            ? ICodeDescResponse.ECHEC_DESCRIPTION
                                            : responseResponse.getDescription());

                            // AnnulationPaiement annulationPaiement = new AnnulationPaiement();
                            log.info("err= [{}]", errObj.toString());
                            log.info("err= [{}]", obj.toString());
                            /*
                             * annulationPaiement.setCode(obj.getJSONObject("annulation").getString("PCOD"))
                             * ; annulationPaiement.setResultat(obj.getJSONObject("annulation").getString(
                             * "PMSG")); genericResponse.setAnnulationPaiement(annulationPaiement);
                             */
                            genericResponse.setAnnulationCode(obj.getJSONObject("annulation").getString("PCOD"));
                            genericResponse.setAnnulationMsg(obj.getJSONObject("annulation").getString("PMSG"));
                            // PaieMobile paieMobile = new PaieMobile();
                            // genericResponse.setPaieMobile(paieMobile);
                            // genericResponse.setExceptionResponse(exceptionResponse);
                            TransactionGlobal trG = apiService.createTransaction(payementRequest,
                                    genericResponse.getCode(), checkFactoryResponse.getCustomerName(),
                                    accountResponse.getNumAccount(), billFeesResponse.getMontantFrais(),
                                    checkFactoryResponse.getBillNum(), checkFactoryResponse.getCustumerRef(),
                                    checkFactoryResponse.getBillAmount(), responseResponse.getDescription());
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
                            ResponseResponse responseResponse = apiService.getResponse(responseRequest);

                            genericResponse
                                    .setCode((responseResponse == null || responseResponse.getCode().equals("0000"))
                                            ? responseRequest.getRetourCode()
                                            : responseResponse.getCode());
                            genericResponse.setDateResponse(Instant.now());
                            genericResponse.setDescription(
                                    (responseResponse == null || responseResponse.getCode().equals("0000"))
                                            ? obj.getString("PMSG")
                                            : responseResponse.getDescription());

                            TransactionGlobal trG = apiService.createTransaction(payementRequest,
                                    genericResponse.getCode(), checkFactoryResponse.getCustomerName(),
                                    accountResponse.getNumAccount(), billFeesResponse.getMontantFrais(),
                                    checkFactoryResponse.getBillNum(), checkFactoryResponse.getCustumerRef(),
                                    checkFactoryResponse.getBillAmount(), responseResponse.getDescription());
                            log.info("transaction saved [{}]", trG);

                            tracking.setCodeResponse(
                                    (responseResponse == null || responseResponse.getCode().equals("0000"))
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
                        /*
                         * String[] tabPath = webServices.getResponsePath().split("\\$"); //
                         * "Paie_MobileResult$paie_mobile" String[] pathResp = null; if
                         * (result.contains("paiemobile")) { pathResp = tabPath[0].split(split);
                         * log.info("tab [{}]", tabPath[0]); } else { pathResp =
                         * tabPath[1].split(split); } log.info("taille path resp [{}]",
                         * pathResp.length); obj = new JSONObject(result).getJSONObject(pathResp[0]); if
                         * (obj != null) { result = obj.toString(); log.info("result [{}]", result);
                         * 
                         * if (result != null && result.contains(pathResp[pathResp.length - 1])) {
                         * JSONObject itObj = new JSONObject(); itObj = obj; for (int i = 1; i <
                         * pathResp.length - 1; i++) { log.info("pathResp[i] => [{}] et {}",
                         * pathResp[i], i); itObj = itObj.getJSONObject(pathResp[i]);
                         * log.info("iiObj [{}]", itObj.toString()); } result =
                         * itObj.getString(pathResp[pathResp.length - 1]); log.info("ok [{}]", result);
                         * 
                         * if (result.contains("P000") || result.contains("0401")) {// ExceptionResponse
                         * exceptionResponse = new ExceptionResponse(); String[] tabErr = new String[2];
                         * tabErr = result.split("#"); ResponseRequest responseRequest = new
                         * ResponseRequest();
                         * responseRequest.setBillerCode(payementRequest.getBillerCode());
                         * responseRequest.setLangue(payementRequest.getLangue());
                         * responseRequest.setRetourCode(tabErr[0].trim());
                         * responseRequest.setServiceName(ICodeDescResponse.SERVICE_PAIEMENT);
                         * 
                         * ResponseResponse responseResponse = apiService.getResponse(responseRequest);
                         * 
                         * exceptionResponse.setNumber(tabErr[0].trim());
                         * exceptionResponse.setDescription(tabErr[1]); genericResponse.setCode(
                         * (responseResponse == null || responseResponse.getCode().equals("0000")) ?
                         * ICodeDescResponse.ECHEC_CODE : responseResponse.getCode());
                         * genericResponse.setDateResponse(Instant.now());
                         * genericResponse.setDescription( (responseResponse == null ||
                         * responseResponse.getCode().equals("0000")) ?
                         * ICodeDescResponse.ECHEC_DESCRIPTION : responseResponse.getDescription());
                         * 
                         * AnnulationPaiement annulationPaiement = new AnnulationPaiement();
                         * annulationPaiement.setCode(obj.getJSONObject("response")//
                         * .getJSONObject("paiemobile") .getJSONObject("annulation").getString("PCOD"));
                         * annulationPaiement.setResultat(obj.getJSONObject("response")
                         * .getJSONObject("annulation").getString("PMSG"));
                         * genericResponse.setAnnulationPaiement(annulationPaiement); TransactionGlobal
                         * trG = apiService.createTransaction(payementRequest,
                         * genericResponse.getCode(), checkFactoryResponse.getCustomerName(),
                         * accountResponse.getNumAccount(), billFeesResponse.getMontantFrais(),
                         * checkFactoryResponse.getBillNum(), checkFactoryResponse.getCustumerRef(),
                         * checkFactoryResponse.getBillAmount(), responseResponse.getDescription());
                         * log.info("transaction saved [{}]", trG);
                         * 
                         * tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE + "");
                         * tracking.setDateResponse(Instant.now()); tracking.setEndPointTr("payBill");
                         * tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin()
                         * ); tracking.setResponseTr(result); tracking.setTokenTr(tab[1]);
                         * tracking.setDateRequest(Instant.now());
                         * 
                         * } else { // good resp split ResponseRequest responseRequest = new
                         * ResponseRequest();
                         * responseRequest.setBillerCode(payementRequest.getBillerCode());
                         * responseRequest.setLangue(payementRequest.getLangue());
                         * responseRequest.setRetourCode(obj.getJSONObject("response").getString("PCOD")
                         * ); responseRequest.setServiceName(ICodeDescResponse.SERVICE_PAIE_MOBILE);
                         * ResponseResponse responseResponse = apiService.getResponse(responseRequest);
                         * 
                         * genericResponse.setCode( (responseResponse == null ||
                         * responseResponse.getCode().equals("0000")) ? responseRequest.getRetourCode()
                         * : responseResponse.getCode());
                         * genericResponse.setDateResponse(Instant.now());
                         * genericResponse.setDescription( (responseResponse == null ||
                         * responseResponse.getCode().equals("0000")) ? obj.getString("PMSG") :
                         * responseResponse.getDescription());
                         * 
                         * TransactionGlobal trG = apiService.createTransaction(payementRequest,
                         * genericResponse.getCode(), checkFactoryResponse.getCustomerName(),
                         * accountResponse.getNumAccount(), billFeesResponse.getMontantFrais(),
                         * checkFactoryResponse.getBillNum(), checkFactoryResponse.getCustumerRef(),
                         * checkFactoryResponse.getBillAmount(), responseResponse.getDescription());
                         * log.info("transaction saved [{}]", trG);
                         * 
                         * tracking.setCodeResponse( (responseResponse == null ||
                         * responseResponse.getCode().equals("0000")) ? ICodeDescResponse.SUCCES_CODE :
                         * responseResponse.getCode()); tracking.setDateResponse(Instant.now());
                         * tracking.setEndPointTr("payBill");
                         * tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin()
                         * ); tracking.setResponseTr(result); tracking.setTokenTr(tab[1]);
                         * tracking.setDateRequest(Instant.now()); } } // END Split else {// response
                         * normal
                         * 
                         * log.info("resul ==null exepti resp normal");
                         * 
                         * }
                         * 
                         * } else { // obj = null
                         * 
                         * }
                         */

                    } else { // without split
                        constructRespPay(webServices, result, genericResponse, payementRequest.getBillerCode(),
                                payementRequest.getLangue(), tracking, request, tab[1], payementRequest,
                                checkFactoryResponse, accountResponse, billFeesResponse);
                    }

                } else {// json 200
                    log.info("json ==> [{}]", result);
                    constructRespPay(webServices, result, genericResponse, payementRequest.getBillerCode(),
                            payementRequest.getLangue(), tracking, request, tab[1], payementRequest,
                            checkFactoryResponse, accountResponse, billFeesResponse);

                }

            } else {
                genericResponse.setCode(String.valueOf(conn.getResponseCode()));
                // resp != 200
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String ligne = br.readLine();
                while (ligne != null) {
                    result += ligne;
                    ligne = br.readLine();
                }
                log.info("resp ===== [{}]", result);
                if (webServices.getProtocole().equalsIgnoreCase("xml")) {

                } else {
                    // resp !=200 for json
                    constructRespPay(webServices, result, genericResponse, payementRequest.getBillerCode(),
                            payementRequest.getLangue(), tracking, request, tab[1], payementRequest,
                            checkFactoryResponse, accountResponse, billFeesResponse);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        trackingService.save(tracking);
        return genericResponse;
    }

    private PayementResponse constructRespPay(WebServices webServices, String result, PayementResponse genericResponse,
            String billerCode, String langue, Tracking tracking, HttpServletRequest request, String token,
            PayementRequest payementRequest, CheckFactoryResponse checkFactoryResponse,
            GetAccountResponse accountResponse, GetBillFeesResponse billFeesResponse) throws Exception {
        log.info("json [{}]", result);
        if (result == null) {
            genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
            genericResponse.setDescription(ICodeDescResponse.RESPONSE_INC);
            genericResponse.setDateResponse(Instant.now());
            return genericResponse;
        }
        Integer k = 0;
        if (result.contains("0700") || result.contains("0401")) {
            k = 0;
        } else
            k = 1;

        for (WebServiceParams it : webServices.getServiceParams()) {
            JSONObject object = new JSONObject(result);
            if (it.getParamSens().equalsIgnoreCase("O")) {
                log.info("p -> [{} ]", it.getParamName());
                if (object.toString().contains(it.getParamName())) {
                    Field variableName = genericResponse.getClass().getDeclaredField(it.getParamNameCorresp());
                    variableName.setAccessible(true);
                    if (it.getParentResponse() != null) {
                        String path = it.getParentResponse();
                        if (it.getParentResponse().contains("\\$")) {
                            path = it.getParentResponse().split("\\$")[k];
                        }

                        String[] tt = path.split("#");// it.getParentResponse().split("#");
                        for (int i = 0; i < tt.length; i++) {
                            if (object.toString().contains(tt[i])) {
                                log.info("tti [{}]", tt[i]);
                                object = object.getJSONObject(tt[i]);
                            }
                        }
                    }
                    variableName.set(genericResponse, object.get(it.getParamName()).toString());
                }
            }
        }
        if (k == 0) {
            genericResponse.setAnnulationCode(null);
            genericResponse.setAnnulationMsg(null);
        }
        log.info("resp [{}]", genericResponse);
        ResponseRequest responseRequest = new ResponseRequest();
        responseRequest.setBillerCode(billerCode);
        responseRequest.setLangue(langue);
        // genericResponse.setCode(code);
        responseRequest.setRetourCode(genericResponse.getCode());
        responseRequest.setServiceName(webServices.getServiceName());

        ResponseResponse responseResponse = apiService.getResponse(responseRequest);
        genericResponse = (PayementResponse) apiService.clientAbsent(genericResponse, tracking,
                webServices.getServiceName(), 
                (responseResponse == null /* || responseResponse.getCode().equals("0000") */)
                        ? genericResponse.getCode()
                        : responseResponse.getCode(),
                (responseResponse == null /* || responseResponse.getCode().equals("0000") */)
                        ? genericResponse.getDescription()
                        : responseResponse.getDescription(),
                request.getRequestURI(), token); 
        TransactionGlobal trG = apiService.createTransaction(payementRequest, genericResponse.getCode(),
                checkFactoryResponse.getCustomerName(), accountResponse.getNumAccount(),
                billFeesResponse.getMontantFrais(), checkFactoryResponse.getBillNum(),
                checkFactoryResponse.getCustumerRef(), checkFactoryResponse.getBillAmount(),
                responseResponse.getDescription());
        return genericResponse;
    }

    /*
     * public static void main(String[] args) throws ParserConfigurationException,
     * SAXException, IOException { //import org.w3c.dom.*; //import
     * javax.xml.parsers.*; //import java.io.*; String xmlStr= "<employees>"+
     * "<employee id='111'>"+ "<firstName>Lokesh</firstName>"+
     * "<lastName>Gupta</lastName>"+ "<location>India</location>"+ "</employee>"+
     * "<employee id='222'>"+ "<firstName>Alex</firstName>"+
     * "<lastName>Gussin</lastName>"+ "<location>Russia</location>"+ "</employee>"+
     * "<employee id='333'>"+ "<firstName>David</firstName>"+
     * "<lastName>Feezor</lastName>"+ "<location>USA</location>"+ "</employee>"+
     * "</employees>"; //Get Document Builder DocumentBuilderFactory factory =
     * DocumentBuilderFactory.newInstance(); DocumentBuilder builder =
     * factory.newDocumentBuilder(); //Build Document Document document =
     * builder.parse(new InputSource(new StringReader(xmlStr))); //Document document
     * = builder.parse(xmlStr); //Normalize the XML Structure; It's just t
     * //Normalize the XML Structure; It's just too important !!oo important !!
     * document.getDocumentElement().normalize(); //Here comes the root node Element
     * root = document.getDocumentElement();
     * System.out.println("root nodename===="+root.getNodeName()); //Get all
     * employees NodeList nList = document.getElementsByTagName("employee");
     * //returns specific attribute root.getAttributes(); //returns a Map (table) of
     * names/values
     * System.out.println("root Attributes="+root.getAttributes().toString());
     * System.out.println("============================"); for (int temp = 0; temp <
     * nList.getLength(); temp++) { Node node = nList.item(temp);
     * System.out.println(""); //Just a separator if (node.getNodeType() ==
     * Node.ELEMENT_NODE) { //Print each employee's detail Element eElement =
     * (Element) node; System.out.println("Employee id : " +
     * eElement.getAttribute("id")); System.out.println("First Name : " +
     * eElement.getElementsByTagName("firstName").item(0).getTextContent());
     * System.out.println("Last Name : " +
     * eElement.getElementsByTagName("lastName").item(0).getTextContent());
     * System.out.println("Location : " +
     * eElement.getElementsByTagName("location").item(0).getTextContent()); } }
     * 
     * }
     */
    public static void main(String[] args) {
        String str = "paie_mobile#response#paiemobile#PMSG#Envelope#Body#Paie_MobileResponse#Paie_MobileResult$paie_mobile#response#PMSG#Envelope#Body#Paie_MobileResponse#Paie_MobileResult";
        String[] tab = str.split("\\$");
        System.out.println("******************");
        for (String string : tab) {
            System.out.println("tab " + string);
        }
    }

    public NotificationPaiementResponse notificationPaiement(NotificationPaiementRequest nPaiementRequest,
            HttpServletRequest request) {
        log.info("in service notificationPaiement [{}]", nPaiementRequest);
        NotificationPaiementResponse genericResponse = new NotificationPaiementResponse();
        Tracking tracking = new Tracking();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");

        GetAccountRequest accountRequest = new GetAccountRequest();
        accountRequest.setAccountType(ICodeDescResponse.ACCOUNT_PRINCIPAL);
        accountRequest.setBillerCode(nPaiementRequest.getBillerCode());
        GetAccountResponse accountResponse = apiService.getBillerAccount(accountRequest, request);
        if (accountResponse == null || accountResponse.getNumAccount() == null) {
            genericResponse = (NotificationPaiementResponse) apiService.clientAbsent(genericResponse, tracking,
                    "getBillAccount in paiement", ICodeDescResponse.ECHEC_CODE,
                    ICodeDescResponse.ACCOUNT_PRINCIPAL_NON_TROUVE, request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        nPaiementRequest.setCompteCredit(accountResponse.getNumAccount());
        nPaiementRequest.valDisponible("V").disponible("DISPONIBLE").libelle("Paiement CNPS");

        GetBillFeesRequest billFeesRequest = new GetBillFeesRequest();
        billFeesRequest.setBillerCode(nPaiementRequest.getBillerCode());
        billFeesRequest.setMontant(nPaiementRequest.getAmount().toString());// TODO montant du getBill
        billFeesRequest.setTypeCanal(nPaiementRequest.getCanal());
        GetBillFeesResponse billFeesResponse = apiService.getBillFees(billFeesRequest, request);
        if (billFeesResponse == null || billFeesResponse.getMontantFrais() == null) {
            genericResponse = (NotificationPaiementResponse) apiService.clientAbsent(genericResponse, tracking,
                    "getBillFees in paiement", ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.FEES_NON_TROUVE,
                    request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        nPaiementRequest.setMntfrais(billFeesResponse.getMontantFrais());
        // TODO call paiement wso2 if success process

        WebServices webServices = webServicesService.getWebServiceByParams(nPaiementRequest.getBillerCode(),
                notifPaiementService);
        log.info("ws = [{}]", webServices);
        if (webServices == null) {
            return genericResponse = (NotificationPaiementResponse) apiService.clientAbsent(genericResponse, tracking,
                    getBillService, ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    nPaiementRequest.toString(), tab[1]);
        }
        String requestParam = webServices.getXmlRequest();
        for (WebServiceParams it : webServices.getServiceParams()) {
            if (it.getParamSens().equalsIgnoreCase("I")) {
                log.info("inf = [{}]", it.getParamNameCorresp());
                try {
                    Object fs = new PropertyDescriptor(it.getParamNameCorresp(), NotificationPaiementRequest.class)
                            .getReadMethod().invoke(nPaiementRequest);
                    log.info("invoke = [{}]", fs);
                    requestParam = requestParam.replace("#" + it.getOrdre() + "#", fs.toString());
                } catch (Exception e1) {
                    log.error("err = [{}]", e1.getMessage());
                    e1.fillInStackTrace();
                    return genericResponse = (NotificationPaiementResponse) apiService.clientAbsent(genericResponse,
                            tracking, getBillService, ICodeDescResponse.ECHEC_CHAMP_CODE,
                            ICodeDescResponse.ECHEC_CHAMP_DESC, nPaiementRequest.toString(), tab[1]);
                }

            }
        }
        log.info("req notif after  = [{}]", requestParam);
        HttpURLConnection conn = null;
        try {
            conn = utils.doConnexion(webServices.getEndPointExpose(), requestParam, webServices.getProtocole(), null,
                    null, false, null, null, webServices.getAttribute02());
            BufferedReader br = null;
            // JSONObject obj = new JSONObject();
            String result = "";
            log.info("resp code notif paiement [{}]", (conn != null ? conn.getResponseCode() : ""));
            if (conn.getResponseCode() == 200) {
                genericResponse.setCode(String.valueOf(conn.getResponseCode()));
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String ligne = br.readLine();
                while (ligne != null) {
                    result += ligne;
                    ligne = br.readLine();
                }
                log.info("resp notif paiement ===== [{}]", result);
                tracking.setResponseTr(result);

                constructRespNotif(webServices, result, genericResponse, nPaiementRequest.getLangue(),
                         tracking, request, tab[1], nPaiementRequest, accountResponse, billFeesResponse);
                log.info("json ==> [{}]", result);

            } else {// !=200
                genericResponse.setCode(String.valueOf(conn.getResponseCode()));
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String ligne = br.readLine();
                while (ligne != null) {
                    result += ligne;
                    ligne = br.readLine();
                }
                log.info("resp ===== [{}]", result);

                // resp !=200 for json
                constructRespNotif(webServices, result, genericResponse, nPaiementRequest.getLangue(),
                         tracking, request, tab[1], nPaiementRequest, accountResponse, billFeesResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("err = [{}]", e.getMessage());
            // e.fillInStackTrace();
            return genericResponse = (NotificationPaiementResponse) apiService.clientAbsent(genericResponse, tracking,
                    getBillService, ICodeDescResponse.ECHEC_CHAMP_CODE, ICodeDescResponse.ECHEC_CHAMP_DESC,
                    nPaiementRequest.toString(), tab[1]);
        }

        return genericResponse;
    }

    private NotificationPaiementResponse constructRespNotif(WebServices webServices, String result,
            NotificationPaiementResponse genericResponse, String langue, Tracking tracking,
            HttpServletRequest request, String token, NotificationPaiementRequest paiementRequest,
            GetAccountResponse accountResponse, GetBillFeesResponse billFeesResponse) throws Exception {
        log.info("resul json to build [{}]", result);
        if (result == null) {
            genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
            genericResponse.setDescription(ICodeDescResponse.RESPONSE_INC);
            genericResponse.setDateResponse(Instant.now());
            return genericResponse;
        }

        for (WebServiceParams it : webServices.getServiceParams()) {
            JSONObject object = new JSONObject(result);
            if (it.getParamSens().equalsIgnoreCase("O")) {
                log.info("p -> [{} ]", it.getParamName());
                if (object.toString().contains(it.getParamName())) {
                    Field variableName = genericResponse.getClass().getDeclaredField(it.getParamNameCorresp());
                    variableName.setAccessible(true);
                    Boolean flag = false;
                    if (it.getParentResponse() != null) {
                        String[] tt = it.getParentResponse().split("#");
                        
                        for (int i = 0; i < tt.length; i++) {
                            flag = false;
                            if (object.toString().contains(tt[i])) {
                                log.info("tti [{}]", tt[i]);
                                object = object.getJSONObject(tt[i]);
                                flag = true;
                            }
                        }
                    }
                    if (it.getParamNameCorresp().equalsIgnoreCase("amount")
                            || it.getParamNameCorresp().equalsIgnoreCase("feeAmount")) {

                        Double amount = Double.valueOf(!StringUtils.isEmpty(object.get(it.getParamName()))
                                ? object.get(it.getParamName()).toString()
                                : "0");
                        variableName.set(genericResponse, amount);
                    } else if(flag)
                        variableName.set(genericResponse, (object.get(it.getParamName())).toString());
                    // variableName.set(genericResponse, object.get(it.getParamName()).toString());
                }
            }
        }

        log.info("resp generic notif [{}]", genericResponse);
        ResponseRequest responseRequest = new ResponseRequest();
        responseRequest.setBillerCode(paiementRequest.getBillerCode());
        responseRequest.setLangue(langue);
        // genericResponse.setCode(code);
        responseRequest.setRetourCode(genericResponse.getCode());
        responseRequest.setServiceName(webServices.getServiceName());

        ResponseResponse responseResponse = apiService.getResponse(responseRequest);
        genericResponse = (NotificationPaiementResponse) apiService.clientAbsent(genericResponse, tracking,
                webServices.getServiceName(),
                (responseResponse == null /* || responseResponse.getCode().equals("0000") */)
                        ? genericResponse.getCode()
                        : responseResponse.getCode(),
                (responseResponse == null /* || responseResponse.getCode().equals("0000") */)
                        ? genericResponse.getDescription()
                        : responseResponse.getDescription(),
                request.getRequestURI(), token);
        TransactionGlobal trG = apiService.createTransactionB(paiementRequest.getBillerCode(), paiementRequest.getCanal(),
          paiementRequest.getCompteDeb(), paiementRequest.getBillerReference(), genericResponse.getCode(),
                "nameCustommer", accountResponse.getNumAccount(),billFeesResponse.getMontantFrais(), paiementRequest.getBillNum(),
                "", paiementRequest.getAmount(),responseResponse.getDescription(), "");
                //(String billerCode, String canal, String compteDeb,String reference, String codeRetour,
    //String nom, String creditAccount, Double frais, String numFacture, String client, Double montant,
    //String msgFr, String telephone)
        return genericResponse;
    }
}