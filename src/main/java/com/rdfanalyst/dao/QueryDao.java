package com.rdfanalyst.dao;

import com.rdfanalyst.accounting.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class QueryDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean doesQueryWithNameExist(String name) {
        return jdbcTemplate.queryForObject("select count(topic) from query where topic = ?", new String[]{name}, Integer.class) > 0;
    }

    public void addQuery(Query query) {
        jdbcTemplate.update("insert into query (topic, query) values (?, ?)", query.getTopic(), query.getQuery());
    }

    public List<Query> getAllQueries() {
        return jdbcTemplate.query("select topic, query from query", new QueryMapper());
    }

    public Query findByName(String topic) {
        return jdbcTemplate.queryForObject("select topic, query from query where topic = ?", new String[]{topic}, new QueryMapper());
    }

    class QueryMapper implements RowMapper<Query> {

        @Override
        public Query mapRow(ResultSet rs, int rowNum) throws SQLException {
            Query query = new Query();
            query.setQuery(rs.getString("query"));
            query.setTopic(rs.getString("topic"));
            return query;
        }
    }

    public void deleteQuery(String topic) {
        jdbcTemplate.update("delete from query where topic = ?", topic);
    }
}
