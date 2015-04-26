package com.rdfanalyst;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(locations = "classpath:common.properties")
public class CommonProperties {

    private String appBaseURL;

    private String relativeQueryResponseCallbackBaseURL;

    public String composeRabbitToHereCallbackURL(String topic) {
        return appBaseURL + relativeQueryResponseCallbackBaseURL + topic;
    }

    public void setAppBaseURL(String appBaseURL) {
        this.appBaseURL = appBaseURL;
    }

    public void setRelativeQueryResponseCallbackBaseURL(String relativeQueryResponseCallbackBaseURL) {
        this.relativeQueryResponseCallbackBaseURL = relativeQueryResponseCallbackBaseURL;
    }

}
