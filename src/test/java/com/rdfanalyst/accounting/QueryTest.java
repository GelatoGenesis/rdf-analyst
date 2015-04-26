package com.rdfanalyst.accounting;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryTest {

    @Test(expected = InvalidQueryException.class)
    public void queriesLessThanThreeWordsAreConsideredInvalid() {
        new Query("oneword twoword", null);
    }

    @Test
    public void thirdWordOfQueryIsParsedAsQueryName() {
        Query query = new Query("firstword secondword thisIsTheNameOfTheQuery fourthWord And So On", null);
        assertEquals("thisIsTheNameOfTheQuery", query.getTopic());
    }

    @Test
    public void queryIsTrimmedWhenItsParsed() {
        Query query = new Query("        some random query with lots of whitespace      ", null);
        assertEquals("some random query with lots of whitespace", query.getQuery());
    }

}
