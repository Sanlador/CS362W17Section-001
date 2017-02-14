ALL_FILES = src/main/java/cs362/dominion/PlayDominion.java src/main/java/cs362/dominion/Card.java src/main/java/cs362/dominion/Randomness.java src/main/java/cs362/dominion/Player.java src/main/java/cs362/dominion/GameState.java 

default: all
#src/main/java/cs362/dominion/
all: $(ALL_FILES)
	mvn compile
	javac -d . $(ALL_FILES)
	echo Main-Class: dominion.PlayDominion > dominion/manifest.txt
	jar cvfm dimc_dom.jar dominion/manifest.txt dominion/*.class
	chmod +x dimc_dom.jar

run: all
	java -jar dimc_dom.jar

tests:
	mvn test

coverage:
	mvn cobertura:cobertura site

clean:
	mvn clean
	rm -f -rf dominion/
	rm -f dimc.jar
