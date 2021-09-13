Ims-Authn-Kappa-Integration-Tests
==============================

The tests exercise ims-authn-kappa integration with mocked kappa-web. Ims-authn-kappa is dockerized and runs locally.
Wiremock framework is used to mock kappa-web apis which is controlled by Junit itself.

Running locally
---------------

First, you need to create docker container for ims-authn-kappa using docker-compose.yaml file.

Go to directory containing docker-compose.yml and run command:

`docker-compose up --build`
 
It will start up ims-authn-kappa on port 9010. Wiremock is configured to run on localhost on port 8095, which will serve as mocked kappa-web and it will start with execution of tests and stop by end of tests.

To run tests:

`./gradlew clean build -Dtest.config.file=localhost.properties`

OR 

`./gradlew build -Dtest.config.file=localhost.properties`

Default configuration is read from dev.properties, as we are running dockerize ims-authn-kappa so environment variable `test.config.file` must to be passed.




 
 