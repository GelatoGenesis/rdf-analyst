package com.rdfanalyst.accounting;

public class Query {
    private String name;
    private String query;

    public Query(String queryString) {
        try {
            queryString = queryString.trim();
            name = queryString.split(" ")[2];
            query = queryString;
        } catch (Exception e) {
            throw new InvalidQueryException();
        }
    }

    public Query(String name, String query) {
        this.name = name;
        this.query = query;
    }

    public String getName() {
        return name;
    }

    public String getQuery() {
        return query;
    }
}
