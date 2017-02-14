In order to compile and run the code and test it, take the following steps:
1. create a Maven project
2. copy the files in the src folder on GitHub into the relevant src/main folder of the Maven project
3. copy the files in the src/test folder on GitHub into the relevant src/test folder of the Maven project

To run without code coverage:
mvn test
To run with code coverage:
add cobertura dependency to pom.xml
mvn cobertura:cobertura
