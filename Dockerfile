FROM maven:latest AS build
COPY src /app/src/
COPY pom.xml /app/
WORKDIR /app
RUN mvn clean install javafx:run
#
#FROM openjdk:11
#COPY --from=build /app/target/FantaSolver.jar /app/FantaSolver.jar
#ENTRYPOINT ["java","-jar","/app/FantaSolver.jar"]
