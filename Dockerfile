FROM maven:3.6.3-jdk-11

WORKDIR /app
COPY . /app/

RUN mvn package -DskipTests
