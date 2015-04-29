package com.rdfanalyst.dao;

import com.rdfanalyst.accounting.RDFTriple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RDFTripleInMemoryDB {

    private static Map<String, ArrayList<RDFTriple>> rdfTriples = new HashMap<>();

    public static void addTriple(String queryName, RDFTriple triple) {
        if (!rdfTriples.containsKey(queryName)) {
            rdfTriples.put(queryName, new ArrayList<RDFTriple>());
        }
        rdfTriples.get(queryName).add(0, triple);
    }

    public static List<RDFTriple> getTriplesForTopic(String query) {
        List<RDFTriple> allQueryTriples = rdfTriples.get(query);
        return allQueryTriples == null ? new ArrayList<RDFTriple>() : allQueryTriples;
    }
}
