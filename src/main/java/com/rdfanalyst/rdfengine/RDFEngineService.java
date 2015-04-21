package com.rdfanalyst.rdfengine;

import com.rdfanalyst.CommonProperties;
import com.rdfanalyst.accounting.Query;
import com.rdfanalyst.http.HttpRequester;
import com.rdfanalyst.http.HttpResponseInfo;
import com.rdfanalyst.rabbit.RabbitProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.rdfanalyst.rdfengine.RDFEngineRequestParamConstants.*;

@Component
public class RDFEngineService {

    public static final String RDF_ENGINE_RESPONSE_OK_INDICATOR = "200 OK";

    @Autowired
    private RDFEngineProperties rdfEngineProperties;

    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private RabbitProperties rabbitProperties;

    @Autowired
    private HttpRequester httpRequester;

    public void registerQuery(Query query) {
        String topic = query.getName();
        String queryString = query.getQuery();
        registerQueryToRDFEngineStream(topic, queryString);
        registerRabbitToRDFEngineStream(topic);
    }

    private void registerQueryToRDFEngineStream(String topic, String query) {
        HttpResponseInfo rdfEngineToStreamRegistrationResponse = httpRequester.makeHTTPPutRequest(
                rdfEngineProperties.composeRDFEngineTopicURL(topic),
                composeQueryToRDFEngineRequestParameters(query)
        );
        assertHTTPResponseOK(rdfEngineToStreamRegistrationResponse);
    }

    private Map<String, String> composeQueryToRDFEngineRequestParameters(String query) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(KEY_QUERY, query);
        paramsMap.put(KEY_STREAM_NAME, commonProperties.getStreamName());
        return paramsMap;
    }

    private void registerRabbitToRDFEngineStream(String topic) {
        HttpResponseInfo rdfEngineToStreamRegistrationResponse = httpRequester.makeHTTPPostRequest(
                rdfEngineProperties.composeRDFEngineTopicURL(topic),
                composeRabbitToRDFEngineRequestParameters(topic)
        );
        assertHTTPResponseOK(rdfEngineToStreamRegistrationResponse);
    }

    private Map<String, String> composeRabbitToRDFEngineRequestParameters(String topic) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(KEY_CALLBACK_URL, rabbitProperties.composeRDFToRabitCallbackURL(topic));
        paramsMap.put(KEY_STREAM_NAME, commonProperties.getStreamName());
        return paramsMap;
    }

    private void assertHTTPResponseOK(HttpResponseInfo rdfEngineToStreamRegistrationResponse) {
        String responseStatus = rdfEngineToStreamRegistrationResponse.getStatus();
        if (!responseStatus.contains(RDF_ENGINE_RESPONSE_OK_INDICATOR)) {
            throw new RuntimeException("RDF Engine denied the request with status message '" + responseStatus +
                    "' and body '" + rdfEngineToStreamRegistrationResponse.getBody() + "'");
        }
    }

    public void setRDFEngineProperties(RDFEngineProperties rdfEngineProperties) {
        this.rdfEngineProperties = rdfEngineProperties;
    }

    public void setCommonProperties(CommonProperties commonProperties) {
        this.commonProperties = commonProperties;
    }

    public void setHttpRequester(HttpRequester httpRequester) {
        this.httpRequester = httpRequester;
    }

    public void setRabbitProperties(RabbitProperties rabbitProperties) {
        this.rabbitProperties = rabbitProperties;
    }

}
