# Build
FROM maven:3.8.4-jdk-11 AS build
COPY src /home/app/src
COPY pom.xml /home/app
WORKDIR /home/app
RUN mvn clean package

# Package
FROM gcr.io/distroless/java
COPY --from=build /home/app/target/multi-region-consumer-1.0.0-jar-with-dependencies.jar /usr/local/lib/multi-region-consumer.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/multi-region-consumer.jar"]