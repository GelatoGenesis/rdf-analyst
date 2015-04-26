package com.rdfanalyst.accounting;

public class InvalidQueryException extends RuntimeException {
    public InvalidQueryException() {
        super("There was an exception while parsing the query.");
    }
}
