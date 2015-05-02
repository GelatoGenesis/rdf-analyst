package com.rdfanalyst.dao;

import com.rdfanalyst.accounting.RDFTriple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ResultDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addNewResults(final String topic, final List<RDFTriple> results) {
        jdbcTemplate.batchUpdate("insert into rdf_triple (topic, subject, predicate, object, received_ms) " +
                "values (?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                RDFTriple result = results.get(i);
                ps.setString(1, topic);
                ps.setString(2, result.getSubject());
                ps.setString(3, result.getPredicate());
                ps.setString(4, result.getObject());
                ps.setLong(5, result.getCreated().getTime());
            }

            @Override
            public int getBatchSize() {
                return results.size();
            }
        });
    }

    public List<RDFTriple> getTriplesForTopic(String topic) {
        return jdbcTemplate.query("select subject, predicate, object, received_ms from rdf_triple where topic = ? " +
                "order by received_ms desc", new String[] {topic}, new RowMapper<RDFTriple>() {

            @Override
            public RDFTriple mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new RDFTriple(
                        rs.getString("subject"),
                        rs.getString("predicate"),
                        rs.getString("object"),
                        new Date(rs.getLong("received_ms"))
                );
            }
        });
    }

    public void clearResultsOfTopic(String topic) {
        jdbcTemplate.update("delete from rdf_triple where topic = ?", topic);
    }


}
