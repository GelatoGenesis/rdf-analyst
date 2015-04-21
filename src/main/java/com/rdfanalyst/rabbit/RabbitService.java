package com.rdfanalyst.rabbit;

import com.rdfanalyst.CommonProperties;
import com.rdfanalyst.http.HttpRequester;
import com.rdfanalyst.http.HttpResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.rdfanalyst.rabbit.RabbitRequestParamConstants.*;

@Service
public class RabbitService {

    public static final String RABBIT_REQUEST_OK_STATUS_INDICATOR = "202 Accepted";

    @Autowired
    private RabbitProperties properties;

    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private HttpRequester httpRequester;

    public void subscribeToTopic(String topicName) {
        HttpResponseInfo httpResponseInfo = httpRequester.makeHTTPPostRequest(properties.getSubscriptionURL(), composeRequestParamsMap(topicName));
        String responseStatus = httpResponseInfo.getStatus();
        assertResponseOK(responseStatus);
    }

    private void assertResponseOK(String responseStatus) {
        if (!responseStatus.contains(RABBIT_REQUEST_OK_STATUS_INDICATOR)) {
            throw new RuntimeException("RabbitHub denied request with status message '" + responseStatus + "'");
        }
    }

    private Map<String, String> composeRequestParamsMap(String topicName) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(KEY_MODE, PARAM_MODE_SUBSCRIBE);
        paramsMap.put(KEY_TOPIC, topicName);
        paramsMap.put(KEY_VERIFY, PARAM_VERIFY_ASYNC);
        paramsMap.put(KEY_CALLBACK, commonProperties.composeRabbitToHereCallbackURL(topicName));
        return paramsMap;
    }

    public void setRabbitProperties(RabbitProperties properties) {
        this.properties = properties;
    }

    public void setCommonProperties(CommonProperties commonProperties) {
        this.commonProperties = commonProperties;
    }

    public void setHttpRequester(HttpRequester httpRequester) {
        this.httpRequester = httpRequester;
    }
}
