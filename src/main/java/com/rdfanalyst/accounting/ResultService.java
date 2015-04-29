package com.rdfanalyst.accounting;

import java.util.Collection;

public interface ResultService {

    Collection<RDFTriple> findAllResultsForTopic(String topic);

    void registerNewTriple(String queryName, RDFTriple RDFTriple);
}
