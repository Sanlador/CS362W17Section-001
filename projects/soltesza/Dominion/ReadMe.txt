To run Dominion:
mvn clean
mvn package -DskipTests
java -cp target/Dominion-1.0-SNAPSHOT.jar org.cs362.dominion.PlayDom


To run tests:
mvn clean
mvn package
mvn test