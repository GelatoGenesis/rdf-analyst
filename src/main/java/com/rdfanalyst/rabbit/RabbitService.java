package com.rdfanalyst.rabbit;

import com.rdfanalyst.CommonProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RabbitService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitService.class);

    @Autowired
    private RabbitProperties properties;

    @Autowired
    private CommonProperties commonProperties;

    public void subscribeToTopic(String topicName) {
        try {
            CloseableHttpClient rabbitClient = HttpClients.createDefault();
            HttpPost rabbitRequest = new HttpPost(properties.getSubscriptionURL());

            rabbitRequest.setEntity(new UrlEncodedFormEntity(Arrays.asList(
                    new BasicNameValuePair("hub.mode", "subscribe"),
                    new BasicNameValuePair("hub.topic", topicName),
                    new BasicNameValuePair("hub.verify", "async"),
                    new BasicNameValuePair("hub.callback", commonProperties.composeRabbitToHereCallbackURL(topicName))
            )));

            try(CloseableHttpResponse rabbitResponse = rabbitClient.execute(rabbitRequest)) {
                String responseStatus = rabbitResponse.getStatusLine().toString();
                logger.debug("Response status from RabbitHub: " + responseStatus);
                if(!StringUtils.contains(responseStatus, "202 Accepted")) {
                    throw new RuntimeException("RabbitHub denied request with status message '" + responseStatus + "'");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
