# stage 1: install maven, build the jar

FROM maven:3.8.5-openjdk-17 AS stage1
ARG M2_HOME=/Users/ramanan
VOLUME $M2_HOME/.m2
RUN mkdir -p /opt/repo/
WORKDIR /opt/repo/

COPY investtech-commons/pom.xml /opt/repo/
COPY investtech-portfolio/src/ /opt/repo/src
RUN mvn clean install
RUN mvn clean
RUN rm -rf src pom.xml


COPY investtech-portfolio/pom.xml /opt/repo/
COPY investtech-portfolio/src/ /opt/repo/src
RUN mvn clean install

#stage 2: install java

FROM amazoncorretto:17-alpine as stage1
WORKDIR /opt/repo
COPY --from=stage1 /opt/repo/target/portfolio.jar /opt/repo
