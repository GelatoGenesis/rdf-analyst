package com.rdfanalyst.accounting;

public class Query {
    private String topic;
    private String query;
    private String stream;

    public Query(String queryString, String stream) {
        try {
            queryString = queryString.trim();
            topic = queryString.split(" ")[2];
            query = queryString;
        } catch (Exception e) {
            throw new InvalidQueryException();
        }
        this.stream = stream;
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
}
