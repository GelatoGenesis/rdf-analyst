package com.rdfanalyst.rabbit;

import com.rdfanalyst.MockCommonProperties;
import com.rdfanalyst.http.MockHttpRequester;
import org.junit.Before;
import org.junit.Test;

public class RabbitServiceTest {

    private RabbitService rabbitService = new RabbitService();

    @Before
    public void setUp() throws Exception {
        rabbitService.setRabbitProperties(new MockRabbitProperties());
        rabbitService.setCommonProperties(new MockCommonProperties());
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
