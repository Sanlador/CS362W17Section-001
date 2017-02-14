Assuming Maven 3.3.9 is downloaded,
Assuming Java JDK1.8 is downloaded.


in windows cmd line
-Navigate to the "DOMINIONvF" file
-Navigate into "DOMINION"
	pom file should be there, with src
-type "mvn compile"
	"mvn package"

Then either run the jar file for testing or the dominion code. Or run a Maven Test on it with
"mvn test"

Assuming Cobertura coverage tool is installed:
	To run code coverage:
	"mvn cobertura:cobertura"
	index file is located in
	target->site->cobertura->index.

*THESE INSTRUCTIONS ARE EXACTLY WHAT IS ON
THE SLIDES THAT ARE POSTED ON CANVAS.
THERE SHOULD BE NOTHING DIFFERENT ABOUT MY CODE*
