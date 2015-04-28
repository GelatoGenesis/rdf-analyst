package com.rdfanalyst.rdf;

import com.rdfanalyst.accounting.Query;
import com.rdfanalyst.general.GeneralOKResponse;
import com.rdfanalyst.rabbit.RabbitService;
import com.rdfanalyst.rdf.engine.RDFEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RDFQueryRegistrationServiceEndpoint {

    @Autowired
    private RDFEngineService rdfEngineService;

    @RequestMapping("/available-queries")
    public @ResponseBody
    List<Query> availableQueries() {
        return rdfEngineService.getAvailableQueries();
    }

    @RequestMapping("/cancel-query/{topic}")
    public @ResponseBody
    GeneralOKResponse cancelQuery(@PathVariable String topic) {
        rdfEngineService.deleteQuery(topic);
        return new GeneralOKResponse();
    }


}
