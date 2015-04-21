package com.rdfanalyst.rabbit;

public class MockRabbitProperties extends RabbitProperties {

    @Override
    public String getSubscriptionURL() {
        return "http://127.0.0.1/";
    }

    @Override
    public String composeRDFToRabitCallbackURL(String topic) {
        return "http://127.0.0.1/" + topic;
    }

}
