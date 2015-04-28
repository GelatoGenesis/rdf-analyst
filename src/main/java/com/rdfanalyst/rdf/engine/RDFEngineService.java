package com.rdfanalyst.rdf.engine;

import com.rdfanalyst.accounting.Query;

import java.util.List;

public interface RDFEngineService {

    void registerQuery(Query query);

    List<String> getAvailableRunningStreams();

    List<Query> getAvailableQueries();

    void deleteQuery(String topic);

}
