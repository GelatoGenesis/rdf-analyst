package com.rdfanalyst.http;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.util.Map;

public class MockHttpRequester extends HttpRequester {

    private String responseStatusText;

    public MockHttpRequester(String responseStatusText) {
        this.responseStatusText = responseStatusText;
    }

    protected HttpResponseInfo makeHTTPPutOrPostRequest(HttpEntityEnclosingRequestBase request, Map<String, String> requestParameters) {
        return new HttpResponseInfo(responseStatusText, "some body content too");
    }
}
