in order to run this project
you need to edit the test.properties file with correct values 
-- e.g. aws cred and region, comparable (old) result.json path, faasbenchmark project path, test-name and test-framework (aws only supported now) 

java jdk 11; maven; (and all faasbenchmark depencdencies is needed)
run <from project path>: mvn clean test -Dsurefire.suiteXmlFiles=testng.xml
