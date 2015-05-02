package com.rdfanalyst.accounting;

import com.rdfanalyst.dao.QueryDao;
import com.rdfanalyst.rabbit.RabbitService;
import com.rdfanalyst.rdf.engine.RDFEngineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryAccountingServiceImpl implements QueryAccountingService {

    @Autowired
    private RabbitService rabbitService;

    @Autowired
    private RDFEngineServiceImpl rdfEngineService;

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
