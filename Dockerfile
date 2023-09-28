FROM openjdk:8-jdk-alpine

RUN apk add --no-cache curl tar bash
ARG MAVEN_VERSION=3.6.3
ARG USER_HOME_DIR="/Users/rammohan/"
RUN mkdir -p /usr/share/maven && \
curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 && \
ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
WORKDIR /usr/src/app
#ENTRYPOINT ["/bin/bash"]

RUN mkdir -p /usr/src/app


COPY pom.xml /usr/src/app
RUN mvn install && rm -rf target

COPY src /usr/src/app/src