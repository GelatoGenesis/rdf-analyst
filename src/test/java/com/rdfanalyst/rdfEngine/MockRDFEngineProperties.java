package com.rdfanalyst.rdfEngine;

import com.rdfanalyst.rdfengine.RDFEngineProperties;

public class MockRDFEngineProperties extends RDFEngineProperties {

    @Override
    public String composeRDFEngineTopicURL(String topic) {
        return "http://127.0.0.1/" + topic;
    }

}
