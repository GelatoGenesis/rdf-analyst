package com.rdfanalyst.rabbit;

import com.rdfanalyst.CommonProperties;
import com.rdfanalyst.http.MockHttpRequester;
import org.junit.Before;
import org.junit.Test;

public class RabbitServiceTest {

    private RabbitService rabbitService = new RabbitService();

    @Before
    public void setUp() throws Exception {
        rabbitService.setRabbitProperties(new RabbitProperties() {

            @Override
            public String getSubscriptionURL() {
                return "http://127.0.0.1/";
            }

            @Override
            public String composeRDFToRabitCallbackURL(String topic) {
                return "http://127.0.0.1/" + topic;
            }
        });

        rabbitService.setCommonProperties(new CommonProperties() {

            @Override
            public String composeRabbitToHereCallbackURL(String topic) {
                return "http://127.0.0.1/";
            }

            @Override
            public String getStreamName() {
                return "some random stream name";
            }
        });
    }

    @Test
    public void successfulRequestTest() {
        rabbitService.setHttpRequester(new MockHttpRequester("Everything went OK 202 Accepted was the response"));
        rabbitService.subscribeToTopic("someTopic");
    }

    @Test(expected = RuntimeException.class)
    public void failedRequestTest() {
        rabbitService.setHttpRequester(new MockHttpRequester("No success indicator here"));
        rabbitService.subscribeToTopic("someTopic");
    }

}
