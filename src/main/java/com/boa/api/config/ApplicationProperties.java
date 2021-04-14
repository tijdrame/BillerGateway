package com.boa.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to BillerGateway.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private Integer timeOut;

    public Integer getTimeOut() {
        return this.timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

}
