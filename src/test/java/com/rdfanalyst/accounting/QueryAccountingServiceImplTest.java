package com.rdfanalyst.accounting;

import com.rdfanalyst.dao.QueryDao;
import org.junit.Test;

public class QueryAccountingServiceImplTest {

    private static final String VALID_EXAMPLE_QUERY = "REGISTER STREAM query1 AS CONSTRUCT { ?s ?p ?o } FROM STREAM <http://ex.org/rabbit> [RANGE 2s STEP 2s] WHERE { ?s ?p ?o }";

    private QueryAccountingServiceImpl queryAccountingService = new QueryAccountingServiceImpl();

    @Test(expected = DuplicateQueryNameException.class)
    public void anExceptionIsThrownIfStreamWithNameIsAlreadyRegistered() {
        queryAccountingService.setQueryDao(new QueryDao() {
            @Override
            public boolean doesQueryWithNameExist(String name) {
                return true;
            }
        });
        queryAccountingService.registerQuery(new Query(VALID_EXAMPLE_QUERY, null));
    }
}
