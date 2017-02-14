The Index html files are teh Code Coverage for my tests (I used Maven through the IntelliJ IDE)
I decided to combine all of my Tests into One big TestSuite Java file under src/test/java/org/cs362/dominion

How to Run the code on Unix System:
1. Copy All Files and Folders over. 
2. cd your way into this folder (Where the readme.txt and the pom.xml is)
3. mvn compile
^^^^^^^ Compiles maven
4. mvn test
^^^^^^^^ Runs the Unit tests for Dominion
5. Code should exit with 8 unit tests, 0 failures, 0 erros and 0 skips.

6. In order to run just the Dominion Game:

7. From the Same Folder (Where readme.txt and pom.xml are)

8. mvn compile
// Compile through maven

9. mvn package
// Create a executable .jar file in Target Folder

10. java -jar target/Dominion-1.0-SNAPSHOT.jar
// Now we access the .jar file in the target folder (Dominion-1.0-SNAPSHOT.jar) was the name of the file for me
