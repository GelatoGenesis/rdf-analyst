package com.rdfanalyst.rest;

public class AddQueryRequest {

    private String query;
    private String stream;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }
}
