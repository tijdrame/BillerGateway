package com.boa.api.web.rest;

import com.boa.api.security.jwt.JWTFilter;
import com.boa.api.security.jwt.TokenProvider;
import com.boa.api.web.rest.vm.LoginVM;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
@Api(description = "JIRAMA", tags="JIRAMA Web Services.")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    //@PostMapping("/authenticate")
    @PostMapping(produces  = { MediaType.APPLICATION_XML_VALUE,
        MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE }, path = "/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        /*
         * UsernamePasswordAuthenticationToken authenticationToken = new
         * UsernamePasswordAuthenticationToken(loginVM.getUsername(),
         * loginVM.getPassword());
         * 
         * Authentication authentication =
         * authenticationManagerBuilder.getObject().authenticate(authenticationToken);
         * SecurityContextHolder.getContext().setAuthentication(authentication); boolean
         * rememberMe = (loginVM.isRememberMe() == null) ? false :
         * loginVM.isRememberMe(); String jwt =
         * tokenProvider.createToken(authentication, rememberMe); HttpHeaders
         * httpHeaders = new HttpHeaders();
         * httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt); return new
         * ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
         */
        log.debug("REST request to authenticate a User : {}", loginVM);
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginVM.getUsername(), loginVM.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
            String jwt = tokenProvider.createToken(authentication, rememberMe);

            httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            JWTToken token = new JWTToken(jwt);
            token.setDescription("Authentification réussie");
            token.setDateResponse(Instant.now());
            token.setCode("200");
            return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);            
        } catch (Exception e) {
            JWTToken token = new JWTToken(null);
            token.setDescription("Authentification échouée");
            token.setDateResponse(Instant.now());
            token.setCode("402");
            return new ResponseEntity<>(token, httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    @XmlRootElement
    static class JWTToken {

        @ApiModelProperty(value = "Generated Token", example = "xxx")
        @XmlElement
        private String idToken;

        private String code;
        private String description;
        private Instant dateResponse;

        JWTToken() {}

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Instant getDateResponse() {
            return this.dateResponse;
        }

        public void setDateResponse(Instant dateResponse) {
            this.dateResponse = dateResponse;
        }

    }
}
