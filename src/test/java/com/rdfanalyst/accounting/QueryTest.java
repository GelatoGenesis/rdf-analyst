package com.rdfanalyst.accounting;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryTest {

    @Test(expected = InvalidQueryException.class)
    public void queriesLessThanThreeWordsAreConsideredInvalid() {
        new Query("oneword twoword");
    }

    @Test
    public void thirdWordOfQueryIsParsedAsQueryName() {
        Query query = new Query("firstword secondword thisIsTheNameOfTheQuery fourthWord And So On");
        assertEquals("thisIsTheNameOfTheQuery", query.getName());
    }

    @Test
    public void queryIsTrimmedWhenItsParsed() {
        Query query = new Query("        some random query with lots of whitespace      ");
        assertEquals("some random query with lots of whitespace", query.getQuery());
    }

}
