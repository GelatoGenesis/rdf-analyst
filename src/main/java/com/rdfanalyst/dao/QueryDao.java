package com.rdfanalyst.dao;

import com.rdfanalyst.accounting.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class QueryDao {

    public boolean doesQueryWithNameExist(String name) {
        return QueryInMemoryDB.queryExists(name);
    }

    public void addQuery(Query query) {
        QueryInMemoryDB.addQuery(query);
    }

    public Collection<Query> getAllQueries() {
        return QueryInMemoryDB.getAllQueries();
    }

    public Query findByName(String topic) {
        return QueryInMemoryDB.getQuery(topic);
    }
}
