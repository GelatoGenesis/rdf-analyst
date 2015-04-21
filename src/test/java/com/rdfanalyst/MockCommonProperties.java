package com.rdfanalyst;

public class MockCommonProperties extends CommonProperties {

    @Override
    public String composeRabbitToHereCallbackURL(String topic) {
        return "http://127.0.0.1/";
    }

    @Override
    public String getStreamName() {
        return "some random stream name";
    }

}
