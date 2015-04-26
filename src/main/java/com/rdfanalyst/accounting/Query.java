package com.rdfanalyst.accounting;

public class Query {
    private String topic;
    private String query;
    private String stream;
    private boolean local;

    public Query(String topic, String queryString, String stream, boolean local) {
        this.topic = topic;
        this.query = queryString;
        this.stream = stream;
        this.local = local;
    }

    public Query(String queryString) {
        try {
            queryString = queryString.trim();
            topic = queryString.split(" ")[2];
            stream = queryString.split("FROM STREAM <")[1].split(">")[0];
            query = queryString;
        } catch (Exception e) {
            throw new InvalidQueryException();
        }
    }

    public String getTopic() {
        return topic;
    }

    public String getQuery() {
        return query;
    }

    public String getStream() {
        return stream;
    }

    public boolean isLocal() {
        return local;
    }
}
