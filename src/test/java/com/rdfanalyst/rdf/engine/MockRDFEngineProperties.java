package com.rdfanalyst.rdf.engine;

import com.rdfanalyst.rdf.engine.RDFEngineProperties;

public class MockRDFEngineProperties extends RDFEngineProperties {

    @Override
    public String composeRDFEngineTopicURL(String topic) {
        return "http://127.0.0.1/" + topic;
    }

}
