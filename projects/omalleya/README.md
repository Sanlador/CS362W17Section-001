Create README.md for Aidan O'Malley

***Command to run jar file when in dominion folder***
***Be sure to be in the same directory as the pom.xml file***


-first run "mvn package" then run the following command

"java -cp target/dominion-1.0-SNAPSHOT.jar com.omalleya.app.PlayDominion"

-to test the dominion implementation run the following commands

"mvn test"

"mvn cobertura:cobertura"

-mvn test will run the unit tests

-running the cobertura tool afterwards creates the coverage files

-to run a specific test

"mvn -Dtest=TESTCLASSNAME#TESTMETHODNAME test"

-the code coverage can be seen in the cobertura/ directory in the index.html file


