package com.rdfanalyst.resultlistening;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdfanalyst.accounting.QueryAccountingService;
import com.rdfanalyst.accounting.RDFTriple;
import com.rdfanalyst.accounting.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
public class QueryResultListeningServiceEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(QueryResultListeningServiceEndpoint.class);

    public static final String REQUEST_SUCCESSFUL = "OK";

    @Autowired
    private QueryAccountingService queryAccountingService;

    @Autowired
    private ResultService resultService;

    @RequestMapping(value = "queryresponselistener/{queryName}", method = RequestMethod.POST)
    public
    @ResponseBody
    String onQueryResponse(@PathVariable String queryName, @RequestBody String requestBody) {
        logger.debug("Received a query response on topic {}. The response was {}.", queryName, requestBody);
        try {
            if (queryAccountingService.areWeCurrentlyListeningTopic(queryName)) {
                for (RDFTriple triplet : parseTripletsFromRDFJSON(queryName, requestBody)) {
                    resultService.registerNewTriple(queryName, triplet);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return REQUEST_SUCCESSFUL;
    }

    @RequestMapping(value = "queryresponselistener/{queryName}", method = RequestMethod.GET)
    public
    @ResponseBody
    String rabbitHandshakeResponder(@PathVariable String queryName, @RequestParam("hub.challenge") String challenge) {
        logger.info("Received a rabbit handshake challenge: {}.", challenge);
        return challenge;
    }

    // TODO: This parsing technique is ugly. Refactor.
    private List<RDFTriple> parseTripletsFromRDFJSON(String queryName, String requestBody) throws IOException {
        List<RDFTriple> triplets = new ArrayList<>();
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        JsonNode rootNode = mapper.readTree(requestBody);
        Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            String subject = field.getKey();
            JsonNode objectMap = field.getValue();
            Iterator<Map.Entry<String,JsonNode>> objectMapIterator = objectMap.fields();
            while (objectMapIterator.hasNext()) {
                Map.Entry<String, JsonNode> objectMapField = objectMapIterator.next();
                String predicate = objectMapField.getKey();
                String object = objectMapField.getValue().toString();
                triplets.add(new RDFTriple(subject, predicate, object, new Date()));
            }
        }
        return triplets;
    }
}
