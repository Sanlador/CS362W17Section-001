Compiling mvn:
 
mvn compile
mvn package
mvn test

Running a Program:
java -cp target/Dominion-1.0-SNAPSHOT.jar org.cs362.dominion.PlayDominion

Run test suite:
mvn -Dtest=AllTests test

Code coverage:

Had a very difficult time figuring out how to run mvn through the terminal so I ran it through Eclipse's IED using EclEmma which is how I was able to produce the html file. 
