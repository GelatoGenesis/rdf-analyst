package com.rdfanalyst.rdf.engine;

import com.rdfanalyst.MockCommonProperties;
import com.rdfanalyst.accounting.Query;
import com.rdfanalyst.http.MockHttpRequester;
import com.rdfanalyst.rabbit.MockRabbitProperties;
import com.rdfanalyst.rdf.engine.RDFEngineServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class RDFEngineServiceTest {

    private static final String VALID_EXAMPLE_QUERY = "REGISTER QUERY query1 AS CONSTRUCT { ?s ?p ?o } FROM STREAM <http://ex.org/rabbit> [RANGE 2s STEP 2s] WHERE { ?s ?p ?o }";

    private RDFEngineServiceImpl rdfEngineService = new RDFEngineServiceImpl();

    @Before
    public void setUp() throws Exception {
        rdfEngineService.setRDFEngineProperties(new MockRDFEngineProperties());
        rdfEngineService.setCommonProperties(new MockCommonProperties());
        rdfEngineService.setRabbitProperties(new MockRabbitProperties());
    }

    @Test
    public void successfulRequestTest() {
        rdfEngineService.setHttpRequester(new MockHttpRequester("Everything went OK 200 OK was the response"));
        rdfEngineService.registerQuery(new Query(VALID_EXAMPLE_QUERY));
    }

    @Test(expected = RuntimeException.class)
    public void failedRequestTest() {
        rdfEngineService.setHttpRequester(new MockHttpRequester("No success indicator here"));
        rdfEngineService.registerQuery(new Query(VALID_EXAMPLE_QUERY));
    }

}
