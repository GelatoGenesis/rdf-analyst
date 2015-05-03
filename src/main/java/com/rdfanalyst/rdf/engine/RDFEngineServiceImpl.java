package com.rdfanalyst.rdf.engine;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rdfanalyst.CommonProperties;
import com.rdfanalyst.accounting.Query;
import com.rdfanalyst.dao.QueryDao;
import com.rdfanalyst.http.HttpRequester;
import com.rdfanalyst.http.HttpResponseInfo;
import com.rdfanalyst.rabbit.RabbitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rdfanalyst.rdf.engine.RDFEngineRequestParamConstants.*;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class RDFEngineServiceImpl implements RDFEngineService {

    private static final Logger logger = LoggerFactory.getLogger(RDFEngineServiceImpl.class);

    public static final String RDF_ENGINE_RESPONSE_OK_INDICATOR = "200 OK";

    private static final Gson gson = new Gson();

    @Autowired
    private RDFEngineProperties rdfEngineProperties;

    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private RabbitProperties rabbitProperties;

    @Autowired
    private HttpRequester httpRequester;

    @Autowired
    private QueryDao queryDao;

    public void registerQuery(Query query) {
        registerQueryToRDFEngineStream(query);
        registerRabbitToRDFEngineStream(query);
    }

    @Override
    public List<String> getAvailableRunningStreams() {
        List<String> availableStreams = new ArrayList<>();
        try {
            HttpResponseInfo availableStreamsResponse = httpRequester.makeHttpGetRequest(rdfEngineProperties.getAvailableStreamsInfoUrl());
            assertHTTPResponseOK(availableStreamsResponse);
            List<StreamInfo> streamsInfo = gson.fromJson(availableStreamsResponse.getBody(), new TypeToken<List<StreamInfo>>() {
            }.getType());
            for (StreamInfo streamInfo : streamsInfo) {
                if (streamInfo.isStreamRunning()) {
                    availableStreams.add(streamInfo.getStreamIRI());
                }
            }
            return availableStreams;
        } catch (Exception e) {
            logger.info("There was an exception while requesting or processing info about available running streams.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Query> getAvailableQueries() {
        try {
            HttpResponseInfo availableQueriesResponse = httpRequester.makeHttpGetRequest(rdfEngineProperties.getAvailableQueriesInfoUrl());
            assertHTTPResponseOK(availableQueriesResponse);
            List<QueryInfo> queriesInfo = gson.fromJson(availableQueriesResponse.getBody(), new TypeToken<List<QueryInfo>>() {
            }.getType());
            return filterAndComplementRunningQueries(queriesInfo);
        } catch (Exception e) {
            logger.info("There was an exception while requesting or processing info about available running streams.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteQuery(String topic) {
        httpRequester.makeHTTPDeleteRequest(rdfEngineProperties.composeRDFEngineTopicURL(topic));
    }

    private List<Query> filterAndComplementRunningQueries(List<QueryInfo> queriesInfo) {
        List<Query> availableQueries = new ArrayList<>();
        for (QueryInfo query : queriesInfo) {
            if (query.isOfTypeQuery() && query.isRunning()) {
                String topic = query.getId();
                availableQueries.add(new Query(topic, query.getBody(), query.getStream(), doesLocalInfoAboutTopicExist(topic)));
            }
        }
        return availableQueries;
    }

    private boolean doesLocalInfoAboutTopicExist(String topic) {
        try {
            return queryDao.doesQueryWithNameExist(topic);
        } catch (Exception e) {
            logger.error("There was an exception while trying to determine weather topic " + topic + " was local.", e);
            return false;
        }
    }

    private void registerQueryToRDFEngineStream(Query query) {
        HttpResponseInfo rdfEngineToStreamRegistrationResponse = httpRequester.makeHTTPPutRequest(
                rdfEngineProperties.composeRDFEngineTopicURL(query.getTopic()),
                composeQueryToRDFEngineRequestParameters(query)
        );
        assertHTTPResponseOK(rdfEngineToStreamRegistrationResponse);
    }

    private Map<String, String> composeQueryToRDFEngineRequestParameters(Query query) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(KEY_QUERY, query.getQuery());
        paramsMap.put(KEY_STREAM_NAME, query.getStream());
        return paramsMap;
    }

    private void registerRabbitToRDFEngineStream(Query query) {
        HttpResponseInfo rdfEngineToStreamRegistrationResponse = httpRequester.makeHTTPPostRequest(
                rdfEngineProperties.composeRDFEngineTopicURL(query.getTopic()),
                composeRabbitToRDFEngineRequestParameters(query)
        );
        assertHTTPResponseOK(rdfEngineToStreamRegistrationResponse);
    }

    private Map<String, String> composeRabbitToRDFEngineRequestParameters(Query query) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(KEY_CALLBACK_URL, rabbitProperties.composeRDFToRabitCallbackURL(query.getTopic()));
        paramsMap.put(KEY_STREAM_NAME, query.getStream());
        return paramsMap;
    }

    private void assertHTTPResponseOK(HttpResponseInfo rdfEngineToStreamRegistrationResponse) {
        String responseStatus = rdfEngineToStreamRegistrationResponse.getStatus();
        if (!responseStatus.contains(RDF_ENGINE_RESPONSE_OK_INDICATOR)) {
            throw new RuntimeException("RDF Engine denied the request with status message '" + responseStatus +
                    "' and body '" + rdfEngineToStreamRegistrationResponse.getBody() + "'");
        }
    }

    public void setRDFEngineProperties(RDFEngineProperties rdfEngineProperties) {
        this.rdfEngineProperties = rdfEngineProperties;
    }

    public void setCommonProperties(CommonProperties commonProperties) {
        this.commonProperties = commonProperties;
    }

    public void setHttpRequester(HttpRequester httpRequester) {
        this.httpRequester = httpRequester;
    }

    public void setRabbitProperties(RabbitProperties rabbitProperties) {
        this.rabbitProperties = rabbitProperties;
    }

}
