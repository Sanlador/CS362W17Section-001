CS 362 - WINTER TERM - 2017
LOUIS LEON

In order to compile and run the code and test it: 
1. create a Maven project 
2. copy the files in the src folder on GitHub into the relevant src/main folder of the Maven project 
3. copy the files in the src/test folder on GitHub into the 
relevant src/test folder of the Maven project

To run with code coverage: 
1. Install Eclemma Jacoco Plugin or add to pom.xml as dependency (the instructor's instructions)
2. Run project with coverage by Jacoco
So, Navigate to directory that contains maven src folder 2) mvn package (This will run the unit tests) 3) After doing mvn package use 
java -cp target/dominion-1.0-SNAPSHOT.jar PlayDominion to run the game without the unit tests 4) To get code coverage use mvn 
jacoco:report and the html file will be located in target\site\jacoco folder
