package com.rdfanalyst.rdf.engine;

public class StreamInfo {

    private String status;
    private String streamIRI;

    public boolean isStreamRunning() {
        return "RUNNING".equals(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStreamIRI() {
        return streamIRI;
    }

    public void setStreamIRI(String streamIRI) {
        this.streamIRI = streamIRI;
    }
}
