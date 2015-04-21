package com.rdfanalyst.dao;

import com.rdfanalyst.accounting.Query;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class QueryInMemoryDB {

    private static Map<String, Query> queries = new HashMap<>();

    public static void addQuery(Query query) {
        String queryName = query.getName();
        if (!queryExists(queryName)) {
            queries.put(queryName, query);
        }
    }

    public static boolean queryExists(String queryName) {
        return queries.containsKey(queryName);
    }

    public static Query getQuery(String queryName) {
        return queries.get(queryName);
    }

    public static Collection<Query> getAllQueries() {
        return queries.values();
    }

}
