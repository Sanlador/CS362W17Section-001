Hello This is the README Document for my assignment.
So, in theory the program should compile for both maven and normal use, just like any other.
to compile for MVN
Currently there is a glitch in the pom.xml file that me and the teacher have been working on fixing so currently it
doesn't run correctly on my computer. But others have told me that this is a local problem and it compiles fine
on other people's computers, so in theory this is how it should work.

"mvn compile" after putting the src into the correct place
"mvn package"

This should normally work to compile the code in maven

Outside of maven
src/main/java$ javac org/cs362/dominion/*.java

this should create a program that can be run.

src/main/java$ java org.cs362.dominion.PlayDominion

My code prints alot of stuff, but it has always run to the end.
