FROM ubuntu:latest AS build
RUN apt-get update && apt-get install -y openjdk-17-jdk maven
WORKDIR /
COPY . .

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /

COPY --from=build target/GiftCard-0.0.1-SNAPSHOT.jar GiftCard-0.0.1-SNAPSHOT.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "GiftCard-0.0.1-SNAPSHOT.jar"]