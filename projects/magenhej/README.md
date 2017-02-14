To compile for maven, go to directory CS362-SE2/projects/magenhej and run the following:
	mvn clean
	mvn compile
	(Under the CS362-SE2/projects/magenhej directory, a new directory "target" is created, which now holds the class files)
In the same directory, run the following to acheive coberture code coverage of Dominion:
	mvn cobertura:cobertura
	(All tests and results are displayed with intentional failures from VillageTest, CutpurseTest, Great_HallTest, SmithyTest, and BaronTest; 
	the directory CS362-SE2/projects/magenhej/target/site/corbertura now holds the html coverage reports; note that these html coverage 
	reports were already provided in directory CS362-SE2/projects/magenhej/cobertura prior to running the above maven command)
To run the unit tests without test coverage in maven, go to directory CS362-SE2/projects/magenhej and run the following:
	mvn clean
	mvn compile
	mvn test
	(Again, all tests and results will be displayed with intentional failures from VillageTest, CutpurseTest, Great_HallTest, SmithyTest, 
	and BaronTest)

To compile and run PlayDominion outside of maven, go to directory CS362-SE2/projects/magenhej/src/main/java and run the following:
	javac dominion/*.java
	java dominion.PlayDominion
	(The Dominion game is played)

	
	
