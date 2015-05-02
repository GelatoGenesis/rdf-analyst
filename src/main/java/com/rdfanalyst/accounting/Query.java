package com.rdfanalyst.accounting;

public class Query {
    private String topic;
    private String query;
    private transient String stream;
    private transient boolean local;

    public Query() {
    }

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

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getStream() {
        return stream;
    }

    public boolean isLocal() {
        return local;
    }
}
