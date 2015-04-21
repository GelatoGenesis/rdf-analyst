# rdf-analyst

This is a webapp which takes a supporting role in registering C-SPARQL query-s in RDF engine and at the same time registering user as a listener of those querys. It also helps user to visualise the results.
This app is to be combined with components described [here](https://github.com/a71993/csparqlpush).
It's runs on Jetty Servlet container, uses Spring Boot in the backend and Angular JS in the frontend.

It communicates over HTTP with C-SPARQL enginge and RabbitHub. Configuration parameters of those can be found at src/main/resources.

Only dependency needed to build the application is Java 7. The rest (including gradle it self which is the build tool) will be downloaded by the build script.

To build the app (Here an in all following commands in Windows environment replace the ./gradlew with gradlew.bat):

`./gradlew clean build`

Unit tests can be run:

`./gradlew test`

To run the app in jetty container (Default is on port 9595. This can be changed in build.gradle):

`./gradlew jettyRun`