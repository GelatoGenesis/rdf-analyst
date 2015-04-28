package com.rdfanalyst.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class HttpRequester {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequester.class);
    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";
    public static final String HTTP_PUT = "PUT";
    public static final String HTTP_DELETE = "DELETE";

    public HttpResponseInfo makeHTTPDeleteRequest(String url) {
        logger.info(composeRequestLog(HTTP_DELETE, url, null));
        return executeRequest(new HttpDelete(url));
    }

    public HttpResponseInfo makeHttpGetRequest(String url) {
        logger.info(composeRequestLog(HTTP_GET, url, null));
        return executeRequest(new HttpGet(url));
    }

    public HttpResponseInfo makeHTTPPostRequest(String url, Map<String, String> requestParameters) {
        logger.info(composeRequestLog(HTTP_POST, url, requestParameters));
        return makeHTTPPutOrPostRequest(new HttpPost(url), requestParameters);
    }

    public HttpResponseInfo makeHTTPPutRequest(String url, Map<String, String> requestParameters) {
        logger.info(composeRequestLog(HTTP_PUT, url, requestParameters));
        return makeHTTPPutOrPostRequest(new HttpPut(url), requestParameters);
    }

    protected HttpResponseInfo makeHTTPPutOrPostRequest(HttpEntityEnclosingRequestBase request, Map<String, String> requestParameters) {
        request.setEntity(composeParameters(requestParameters));
        return executeRequest(request);
    }

    private HttpResponseInfo executeRequest(HttpRequestBase request) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseStatus = response.getStatusLine().toString();
            StringBuilder wholeResponse = new StringBuilder();
            HttpEntity bodyEntity = response.getEntity();
            try (BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(bodyEntity.getContent()))) {
                String reponseBodyLine;
                while ((reponseBodyLine = responseBuffer.readLine()) != null) {
                    wholeResponse.append(reponseBodyLine);
                }
            }
            String responseBody = wholeResponse.toString();
            logger.info("HTTP response status was: '" + responseStatus + "' and response body was: '" + responseBody + "'.");
            return new HttpResponseInfo(responseStatus, responseBody);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private UrlEncodedFormEntity composeParameters(Map<String, String> paramsMap) {
        List<BasicNameValuePair> valuePairs = new ArrayList<>();
        for (Map.Entry<String, String> param : paramsMap.entrySet()) {
            valuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        try {
            return new UrlEncodedFormEntity(valuePairs);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private String composeRequestLog(String reqType, String url, Map<String, String> requestParameters) {
        StringBuilder log = new StringBuilder("Starting a ").append(reqType).append(" request to URL ")
                .append(url);
        if (requestParameters != null) {
            log.append(" with parameters: ");
            for (Map.Entry<String, String> param : requestParameters.entrySet()) {
                log.append("(").append(param.getKey()).append("=").append(param.getValue()).append(") ");
            }
        }
        return log.toString();
    }
}
