package com.rdfanalyst.accounting;

import com.rdfanalyst.dao.RDFTripleInMemoryDB;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ResultServiceImpl implements ResultService {

    @Override
    public Collection<RDFTriple> findAllResultsForTopic(String topic) {
        return RDFTripleInMemoryDB.getTriplesForTopic(topic);
    }

    @Override
    public void registerNewTriple(String queryName, RDFTriple rdfTriple) {
        RDFTripleInMemoryDB.addTriple(queryName, rdfTriple);
    }
}
