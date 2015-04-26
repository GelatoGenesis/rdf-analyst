package com.rdfanalyst.rest;

import com.rdfanalyst.accounting.QueryAccountingService;
import com.rdfanalyst.accounting.RDFTriple;
import com.rdfanalyst.accounting.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class QueryResultListeningServiceEndpoint {

    public static final String REQUEST_SUCCESSFUL = "OK";

    @Autowired
    private QueryAccountingService queryAccountingService;

    @Autowired
    private ResultService resultService;

    @RequestMapping("queryresponselistener/{queryName}")
    public
    @ResponseBody
    String onQueryResponse(@PathVariable String queryName) {
        if (queryAccountingService.areWeCurrentlyListeningTopic(queryName)) {
            resultService.registerNewTriple(queryName, new RDFTriple(
                    "http://piksel.ee/mooste/index.php?tid=6sJYoa66lfUulx9RXXoJoikf6RKKHuTxg9iu8fY8diff/1429036812-1429043285/19/addition",
                    "http://vocab.deri.ie/diff#ReferenceInsertion",
                    "http://piksel.ee/mooste/index.php?tid=6sJYoa66lfUulx9RXXoJoikf6RKKHuTxg9iu8fY8diff/1429036812-1429043285/19",
                    new Date()
            ));
        }
        return REQUEST_SUCCESSFUL;
    }

}
