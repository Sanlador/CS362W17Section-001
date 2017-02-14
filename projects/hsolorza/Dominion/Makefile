all:
	echo "Use clean, test, build, coverage, or run."
clean:
	mvn clean
test:
	mvn test
build:
	mvn package
coverage: clean
	mvn cobertura:cobertura
run:
	java -jar target/hsolorza-1.0-SNAPSHOT.jar
