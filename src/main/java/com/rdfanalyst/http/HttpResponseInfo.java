package com.rdfanalyst.http;

public class HttpResponseInfo {

    private String status;
    private String body;

    public HttpResponseInfo(String status, String body) {
        this.status = status == null ? "" : status;
        this.body = body == null ? "" : body;
    }

    public String getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }
}
