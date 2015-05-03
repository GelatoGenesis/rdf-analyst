package com.rdfanalyst.accounting;

import com.rdfanalyst.dao.QueryDao;
import com.rdfanalyst.rabbit.RabbitService;
import com.rdfanalyst.rdf.engine.RDFEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Component
@Transactional(propagation = Propagation.REQUIRED)
public class QueryAccountingServiceImpl implements QueryAccountingService {

    @Autowired
    private RabbitService rabbitService;

    @Autowired
    private RDFEngineService rdfEngineService;

    @Autowired
    private QueryDao queryDao;

    @Override
    public void registerQuery(Query query) {
        String topic = query.getTopic();
        if (queryDao.doesQueryWithNameExist(topic)) {
            throw new DuplicateQueryNameException();
        }
        rabbitService.createExchangeAndSubscribeToIt(topic);
        rdfEngineService.registerQuery(query);
        queryDao.addQuery(query);
    }

    @Override
    public List<Query> getArchivedQueries() {
        return queryDao.getAllQueries();
    }

    @Override
    public Query findQueryByTopic(String topic) {
        return queryDao.findByName(topic);
    }

    @Override
    public boolean areWeCurrentlyListeningTopic(String topic) {
        return queryDao.doesQueryWithNameExist(topic);
    }

    public void deleteQuery(String topic) {
        queryDao.deleteQuery(topic);
    }

    public void setQueryDao(QueryDao queryDao) {
        this.queryDao = queryDao;
    }
}
