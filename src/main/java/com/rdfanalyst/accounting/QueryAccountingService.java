package com.rdfanalyst.accounting;

import java.util.List;

public interface QueryAccountingService {

    void registerQuery(Query query);

    List<Query> getArchivedQueries();

    Query findQueryByTopic(String topic);

    boolean areWeCurrentlyListeningTopic(String topic);

    void deleteQuery(String topic);
}
