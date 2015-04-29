package com.rdfanalyst.accounting;

import java.util.Date;

public class RDFTriple {

    private String subject;
    private String predicate;
    private String object;
    private Date created;

    public RDFTriple(String subject, String predicate, String object, Date created) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    public String getSubject() {
        return subject;
    }

    public String getPredicate() {
        return predicate;
    }

    public String getObject() {
        return object;
    }
}
