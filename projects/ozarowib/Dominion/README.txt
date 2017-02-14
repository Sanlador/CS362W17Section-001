- Commands to compile:
mvn compile
mvn package
mvn test

- Command to run program:
java -cp target/Dominion-1.0-SNAPSHOT.jar org.cs362.dominion.PlayDominion

-  Command to run test suite:
mvn -Dtest=AllTests test

- Code coverage:
No code coverage tools seem to run properly on my program when used as a mvn command. I am unable to figure out why. I tried three different ones, none found any files to test. The same tools work correctly when run through the Eclipse IDE, so that is how I generated the coverage report I submitted on GitHub. I used EclEmma.
