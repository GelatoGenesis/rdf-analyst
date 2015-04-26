package com.rdfanalyst.rdf.engine;

import java.util.List;

public class QueryInfo {

    private String id;
    private String type;
    private List<String> streams;
    private String body;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOfTypeQuery() {
        return "query".equals(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStream() {
        return streams == null || streams.isEmpty() ? null : streams.get(0);
    }

    public void setStreams(List<String> streams) {
        this.streams = streams;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isRunning() {
        return "RUNNING".equals(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
