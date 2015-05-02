package com.rdfanalyst.accounting;

import java.util.List;

public interface ResultService {

    List<RDFTriple> findAllResultsForTopic(String topic);

    void registerNewTriples(String queryName, List<RDFTriple> rdfTriples);

    void clearResultsOfTopic(String topic);
}
