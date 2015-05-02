# rdf-analyst

This is a webapp which takes a supporting role in registering C-SPARQL query-s in RDF engine and at the same time registering user as a listener of those querys. It also helps user to visualise the results.
This app is to be combined with components described [here](https://github.com/a71993/csparqlpush).
It's runs on Jetty Servlet container, uses Spring Boot in the backend and Angular JS in the frontend. As a database it uses MySQL.

It communicates over HTTP with C-SPARQL enginge and RabbitHub. Configuration parameters of those can be found at src/main/resources.

For running the application there is dependency for MySQL. To initiate the MySQL schema and tables, look at the init-mysql-database.sql script and also change the connection properties in application.properties if needed.

Before building the app for the first time make a copy of each property file in src/main/resources and remove -example from the end of the property file name. In the newly created property files change the properties to mach your setup.

Only dependency needed to run the build script of the application is Java 7. The rest (including gradle itself which is the build tool) will be downloaded by the build script.

To build the app (Here an in all following commands in Windows environment replace the ./gradlew with gradlew.bat):

`./gradlew clean build`

Unit tests can be run:

`./gradlew test`

To run the app in jetty container (Default is on port 9595. This can be changed in build.gradle):

`./gradlew jettyRun`

To build a distribution package of the app run:

`./gradlew buildProduct`

The ready made app can be found in build/output/rdf-analyst and from there it can be run with `./start.sh` and stopped accordingly with `./stop.sh` or .bat alternatives if you are on a Windows machine.