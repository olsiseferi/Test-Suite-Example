# Test Suite Example using a simple Event Management Webapp

Go check out the medium article i wrote about Automated Testing [here](https://olsiseferi.medium.com/the-single-most-useful-skill-in-software-development-is-writing-effective-tests-563c461369fd "The single most useful skill in software development, is writing effective Tests?")

### Reference Documentation
To run integration tests use command:

`mvn failsafe:integration-test`

To run unit tests and generate reports use:

`mvn clean install test`

---
Reports are generated under /target/site/jacoco


App logs are generated under /logs/{profile} where profile is either dev/test
