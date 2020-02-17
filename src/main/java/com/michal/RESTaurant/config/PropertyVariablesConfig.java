package com.michal.RESTaurant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class PropertyVariablesConfig {

    @Value("${graphhopper.url}")
    private String graphhopper_url;

    @Value("${graphhopper.apiKey}")
    private String grapHopperApiKey;

    public String getGraphhopper_url() {
        return graphhopper_url;
    }

    public String getGrapHopperApiKey() {
        return grapHopperApiKey;
    }

}
