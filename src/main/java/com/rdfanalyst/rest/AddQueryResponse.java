package com.rdfanalyst.rest;

public class AddQueryResponse {

    public static final AddQueryResponse REQUEST_OK = new AddQueryResponse("OK");
    public static final AddQueryResponse REQUEST_FAIL_DUPLICATE_QUERY_NAME = new AddQueryResponse("DUPLICATE_QUERY_NAME");
    public static final AddQueryResponse REQUEST_FAIL_GENERAL_EXCEPTION = new AddQueryResponse("GENERAL_EXCEPTION");

    private String status;

    private AddQueryResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
