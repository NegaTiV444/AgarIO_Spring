FROM maven
MAINTAINER NegaTiV444

ADD / /app
WORKDIR /app

RUN mvn package
