Running tests without code coverage tools:
1. Assuming environment is set up, navigate to the Dominion file, i.e. cd Dominion
2. Run 'mvn test', this will run all the tests and print their output
Running tests with code coverage tools: 
1. Assuming environment and pom.xml file is set up for Cobertura, navigate to the Dominion file 
2. Run 'mvn cobertura:cobertura', this will run the tests and check their code coverage of dominion and create an html file of the coverage
