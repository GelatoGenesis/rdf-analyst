package com.rdfanalyst.rabbit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(locations = "classpath:rabbit.properties")
public class RabbitProperties {

    private String baseURL;

    private String relativeCallbackBaseURL;

    private String relativeSubscriptionURL;

    public String getSubscriptionURL() {
        return baseURL + relativeSubscriptionURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public void setRelativeCallbackBaseURL(String relativeCallbackBaseURL) {
        this.relativeCallbackBaseURL = relativeCallbackBaseURL;
    }

    public void setRelativeSubscriptionURL(String relativeSubscriptionURL) {
        this.relativeSubscriptionURL = relativeSubscriptionURL;
    }

    public String getRelativeCallbackBaseURL() {
        return relativeCallbackBaseURL;
    }
}
