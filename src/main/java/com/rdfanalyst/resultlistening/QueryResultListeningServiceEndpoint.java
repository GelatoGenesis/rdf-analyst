package com.rdfanalyst.resultlistening;

import com.rdfanalyst.accounting.QueryAccountingService;
import com.rdfanalyst.accounting.RDFTriple;
import com.rdfanalyst.accounting.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
        logger.info("Received a query response on topic " + queryName + ": " +requestBody);
        if (queryAccountingService.areWeCurrentlyListeningTopic(queryName)) {
            resultService.registerNewTriple(queryName, new RDFTriple(requestBody, "UNKNOWN", "UNKNOWN", new Date()));
        }
        return REQUEST_SUCCESSFUL;
    }

    @RequestMapping(value = "queryresponselistener/{queryName}", method = RequestMethod.GET)
    public
    @ResponseBody
    String rabbitHandshakeResponder(@PathVariable String queryName, @RequestParam("hub.challenge") String challenge) {
        logger.info("Received a rabbit handshake challenge: " + challenge);
        return challenge;
    }
}
