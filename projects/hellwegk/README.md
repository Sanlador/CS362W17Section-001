Project folder for Kailyn Hellwege for CS 362.

Instructions for Running Dominion Game:

1. Go to directory with maven src folder 

2. Type "mvn package", which will run the unit tests with code coverage

3. You can play the game without the unit tests by typing "java -cp target/dominion-1.0-SNAPSHOT.jar PlayGame". (Make sure this is after you have already done "mvn package")

4. You can make the code coverage report by typing "mvn jacoco:report". The HTML file will be in target\site\jacoco.

