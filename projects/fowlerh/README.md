This is the project folder for Henry Fowler

Instructions for compiling and running dominion game:

1) Navigate to directory that contains maven src folder
2) mvn package (This will run the unit tests)
3) After doing mvn package use java -cp target/dominion-1.0-SNAPSHOT.jar PlayDominion to run the game without the unit tests
4) To get code coverage use mvn jacoco:report and the html file will be located in target\site\jacoco folder
