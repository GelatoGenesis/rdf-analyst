package com.rdfanalyst.rdfengine;

import com.rdfanalyst.CommonProperties;
import com.rdfanalyst.accounting.Query;
import com.rdfanalyst.http.HttpRequester;
import com.rdfanalyst.http.HttpResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.rdfanalyst.rdfengine.RDFEngineRequestParamConstants.KEY_QUERY;
import static com.rdfanalyst.rdfengine.RDFEngineRequestParamConstants.KEY_STREAM_NAME;

@Component
public class RDFEngineService {

    public static final String RDF_ENGINE_RESPONSE_OK_INDICATOR = "200 OK";

    @Autowired
    private RDFEngineProperties rdfEngineProperties;

    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private HttpRequester httpRequester;

    public void registerQuery(Query query) {
        String topic = query.getName();
        String queryString = query.getQuery();
        HttpResponseInfo rdfEngineResponse = httpRequester.makeHTTPPutRequest(
                rdfEngineProperties.composeRDFEngineTopicURL(topic),
                composeParameters(queryString)
        );
        String responseStatus = rdfEngineResponse.getStatus();
        if (!responseStatus.contains(RDF_ENGINE_RESPONSE_OK_INDICATOR)) {
            throw new RuntimeException("RDF Engine denied the request with status message '" + responseStatus +
                    "' and body '" + rdfEngineResponse.getBody() + "'");
        }

    }

    private Map<String, String> composeParameters(String queryString) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(KEY_QUERY, queryString);
        paramsMap.put(KEY_STREAM_NAME, commonProperties.getStreamName());
        return paramsMap;
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

}
