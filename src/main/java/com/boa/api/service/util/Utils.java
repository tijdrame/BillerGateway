package com.boa.api.service.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import com.boa.api.config.ApplicationProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Utils {

    private final Logger log = LoggerFactory.getLogger(Utils.class);
    private final ApplicationProperties applicationProperties;

    public Utils(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }
    
    public HttpURLConnection doConnexion(String endPoint, String params, String appType, String appRetour, String token,
            Boolean auth, String username, String pwd, String protocole) throws IOException {
        OutputStream os = null;
        HttpURLConnection conn = null;
        try {
            log.info("end point calling== [{}]", endPoint);
            if(protocole.equalsIgnoreCase("GET")) endPoint = endPoint + params;
            URL url = new URL(endPoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(protocole);
            if(appType.equalsIgnoreCase("json")) appType="application/json";
            else appType="application/xml";
            conn.setRequestProperty("Content-Type", appType);
            if (auth) {
                String autho = username + ":" + pwd;
                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(autho.getBytes()));
                conn.setRequestProperty("Authorization", basicAuth);
            }
            if (!StringUtils.isEmpty(appRetour))
                conn.setRequestProperty("Accept", appRetour);

            if (!StringUtils.isEmpty(token))
                conn.setRequestProperty("Authorization", token);

            conn.setConnectTimeout(applicationProperties.getTimeOut());// 5000 ms <=> 5s
            conn.setReadTimeout(applicationProperties.getTimeOut());// 5000 ms <=> 5s

            if (!protocole.equalsIgnoreCase("GET")) {
                
                os = conn.getOutputStream();
                byte[] postDataBytes = params.getBytes();
                log.info("req= [{}]", params);
                os.write(postDataBytes);
                os.flush();
            } else {
                os = conn.getOutputStream();
                //byte[] postDataBytes = params.getBytes();

                //os.write(postDataBytes);
                os.flush();       
            }
            conn.connect();
            // tracking.setRequestTr(jsonString);
            //os = conn.getOutputStream();
            //byte[] postDataBytes = params.getBytes();
            //os.write(postDataBytes);
            //os.flush();
        } catch (Exception e) {
            log.error("Error in doConn endpoint[{}], params [{}] & trace [{}]", e);
            return null;
        }
        os.close();
        return conn;
    }
}
