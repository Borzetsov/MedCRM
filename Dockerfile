FROM maven:3.9.16-eclipse-temurin-21 AS build
WORKDIR /med-crm
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre
COPY --from=build /med-crm/target/*.jar /med-crm.jar
ENTRYPOINT ["java", "-jar", "/med-crm.jar"]