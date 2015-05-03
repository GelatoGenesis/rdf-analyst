package com.rdfanalyst.accounting;

import com.rdfanalyst.dao.ResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultDao resultDao;

    @Override
    public List<RDFTriple> findAllResultsForTopic(String topic) {
        return resultDao.getTriplesForTopic(topic);
    }

    @Override
    public void registerNewTriples(String queryName, List<RDFTriple> rdfTriples) {
        resultDao.addNewResults(queryName, rdfTriples);
    }

    public void clearResultsOfTopic(String topic) {
        resultDao.clearResultsOfTopic(topic);
    }
}
