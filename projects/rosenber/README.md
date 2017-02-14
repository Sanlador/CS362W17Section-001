Project folder for Robert Rosenberger, CS362 Winter 2017


The following instructions for running the different facets of the
program assume Maven is installed and set-up correctly. The pom.xml
for Maven is located in rosenber/Dominion. The following instructions
assume you are in the mentioned directory.

To compile the program, run the following command:
	mvn compile
	mvn package
		
To run the tests for this program, call the following command:
	mvn test
	
	*note this may take some time as the full game is run multiple times
	*There will be a lot of output printed to the screen
	
To run the tests and generate a code coverage report:
	mvn cobertura:cobertura
	
 - The coverage report can be found in:
	Dominion/target/site/cobertura/index.html

