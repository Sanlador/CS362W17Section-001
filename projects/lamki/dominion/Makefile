ALL_FILES = src/main/java/dominion/Dominion.java src/main/java/dominion/Player.java src/main/java/dominion/Game.java src/main/java/dominion/Card.java src/main/java/dominion/Treasure.java src/main/java/dominion/Adventure.java src/main/java/dominion/Victory.java

default: all

all: $(ALL_FILES) lamki_dom 		#compile all items
	mvn compile
	
	

lamki_dom: $(ALL_FILES) 		#compile game into executable 
	javac -d . $(ALL_FILES)
	echo Main-Class: dominion.Dominion > dominion/manifest.txt
	jar cvfm lamki_dom.jar dominion/manifest.txt dominion/*.class
	chmod +x lamki_dom.jar

run: lamki_dom #run game
	java -jar lamki_dom.jar

tests: all #run tests
	mvn test

coverage: all 				#run tests with coverage
	mvn cobertura:cobertura site

clean: 					#clean dir
	mvn clean
	rm -f -rf dominion/
	rm -f lamki_dom.jar