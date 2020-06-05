package com.boa.api.config;

import com.boa.api.request.CheckFactoryRequest;
import com.boa.api.response.CheckFactoryResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class Initializer {
 
   /* private String jirama;
    private String cnssTG;
 
    public Initializer(@Value("${biller.jirama}") String jirama, @Value("${biller.cnss}") String cnssTG) {
        this.jirama = jirama;
        this.cnssTG = cnssTG;
    }
 
    public CheckFactoryRequest initClassCheckFact() {
        return new CheckFactoryRequest(
          this.jirama, this.cnssTG);
    }

    public CheckFactoryResponse initClassCheckFactR() {
        return new CheckFactoryResponse(
          this.jirama, this.cnssTG);
    }*/
}