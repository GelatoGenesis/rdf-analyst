package com.rdfanalyst.rdf;

import com.rdfanalyst.accounting.Query;
import com.rdfanalyst.rdf.engine.RDFEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RDFQueryInfoServiceEndpoint {

    @Autowired
    private RDFEngineService rdfEngineService;

    @RequestMapping("/available-queries")
    public @ResponseBody
    List<Query> availableQueries() {
        return rdfEngineService.getAvailableQueries();
    }


}
