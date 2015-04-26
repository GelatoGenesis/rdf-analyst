package com.rdfanalyst.rdfengine;

import com.rdfanalyst.accounting.Query;

import java.util.List;

public interface RDFEngineService {

    void registerQuery(Query query);

    List<String> getAvailableRunningStreams();

}
