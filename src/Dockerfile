#
# Build stage
#
FROM maven:3.8.4-openjdk-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/ProjectBackend-0.0.1-SNAPSHOT.jar demo.jar
# ENV PORT=8443
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]