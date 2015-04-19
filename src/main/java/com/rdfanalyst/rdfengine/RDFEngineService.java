package com.rdfanalyst.rdfengine;

import com.rdfanalyst.CommonProperties;
import com.rdfanalyst.accounting.DuplicateQueryNameException;
import com.rdfanalyst.accounting.Query;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@Component
public class RDFEngineService {

    private static final Logger logger = LoggerFactory.getLogger(RDFEngineService.class);

    @Autowired
    private RDFEngineProperties rdfEngineProperties;

    @Autowired
    private CommonProperties commonProperties;

    public void registerQuery(Query query) {
        try {
            String topic = query.getName();
            String queryString = query.getQuery();
            CloseableHttpClient rdfEngineClient = HttpClients.createDefault();
            HttpPut queryRegistrationRequest = new HttpPut(rdfEngineProperties.composeRDFEngineTopicURL(topic));
            queryRegistrationRequest.setEntity(
                    createQueryRegistrationRequestParameters(queryString)
            );
            try(CloseableHttpResponse queryRegistrationResponse = rdfEngineClient.execute(queryRegistrationRequest)) {
                String responseStatus = queryRegistrationResponse.getStatusLine().toString();
                logger.debug("RDF Engine responded with status: " + responseStatus);
                HttpEntity responseBody = queryRegistrationResponse.getEntity();

                StringBuilder wholeResponse = new StringBuilder();
                try(BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(responseBody.getContent()))) {
                    logger.debug("RDF Engine response body:");
                    String reponseBodyLine;
                    while ((reponseBodyLine = responseBuffer.readLine()) != null) {
                        wholeResponse.append(reponseBodyLine);
                    }
                }
                if(!StringUtils.contains(responseStatus, "200 OK")) {
                    String responseAsString = wholeResponse.toString();
                    throw new RuntimeException("RDF Engine denied the request with status message '" + responseStatus +
                            "' and body '" + responseAsString + "'");
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private UrlEncodedFormEntity createQueryRegistrationRequestParameters(String queryString) throws UnsupportedEncodingException {
        return new UrlEncodedFormEntity(
                Arrays.asList(
                        new BasicNameValuePair("query", queryString),
                        new BasicNameValuePair("streamName", commonProperties.getStreamName())
                )
        );
    }

}
