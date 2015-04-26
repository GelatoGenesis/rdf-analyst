package com.rdfanalyst.rdfengine;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(locations = "classpath:rdf-engine.properties")
public class RDFEngineProperties {

    private String baseUrl;

    private String relativeQueriesBaseURL;

    private String relativeAvailableStreamsBaseURL;

    public String composeRDFEngineTopicURL(String topic) {
        return baseUrl + relativeQueriesBaseURL + "/" + topic;
    }

    public String getAvailableStreamsInfoUrl() {
        return baseUrl + relativeAvailableStreamsBaseURL;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setRelativeQueriesBaseURL(String relativeQueriesBaseURL) {
        this.relativeQueriesBaseURL = relativeQueriesBaseURL;
    }

    public void setRelativeAvailableStreamsBaseURL(String relativeAvailableStreamsBaseURL) {
        this.relativeAvailableStreamsBaseURL = relativeAvailableStreamsBaseURL;
    }
}
