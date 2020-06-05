package com.boa.api.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.boa.api.domain.ParamFiliale;
import com.boa.api.domain.Tracking;
import com.boa.api.repository.ParamFilialeRepository;
import com.boa.api.request.HistoPaiementClientRequest;
import com.boa.api.request.HistoPaiementFacRequest;
import com.boa.api.response.HistoPaiementClientResponse;
import com.boa.api.response.HistoPaiment;
import com.boa.api.service.util.ICodeDescResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HistoriquePaiementService {

	private final Logger log = LoggerFactory.getLogger(HistoriquePaiementService.class);
	private final ParamFilialeRepository paramFilialeRepository;
	private final ApiService apiService;
	private final UserService userService;
	private final TrackingService trackingService;

	public HistoriquePaiementService(ParamFilialeRepository paramFilialeRepository,
	ApiService apiService, UserService userService, TrackingService trackingService){
		this.paramFilialeRepository = paramFilialeRepository;
		this.apiService = apiService;
		this.userService = userService;
		this.trackingService = trackingService;
	}

    public HistoPaiementClientResponse histoClient(HistoPaiementClientRequest paiementClientRequest,
			HttpServletRequest request) {
		HistoPaiementClientResponse response = new HistoPaiementClientResponse();
		ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("histoClient");
        Tracking tracking = new Tracking();
		String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        if (filiale == null) {
            response = (HistoPaiementClientResponse) apiService.clientAbsent(response, tracking, "getBill",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);

            return response;
        }
		OutputStream os = null;
		String result = "";
        try {
			log.info("end point wso2== [{}]", filiale.getEndPoint());
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
			//conn.setRequestProperty("Accept", "application/json");
			
			String jsonString = new JSONObject()
			.put("biller_code", paiementClientRequest.getCodeFacturier()) 
			.put("biller_account", paiementClientRequest.getAccountCustomer())
			.toString();
            tracking.setRequestTr(jsonString);
            os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = null;
            log.info("resp code [{}]", conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String ligne = br.readLine();
                while (ligne != null) {
                    result += ligne;
                    ligne = br.readLine();
                }
				log.info("resp ===== [{}]", result);
				obj = new JSONObject(result); 
				log.info("resp ===== [{}]", obj.toString());
				//if(obj.has("Transaction")){
				if(!obj.isNull("Transactions")){
					obj = obj.getJSONObject("Transactions");
					//obj = obj.getJSONObject("Transaction");
					response.setCode(ICodeDescResponse.SUCCES_CODE);
					response.setDateResponse(Instant.now());
					response.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
					JSONArray array = obj.getJSONArray("Transaction");
					if(array!=null){
						for (int i=0; i < array.length(); i++) {
							JSONObject myObj = array.getJSONObject(i);
							HistoPaiment histoPaiment = new HistoPaiment();
							histoPaiment.setAmount(myObj.getDouble("montant"));
							histoPaiment.setCanal(myObj.getString("channel"));
							histoPaiment.setCompteClient(myObj.getString("debit_account"));
							//ZonedDateTime results = ZonedDateTime.parse(myObj.getString("date_paiement"), DateTimeFormatter.ISO_DATE_TIME);
							histoPaiment.setDatePaiement(myObj.getString("date_paiement"));
							histoPaiment.setFacturier(myObj.getString("beneficiaire"));
							histoPaiment.setNumeroFacture(myObj.getString("numero_facture"));
							histoPaiment.setNumeroTrx(myObj.getString("reference_transaction"));
							//histoPaiment.setRefPaiement();
							response.getHistoPaiments().add(histoPaiment);
						}
					}
					
					tracking = createTracking(ICodeDescResponse.SUCCES_CODE, filiale.getEndPoint(), result, tab[1]);

				} else {
					response.setCode(ICodeDescResponse.SUCCES_CODE);
					response.setDateResponse(Instant.now());
					response.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
					tracking = createTracking(ICodeDescResponse.SUCCES_CODE, filiale.getEndPoint(), result, tab[1]);
				}
			}else {
                response = (HistoPaiementClientResponse) apiService.clientAbsent(response, tracking, "histoPaiementClient",
                        ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.ECHEC_DESCRIPTION, request.getRequestURI(),
                        tab[1]);
                
			}
			os.close(); 
		} catch (Exception e) {
			log.error("exception in histoClient [{}]", e.fillInStackTrace());
			response.setCode(ICodeDescResponse.ECHEC_CODE);
			response.setDateResponse(Instant.now());
			response.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);
			tracking = createTracking(ICodeDescResponse.ECHEC_CODE, filiale.getEndPoint(), e.getMessage(), tab[1]);
		}
        trackingService.save(tracking);
		return response;
	}

	public HistoPaiementClientResponse histofacturier(HistoPaiementFacRequest facRequest, HttpServletRequest request) {
		HistoPaiementClientResponse response = new HistoPaiementClientResponse();
		ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("histoFacturier");
		Tracking tracking = new Tracking();
		String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        if (filiale == null) {
            response = (HistoPaiementClientResponse) apiService.clientAbsent(response, tracking, "getBill",
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);

            return response;
        }
        OutputStream os = null;
		try {
			log.info("end point wso2== [{}]", filiale.getEndPoint());
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
			//conn.setRequestProperty("Accept", "application/json");
			String jsonString = new JSONObject()
			.put("biller_code", facRequest.getCodeFacturier()) 
			.toString();
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
                String ligne = br.readLine();
                while (ligne != null) {
                    result += ligne;
                    ligne = br.readLine();
                }
                log.info("resp ===== [{}]", result);
				obj = new JSONObject(result); 
				log.info("resp ===== [{}]", obj.toString());
				if(!obj.isNull("Transactions")){
				//if(obj.getJSONObject("Transactions")!=null && obj.getJSONObject("Transactions").toString().contains("Transaction")){
					obj = obj.getJSONObject("Transactions");
					response.setCode(ICodeDescResponse.SUCCES_CODE);
					response.setDateResponse(Instant.now());
					response.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
					JSONArray array = obj.getJSONArray("Transaction");
					if(array!=null){
						//DateTimeFormatter formatter = DateTimeFormatter
						//.ofPattern("yyyy-MMM-ddT03:00:00.000+03:00");
						//formatter = formatter.withLocale(Locale.FRANCE );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
						//LocalDate date = LocalDate.parse("2005-nov-12", formatter);
						for (int i=0; i < array.length(); i++) {
							HistoPaiment histoPaiment = new HistoPaiment();
							JSONObject myObj = array.getJSONObject(i);
							histoPaiment.setAmount(myObj.getDouble("montant"));
							histoPaiment.setCanal(myObj.getString("channel"));
							histoPaiment.setCompteClient(myObj.getString("debit_account"));
							//ZonedDateTime results = ZonedDateTime.parse(myObj.getString("date_paiement"), DateTimeFormatter.ISO_DATE_TIME);
							histoPaiment.setDatePaiement(myObj.getString("date_paiement"));
							histoPaiment.setFacturier(myObj.getString("beneficiaire"));
							histoPaiment.setNumeroFacture(myObj.getString("numero_facture"));
							histoPaiment.setNumeroTrx(myObj.getString("reference_transaction"));
							response.getHistoPaiments().add(histoPaiment);
						}
					}
					tracking = createTracking(ICodeDescResponse.SUCCES_CODE, filiale.getEndPoint(), result, tab[1]);
				} else {
 					response.setCode(ICodeDescResponse.SUCCES_CODE);
					response.setDateResponse(Instant.now());
					response.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
					tracking = createTracking(ICodeDescResponse.SUCCES_CODE, filiale.getEndPoint(), result, tab[1]);
					
				}
			}else {
                response = (HistoPaiementClientResponse) apiService.clientAbsent(response, tracking, "histoPaiementFacturier",
                        ICodeDescResponse.ECHEC_CODE, ICodeDescResponse.ECHEC_DESCRIPTION, request.getRequestURI(),
                        tab[1]);
                
			}
			os.close();
		} catch (Exception e) {
			log.error("exception in histoFacturier [{}]", e.fillInStackTrace());
			response.setCode(ICodeDescResponse.ECHEC_CODE);
			response.setDateResponse(Instant.now());
			response.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);
			tracking = createTracking(ICodeDescResponse.ECHEC_CODE, filiale.getEndPoint(), e.getMessage(), tab[1]);
		}
        trackingService.save(tracking);
		return response;
	}

	public Tracking createTracking(String code, String endPoint, String result, String token){
        Tracking tracking = new Tracking();
        tracking.setCodeResponse(code);
        tracking.setDateResponse(Instant.now());
        tracking.setEndPointTr(endPoint);
        tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
        tracking.setResponseTr(result);
        tracking.setTokenTr(token);
        tracking.setDateRequest(Instant.now());
        return tracking;
    }

	

}