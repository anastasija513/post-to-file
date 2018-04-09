FROM openjdk:8u151
VOLUME /tmp

ADD . /Main
WORKDIR /Main

ENV SBT_VERSION 1.1.1

RUN touch /usr/lib/jvm/java-8-openjdk-amd64/release

RUN \
  curl -L -o sbt-$SBT_VERSION.deb https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion

EXPOSE 3030

#CMD  sbt run
ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "sbt run" ]