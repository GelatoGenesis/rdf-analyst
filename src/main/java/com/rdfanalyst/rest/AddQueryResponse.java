package com.rdfanalyst.rest;

public class AddQueryResponse {

    private String message;

    private AddQueryResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static AddQueryResponse customError(Exception e) {
        return new AddQueryResponse(getRootExceptionCause(e));
    }

    private static String getRootExceptionCause(Throwable e) {
        if (e == null) {
            return null;
        } else if (e.getCause() != null) {
            return getRootExceptionCause(e.getCause());
        }
        return e.getMessage();
    }

    public static AddQueryResponse ok() {
        return new AddQueryResponse("OK");
    }

    public static AddQueryResponse duplicateNameError() {
        return new AddQueryResponse("Query with this name already exists according to database.");
    }
}
