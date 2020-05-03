IF "%1"=="build" (
    mvn clean install
)
IF "%1" == "noTests" (
	mvn clean install -DskipTests
)
IF "%1"=="start" (
    java -jar target\what-to-eat-0.0.1-SNAPSHOT.jar -Dspring.config.location=config\application.yml
)
